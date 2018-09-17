package company.Main.MainView.Login.Sifarnik.Korisnici;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import company.Main.Support.Korisnici.Korisnici;
import company.Main.Support.Korisnici.KorisniciDAO;

import java.io.IOException;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.TextArea;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class SifarnikKorisniciController implements Initializable{
	
	@FXML
	private TextArea resultArea;
	@FXML
	private TextArea korisnikNapomenaArea;
	@FXML
	private TextField korIdText;
	@FXML
	private TextField korisnikImeText;
	@FXML
	private TextField korisnikPrezimeText;
	@FXML
	private TextField korisnikJmbgText;
	@FXML
	private TextField korisnikRodjText;
	@FXML
	private TextField korisnikTekRacunText;
	@FXML
	private TextField korisnikAdresaText;
	@FXML
	private TextField korisnikGradText;
	@FXML
	private TextField korisnikOpstinaText;
	@FXML
	private TextField korisnikSpremaText;
	@FXML
	private TextField korisnikZanimanjeText;
	@FXML
	private TextField korisnikRadMjestoText;
	@FXML
	private TextField korisnikZaposlenOdText;
	@FXML
	private TextField tipV;
	@FXML
	private TextField regV;
	@FXML
	private TextField vlV;
	@FXML
	private TextField korisnikRadStazGodText;
	@FXML
	private TextField korisnikRadStazMjesText;
	@FXML
	private TextField korisnikRadStazDanText;
	@FXML
	private PasswordField korisnikLozinka;
	
	@FXML
	private TableView <Korisnici> korisnikTable;
	@FXML
	private TableColumn <Korisnici, Integer> korisnikIdColumn;
	@FXML
	private TableColumn <Korisnici, String>korisnikImeColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikPrezimeColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikLozinkaColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikJmbgColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikTekRacunColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikRodjColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikAdresaColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikGradColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikOpstinaColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikSpremaColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikZanimanjeColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikRadMjestoColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikZaposlenOdColumn;
	@FXML
	private TableColumn <Korisnici, String> tipVColumn;
	@FXML
	private TableColumn <Korisnici, String> regVColumn;
	@FXML
	private TableColumn <Korisnici, String> vlVColumn;
	@FXML
	private TableColumn <Korisnici, Integer> korisnikRadStazGodColumn;
	@FXML
	private TableColumn <Korisnici, Integer> korisnikRadStazMjesColumn;
	@FXML
	private TableColumn <Korisnici, Integer> korisnikRadStazDanColumn;
	@FXML
	private TableColumn <Korisnici, String> korisnikNapomenaColumn;
		
	@FXML
	private Button brisiKorisnikaBtn;
	@FXML
	private Button izmjeniKorisnikaBtn;
	@FXML
	private Button addKorisnikaBtn;
	@FXML
	private Button traziKorisnikaBtn;
	@FXML
	private Button searchKorisnikAllBtn;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnStampa;
	@FXML
	private Button clearDataBtn;
	@FXML
	private Button clearTableDataBtn;
	
	
	private int countPass;
	private int countJmbg;
	private int countPassId;

	
	@FXML
	private void formattedTextFieldBirthday(){
	//FormattedTextField<String> korisnikRodjText= new FormattedTextField<>();

	}
	
	// Event Listener on Button[#searchKorisnikaBtn].onAction
	@FXML
	private void searchKorisnik(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
			try {
	            //Get Employee information
				ObservableList<Korisnici> korData = KorisniciDAO.searchKorisnik(korisnikImeText.getText(), korisnikPrezimeText.getText());
	            //Populate Employee on TableView and Display on TextArea
		        //Populate Employees on TableView
		        populateKorisnici(korData);
		        resultArea.setText("Svi korisnici su prikazani u tabeli.");
		            
		        } catch (SQLException e){
		            System.out.println("Došlo je do greške prilikom dobivanja informacija korisnika iz baze podataka.\n" + e);
		            throw e;
		        }
	}
		
	//Populate Employee for TableView and Display Employee on TextArea
	@SuppressWarnings("unused")
	private void populateAndShowKorisnici(Korisnici kor) throws ClassNotFoundException {
			if (kor != null) {
	            populateKorisnik(kor);
	            setKorisnikInfoToTextArea(kor);
	        } else {
	            resultArea.setText("Traženi korisnik ne postoji u bazi.\n");
	        }
	}
	    
	  //Set Employee information to Text Area
	private void setKorisnikInfoToTextArea(Korisnici kor) {
			 resultArea.setText("Ime: " + kor.getImeKorisnici() + "\n" +
		                "Prezime: " + kor.getPrezimeKorisnici());
	}
	  
     //Populate Employee
	private void populateKorisnik(Korisnici kor) throws ClassNotFoundException {
			try{
	    	//Declare and ObservableList for table view
	        ObservableList<Korisnici> korData = FXCollections.observableArrayList();
	        //Add employee to the ObservableList
	        korData.add(kor);
	        //Set items to the employeeTable 
	        korisnikTable.setItems(korData);
			//resultArea.setText("Trazeni korisnik je prikazan u tabeli");
			} catch (Exception e){
				resultArea.setText(e.getMessage());
			} 
	}
	    
	// Event Listener on Button[#brisiKorisnikaBtn].onAction
	@FXML
	public void deleteKorisnik(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
			 try {
		            KorisniciDAO.deleteKorisnikSaId(korisnikImeText.getText(), korisnikPrezimeText.getText());
		            resultArea.setText("Korisnik : " + korisnikImeText.getText()+" " + korisnikPrezimeText.getText()+" je izbrisan!\n");
		            searchKorisnikAll(actionEvent);
		            clearData(actionEvent);
		        } catch (SQLException e) {
		            resultArea.setText("Došlo je do problema prilikom brisanja korisnika. " + e);
		            throw e;
		        }
	}
				
	// Event Listener on Button[#izmjeniKorisnikaBtn].onAction
	@FXML
	public void updateKorisnik(ActionEvent actionEvent) throws ClassNotFoundException, ParseException {
			try {
				searchPassAndId();
				
				if (countPassId==1) {
					korisnikLozinka.requestFocus();
	         		resultArea.setText("Korisnik sa istom lozinkom vec postoji u bazi. \n Unesite drugu lozinku!");
				}
				else{
					
					String JMBGtext = korisnikJmbgText.getText();
					Pattern pJ = Pattern.compile("^[0-9]+$");
					Matcher mJ = pJ.matcher(JMBGtext);
					boolean bJ = mJ.find();
					if (bJ == false || JMBGtext.length()!=13)  {
						korisnikJmbgText.requestFocus();
						resultArea.setText("JMBG mora sadrzati 13 cifara!");
						
			  }
				else{									
					String opstina = korisnikOpstinaText.getText();
					Pattern p = Pattern.compile("^[0-9]+$");
					Matcher m = p.matcher(opstina);
					boolean b = m.find();
					System.out.println(b);
					if (b == false || opstina.length()!=3)  {
						korisnikOpstinaText.requestFocus();
						resultArea.setText("Sifra opštine mora biti troznamenkasti broj!");
					}
					else{
					KorisniciDAO.updateSaId(korIdText.getText(),korisnikImeText.getText(), korisnikPrezimeText.getText(),korisnikLozinka.getText(),
	            		korisnikJmbgText.getText(),korisnikTekRacunText.getText(),korisnikRodjText.getText(),
	            		korisnikAdresaText.getText(),korisnikGradText.getText(),korisnikOpstinaText.getText(),
	            		korisnikSpremaText.getText(),korisnikZanimanjeText.getText(),korisnikRadMjestoText.getText(),
	            		tipV.getText(), regV.getText(), vlV.getText(),
	            		korisnikZaposlenOdText.getText(),korisnikRadStazGodText.getText(),korisnikRadStazMjesText.getText(),
	            		korisnikRadStazDanText.getText(),korisnikNapomenaArea.getText());
	            
	            resultArea.setText("Podaci su izmjenjeni za korisnika: " + korisnikImeText.getText() + "\n");
	            searchKorisnikAll(actionEvent); 
						}
				}
				}	
				
	        } catch (SQLException e) {
	            resultArea.setText("Došlo je do problema prilikom izmjene podataka: " + e);
	        }
	}
				
	// Event Listener on Button[#addKorisnikaBtn].onAction
	@FXML
	private void insertKorisnik(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
			 try{
					searchPass();
					searchJmbg();
					
					if (countPass==1) {
						korisnikLozinka.requestFocus();
		         		resultArea.setText("Korisnik sa istom lozinkom vec postoji u bazi. \n Unesite drugu lozinku!");
					}
					else if (countJmbg==1){
						korisnikImeText.requestFocus();
						resultArea.setText("Korisnik vec postoji u bazi podataka.");
					}
					else{
						String JMBGtext = korisnikJmbgText.getText();
						Pattern pJ = Pattern.compile("^[0-9]+$");
						Matcher mJ = pJ.matcher(JMBGtext);
						boolean bJ = mJ.find();
						if (bJ == false || JMBGtext.length()!=13)  {
							korisnikJmbgText.requestFocus();
							resultArea.setText("JMBG mora sadrzati 13 cifara!");
							resultArea.setStyle("errorTextField");
				  }
					else{
						String opstina = korisnikOpstinaText.getText();
						Pattern p = Pattern.compile("^[0-9]+$");
						Matcher m = p.matcher(opstina);
						boolean b = m.find();
						if (b == false || opstina.length()!=3)  {
							korisnikOpstinaText.requestFocus();
							resultArea.setText("Sifra opštine mora biti troznamenkasti broj!");	
				  }
				  else {
				  String i="";
		            if (i.equals(korisnikImeText.getText())) korisnikImeText.requestFocus();
		            else if(i.equals(korisnikPrezimeText.getText())) korisnikPrezimeText.requestFocus();
		            else if(i.equals(korisnikJmbgText.getText())) korisnikJmbgText.requestFocus();
		            else if (i.equals(korisnikTekRacunText.getText())) korisnikTekRacunText.requestFocus();
		            else if (i.equals(korisnikGradText.getText())) korisnikGradText.requestFocus();
		            else if (i.equals(korisnikOpstinaText.getText())) korisnikOpstinaText.requestFocus();
		            else if (i.equals(korisnikSpremaText.getText())) korisnikSpremaText.requestFocus();
		            else if (i.equals(korisnikZanimanjeText.getText())) korisnikZanimanjeText.requestFocus();
		            else if (i.equals(korisnikRadMjestoText.getText())) korisnikRadMjestoText.requestFocus();
		            else if (i.equals(tipV.getText())) tipV.requestFocus();
		            else if (i.equals(regV.getText())) regV.requestFocus();
		            else if (i.equals(vlV.getText())) vlV.requestFocus();
		            else if (i.equals(korisnikZaposlenOdText.getText())) korisnikZaposlenOdText.requestFocus();
		            else {
				  KorisniciDAO.insertKorisnik(korisnikImeText.getText(), korisnikPrezimeText.getText(),korisnikLozinka.getText(),
		            		korisnikJmbgText.getText(),korisnikTekRacunText.getText(),korisnikRodjText.getText(),
		            		korisnikAdresaText.getText(),korisnikGradText.getText(),korisnikOpstinaText.getText(),
		            		korisnikSpremaText.getText(),korisnikZanimanjeText.getText(),korisnikRadMjestoText.getText(),
		            		tipV.getText(), regV.getText(), vlV.getText(),
		            		korisnikZaposlenOdText.getText(),korisnikRadStazGodText.getText(),korisnikRadStazMjesText.getText(),
		            		korisnikRadStazDanText.getText(),korisnikNapomenaArea.getText());
		           
		            clearTableData(actionEvent);
		            resultArea.setText("Novi korisnik je dodat! \n");
		            searchKorisnikAll(actionEvent);} } } }
		        } catch (SQLException e) {
		            resultArea.setText("Došlo je do problema prilikom dodavanja korisnika. " + e);
		            throw e;
		        }	
	}
		
	// Event Listener on Button[#searchKorisnikAllBtn].onAction
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

	// Event Listener on Button[#btnStampa].onAction
	@FXML
	public void stampaKorisnici(ActionEvent actionEvent) throws IOException, SQLException {
				company.Main.Main.showPopUpWindow("MainView/Login/Sifarnik/Korisnici/PopUpKorisnici.fxml","Style/printPreview.css");	
		}
		
	// Event Listener on Button[#btnCancel].onAction
	@FXML
	public void closeView(ActionEvent actionEvent) throws IOException, SQLException {
			company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
			company.Main.DBConnection.DBConnection.dbDisconnect();
	}
			
	// Event Listener on Button[#clearDataBtn].onAction
	@FXML
	public void clearData(ActionEvent actionEvent) {   // dopunjeno za PUTNI NALOG
			korIdText.clear();
			korisnikImeText.clear();
			korisnikPrezimeText.clear();
			korisnikLozinka.clear();
			korisnikJmbgText.clear();
			korisnikTekRacunText.clear();
			korisnikRodjText.clear();
			korisnikAdresaText.clear();
			korisnikGradText.clear();
			korisnikOpstinaText.clear();
			korisnikSpremaText.clear();
			korisnikZanimanjeText.clear();
			korisnikRadMjestoText.clear();
			korisnikZaposlenOdText.clear();
			korisnikRadStazGodText.clear();
			korisnikRadStazMjesText.clear();
			korisnikRadStazDanText.clear();
			korisnikNapomenaArea.clear();
			tipV.clear();
			regV.clear();
			vlV.clear();
			
			resultArea.setText("Polja su izbrisana / prazna.");
	}
		
	@FXML
	public void clearTableData(ActionEvent event) {
			korisnikTable.setItems(null);
			resultArea.setText("Tabela je izbrisana / prazna.");
	}
	
	private void searchPassAndId() throws ClassNotFoundException{
			String selectPassId="SELECT * FROM "+ company.Main.Main.dbName+".korisnici where password='" + korisnikLozinka.getText()+"' and id_korisnik<>"+ korIdText.getText();
			
			try { 
					ResultSet rsPassId = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectPassId,"korisnici");
					 if (rsPassId.next()) {
						
						String korPassId=rsPassId.getString("password").toString();
						String fieldPass=korisnikLozinka.getText();
						 System.out.println(korPassId+" i "+fieldPass);
			         	if (korPassId.equals(fieldPass)){
			         		countPassId=1;
		         	}
			         	else {countPassId=0;}
					 }
					 else {countPassId=0;}
					 company.Main.DBConnection.DBConnection.dbDisconnect();
			  
					
			
		} catch (SQLException e){
	        System.out.println("Došlo je do greške prilikom dobivanja informacija korisnika iz baze podataka.\n" + e);
		} 
	}
				
	private void searchPass() throws ClassNotFoundException{
			 String selectPass="SELECT * FROM "+ company.Main.Main.dbName+".korisnici where password='" + korisnikLozinka.getText()+"'";
			
			  try { 
					ResultSet rsPass = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectPass,"korisnici");
					 if (rsPass.next()) {
						
						String korPass=rsPass.getString("password").toString();
						String fieldPass=korisnikLozinka.getText();
						 System.out.println(korPass+" i "+fieldPass);
			         	if (korPass.equals(fieldPass)){
			         		countPass=1;
		         	}
			         	else {countPass=0;}
			         	
					 } else {countPass=0;}
					
					 company.Main.DBConnection.DBConnection.dbDisconnect();
			  
					
			
		} catch (SQLException e){
	        System.out.println("Došlo je do greške prilikom dobivanja informacija korisnika iz baze podataka.\n" + e);
		}
	}
					
	private void searchJmbg() throws ClassNotFoundException{
		 String selectJmbg="SELECT * FROM "+ company.Main.Main.dbName+".korisnici where jmbg='" + korisnikJmbgText.getText()+"'";
		 
		  try { 
				ResultSet rsJmbg = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectJmbg,"korisnici");
				 if (rsJmbg.next()) {
					
					String korJmbg=rsJmbg.getString("jmbg").toString();
					String fieldJmbg=korisnikJmbgText.getText();
					
		         	if (korJmbg.equals(fieldJmbg)){
		         		countJmbg=1;
	         	}
		         	else {countJmbg=0;}	
				 } 	else {countJmbg=0;}
				
				 company.Main.DBConnection.DBConnection.dbDisconnect();
		
	} catch (SQLException e){
        System.out.println("Došlo je do greške prilikom dobivanja informacija korisnika iz baze podataka.\n" + e);
	} 
	}
		
	public static long daysBetween(Calendar startDate, Calendar endDate) {  
		  Calendar date = (Calendar) startDate.clone();  
		  long daysBetween = 0;  
		  while (date.before(endDate)) {  
		    date.add(Calendar.DAY_OF_MONTH, 1);  
		    daysBetween++;  
		  }  
		  return daysBetween;  
		}  
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {				
			searchKorisnikAll(null);
			} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			}
			
			korisnikIdColumn.setCellValueFactory(cellData -> cellData.getValue().IdKorisniciProperty().asObject());
			korisnikImeColumn.setCellValueFactory(cellData -> cellData.getValue().ImeKorisniciProperty());
			korisnikPrezimeColumn.setCellValueFactory(cellData -> cellData.getValue().PrezimeKorsiniciProperty());
			korisnikLozinkaColumn.setCellValueFactory(cellData -> cellData.getValue().PasswordProperty());
			korisnikJmbgColumn.setCellValueFactory(cellData -> cellData.getValue().JmbgProperty());
			korisnikTekRacunColumn.setCellValueFactory(cellData -> cellData.getValue().TekuciRacunProperty());
			korisnikRodjColumn.setCellValueFactory(cellData -> cellData.getValue().BirthdayProperty());
			korisnikAdresaColumn.setCellValueFactory(cellData -> cellData.getValue().AdresaProperty());
			korisnikGradColumn.setCellValueFactory(cellData -> cellData.getValue().GradProperty());
			korisnikOpstinaColumn.setCellValueFactory(cellData -> cellData.getValue().OpstinaProperty());	
			korisnikSpremaColumn.setCellValueFactory(cellData -> cellData.getValue().SpremaProperty());
			korisnikZanimanjeColumn.setCellValueFactory(cellData -> cellData.getValue().ZanimanjeProperty());
			korisnikRadMjestoColumn.setCellValueFactory(cellData -> cellData.getValue().RadnoMjestoProperty());
			korisnikZaposlenOdColumn.setCellValueFactory(cellData -> cellData.getValue().ZaposlenOdProperty());
			tipVColumn.setCellValueFactory(cellData -> cellData.getValue().TipVozilaProperty());
			regVColumn.setCellValueFactory(cellData -> cellData.getValue().RegVozilaProperty());
			vlVColumn.setCellValueFactory(cellData -> cellData.getValue().VlasnikVozilaProperty());
			korisnikRadStazGodColumn.setCellValueFactory(cellData -> cellData.getValue().PrijeStazGodProperty().asObject());
			korisnikRadStazMjesColumn.setCellValueFactory(cellData -> cellData.getValue().PrijeStazMjesProperty().asObject());
			korisnikRadStazDanColumn.setCellValueFactory(cellData -> cellData.getValue().PrijeStazDanProperty().asObject());
			korisnikNapomenaColumn.setCellValueFactory(cellData -> cellData.getValue().napomenaKorProperty());
		
			
			korisnikIdColumn.setVisible(false);
			korIdText.setVisible(false);
			
			korisnikTable.setOnMousePressed(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
				    
				int row = korisnikTable.getSelectionModel().getSelectedIndex();
				
				korIdText.setText(korisnikIdColumn.getCellData(row).toString());
				korisnikImeText.setText(korisnikImeColumn.getCellData(row).toString());
				korisnikPrezimeText.setText(korisnikPrezimeColumn.getCellData(row).toString());
				korisnikJmbgText.setText(korisnikJmbgColumn.getCellData(row).toString());
				korisnikLozinka.setText(korisnikLozinkaColumn.getCellData(row).toString());
				korisnikRodjText.setText(korisnikRodjColumn.getCellData(row).toString());
				korisnikTekRacunText.setText(korisnikTekRacunColumn.getCellData(row).toString());
				korisnikAdresaText.setText(korisnikAdresaColumn.getCellData(row).toString());
				korisnikGradText.setText(korisnikGradColumn.getCellData(row).toString());
				korisnikOpstinaText.setText(korisnikOpstinaColumn.getCellData(row).toString());
				korisnikSpremaText.setText(korisnikSpremaColumn.getCellData(row).toString());
				korisnikZanimanjeText.setText(korisnikZanimanjeColumn.getCellData(row).toString());
				korisnikRadMjestoText.setText(korisnikRadMjestoColumn.getCellData(row).toString());
				korisnikZaposlenOdText.setText(korisnikZaposlenOdColumn.getCellData(row).toString());
				tipV.setText(tipVColumn.getCellData(row).toString());
				regV.setText(regVColumn.getCellData(row).toString());
				vlV.setText(vlVColumn.getCellData(row).toString());
				korisnikRadStazGodText.setText(korisnikRadStazGodColumn.getCellData(row).toString());
				korisnikRadStazMjesText.setText(korisnikRadStazMjesColumn.getCellData(row).toString());
				korisnikRadStazDanText.setText(korisnikRadStazDanColumn.getCellData(row).toString());
			
				String napomena = korisnikTable.getSelectionModel().getSelectedItem().getNapomenaKor();
				if (napomena=="null") {napomena="";
				korisnikNapomenaArea.setText(napomena);}
				
				
				// izracunavanje ukupnog staza
				else{
					String string = korisnikZaposlenOdText.getText();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					LocalDate dateZOd = LocalDate.parse(string, formatter);
					System.out.println(dateZOd);
					LocalDate dateToday=LocalDate.now();
					
					int y1 = Integer.parseInt(korisnikRadStazGodText.getText());
					int m1 = Integer.parseInt(korisnikRadStazMjesText.getText());
					int d1 = Integer.parseInt(korisnikRadStazDanText.getText());
							
					Period p = Period.between(dateZOd, dateToday);
					
					int y2 = p.getYears();
					int m2= p.getMonths();
					int d2= p.getDays();
					
					int y = y1+y2;
					int m = m1+m2;
					int d = d1+d2;
					int dm, my;
					if (d>30) { d = d-30; dm=1;}
					else {dm=0;}
					m=m+dm;
					if (m>12) {m= m-12; my=1;}
					else{my=0;}
					y=y+my;
					
					korisnikNapomenaArea.setText("Godina: " + y + ", \nmjeseci: " + m +
					                   ", \ndana " + d);
				}
				resultArea.setText("Oznacili ste korisnika:\n Ime: "+ korisnikImeText.getText() + "\n Prezime: " + korisnikPrezimeText.getText());
				}
				});
	}
}
