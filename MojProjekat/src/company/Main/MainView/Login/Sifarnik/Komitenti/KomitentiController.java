package company.Main.MainView.Login.Sifarnik.Komitenti;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import company.Main.Main;
import company.Main.MainView.Login.Sifarnik.CompanyData.CompanyDataController;
import company.Main.Support.Korisnici.Korisnici;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class KomitentiController extends CompanyDataController {

public static BorderPane mainPane;

public static CompanyDataController c;
public static Label l;
public static AnchorPane center;
public static RadioButton rbRS=new RadioButton("RS");
public static RadioButton rbFBiH=new RadioButton("FBiH");
public static RadioButton rbBD= new RadioButton("BD");
public static RadioButton rbIno= new RadioButton("Ino");
public static ToggleGroup entitet=new ToggleGroup();
public static TextField tfKK = new TextField();
public static TextField tfKD = new TextField();
private static String ent;
public static HBox hbEntitet=new HBox();
public static String key="";
public static int q=0;

	public static void Komitenti() throws IOException, ClassNotFoundException, SQLException {
		mainPane=company.Main.Main.mainPane;
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView/Login/Sifarnik/CompanyData/CompanyData.fxml"));
		center= (AnchorPane) loader.load();
		c= loader.getController(); 
		mainPane.setCenter(center);
		
		setScene();
		c.table="komitenti";
		c.tablePregled.setItems(getData());
		sortedTable();
		c.tablePregled.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				int row = c.tablePregled.getSelectionModel().getSelectedIndex();
				try{
				c.populateFields(row);
				findRadioButtonEntitet(row);
				tfKK.setText(c.listData.get(row).getCompanySign());
				tfKD.setText(c.listData.get(row).getCompanyStamp());
				}catch (Exception e){
					e.getMessage();
				}
			}	
		});	
	}
	
	public static void sortedTable(){
		c.tablePregled.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t)  {		
				if (t.getCode()== KeyCode.ESCAPE){
					key="";
				c.tablePregled.setItems(null);
            	try {
            		c.listData.clear();
            		c.listData=company.Main.Support.Korisnici.KorisniciDAO.getSortedDataCompany(key,"komitenti", "entitet", "konto_kupac", "konto_dobavljac");
            		c.tablePregled.setItems(c.listData);
            		selectTable();
            		
				} catch (ClassNotFoundException | SQLException e){
					e.printStackTrace();
				} 
            	
				}
				if (t.getCode()==KeyCode.DOWN){
					int row = c.tablePregled.getSelectionModel().getSelectedIndex()+1;
					try{
					c.populateFields(row);
					}catch (Exception e){
						e.getMessage();
					}
				}
				if (t.getCode()==KeyCode.UP){
					int row = c.tablePregled.getSelectionModel().getSelectedIndex()-1;
					try{
					c.populateFields(row);
					}catch (Exception e){
						e.getMessage();
					}
				}
				 if (t.getCode()==KeyCode.BACK_SPACE){
	                	if (key.length()>0){
	                	key=key.substring(0, key.length()-1);
	                	System.out.println("Key typed: "+key);
	                	c.tablePregled.setItems(null);
	                	try {
	                		c.listData.clear();
	                		c.listData=company.Main.Support.Korisnici.KorisniciDAO.getSortedDataCompany(key,"komitenti", "entitet", "konto_kupac", "konto_dobavljac");
	                		c.tablePregled.setItems(c.listData);
	                		selectTable();
						} catch (ClassNotFoundException | SQLException e){
							e.printStackTrace();
						} 
	                	}
	                }     
	            }  
	        });  
	        
	        c.tablePregled.setOnKeyTyped(new EventHandler<KeyEvent>() {  
	            @Override  
	            public void handle(KeyEvent ke) {  
	            	if (!ke.getCharacter().matches( "[^A-Za-z0-9. ]")){
	            	
	                key=key.concat(ke.getCharacter());
	                System.out.println("Key typd: "+key);
	                c.tablePregled.setItems(null);
                	try {
                		c.listData.clear();
                		c.listData=company.Main.Support.Korisnici.KorisniciDAO.getSortedDataCompany(key,"komitenti", "entitet", "konto_kupac", "konto_dobavljac");
                		c.tablePregled.setItems(c.listData);
                		selectTable();
					} catch (ClassNotFoundException | SQLException e){
						e.printStackTrace();
					} 
	            	}else{System.out.println("Letter is not alphanumeric or space or dash");}
			}	
		});	
	}
	
	public static void selectTable(){
		try{
			c.tablePregled.getSelectionModel().select(0);
			c.populateFields(0);
			}catch (Exception e){
				e.getMessage();
			}
	}
	
	public static ObservableList<Korisnici>  setupNewTable (ArrayList<String> sortedData) throws IOException{
		ObservableList<Korisnici> newData=FXCollections.observableArrayList();
		newData.clear();
			
		for (int i=0; i<sortedData.size();i++){
			System.out.println("PODACI U TABLEPOPUP iz ARRAY su:"+sortedData.get(i).toString()+","+sortedData.get(i+2).toString());
			Korisnici dt=new Korisnici();
			dt.setCompanySifraPJ(sortedData.get(i).toString());
			dt.setCompanyName(sortedData.get(i+1).toString());
			newData.add(dt);
			i=i+3;		
		}
		return newData;
	}
	
	private static void findRadioButtonEntitet(int row) {
		String rb=c.listData.get(row).getCompanyLogo();
		for (Node node : hbEntitet.getChildren()){
			if (node.getId()!=null ){
				if(((RadioButton)node).getText().equals(rb)){			
					((RadioButton)node).setSelected(true);
				}
			}
		}	
		
	}
	
	private static ObservableList<Korisnici> getData() throws ClassNotFoundException, SQLException {
		c.listData=company.Main.Support.Korisnici.KorisniciDAO.searchDataCompany("komitenti", "entitet", "konto_kupac", "konto_dobavljac");		
		return c.listData;
	}

	private static void setScene() throws IOException{
		c.lbDani.setText("Broj dana:");
		c.tablePregled.setLayoutX(550);
		c.tablePregled.setLayoutY(80);
		c.tablePregled.setPrefSize(300, 500);
		c.colNazivPJ.setText("NAZIV KOMITENTA");
		c.colNazivPJ.setPrefWidth(240);
		c.colSifraPJ.setPrefWidth(60);
		c.btnSaveImg.setVisible(false);
		c.apImages.setVisible(false);
		try{
		getNewTextFields();
		}catch(IllegalArgumentException iae){
			System.out.println("Greska u postavljanju scene"+iae.getStackTrace());
			iae.getMessage();
		}
		setBtnClear();
		setBtnAdd();
		setBtnUpdate();
		setBtnDelete();
	}
	
	private static void setBtnDelete() {
		c.btnDelete.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	int id=Integer.parseInt(c.tfSifraPJ.getText());		
						try {

							int r=company.Main.Main.showAlertConfirmation("OPREZ!!!", "Jeste li sigurni da zelite obrisati komitenta "+c.tfCompanyName.getText()+" ?",false);
							if (r==1){
								company.Main.Support.Korisnici.KorisniciDAO.deleteCompanyData( id, "komitenti");
								c.tablePregled.setItems(getData());
								clearALL();
							}
							
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
			  }
		});
	}

	private static void setBtnUpdate() {
		c.btnEdit.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	if (c.tfCompanyName.getText()!=null && c.tfCompanyName.getText().length()>0 && !c.tfCompanyName.getText().trim().equals("")) {
		    		if (c.tfSifraPJ.getText().length()<1){
		    			c.tfSifraPJ.requestFocus();
		    		}else if(c.tfCity.getText()==null || c.tfCity.getText().trim().equals("")){
		    			c.tfCity.requestFocus();
		    		}else if(c.tfJIB.getText().length()!=13){
		    			c.tfJIB.requestFocus();
		    		}else if (c.rbDaPDV.isSelected()&& c.tfPDV.getText().length()!=12) {
		    			c.tfPDV.requestFocus();
		    		}else if (!rbRS.isSelected() && !rbFBiH.isSelected() && !rbBD.isSelected() && !rbIno.isSelected()) {
		    			rbRS.requestFocus();
		    			rbRS.setSelected(true);	
					}else{
		    			int id=Integer.parseInt(c.tfSifraPJ.getText());
		    			c.tfs.clear();
		    			c.checkTF();
		    			changeDataBase("edit", id);
					}
		    	} else {
		    		company.Main.Main.showAlertError("GRESKA!", "Unesite naziv kompanije.");
					c.tfCompanyName.requestFocus();
		    	}    	
		    }
		});
	}

	private static void setBtnAdd() {
		c.btnAdd.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		    	if(c.tfJIB.getText().length()!=13){
		    		c.tfJIB.requestFocus();
		    	}else if (c.rbDaPDV.isSelected()&& c.tfPDV.getText().length()!=12) {
					c.tfPDV.requestFocus();
				}else if (!rbRS.isSelected() && !rbFBiH.isSelected() && !rbBD.isSelected() && !rbIno.isSelected()) {
		    		rbRS.requestFocus();
		    		rbRS.setSelected(true);
		    	}else{
		      
		    	int id=Integer.parseInt(c.tfSifraPJ.getText());
		    	if (c.tfCompanyName.getText()!=null && c.tfCompanyName.getText().length()>0 && !c.tfCompanyName.getText().trim().equals("")) {
					if (!c.findIfNameExists()){
					c.tfCompanyName.requestFocus();				
		    	}else if (!c.findIfIDExists(id)){
		    		c.tfSifraPJ.requestFocus();
		    	}else if(c.tfCity.getText()==null || c.tfCity.getText().trim().equals("")){
		    		c.tfCity.requestFocus();
		    	}else if(!c.findIfJIBExists()){
		    		c.tfJIB.requestFocus();
		    	}else if(c.tfPDV.getText().length()==12 && !c.findIfPDVExists()){
		    		c.tfPDV.requestFocus();
		    	}else{
		    		c.tfs.clear();
		    		 try {
		    				c.getTFS();
		    			} catch (ClassNotFoundException | SQLException e1) {
		    				e1.printStackTrace();
		    			}
		    	changeDataBase("add", id);
		    	}
		    	}
		    	}
		    }
		});
	}
	
	private static void changeDataBase(String btn, int id) {
		   
        System.out.println("c.TFS prije remove 21,20,19 je: "+c.tfs);
        if (btn.equals("add")){
        c.tfs.remove(21);
        c.tfs.remove(20);
        c.tfs.remove(19);
        }
        c.tfs.add(ent);
        c.tfs.add(tfKK.getText());
        c.tfs.add(tfKD.getText());
        System.out.println("TFS u KOMITENTI je:"+ c.tfs);
        try {
        	if (btn.equals("add")){
			company.Main.Support.Korisnici.KorisniciDAO.insertCompanyData(c.tfs, id, "komitenti","entitet", "konto_kupac", "konto_dobavljac");
			company.Main.Main.showAlertInfo("NOVI KOMITENT", "Komitent "+ c.tfCompanyName.getText()+ " je uspjesno dodat u bazu podataka");
        	}
        	if (btn.equals("edit")){
    			company.Main.Support.Korisnici.KorisniciDAO.updateCompanyData(c.tfs, id, "komitenti","entitet", "konto_kupac", "konto_dobavljac");
    			company.Main.Main.showAlertInfo("NOVI KOMITENT", "Komitent "+c.tfCompanyName.getText()+" je uspjesno izmjenjen u bazi podataka");
        	}
        	
		} catch (ClassNotFoundException | SQLException | ParseException e1) {
			e1.printStackTrace();
			company.Main.Main.showAlertError("GRESKA!","Neuspjesno doavanje/izmjena komitenta");
		}

		try {
			c.tablePregled.setItems(getData());
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
    }
	
	private static void setBtnClear() {
		c.btnClear.setOnAction(new EventHandler<ActionEvent>() {
		    @Override 
		    public void handle(ActionEvent e) {
		         clearALL();
		    }
		});
	}
	
	public static void clearALL (){
		 c.clear(null); 
	        tfKK.setText("");
	        tfKD.setText("");
	        try{
	        entitet.getSelectedToggle().setSelected(false);
	        }catch(Exception ex){
	        	ex.getMessage();
	        }	     
	}
	
	public static void getNewTextFields(){
	VBox vbNew=new VBox();
	Label lbEnt=new Label("Entitet:");

	rbRS.setId("RS");
	rbFBiH.setId("FBiH");
	rbBD.setId("BD");
	rbIno.setId("Ino");
	rbRS.setToggleGroup(entitet);
	rbFBiH.setToggleGroup(entitet);
	rbBD.setToggleGroup(entitet);
	rbIno.setToggleGroup(entitet);
	
	hbEntitet.getChildren().clear();
	hbEntitet.getChildren().addAll(rbRS,rbFBiH,rbBD,rbIno);
	hbEntitet.setSpacing(5);

	VBox vbE= new VBox();
	vbE.getChildren().addAll(lbEnt,hbEntitet);
	VBox.setMargin(hbEntitet, new Insets(5,0,5,0));
	
	Label lbKK = new Label ("Konto kupca:");
	VBox vbKK= new VBox();
	vbKK.getChildren().addAll(lbKK,tfKK);
	vbKK.setSpacing(2);
	
	Label lbKD = new Label ("Konto dobavljaca:");
	VBox vbKD= new VBox();
	vbKD.getChildren().addAll(lbKD,tfKD);
	vbKD.setSpacing(2);
	
	vbNew.getChildren().addAll(vbE,vbKK,vbKD);
	vbNew.setSpacing(5);
	vbNew.setLayoutX(25);
	vbNew.setLayoutY(450);
	vbNew.setPrefWidth(187);
	
	Separator h=new Separator();
	h.setOrientation(Orientation.HORIZONTAL);
	h.setLayoutX(35);
	h.setLayoutY(420);
	h.setPrefWidth(165);
	
	center.getChildren().addAll(h,vbNew);
	
	entitet.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		public void changed(ObservableValue<? extends Toggle> ov,
	        Toggle old_toggle, Toggle new_toggle) {
			if(new_toggle!=null){
	    	RadioButton chk = (RadioButton)new_toggle.getToggleGroup().getSelectedToggle(); // Cast object to radio button
	        System.out.println("Selected Radio Button - "+chk.getText());
	        ent=chk.getText();
			}
	        if (rbRS.isSelected()) {
	           tfKK.setText("201000");
	           tfKD.setText("432000"); 
	           } 
	        if (rbFBiH.isSelected()) {
		        tfKK.setText("202000");
		        tfKD.setText("433000"); 
		       }
	        if (rbBD.isSelected()) {
		       tfKK.setText("203000");
		       tfKD.setText("434000"); 
		       } 
	        if (rbIno.isSelected()) {
		        tfKK.setText("205000");
		        tfKD.setText("435000"); 
		       } 
	        }
	});
	}
}
