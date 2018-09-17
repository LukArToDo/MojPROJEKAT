package company.Main.MainView.Login.Ostalo.Izvodi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class CenterIzvodController implements Initializable{
	@FXML
	private Button uveziIzvod;

	// Event Listener on Button[#uveziIzvod].onAction
	@FXML
	public void goToGetIzvod(ActionEvent event) throws IOException {
		company.Main.Main.closePopUpIzvod();
		company.Main.Main.getIzvod("MainView/Login/Ostalo/Izvodi/InputIzvod.fxml");
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
