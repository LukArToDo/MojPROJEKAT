package company.Main.Support.Izvodi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import company.Main.Support.Izvodi.PDFManagerIzvodi;


public class ChooseIzvodController implements Initializable{
	@FXML
	private ComboBox<String> cbSender;
	@FXML
	private ComboBox<String> cbReceiver;
	@FXML
	private PasswordField tfPass;
	@FXML
	private DatePicker dpFromDay;
	@FXML
	private DatePicker dpToDay;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnSearch;
	@FXML
	private ScrollPane scroll;
	@FXML
	private VBox vbInbox;
	@FXML
	private Button btnOK;
	@FXML
	private Button btnCancel;
	@FXML
	private Label lbError;

	private String pattern = "dd.MM.yyyy";		     	    	
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
	public PDFManagerIzvodi pdfManager;
    private String path;
	
	// Event Listener on Button[#btnClear].onAction
	@FXML
	private void clearAll(ActionEvent event) throws ClassNotFoundException, SQLException {
		tfPass.setText("");
		cbSender.setValue("");
		cbReceiver.setValue("");
		dpToDay.setValue(LocalDate.now());
		dpFromDay.setValue(LocalDate.now().minusDays(1));
		vbInbox.getChildren().clear();
	}
	// Event Listener on Button[#btnSearch].onAction
	@FXML
	private void searchIzvod(ActionEvent event) throws ParseException, MessagingException, ClassNotFoundException, SQLException  {
	checkCB(cbSender);
	checkCB(cbReceiver);	
	checkTF(tfPass);
	LocalDate from=checkFromDP();
	LocalDate to=checkToDP();
	if (from.isAfter(to)){
		company.Main.Main.showAlertError("GRESKA!", "Unesite ispravan datum!");
		dpFromDay.requestFocus();
	}else{
		System.out.println("Adresa posiljaoca: "+cbSender.getValue());
		System.out.println("Adresa primaoca: "+cbReceiver.getValue());
		System.out.println("Password primaoca: "+tfPass.getText());
		System.out.println("Od datuma: "+from+" do datuma: "+to);
		long days = from.until(to, ChronoUnit.DAYS);
		System.out.println("Broj dana za pretragu je: "+ days);
		int d=(int)days;
		try{
		if(!company.Main.Support.Izvodi.EmailAttachmentReceiver.downloadEmailAttachments(cbSender.getValue(), cbReceiver.getValue(), tfPass.getText(), to, d )){
			System.out.println("konekcija nije uspjesna");
			cbReceiver.requestFocus();
			lbError.setText("Konekcija nije uspjela!");
			lbError.setStyle("-fx-text-fill:red;");
		}
		else{
			System.out.println("konekcija je uspjesna!");
			String stm1="INSERT INTO "+company.Main.Main.dbName+".inbox (address,pass,id) SELECT * FROM (SELECT '"+cbReceiver.getValue()+"','"+tfPass.getText()+
					"','r') AS tmp WHERE NOT EXISTS (SELECT address from "+company.Main.Main.dbName+".inbox where address='"+cbReceiver.getValue()+"') LIMIT 1";
			String stm2="UPDATE "+company.Main.Main.dbName+".inbox SET pass='"+tfPass.getText()+"', id='r' where address='"+cbReceiver.getValue()+"'";
			String stm3="INSERT INTO "+company.Main.Main.dbName+".inbox (address,id) SELECT * FROM (SELECT '"+cbSender.getValue()+
					"','s') AS tmp WHERE NOT EXISTS (SELECT address from "+company.Main.Main.dbName+".inbox where address='"+cbSender.getValue()+"') LIMIT 1";
			
			if(!company.Main.DBConnection.DBConnection.dbExecuteUpdate(stm1, "inbox")){
				company.Main.DBConnection.DBConnection.dbExecuteUpdate(stm2, "inbox");
			}
			company.Main.DBConnection.DBConnection.dbExecuteUpdate(stm3, "inbox");
		
			File attach=new File("C:\\MojProjekat\\Attachment");
			attach.isDirectory();
			File[] list=attach.listFiles();
		
			
			ToggleGroup inbox= new ToggleGroup();
			vbInbox.getChildren().clear();
			if(list.length>0){
			for(int i=0; i<list.length; i++){
				System.out.println("Files in folder attachment are: "+list[i].toString());
				RadioButton rb=new RadioButton(list[i].toString().substring(26));
				rb.setId("rb"+i);
				rb.setUserData(list[i].toString());
				rb.setToggleGroup(inbox);
				
				Tooltip t=new Tooltip(list[i].toString().substring(26));
				rb.setTooltip(t);
				vbInbox.getChildren().add(rb);
			}
			}else{
				Label l=new Label("");
				Label l1=new Label("U zadatom periodu nema poruka");
				Label l2=new Label("od posiljaoca "+cbSender.getValue());
				l.setStyle("-fx-text-fill:red");
				l1.setStyle("-fx-text-fill:red");
				l2.setStyle("-fx-text-fill:red");
				vbInbox.getChildren().addAll(l,l1,l2);
			}
			
			inbox.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

			         if (inbox.getSelectedToggle() != null) {
			             System.out.println(inbox.getSelectedToggle().getUserData().toString());
			             path=inbox.getSelectedToggle().getUserData().toString();
			         }
			     } 
			});
		}
		}catch(Exception e){
			e.getMessage();
		}
	}
	}
	
	// Event Listener on Button[#btnOK].onAction
	@FXML
	private void chooseIzvod(ActionEvent event) throws ClassNotFoundException, IOException {
		if (path!=null){ 
		showIzvod(path);
		}
	}
	
	private void showIzvod(String filePath) throws ClassNotFoundException, IOException{
		System.out.println("Path u showIzvod je "+filePath);
		File file=new File(path);
		if (file.exists()){		
			System.out.println("AbsolutePath u show izvod je :"+filePath);
		 PDFManagerIzvodi pdfManager = new PDFManagerIzvodi();
			pdfManager.setFilePath(filePath);
			@SuppressWarnings({ "unused", "static-access" })
			String text = pdfManager.ToText();
			company.Main.Main.closePopUpWindow();
		}
	}
	
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	private void close(ActionEvent event) {
		company.Main.Main.closePopUpWindow();
	}
	
	private void checkCB(ComboBox<String> cb){
		if (cb.getValue().toString().trim().equals("") || cb.getValue()==null ){
			cb.requestFocus();
		}
	}
	
	private void checkTF(PasswordField tf) {
		if (tf.getText().trim().equals("")|| tf.getText()==null){
			tf.requestFocus();
		}
	}
	
	private LocalDate checkFromDP(){
		if (dpFromDay.getValue()==null||dpFromDay.getValue().equals("")){
			dpFromDay.requestFocus();
		}
		else{
			return dpFromDay.getValue();
		}
		return null;	
	}
	
	private LocalDate checkToDP(){
		if (dpToDay.getValue()==null||dpFromDay.getValue().equals("")){
			dpToDay.requestFocus();
		}
		else{
			return dpToDay.getValue();
		}
		return null;
		
	}
	
	private void setOtherFields() throws ClassNotFoundException, SQLException {
		setSender();
		setReceiver();
		setDatePicker(dpFromDay);
		setDatePicker(dpToDay);
		dpToDay.setValue(LocalDate.now());
		dpFromDay.setValue(LocalDate.now().minusDays(1));
	}
	
	private void setSender() throws ClassNotFoundException, SQLException{
		ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery("Select * from "+company.Main.Main.dbName+".inbox where id='s'", "inbox");
		ObservableList<String> data= FXCollections.observableArrayList(); 
		while(rs.next()){
			data.add(rs.getString("address"));
		}
		cbSender.setEditable(true);
		cbSender.setItems(data);
		if (!data.isEmpty()){
		cbSender.setValue(data.get(0));
		}
	}
	
	private void setReceiver() throws ClassNotFoundException, SQLException{
		ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery("Select * from "+company.Main.Main.dbName+".inbox where id='r'", "inbox");
		ObservableList<String> data= FXCollections.observableArrayList(); 
		while(rs.next()){
			data.add(rs.getString("address"));
		}	
		cbReceiver.setEditable(true);
		cbReceiver.setItems(data);
		if (!data.isEmpty()){
		cbReceiver.setValue(data.get(0));
		setTextFields(cbReceiver.getValue());
		}
		if (data.isEmpty()){
			System.err.println("Data je prazna");
			getMail();
		}
	}
	
	private void setDatePicker(DatePicker dp){
	    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
	      @Override
	      public String toString(LocalDate date) {
	        if (date != null) {
	          return dateFormatter.format(date);
	        } else {
	          return "";
	        }
	      }

	      @Override
	      public LocalDate fromString(String string) {
	    	  try{
	        if (string != null && !string.isEmpty()) {
	          return LocalDate.parse(string, dateFormatter);
	        } 
	    	  }catch (Exception ex){
	    		  string="";
	    		  dp.requestFocus();
	    	  }
			return null;
	      }
	    };
	    dp.setConverter(converter);
	    dp.setPromptText(pattern.toLowerCase());
	    dp.focusedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	            if (!newValue){
	                dp.setValue(dp.getConverter().fromString(dp.getEditor().getText()));
	            }
	        }
	    });
	}
	
	private void setTextFields(String newValue) throws ClassNotFoundException, SQLException {
		ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery("Select * from "+company.Main.Main.dbName+".inbox where address='"+newValue+"' and id='r'", "inbox");
		ObservableList<String> data= FXCollections.observableArrayList(); 
		while(rs.next()){
			data.add(rs.getString("pass"));
		}
		if (!data.isEmpty()){
		tfPass.setText(data.get(0));
		}
	}
	
	private void getMail() throws ClassNotFoundException, SQLException{
	ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery("Select * from "+company.Main.Main.dbName+".companydata", "companydata");
	ObservableList<String> data= FXCollections.observableArrayList(); 
		while(rs.next()){
			data.add(rs.getString("mail"));
		}
		cbReceiver.setEditable(true);
		if (!data.isEmpty()) {
		cbReceiver.setItems(data);
		cbReceiver.setValue(data.get(0));
		}
	}
	
	private void getTextProperty(ComboBox<String> cb) {
		cb.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
		    lbError.setText("");    
		    });
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			setOtherFields();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	
		cbReceiver.getSelectionModel().selectedItemProperty()
	    .addListener(new ChangeListener<String>() {
	        public void changed(ObservableValue<? extends String> observable,
	                            String oldValue, String newValue) {
	        	
	        	System.out.println("cbReceiver from "+oldValue+" to "+newValue);
	        	vbInbox.getChildren().clear();
	            try {
					setTextFields(newValue);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
	        	Platform.runLater(() -> { 
	    			tfPass.requestFocus();	
	    		});
	        }

	    });
		
		cbSender.getSelectionModel().selectedItemProperty()
	    .addListener(new ChangeListener<String>() {
	        public void changed(ObservableValue<? extends String> observable,
	                            String oldValue, String newValue) {
	        	
	        	System.out.println("cbSender from "+oldValue+" to "+newValue);
	        	vbInbox.getChildren().clear();
	        }

	    });
		
		cbReceiver.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
	       
			if (! isNowFocused) {
	            cbReceiver.setValue(cbReceiver.getEditor().getText());
	            lbError.setText("");
	            try {
					setTextFields(cbReceiver.getEditor().getText());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
	        }
	    });
	
		cbSender.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
		        if (! isNowFocused) {
		            cbSender.setValue(cbSender.getEditor().getText());
		        }
		    });
		getTextProperty(cbReceiver);
	}
	
}
