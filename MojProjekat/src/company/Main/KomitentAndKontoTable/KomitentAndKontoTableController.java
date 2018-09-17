package company.Main.KomitentAndKontoTable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import company.Main.Support.Print.PrintPreview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KomitentAndKontoTableController implements Initializable {
	@FXML
	private AnchorPane popUPKomitentKonto;
	@FXML
	public Tab tabKomitenti;
	@FXML
	public TableView<PrintPreview>tableKomitenti;
	@FXML
	private TableColumn<PrintPreview,String> colSifraKomitent;
	@FXML
	private TableColumn<PrintPreview,String> colNazivKomitent;
	@FXML
	private TableColumn <PrintPreview,String>colKontoKupac;
	@FXML
	private TableColumn<PrintPreview,String> colKontoDob;
	@FXML
	private Tab tabKonta;
	@FXML
	private TableView<?> tableKonto;
	@FXML
	private TableColumn<?, ?> colKonto;
	@FXML
	private TableColumn<?, ?> colSifra;
	@FXML
	private TableColumn<?, ?> colOpisKonto;
	
	private static ObservableList<PrintPreview> data=FXCollections.observableArrayList();
	public static ArrayList<String> dataString=new ArrayList<>();
	public static ArrayList<String> exportData = new ArrayList<>();
	public static String rowString;
	private Stage dialogStage;
	public static String key;
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage=dialogStage;
	}
	
	public static void  setupTable (ArrayList<String> newdataString) throws IOException{
		
		dataString.clear();
		data.clear();
		
		dataString.addAll(newdataString);
		rowString=dataString.get(0).toString();
		//ObservableList<PrintPreview> data=FXCollections.observableArrayList();
		for (int i=1; i<dataString.size();i++){
			System.out.println("PODACI U TABLEPOPUP iz ARRAY su:"+dataString.get(i).toString()+","+dataString.get(i+2).toString());
			PrintPreview dt=new PrintPreview();
			dt.setKomit(dataString.get(i+1).toString());
			dt.setKontSif(dataString.get(i).toString());
			dt.setKont(dataString.get(i+2).toString());
			dt.setPromj(dataString.get(i+3).toString());
			data.add(dt);
			i=i+3;		
		}
	}
	
	public static ObservableList<PrintPreview>  setupNewTable (ArrayList<String> sortedData) throws IOException{
		ObservableList<PrintPreview> newData=FXCollections.observableArrayList();
		newData.clear();
			
		for (int i=0; i<sortedData.size();i++){
			System.out.println("PODACI U TABLEPOPUP iz ARRAY su:"+sortedData.get(i).toString()+","+sortedData.get(i+2).toString());
			PrintPreview dt=new PrintPreview();
			dt.setKomit(sortedData.get(i+1).toString());
			dt.setKontSif(sortedData.get(i).toString());
			dt.setKont(sortedData.get(i+2).toString());
			dt.setPromj(sortedData.get(i+3).toString());
			newData.add(dt);
			i=i+3;		
		}
		return newData;
	}
	
	public void  populateTableKomitent(){
		tableKomitenti.setItems(data);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		key="";
		exportData.clear();
		populateTableKomitent();
		
		colSifraKomitent.setCellValueFactory(cellData -> cellData.getValue().KontSifProperty());		      
		colNazivKomitent.setCellValueFactory(cellData -> cellData.getValue().KomitProperty());		      
		colKontoKupac.setCellValueFactory(cellData -> cellData.getValue().KontProperty());		      
		colKontoDob.setCellValueFactory(cellData -> cellData.getValue().PromjProperty());		      
		
		tableKomitenti.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t)  {
				if (t.getCode()== KeyCode.ENTER){
					exportData.clear();
					String komitentIme= tableKomitenti.getSelectionModel().getSelectedItem().getKomit();			
					String sifraK=tableKomitenti.getSelectionModel().getSelectedItem().getKontSif();
					String kupacKonto=tableKomitenti.getSelectionModel().getSelectedItem().getKont();
					String dobKonto=tableKomitenti.getSelectionModel().getSelectedItem().getPromj();
					System.out.println("Selektovano u tabeli: "+komitentIme+", "+sifraK+", "+kupacKonto);
					exportData.add(rowString);
					exportData.add(komitentIme);
					exportData.add(sifraK);
					exportData.add(kupacKonto);
					exportData.add(dobKonto);
					passArray(exportData);
					key="";
					dialogStage.close();
					//company.Main.Main.closePopUpWindow();
				}
				if (t.getCode()== KeyCode.ESCAPE){
				key="";
				tableKomitenti.setItems(null);
            	try {
            		tableKomitenti.setItems(setupNewTable(company.Main.Support.Finansije.FinasnijeDAO.getSortedData(key)));
				} catch (IOException | ClassNotFoundException e){
					e.printStackTrace();
				} 
				}
				 if (t.getCode()==KeyCode.BACK_SPACE){
	                	if (key.length()>0){
	                	key=key.substring(0, key.length()-1);
	                	System.out.println("Key typed: "+key);
	                	tableKomitenti.setItems(null);
	                	try {
	                		tableKomitenti.setItems(setupNewTable(company.Main.Support.Finansije.FinasnijeDAO.getSortedData(key)));
						} catch (IOException | ClassNotFoundException e){
							e.printStackTrace();
						} 
	                	
	                	}
	                }     
	            }  
	        });  
	        
	        tableKomitenti.setOnKeyTyped(new EventHandler<KeyEvent>() {  
	            @Override  
	            public void handle(KeyEvent ke) {  
	            	if (!ke.getCharacter().matches( "[^A-Za-z0-9. ]")){
	            	
	                key=key.concat(ke.getCharacter());
	                System.out.println("Key typd: "+key);
	                tableKomitenti.setItems(null);
                	try {
                		tableKomitenti.setItems(setupNewTable(company.Main.Support.Finansije.FinasnijeDAO.getSortedData(key)));
					} catch (IOException | ClassNotFoundException e){
						e.printStackTrace();
					} 
	            	}else{System.out.println("Letter is not alphanumeric or space or dash");}
			}	
		});	
	}

	public ArrayList<String> passArray(ArrayList<String> exportList){
	
	return exportData;
	
}

}
