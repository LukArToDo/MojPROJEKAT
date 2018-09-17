package company.Main.Support.Finansije;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Finansije {

	
private StringProperty redBroj;
private StringProperty opisPromjene;
private StringProperty komitentZR;
private StringProperty komitenti;
private StringProperty duguje;
private StringProperty potrazuje;
private StringProperty konto;
private StringProperty kontoSifra;


public Finansije(){
	this.redBroj= new SimpleStringProperty();
	this.opisPromjene=new SimpleStringProperty();
	this.komitentZR=new SimpleStringProperty();
	this.komitenti=new SimpleStringProperty();
	this.duguje=new SimpleStringProperty();
	this.potrazuje=new SimpleStringProperty();
	this.konto=new SimpleStringProperty();
	this.kontoSifra=new SimpleStringProperty();
}


public String geRedBroj(){
	return redBroj.get();
}

public void setRedBroj(String rb){
	this.redBroj.set(rb);
}

public StringProperty RedBrojProperty () {
	return redBroj;
}
public String getPromjena(){
	return opisPromjene.get();
}

public void setPromjene (String prom){
	this.opisPromjene.set(prom);
}

public StringProperty PromjenaProperty () {
	return opisPromjene;
}
public String getKomitentZR(){
	return komitentZR.get();
}

public void setKomitentZR(String kzr){
	this.komitentZR.set(kzr);
}

public StringProperty KomitentZRProperty () {
	return komitentZR;
}
public String getKomitenti(){
	return komitenti.get();
}

public void setKomitenti(String kom){
	this.komitenti.set(kom);
}

public StringProperty KomitentiProperty () {
	return komitenti;
}
public String getDuguje(){
	return duguje.get();
}

public void setDuguje(String dug){
	try{
	new DecimalFormatSymbols();
	DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
	symbols.setGroupingSeparator(',');
	symbols.setDecimalSeparator('.');
	DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
	dug=dug.replaceAll(",", "");
	double numToString=Double.parseDouble(dug);
	dug=df.format(numToString);
	} catch (NumberFormatException e) {
		dug="0.00";
		company.Main.Main.showAlertError("GRESKA!!!", "Polje DUGUJE se ne moze formatirati u broj. Unesite tacan iznos!!!");
	}
	this.duguje.set(dug);
}

public StringProperty DugujeProperty () {
	return duguje;
}
public String getPotrazuje(){
	return potrazuje.get();
}

public void setPotrazuje(String pot){
	try{
	new DecimalFormatSymbols();
	DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
	symbols.setGroupingSeparator(',');
	symbols.setDecimalSeparator('.');
	DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
	pot=pot.replaceAll(",", "");
	double numToString=Double.parseDouble(pot);
	pot=df.format(numToString);
	} catch (NumberFormatException e) {
		pot="0.00";
		company.Main.Main.showAlertError("GRESKA!!!", "Polje DUGUJE se ne moze formatirati u broj. Unesite tacan iznos!!!");
	}
	this.potrazuje.set(pot);
}

public StringProperty PotrazujeProperty () {
	return potrazuje;
}
public String getKonto(){
	return konto.get();
}

public void setKonto(String kon){
	this.konto.set(kon);
}

public StringProperty KontoProperty () {
	return konto;
}

public String getKontoSifra(){
	return kontoSifra.get();
}

public void setKontoSifra(String konSifra){
	this.kontoSifra.set(konSifra);
}

public StringProperty KontoSifraProperty () {
	return kontoSifra;
}

}
