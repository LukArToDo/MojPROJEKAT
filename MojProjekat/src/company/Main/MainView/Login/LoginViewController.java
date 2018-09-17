package company.Main.MainView.Login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;

public class LoginViewController implements Initializable {
	@FXML
	private TextField userName;
	@FXML
	private PasswordField userPass;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnClear;
	@FXML
	private Label labelUser;
	@FXML
	private Label labelPass;
	
	
	
	
	// Event Listener on Button[#btnLogin].onAction
	@FXML
	public void handleKeyEvent(KeyEvent ke) throws ClassNotFoundException, IOException, InterruptedException, SQLException{
		if (ke.getCode() == KeyCode.ENTER) {
			searchPassword(null);
		}
	}
	
	@FXML
	public void searchPassword(ActionEvent event) throws ClassNotFoundException, IOException, InterruptedException, SQLException {
		/* check if password and username textfields are null */ 	
		if (userPass.getText()!=null && userName.getText()!=null ){
		 	/* password and username textfields are not null, check if they are filled */
			if (!userPass.getText().trim().equals("") && !userName.getText().trim().equals("")){
		 		/* password and username textfields are filled
		 		 * check if username textfield contains only numbers  */
		 		if (!checkString(userName.getText().trim())){
		 			this.labelUser.setVisible(true);
		 			userName.setText("");
		 			userName.requestFocus();
		 		/* username textfield contains not only numbers
		 		 * set name of main database name with value from username textfield,
		 		 * then check if exist database */
		 		}else{
		 			System.out.println("trazimo databasu "+userName.getText().trim());
		 			company.Main.Main.setNameOfDB(userName.getText().trim());
		 			/* if database exist, check if password (from table 'korisnici') is correct 
		 			 * in case that table 'korisnici' not existing, table will be created and correct value of password is 'admin'
		 			 * or password what we typed when starting this program*/
		 			if(company.Main.DBConnection.DBConnection.DBExists()){ 				 
		 				String pass = userPass.getText().trim();	
		 				//Declare a SELECT statement
		 				String selectStmt = "SELECT * FROM "+company.Main.Main.dbName +".korisnici WHERE password='"+ pass+"'";
		 				ResultSet rsPass=null;
		 				//Execute SELECT statement
		 				try {
		 					// checking if data base exists
		 // ????? we need this line?			//	company.Main.DBConnection.DBConnection.loginFirstConnection();
		 					//Get ResultSet from dbExecuteQuery method, and checking if table korisnici exist
		 					rsPass = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt, "korisnici");
		 				} catch (SQLException e) {
		 					e.getMessage();
		 				}
		 				//Send ResultSet to the getKorisniciFromResultSet method and get korisnici object
	          
		 				if (rsPass.next()==false  & !userPass.getText().equals(company.Main.Main.firstPass) && !userPass.getText().equals("admin") ) {
		 					this.labelPass.setVisible(true);
		 					System.out.println("pasword je pogresan");
		 					userPass.clear();
		 					userPass.requestFocus();
		 				/* if database (username) exist and password match go to next window */
		 				} else {  
		 					company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
		 					company.Main.Main.changeLeftMain("MainView/LeftMain.fxml");
		 					company.Main.Support.MySQLDump.MySQLDump.findDateOfFirstMonday();
		 				}  
		 	}else{
		 			this.labelUser.setVisible(true);
					System.out.println("UserName je pogresan");
					userName.clear(); 
					userName.requestFocus();
		 	}
		 	}
		 	}else{
	 			
				System.out.println("UserName ili UserPass je null");
				if (userName.getText().trim().equals("")) {
					userName.requestFocus();
				}else{
					userPass.requestFocus();
				}
		 	}
		 	}else{
	 			
				System.out.println("UserName ili UserPass nije unesen");
				if (userName.getText()==null) {
					userName.requestFocus();
				}else{
					userPass.requestFocus();
				}
		 	}	
	    }
	
	// Event Listener on Button[#btnClear].onAction
	@FXML
	public void clearField(ActionEvent event) {
		userName.clear();
		userPass.clear();
		this.labelPass.setVisible(false);
		this.labelUser.setVisible(false);
	}
	
	public static Boolean checkString(String name){
	    	if( name.length() > 1 && name.matches("[0-9]+")){
	    		System.out.println("username contains only number ");
	    	return false;
	    	}
	    	else{
	    		return true;
	    	}
	    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		userName.setText(company.Main.Main.dbName);
		 Platform.runLater(() -> {
			 userName.requestFocus();
		    });
		
		 userName.textProperty().addListener(new ChangeListener<String>()
			{
			    public void changed(ObservableValue<? extends String> arg0, String oldPropertyValue, String newPropertyValue)
			    {
			        if (newPropertyValue.length()>0)
			        {labelUser.setVisible(false);
			        }
			    }
			});
		 
		 userPass.textProperty().addListener(new ChangeListener<String>()
			{
			    public void changed(ObservableValue<? extends String> arg0, String oldPropertyValue, String newPropertyValue)
			    {
			        if (newPropertyValue.length()>0)
			        {labelPass.setVisible(false);
			        }
			    }
			});
		 
		 userName.setOnMousePressed(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
				labelUser.setVisible(false);
				}
				});
		
		userPass.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
			labelPass.setVisible(false);
			}
			});
	}
}
