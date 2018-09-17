package company.Main.Support.Print;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.WritableImage;

public class ExportToPDF {

	public static void nodeToPdf (Node node){
	
		WritableImage image = node.snapshot(null, null);

	    // TODO: probably use a file chooser here
	    File file = new File("test.png");

	    try {
	        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
	    } catch (IOException e) {
	        // TODO: handle exception here
	    }
	   // String someImage = "C:/Users/Natasa/Desktop/mrBean.jpg" ;
		
	    try {
	    PDDocument document = new PDDocument();
	    FileInputStream in = new FileInputStream(file);
	    BufferedImage bimg = ImageIO.read(in);
	    float width = bimg.getWidth();
	    System.out.println("width je= "+width);
	    float height = bimg.getHeight();
	    System.out.println("height= "+height);
	    PDPage page = new PDPage(new PDRectangle(width, height));
	   // PDPage page = new PDPage(new PDRectangle(595, 842));
	    document.addPage(page); 

	    // if you already have the image in a BufferedImage, 
	    // call LosslessFactory.createFromImage() instead
	    // PDImageXObject pdImage = PDImageXObject.createFromFile(someImage, doc);
	    PDImageXObject img = LosslessFactory.createFromImage(document,bimg); // new PDImageXObject(document);
	    PDPageContentStream contentStream = new PDPageContentStream(document, page);
	    contentStream.drawImage(img, 0, 0);
	    contentStream.close();
	    in.close();

	    document.save("test.pdf");
	   
	    document.close();
	    
	    if (System.getProperty("user.dir") != null) {
         // File in user working directory, System.getProperty("user.dir");
         File file1 = new File("test.pdf");
         if (!file1.exists()) {
             // In JAR
             InputStream inputStream = ClassLoader.getSystemClassLoader()
                                 .getResourceAsStream("test.pdf");
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
         // Open file
         Desktop.getDesktop().open(file1);
     
 }
	    }catch (Exception e) {}

	
}
}
