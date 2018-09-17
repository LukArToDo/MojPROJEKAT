package company.Main.Support.Print;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import company.Main.Main;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class PrintMultiNode {
//	public static TableView <PrintPreview> table;
	public static TableColumn <PrintPreview,String> dugCol= new TableColumn<PrintPreview, String>("DUGUJE");
	public static TableColumn <PrintPreview,String> potCol = new TableColumn<PrintPreview, String>("POTRAZUJE");
	public static  Label ukDuguje=new Label();
	public static Label ukPotrazuje = new Label();
	public static double prefWidth;
	public static double prefHeight;
	public static String brojIz;
	public static String datumIz;
	public static List<String>listString;
	
	public static void printData(List<String>list, String broj, String datum, int pocInd,int zadInd ) 
	{
	int pInd=pocInd;
	int zInd=zadInd;
		System.out.println("ColumnData Promjena: "+list);
		listString=list;
		brojIz = broj;
		datumIz = datum;
		nodeToPrint(getData(pInd, zInd));
		
	}
public static ObservableList<PrintPreview> getData(int pocInd,int zadInd){
	ObservableList<PrintPreview> dataOL =FXCollections.observableArrayList();
	dataOL.clear();
	//List<String> lp =null;
	//lp = listString.subList(pocInd, zadInd);
	for (int i=pocInd; i<zadInd; i++){
		PrintPreview data = new PrintPreview();
		data.setPromj(listString.get(i));		
		data.setKont(listString.get(i+1));
		data.setKontSif(listString.get(i+2));
		data.setDug(listString.get(i+3));
		data.setPotr(listString.get(i+4));
		i=i+4;
		dataOL.add(data);
		}
	return dataOL;
}
	@SuppressWarnings({ "unchecked" })
	public static TableView<PrintPreview> setupTable (ObservableList<PrintPreview> ol){ 
	      
	      TableView <PrintPreview> table= new TableView<PrintPreview>();
	      table.setEditable(false);
	      table.setSelectionModel(null);
	      
	      
	      TableColumn <PrintPreview,String> opisCol = new TableColumn<PrintPreview, String>("OPIS");
	      opisCol.setMinWidth(237);  //287-40-20
	      opisCol.setMaxWidth(237); 
	      opisCol.setCellValueFactory(cellData -> cellData.getValue().PromjProperty());		      
	      opisCol.setSortable(false);
	      
	      
	      TableColumn <PrintPreview,String> kontoCol = new TableColumn<PrintPreview, String>("KONTO");
	      kontoCol.setMinWidth(50);
	      kontoCol.setMaxWidth(50);
	      kontoCol.setCellValueFactory(cellData -> cellData.getValue().KontProperty());
	      kontoCol.setStyle("-fx-alignment: CENTER-RIGHT;");
	      kontoCol.setSortable(false);
	      
	      TableColumn <PrintPreview,String> kontoSifraCol = new TableColumn<PrintPreview, String>("SIFRA");
	      kontoSifraCol.setMinWidth(40);
	      kontoSifraCol.setMaxWidth(40);	      
	      kontoSifraCol.setCellValueFactory(cellData -> cellData.getValue().KontSifProperty());
	      kontoSifraCol.setStyle("-fx-alignment: CENTER-RIGHT;");
	      kontoSifraCol.setSortable(false);
	      
	      dugCol.setMinWidth(80);
	      dugCol.setMaxWidth(80);
	      dugCol.setCellValueFactory(cellData -> cellData.getValue().DugProperty());
	      dugCol.setStyle("-fx-alignment: CENTER-RIGHT;");
	      dugCol.setSortable(false);
	      
	      potCol.setMinWidth(80);
	      potCol.setMaxWidth(80);
	      potCol.setCellValueFactory(cellData -> cellData.getValue().PotrProperty());
	      potCol.setStyle("-fx-alignment: CENTER-RIGHT;");
	      potCol.setSortable(false);
	      
	      table.setItems(ol);  
	      
	      table.getColumns().addAll(opisCol,kontoCol,kontoSifraCol,dugCol,potCol);
	      table.setMinWidth(prefWidth);
	      table.setTableMenuButtonVisible(false);		      
	      table.setFixedCellSize(15);
	      table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.30)));
	      table.minHeightProperty().bind(table.prefHeightProperty());
	      table.maxHeightProperty().bind(table.prefHeightProperty());
	      
	      return table;
	}
	
public static void nodeToPrint(ObservableList<PrintPreview> dataOL){
	/*	int index = 5;
		for (int i=0; i<index;i++){
		ObservableList<PrintPreview>pd= dataOL;
		}*/
	  	VBox finalBorder = new VBox();
		
		VBox border=new VBox();
		
		Printer printer = Printer.getDefaultPrinter();
		PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
	       prefWidth = pageLayout.getPrintableWidth(); // 487
	       prefHeight = pageLayout.getPrintableHeight(); // 734
	
	       ScrollPane sp=new ScrollPane();
		      sp.setContent(border);
		      sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		      sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		      sp.setFitToHeight(true);
		      sp.setFitToWidth(true);
		
		      
	      Label empty=new Label(" ");
	      Label labelBR = new Label("Broj naloga : 10 / "+brojIz);
	      Label labelDatum=new Label("Datum knjizenja : "+datumIz);
	      Label label = new Label("");
	      VBox zagl = new VBox();
	      zagl.getChildren().addAll(empty,labelBR,labelDatum, label);
	      VBox ispodVB=new VBox();
	      HBox ispodHB = new HBox();
	    
	      Label ukPromet=new Label();
	      ukPromet.setText("UKUPNO PROMET NALOGA: ");
	      Label razmak1=new Label("                                                                                         ");
	      Label razmak2=new Label("            ");	  
	      sumDugAndPot(setupTable(dataOL));
	      ispodHB.getChildren().addAll(ukPromet,razmak1,ukDuguje,razmak2,ukPotrazuje);
	      ispodVB.getChildren().addAll(new Label(""),ispodHB);
	      
	      border.getChildren().addAll(zagl,setupTable(dataOL),ispodVB);
	      border.setStyle("-fx-background-color: white ;");
	      border.getStylesheets().add(Main.class.getResource("Style/printPreview.css").toExternalForm());
	      border.setMinSize(prefWidth+3, prefHeight);
	      border.setMaxWidth(prefWidth+3);
	          
			MenuBar menuBar= new MenuBar();
			Menu file = new Menu("File");
			MenuItem printMI=new MenuItem("Print");
			file.getItems().add(printMI);
			menuBar.getMenus().add(file);
			
	      finalBorder.getChildren().addAll(menuBar,border,sp);
	      finalBorder.setMinSize(prefWidth+14, prefHeight);
	      finalBorder.setMaxWidth(prefWidth+14);
	      VBox.setVgrow(sp, Priority.ALWAYS);
	 //    company.Main.Support.ExportToPDF.nodeToPdf(border); // ide direktno u PDF printpreview, zato se donje linije visak 
		     
	       Scene scene=new Scene(new Group());
	       ((Group)scene.getRoot()).getChildren().addAll(finalBorder);
	       Stage printStage = new Stage();
	       printStage.setWidth(prefWidth+19);
	       printStage.setMinHeight(prefHeight);
	       printStage.setTitle("PrintPreview");
	       printStage.setScene(scene);		     
	       printStage.show();
	       
	       border.setFocusTraversable(true);
	       border.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ESCAPE) {
						dataOL.clear();
						listString.clear();
						printStage.close();						
						System.out.println("nakol ol.clear : "+ listString);
					}
					if (t.getCode() == KeyCode.P) {
						goPrint(printer, pageLayout, printStage, zagl, ispodVB);
					   
					}
				}
	      });

	       printMI.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent ev) {
				goPrint(printer, pageLayout, printStage, zagl, ispodVB);		
			}
	       });
	     
	// scrolovanje i zoomiranje sa klikom
	     
/*	 final double SCALE_DELTA = 1.1;
	    scene.setOnScroll(new EventHandler<ScrollEvent>() {
	        public void handle(ScrollEvent event) {
	            event.consume();

	            if (event.getDeltaY() == 0) {
	                return;
	            }

	            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;

	            border.setScaleX(border.getScaleX() * scaleFactor);
	            border.setScaleY(border.getScaleY() * scaleFactor);
	        }
	    });
*/
	    border.setOnMousePressed(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) {
	            if (event.getClickCount() == 2) {
	                border.setScaleX(1.0);
	                border.setScaleY(1.0);
	            }
	            else {   	
	            	border.setScaleX(0.8);
	            	border.setScaleY(0.8);
	            	
	            }
	        }
	    }); 
	    
	}   

public static void goPrint(Printer printer,PageLayout pageLayout,Stage printStage,VBox zagl,VBox podgl){
	int rI=43;
	double rD=(double)rI;
	int nRow=3+listString.size()/5;
	int nPages=nRow/rI;
	double np=nRow/rD;
	if(np>nPages){
		nPages=nPages+1;
	}
	
	PrinterJob job = PrinterJob.createPrinterJob();
	if (job == null) 
	{
		return;
	}

	// Show the print setup dialog
	boolean proceed = job.showPrintDialog(printStage);
	
	if (proceed) 
	{for (int p=0;p<nPages;p++){
		VBox printBorder=new VBox();	
		Label str = new Label("Strana "+(p+1)+" od "+ nPages);
		str.setMinWidth(prefWidth);
		str.setMaxWidth(prefWidth);
		str.setAlignment(Pos.CENTER_RIGHT);
		str.setStyle("-fx-font-size: 8px;");
		
		int pocInd=p*5*rI;
		int zadInd=((p+1)*rI*5)-1;
		if(zadInd>listString.size()-1){zadInd=listString.size();}
		if (p==0 && nPages>1){printBorder.getChildren().addAll(str,zagl,setupTable(getData(pocInd, zadInd))); }
		else if (p>0 && p<nPages-1){printBorder.getChildren().addAll(str,setupTable(getData(pocInd, zadInd))); }
		else if(p>1 && p==nPages-1){printBorder.getChildren().addAll(str,setupTable(getData(pocInd, zadInd)),podgl);}
		else { printBorder.getChildren().addAll(str,zagl,setupTable(getData(pocInd, zadInd)),podgl);
			}
		printBorder.getStylesheets().add(Main.class.getResource("Style/printPreview.css").toExternalForm());
		
		job.printPage(pageLayout,printBorder);
		
	}	
	job.endJob();
 }	
	nodeToPrint(getData(0, listString.size()));
	printStage.close();
	
}


	public static void sumDugAndPot(TableView<PrintPreview> tableView) {
		int numRows= dugCol.getTableView().getItems().size();
		ArrayList<Double> potrList = new ArrayList<Double>();
		ArrayList<Double> dugList = new ArrayList<Double>();
		double sumDug=0;
		double sumPot=0;
		for (int i=0; i<numRows; i++){
		String potrazuje=null;
		String duguje=null;
		double numPotr=0;
		double numDug=0;
		duguje=dugCol.getCellData(i).toString();
		potrazuje=potCol.getCellData(i).toString();
		
		duguje=duguje.replaceAll(",", "");
		potrazuje=potrazuje.replaceAll(",", "");
		
		numPotr=Double.parseDouble(potrazuje);
		numDug=Double.parseDouble(duguje);
		
		potrList.add(numPotr);
		dugList.add(numDug);
		}
		
		for(int i=0; i<potrList.size(); i++){
		    sumPot =sumPot+potrList.get(i);
		    sumDug =sumDug+dugList.get(i);	   
	}
		
		new DecimalFormatSymbols();
		// Ispis brojeva kao formatirani text
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.FRANCE);
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("###,##0.00", symbols);

		ukDuguje.setText(df.format(sumPot));
		ukPotrazuje.setText(df.format(sumDug));
		
	} 
}