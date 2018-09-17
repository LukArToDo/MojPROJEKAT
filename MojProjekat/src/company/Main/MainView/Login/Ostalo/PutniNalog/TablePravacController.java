package company.Main.MainView.Login.Ostalo.PutniNalog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import company.Main.Support.Finansije.Finansije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TableView;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TableColumn;

public class TablePravacController  implements Initializable {
	@FXML
	private AnchorPane app;
	@FXML
	private TableView<Finansije> table;
	@FXML
	private TableColumn<Finansije, String> pravacColumn;
	@FXML
	private TableColumn<Finansije, String> kmColumn;
	@FXML
	private VBox vbLabel;
	@FXML
	private VBox vbText;
	@FXML
	private VBox vbAdd;
	@FXML
	private VBox vbRemove;
	@FXML
	private Button add;
	@FXML
	private Button remove;
	@FXML
	private TextField tfStart;
	@FXML
	private TextField tfMjesto1;
	@FXML
	private Label pravacLabel;
	@FXML
	private Button addTF;
	@FXML
	private Button delTF;
	@FXML
	private TextField tfKM;
	@FXML
	private Button addToBase;
	@FXML
	private Button cancel;
	@FXML
	private Button close;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	
	public int i=1;
	public String field="tfMjesto";
	public String broj="";
	public Label newLabel;
	public Button newAdd;
    private String oldString;
    
	// Event Listener on Button[#addTF].onAction
	@FXML
	public void addTextField() {
		TextField newTF= new TextField();
		newTF.setId(field+i);
		vbText.getChildren().add(newTF);
		i=i+1;
		newTF.setId(field+i);	
			
		newLabel = new Label("Mjesto posjete:");
		vbLabel.getChildren().add(newLabel);
		
		add = new Button ("+");
		vbAdd.getChildren().add(add);
		
		remove = new Button("-");
		remove.setMinWidth(vbRemove.getWidth());
		vbRemove.getChildren().add(remove);
		
		addListenerToTF(newTF);
		addListenerToAddButton ();
		addListenerToDelButton();
	}
	
	private void addListenerToDelButton() {

		for (Node node : vbRemove.getChildren()){
			((Button)node).setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                int index=vbRemove.getChildren().indexOf((Button)node);
	            	try {
						delFields(index);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}          
	            }
	        });
			}	  
	}
	
	private void delFields(int index) throws ClassNotFoundException {
		if (index>0) {
		   vbText.getChildren().remove(index+1);
		   vbLabel.getChildren().remove(index);
		   vbAdd.getChildren().remove(index);
		   vbRemove.getChildren().remove(index);
		   changeLabelAndTable ();
		}
	}
	
	// Event Listener on Button[#addToBase].onAction
	@FXML
	public void addToBase(ActionEvent event) throws SQLException, ClassNotFoundException {
	String insertStmt="INSERT INTO "+company.Main.Main.dbName+".putninalog "
	       		+ "(pravac, "
	       		+ "km) "
	       		+ "VALUES ('"+
	       		pravacLabel.getText()+"', '"+
	       		tfKM.getText()+"')"  ;
	//Execute INSERT operation
    try {
    	company.Main.DBConnection.DBConnection.dbExecuteUpdate(insertStmt, "putninalog");
 	   	populateTable();
    } catch (SQLException e) {
        System.out.print("Error occurred while INSERT Operation: " + e);
        throw e;
    }
	}
	
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void updatePravac(ActionEvent event) throws SQLException, ClassNotFoundException {
		 String updateStmt =
                 "   UPDATE "+company.Main.Main.dbName+".putninalog " +
                 "   SET pravac='"+pravacLabel.getText()+"', km='"+tfKM.getText()+"'"+
                 "   WHERE pravac = '" + oldString+"'" ;
		//Execute UPDATE operation
	       try {
	    	   company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt, "putninalog");
	    	   populateTable();
	       } catch (SQLException e) {
	           System.out.print("Error occurred while UPDATE Operation: " + e);
	           throw e;
	       }
	}
	
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void deletePravac(ActionEvent event) throws SQLException, ClassNotFoundException {
	       //Declare a DELETE statement
	       String deleteStmt =
	                       "   DELETE FROM "+company.Main.Main.dbName+".putninalog" +
	                       "    WHERE pravac = '" + pravacLabel.getText()+"'";
	                       
	       //Execute DELETE operation
	       try {
	    	   company.Main.DBConnection.DBConnection.dbExecuteUpdate(deleteStmt, "putninalog");
	    	   populateTable();
	       } catch (SQLException e) {
	           System.out.print("Error occurred while DELETE Operation: " + e);
	           throw e;
	       }
	}	
		
	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancel(ActionEvent event) {	
		for (Node node : vbText.getChildren()){
		((TextField)node).setText("");
		}
		tfKM.setText("");
		pravacLabel.setText("");
	}
	
	// Event Listener on Button[#delTF].onAction
	@FXML
	public void close(ActionEvent event) throws SQLException, ClassNotFoundException {
		company.Main.DBConnection.DBConnection.dbDisconnect();
		company.Main.Main.closePopUpWindow();
	}
	
	public void addListenerToAddButton () {	
		for (Node node : vbAdd.getChildren()){
			((Button)node).setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	               addTextField();
	            }
	        });
			}
	}
	
	public void addListenerToTF(TextField tf) {
		tf.textProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.println("textfield changed from " + oldValue + " to " + newValue);
		    try {
				changeLabelAndTable ();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}		
		});
	}
	
	private void populateTable() throws ClassNotFoundException, SQLException {	 
		 ResultSet	 rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select pravac, km from "+company.Main.Main.dbName+".putninalog","putninalog");	
		 ObservableList <Finansije> users= FXCollections.observableArrayList();
		
		 while (rs.next()){	 
			 Finansije dir = new Finansije();
			 dir.setKomitenti(rs.getString("pravac"));
			 dir.setKomitentZR(rs.getString("km"));
			 users.add(dir);
		 }
		table.setItems(users);
	}
	
	public void changeLabelAndTable () throws ClassNotFoundException{
		  ObservableList <Finansije> users= FXCollections.observableArrayList();
		    String pravac="";
		    for (Node node : vbText.getChildren()){
		    	if(!((TextField)node).getText().equals("")||!((TextField)node).getText().isEmpty()){
		    		String tf="";
		    		if (((TextField)node).getText().toUpperCase().equals("BL") ) {
		    			tf =((TextField)node).getText().toUpperCase();
		    		} else {
		    		 tf = ((TextField)node).getText();
		    		 if (tf.length()>0){
		    			 tf=tf.substring(0, 1).toUpperCase()+tf.substring(1).toLowerCase();
		    		 }
		    		}
				pravac=pravac.concat(tf+"-");
		    	}
		    	}
		    String start="";
		    if (tfStart.getText().toUpperCase().equals("BL")){
		    	start=tfStart.getText().toUpperCase();
		    } else {
		    	if (tfStart.getText().length()>0){
		    	start=tfStart.getText().substring(0, 1).toUpperCase() + tfStart.getText().substring(1).toLowerCase();
		    	}
		    }
		    String labelText=pravac+start;
			pravacLabel.setText(labelText);
		
			/* *********** */	
			String [] splitSt = labelText.split("-");
		
			try {
				ResultSet rs=null;
				if (splitSt.length>2){
					String st = "";
					for (int q=1; q<splitSt.length-1; q++){
						st=st+splitSt[q]+"%' or pravac like '%";
					}
					st=st.substring(0, st.length()-20);
					rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select pravac, km from "+company.Main.Main.dbName+".putninalog where pravac like '"+splitSt[0]+"%' and ( pravac like '%"+st+"%');","putninalog");
						
				} else{
					rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select pravac, km from "+company.Main.Main.dbName+".putninalog where pravac like '"+splitSt[0]+"%';","putninalog");
				}
			while (rs.next()){
					 Finansije dir = new Finansije();
					 dir.setKomitenti(rs.getString("pravac"));
					 dir.setKomitentZR(rs.getString("km"));
					 users.add(dir);
				 }
				table.setItems(users);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
	
	public void editData(String oldString) throws ClassNotFoundException{
		String [] splitOld = oldString.split("-");
		tfStart.setText(splitOld[0]);
		tfMjesto1.setText(splitOld[1]);
		int x=0;
		for (@SuppressWarnings("unused") Node node : vbAdd.getChildren()){	
			 x++ ;
			}
		System.out.println("imamo ukupno tf: " +x);
		for (int a=x-1; a>0; a--){
			delFields(a);
		}
		
		for (int a=2; a<splitOld.length-1; a++){
			addTextField();
		}
		int q=2;
		for(Node node: vbText.getChildren()){
			if(vbText.getChildren().indexOf((TextField)node)>1){
				((TextField)node).setText(splitOld[q]);
				q++;
			}
		}
	}
    
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		tfMjesto1.setId("tfMjesto1");
		addListenerToTF(tfStart);
		addListenerToTF(tfMjesto1);
		pravacLabel.setStyle("-fx-font-size: 14px;");
		pravacLabel.setTextFill(Color.RED);
		
		try {
			populateTable();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println (e.getMessage());
		}
		
		pravacColumn.setCellValueFactory(cellData -> cellData.getValue().KomitentiProperty());
		kmColumn.setCellValueFactory(cellData -> cellData.getValue().KomitentZRProperty());
		
		table.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			int row = table.getSelectionModel().getSelectedIndex();			
			oldString=pravacColumn.getCellData(row).toString();
			tfKM.setText(kmColumn.getCellData(row).toString());
			System.out.println("Selektovan podatak u tabeli je: "+oldString);
			try {
				editData(oldString);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			}
		});
	}
	
}
