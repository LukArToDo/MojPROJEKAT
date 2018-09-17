package company.Main.MainView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class LeftMainController implements Initializable{

	@FXML
	private static Button sifarnik;
	@FXML
	private Button robno;
	@FXML
	private Button finansije;
	@FXML
	private Button analize;
	@FXML
	private Button ostalo;
	@FXML
	private Button exit;

	
	// Event Listener on Button[#sifarnik].onAction
	@FXML
	public void goToSifarnik(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
		company.Main.Main.changeLeftMain("MainView/Login/Sifarnik/LeftSifarnik.fxml");
	}
	// Event Listener on Button[#robno].onAction
	@FXML
	public void goToRobno(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
		company.Main.Main.changeLeftMain("MainView/Login/Robno/LeftRobno.fxml");
	}
	// Event Listener on Button[#ostalo].onAction
	@FXML
	public void goToOstalo(ActionEvent event) throws SQLException, IOException {
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
		company.Main.Main.changeLeftMain("MainView/Login/Ostalo/LeftOstalo.fxml");
	}
	// Event Listener on Button[#finansije].onAction
	@FXML
	public void goToFinansije(ActionEvent event) throws SQLException {
			
	}
	// Event Listener on Button[#analize].onAction
	@FXML
	public void goToAnalize(ActionEvent event) throws SQLException {
				
	}
	// Event Listener on Button[#exit].onAction
	@FXML
	public void close(ActionEvent event) throws SQLException {
		company.Main.DBConnection.DBConnection.dbDisconnect();
		System.exit(0);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
