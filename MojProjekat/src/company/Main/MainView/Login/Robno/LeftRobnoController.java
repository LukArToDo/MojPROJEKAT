package company.Main.MainView.Login.Robno;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class LeftRobnoController implements Initializable {
	@FXML
	private Button btnFaktura;
	@FXML
	private Button btnKalkulacija;
	@FXML
	private Button btnMedjuskladisnica;
	@FXML
	private Button btnNivelacija;
	@FXML
	private Button btnlager;
	@FXML
	private Button btnPopisi;
	@FXML
	private Button btnToGlavniMeni;
	@FXML
	private Button exit;

	// Event Listener on Button[#btnFaktura].onAction
	@FXML
	public void goToFakturaMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnKalkulacija].onAction
	@FXML
	public void goToKalkulacijaMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnMedjuskladisnica].onAction
	@FXML
	public void goToMedjuskladisnicaMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnNivelacija].onAction
	@FXML
	public void goToNivelacijaMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnlager].onAction
	@FXML
	public void goToLagerMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnPopisi].onAction
	@FXML
	public void goToPopisiMeni(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnToGlavniMeni].onAction
	@FXML
	public void goToGlavniMeni(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
		company.Main.Main.changeLeftMain("MainView/LeftMain.fxml");
	}
	// Event Listener on Button[#exit].onAction
	@FXML
	public void close(ActionEvent event) throws SQLException {
		company.Main.DBConnection.DBConnection.dbDisconnect();
		System.exit(0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}