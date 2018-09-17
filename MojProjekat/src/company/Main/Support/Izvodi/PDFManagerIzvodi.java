package company.Main.Support.Izvodi;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
 
public class PDFManagerIzvodi {
    
   @SuppressWarnings("unused")
private PDFParser parser;
   private static PDFTextStripper pdfStripper;
   public static PDDocument pdDoc ;
   @SuppressWarnings("unused")
private static COSDocument cosDoc ;
   
   public static String Text ;
   private static String filePath;
   @SuppressWarnings("unused")
private File file;
 
    public PDFManagerIzvodi() {
        
    }
   public static String ToText() throws IOException, ClassNotFoundException
   {
       pdfStripper = null;
       pdDoc = null;
       cosDoc = null;
       int bs=0;
       
   /*    file = new File(filePath);
       parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0
       
       parser.parse();
       cosDoc = parser.getDocument();
       pdfStripper = new PDFTextStripper();
       pdDoc = new PDDocument(cosDoc);  */
       try{
       pdDoc = PDDocument.load(new File(filePath));  // ovo i
       pdfStripper = new PDFTextStripper();			// ovo je ubaceno umjesto gornjih 6 linija
       bs=pdDoc.getNumberOfPages();
       System.out.println("BROJ STRANA U PDF FAJLU JE: "+bs);
       pdfStripper.setStartPage(1);
       //pdfStripper.setEndPage(10);
       
       // reading text from page 1 to 10
       // if you want to get text from full pdf file use this code
       pdfStripper.setEndPage(pdDoc.getNumberOfPages());
       
       Text = pdfStripper.getText(pdDoc);
       
       return Text;
       }
       finally
       {
          if( pdDoc != null )
          {
        	  StringToSplit.splitString(Text, bs);
        	  pdDoc.close();
          }
          pdDoc.close();
       }
      
   }
 
    @SuppressWarnings("static-access")
	public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
   
}