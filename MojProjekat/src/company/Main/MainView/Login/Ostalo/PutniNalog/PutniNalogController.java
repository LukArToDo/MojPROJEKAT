package company.Main.MainView.Login.Ostalo.PutniNalog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;


public class PutniNalogController implements Initializable {
	@FXML 
	public AnchorPane putniNalog;
	@FXML
	private MenuItem noviNalog;
	@FXML
	private MenuItem close;	
	@FXML
	private MenuItem stampajNalog;
	@FXML
	private MenuItem putniPravac;
	@FXML
	private TextField tfBrNaloga;
	@FXML
	private DatePicker dpDatumN;
	@FXML
	private ComboBox <String> cbKorisnik ;
	@FXML
	private TextField tfZvanje;
	@FXML
	private ComboBox<String> cbSvrha1;
	@FXML
	private ComboBox<String> cbSvrha2;
	@FXML
	private ComboBox<String> cbPravac;
	@FXML
	private TextField tfMjesta;	
	@FXML
	private TextField tfBrDana;
	@FXML
	private RadioButton rbDa;
	@FXML
	private ToggleGroup useVozilo;
	@FXML
	private RadioButton rbNe;
	@FXML
	private RadioButton rbPrivatno;
	@FXML
	private ToggleGroup propertyVozilo;
	@FXML
	private RadioButton rbSluzbeno;
	@FXML
	private TextField tfVoziloData;
	@FXML
	private TextField tfVoziloVlasnik;
	@FXML
	private TextField tfDateGo;
	@FXML
	private TextField tfHoursGo;
	@FXML
	private TextField tfMinutGo;
	@FXML
	private TextField tfDateReturn;
	@FXML
	private TextField tfHoursReturn;
	@FXML
	private TextField tfMinutReturn;
	@FXML
	private TextField tfBrDanaDD;
	@FXML
	private TextField tfIznosDD;
	@FXML
	private TextField tfBrojDanaID;
	@FXML
	private TextField tfIznosID;
	@FXML
	private TextField tfGorivo;
	@FXML
	private TextField tfKM;
	@FXML
	private TextField tfRacun1;
	@FXML
	private TextField tfIznosR1;
	@FXML
	private TextField tfRacun2;
	@FXML
	private TextField tfIznosR2;
	@FXML
	private TextField tfRacun3;
	@FXML
	private TextField tfIznosR3;
	@FXML
	private TextField tfUkupnoOstalo;
	@FXML
	private Label lbDD;
	@FXML
	private Label lbID;
	@FXML
	private Label lbDnevnice;
	@FXML
	private Label lbDnevnice1;
	@FXML
	private Label lbNaknada;
	@FXML
	private Label lbTrVozilo;
	@FXML
	private Label lbOstaliTR;
	@FXML
	private Label lbUkupniTROSKOVI;
	@FXML
	private Label lbDateObracuna;
	
	public double kurs=1.95583;
	public ObservableList <String> directions=FXCollections.observableArrayList();
	public String user;
	public String pattern = "dd.MM.yyyy";		     	    	
    public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
    public String regBroj;
    public String tipVozila;
    public String nV=null;
	
	// Event Listener on MenuItem[#noviNalog].onAction
	@FXML
	public void newNalog(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/Login/Ostalo/PutniNalog/PutniNalog.fxml");	
	}
	
	@FXML
	public void newPravac(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		company.Main.Main.showPopUp("MainView/Login/Ostalo/PutniNalog/TablePravac.fxml", "Putni pravac");
		try {
			setDirection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on MenuItem[#stampajNalog].onAction
	@FXML
	public void print(ActionEvent event) {
		String svrha=null;
		String[] printData = null;
	   if (cbSvrha2.getValue()==null) {svrha=cbSvrha1.getValue();
	   }else{svrha=cbSvrha1.getValue()+","+cbSvrha2.getValue();}
	
	   int q=0;
	    if(rbDa.isSelected()){
	    	printData= new String[]{cbKorisnik.getValue(), tfZvanje.getText(), tfMjesta.getText(), svrha, tipVozila, regBroj,tfVoziloVlasnik.getText(),
	    		tfBrDana.getText(),cbPravac.getValue(),tfDateGo.getText(),tfHoursGo.getText(),tfMinutGo.getText(),tfDateReturn.getText(),tfHoursReturn.getText(),
	    		tfMinutReturn.getText(), tfBrDanaDD.getText(), tfIznosDD.getText(),lbDD.getText(),tfBrojDanaID.getText(),tfIznosID.getText(),lbID.getText(),
	    		lbDnevnice.getText(),tfKM.getText(),tfGorivo.getText(),lbNaknada.getText(),tfUkupnoOstalo.getText(),lbUkupniTROSKOVI.getText(),lbDateObracuna.getText(), 
	    		tfBrNaloga.getText(),tfRacun1.getText(), tfIznosR1.getText(),tfRacun2.getText(), tfIznosR2.getText(),tfRacun3.getText(), tfIznosR3.getText()};
	    }else{
	    	if(tfVoziloData.getText()!=null && tfVoziloData.getText().length()>9){
	    		tipVozila=tfVoziloData.getText().substring(0, tfVoziloData.getText().length()-9);
	    		regBroj=tfVoziloData.getText().substring(tfVoziloData.getText().length()-9);
	    	}
	    	if (tfVoziloVlasnik.getText()==null) tfVoziloVlasnik.setText("-");
	    	if (tfKM.getText()==null)tfKM.setText("0");
	    	if (tfGorivo.getText()==null)tfGorivo.setText("0.00");
	    	
	    	printData= new String[]{cbKorisnik.getValue(), tfZvanje.getText(), tfMjesta.getText(), svrha, tipVozila, regBroj,tfVoziloVlasnik.getText(),
		    		tfBrDana.getText(),cbPravac.getValue(),tfDateGo.getText(),tfHoursGo.getText(),tfMinutGo.getText(),tfDateReturn.getText(),tfHoursReturn.getText(),
		    		tfMinutReturn.getText(), tfBrDanaDD.getText(), tfIznosDD.getText(),lbDD.getText(),tfBrojDanaID.getText(),tfIznosID.getText(),lbID.getText(),
		    		lbDnevnice.getText(),tfKM.getText(),tfGorivo.getText(),lbNaknada.getText(),tfUkupnoOstalo.getText(),lbUkupniTROSKOVI.getText(),lbDateObracuna.getText(), 
		    		tfBrNaloga.getText(),tfRacun1.getText(), tfIznosR1.getText(),tfRacun2.getText(), tfIznosR2.getText(),tfRacun3.getText(), tfIznosR3.getText()};
	    }
	    try{
	    for (int i=0; i<printData.length-7;i++){
	    	if (printData[i].isEmpty()||printData[i].equals(null)||printData[i].equals("")){
	    		q++;
	    		company.Main.Main.showAlertError("GRESKA!!!", "Popunite prazna polja");
	    		break;
	    	}
	    }
	    if (q==0){
		company.Main.Support.Print.PutniNalogToPDF.printPDF(printData);
	    }
	   }catch (RuntimeException re){
		   company.Main.Main.showAlertError("GRESKA!!!", "Popunite prazna polja");	
	   }    
	}
	
	// Event Listener on MenuItem.onAction
	@FXML
	public void exitFromScene(ActionEvent event) throws IOException {
		File dest = new File(System.getProperty("user.home") + "/Desktop/PutniNalog.pdf");
		System.out.println("Path od DEST: "+dest.getPath());
		dest.delete();
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
	}
	// Event Listener on TextField[#tfMjesta].onAction
	@FXML
	private void changeDnevnice(ActionEvent event) {
		obracunDnevnice();
	}
	
	@FXML
	private void voziloSaBez(ActionEvent event){
		if (rbNe.isSelected()){
			if (rbNe.isSelected()){
				rbPrivatno.setSelected(false);
				rbSluzbeno.setSelected(false);
				tfVoziloData.clear();
			//	tfVoziloData.setEditable(false);
				tfVoziloVlasnik.clear();
			//	tfVoziloVlasnik.setEditable(false);
				tfGorivo.clear();
			
				tfKM.clear();
			//	tfKM.setEditable(false);
				lbTrVozilo.setText("0.00");
				tipVozila=" -";
				regBroj=" -";
				tfVoziloData.setText("  -       -  ");
				tfVoziloVlasnik.setText("  -");
				tfKM.setText("0");
				tfGorivo.setText("0.00");
				tfGorivo.setEditable(false);
			}
		}
	}

	private void setUsers() throws ClassNotFoundException, SQLException {
		ResultSet rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select ime_korisnik, prezime_korisnik from "+ company.Main.Main.dbName+".korisnici","korisnici");	
		 ObservableList <String> users= FXCollections.observableArrayList();
		 while (rs.next()){
			 String user= rs.getString("ime_korisnik")+" "+rs.getString("prezime_korisnik");
			 users.add(user);	 
		 }
		 cbKorisnik.setItems(users.sorted());
		 company.Main.DBConnection.DBConnection.dbDisconnect();
	}
	
	private void setSvrha() {
		ObservableList <String> svrha= FXCollections.observableArrayList("posjeta poslovnim partnerima", "sastanak i izvjestaj","posjeta dobavljacima","komisijski poslovi (popis robe i sl)","");
		cbSvrha1.setItems(svrha);
		cbSvrha2.setItems(svrha);
	}
	
	private void setKM() throws ClassNotFoundException, SQLException{	
		ResultSet rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select km from "+ company.Main.Main.dbName+".putninalog where pravac='"+cbPravac.getValue()+"'", "putninalog");	
		while (rs.next()){
			 tfKM.setText(rs.getString("km"));
		}
		company.Main.DBConnection.DBConnection.dbDisconnect();
	}
	
	public void setDirection() throws ClassNotFoundException, SQLException{
		ResultSet rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select pravac from "+ company.Main.Main.dbName+".putninalog","putninalog");	
		
		while (rs.next()){
			 directions.add(rs.getString("pravac"));	 
		}
		cbPravac.setItems(directions.sorted());
		if(directions.size()>10){
			cbPravac.setVisibleRowCount(10);
		}else{
			cbPravac.setVisibleRowCount(directions.size());
		}
		company.Main.DBConnection.DBConnection.dbDisconnect();
	}
	
	private void setUsersFields(String user) throws ClassNotFoundException, SQLException {
		String [] userSplit= user.split("\\s+");
		ResultSet rs = company.Main.DBConnection.DBConnection.dbExecuteQuery("Select radno_mjesto, tipVozila, regVozila, vlasnikVozila from "+ company.Main.Main.dbName+".korisnici where ime_korisnik='"+userSplit[0].trim()+"' and prezime_korisnik='"+userSplit[1].trim()+"'","korisnici");		
		while (rs.next()){
			tfZvanje.setText(rs.getString("radno_mjesto"));
			tipVozila=rs.getString("tipVozila");
			regBroj=rs.getString("regVozila");
			tfVoziloData.setText(tipVozila+", reg.broj: "+ regBroj);
			tfVoziloVlasnik.setText(rs.getString("vlasnikVozila"));				
		}	
	}
	
	private void setDatePicker(){
	    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
	      @Override
	      public String toString(LocalDate date) {
	        if (date != null) {
	          return dateFormatter.format(date);
	        } else {
	          return "";
	        }
	      }

	      @Override
	      public LocalDate fromString(String string) {
	        if (string != null && !string.isEmpty()) {
	          return LocalDate.parse(string, dateFormatter);
	        } else {
	          return null;
	        }
	      }
	    };
	    dpDatumN.setConverter(converter);
	    dpDatumN.setPromptText(pattern.toLowerCase());	
	}
	
	private void setMjestoField(String pravac){
		if(pravac!=null){
		String mjesta="";
		String[]splitPravac= new String[20];
				splitPravac=pravac.split("\\-");
		for(int i=1; i<splitPravac.length-1;i++){
			if(splitPravac[i].equals("Zagreb")||splitPravac[i].equals("Split")||splitPravac[i].equals("Cakovec")){
				splitPravac[i]=splitPravac[i].concat("(HR)");
			}
			if(splitPravac[i].equals("Prag")||splitPravac[i].equals("Steti")){
				splitPravac[i]=splitPravac[i].concat("(CZ)");
			}
			if(splitPravac[i].equals("Beograd")||splitPravac[i].equals("Cacak")|| splitPravac[i].equals("Novi Sad")){
				splitPravac[i]=splitPravac[i].concat("(SRB)");
			}
			if(splitPravac[i].equals("Budimpesta")){
				splitPravac[i]=splitPravac[i].concat("(HUN)");
			}
			if(splitPravac[i].equals("Hanover")){
				splitPravac[i]=splitPravac[i].concat("(GER)");
			}
			if(splitPravac[i].equals("Celje")||splitPravac[i].equals("Ljubljana")||splitPravac[i].equals("Vevce")||splitPravac[i].equals("Kolicevo")||splitPravac[i].equals("Krsko")||splitPravac[i].equals("Radece")||splitPravac[i].equals("Medvode")){
				splitPravac[i]=splitPravac[i].concat("(SLO)");
			}
			mjesta=mjesta+", "+splitPravac[i];
		}
		mjesta=mjesta.substring(2);
		tfMjesta.setText(mjesta);
		}
	}
	
	private void setFields(){
		tfHoursGo.setPromptText("hh");
		tfMinutGo.setPromptText("mm");
		tfHoursReturn.setPromptText("hh");
		tfMinutReturn.setPromptText("mm");
		tfIznosDD.setText("20.00");
		tfIznosID.setText("30.00");
		tfUkupnoOstalo.setText("0.00");
	}
	
	private void obracunDnevnice(){
		double dd;
		double id;
		double iznosDD;
		double iznosID;
		try{
		if (tfBrDanaDD.getText().matches("[0-9]+")){
			dd=Double.parseDouble(tfBrDanaDD.getText());
		}else {dd=0;}
		if (tfBrojDanaID.getText().matches("[0-9]+")){
			id=Double.parseDouble(tfBrojDanaID.getText());
		}else {id=0;}
		if (tfIznosDD.getText().matches("[0-9].+")){
			iznosDD=Double.parseDouble(tfIznosDD.getText());
		}else {iznosDD=0;}
		if (tfIznosID.getText().matches("[0-9].+")){
			iznosID=Double.parseDouble(tfIznosID.getText());
		}else {iznosID=0;}
		String domD=""+(dd*iznosDD);
		String inoD=""+(id*iznosID*1.95583);
		String ukD=""+(dd*iznosDD+id*iznosID*1.95583);
		lbDD.setText(formatterDecimalString(domD));
		lbID.setText(formatterDecimalString(inoD));
		lbDnevnice1.setText(formatterDecimalString(ukD));
		lbDnevnice.setText(formatterDecimalString(ukD));
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		obracunUkupno();
	}
	
	private void obracunNaknade() {
		double gorivo;
		double km;
		try{
		if (tfGorivo.getText().matches("[.0-9]+")){
			gorivo=Double.parseDouble(tfGorivo.getText());
		}else {gorivo=0;}
		if (tfKM.getText().matches("[0-9]+")){
			km=Double.parseDouble(tfKM.getText());
		}else {km=0;}
		String naknada=""+(0.2*km*gorivo);
		lbTrVozilo.setText(formatterDecimalString(naknada));
		lbNaknada.setText(lbTrVozilo.getText());
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}	
		obracunUkupno();		
	}
	
	private void obracunOstalo() {
		double r1;
		double r2;
		double r3;
		try{
		if(!tfIznosR1.getText().isEmpty() || tfIznosR1.getText().matches("[0-9].+")){
			r1=Double.parseDouble(tfIznosR1.getText());
		}else{r1=0.00;}
		if(!tfIznosR2.getText().isEmpty() || tfIznosR2.getText().matches("[0-9].+")){
			r2=Double.parseDouble(tfIznosR2.getText());
		}else{r2=0.00;}
		if(!tfIznosR3.getText().isEmpty() || tfIznosR3.getText().matches("[0-9].+")){
			r3=Double.parseDouble(tfIznosR3.getText());
		}else{r3=0.00;}
		
		String r123=""+(r1+r2+r3);
		tfUkupnoOstalo.setText(formatterDecimalString(r123));
		lbOstaliTR.setText(tfUkupnoOstalo.getText());
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		obracunUkupno();		
	}
	
	private void obracunUkupno(){
		try{
		String ukT=""+(Double.parseDouble(lbDnevnice.getText().replace(",", "."))+Double.parseDouble(lbTrVozilo.getText().replace(",", "."))+Double.parseDouble(lbOstaliTR.getText().replace(",", ".")));
		lbUkupniTROSKOVI.setText(formatterDecimalString(ukT));
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
	}
	
	public String formatterDecimalString(String gItem){
		DecimalFormat df = new DecimalFormat("###,##0.00");
		try{
		double numToString=Double.parseDouble(gItem);
		gItem=df.format(numToString);
		return gItem;
		}catch (Exception e){
			gItem="";	
			company.Main.Main.showAlertError("GRESKA!!!", "Polje se ne moze formatirati u broj. Unesite tacan iznos!!!");
			return gItem;
		}
	}
	
	private Boolean isHours(String newValue){
		int nw = 0;
		try{
		nw=Integer.parseInt(newValue);
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		if (nw<24 && nw>-1){return false;}
		else{return true;}
	}
	
	private Boolean isMinut(String newValue){
		int nw = 0;
		try{
		nw=Integer.parseInt(newValue);
		}catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}
		if (nw<60 && nw>-1){return false;}
		else{return true;}
	}
	
	private void checkIfTextFieldIsEmpty(Boolean newPropertyValue, TextField tf) {
        if (!newPropertyValue && tf.getText().isEmpty())
            	tf.requestFocus();
	}

	private void checkIfCBIsEmpty(Boolean newPropertyValue, ComboBox<String> cb) {
		 if (!newPropertyValue && cb.getValue()==null)
         	cb.requestFocus();	
	}
	
	private void checkIfDPIsEmpty(Boolean newPropertyValue, DatePicker dp) {
		System.out.println(dp.getValue());	
		 if (!newPropertyValue ) dp.setValue(dp.getConverter().fromString(dp.getEditor().getText()));
		 if(dp.getValue()==null)
	         	dp.requestFocus();	
	}
	
/* ****************************************INITIALIZE*********************************************** */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// set focus to first textField "tfBrNaloga"
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	tfBrNaloga.requestFocus();
		    	cbSvrha1.setValue("posjeta poslovnim partnerima");
		    	
		 //   	cbPravac.setVisibleRowCount(10);
		    }
		});
		
		// set all ComboBoxes
		try {
			setUsers();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		setSvrha();
		try {
			setDirection();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		setFields();
		
		// set all ComboBoxes editiable
		cbKorisnik.setEditable(false);
		cbPravac.setEditable(false);
		cbSvrha1.setEditable(false);
		cbSvrha2.setEditable(false);
	
		cbKorisnik.getSelectionModel().selectedItemProperty()
	    .addListener(new ChangeListener<String>() {
	        public void changed(ObservableValue<? extends String> observable,
	                            String oldValue, String newValue) {
	        	System.out.println("cbKorisnik from "+oldValue+" to "+newValue);
	            try {
					setUsersFields(newValue);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	Platform.runLater(() -> { 
	    			cbSvrha1.requestFocus();	
	    		});
	        }
	    });
		
	  
		// set DatePicker   
		// Make the DatePicker editable
		dpDatumN.setEditable(true);
		
		
		// Print the new date in the TextArea
		dpDatumN.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
		     public void handle(ActionEvent event) 
		     {
		         LocalDate date = dpDatumN.getValue();
		         DayOfWeek day = dpDatumN.getValue().getDayOfWeek();
		         if (day.toString().equals("SUNDAY") || day.toString().equals("SATURDAY")){
		        	 dpDatumN.requestFocus();
		        	 String dan=null;
		        	 if(day.toString().equals("SUNDAY")) {
		        		 dan="NEDELJA";
		        	 }else{
		        		 dan="SUBOTA";
		        	 }
		        	 company.Main.Main.showAlertError("IZABRAN DATUM JE "+dan+"!", "Odaberite drugi datum.");
		         }
		          	 
		        	 tfDateGo.setText(dateFormatter.format(date));
		        	 if (!tfBrDana.getText().isEmpty()||!tfBrDana.getText().equals("")){
		        		 String dan=tfBrDana.getText();
		        		 tfBrDana.setText("0");
		        		 tfBrDana.setText(dan);
		        	 }
		        	 cbKorisnik.requestFocus();
		         
		       System.out.println("Selected date: " + date+"," +day);
		     }
		});
		
		setDatePicker();
	
		
	
		
		tfBrDana.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfBrDana from "+oldValue+" to "+newValue);	
			try{
		    if(newValue.matches("[0-9]+")){
		    tfBrDanaDD.setText(newValue);
		    tfBrojDanaID.setText("0");
		    int d=Integer.parseInt(newValue);
		    dpDatumN.getValue().plusDays(d);
		    tfDateReturn.setText(dateFormatter.format(dpDatumN.getValue().plusDays(d-1)));
	// provjeriti da li je potrebno jer prelazi na tf odmah nakon prvog karaktera:	   
			Platform.runLater(() -> { 
				 tfHoursGo.selectHome();
				 tfHoursGo.requestFocus();	
			});
		    }else{
		    	newValue="0";
		    	tfBrDanaDD.setText(newValue);}
		    obracunDnevnice();
		    }catch(Exception e){
		    	System.out.println(e.getMessage());
		    }
		});
		
		tfDateReturn.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfDateReturn from "+oldValue+" to "+newValue);
		    try{
		    if(newValue.matches("[0-9].+")){
		    	int i=0;
		    	LocalDate ld = LocalDate.parse(newValue, dateFormatter);
		    	if(ld.getDayOfWeek().toString().equals("SATURDAY")){i=1;}
		    	if (ld.getDayOfWeek().toString().equals("FRIDAY")){i=2;}
		    	lbDateObracuna.setText(dateFormatter.format(ld.plusDays(1+i)).toString());
		    }else{
		    	lbDateObracuna.setText("");
		    }
		    }catch(Exception e){
		    	System.out.println(e.getMessage());
		    }	    
		});
		
		tfBrDanaDD.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfBrDanaDD from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("")){
				obracunDnevnice();
			}
			else{
				tfBrDanaDD.setText(oldValue);
			
			}
		});
		tfBrojDanaID.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfBrojDanaID from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("")){
				obracunDnevnice();
			} else{
				tfBrojDanaID.setText(oldValue);
	
			}
			});
		tfIznosDD.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfIznosDD from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunDnevnice();
			} else{
				tfIznosDD.setText(oldValue);
	
			}
		});
		tfIznosID.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfIznosID from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunDnevnice();
			} else{
				tfIznosID.setText(oldValue);
	
			}
		});
		tfKM.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfKM from "+oldValue+" to "+newValue);	
		    if(newValue.matches("[0-9]+")||newValue.equals("")){
		   obracunNaknade();
		    } else{
		    	tfKM.setText(oldValue);
		
		    	}
		});
		tfGorivo.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfGorivo from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunNaknade();
			} else{
				tfGorivo.setText(oldValue);
		
			}
		});
		tfIznosR1.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tIznosR1 from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunOstalo();
			} else{
				tfIznosR1.setText(oldValue);
		
			}
		});
		tfIznosR2.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tIznosR2 from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunOstalo();
			}else{
				tfIznosR2.setText(oldValue);
		
			}
		});
		tfIznosR3.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tIznosR3 from "+oldValue+" to "+newValue);	
			if(newValue.matches("[.0-9]+")||newValue.matches("[0-9]+")||newValue.equals("")){
				obracunOstalo();
			}else{
				tfIznosR3.setText(oldValue);
		
			}
		});
		tfHoursGo.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfHoursGo from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("") && newValue.length()<3){
				if (isHours(newValue)){
				tfHoursGo.setText(oldValue);
				}
			}else {tfHoursGo.setText(oldValue);}	
		});
		tfMinutGo.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfMinutGo from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("") && newValue.length()<3){
				if (isMinut(newValue)){
				tfMinutGo.setText(oldValue);
				}
			}else {tfMinutGo.setText(oldValue);}	
		});
		tfHoursReturn.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfHoursReturn from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("") && newValue.length()<3){
				if (isHours(newValue)){
				tfHoursReturn.setText(oldValue);
				}
			}else {tfHoursReturn.setText(oldValue);}	
		});
		tfMinutReturn.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("tfMinutReturn from "+oldValue+" to "+newValue);	
			if(newValue.matches("[0-9]+")||newValue.equals("") && newValue.length()<3){
				if (isMinut(newValue)){
					tfMinutReturn.setText(oldValue);
				}
			}else {tfMinutReturn.setText(oldValue);}	
		});
		
		// perform task on javaFX TextField at onfocus and outfocus
		tfHoursGo.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfHoursGo);     
		    }
		});
		
		tfMinutGo.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfMinutGo);     
		    }
		});
		
		tfHoursReturn.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfHoursReturn);     
		    }
		});
		
		tfMinutReturn.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfMinutReturn);     
		    }
		});
		
		tfKM.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfKM);     
		    }
		});
		
		tfGorivo.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfGorivo);     
		    }
		});
		
		tfDateGo.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfDateGo);     
		    }
		});
		
		tfDateReturn.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfDateReturn);
		    }
		});
		
		tfBrDanaDD.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfBrDanaDD);     
		    }
		});
		
		tfBrojDanaID.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfBrojDanaID);     
		    }
		});
		
		tfIznosDD.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfIznosDD);     
		    }
		});
		
		tfIznosID.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfIznosID);     
		    }
		});
		
		tfBrDana.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfBrDana);     
		    }
		});
		
		tfZvanje.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfZvanje);     
		    }
		});
		
		tfMjesta.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfTextFieldIsEmpty(newPropertyValue, tfMjesta);     
		    }
		});
		
		cbKorisnik.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfCBIsEmpty(newPropertyValue, cbKorisnik);     
		    }
		});
		
		cbSvrha1.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfCBIsEmpty(newPropertyValue, cbSvrha1);     
		    }
		});
		
		cbPravac.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	if(newPropertyValue){
		     	   	try {
						setDirection();
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
		    	}else{	
		    		Platform.runLater(() -> { 
	        			tfBrDana.selectHome();
	        			tfBrDana.requestFocus();	
	        		});
		    	}
		    	checkIfCBIsEmpty(newPropertyValue, cbPravac);     
		    }
		});
		
		dpDatumN.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    { 
		    	checkIfDPIsEmpty(newPropertyValue, dpDatumN);     
		    }
		});
		cbPravac.getSelectionModel().selectedItemProperty()
	    .addListener(new ChangeListener<String>() {
	        public void changed(ObservableValue<? extends String> observable,
	                            String oldValue, String newValue) {
	        	System.out.println("cbPravac from "+oldValue+" to "+newValue);
	            setMjestoField(newValue);
	            try {
					setKM();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
	            	/*Platform.runLater(() -> { 
	        			tfBrDana.selectHome();
	        			tfBrDana.requestFocus();	
	        		});     */      
	        }
	    });
	
	}
}
