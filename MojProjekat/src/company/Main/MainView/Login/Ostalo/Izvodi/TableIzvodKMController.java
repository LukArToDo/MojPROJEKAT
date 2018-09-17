package company.Main.MainView.Login.Ostalo.Izvodi;


import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import company.Main.Support.Finansije.Finansije;
import company.Main.Support.Finansije.FinasnijeDAO;
import company.Main.Support.Izvodi.StringToSplit;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;


public class TableIzvodKMController  implements Initializable {
	@FXML
	private AnchorPane izvodKM ;
	@FXML
	private TableView <Finansije>tableIzvodKM;
	@FXML
	private TableColumn <Finansije,String>colBrPromjena;
	@FXML
	private TableColumn <Finansije,String>colPromjena;
	@FXML
	private TableColumn <Finansije,String> colZrac;
	@FXML
	private TableColumn<Finansije,String> colKomitent;
	@FXML
	private TableColumn<Finansije,String> colDuguje;
	@FXML
	private TableColumn<Finansije,String> colPotrazuje;
	@FXML
	private TableColumn<Finansije,String> colKonto;
	@FXML
	private TableColumn<Finansije,String> colKontoSifra;
	@FXML
	private Label lb1;
	@FXML
	private Label lb2;
	@FXML
	private Label lb3;
	@FXML
	private Label lb4;
	@FXML
	private TextField vrstaNalogaText;
	@FXML
	private TextField ukPromPotrText;
	@FXML
	private TextField ukPromDugText;
	@FXML
	private TextField nLimitText;
	@FXML
	private TextField rIznosText;
	@FXML
	private TextField raspText;
	@FXML
	private TextField dosPotText;
	@FXML
	private TextField nStanjeText;
	@FXML
	private TextField pStanjeText;
	@FXML
	private TextField brIzvodText;
	@FXML
	private TextField datIzvodText;
	@FXML
	private TextField bankaText;
	@FXML
	private TextField bankaKontoText;
	@FXML
	private TextField bankaKontoSifraText;
	@FXML
	private MenuItem uveziIzvodMenuIt;
	@FXML
	private MenuItem unesiRucnoMenuIt;
	@FXML
	private Menu editIzvodMenu;
	@FXML
	private MenuItem delRowMenuIt;
	@FXML
	private MenuItem delAllMenuIt;
	@FXML
	private Menu printIzvodMenu;
	@FXML
	private Menu knjiziIzvodMenu;
	@FXML
	private Menu exitIzvodMenu;
	
	
	public String OrginalUkupDuguje;
	public String OrginalUkupPotrazuje;
	public double sumDug;
	public double sumPot;
	public ObservableList<Finansije> korData =FinasnijeDAO.searchDataPromjene(StringToSplit.mergeArrays);
	public String isFormatString;
	public int nRows;
	public int nr=1;
	public static int x=0;
	public static String newKomitent;
	public static String newKonto;
	public static String newSifra;
	public static int newRow;
	
	public ObservableList<Finansije> getFreshData(){
	korData.clear();
	korData =FinasnijeDAO.searchDataPromjene(StringToSplit.mergeArrays);
		return korData;
				
	}
	
	public static void getNewKotnoFromTable(String Komitent, String Konto,String Sifra){
		System.out.println("New populate PODACI u STRINGTOSPLIT su:"+ newKomitent+", "+ newKonto+" i "+ newSifra);
		/*listKonto=listKonto.set(piROW, newKonto);
		listKomitent=listKomitent.set(piROW, newKomitent);
		listKontoSifra=listKontoSifra.set(piROW, newSifra);*/
		
	}
	
	// Event Listener on Menu[#printMenu].onAction
		@FXML
	public void print(ActionEvent event) throws IOException {
			String broj=brIzvodText.getText();
			String datum=datIzvodText.getText();
			List<String> list = new ArrayList<>();
			list.clear();
			for (Finansije item : tableIzvodKM.getItems()) {
			    list.add(colPromjena.getCellObservableValue(item).getValue());
			    list.add(colKonto.getCellObservableValue(item).getValue());
			    list.add(colKontoSifra.getCellObservableValue(item).getValue());
			    list.add(colDuguje.getCellObservableValue(item).getValue());
			    list.add(colPotrazuje.getCellObservableValue(item).getValue());
			}
			list.add("Ziro-racun 555007-01032074-81");
			list.add("241010");
			list.add("000");
			list.add(ukPromPotrText.getText());
			list.add(ukPromDugText.getText());
			
			int zadInd=list.size();
			company.Main.Support.Print.PrintMultiNode.printData(list, broj, datum, 0,zadInd);		
		}
		
	// Event Listener on MenuItem[#uveziIzvodMenuIt].onAction
	@FXML
	public void unesiRucno(ActionEvent event) {	
		startNewIzvod();
		addNewRow();	
		activiateSetFields(true);
		Platform.runLater(() -> { 
			vrstaNalogaText.selectHome();
			vrstaNalogaText.requestFocus();	
		});
	}	
	
	public void startNewIzvod(){
		korData.clear();
		StringToSplit.clearAllArrayLists();
		labelVisibile(false);
		clearOtherFields();	
		activiateSetFields(false);
		
	}
	// Event Listener on MenuItem[#uveziIzvodMenuIt].onAction
	@FXML
	public void izmjeniIzvod(ActionEvent event) {
		// TODO Autogenerated
	}	
	// Event Listener on MenuItem[#uveziIzvodMenuIt].onAction
	@FXML
	public void delRowOfIzvod(ActionEvent event) {
		// TODO Autogenerated
		removeSelectedRow();
		sumDugAndPot();
	}
	
	public void sumDugAndPot() {
		int numRows= colBrPromjena.getTableView().getItems().size();
		ArrayList<Double> potrList = new ArrayList<Double>();
		ArrayList<Double> dugList = new ArrayList<Double>();
		sumDug=0;
		sumPot=0;
		for (int i=0; i<numRows; i++){
		String potrazuje=null;
		String duguje=null;
		double numPotr=0;
		double numDug=0;
		duguje=colDuguje.getCellData(i).toString();
		potrazuje=colPotrazuje.getCellData(i).toString();
		
		duguje=duguje.replaceAll(",", "");
		potrazuje=potrazuje.replaceAll(",", "");
		
		numPotr=Double.parseDouble(potrazuje);
		numDug=Double.parseDouble(duguje);
		
		potrList.add(numPotr);
		dugList.add(numDug);
		}
		
		for(int i=0; i<potrList.size(); i++){
		    sumPot =sumPot+potrList.get(i);
		    sumDug =sumDug+dugList.get(i);	   
	}
		
		new DecimalFormatSymbols();
		// Ispis brojeva kao formatirani text
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
		
		double pretStanje=0.00;
		try{
		String pStanje=pStanjeText.getText();
		pStanje=pStanje.replaceAll(",", "");
		pretStanje=Double.parseDouble(pStanje);
		} catch(NumberFormatException e) {
			pStanjeText.requestFocus();
		}
		
		double novoStanje=pretStanje+sumPot-sumDug;
		if (novoStanje>-0.01 && novoStanje<0) {novoStanje=0.00;}
		nStanjeText.setText(df.format(novoStanje));
				
		ukPromPotrText.setText(df.format(sumPot));
		ukPromDugText.setText(df.format(sumDug));
		if(!df.format(sumDug).equals(OrginalUkupDuguje)||!df.format(sumPot).equals(OrginalUkupPotrazuje)) {
			String style="-fx-text-fill: red;";
			ukPromPotrText.setStyle(style);
			ukPromDugText.setStyle(style);
		} else {ukPromPotrText.setStyle(null);
		ukPromDugText.setStyle(null);}
	}
	
	@FXML
	public void delAllOfIzvod(ActionEvent event) {
		StringToSplit.clearAllArrayLists();
		labelVisibile(false);
		activiateSetFields(true);
		ukPromPotrText.setText("0.00");
		ukPromDugText.setText("0.00");
		nStanjeText.setText(pStanjeText.getText());
		addNewRow();
	}
	// Event Listener on MenuItem[#uveziIzvodMenuIt].onAction
	@FXML
	public void uveziIzvod(ActionEvent event) throws IOException, ClassNotFoundException, ParseException, MessagingException {
		korData.clear();
		clearOtherFields();
		StringToSplit.clearAllArrayLists();
		labelVisibile(false);
		activiateSetFields(true);
		company.Main.Main.showPopUp("Support/Izvodi/ChooseIzvod.fxml", "INBOX");
		//company.Main.Support.Izvodi.EmailAttachmentReceiver.downloadEmailAttachments("novabanka-eizvodi@novabanka.com","maxpapirdoo@gmail.com","dragoslava", LocalDate.now(),2);
		//homebank@nlbbl.com
		//info.rbbh@rbbh.ba
		//company.Main.Support.Izvodi.ChoosePdfFile.chooseFileFromDirectory(company.Main.Main.izvodStage);
   		}
	
	// Event Listener on TextField[#pStanjeText].onAction
	@FXML
	public void setPretStanjeTF(KeyEvent ke) throws IOException {
		
			 if (ke.getCode()==KeyCode.TAB) {
			pStanjeText.setText(formatterDecimalString(pStanjeText.getText()));		
			Platform.runLater(
		        	   new Runnable() {
	                        @Override
	                        public void run() {
	                        	if (vrstaNalogaText.getText().isEmpty()) vrstaNalogaText.requestFocus();
	                        	else if (pStanjeText.getText().isEmpty()) {pStanjeText.requestFocus();}
	                			else{
	                			nStanjeText.setText(pStanjeText.getText());                	                   	
			tableIzvodKM.requestFocus();
			tableIzvodKM.getSelectionModel().select(0);
			tableIzvodKM.getFocusModel().focus(0, colPromjena);
	                        }  	 
	                        }
		            });
			 
			 }
		}

	public String formatterDecimalString(String gItem){
			new DecimalFormatSymbols();
			DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
			try{
			gItem=gItem.replaceAll(",", "");
			double numToString=Double.parseDouble(gItem);
			gItem=df.format(numToString);
			return gItem;
			}catch (Exception e){
				gItem="";	
				company.Main.Main.showAlertError("GRESKA!!!", "Polje DUGUJE se ne moze formatirati u broj. Unesite tacan iznos!!!");
				return gItem;
			}
		}
	
	// Event Listener on Menu[#knjiziIzvodMenu].onAction
	@FXML
	public void knjiziIzvod(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Menu[#exitIzvodMenu].onAction
	@FXML
	public void exitFromScene(ActionEvent event) throws IOException {
		
		StringToSplit.clearAllArrayLists();
		company.Main.Main.closePopUpIzvod();
		labelVisibile(false);
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
	}
	
	private void addNewRow (){
		korData.clear();
		 Finansije kor = new Finansije();
         kor.setRedBroj("1");
         kor.setPromjene(" ");
         kor.setKomitentZR(" ");
         kor.setKomitenti(" ");
         kor.setDuguje("0");
         kor.setPotrazuje("0");
         kor.setKonto(" ");
         kor.setKontoSifra(" ");;
         korData.add(kor);
         tableIzvodKM.setEditable(true);
         tableIzvodKM.setItems(korData);                  
        
		}
	

	public void populateTable() {	
		tableIzvodKM.setItems(getFreshData());          
	}

	public void populateOtherFields(){
	
	ukPromDugText.setText(formatterDecimalString(StringToSplit.prometDuguje));
	OrginalUkupDuguje=ukPromDugText.getText();
	ukPromPotrText.setText(formatterDecimalString(StringToSplit.prometPotrazuje));
	OrginalUkupPotrazuje=ukPromPotrText.getText();
	pStanjeText.setText(formatterDecimalString(StringToSplit.prethStanje));
	nLimitText.setText(StringToSplit.neiskLimit);
	rIznosText.setText(StringToSplit.rezervIznos);
	raspText.setText(StringToSplit.raspolozivo);
	dosPotText.setText(StringToSplit.dospPotrazivanja);
	nStanjeText.setText(formatterDecimalString(StringToSplit.novoStanje));
	brIzvodText.setText(StringToSplit.brIzvoda);
	datIzvodText.setText(StringToSplit.datumIzvoda);
	bankaKontoText.setText(StringToSplit.kontoIzvodaBanke);  //  ispraviti poslije
	bankaText.setText(StringToSplit.bankaNaziv); // ispraviti poslije
	vrstaNalogaText.setText(StringToSplit.vrstaNaloga);   // ispraviti poslije
}

	public void labelVisibile(boolean b){
	this.lb1.setVisible(b);
	this.lb2.setVisible(b);
	this.lb3.setVisible(b);
	this.lb4.setVisible(b);
	nLimitText.setVisible(b);
	rIznosText.setVisible(b);
	raspText.setVisible(b);
	dosPotText.setVisible(b);
}

	public void activiateSetFields(boolean b){
	ukPromDugText.setEditable(b);
	ukPromPotrText.setEditable(b);
	pStanjeText.setEditable(b);	
	nStanjeText.setEditable(b);
	brIzvodText.setEditable(b);
	datIzvodText.setEditable(b);
	bankaKontoText.setEditable(b);
	bankaText.setEditable(b);
	bankaKontoSifraText.setEditable(b);
	vrstaNalogaText.setEditable(b);
	}
	
	public void clearOtherFields(){
	ukPromDugText.clear();	
	ukPromPotrText.clear();
	pStanjeText.clear();
	nLimitText.clear();
	rIznosText.clear();
	raspText.clear();
	dosPotText.clear();
	nStanjeText.clear();
	brIzvodText.clear();
	datIzvodText.clear();
	bankaKontoText.clear();
	bankaKontoSifraText.clear();
	bankaText.clear();
	vrstaNalogaText.clear();
	}
	
	public static void addMask(TextField tf, String tfText, String mask, TextField nextTF) {
	tf.selectHome();

	System.out.println("X je :"+x);
	addTextLimiter(tf, mask.length()-1);

    tf.textProperty().addListener(new ChangeListener<String>() {
   
		@Override
		public void changed(final ObservableValue<? extends String> ov,  String oldValue,  String newValue) {
			// TODO Auto-generated method stub
			System.out.println("oldValue: "+oldValue+" newValue: "+newValue);
			
			if (x==0){
				tf.setText(comparator(tf.getText(), mask, tf.getCaretPosition()+1));
				
			}
			if (x==1){
				tf.setText(newValue);
			}
			
			tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
			        @Override
			        public void handle(KeyEvent e) {
			        	
				        int a=0;          
			            int caretPosition = tf.getCaretPosition();
			            System.out.println(e.getCode() + "pozicija" +caretPosition);
			            if (caretPosition < mask.length()-1 && (caretPosition != 1 || caretPosition!=4) && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.LEFT) {
			                a=caretPosition + 1; x=0;
			           }
			            if (caretPosition== 1 || caretPosition== 4) {a=caretPosition+2; 
			            x=0;
			            }
			            if (caretPosition >0 &&  (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT)) {
			            	a=caretPosition -1; x=1;
			            }
			            
			            if (e.getCode()==KeyCode.DELETE){
			            a=caretPosition;	
			            }
			            if (e.getCode()==KeyCode.TAB) {
			            	a=mask.length();
			            }
			            final int b=a;
			            
			            Platform.runLater(		// iz nepoznatog razloga postavljanje caretPosition na odredjenu poziciju ne radi bez ove metode> Platform.runLater - Runnable
			                    new Runnable() {
			                        @Override
			                        public void run() {
			                        	 if (b<mask.length()-1){
			                        	tf.requestFocus();
			                            tf.deselect(); 
			                            tf.positionCaret(b);}
			                            else {nextTF.requestFocus();
			                            }
			                        }          
			        });   
			  }
			});		  
		}
    });
}   

	public static String comparator(String txt,String mask, int c){
	System.out.println("Pozicija u comparatoru: "+c);
	HashMap<Integer,Character>hmap=new HashMap<>();
	for (int i=0; i<mask.length(); i++) {
		char m=mask.charAt(i);
		if (m!=' ') {
			hmap.put(i,m);
		}
	}
	for (Iterator<Entry<Integer, Character>> iterator = hmap.entrySet().iterator(); iterator.hasNext();) {
		Entry<Integer, Character> mentry = iterator.next();
		System.out.println("key is: "+mentry.getKey()+" and value is: "+mentry.getValue());
		String cs=c+"";
		System.out.println("pozicija : "+cs+" jednaka ili ne key :"+mentry.getKey().toString());
	    if (cs.equals(mentry.getKey().toString())) {	    	
	    if(txt.length()>c){	  
	    StringBuilder newTxt = new StringBuilder(txt);
	    newTxt.setCharAt(c, mentry.getValue().toString().charAt(0));
	    txt=newTxt.toString();		
	    }
	    else {txt=txt.concat(mentry.getValue().toString());}
	    }
	}	
	System.out.println("Novi text je: "+txt);
	return txt;
}

	public static void addTextLimiter(TextField tf, int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(final ObservableValue<? extends String> ov,  String oldValue,  String newValue) {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        }
    });
}

	@FXML
	public void nalogVrstaKeyPress(KeyEvent t) {
	bankaText.clear();
	if (t.getCode()==KeyCode.TAB || t.getCode()==KeyCode.ENTER) {
		switch (vrstaNalogaText.getText()){
		case "10" : 
			bankaText.setText("NOVA BANKA ad");
			break;
		case "11" :
			bankaText.setText("NLB Banka ad");
			break;
		case "12" :
			bankaText.setText("Raiffeisen bank");
			break;

		}
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		datIzvodText.setText(reportDate);
		
	}
}

	@FXML
	public void handleKeyPress(KeyEvent t) {
	if (t.getCode() == KeyCode.ESCAPE ) {
		try {
			exitFromScene(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	if (t.getCode() == KeyCode.P) {
		try {
			print(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}

	public void removeSelectedRow(){
	int selectedIndex = tableIzvodKM.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        tableIzvodKM.getItems().remove(selectedIndex);
        korData.remove(selectedIndex);
      /*  populateTable();
        tableIzvodKM.getSelectionModel().selectLast();*/
    }
}
	
	private void setNumberField(TextField tf, String mask, int maxLength, TextField nextTf) {
	// TODO Auto-generated method stub
	//addTextLimiter(tf, maxLength);
	tf.textProperty().addListener((observable,oldValue,newValue)->{
		if (newValue.length()==maxLength ){
			
			if( newValue.matches(mask)){
			System.out.println("Konto je 6 cifara");
			tf.deselect();
			nextTf.requestFocus();
			}
			else{tf.clear();
			tf.requestFocus();}
		}
	});
}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	addMask(datIzvodText, datIzvodText.getText(),"  .  .     ",bankaKontoText);
	setNumberField(bankaKontoText,"\\d{6}", 6, bankaKontoSifraText);
	
	
	if (StringToSplit.bankaNaziv.startsWith("NOVA BANKA ad")){
		labelVisibile(true);
	} 
	else{labelVisibile(false);}
	
	activiateSetFields(false);
	
	izvodKM.setOnKeyPressed(new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent t) {
			
			if (t.getCode() == KeyCode.P) {
				try {
					print(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		}
		});

	if (korData.isEmpty()){
		Label label=new Label();
		label.setText("DOBRO DOSLI! MOLIMO VAS DA POCNEMO...");
		tableIzvodKM.setPlaceholder(label);
	}
	else{
	populateOtherFields();
	populateTable();
	}
	tableIzvodKM.setEditable(true);
	colBrPromjena.setCellValueFactory(cellData -> cellData.getValue().RedBrojProperty());
	colPromjena.setCellValueFactory(cellData -> cellData.getValue().PromjenaProperty());
	colZrac.setCellValueFactory(cellData -> cellData.getValue().KomitentZRProperty());
	colKomitent.setCellValueFactory(cellData -> cellData.getValue().KomitentiProperty());
	colDuguje.setCellValueFactory(cellData -> cellData.getValue().DugujeProperty());
	colPotrazuje.setCellValueFactory(cellData -> cellData.getValue().PotrazujeProperty());
	colKonto.setCellValueFactory(cellData -> cellData.getValue().KontoProperty());
	colKontoSifra.setCellValueFactory(cellData -> cellData.getValue().KontoSifraProperty());

	colDuguje.setStyle("-fx-alignment: CENTER-RIGHT;");
	colPotrazuje.setStyle("-fx-alignment: CENTER-RIGHT;");
	
	nRows=colBrPromjena.getTableView().getItems().size();
	System.out.println("nRows: "+nRows);
	// make cell editiable

	tableIzvodKM.setEditable(true);
	
	tableIzvodKM.setRowFactory(new Callback<TableView<Finansije>, TableRow<Finansije>>() {

        @Override
        public TableRow<Finansije> call(TableView<Finansije> param) {
            TableRow<Finansije> row = new TableRow<Finansije>();
    
            tableIzvodKM.setOnKeyPressed(new EventHandler<KeyEvent>() {
    			@Override
    			public void handle(KeyEvent t) {
    				if (t.getCode()==KeyCode.F1) {
                            //create a new Item and intialize it ...
                            Finansije kor = new Finansije();
                            
                            nRows=colBrPromjena.getTableView().getItems().size();
                            nRows=nRows+1;
                            
                            kor.setRedBroj(""+nRows++);
                            kor.setPromjene("");
                            kor.setKomitentZR("");
                            kor.setKomitenti("");
                            kor.setDuguje("0");
                            kor.setPotrazuje("0");
                            kor.setKonto("");
                            kor.setKontoSifra("");
                            korData.add(kor);
                            tableIzvodKM.setItems(korData);                  
                            tableIzvodKM.setEditable(true);
                            tableIzvodKM.getSelectionModel().selectLast();
                        } 
                    
    			if (t.getCode() == KeyCode.DELETE || t.getCode()==KeyCode.F2) {
				removeSelectedRow();
				}
    			}
                });
            
            return row;
        }	
    });
	
	
/*	Callback<TableColumn<Finansije,String>, TableCell<Finansije,String>> cellFactory = new Callback<TableColumn<Finansije,String>, TableCell<Finansije,String>>() {
		public TableCell call(TableColumn p) {
			return new EditingCell();		
		}
	};
*/
	Callback<TableColumn<Finansije,String>, TableCell<Finansije,String>> cellFactory = p -> new EditingCell();
		
	colBrPromjena.setCellFactory(cellFactory);
	colBrPromjena.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setRedBroj(t.getNewValue());			        
		});
	
	
	colPromjena.setCellFactory(cellFactory);
	colPromjena.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setPromjene(t.getNewValue());
		});
	

	colZrac.setCellFactory(cellFactory);
	colZrac.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setKomitentZR(t.getNewValue());	        
		});
	

	colKomitent.setCellFactory(cellFactory);
	colKomitent.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setKomitenti(t.getNewValue());		        
		});
	

	colDuguje.setCellFactory(cellFactory);
	colDuguje.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setDuguje(t.getNewValue());
	        
		});
	

	colPotrazuje.setCellFactory(cellFactory);
	colPotrazuje.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setPotrazuje(t.getNewValue());        
		});
	
	
	colKonto.setCellFactory(cellFactory);
	colKonto.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setKonto(t.getNewValue());
		});
	
	
	colKontoSifra.setCellFactory(TextFieldTableCell.<Finansije>forTableColumn());
	colKontoSifra.setCellFactory(cellFactory);
	colKontoSifra.setOnEditCommit(
		    (CellEditEvent<Finansije, String> t) -> {
		        ((Finansije) t.getTableView().getItems().get(
		            t.getTablePosition().getRow())
		            ).setKontoSifra(t.getNewValue());   
		});
	
	
/*	korData.addListener((ListChangeListener) change -> {
	    while (change.next()) {
	        if (change.wasReplaced()) {
	            Finansije changedItem = korData.get(change.getFrom());
	            System.out.println("ListChangeListener item: " + changedItem);
	        }
	    }
	});
	*/
}


	
class EditingCell extends TableCell<Finansije, String> {
	
	private int currentIndex;
	private int currentPrevIndex;
	private TextField textField;
	private String originalValue = null;
	private List<TableColumn<Finansije, ?>> columns;
	private int nextIndex;
	private int prevIndex;
	private int colIndex;
	
	public EditingCell() {
	}
	
	
	@Override
	public void startEdit() {
		originalValue=null;
		if (!isEmpty()|| isEmpty()) {
			super.startEdit();
			originalValue=getItem();
			if (textField == null) {
				createTextField();
			}
			
			setGraphic(textField);
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			// textField.selectAll();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					textField.requestFocus();
					textField.selectAll();				
				}
			});
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		setText((String) getItem());
		setContentDisplay(ContentDisplay.TEXT_ONLY);
		//setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		Tooltip t = new Tooltip();
		t.setText(item);
		this.setTooltip((empty || item ==null) ? null : t);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {			
						textField.setText(getString());		
				}			
				setGraphic(textField);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				
				
			} else {
				setText(getString());		
				setContentDisplay(ContentDisplay.TEXT_ONLY);
			}
		}
	}

	
	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()
				* 2);
		
	    ChangeListener<? super Boolean> changeListener = (observable, oldSelection, newSelection) ->
	    {
	        if (! newSelection) {
	            commitEdit(textField.getText());
	        }
	    };
	
	    textField.focusedProperty().addListener(changeListener);

		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void handle(KeyEvent t)  {
				if (t.getCode()== KeyCode.F5){
					//  open dialogStage "Tabelu komitenata i konta"
				
					TablePosition selCell = tableIzvodKM.getSelectionModel().getSelectedCells().get(0); // ova i sledeca linija
					int rowIndex=selCell.getRow();		// sluzi da se dobije row u kojem je celija trenutno focused/aktivna
					int columnIndex=selCell.getColumn();
					
					String komitent= tableIzvodKM.getSelectionModel().getSelectedItem().getKomitenti();
					if (komitent.contains("DOO")) komitent=komitent.replaceAll("DOO", "");
					if (komitent.contains("D.O.O.")) komitent=komitent.replaceAll("D.O.O.", "");
					String ziroracun=tableIzvodKM.getSelectionModel().getSelectedItem().getKomitentZR();
					String duguje=tableIzvodKM.getSelectionModel().getSelectedItem().getDuguje();
					String promjena=tableIzvodKM.getSelectionModel().getSelectedItem().getPromjena();
					System.out.println("Row and column after F5 click is: "+rowIndex +" and "+columnIndex);
					System.out.println("KOmitent: "+komitent + ", duguje: "+duguje);
					
				// primjer kako izmjeniti polja nakon [to se dobiju rezultati iz sifarnika:
				/*	tableIzvodKM.getSelectionModel().getSelectedItem().setKomitenti("PROBA");					
					textField.setText(tableIzvodKM.getSelectionModel().getSelectedItem().getKomitenti());
				
					tableIzvodKM.getSelectionModel().getSelectedItem().setPotrazuje("1,000,000.00");
					textField.setText(tableIzvodKM.getSelectionModel().getSelectedItem().getPotrazuje()); */
				
					ArrayList<String>newData=new ArrayList<>();
					newData.clear();
								try {
									newData.addAll(StringToSplit.blankKontoAndKontoSifra('t',rowIndex, rowIndex+1,komitent,promjena,ziroracun,duguje));
									System.out.println("NewDATA "+newData);	
								} catch (ClassNotFoundException | IOException e) {
									e.printStackTrace();
								}
								
								if (!newData.isEmpty() && newData!=null){
									try{
								tableIzvodKM.getSelectionModel().getSelectedItem().setKomitenti(newData.get(1));
						//		textField.setText(newData.get(1));
								StringToSplit.listKomitent.set(rowIndex, newData.get(1));
						//		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
								
								if (duguje.equals("0.00")){
									tableIzvodKM.getSelectionModel().getSelectedItem().setKonto(newData.get(3));
									StringToSplit.listKonto.set(rowIndex, newData.get(3));
								} else{
									tableIzvodKM.getSelectionModel().getSelectedItem().setKonto(newData.get(4));
									StringToSplit.listKonto.set(rowIndex, newData.get(4));
									}
							//    textField.setText(tableIzvodKM.getSelectionModel().getSelectedItem().getKonto());
						
							//	setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
							
								tableIzvodKM.getSelectionModel().getSelectedItem().setKontoSifra(newData.get(2));
								StringToSplit.listKontoSifra.set(rowIndex, newData.get(2));
								
							//	textField.setText(tableIzvodKM.getSelectionModel().getSelectedItem().getKontoSifra());
						
							//	setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
									}catch(IndexOutOfBoundsException iobe){
										System.out.println(iobe.getMessage());
									}
									}
								StringToSplit.setMergeArrays();
								korData.clear();
								tableIzvodKM.setItems(korData);
								tableIzvodKM.setItems(getFreshData());
								
								
								
				}
				
				if (t.getCode() == KeyCode.ENTER ) {
				commitEdit(textField.getText());
				sumDugAndPot();
				
				} else if (t.getCode() == KeyCode.ESCAPE) {
					textField.setText(originalValue);
					textField.focusedProperty().removeListener(changeListener);
					cancelEdit();
				} else if (t.getCode() == KeyCode.TAB || t.getCode()==KeyCode.RIGHT ) {		
					
					TablePosition selCell = tableIzvodKM.getSelectionModel().getSelectedCells().get(0);
					int rowIndex=selCell.getRow();
					colIndex=selCell.getColumn();
					nRows=colBrPromjena.getTableView().getItems().size();
					System.out.println("Number of rows in TW: "+nRows);
					System.out.println("Number of selected row is: "+rowIndex);
					TableColumn nextColumn = getNextColumn(!t.isShiftDown());
					commitEdit(textField.getText()); 
					sumDugAndPot();
					
				if (nextColumn != null) 
				{ 
					if (nextColumn==columns.get(0)) 
					{ 
						getTableView().edit(getTableRow().getIndex()+1,
								nextColumn);					
					} else
					{	
					getTableView().edit(getTableRow().getIndex(),
							nextColumn);
					}
				}	
				commitEdit(textField.getText()); 
				sumDugAndPot();
				}
				
				
				else if (t.getCode() == KeyCode.LEFT )
				{
					commitEdit(textField.getText());
					sumDugAndPot();
					TableColumn prevColumn = getPrevColumn(!t.isShiftDown());
					if (prevColumn != null) 
					{
						if (currentPrevIndex<1) 
						{
							getTableView().edit(getTableRow().getIndex()-1,
									prevColumn);					
						} else
						{	
						getTableView().edit(getTableRow().getIndex(),
								prevColumn);
						}
					}			 
				}
				
				
				else if (t.getCode() == KeyCode.DOWN )
				{			
					commitEdit(textField.getText());
					sumDugAndPot();
					nRows=colBrPromjena.getTableView().getItems().size();  // dobijanje trenutnog broja redova u tabeli
					TablePosition selCell = tableIzvodKM.getSelectionModel().getSelectedCells().get(0); // ova i sledeca linija
					int rowIndex=selCell.getRow();		// sluzi da se dobije row u kojem je celija trenutno focused/aktivna 
					
				TableColumn downColumn = getDownColumn(!t.isShiftDown());
				if (downColumn != null) 
				{ if (rowIndex==nRows-1){
					getTableView().edit( getTableRow().getIndex(),
							downColumn);
					cancelEdit();
				}
				else {
					getTableView().edit( getTableRow().getIndex()+1,
						downColumn);
					tableIzvodKM.getSelectionModel().selectNext();
				}
				}  			
				}
						
				else if (t.getCode() == KeyCode.UP )
				{
					commitEdit(textField.getText());
					sumDugAndPot();
				TableColumn upColumn = getUpColumn(!t.isShiftDown());
				if (upColumn != null) 
				{
					getTableView().edit( getTableRow().getIndex()-1,
						upColumn);	
					tableIzvodKM.getSelectionModel().selectPrevious();
				}		
				}		
				}
		});	
	}
	
	private String getString() {
		String gItem;
		new DecimalFormatSymbols();
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
		
		if(getItem() == null) gItem= "" ;
		else {gItem=getItem().toString();
		try{
		if (currentIndex <6 && currentIndex>3){		
				gItem=gItem.replaceAll(",", "");
				double numToString=Double.parseDouble(gItem);
				gItem=df.format(numToString);
				sumDugAndPot();
			}		
		if (colIndex <6 && colIndex>3 ){
			gItem=gItem.replaceAll(",", "");
			double numToString=Double.parseDouble(gItem);
			gItem=df.format(numToString);
			sumDugAndPot();
		}
		
		} catch (NumberFormatException e) {
			gItem="0.00";
			company.Main.Main.showAlertError("GRESKA!!!", "Polje DUGUJE se ne moze formatirati u broj. Unesite tacan iznos!!!");
		
		}
		}
		return gItem;
	
	}
	
	private void addRow(){
		nRows=colBrPromjena.getTableView().getItems().size();
		int selInd=tableIzvodKM.getSelectionModel().getSelectedIndex();
		System.out.println("Ukupan broj rows :"+nRows);
		System.out.println("Trenutni number of rows is: "+selInd);
		if (nRows==selInd+1){
			
		tableIzvodKM.getSelectionModel().selectLast();
		
		 Finansije kor = new Finansije();
         
         nRows=nRows+1;
         
         kor.setRedBroj(""+nRows++);
         kor.setPromjene(" ");
         kor.setKomitentZR(" ");
         kor.setKomitenti(" ");
         kor.setDuguje("0.00");
         kor.setPotrazuje("0.00");
         kor.setKonto(" ");
         kor.setKontoSifra(" ");;
         korData.add(kor);
         tableIzvodKM.setItems(korData);//  populateTable();                   
         tableIzvodKM.setEditable(true);
         tableIzvodKM.getSelectionModel().selectLast();
		}
	}
	private TableColumn<Finansije, ?> getDownColumn(boolean down) {
		columns = new ArrayList<>();
		
		for (TableColumn<Finansije, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		int currDownIndex = columns.indexOf(getTableColumn());
		// There is no other column that supports editing.
		if (columns.size() < 1) {
			return null;
		} 
	//	 addRow();  /* dodavanje novog reda sa key UP. Zbog bag-a u Java-i, tj.presoprog racunanja "size of column" */
					/* u odnosu na "sroling", bolje je iskljuciti ovu opciju jer poremeti rad koji je uslovljen scrolovanjem*/
		
		return columns.get(currDownIndex);
	}
	
	private TableColumn<Finansije, ?> getUpColumn(boolean up) {
		columns = new ArrayList<>();
		for (TableColumn<Finansije, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		int currUpIndex = columns.indexOf(getTableColumn());
		// There is no other column that supports editing.
		if (columns.size() < 1) {
			return null;
		} 
		return columns.get(currUpIndex);
	}
	
	private TableColumn<Finansije, ?> getPrevColumn(boolean previus) {
		columns = new ArrayList<>();
		for (TableColumn<Finansije, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		// There is no other column that supports editing.
		if (columns.size() < 1) {
			return null;
		}
		currentPrevIndex = columns.indexOf(getTableColumn());
		prevIndex = currentPrevIndex;
		if (previus) {
			prevIndex--;
			if (prevIndex < 0 ) {
				prevIndex = columns.size()-1;
				tableIzvodKM.getSelectionModel().selectPrevious();
			}
		} else {
			prevIndex++;
			if (prevIndex > columns.size()) {
				prevIndex = columns.size()-1;
			}
		}
		System.out.println("PrevIndex je :"+prevIndex);
		return columns.get(prevIndex);
		
	}
	private TableColumn<Finansije, ?> getNextColumn(boolean forward) {
		@SuppressWarnings("rawtypes")
		TablePosition selCell = tableIzvodKM.getSelectionModel().getSelectedCells().get(0); // ova i sledeca linija
		int rowIndex=selCell.getRow();
		columns = new ArrayList<>();
		for (TableColumn<Finansije, ?> column : getTableView().getColumns()) {
			columns.addAll(getLeaves(column));
		}
		// There is no other column that supports editing.
		if (columns.size() < 2) {
			return null;
		}
		currentIndex = columns.indexOf(getTableColumn());
		System.out.println("CurrentIndex je :"+currentIndex+ "  Broj redova: "+nRows+ " trenutni red:"+rowIndex);
		nextIndex = currentIndex;
		if (forward) {
			commitEdit(textField.getText());
			nextIndex++;
			if (nextIndex > columns.size()-1 ) {
				nextIndex = 0;
				if(nRows-1==rowIndex){
				addRow();
				tableIzvodKM.getSelectionModel().selectNext();}
			}
		} else {
			nextIndex--;
			if (nextIndex < 0) {
				nextIndex = columns.size() - 1;
			}
		}
		System.out.println("NextIndex je :"+nextIndex);
		return columns.get(nextIndex);
	}

	
	private List<TableColumn<Finansije, ?>> getLeaves(
			TableColumn<Finansije, ?> root) {
		List<TableColumn<Finansije, ?>> columns = new ArrayList<>();
		if (root.getColumns().isEmpty()) {
			// We only want the leaves that are editable.
			if (root.isEditable()) {
				columns.add(root);
			}
			return columns;
		} else {
			for (TableColumn<Finansije, ?> column : root.getColumns()) {
				columns.addAll(getLeaves(column));
			}
			return columns;
		}
	}
}
}