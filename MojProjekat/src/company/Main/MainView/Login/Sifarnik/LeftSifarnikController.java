package company.Main.MainView.Login.Sifarnik;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class LeftSifarnikController implements Initializable{
	@FXML
	private Button btnKorisnici;
	@FXML
	private Button btnKomitenti;
	@FXML
	private Button btnArtikli;
	@FXML
	private Button btnKontniPlan;
	@FXML
	private Button btnCompany;
	@FXML
	private Button btnToGlavniMeni;
	@FXML
	private Button exit;

	// Event Listener on Button[#btnKorisnici].onAction
	@FXML
	public void goToKorisnici(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/Login/Sifarnik/Korisnici/SifarnikKorisnici.fxml");
		
	}
	// Event Listener on Button[#btnKomitenti].onAction
	@FXML
	public void goToKomitenti(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		company.Main.MainView.Login.Sifarnik.Komitenti.KomitentiController.Komitenti();
	}
	// Event Listener on Button[#btnArtikli].onAction
	@FXML
	public void goToArtikli(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnKontniPlan].onAction
	@FXML
	public void goToKontniPlan(ActionEvent event) throws IOException {
		
	}
	// Event Listener on Button[#btnOstalo].onAction
	@FXML
	public void goToCompanyData(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/Login/Sifarnik/CompanyData/CompanyData.fxml");
		
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
