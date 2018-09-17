package company.Main.MainView.Login.Ostalo.Izvodi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import company.Main.Support.Izvodi.StringToSplit;
import javafx.event.ActionEvent;

public class InputIzvodController implements Initializable {
	@FXML
	private Button OkIzvod;
	@FXML
	private Button Cancel;
	

	// Event Listener on Button[#OkIzvod].onAction
	@FXML
	public void populateDataFromIzvod(ActionEvent event) throws IOException {
		company.Main.Main.closePopUpIzvod();
		company.Main.Main.changeCenterMain("MainView/Login/Ostalo/Izvodi/TableIzvodKM.fxml");	
	}
	
	// Event Listener on Button[#Cancel].onAction
	@FXML
	public void closeInputIzvod(ActionEvent event) {
		StringToSplit.clearAllArrayLists();	
		company.Main.Main.closePopUpIzvod();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		OkIzvod.setVisible(false);
		Cancel.setVisible(false);
	}
}
