package company.Main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.LogManager;

import company.Main.KomitentAndKontoTable.KomitentAndKontoTableController;
import company.Main.Support.Izvodi.StringToSplit;
import javafx.util.Duration;


public class Main extends Application {

public static String dbName;
public static AnchorPane center;
public static BorderPane mainPane;
public static BorderPane popUp;
public static VBox leftView;
private static Scene scene;
public static Stage stage;
public static Stage dialogStage = new Stage();
public static Stage izvodStage=new Stage();
public static Stage calcStage = new Stage();
public static String styleAlert;
public static String key="";
public static String firstPass=null;

    @Override
    public void start(Stage stage) throws InterruptedException {
        //1) Declare a primary stage (Everything will be on this stage)
        Main.stage = stage;    
        //2) Initialize RootLayout
        initRootLayout();
        dbName=getDBname();
    }
    
    // 3) Get (or create) name of database
    private String getDBname() throws InterruptedException {
    	String name="";
    	File mp=new File("C:\\MojProjekat");
    	mp.mkdir();
    	
    	String path="C:\\MojProjekat\\Resources\\DataBaseName.txt";
    try {
    	File user = new File("C:\\MojProjekat\\Resources");
    
    	if (!user.exists()){
    		System.out.println("Kreiramo skriveni folder: Resources");
    		 Process p=Runtime.getRuntime().exec("attrib +h C:\\MojProjekat\\Resources");
             p.waitFor();
    	}
    	
   		 File file = new File (path);
   		 
   		 if (!file.exists()) {
   			 
		 	TextInputDialog dialog = new TextInputDialog("database name");
		 	dialog.setTitle("Kreiranje DB");
		 	dialog.setHeaderText("KREIRAJTE BAZU PODATAKA!");
		 	dialog.setContentText("Molimo Vas unesite naziv baze podataka:");
		 	dialog.initStyle(StageStyle.UNDECORATED);
		
		 	// Traditional way to get the response value.
		 	Optional<String> result = dialog.showAndWait();
		 	if (result.isPresent()){
		 		System.out.println("Your name: " + result.get());
		 		file.getParentFile().mkdirs(); 
		 		file.createNewFile();
		 		FileWriter writer = new FileWriter(path, true);
		 		BufferedWriter bf = new BufferedWriter(writer);
			
		 		bf.write(result.get());
		 		bf.close();
		 		dbName=result.get();
		 		if (!company.Main.MainView.Login.LoginViewController.checkString(dbName)){
		 			showAlertDB();
		 		}
		 		company.Main.DBConnection.DBConnection.loginFirstConnection();
		 	} else {
		 		System.exit(0);
		 	}
		 
		 	if (firstPass==null){
		 		TextInputDialog pass = new TextInputDialog("");
		 		pass.setTitle("Ulazni password");
		 		pass.setHeaderText("Oavj password koristi se samo pri prvom ulazu. \nMolimo Vas da po ulasku u program OBAVEZNO kreirate \nkorisnicki password u sifarniku 'Korisnici'");
		 		pass.setContentText("Ulazni password: ");
		 		pass.initStyle(StageStyle.UNDECORATED);
		 		Optional<String> res= pass.showAndWait();
		 		if(res.isPresent()){
		 			firstPass=res.get();
		 		}
		 	}
   		 }
   		 FileReader reader = new FileReader(path);    
   		 int character; 
   		 while ((character = reader.read()) != -1) {
   		 	name=name+((char) character);
   		 }
   		 reader.close();       
   		 System.out.println("Reader: "+ name);
		 
   		 Process p=Runtime.getRuntime().exec("attrib +h C:/MojProjekat/Resources");
   		 p.waitFor();
   
     } catch (IOException | ClassNotFoundException | SQLException e) {
    	 e.printStackTrace();
    	 e.getMessage();
     } 
	return name;
	}
    
    // show AlertError dialog if creating DB is not correct 
    private void showAlertDB() throws InterruptedException {
    	showAlertError("GRESKA!!!","Naziv baze podataka ne mogu ciniti samo brojevi.\nMolimo Vas pokusajte ponovo.");
    	String path="C:\\MojProjekat\\Resources\\DataBaseName.txt";
    	File f = new File(path);
    	f.delete();
    	getDBname();	
	}
    
	public static void setNameOfDB(String name){
    	dbName=name;
    }
	
	public void initRootLayout() {
        try {   
        mainPane=new BorderPane();   
        mainPane.isResizable(); 
        mainPane.getStylesheets().add(Main.class.getResource("Style/ProbaStyle.css").toExternalForm());
    
        Image icon = new Image(Main.class.getResourceAsStream("Images/icon.png"));
        String image = Main.class.getResource("Images/greyM.jpg").toExternalForm();
        mainPane.setStyle("-fx-background-image: url('" + image + "'); " +
                   "-fx-background-position: center center; " +
                   "-fx-background-repeat: stretch;");
        
        changeCenterMain("MainView/CenterView.fxml");
        changeLeftMain("MainView/LeftMainFirst.fxml");
        changeRightMain("MainView/TopIcon.fxml");
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {  
            @Override  
            public void handle(KeyEvent keyEvent) {  
                if (keyEvent.getCode().equals(KeyCode.F12)) {  
                	try {
						VBox paneStyle = FXMLLoader.load(Main.class.getResource("Style/StyleMenu.fxml"));
						mainPane.setRight(paneStyle);
					} catch (IOException e) {
						e.printStackTrace();
					} 
                }   if (keyEvent.getCode().equals(KeyCode.F11)) {  
                	try {
						showCalc();
					} catch (IOException e) {
						e.printStackTrace();
					} 
                }  
                
            }  
        });  
  
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        mainPane.setMinWidth(primaryScreenBounds.getWidth());
        mainPane.setMaxWidth(primaryScreenBounds.getWidth());
        mainPane.setMinHeight(primaryScreenBounds.getHeight());
        mainPane.setMaxHeight(primaryScreenBounds.getHeight());
        
        scene = new Scene(mainPane);  
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.setTitle("MOJ PROJEKAT");
        stage.initStyle(StageStyle.TRANSPARENT);

       
        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        stage.show();                  	
	
        izvodStage.initStyle(StageStyle.TRANSPARENT);
        izvodStage.initModality(Modality.WINDOW_MODAL);
        izvodStage.initOwner(stage);
        
        calcStage.initStyle(StageStyle.TRANSPARENT);
        
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
	
    public static void changeCenterMain(String fxmlCenter) throws IOException{
	  center = FXMLLoader.load(Main.class.getResource(fxmlCenter));
	  mainPane.setCenter(center);
	  fadeTransition(center);
    }
    
  	public static void changeStyleMenu(String imageChange, String styleChange) throws IOException{
	  String image1 = Main.class.getResource(imageChange).toExternalForm();
    
      mainPane.setStyle("-fx-background-image: url('" + image1 + "'); " +
                 "-fx-background-position: center center; " +
                 "-fx-background-repeat: stretch;");
      
      mainPane.getStylesheets().clear();
      setUserAgentStylesheet(null);
      mainPane.getStylesheets().add(Main.class.getResource(styleChange).toExternalForm());
      styleAlert=styleChange;
  	}
  
  	public static void changeLeftMain (String fxmlLeft) throws IOException{
  		 leftView=FXMLLoader.load(Main.class.getResource(fxmlLeft));
  		 
  		 leftView.setOnMouseExited(new EventHandler<MouseEvent>() {
  			 public void handle(MouseEvent me) {
				TranslateTransition swipeTransition = new TranslateTransition();
		    	swipeTransition.setNode(leftView);
		    	swipeTransition.setDuration(Duration.millis(500));
		    	swipeTransition.setToX(leftView.getPrefWidth()-150);
		    	leftView.setOpacity(0);
		    	swipeTransition.play();
		       }
  		 });
  			
  		 leftView.setOnMouseEntered(new EventHandler<MouseEvent>() {       
  			 public void handle(MouseEvent me) {	
				TranslateTransition swipeTransition = new TranslateTransition();
				swipeTransition.setNode(leftView);
				swipeTransition.setDuration(Duration.millis(500));
				swipeTransition.setToX(leftView.getPrefWidth()-150);
				leftView.setOpacity(1.0);
				swipeTransition.play();
				
  			 }
  		 });
	       
			Main.mainPane.setLeft(leftView);	
			fadeTransition(leftView);
			
		
  	}
    
  	private static void fadeTransition(Node e){
  		FadeTransition x=new FadeTransition(new Duration(1000),e);
  		x.setFromValue(0);
  		x.setToValue(100);
  		x.setCycleCount(1);
  		x.setInterpolator(Interpolator.LINEAR);
  		x.play();
  		
  	}
  	
  	public static void changeRightMain (String fxmlLeft) throws IOException{
  		VBox rightView=FXMLLoader.load(Main.class.getResource(fxmlLeft));
  		Main.mainPane.setRight(rightView);
  	}
  	 
  	public static void showAlertError(String headerText, String contentText){
		Alert alert=new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setTitle("Greška");
		alert.initOwner(stage);
	    alert.initModality(Modality.WINDOW_MODAL);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.getDialogPane().getStylesheets().add(Main.class.getResource("Style/ProbaStyle.css").toExternalForm());
	
		alert.showAndWait();
  	}
  	
  	public static void showAlertInfo(String headerText, String contentText){
		Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setTitle("INFORMATION");
		alert.initOwner(stage);
	    alert.initModality(Modality.WINDOW_MODAL);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.getDialogPane().getStylesheets().add(Main.class.getResource("Style/ProbaStyle.css").toExternalForm());
	
		alert.showAndWait();
  	}
  	
 	public static int showAlertConfirmation(String headerText, String contentText, boolean b){
		Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setTitle("RESTORE PODATAKA");
		alert.initOwner(stage);
	    alert.initModality(Modality.WINDOW_MODAL);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.getDialogPane().getStylesheets().add(Main.class.getResource("Style/ProbaStyle.css").toExternalForm());
		
		if (b){
		ButtonType buttonTypeOne = new ButtonType("DA");
		ButtonType buttonTypeTwo = new ButtonType("NE");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo,  buttonTypeCancel);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		    return 1;
		} else if (result.get() == buttonTypeTwo) {
		   return 2;
		} else {
		   return 0;
		}	
		}
		
		else{
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			  return 1;
			} else {
			  return 0;
			}
		}
  	}
 	
	public static void showPopUpWindow(String popUpFxml, String popUpCSS) throws IOException {
	
		BorderPane popUp= FXMLLoader.load(Main.class.getResource(popUpFxml));
		popUp.setStyle("-fx-background-color: white;");
	//	dialogStage.initModality(Modality.WINDOW_MODAL);  // samo ovaj popUp je aktivan
	//  dialogStage.initOwner(stage); // ide uz Modality.Window_modal
	    Scene scene = new Scene(popUp);
	    scene.getStylesheets().add(Main.class.getResource(popUpCSS).toExternalForm());
	    dialogStage.setScene(scene);
	    dialogStage.setTitle("Print PREVIEW");
	    dialogStage.show();    
	}
	
	public static void showCalc() throws IOException {		
		AnchorPane calc = FXMLLoader.load(Main.class.getResource("Support/Calculator/Calculator.fxml"));
	    Scene scene = new Scene(calc);
	    calcStage.setScene(scene);
	    calcStage.setX(stage.getWidth()-230);
	    calcStage.setY(30);
	    calcStage.setAlwaysOnTop(true);
	    calcStage.show();    
	}
	
	public static void showPopUp(String popUpFxml, String title) throws IOException {
		AnchorPane pUp= FXMLLoader.load(Main.class.getResource(popUpFxml));
	    Scene scene = new Scene(pUp);
	    dialogStage.setScene(scene);
	    dialogStage.setTitle(title);
	    dialogStage.show();    
	}
	
	public static ArrayList<String> showPopUpKonto(String popUpFxml) throws IOException {
		ArrayList<String> passList=new ArrayList<String>();
		passList.clear();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(Main.class.getResource(popUpFxml));
		AnchorPane popUp= (AnchorPane) loader.load();
	
	    Scene scene = new Scene(popUp);
	//  scene.getStylesheets().add(Main.class.getResource(popUpCSS).toExternalForm());
	    dialogStage.setScene(scene);
	    KomitentAndKontoTableController controller=loader.getController();
	    dialogStage.setTitle("Konto/komitent");
	    controller.setDialogStage(dialogStage);
	    dialogStage.showAndWait();
	    passList.addAll(controller.passArray(null));
	
	    return passList;
	}
	
	public static void getIzvod(String popUpIzvodFxml/*, String popUpIzvodStyle*/) throws IOException {
		AnchorPane popUpIzvod = FXMLLoader.load(Main.class.getResource(popUpIzvodFxml));
        popUpIzvod.setBackground(Background.EMPTY);
      
        TextArea ta=new TextArea();  
        ta.setMinHeight(700);
        ta.setMaxHeight(700);
        ta.setPrefWidth(750);
        ta.setLayoutX(75);
        ta.setLayoutY(50);
        ta.setText(company.Main.Support.Izvodi.PDFManagerIzvodi.Text);
        popUpIzvod.getChildren().add(ta);
        
        Scene scene = new Scene(popUpIzvod); 
        scene.setFill(Color.TRANSPARENT);
   //   scene.getStylesheets().add(Main.class.getResource(popUpIzvodStyle).toExternalForm());
        
        izvodStage.setScene(scene); 
        izvodStage.show(); 
       
        ta.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode()==KeyCode.ESCAPE) {	
					StringToSplit.clearAllArrayLists();
					closePopUpIzvod();	
					
                    } 
                
				if (t.getCode() == KeyCode.ENTER || t.getCode()==KeyCode.SPACE) {
				closePopUpIzvod();
					try {
						changeCenterMain("MainView/Login/Ostalo/Izvodi/TableIzvodKM.fxml");
					} catch (IOException e) {
						e.printStackTrace();
					}        
				}	
			}
        });
	} 
	
	public static void closeStage(){
		stage.close();
	}
	
	public static void closePopUpIzvod (){
		izvodStage.close();
	}
	
	public static void closePopUpWindow (){
		dialogStage.close();
	}
	
	public static void closeCalc (){
		calcStage.close();
	}
	

/* ************************************************************************************************** */
/* ************************************************************************************************** */
/* ************************************************************************************************** */
	
	public static void main(String[] args) {
	 // Suppress the warning message when the Add button is clicked.
	    LogManager.getLogManager().reset();
	    launch(args);
	    }
}
