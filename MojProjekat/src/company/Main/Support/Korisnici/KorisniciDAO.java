package company.Main.Support.Korisnici;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class KorisniciDAO {
	
	public static ObservableList<Korisnici> searchKorisnik (String korIme, String korPrezime) throws ClassNotFoundException, SQLException {
		//Declare a SELECT statement
        String selectStmt = "SELECT * FROM "+company.Main.Main.dbName+".korisnici where ime_korisnik='" + korIme+"' or prezime_korisnik='"+korPrezime+"'";
      //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsKorisnici = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,"korisnici");
 
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Korisnici> dataList = getKorisniciList(rsKorisnici);
            company.Main.DBConnection.DBConnection.dbDisconnect();
            //Return employee object
            return dataList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);  
            //Return exception
            company.Main.DBConnection.DBConnection.dbDisconnect();
            throw e;
        }
    }
	
	@SuppressWarnings("unused")
	private static Korisnici getKorisnikFromResultSet(ResultSet rs) throws SQLException {
		 Korisnici kor =null;
		 if (rs.next()) {
			 kor=new Korisnici();
         kor.setIdKorisnici(rs.getInt("id_korisnik"));
         kor.setPassword(rs.getString("password"));
         kor.setImeKorisnici(rs.getString("ime_korisnik"));
         kor.setPrezimeKorisnici(rs.getString("prezime_korisnik"));
        
         kor.setJMBG(rs.getString("jmbg"));
         kor.setTekRacun(rs.getString("tek_racun"));
         kor.setBirthday(rs.getString("rodj"));
         kor.setAdresa(rs.getString("adresa"));
         kor.setGrad(rs.getString("grad"));
         kor.setOpstina(rs.getString("opstina"));
         kor.setSprema(rs.getString("sprema"));
         kor.setZanimanje(rs.getString("zanimanje"));
         kor.setRadnoMjesto(rs.getString("radno_mjesto"));
         kor.setTipVozila(rs.getString("tipVozila"));
         kor.setRegVozila(rs.getString("regVozila"));
         kor.setVlasnikVozila(rs.getString("vlasnikVozila"));
         kor.setZaposlenOd(rs.getString("zaposlen_od"));
         kor.setPrijeStazGod(rs.getInt("prije_staz_god"));
         kor.setPrijeStazMjes(rs.getInt("prije_staz_mjes"));
         kor.setPrijeStazDan(rs.getInt("prije_staz_dan"));
         kor.setNapomenaKor(rs.getString("napomenaKor")); 
		 }
		 company.Main.DBConnection.DBConnection.dbDisconnect();
		return kor;
	}

	public static ObservableList<Korisnici> searchKorisnici () throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM "+company.Main.Main.dbName+".korisnici";
 
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsKorisnici = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,"korisnici");
 
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Korisnici> dataList = getKorisniciList(rsKorisnici);
            company.Main.DBConnection.DBConnection.dbDisconnect();
            //Return employee object
            return dataList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            company.Main.DBConnection.DBConnection.dbDisconnect();
            //Return exception
            throw e;
        }
    }
 
    //Select * from employees operation
   public static ObservableList<Korisnici> getKorisniciList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Korisnici> dataList = FXCollections.observableArrayList();
 
        while (rs.next()) {
            Korisnici kor = new Korisnici();
            kor.setIdKorisnici(rs.getInt("id_korisnik"));
            kor.setPassword(rs.getString("password"));
            kor.setImeKorisnici(rs.getString("ime_korisnik"));
            kor.setPrezimeKorisnici(rs.getString("prezime_korisnik"));
            
            kor.setJMBG(rs.getString("jmbg"));
            kor.setTekRacun(rs.getString("tek_racun"));
            kor.setBirthday(rs.getString("rodj"));
            kor.setAdresa(rs.getString("adresa"));
            kor.setGrad(rs.getString("grad"));
            kor.setOpstina(rs.getString("opstina"));
            kor.setSprema(rs.getString("sprema"));
            kor.setZanimanje(rs.getString("zanimanje"));
            kor.setRadnoMjesto(rs.getString("radno_mjesto"));
            kor.setTipVozila(rs.getString("tipVozila"));
            kor.setRegVozila(rs.getString("regVozila"));
            kor.setVlasnikVozila(rs.getString("vlasnikVozila"));
            kor.setZaposlenOd(rs.getString("zaposlen_od"));
            kor.setPrijeStazGod(rs.getInt("prije_staz_god"));
            kor.setPrijeStazMjes(rs.getInt("prije_staz_mjes"));
            kor.setPrijeStazDan(rs.getInt("prije_staz_dan"));
            kor.setNapomenaKor(rs.getString("napomenaKor"));
             
            //Add employee to the ObservableList
            dataList.add(kor);
        }
        company.Main.DBConnection.DBConnection.dbDisconnect();
        //return empList (ObservableList of Employees)
        return dataList;
    }
 
   
   
   public static void updateSaId (String korId, String korIme, String korPrezime, String korPass, String korJMBG, String korTRac, String korRodj,String korAdresa, 
		   String korGrad, String korOpstina, String korSprema, String korZanim, String korRMjesto, String tipV, String regV, String vlasnV , String korZapOd,
		   String korPrijeRSGod, String korPrijeRSMjes, String korPrijeRSDan, String korNapomena) throws SQLException, ClassNotFoundException, ParseException {
       //Declare a UPDATE statement
	  int korIdInt=Integer.parseInt(korId);	
	  int korPSGodinaInt=Integer.parseInt(korPrijeRSGod);
	  int korPSMjesInt=Integer.parseInt(korPrijeRSMjes);
	  int korPSDanInt=Integer.parseInt(korPrijeRSDan);
	 // DateFormat format = new SimpleDateFormat("yyyy,mm,dd");
	//  Date korZapOdDate = format.parse(korZapOd);
	   
       String updateStmt =
                       "   UPDATE "+company.Main.Main.dbName+".korisnici " +
                       "      SET ime_korisnik='"+korIme+"', prezime_korisnik='"+korPrezime+"', password = '" +
                    		   korPass + "', jmbg='" + korJMBG + "',tek_racun='"+korTRac+"', rodj='"+ korRodj +
                       "', adresa='"+korAdresa+"', grad='"+korGrad+"', opstina='"+korOpstina+"', sprema='"+
                    		   korSprema+"', zanimanje='"+korZanim+"', radno_mjesto='"+korRMjesto+"', tipVozila='"
                       +tipV+"', regVozila='"+regV+"', vlasnikVozila='"+vlasnV+"', zaposlen_od='"+
                       korZapOd+"', prije_staz_god ="+korPSGodinaInt+",prije_staz_mjes="+korPSMjesInt+
                       ", prije_staz_dan="+korPSDanInt+", napomenaKor='"+korNapomena+"' "+
                       "    WHERE id_korisnik = " + korIdInt ;
                      
       //Execute UPDATE operation
       try {
           company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt,"korisnici");
           company.Main.DBConnection.DBConnection.dbDisconnect();
       } catch (SQLException e) {  
           System.out.print("Error occurred while UPDATE Operation: " + e);
           throw e;
       }
   }
   
   
   public static void deleteKorisnikSaId (String korIme, String korPrezime) throws SQLException, ClassNotFoundException {
       //Declare a DELETE statement
       String updateStmt =
                       "   DELETE FROM "+company.Main.Main.dbName+".korisnici" +
                       "    WHERE ime_korisnik = '" + korIme + "' and prezime_korisnik='"+korPrezime+"'";
                       

       //Execute UPDATE operation
       try {
           company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt,"korisnici");
           company.Main.DBConnection.DBConnection.dbDisconnect();
       } catch (SQLException e) {
           System.out.print("Error occurred while DELETE Operation: " + e);
           throw e;
       }
   }

   //*************************************
   //INSERT an employee
   //*************************************
   public static void insertKorisnik (String korIme, String korPrezime, String korPass, String korJMBG, String korTRac, String korRodj, String korAdresa,
		   String korGrad, String korOpstina, String korSprema, String korZanim, String korRMjesto,String tipV, String regV, String vlasnV , String korZapOd,
		   String korPrijeRSGod, String korPrijeRSMjes, String korPrijeRSDan, String korNapomena) throws SQLException, ClassNotFoundException {
       //Declare a DELETE statement
	
	   String i="";
	
	   if (i.equals(korPrijeRSGod)) {korPrijeRSGod="0";}
	int korPSGodinaInt=Integer.parseInt(korPrijeRSGod);
	
		if (i.equals(korPrijeRSMjes)) {korPrijeRSMjes="0";}
	int korPSMjesInt=Integer.parseInt(korPrijeRSMjes);
	
		 if (i.equals(korPrijeRSDan)) {korPrijeRSDan="0";} 
	int korPSDanInt=Integer.parseInt(korPrijeRSDan);
		 
       String updateStmt ="INSERT INTO "+company.Main.Main.dbName+".korisnici "
       		+ "(password, "
       		+ "ime_korisnik, "
       		+ "prezime_korisnik,"
       		+ "jmbg, "
       		+ "tek_racun, "
       		+ "rodj, "
       		+ "adresa,"
       		+ "grad, "
       		+ "opstina, "
       		+ "sprema, "
       		+ "zanimanje, "
       		+ "radno_mjesto, "
       		+ "tipVozila, "
       		+ "regVozila, " 
       		+ "vlasnikVozila, "
       		+ "zaposlen_od, "
       		+ "prije_staz_god, "
       		+ "prije_staz_mjes, "
       		+ "prije_staz_dan, "
       		+ "napomenaKor) "
    		+ "VALUES ('"+
       		korPass+"', '"+
    		korIme+"', '"+
    		korPrezime+"','"+
    		korJMBG+"', '"+
    		korTRac+"', '"+
    		korRodj+"', '"+
    		korAdresa+"', '"+
    		korGrad+"', '"+
    		korOpstina+"', '"+
    		korSprema+"', '"+
    		korZanim+"', '"+
    		korRMjesto+"', '"+
    		tipV+"', '"+
    		regV+"', '"+
    		vlasnV+"', '"+
    		korZapOd+"', '"+ 
    		korPSGodinaInt+"', '"+
    		korPSMjesInt+"', '"+
    		korPSDanInt+"', '"+
    		korNapomena+"')"  ;
                     
       //Execute DELETE operation
       try {
           company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt,"korisnici");
           company.Main.DBConnection.DBConnection.dbDisconnect();
       } catch (SQLException e) {
           System.out.print("Error occurred while DELETE Operation: " + e);
           throw e;
       }
   }
   public static ObservableList<Korisnici> searchDataCompany (String table, String c19, String c20, String c21) throws ClassNotFoundException, SQLException {
		//Declare a SELECT statement
       String selectStmt = "SELECT * FROM "+company.Main.Main.dbName+"."+table;
     //Execute SELECT statement
       try {
           //Get ResultSet from dbExecuteQuery method
           ResultSet rsCD = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,table);

           //Send ResultSet to the getEmployeeList method and get employee object
           ObservableList<Korisnici> dataList = getCompDataList(rsCD,c19, c20, c21 );
           company.Main.DBConnection.DBConnection.dbDisconnect();
           //Return employee object
           return dataList;
       } catch (SQLException e) {
           System.out.println("SQL select operation has been failed: " + e);  
           //Return exception
           company.Main.DBConnection.DBConnection.dbDisconnect();
           throw e;
       }
   }

private static ObservableList<Korisnici> getCompDataList(ResultSet rs, String c19, String c20, String c21) throws SQLException {

	ObservableList<Korisnici> dataList = FXCollections.observableArrayList();
	try{
     while (rs.next()) {
         Korisnici kor = new Korisnici();
         kor.setCompanyName(rs.getString("company_name"));
         kor.setCompanyStreet(rs.getString("street"));
         kor.setCompanyCity(rs.getString("city"));
         kor.setCompanyPostCode(rs.getString("post_code"));
         kor.setCompanyJIB(rs.getString("jib"));
         kor.setCompanyPDV(rs.getString("pdv"));
         kor.setCompanyMB(rs.getString("mb"));
         kor.setCompanyDirector(rs.getString("director"));
         kor.setCompanySifraPJ(rs.getString("sifra_pj"));
         kor.setCompanyPhone(rs.getString("phone"));
         kor.setCompanyFax(rs.getString("fax"));
         kor.setCompanyMobile(rs.getString("mobile"));
         kor.setCompanyMail(rs.getString("mail"));
         kor.setCompanyWeb(rs.getString("web"));
         kor.setCompanyZiroRacun(rs.getString("ziro_racun"));
         kor.setCompanyBanka(rs.getString("banka"));
         kor.setCompanyDevRacun(rs.getString("dev_racun"));
         kor.setCompanyIBAN(rs.getString("iban"));
         kor.setCompanySWIFT(rs.getString("swift"));
         kor.setCompanyLogo(rs.getString(c19));
         kor.setCompanySign(rs.getString(c20));
         kor.setCompanyStamp(rs.getString(c21));
         kor.setCompanyID(rs.getInt("company_id"));
         
          
         //Add employee to the ObservableList
         dataList.add(kor);
     }
	}catch(SQLException e){
		e.getMessage();
	}
	 company.Main.DBConnection.DBConnection.dbDisconnect();
		
	return dataList;
}

public static ObservableList<Korisnici> getSortedDataCompany (String key, String table, String c19, String c20, String c21) throws ClassNotFoundException, SQLException {
	//Declare a SELECT statement
   String selectStmt = "SELECT * FROM "+company.Main.Main.dbName+"."+table+" where  company_name LIKE '" +key.toUpperCase() +"%' or company_id LIKE '" +key.toUpperCase() +"%'" ;
	
 //Execute SELECT statement
   try {
       //Get ResultSet from dbExecuteQuery method
       ResultSet rsCD = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,table);

       //Send ResultSet to the getEmployeeList method and get employee object
       ObservableList<Korisnici> dataList = getCompDataList(rsCD,c19, c20, c21 );
       company.Main.DBConnection.DBConnection.dbDisconnect();
       //Return employee object
       return dataList;
   } catch (SQLException e) {
       System.out.println("SQL select operation has been failed: " + e);  
       //Return exception
       company.Main.DBConnection.DBConnection.dbDisconnect();
       throw e;
   }
}
public static void insertCompanyData (ArrayList<String>al, int ID ,String table, String c19, String c20,String c21) throws SQLException, ClassNotFoundException {
    //Declare a DELETE statement
		 
    String insertStmt ="INSERT INTO "+company.Main.Main.dbName+"."+table
    		+ "(company_name,"+ 
				"street,"+ 
				"city,"+ 
				"post_code,"+ 
				"jib,"+ 
				"pdv,"+ 
				"mb,"+ 
				"director,"+
				"sifra_pj,"+ 
				"phone,"+ 
				"fax ,"+
				"mobile,"+ 
				"mail,"+ 
				"web,"+
				"ziro_racun ,"+
				"banka ,"+
				"dev_racun,"+ 
				"iban ,"+
				"swift ,"+
				c19+" ,"+
				c20+" ,"+
				c21+" ,"+
				"company_id) "
 		+ "VALUES ('"+
    		al.get(0)+"', '"+
    		al.get(1)+"', '"+
    		al.get(2)+"', '"+
    		al.get(3)+"', '"+
    		al.get(4)+"', '"+
    		al.get(5)+"', '"+
    		al.get(6)+"', '"+
    		al.get(7)+"', '"+
    		al.get(8)+"', '"+
    		al.get(9)+"', '"+
    		al.get(10)+"', '"+
    		al.get(11)+"', '"+
    		al.get(12)+"', '"+
    		al.get(13)+"', '"+
    		al.get(14)+"', '"+
    		al.get(15)+"', '"+ 
    		al.get(16)+"', '"+
    		al.get(17)+"', '"+
    		al.get(18)+"', '"+
    		al.get(19)+"', '"+
    		al.get(20)+"', '"+
    		al.get(21)+"', '"+
    		ID+"')"  ;
      
    try {
        company.Main.DBConnection.DBConnection.dbExecuteUpdate(insertStmt,table);
        company.Main.DBConnection.DBConnection.dbDisconnect();
    } catch (SQLException e) {
        System.out.print("Error occurred while INSERT Operation: " + e);
        throw e;
    }
}

public static void updateCompanyData (ArrayList<String> al, int ID, String table, String c19, String c20, String c21) throws SQLException, ClassNotFoundException, ParseException {
    //Declare a UPDATE statement
	
    String updateStmt =
                    "   UPDATE "+company.Main.Main.dbName+"."+ table +
                    "   SET company_name ='"+ al.get(0) + "', " +
                    "street='"		+ al.get(1) + "', " +
                    "city='"		+ al.get(2) + "', " +
                    "post_code='"	+ al.get(3) + "', " +
                    "jib='"			+ al.get(4) + "', " +
                    "pdv='"			+ al.get(5) + "', " +
                    "mb='"			+ al.get(6) + "', " +
                    "director='"	+ al.get(7) + "', " +
                    "sifra_pj='"	+ al.get(8) + "', " +
                    "phone='"		+ al.get(9) + "', " +
                    "fax ='"		+ al.get(10)+ "', " +
                    "mobile='"		+ al.get(11)+ "', " +
                    "mail='"		+ al.get(12)+ "', " +
                    "web='"			+ al.get(13)+ "', " +
                    "ziro_racun ='"	+ al.get(14)+ "', " +
                    "banka ='"		+ al.get(15)+ "', " +
                    "dev_racun='"	+ al.get(16)+ "', " +
                    "iban ='"		+ al.get(17)+ "', " +
                    "swift ='"		+ al.get(18)+ "', " +
                    c19 +" ='"		+ al.get(19)+ "', " +  // kod company data: logo, sign, stamp
    				c20 +" ='"		+ al.get(20)+ "', " +  // a kod komitenti: entitet, konto_kupac, konto_dobavljac 
    				c21 +" ='"		+ al.get(21)+ "'  " +
                	" WHERE company_id =" + ID;
                   
    //Execute UPDATE operation
    try {
        company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt,table);
        company.Main.DBConnection.DBConnection.dbDisconnect();
    } catch (SQLException e) {  
        System.out.print("Error occurred while UPDATE Operation: " + e);
        throw e;
    }
}

public static void deleteCompanyData (int ID, String table) throws SQLException, ClassNotFoundException {
    //Declare a DELETE statement
    String updateStmt =
                    "   DELETE FROM "+company.Main.Main.dbName+"."+table +
                    "    WHERE company_id =" + ID;
                    

    //Execute UPDATE operation
    try {
        company.Main.DBConnection.DBConnection.dbExecuteUpdate(updateStmt,table);
        company.Main.DBConnection.DBConnection.dbDisconnect();
    } catch (SQLException e) {
        System.out.print("Error occurred while DELETE Operation: " + e);
        throw e;
    }
}

}
