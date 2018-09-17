package company.Main.Support.MySQLDump;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.stage.FileChooser;

public class MySQLDump {
	 /*NOTE: Creating Database Constraints*/
	private static String ip="localhost";
	private static String port="3306";
	private static String database=company.Main.Main.dbName;
	private static String user="root";
	private static String pass1="bakutaner";
	private static String pass2="luka";
	
    
	private static String path="C:\\MojProjekat\\BackupFiles";
	
	
	public static void exportData(String end) throws IOException, InterruptedException{
		createFolders();
		String pathLong= path+"/"+database;
		File f = new File (pathLong);
		f.mkdir();
		/*NOTE: Here the backup folder is created for saving inside it*/
		String savePath=pathLong+"/"+database+"."+getDate()+end;
		/*NOTE: Used to create a cmd command*/
		String dumpCommand = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass1+" -r "+savePath;
		/*NOTE: Here checking if command is execute */
		if (dumpCommandExecute(dumpCommand)==0) {
		        	company.Main.Main.showAlertInfo("BACKUP DATABASE", "Backup baze podataka je uspjesno zavrsen!");
		        } else {
		        	String dumpCommand1 = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass2+" -r "+savePath;
		        	if (dumpCommandExecute(dumpCommand1)==0) {
		   		        	company.Main.Main.showAlertInfo("BACKUP DATABASE", "Backup baze podataka je uspjesno zavrsen!");
		   		        }
		   		        else{
		   		        	company.Main.Main.showAlertError("BACKUP DATABASE ERROR", "Doslo je do greske prilikom arhiviranja podataka!");
		   		        }
		}
	}
	
	 /*NOTE: Executing the command here*/
	private static int dumpCommandExecute(String dumpCommand) throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
		Process child = rt.exec(dumpCommand);
		int processComplete = child.waitFor();
		return processComplete ;
	}
	
	private static void createFolders() {
		String path1= "C:\\MojProjekat";
		File f1 = new File(path1);
		f1.mkdir();
		
		String path2=path1+"\\BackupFiles";
		File f2=new File(path2);
		f2.mkdir();	
	}

	private static String getDate() {
		String today="";
		LocalDate date=LocalDate.now();
		today=""+ date;
		return today;
	}

	public static void importData () throws IOException, InterruptedException{
		String headerText="VAZNO!!! \nSvi korisnici programa, osim Vas, neka izadju iz programa.";
		String contentText="PREPORUKA: prvo uradite BACKUP podataka, zatim RESTORE.\nZelite li uraditi backup podataka?";
		int r =company.Main.Main.showAlertConfirmation(headerText, contentText, true);
		if (r==1){
			exportData("-beforeRESTORE.sql");
			chooseBackupFile();
		}
		if (r==2){
			System.out.println("GO WITHOIT BACKUP TO RESTORE");
			chooseBackupFile();
		}
	}
	

	private static void chooseBackupFile() throws IOException, InterruptedException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(
			new File("C:\\MojProjekat\\BackupFiles\\"+database+"\\")
	        );                 
	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("SQL files", "*.sql*")           
	        );
	    File file=fileChooser.showOpenDialog(company.Main.Main.stage);
		if(file==null) {	
			System.out.println("Niste izabrali fajl");
		}
		else{		
			System.out.println("Name of selected file is "+file.getName());
			String savePath=  file.getAbsolutePath();       // "C:/MojProjekat/BackupFiles/"+database+"/"+file.getName();
			System.out.println("SavePath= "+savePath);
			String dumpCommand="C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass2+" -e\"" + "source "+savePath+"\"";	
			if (dumpCommandExecute(dumpCommand)==0) {
		       	company.Main.Main.showAlertInfo("RESTORE PODATAKA", "Restore/povratak podataka je uspjesno zavrsen!");
		    } else {
		    		String dumpCommand1 = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass1+" -e\"" + "source "+savePath+"\"";
		        	if (dumpCommandExecute(dumpCommand1)==0) {
		   		       	company.Main.Main.showAlertInfo("RESTORE PODATAKA", "Restore/povratak podataka je uspjesno zavrsen!");
		   		    }
		   		    else{
		   		       	company.Main.Main.showAlertError("RESTORE DATA ERROR", "Doslo je do greske prilikom vracanja podataka!");
		   		    }
		    } 
			System.out.println("Zavrseno...");
			}
		}
		
		public static void findDateOfFirstMonday() throws IOException, InterruptedException {
			 LocalDate dateNow = LocalDate.now();
			 int y=dateNow.getYear();
			 int m=dateNow.getMonthValue();
			   LocalDate date=LocalDate.of(y,m, 1);
		        for(int i=0;i<date.lengthOfMonth();i++){
		            if("Monday".equalsIgnoreCase(date.getDayOfWeek().toString())){
		                break;
		            }else{
		                date=date.plusDays(1);
		            }
		        }
		        int d= date.getDayOfMonth();
		        LocalDate dateMonday = LocalDate.of(y, m, d);
		        System.out.println("Prvi ponedeljak u mjesecu je "+dateMonday);
		       
		        System.out.println("Danas je: "+ dateNow);
		        
		        if (dateNow.isEqual(dateMonday)|| dateNow.isAfter(dateMonday)) {
		        	System.out.println("Danas je prvi ponedeljak u mjesecu ili je ponedeljak vec bio");
		        	findFile(""+dateMonday);
		        }
		       
		}
		
		public static void findFile (String part) throws IOException, InterruptedException {
			File dir = new File ("C:\\MojProjekat\\BackupFiles\\"+company.Main.Main.dbName);
			System.out.println("Finde file za backup je: "+dir);
			dir.mkdirs();
			dir.isDirectory();
			if(dir.list().length >0){
				File[] matches=dir.listFiles(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String name) {				
					return name.endsWith(part+".sql");
				}				
			});
			//	System.out.println(matches.length);	
				if (matches.length==0 || matches==null) {
					automaticBackup(part);
				} else {
				System.out.println("Automatski backup prvog pondeljka u mjesecu je uradjen");
				}
			}else{
				automaticBackup(part);
			}
		}
		public static void automaticBackup(String part) throws IOException, InterruptedException{
			String savePath=path+"\\"+database+"\\"+database+"."+part+".sql";
			/*NOTE: Used to create a cmd command*/
			String dumpCommand = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass1+" -r "+savePath;
			/*NOTE: Here checking if command is execute */
			if (dumpCommandExecute(dumpCommand)==0) {
			        	company.Main.Main.showAlertInfo("AUTOMATSKI BACKUP DATABASE", "Backup baze podataka je uspjesno zavrsen!");
			        } else {
			        	String dumpCommand1 = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe " + database + " -h " + ip + " -P "+port+" -u " + user +" -p" + pass2+" -r "+savePath;
			        	if (dumpCommandExecute(dumpCommand1)==0) {
			   		        	company.Main.Main.showAlertInfo("AUTOMATSKI BACKUP DATABASE", "Backup baze podataka je uspjesno zavrsen!");
			   		        }
			   		        else{
			   		        	company.Main.Main.showAlertError("BACKUP DATABASE ERROR", "Doslo je do greske prilikom automatskog arhiviranja podataka!");
			   		        }
			}
		}
	}
	
	

