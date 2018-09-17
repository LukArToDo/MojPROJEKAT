package company.Main.Support.Finansije;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import company.Main.Support.Izvodi.StringToSplit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FinasnijeDAO {
	
	
public static ObservableList<Finansije> searchDataPromjene (ArrayList<String> list){		
	            ObservableList<Finansije> dataList2 = getDataPromjene(list);        
	            return dataList2;     
}

public static ObservableList<Finansije> getDataPromjene(ArrayList<String> list){

	 ObservableList<Finansije> dataList = FXCollections.observableArrayList();
	 
	 int j=list.size()/8;
		 for (int i=0; i<j;i++){
			 Finansije kor = new Finansije();
			 kor.setRedBroj(list.get(i));
			 kor.setPromjene(list.get(j+i));	
			 kor.setKomitentZR(list.get(2*j+i));
			 kor.setKomitenti(list.get(3*j+i));
			 kor.setDuguje(list.get(4*j+i));
			 kor.setPotrazuje(list.get(5*j+i));
			 kor.setKonto(list.get(6*j+i));
			 kor.setKontoSifra(list.get(7*j+i));
	 dataList.add(kor);
		 }	
	return dataList;	
	}


public static void getKontoAndSifra (String komitent, String komitentZR, String duguje) throws ClassNotFoundException{
	String selectStmt=null; 
	String[] kom=komitent.split("\\s+");
	if (kom.length>1){
		selectStmt = "SELECT company_id, konto_kupac,konto_dobavljac FROM "+ company.Main.Main.dbName+".komitenti where  ziro_racun='"+komitentZR+"' or company_name LIKE '%" + kom[0].toString().toUpperCase()+"%' or company_name LIKE '%" + kom[1].toString().toUpperCase()+"%'";	      
	}
	else{  
		selectStmt = "SELECT company_id, konto_kupac,konto_dobavljac FROM "+ company.Main.Main.dbName+".komitenti where  ziro_racun='"+komitentZR+"' or company_name LIKE '%" + kom[0].toString().toUpperCase()+"%'";
	}
      
	  String kontoK=null;
	  String kontoD=null;
	  String sifra=null;
	  
	  //Execute SELECT statement
      try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsKonto = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,"komitenti");
      
            while(rsKonto.next()){
  
            	kontoK=rsKonto.getString("konto_kupac");
            	kontoD=rsKonto.getString("konto_dobavljac");
            	sifra=rsKonto.getString("company_id");
    
    
            	if (kontoK!=null && kontoD!=null && sifra!=null){
            		break;
            		}
            	}
        	} catch (SQLException e) {
        		System.out.println("SQL select operation has been failed: " + e);
        		kontoK=null;kontoD=null;sifra=null;
        		}
      if (kontoK==null || kontoD==null){
    	  StringToSplit.listKonto.add("?");
      }
      else{if (duguje.equals("0.00")){
    	  StringToSplit.listKonto.add(kontoK);
    	  }
      else {StringToSplit.listKonto.add(kontoD);
      }
      }
      if (sifra==null){StringToSplit.listKontoSifra.add("?");}
      else{ StringToSplit.listKontoSifra.add(sifra);}
	 
	 System.out.println("komitent iz DB: "+kontoK+" ili dobavljac "+kontoD);
	 System.out.println("sifra komitenta iz DB: "+sifra);
}

public static ArrayList<String> getTableKonto (int row,String komitent, String komitentZR, String duguje) throws ClassNotFoundException{
	
	ArrayList<String> data=new ArrayList<String>();
	data.add(""+row); 
	String selectStmt=null; 
	String[] kom=komitent.split("\\s+");
	if (kom.length>1 && !kom[1].toString().equals(";")){
		selectStmt = "SELECT company_id, konto_kupac,konto_dobavljac,company_name FROM "+ company.Main.Main.dbName+".komitenti where  ziro_racun='"+komitentZR+"%' or company_name LIKE '%" + kom[0].toString().toUpperCase()+"%' or company_name LIKE '%" + kom[1].toString().toUpperCase()+"%'";	      
	}
	else{  
		selectStmt = "SELECT company_id, konto_kupac,konto_dobavljac,company_name FROM "+ company.Main.Main.dbName+".komitenti where  ziro_racun='"+komitentZR+"' or company_name LIKE '%" + kom[0].toString().toUpperCase()+"%'";
	}
    
	String selectStmtAll="SELECT id, konto_kupac,konto_dobavljac,company_name FROM "+ company.Main.Main.dbName+".komitenti";
	
	  //Execute SELECT statement
      try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsKonto = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmt,"komitenti");
           
            if (!rsKonto.next() ) {
            	 rsKonto = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmtAll,"komitenti");
        	} 
            
            while(rsKonto.next()){
            	
            	data.add(rsKonto.getString("company_id"));
            	data.add(rsKonto.getString("company_name"));
          //  	if (duguje.equals("0.00")){
            	data.add(rsKonto.getString("konto_kupac"));
            //	}
            //	else{
            	data.add(rsKonto.getString("konto_dobavljac"));
          //  	}
            	
            
            	
            }
        } catch (SQLException e) {
        		System.out.println("SQL select operation has been failed: " + e);
      
        	}
      System.out.println("DATA ZA TABLEPOPUP");
      for(int i = 0; i < data.size(); i++) {   
    	    System.out.print(data.get(i)+" ");
    	}
  /*    for (int q=0; q<data.size();q++  ){
  	
      }*/
      
      return data;
	}

public static ArrayList<String> getSortedData(String key) throws IOException, ClassNotFoundException{
	ArrayList <String> sortedData =new ArrayList<String>();
	String selectStmtSort= "SELECT company_id, konto_kupac,konto_dobavljac,company_name FROM "+ company.Main.Main.dbName+".komitenti where  company_name LIKE '" +key.toUpperCase() +"%' or company_id LIKE '" +key.toUpperCase() +"%'" ;
	
	  try {
          //Get ResultSet from dbExecuteQuery method
          ResultSet rsKonto = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmtSort,"komitenti");
           
          while(rsKonto.next()){
          	
          	sortedData.add(rsKonto.getString("company_id"));
          	sortedData.add(rsKonto.getString("company_name"));
        //  	if (duguje.equals("0.00")){
          	sortedData.add(rsKonto.getString("konto_kupac"));
          //	}
          //	else{
          	sortedData.add(rsKonto.getString("konto_dobavljac"));
        //  	}
          	
          
          	
          }
      } catch (SQLException e) {
      		System.out.println("SQL select operation has been failed: " + e);
    
      	}
	  System.out.println("DATA ZA TABLEPOPUP");
      for(int i = 0; i < sortedData.size(); i++) {   
    	    System.out.print(sortedData.get(i)+" ");
    	}
	return sortedData;
}

public static String getKontoDev(String komit) throws ClassNotFoundException{
	String selectStmtKontoSifra= "SELECT company_id FROM "+ company.Main.Main.dbName+".komitenti where  company_name LIKE '" +komit.toUpperCase() +"%'";
	String kontoSifra="";
	  try {
        //Get ResultSet from dbExecuteQuery method
        ResultSet rsKonto = company.Main.DBConnection.DBConnection.dbExecuteQuery(selectStmtKontoSifra,"komitenti");
         
        while(rsKonto.next()){
        	
        	kontoSifra=rsKonto.getString("company_id");
        	
        }
    } catch (SQLException e) {
    		System.out.println("SQL select operation has been failed: " + e);
  
    	}
	return kontoSifra;
}


}

