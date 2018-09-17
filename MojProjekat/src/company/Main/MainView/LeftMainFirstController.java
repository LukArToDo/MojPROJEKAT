package company.Main.MainView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class LeftMainFirstController implements Initializable {
	@FXML
	private VBox vbLogin;
	@FXML
	private Button admin;
	@FXML
	private Button exit;

	// Event Listener on Button[#admin].onAction
	@FXML
	public void goToLoginView(ActionEvent event) throws IOException {
	company.Main.Main.changeCenterMain("MainView/Login/LoginView.fxml");
	//	company.Main.Main.changeLeftMain(fxmlLeft);
		
	}
	// Event Listener on Button[#exit].onAction
	@FXML
	public void close(ActionEvent event) {
		System.exit(0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
