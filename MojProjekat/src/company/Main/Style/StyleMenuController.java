package company.Main.Style;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.layout.VBox;

public class StyleMenuController implements Initializable {
	@FXML
	private VBox styleMenuVb;
	@FXML
	private CheckBox buissUp;
	@FXML
	private CheckBox blackYellow;
	@FXML
	private CheckBox blue;
	@FXML
	private CheckBox grey;

	// Event Listener on VBox[#styleMenuVb].onMouseClicked
	@FXML
	public void changeAndClose(MouseEvent event) throws IOException {
		String imageChange;
		if (buissUp.isSelected()) { imageChange="Images/backM.png";
		company.Main.Main.changeStyleMenu(imageChange,"Style/BlueStyle.css");
		}
		else if (blue.isSelected()) {imageChange="Images/backM1.jpg";
		company.Main.Main.changeStyleMenu(imageChange,"Style/BlueStyle.css");
		}
		else if (blackYellow.isSelected()) {imageChange="Images/blackImg.png";
	company.Main.Main.changeStyleMenu(imageChange,"Style/BlackStyle.css");
		}
		else if (grey.isSelected()) {imageChange="Images/greyM.jpg";
		company.Main.Main.changeStyleMenu(imageChange,"Style/ProbaStyle.css");
		}
		company.Main.Main.changeRightMain("MainView/TopIcon.fxml");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
