package company.Main.DBConnection;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;


@SuppressWarnings("restriction")
public class DBConnection {
    //Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    //Connection
    private static Connection conn = null;
    private static Statement s=null;
    public static String nameDB=company.Main.Main.dbName;
    public static String winOS="";
    
    // username and password for connecting if DB name is not already specified
    // username of MySQL database
    private static String usernameDB="luka";
    // password of MySQL database
    private static String passwordDB="luka";
    
    /* Before connection we need to find name of Windows Platform (OS)
     * because the settings of connection depends on the Windows Platform  */
    public static void findNameOS(){
    	if(System.getProperty("os.name").equals("Windows XP")) {
    		winOS="";
    	}else{
    		winOS="?autoReconnect=true&useSSL=false";
    	}
    }
    
    /* Login if database is not created or not defined on file NameOfDataBase.txt  */
    public static void loginFirstConnection() throws SQLException, ClassNotFoundException {
    	int shNumber=connectWithoutDBname();	
      	if (shNumber==0){
      		try{
      			s=conn.createStatement();
          		int Result=s.executeUpdate("CREATE DATABASE "+company.Main.Main.dbName ); 
          		/*
          		 *  Maybe consider option that after database is created 
          		 * 	next step will be creating all tables ???????????
          		 *  Need to consider because backup/restore file from one to another database
          		 * 
          		 * */
          		if (Result>0) System.out.println("Date Base is Created!");
      		}catch (SQLException se) {
      			System.out.println(se.getMessage());
      			System.out.println(se.getErrorCode());	
      		}
      	}
    	dbConnect();
    }
    
    /* Connect without database name */
    private static int connectWithoutDBname() throws ClassNotFoundException, SQLException {
    	findNameOS();
    	DatabaseMetaData dbm=null;
    	ResultSet shemas=null;
    	try {
            Class.forName(JDBC_DRIVER);
          } catch (ClassNotFoundException e) {
              System.out.println("Where is your MySQL Driver?");
              e.printStackTrace();
              throw e;
          }
          System.out.println("MySQL Driver Registered!");
          try{
          	conn= DriverManager.getConnection("jdbc:mysql://localhost/"+winOS, "root", usernameDB);
          } catch (SQLException c) {
          	conn= DriverManager.getConnection("jdbc:mysql://localhost/"+winOS, "root", passwordDB);
          }
        
        dbm = conn.getMetaData();
        shemas= dbm.getCatalogs();
        int shNumber=0;
        System.out.println ("DB koju trazimo je "+company.Main.Main.dbName);
      	while(shemas.next()){
      		System.out.println("shemas: "+shemas.getString(1));	
      			if(company.Main.Main.dbName.toLowerCase().equals(shemas.getString(1))) {
      				System.out.println(company.Main.Main.dbName+" je jednako "+shemas.getString(1));
      				shNumber++;
      			}
      	} 
      
		return shNumber;
	}
    
	/* Connect to DB with defined name of DB */
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        //Setting Oracle JDBC Driver
    	findNameOS();
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("There is MySQL Driver missing");
            e.printStackTrace();
            throw e;
        }
        System.out.println("MySQL Driver Registered!");
      
        //Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ company.Main.Main.dbName+winOS, "root", usernameDB);
            System.out.println("Connect is succes!");
        } catch (SQLException ex){
        	
        try{
        	System.out.println("drugi pokusaj  "+company.Main.Main.dbName);
        	conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ company.Main.Main.dbName+winOS, "root", passwordDB);
            System.out.println("Connect is succes!");
        } catch (Exception eex){				
        	
        try{ 	System.out.println("treci pokusaj "+company.Main.Main.dbName);
        	conn = DriverManager.getConnection("jdbc:mysql://192.168.03/"+company.Main.Main.dbName+winOS, "root", passwordDB);
            System.out.println("Connect is succes!");
        	} catch (Exception eeex){
        		eeex.getMessage();
        	}
        }
        	}
        
    }
 
    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conection is closed!");
            }
        } catch (Exception e){
           throw e;
        }
    }
 
  //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt, String tableName) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
    	createTableIfNotExists(tableName) ;
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();           
            System.out.println("Select statement: " + queryStmt + "\n");
 
            //Create statement
            stmt = conn.createStatement();
 
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
 
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
            
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }
 
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static boolean dbExecuteUpdate(String sqlStmt, String tableName) throws SQLException, ClassNotFoundException {
    	createTableIfNotExists(tableName) ;
    	//Declare statement as null
        int st=0;
    	Statement stmt = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
           st=stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        if (st>0){
        	return true;
        }else{
        return false;
        }
    }
    
    /* Find boolean value depends if DB exist
     * Name of DB is set before */
    public static Boolean DBExists() throws ClassNotFoundException, SQLException{
    	int c=0;
    	c = connectWithoutDBname();
		if(c==0) {
		return false;
		}
		else{
		return true;
		}
    }
    
    /* Find if table (tableName) exist. If value is not, then create the table (tableName) */
	public static void createTableIfNotExists(String tableName) throws SQLException, ClassNotFoundException {
		dbConnect();
		DatabaseMetaData dbm = conn.getMetaData();
		// check if tableName table is there
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		if (tables.next()) {
			System.out.println("table "+tableName+" exists!"); // Table exists
		}
		else {
			System.out.println("table "+tableName+" NOT exists!"); // Table does not exist
			String stmtTable="";
			if (tableName.equals("korisnici")) {
				stmtTable="CREATE TABLE korisnici ("+
				//	Columns:
				"id_korisnik INT(11) AUTO_INCREMENT,"+ 
				"password varchar(45),"+ 
				"ime_korisnik varchar(45),"+ 
				"prezime_korisnik varchar(45),"+ 
				"jmbg varchar(13),"+ 
				"tek_racun varchar(45),"+ 
				"rodj varchar(45),"+ 
				"adresa varchar(60) ,"+
				"grad varchar(45),"+ 
				"opstina varchar(3),"+ 
				"sprema varchar(5) ,"+
				"zanimanje varchar(60),"+ 
				"radno_mjesto varchar(45),"+ 
				"tipVozila varchar(45),"+
				"regVozila varchar(45) ,"+
				"vlasnikVozila varchar(45),"+ 
				"zaposlen_od varchar(20) ,"+
				"prije_staz_god int(11) ,"+
				"prije_staz_mjes int(11) ,"+
				"prije_staz_dan int(11) ,"+
				"napomenaKor varchar(200),"+
				"PRIMARY KEY(id_korisnik))";	
			}
			
			if (tableName.equals("komitenti")){
				stmtTable="CREATE TABLE komitenti ("+
				//Columns:
				"company_id int(4) NOT NULL ,"+
				"company_name varchar(100), "+ 
				"street varchar(45),"+ 
				"city varchar(45),"+ 
				"post_code varchar(10),"+ 
				"jib varchar(13),"+ 
				"pdv varchar(12),"+ 
				"mb varchar(15),"+ 
				"director varchar(60) ,"+
				"sifra_pj varchar(4),"+ 
				"phone varchar(30),"+ 
				"fax varchar(30) ,"+
				"mobile varchar(30),"+ 
				"mail varchar(60),"+ 
				"web varchar(45),"+
				"ziro_racun varchar(45) ,"+
				"banka varchar(60) ,"+
				"dev_racun varchar(45),"+ 
				"iban varchar(30) ,"+
				"swift varchar(15) ,"+
				"entitet varchar(15) ,"+
				"konto_kupac varchar(6) ,"+
				"konto_dobavljac varchar(6) ,"+
				"PRIMARY KEY(company_id))";	
			}
			
			if (tableName.equals("putninalog")){
				stmtTable="CREATE TABLE putninalog ("+
				// Columns:
				"pravac varchar(200),"+
				"km varchar (10),"+
				"PRIMARY KEY (pravac))";
			}
			
			if (tableName.equals("companydata")) {
				stmtTable="CREATE TABLE companydata ("+
				//	Columns:
				"company_id int(4) NOT NULL ,"+
				"company_name varchar(100), "+ 
				"street varchar(45),"+ 
				"city varchar(45),"+ 
				"post_code varchar(10),"+ 
				"jib varchar(13),"+ 
				"pdv varchar(12),"+ 
				"mb varchar(15),"+ 
				"director varchar(60) ,"+
				"sifra_pj varchar(4),"+ 
				"phone varchar(30),"+ 
				"fax varchar(30) ,"+
				"mobile varchar(30),"+ 
				"mail varchar(60),"+ 
				"web varchar(45),"+
				"ziro_racun varchar(45) ,"+
				"banka varchar(60) ,"+
				"dev_racun varchar(45),"+ 
				"iban varchar(30) ,"+
				"swift varchar(15) ,"+
				"logo varchar(15) ,"+
				"sign varchar(15) ,"+
				"stamp varchar(15) ,"+
				"PRIMARY KEY(company_id))";	
			}
			if (tableName.equals("inbox")){
				stmtTable="CREATE TABLE inbox ("+
				// Columns:
				"id_address INT(11) AUTO_INCREMENT,"+
				"address varchar (60),"+
				"pass varchar(30),"+
				"id varchar (1),"+
				"PRIMARY KEY (id_address))";
			}
			// create table with update query
			System.out.println("Table "+tableName+" is created now...");
			Statement stmt=null;
			 try { 
		            //Connect to DB (Establish Oracle Connection)
		            dbConnect();
		            //Create Statement
		            stmt = conn.createStatement();
		            //Run executeUpdate operation with given sql statement
		            stmt.executeUpdate(stmtTable);
		        } catch (SQLException e) {
		            System.out.println("Problem occurred at executeUpdate operation : " + e);
		            throw e;
		        } finally {
		            if (stmt != null) {
		                //Close statement
		                stmt.close();
		            }
		            //Close connection
		            dbDisconnect();
		        }
			System.out.println("Table "+tableName+" is created!");
		}
	}
}
