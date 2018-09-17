package company.Main.Support.Print;

import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;

public class PrintNode {
	
	@SuppressWarnings("unused")
	public static void printNode(Node node) 
	{
		
		Printer printer = Printer.getDefaultPrinter();
		 PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
	   /*     double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
	        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
	        node.getTransforms().add(new Scale(scaleX, scaleY));
	*/	// Create the PrinterJob		
		PrinterJob job = PrinterJob.createPrinterJob();
		job.showPrintDialog(node.getScene().getWindow());
		if (job == null && job.showPrintDialog(node.getScene().getWindow())) 
		{		
		boolean proceed = job.printPage(pageLayout,node);
		
		if (proceed) 
		{
			job.endJob();
		
	}}}
	

}
