package company.Main.Support.Korisnici;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



public class Korisnici  {

// Declare Korisnici Table Columns
	private  IntegerProperty id_korisnik;
	private  StringProperty password;
	private  StringProperty ime_korisnik;
	private  StringProperty prezime_korisnik;
	
	private StringProperty jmbg;
	private StringProperty tek_racun;
	private StringProperty rodj;
	private StringProperty adresa;
	private StringProperty grad;
	private StringProperty opstina;
	private StringProperty sprema;
	private StringProperty zanimanje;
	private StringProperty radno_mjesto;
	private StringProperty tipVozila;
	private StringProperty regVozila;
	private StringProperty vlasnikVozila;
	private StringProperty zaposlen_od;
	private IntegerProperty prije_staz_god;
	private IntegerProperty prije_staz_mjes;
	private IntegerProperty prije_staz_dan;
	private StringProperty napomenaKor;
	
	private IntegerProperty company_id;
	private StringProperty companyName;
	private StringProperty companyStreet;
	private StringProperty companyCity;
	private StringProperty companyPostCode;
	private StringProperty companyJIB;
	private StringProperty companyPDV;
	private StringProperty companyMB;
	private StringProperty companyDirector;
	private StringProperty companySifraPJ;
	private StringProperty companyPhone;
	private StringProperty companyFax;
	private StringProperty companyMobile;
	private StringProperty companyMail;
	private StringProperty companyWeb;
	private StringProperty companyZiroRacun;
	private StringProperty companyDevRacun;
	private StringProperty companyBanka;
	private StringProperty companyIBAN;
	private StringProperty companySWIFT;
	private StringProperty companyLogo;
	private StringProperty companySign;
	private StringProperty companyStamp;	
  
// Constructor
	public Korisnici (){
		this.id_korisnik = new SimpleIntegerProperty();
		this.password = new SimpleStringProperty();
		this.ime_korisnik = new SimpleStringProperty();
		this.prezime_korisnik = new SimpleStringProperty();
			
		this.jmbg = new  SimpleStringProperty();
		this.tek_racun = new SimpleStringProperty();
		this.rodj = new SimpleStringProperty();
		this.adresa = new SimpleStringProperty();
		this.grad = new SimpleStringProperty();
		this.opstina = new SimpleStringProperty();
		this.sprema = new SimpleStringProperty();
		this.zanimanje = new SimpleStringProperty();
		this.radno_mjesto = new SimpleStringProperty();
		this.zaposlen_od = new SimpleStringProperty();
		this.tipVozila = new SimpleStringProperty();
		this.regVozila = new SimpleStringProperty();
		this.vlasnikVozila=new SimpleStringProperty();
		this.prije_staz_god = new SimpleIntegerProperty();
		this.prije_staz_mjes = new SimpleIntegerProperty();
		this.prije_staz_dan = new SimpleIntegerProperty();
		this.napomenaKor = new SimpleStringProperty();
		
		this.company_id = new  SimpleIntegerProperty();
		this.companyName = new  SimpleStringProperty();
		this.companyStreet = new  SimpleStringProperty();
		this.companyCity  = new  SimpleStringProperty();
		this.companyPostCode = new  SimpleStringProperty();
		this.companyJIB = new  SimpleStringProperty();
		this.companyPDV = new  SimpleStringProperty();
		this.companyMB = new  SimpleStringProperty();
		this.companyDirector = new  SimpleStringProperty();
		this.companySifraPJ = new  SimpleStringProperty();
		this.companyPhone = new  SimpleStringProperty();
		this.companyFax = new  SimpleStringProperty();
		this.companyMobile = new  SimpleStringProperty();
		this.companyMail = new  SimpleStringProperty();
		this.companyWeb = new  SimpleStringProperty();
		this.companyZiroRacun = new  SimpleStringProperty();
		this.companyDevRacun = new  SimpleStringProperty();
		this.companyBanka = new  SimpleStringProperty();
		this.companyIBAN = new  SimpleStringProperty();
		this.companySWIFT = new  SimpleStringProperty();
		this.companyLogo= new SimpleStringProperty();
		this.companySign= new SimpleStringProperty();
		this.companyStamp= new SimpleStringProperty();
		
	}

	public int getIdKorisnici (){
		return id_korisnik.get();
	}
	
	public void setIdKorisnici (int idKorisnici) {
	id_korisnik.set(idKorisnici);
	}

	public IntegerProperty IdKorisniciProperty(){
		return id_korisnik;
	}
	
	public String getPassword(){
		return password.get();
	}
	
	public void setPassword (String lozinka){
		this.password.set(lozinka);
	}
	
	public StringProperty PasswordProperty () {
		return password;
	}
	
	public String getImeKorisnici(){
		return ime_korisnik.get();
	}
	
	public void setImeKorisnici (String imeKorisnici){
		this.ime_korisnik.set(imeKorisnici);
	}
	
	public StringProperty ImeKorisniciProperty () {
		return ime_korisnik;
	}
	
	public String getPrezimeKorisnici(){
		return prezime_korisnik.get();
	}
	
	public void setPrezimeKorisnici (String prezimeKorisnici){
		this.prezime_korisnik.set(prezimeKorisnici);
	}
	
	public StringProperty PrezimeKorsiniciProperty () {
		return prezime_korisnik;
	}
	
	public String getJMBG(){
		return jmbg.get();
	}
	
	public void setJMBG (String korJMBG){
		this.jmbg.set(korJMBG);
	}
	
	public StringProperty JmbgProperty () {
		return jmbg;
	}
	
	
	public String getTekRacuni(){
		return tek_racun.get();
	}
	
	public void setTekRacun (String tekuciRacun){
		this.tek_racun.set(tekuciRacun);
	}
	
	public StringProperty TekuciRacunProperty () {
		return tek_racun;
	}
	

	public String getBirthday() {
        return rodj.get();
    }

    public void setBirthday(String birth) {
       this.rodj.set(birth);
    }

    public StringProperty BirthdayProperty() {
        return rodj;
    }

    public String getAdresa(){
		return adresa.get();
	}
	
	public void setAdresa (String adresaUl){
		this.adresa.set(adresaUl);
	}
	
	public StringProperty AdresaProperty () {
		return adresa;
	}
    
	
	public String getGrad(){
		return grad.get();
	}
	
	public void setGrad (String gradKor){
		this.grad.set(gradKor);
	}
	
	public StringProperty GradProperty () {
		return grad;
	}
	
	public String getOpstina (){
		return opstina.get();
	}
	
	public void setOpstina (String sifraOpstina) {
	opstina.set(sifraOpstina);
	}

	public StringProperty OpstinaProperty(){
		return opstina;
	}
	
	public String getSprema(){
		return grad.get();
	}
	
	public void setSprema (String strSprema){
		this.sprema.set(strSprema);
	}
	
	public StringProperty SpremaProperty () {
		return sprema;
	}
	
	public String getZanimanje(){
		return zanimanje.get();
	}
	
	public void setZanimanje (String zanimKor){
		this.zanimanje.set(zanimKor);
	}
	
	public StringProperty ZanimanjeProperty () {
		return zanimanje;
	}
	
	
	public String getRadnoMjesto(){
		return radno_mjesto.get();
	}
	
	public void setRadnoMjesto (String radnoMjesto){
		this.radno_mjesto.set(radnoMjesto);
	}
	
	public StringProperty RadnoMjestoProperty () {
		return radno_mjesto;
	}
	
	
	public String getTipVozila(){
		return tipVozila.get();
	}
	
	public void setTipVozila (String tipVoz){
		this.tipVozila.set(tipVoz);
	}
	
	public StringProperty TipVozilaProperty () {
		return tipVozila;
	}
	public String getRegVozila(){
		return regVozila.get();
	}
	
	public void setRegVozila (String regVoz){
		this.regVozila.set(regVoz);
	}
	
	public StringProperty RegVozilaProperty () {
		return regVozila;
	}
	public String getVlasnikVozila(){
		return vlasnikVozila.get();
	}
	
	public void setVlasnikVozila (String vlasnVoz){
		this.vlasnikVozila.set(vlasnVoz);
	}
	
	public StringProperty VlasnikVozilaProperty () {
		return vlasnikVozila;
	}
	
    public int getPrijeStazGod (){
		return prije_staz_god.get();
	}
	
	public void setPrijeStazGod (int psGodina) {
	prije_staz_god.set(psGodina);
	}

	public IntegerProperty PrijeStazGodProperty(){
		return prije_staz_god;
	}
    
	public int getPrijeStazMjes (){
		return prije_staz_mjes.get();
	}
	
	public void setPrijeStazMjes (int psMjesec) {
	prije_staz_mjes.set(psMjesec);
	}

	public IntegerProperty PrijeStazMjesProperty(){
		return prije_staz_mjes;
	}
	
	
	public int getPrijeStazDan (){
		return prije_staz_dan.get();
	}
	
	public void setPrijeStazDan (int psDan) {
	prije_staz_dan.set(psDan);
	}

	public IntegerProperty PrijeStazDanProperty(){
		return prije_staz_dan;
	}
	
	public String getNapomenaKor(){
		return napomenaKor.get();
	}
	
	public void setNapomenaKor (String korNapom){
		this.napomenaKor.set(korNapom);
	}
	
	public StringProperty napomenaKorProperty () {
		return napomenaKor;
	}
	
	
	public String getZaposlenOd() {
        return zaposlen_od.get();
    }

    public void setZaposlenOd (String zapOd) {
       this.zaposlen_od.set(zapOd);
    }

    public StringProperty ZaposlenOdProperty() {
        return zaposlen_od;
    }

/* *************************************** COMPANY DATA  ****************************** */
   

	public int getCompanyID (){
		return company_id.get();
	}
	
	public void setCompanyID(int cID) {
		company_id.set(cID);
	}

	public IntegerProperty CompanyIDProperty(){
		return company_id;
	}
	
   
    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName (String cName) {
       this.companyName.set(cName);
    }

    public StringProperty CompanyNameProperty() {
        return companyName;
    }
    
    public String getCompanyStreet() {
        return companyStreet.get();
    }

    public void setCompanyStreet (String cStreet) {
       this.companyStreet.set(cStreet);
    }

    public StringProperty CompanyStreetProperty() {
        return companyStreet;
    }
    
    public String getCompanyCity() {
        return companyCity.get();
    }

    public void setCompanyCity (String cCity) {
       this.companyCity.set(cCity);
    }

    public StringProperty CompanyCityProperty() {
        return companyCity;
    }
    
    public String getCompanyPostCode() {
        return companyPostCode.get();
    }

    public void setCompanyPostCode (String cPostCode) {
       this.companyPostCode.set(cPostCode);
    }

    public StringProperty CompanyPostCodeProperty() {
        return companyPostCode;
    }
    
    public String getCompanyJIB() {
        return companyJIB.get();
    }

    public void setCompanyJIB (String cJIB) {
       this.companyJIB.set(cJIB);
    }

    public StringProperty CompanyJIBProperty() {
        return companyJIB;
    }
    
    public String getCompanyPDV() {
        return companyPDV.get();
    }

    public void setCompanyPDV (String cPDV) {
       this.companyPDV.set(cPDV);
    }

    public StringProperty CompanyPDVProperty() {
        return companyPDV;
    }
    
    public String getCompanyMB() {
        return companyMB.get();
    }

    public void setCompanyMB (String cMB) {
       this.companyMB.set(cMB);
    }

    public StringProperty CompanyMBProperty() {
        return companyMB;
    }
    
    public String getCompanyDirector() {
        return companyDirector.get();
    }

    public void setCompanyDirector (String cDirector) {
       this.companyDirector.set(cDirector);
    }

    public StringProperty CompanyDirectorProperty() {
        return companyDirector;
    }
    
    public String getCompanySifraPJ() {
        return companySifraPJ.get();
    }

    public void setCompanySifraPJ (String cSifraPJ) {
       this.companySifraPJ.set(cSifraPJ);
    }

    public StringProperty CompanySifraPJProperty() {
        return companySifraPJ;
    }
    
    public String getCompanyPhone() {
        return companyPhone.get();
    }

    public void setCompanyPhone (String cPhone) {
       this.companyPhone.set(cPhone);
    }

    public StringProperty CompanyPhoneProperty() {
        return companyPhone;
    }
    
    public String getCompanyFax() {
        return companyFax.get();
    }

    public void setCompanyFax (String cFax) {
       this.companyFax.set(cFax);
    }

    public StringProperty CompanyFaxProperty() {
        return companyFax;
    }
    
    public String getCompanyMobile() {
        return companyMobile.get();
    }

    public void setCompanyMobile (String cMobile) {
       this.companyMobile.set(cMobile);
    }

    public StringProperty CompanyMobileProperty() {
        return companyMobile;
    }
    
    public String getCompanyMail() {
        return companyMail.get();
    }

    public void setCompanyMail (String cMail) {
       this.companyMail.set(cMail);
    }

    public StringProperty CompanyMailProperty() {
        return companyMail;
    }
    
    public String getCompanyWeb() {
        return companyWeb.get();
    }

    public void setCompanyWeb (String cWeb) {
       this.companyWeb.set(cWeb);
    }

    public StringProperty CompanyWebProperty() {
        return companyWeb;
    }
    
    public String getCompanyZiroRacun() {
        return companyZiroRacun.get();
    }

    public void setCompanyZiroRacun (String cZiroRacun) {
       this.companyZiroRacun.set(cZiroRacun);
    }

    public StringProperty CompanyZiroRacunProperty() {
        return companyZiroRacun;
    }
    
    public String getCompanyDevRacun() {
        return companyDevRacun.get();
    }

    public void setCompanyDevRacun (String cDevRacun) {
       this.companyDevRacun.set(cDevRacun);
    }

    public StringProperty CompanyDevRacunProperty() {
        return companyDevRacun;
    }
    
    public String getCompanyBanka() {
        return companyBanka.get();
    }

    public void setCompanyBanka (String cBanka) {
       this.companyBanka.set(cBanka);
    }

    public StringProperty CompanyBankaProperty() {
        return companyBanka;
    }
    
    public String getCompanyIBAN() {
        return companyIBAN.get();
    }

    public void setCompanyIBAN (String cIBAN) {
       this.companyIBAN.set(cIBAN);
    }

    public StringProperty CompanyIBANProperty() {
        return companyIBAN;
    }
    
    public String getCompanySWIFT() {
        return companySWIFT.get();
    }

    public void setCompanySWIFT (String cSWIFT) {
       this.companySWIFT.set(cSWIFT);
    }

    public StringProperty CompanySWIFTProperty() {
        return companySWIFT;
    }
    
    public String getCompanyLogo() {
        return companyLogo.get();
    }

    public void setCompanyLogo (String cLogo) {
       this.companyLogo.set(cLogo);
    }

    public StringProperty CompanyLogoProperty() {
        return companyLogo;
    }
    
    public String getCompanySign() {
        return companySign.get();
    }

    public void setCompanySign (String cSign) {
       this.companySign.set(cSign);
    }

    public StringProperty CompanySignProperty() {
        return companySign;
    }
    
    public String getCompanyStamp() {
        return companyStamp.get();
    }

    public void setCompanyStamp (String cStamp) {
       this.companyStamp.set(cStamp);
    }

    public StringProperty CompanyStampProperty() {
        return companyStamp;
    }
}
