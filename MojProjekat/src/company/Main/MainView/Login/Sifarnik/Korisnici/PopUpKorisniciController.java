package company.Main.MainView.Login.Sifarnik.Korisnici;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import company.Main.Main;
import company.Main.Support.Korisnici.Korisnici;
import company.Main.Support.Korisnici.KorisniciDAO;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.text.TextFlow;



public class PopUpKorisniciController  implements Initializable{
	@FXML
	private MenuItem menuPrint;
	@FXML
	private MenuItem menuExport;
	@FXML
	private MenuItem menuClose;
	@FXML
	private TableView <Korisnici> korisnikTable;
	@FXML
	private TableColumn <Korisnici, Integer> korisnikIdColumn;
	@FXML
	private TableColumn <Korisnici, String>korisnikImeColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikPrezimeColumn;
	
	@FXML 
	private TextFlow textFlow;
	@FXML 
	private TextArea textAreaPopUp;
	@FXML
	private Label jobStatus;
	
	
	@FXML
	private void searchKorisnikAll(ActionEvent actionEvent) throws ClassNotFoundException, SQLException{ 
		
		try {
            //Get all Employees information
            ObservableList<Korisnici> korData = KorisniciDAO.searchKorisnici();
            //Populate Employees on TableView
            populateKorisnici(korData);
           // resultArea.setText("Svi korisnici su prikazani u tabeli.");
            
        } catch (SQLException e){
            System.out.println("Došlo je do greške prilikom dobivanja informacija korisnika iz baze podataka.\n" + e);
            throw e;
        }
		
	}
		   
	//Populate Employees for TableView
	private void populateKorisnici(ObservableList<Korisnici> korData) {
		//Set items to the employeeTable
	    korisnikTable.setItems(korData);    
	}
			
	@FXML
	public void exportToPdf (ActionEvent event){
		company.Main.Support.Print.ExportToPDF.nodeToPdf(textFlow);
	}

	// Event Listener on MenuItem[#menuPrint].onAction
	@FXML
	public void printPopUpKorisnici(ActionEvent event) {
		company.Main.Support.Print.PrintNode.printNode(textFlow);
	}
	
	// Event Listener on MenuItem[#menuClose].onAction
	@FXML
	public void closePopUpKorisnici(ActionEvent event) throws IOException {
	
		Main.closePopUpWindow();
	}
	

	
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		korisnikTable.setEditable(false);
		korisnikTable.setFocusModel(null);  // pri stampanju se ne moze selektovati tabela (row)
		
		 final double SCALE_DELTA = 1.1;
		    textFlow.setOnScroll(new EventHandler<ScrollEvent>() {
		        public void handle(ScrollEvent event) {
		            event.consume();

		            if (event.getDeltaY() == 0) {
		                return;
		            }

		            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

		            textFlow.setScaleX(textFlow.getScaleX() * scaleFactor);
		            textFlow.setScaleY(textFlow.getScaleY() * scaleFactor);
		        }
		    });

		    textFlow.setOnMousePressed(new EventHandler<MouseEvent>() {
		        public void handle(MouseEvent event) {
		            if (event.getClickCount() == 2) {
		                textFlow.setScaleX(1.0);
		                textFlow.setScaleY(1.0);
		            }
		            else {   	
		            	textFlow.setScaleX(0.8);
		            	textFlow.setScaleY(0.8);
		            	
		            }
		        }
		    });
		    
		   
		    
		
			try {
				searchKorisnikAll(null);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    korisnikImeColumn.setCellValueFactory(cellData -> cellData.getValue().ImeKorisniciProperty());
			korisnikPrezimeColumn.setCellValueFactory(cellData -> cellData.getValue().PrezimeKorsiniciProperty());
	
			
	}
}
