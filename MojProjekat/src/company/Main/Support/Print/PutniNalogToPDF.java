package company.Main.Support.Print;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.scene.control.ChoiceDialog;


public class PutniNalogToPDF {
	public static ArrayList<String> data=new ArrayList<>();
	public static String someImage;
	public static int indexLogo=0;
	public static String userName = "C:/MojProjekat/Resources";
	
	public static void printPDF(String[] printData) {
		
		try{		              
		        String ime=printData[0];
		        String zvanje=printData[1];
		        String mjesta=printData[2];
		        String svrha=printData[3];
		        String tipVozila=printData[4];
		        String regist=printData[5];
		        String property=printData[6];
		        String brDana=printData[7];
		        String pravac=printData[8];
		        String dateGO=printData[9];
		        String tgh=printData[10];
		        String tgm=printData[11];;
		        String dateRET=printData[12];
		        String trh=printData[13];
		        String trm=printData[14];
		        String danDD=printData[15];
		        String iznosDD=printData[16];
		        String ukDD=printData[17];
		        String danID=printData[18];
		        String iznosID=printData[19];
		        String ukID=printData[20];
		        String dnevnice=printData[21];
		        String km=printData[22];
		        String gorivo=printData[23];
		        String naknada=printData[24];
		        String ostaliTR=printData[25];
		        String ukupniTroskovi=printData[26];
		        String datumObracuna = printData[27];
		        String nalog=printData[28];
		        
		        System.out.println("Nalog je: "+nalog);
		        if (nalog.isEmpty() || nalog.equals("")|| nalog.equals(" ")){
		        	nalog="";
		        } else {nalog="BROJ "+nalog;}
		        
		        
		        String racun1=printData[29];
		        String iznosR1=printData[30];
		        String racun2=printData[31];
		        String iznosR2=printData[32];
		        String racun3=printData[33];  
		        String iznosR3=printData[34];
		      
		        chooseComapnyData();
		        System.out.println("Create Simple PDF file with Text");
		        
		        if (!data.isEmpty()){
		        	indexLogo=0;
		        	if (data.get(7)!=null){
		        	
		        	someImage=userName+"/"+data.get(7);		
		        	 File file = new File(someImage);
				        if (!file.exists()){
				        	createImageFromText(data.get(0));
			        		someImage=userName+"/"+"text.png";	        		
				        }
		        }
		        }else{
		        	indexLogo=1;
		        	createBlankImage();
	        		someImage=userName+"/"+"text.png";	
		        }
		        
		        String fileName = "PutniNalog.pdf"; // name of our file
		        System.out.println("path to logo: "+someImage);
		        PDDocument doc = new PDDocument();
		       
		        
		        FileInputStream in = new FileInputStream(someImage);			// the right way for input image from project files is next line (InputStream):
		        
		    //  InputStream in = ResourceLoader.load(someImage);
		        BufferedImage bimg = ImageIO.read(in);
		        PDPage page = new PDPage(new PDRectangle(842, 595));
		        doc.addPage(page);
		        PDImageXObject img = LosslessFactory.createFromImage(doc,company.Main.Support.Image.ResizeImage.resizeImg(bimg,150,30));
		  
		        PDPageContentStream content = new PDPageContentStream(doc, page);

		        
/* ***************************************LEFT SIDE **************************************************** */
		        
		        
		        content.drawImage(img, 10, 545); 
		        
		        if (indexLogo==0){
		        	content.beginText();
		        	content.setFont(PDType1Font.HELVETICA, 9);
		        	content.newLineAtOffset(10, 530);
		        	content.showText(data.get(2));
		        	content.endText();
		        
		        	content.beginText();

		        	content.setFont(PDType1Font.HELVETICA, 8);
		        	content.newLineAtOffset(10, 520);
		        	content.showText("JIB: "+data.get(4));
		        	content.endText();
		        
		        	content.beginText();
		        	content.newLineAtOffset(10, 510);
		        	content.showText("PIB: "+data.get(5));
		        	content.endText();
		        
		        	content.beginText();
		        	content.newLineAtOffset(10, 500);
		        	content.showText("tel/fax: "+data.get(6));
		        	content.endText();
		        }
		        if (indexLogo==1){
		        	
		        	content.beginText();
				    content.setFont(PDType1Font.HELVETICA, 6);
				    content.newLineAtOffset(10, 580);
				    content.showText("Naziv predzeca i sjediste:");
				    content.endText();
				        
				    content.beginText();
				    content.setFont(PDType1Font.HELVETICA, 8);
				    content.newLineAtOffset(10, 510);
				    content.showText("JIB/PIB: ");
				    content.endText();
				        
				    content.beginText();
				    content.newLineAtOffset(10, 500);
				    content.showText("tel/fax: ");
				    content.endText();  	
		       	
		        }
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
		        content.newLineAtOffset(60, 460);
		        content.showText("NALOG ZA SLUZBENO PUTOVANJE");
		        content.endText();
		        
		      
		        content.beginText();
				content.setFont(PDType1Font.HELVETICA_BOLD, 12);
				content.newLineAtOffset(140, 445);
				content.showText(nalog);
				content.endText();
		       
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(10, 400);
		        content.showText("Upucuje se _________________________ zvanje-polozaj ________________");
		        content.endText();
		       
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 11);
		        content.newLineAtOffset(60, 400);
		        content.showText(ime.toUpperCase());
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 7);
		        content.newLineAtOffset(80, 392);
		        content.showText("ime i prezime radnika");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(247, 400);
		        content.showText(zvanje);
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(10, 372);
		        content.showText("na sluzbeni put u sljedeca mjesta:    __________________________________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(160, 372);
		        content.showText(mjesta);
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(10, 352);
		        content.showText("Sluzbeno putovanje ima za cilj:    ____________________________________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(160, 352);
		        content.showText(svrha);
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(10, 320);
		        content.showText("Gore imenovani, u svrhe sluzbenog putovanja, koristi se PRIVATNIM putnickim");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(10, 300);
		        content.showText("vozilom tipa _______________, reg.broj__________, u vlasnistvu___________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(60, 300);
		        content.showText(tipVozila);
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(173, 300);
		        content.showText(regist);
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(275, 300);
		        content.showText(property);
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(10, 280);
		        content.showText("Troskovi sluzbenog putovanja padaju na teret preduzeca.");
		        content.endText();
		           
		        content.beginText();
		        content.newLineAtOffset(10, 260);
		        content.showText("Putovanje ce trajati: _______");
		        content.endText();
		          
		        content.beginText();
		        content.newLineAtOffset(92, 260);
		        content.showText(brDana+" dan(a)");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(10, 230);
		        content.showText("Pravac putovanja:   "+pravac.toUpperCase());
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(150, 120);
		        content.showText("M.P.            _____________________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 7);
		        content.newLineAtOffset(235, 112);
		        content.showText("direktor");
		        content.endText();

		        
/* ***************************************RIGHT SIDE **************************************************** */
		        
		        

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
		        content.newLineAtOffset(450, 580);
		        content.showText("( Popunjava racunovodstvena sluzba: )");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(450, 580);
		        content.showText("_______________________________");
		        content.endText();

		        content.beginText();
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 572);
		        content.showText("____________________________________________________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(450, 560);
		        content.showText("(1) Vrijeme provedeno na putovanju :");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(450, 540);
		        content.showText("- Vrijeme polaska:    dana     "+dateGO+"   godine     u       "+ tgh+"h   "+tgm+"min" );
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(530, 540);
		        content.showText("_______________________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(675, 540);
		        content.showText("___________");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(450, 520);
		        content.showText("- Vrijeme dolaska:    dana     "+dateRET+"   godine     u       "+ trh+"h   "+trm+"min" );
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(530, 520);
		        content.showText("_______________________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(675, 520);
		        content.showText("___________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.newLineAtOffset(450, 500);
		        content.showText("  UKUPNO vrijeme provedeno na sluzbenom putovanju :         "+brDana+" dan(a)");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(710, 500);
		        content.showText("________");
		        content.endText();
		        

		        content.beginText();
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 490);
		        content.showText("____________________________________________________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(450, 470);
		        content.showText("(2) Troskovi dnevnica :");
		        content.endText();


		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(450, 450);
		        content.showText("(dom.dnevnice) :            "+danDD+"  dan(a)            X          "+iznosDD+"          =       "+ukDD);
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(520, 450);
		        content.showText("__________________________________________________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 7);
		        content.newLineAtOffset(520, 442);
		        content.showText("(vrijeme provedeno na putu)         (iznos dnevnice KM)      (ukupno dom.dnevnice)");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(450, 420);
		        content.showText("(ino.dnevnice)   :            "+danID+"  dan(a)            X          "+iznosID+"          =       "+ukID);
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(520, 420);
		        content.showText("__________________________________________________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 7);
		        content.newLineAtOffset(520, 412);
		        content.showText("(vrijeme provedeno na putu)         (iznos dnevnice EUR)     (ukupno ino.dnevnice)");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.newLineAtOffset(615, 390);
		        content.showText("UKUPNO DNEVNICE :     "+dnevnice+"   KM");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(715, 390);
		        content.showText("___________");
		        content.endText();
		        content.beginText();
		        content.newLineAtOffset(715, 389);
		        content.showText("___________");
		        content.endText();

		        content.beginText();
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 380);
		        content.showText("____________________________________________________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(450, 360);
		        content.showText("(3) Naknada za koristenje privatnog vozila :");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(450, 340);
		        content.showText("Ukupno predjeni kilometri :              "+km+" km");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(560, 340);
		        content.showText("______________");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(450, 325);
		        content.showText("Cijena goriva po litri          :              "+gorivo+" KM/lit");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(560, 325);
		        content.showText("______________");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(450, 310);
		        content.showText("Koeficijent za obracun      :  0.20  (20% od cijene goriva)");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(560, 310);
		        content.showText("____");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.newLineAtOffset(450, 290);
		        content.showText("UKUPNO naknada          :");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.newLineAtOffset(570, 290);
		        content.showText(km+"  X  "+gorivo+"  X  0.20              =");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(560, 290);
		        content.showText("_________________________________");
		        content.endText();


		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.newLineAtOffset(717, 290);
		        content.showText(naknada+"   KM");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(705, 290);
		        content.showText("_____________");
		        content.endText();
		        content.beginText();
		        content.newLineAtOffset(705, 289);
		        content.showText("_____________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 280);
		        content.showText("____________________________________________________");
		        content.endText();
		    
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(450, 260);
		        content.showText("(4) Ostali troskovi (racuni u prilogu) :");
		        content.endText();
		        
		        	content.beginText();
				    content.setFont(PDType1Font.HELVETICA, 9);
				    content.newLineAtOffset(450, 242);
				    content.showText(" "+setingString(racun1));
				    content.endText();
				        
				    content.beginText();
				    content.newLineAtOffset(735, 242);
				    content.showText(" "+setingString(iznosR1));
				    content.endText();
				        
		        content.beginText();
		        content.newLineAtOffset(450, 240);
		        content.showText("________________________________________________________________");
		        content.endText();

        	    	content.beginText();
        	    	content.newLineAtOffset(450, 227);
        	    	content.showText(" "+setingString(racun2));
        	    	content.endText();
		        
        	    	content.beginText();
        	    	content.newLineAtOffset(735, 227);
        	    	content.showText(" "+setingString(iznosR2));
        	    	content.endText();

		        content.beginText();
		        content.newLineAtOffset(450, 225);
		        content.showText("________________________________________________________________");
		        content.endText();

        	    	content.beginText();
        	    	content.newLineAtOffset(450, 212);
        	    	content.showText(" "+setingString(racun3));
        	    	content.endText();
		        
        	    	content.beginText();
        	    	content.newLineAtOffset(735, 212);
        	    	content.showText(" "+setingString(iznosR3));
        	    	content.endText();
		          
		        content.beginText();
		        content.newLineAtOffset(450, 210);
		        content.showText("________________________________________________________________");
		        content.endText();

		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.newLineAtOffset(605, 190);
		        content.showText("UKUPNO ostali troskovi :     "+ostaliTR+"   KM");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(715, 190);
		        content.showText("___________");
		        content.endText();
		        content.beginText();
		        content.newLineAtOffset(715, 189);
		        content.showText("___________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 180);
		        content.showText("____________________________________________________");
		        content.endText();
		    
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(505, 160);
		        content.showText("UKUPNI TROSKOVI SLUZBENOG PUTOVANJA :   "+ukupniTroskovi+"  KM");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(715, 160);
		        content.showText("___________");
		        content.endText();
		        content.beginText();
		        content.newLineAtOffset(715, 159);
		        content.showText("___________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(555, 140);
		        content.showText("- isplacena akontacija putnih troskova :        0.00  KM");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(715, 140);
		        content.showText("___________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA_BOLD, 9);
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(610, 115);
		        content.showText("UKUPNO ZA ISPLATU :    "+ukupniTroskovi+"  KM");
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(715, 115);
		        content.showText("___________");
		        content.endText();
		        content.beginText();
		        content.newLineAtOffset(715, 114);
		        content.showText("___________");
		        content.endText();
		        
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 9);
		        content.setCharacterSpacing(2);
		        content.newLineAtOffset(450, 95);
		        content.showText("____________________________________________________");
		        content.endText();
		    
		        content.beginText();
		        content.setCharacterSpacing(0);
		        content.newLineAtOffset(450, 70);
		        content.showText("U Banjoj Luci, dana   "+ datumObracuna);
		        content.endText();

		        content.beginText();
		        content.newLineAtOffset(530, 70);
		        content.showText("___________");
		        content.endText();
		          
		        content.beginText();
		        content.setFont(PDType1Font.HELVETICA, 7);
		        content.newLineAtOffset(460, 40);
		        content.showText("____________                                          ____________                                         ____________");
		        content.endText();
		        
		        content.beginText();
		        content.newLineAtOffset(460, 30);
		        content.showText("  obracunao                                                     isplatio                                                      primio");
		        content.endText();
		        
		   
/* ****************************************************************************************************** */		        
		         
		        content.close();
		        in.close();
		        doc.save(fileName);
		        doc.close();
		        
		        System.out.println("your file created in : "+ userName);
		       
		        if (userName != null) {
		            // File in user working directory, System.getProperty("user.dir");
		            File file1 = new File(fileName);
		            if (!file1.exists()) {
		                // In JAR
		                InputStream inputStream = ClassLoader.getSystemClassLoader()
		                                    .getResourceAsStream(fileName);
		                // Copy file
		                OutputStream outputStream = new FileOutputStream(file1);
		                byte[] buffer = new byte[1024];
		                int length;
		                while ((length = inputStream.read(buffer)) > 0) {
		                    outputStream.write(buffer, 0, length);
		                }
		                outputStream.close();
		                inputStream.close();
		            }
		        Desktop.getDesktop().open(file1);
		        
		        }
		}
		        catch(Exception e){
		        System.out.println(e.getMessage());
		        }		        
	}
	
	public static void chooseComapnyData() throws ClassNotFoundException, SQLException{
		
		List<String> choices = new ArrayList<>();	
		List<String> others = new ArrayList<>();
		String queryStmt="SELECT * FROM "+company.Main.Main.dbName+".companydata";
		ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery(queryStmt, "companydata");
		while (rs.next()){
		choices.add(rs.getString("company_name"));
		others.add(rs.getString("street"));
		others.add(rs.getString("city"));
		others.add(rs.getString("post_code"));
		others.add(rs.getString("jib"));
		others.add(rs.getString("pdv"));
		others.add(rs.getString("fax"));
		others.add(rs.getString("logo"));
		}
		if (!choices.isEmpty()){
		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		//dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("IZBOR PREDUZECA / POSLOVNE JEDINICE");
		dialog.setContentText("Izaberite preduzece / PJ:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    
		    
		    System.out.println("Your choice: " + result.get());
		}
		
		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(letter -> System.out.println("Your choice: " + letter));
		int index = choices.indexOf(result.get());
		data.clear();
		data.add(result.get());
		data.add(others.get(index*7+0));
		data.add(others.get(index*7+1));
		data.add(others.get(index*7+2));
		data.add(others.get(index*7+3));
		data.add(others.get(index*7+4));
		data.add(others.get(index*7+5));
		data.add(others.get(index*7+6));
		System.out.println("Povratni array list: "+data);
	
		} else{
			company.Main.Main.showAlertError("NAPOMENA!!!", "Niste unijeli podatke za preduzece.");
		}
	}
	
	public static String setingString(String racunIznos){
		if (racunIznos.isEmpty() || racunIznos.equals("")|| racunIznos.equals(" ")){
			racunIznos="";
        } 
		return racunIznos;
	}

	public static File createImageFromText(String text){
		  BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = img.createGraphics();
	        Font font = new Font("HELVETICA", Font.PLAIN, 48);
	        g2d.setFont(font);
	        FontMetrics fm = g2d.getFontMetrics();
	        int width = fm.stringWidth(text);
	        int height = fm.getHeight();
	        g2d.dispose();

	        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        g2d = img.createGraphics();
	        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	        g2d.setFont(font);
	        fm = g2d.getFontMetrics();
	        g2d.setColor(Color.BLACK);
	        g2d.drawString(text, 0, fm.getAscent());
	        g2d.dispose();
	        try {
	            ImageIO.write(img, "png", new File(userName+"/"+"text.png"));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	
	public static File createBlankImage(){
		  BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = img.createGraphics();  
	        int width = 150;
	        int height = 30;
	        g2d.dispose();
	        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        g2d = img.createGraphics();     
	//        g2d.setColor(Color.WHITE);
	        g2d.setComposite(AlphaComposite.Clear);
	        g2d.fillRect(0, 0, 150, 30);
	        g2d.dispose();
	        try {
	            ImageIO.write(img, "png", new File(userName+"/"+"text.png"));
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
		return null;
	}
	
final public static class ResourceLoader {

	public static InputStream load(String path){
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input ==null){
			input=ResourceLoader.class.getResourceAsStream("/"+path);
			
		}
		
		return input;
		
	}
}


}