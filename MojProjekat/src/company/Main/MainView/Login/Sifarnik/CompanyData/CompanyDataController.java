package company.Main.MainView.Login.Sifarnik.CompanyData;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import company.Main.Support.Korisnici.Korisnici;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class CompanyDataController implements Initializable{
	@FXML
	public AnchorPane apImages;
	@FXML
	public VBox vbBasicData;
	@FXML
	public VBox vbContact;
	@FXML
	public VBox vbAccount;
	@FXML
	public HBox hbBtns;
	@FXML
	public TextField tfCompanyName;
	@FXML
	public TextField tfStreet;
	@FXML
	public TextField tfCity;
	@FXML
	public TextField tfPostCode;
	@FXML
	public TextField tfJIB;
	@FXML
	public TextField tfPDV;
	@FXML
	public TextField tfMatBroj;
	@FXML
	public TextField tfDirector;
	@FXML
	public TextField tfSifraPJ;
	@FXML
	public TextField tfPhone;
	@FXML
	public TextField tfFax;
	@FXML
	public TextField tfMob;
	@FXML
	public TextField tfMail;
	@FXML
	public TextField tfWeb;
	@FXML
	public TextField tfDomRacun;
	@FXML
	public TextField tfBanka;
	@FXML
	public TextField tfInoRacun;
	@FXML
	public TextField tfIBAN;
	@FXML
	public TextField tfSWIFT;
	@FXML
	public ImageView logoView;
	@FXML
	public Button btnLogoCancel;
	@FXML
	public Button btnLogoDelete;
	@FXML
	public Button btnSearchLogo;
	@FXML
	public ImageView signView;
	@FXML
	public Button btnSignDelete;
	@FXML
	public Button btnSignCancel;
	@FXML
	public Button btnSearchSign;
	@FXML
	public ImageView stampView;
	@FXML
	public Button btnStampDelete;
	@FXML
	public Button btnStampCancel;
	@FXML
	public Button btnSearchStamp;
	@FXML
	public Button btnSaveImg;
	@FXML
	public Button btnClose;
	@FXML
	public TableView<Korisnici> tablePregled;
	@FXML
	public TableColumn<Korisnici, String> colSifraPJ;
	@FXML
	public TableColumn<Korisnici, String> colNazivPJ;
	@FXML
	public Button btnAdd;
	@FXML
	public Button btnEdit;
	@FXML
	public Button btnDelete;
	@FXML
	public Button btnClear;
	@FXML
	public RadioButton rbDaPDV;
	@FXML
	public RadioButton rbNePDV;
	@FXML
	public ToggleGroup pdvUser;
	@FXML
	public Label lbDani;

	public Stage stage;
	public ObservableList<Korisnici> listData;
	public ArrayList<String> tfs = new ArrayList<>();
	public int lg=0;
	public int sg=0;
	public int sp=0;
	public int q=0;
	
	public int ID;
	public int tempID;
	public int selectedID;
	public String logo="logo.png";
	public String sign="sign.png";
	public String stamp="stamp.png";
	public String imgPath="C:/MojProjekat/Resources";
	public String table;
	
	// Event Listener on Button[#btnSearchLogo].onAction
	@FXML
	public void searchLogo(ActionEvent event) throws IOException {
		chooseAndSetImage(logoView);
	}
	
	// Event Listener on Button[#btnSearchSign].onAction
	@FXML
	public void searchSign(ActionEvent event) throws IOException {
		chooseAndSetImage(signView);
	}
	
	// Event Listener on Button[#btnSearchStamp].onAction
	@FXML
	public void searchStamp(ActionEvent event) throws IOException {
		chooseAndSetImage(stampView);
	}
	
	// Event Listener on Button[#btnDeleteLogo].onAction
	@FXML
	public void deleteLogo(ActionEvent event) throws IOException {
		lg=1;
		logoView.setImage(null);
	}
	
	// Event Listener on Button[#btnDeleteSign].onAction
	@FXML
	public void deleteSign(ActionEvent event) throws IOException {
		sg=1;
		signView.setImage(null);
	}
	
	// Event Listener on Button[#btnDeleteStamp].onAction
	@FXML
	public void deleteStamp(ActionEvent event) throws IOException {
		sp=1;
		stampView.setImage(null);
	}
	
	public void deleteImage() throws IOException{
		if (lg>0){
			deleteImageToFile(logo,logoView);
			lg=0;
		}
		if (sg>0){
			deleteImageToFile(sign,signView);
			sg=0;
		}
		if (sp>0){
			deleteImageToFile(stamp,stampView);
			sp=0;
		}
	}
	
	// Event Listener on Button[#btnLogoCancel].onAction
	@FXML
	public void cancelLogo(ActionEvent event) throws IOException {
		logoView.setImage(setFirstImage(logo));
		lg=0;
	}
	
	// Event Listener on Button[#btnSignCancel].onAction
	@FXML
	public void cancelSign(ActionEvent event) throws IOException {
		signView.setImage(setFirstImage(sign));
		sg=0;
	}
	
	// Event Listener on Button[#btnStampCancel].onAction
	@FXML
	public void cancelStamp(ActionEvent event) throws IOException {
	stampView.setImage(setFirstImage(stamp));
	sp=0;
	}
	
	// Event Listener on Button[#btnSaveImg].onAction
	@FXML
	public void saveImgChanges(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		deleteImage();
		saveImage();
		
	}
	
	public void saveImage() throws IOException, ClassNotFoundException, SQLException{
		saveImageToFile(logo, logoView);
		saveImageToFile(sign, signView);
		saveImageToFile(stamp, stampView);
	}
	
	// Event Listener on Button[#btnClose].onAction
	@FXML
	public void close(ActionEvent event) throws IOException {
		company.Main.Main.changeCenterMain("MainView/CenterView.fxml");
	}
	
	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addToDataBase(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		getTFS();
		company.Main.Support.Korisnici.KorisniciDAO.insertCompanyData(tfs, ID, "companydata", "logo","sign","stamp");
		populateTable();
		saveImage();
	
	}
	
	public void getTFS () throws ClassNotFoundException, SQLException{
		if (tfCompanyName.getText()!=null && tfCompanyName.getText().length()>0 && !tfCompanyName.getText().trim().equals("")) {
			if (findIfNameExists()){
			ID=getID();
			System.out.println("ID u add to base je :"+ID);
			tfs.clear();
			checkTF();	
			logo="logo"+ID+".png";
			tfs.add(logo);
			sign="sign"+ID+".png";
			tfs.add(sign);
			stamp="stamp"+ID+".png";
			tfs.add(stamp);
			}else{
				company.Main.Main.showAlertError("GRESKA!", "Naziv kompanije vec postoji.");
				tfCompanyName.requestFocus();
			}
			}else {
				company.Main.Main.showAlertError("GRESKA!", "Unesite naziv kompanije.");
				tfCompanyName.requestFocus();
			}
	}
	
	public int getID() throws ClassNotFoundException, SQLException{
		tempID=findNumberOfRowsToDB();
		int ret = 0;
		for (int j=1; j<tempID+1; j++){
			if (findIfIDExists(j)){
				ret=j;
				break;
			}
		}
		System.out.println("Get ID is number: "+ret);
		return ret;
	}
	
	public boolean findIfIDExists(int id) {	
		q=0;
		tablePregled.getItems().forEach(item -> {
		if (id==item.getCompanyID()){
			q=q+1;
		}
		});	
		if (q>0) {
			return false;
		}else{
			return true;
		}
	}
	
	public int findNumberOfRowsToDB() throws ClassNotFoundException, SQLException {
		if (table==null) {table="companydata";}
		ResultSet rs=company.Main.DBConnection.DBConnection.dbExecuteQuery("select count(*) from "+company.Main.Main.dbName+"."+table, table);
		int r = 0;
		rs.next();
		r=rs.getInt(1);
		System.out.println("Nuber of rows in table "+table +" is:" + r);
		//System.out.println("Slobodan ID je: "+ID);
		return r+1;
	}
	
	public boolean findIfNameExists() {		
		q=0;
		tablePregled.getItems().forEach(item -> {
		if (tfCompanyName.getText().trim().toUpperCase().equals(item.getCompanyName())){
			q=q+1;
		}
		});	
		if (q>0) {
			return false;
		}else{
			return true;
		}
	}
	
	public boolean findIfJIBExists() {
		q=0;
		listData.forEach(item -> {
			
			if (tfJIB.getText().trim().equals(item.getCompanyJIB())){
				q=q+1;
				System.out.println("Item JIB: "+item.getCompanyJIB());
			}
			});	
			if (q>0) {
				return false;
			}else{
				return true;
			}
	}
	
	public boolean findIfPDVExists() {
		q=0;
		listData.forEach(item -> {
			if (tfPDV.getText().trim().equals(item.getCompanyPDV())){
				q=q+1;
			}
			});	
			if (q>0) {
				return false;
			}else{
				return true;
			}
	}
	
	public void checkTF() {
		checkIsTFempty(vbBasicData);
		if (tfSifraPJ.getText()==null || tfSifraPJ.getText().equals("")) {
			tfSifraPJ.setText(" ");
		}
		tfs.add(tfSifraPJ.getText().trim());
		checkIsTFempty(vbContact);
		checkIsTFempty(vbAccount);
		System.out.println("TF string je:"+tfs);
	}
	
	public void checkIsTFempty(VBox vb) {	
		for (Node node : vb.getChildren()){
			if (node.getId()!=null && !node.getId().equals("lbDani")){
				if(((TextField)node).getText()==null || ((TextField)node).getText().equals("")){			
				((TextField)node).setText(" ");
				tfs.add(" ");
				
				} else {
					tfs.add(((TextField)node).getText().trim());	
				}
			}
		}	
	}
	
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editData(ActionEvent event) throws ClassNotFoundException, SQLException, ParseException, IOException {
		if (tfCompanyName.getText()!=null && tfCompanyName.getText().length()>0 && !tfCompanyName.getText().trim().equals("")) {
		tfs.clear();
		checkTF();	
		tfs.add(logo);
		tfs.add(sign);
		tfs.add(stamp);
		company.Main.Support.Korisnici.KorisniciDAO.updateCompanyData(tfs, selectedID, "companydata", "logo","sign","stamp");
		deleteImage();
		saveImage();
		populateTable();
		}else {
			company.Main.Main.showAlertError("GRESKA!", "Unesite naziv kompanije.");
			tfCompanyName.requestFocus();
		}
	}
	
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteData(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		lg=1; sg=1; sp=1;
		deleteImage();
		company.Main.Support.Korisnici.KorisniciDAO.deleteCompanyData(selectedID, "companydata");
		populateTable();
	}
	
	// Event Listener on Button[#btnClear].onAction
	@FXML
	public void clear(ActionEvent event) {
		tfs.clear();
		rbDaPDV.setSelected(true);
		clearTF(vbBasicData);
		clearTF(vbContact);
		clearTF(vbAccount);
		tfSifraPJ.setText("");
		logoView.setImage(null);
		signView.setImage(null);
		stampView.setImage(null);
		tfCompanyName.requestFocus();
	}
	
	public void clearTF (VBox vb){
		for (Node node : vb.getChildren()){
			if (node.getId()!=null && !node.getId().equals("lbDani")){
				((TextField)node).setText("");
			}
		}	
	}
	
	public void chooseAndSetImage(ImageView iv) throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")+"/Desktop")
            );                 
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
		File file=fileChooser.showOpenDialog(stage);
		if(file==null) {	
			System.out.println("Niste izabrali fajl");
		}
		else{
			Image image=null;
			Image imageScale=null;
			int targetH=140;
			int targetW;
			double scale;
			if (!iv.getId().equals("logoView")){
				BufferedImage bimg = company.Main.Support.Image.WhiteToTransparent.changeToTransparent(file);
				// calculate target size of height and width
				imageScale = SwingFXUtils.toFXImage(bimg, null);
				scale = imageScale.getHeight()/targetH;
				targetW=(int) (imageScale.getWidth()/scale);
				image = SwingFXUtils.toFXImage(company.Main.Support.Image.ResizeImage.resizeImg(bimg, targetW, targetH ), null );
				
			} else {
				BufferedImage bimg = ImageIO.read(file);
				image = SwingFXUtils.toFXImage(company.Main.Support.Image.ResizeImage.resizeImg(bimg, 150, 30 ), null );
			}
			iv.setImage(image);
		}
	}
	
	public Image setFirstImage(String nameImg) {
    	String path=imgPath+"/"+nameImg;
		File file= new File(path);
		Image img= new Image(file.toURI().toString());
		return img;
	}	
	
	public void saveImageToFile (String imgName, ImageView iv) throws IOException{
		try{
			System.out.println(imgName+ " je sacuvan!");
			File outputFile = new File (imgPath+"/"+imgName);
			BufferedImage bImage = SwingFXUtils.fromFXImage(iv.getImage(), null);
			ImageIO.write(bImage, "png", outputFile);
		}catch(Exception e){
			System.out.println(imgName+" nije moguce sacuvati jer slika nije postavljena");
			e.getMessage();
		}
	}
	
	public void deleteImageToFile (String imgName, ImageView iv) throws IOException{
			File outputFile = new File (imgPath+"/"+imgName);
			if (outputFile.exists()) outputFile.delete();
			iv.setImage(setFirstImage(imgName));	
	}
	
	public void populateTable() throws ClassNotFoundException, SQLException{
		listData=company.Main.Support.Korisnici.KorisniciDAO.searchDataCompany("companydata","logo","sign","stamp");
		tablePregled.setItems(listData);
	}
	
	public void populateFields(int row) {
		System.out.println ("Selected row is "+row);
		tfCompanyName.setText(listData.get(row).getCompanyName());
		tfSifraPJ.setText(listData.get(row).getCompanySifraPJ());
		tfStreet.setText(listData.get(row).getCompanyStreet());
		tfCity.setText(listData.get(row).getCompanyCity());
		tfPostCode.setText(listData.get(row).getCompanyPostCode());
		tfJIB.setText(listData.get(row).getCompanyJIB());
		tfPDV.setText(listData.get(row).getCompanyPDV());
		if (listData.get(row).getCompanyPDV().equals(" ")||listData.get(row).getCompanyPDV().isEmpty()){
			tfPDV.setText(" ");
			rbNePDV.setSelected(true);
		}else{
			rbDaPDV.setSelected(true);
		}
		tfMatBroj.setText(listData.get(row).getCompanyMB());
		tfDirector.setText(listData.get(row).getCompanyDirector());
		tfPhone.setText(listData.get(row).getCompanyPhone());
		tfFax.setText(listData.get(row).getCompanyFax());
		tfMob.setText(listData.get(row).getCompanyMobile());
		tfMail.setText(listData.get(row).getCompanyMail());
		tfWeb.setText(listData.get(row).getCompanyWeb());
		tfDomRacun.setText(listData.get(row).getCompanyZiroRacun());
		tfInoRacun.setText(listData.get(row).getCompanyDevRacun());
		tfBanka.setText(listData.get(row).getCompanyBanka());
		tfIBAN.setText(listData.get(row).getCompanyIBAN());
		tfSWIFT.setText(listData.get(row).getCompanySWIFT());	
		logo=listData.get(row).getCompanyLogo();
		sign=listData.get(row).getCompanySign();
		stamp=listData.get(row).getCompanyStamp();
		selectedID=listData.get(row).getCompanyID();
		System.out.println("Logo slika :"+logo);
		System.out.println("Sign slika :"+sign);
		logoView.setImage(setFirstImage(logo));
		signView.setImage(setFirstImage(sign));
		stampView.setImage(setFirstImage(stamp));
	}
/* ********************************************************************************************  */	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			populateTable();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		colSifraPJ.setCellValueFactory(cellData -> cellData.getValue().CompanySifraPJProperty());
		colNazivPJ.setCellValueFactory(cellData -> cellData.getValue().CompanyNameProperty());
		
		tablePregled.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				int row = tablePregled.getSelectionModel().getSelectedIndex();
				try{
				populateFields(row);
				}catch (Exception e){
					e.getMessage();
				}
			}
		});	
		
	tfCompanyName.textProperty().addListener((observable, oldValue, newValue) -> {
		System.out.println("tfCompanyName from "+oldValue+" to "+newValue);	
		if (oldValue.equals("")) {
			String sif = null;
			try {
				sif = ""+getID();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			int i= 4-sif.length();
			for (int j=0; j<i;j++){
				sif="0"+sif;
			}
			tfSifraPJ.setText(sif);
			rbDaPDV.setSelected(true);
		}
	});
	tfCompanyName.focusedProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue) {
			String name=tfCompanyName.getText().toUpperCase().trim();
			tfCompanyName.setText(name);
			}		
	});
	
	tfSifraPJ.textProperty().addListener((observable, oldValue, newValue) -> {
		System.out.println("tfSifraPJ from "+oldValue+" to "+newValue);	
		try{
		if(  newValue.equals("")|| newValue.matches("[0-9]+")){
			tfSifraPJ.setText(newValue);
		} else{
			tfSifraPJ.setText(oldValue);
		}
		if (newValue.length()>4) {
			tfSifraPJ.setText(oldValue);
		}
		}catch(Exception e){
			e.getMessage();
		}
	});

	tfSifraPJ.focusedProperty().addListener((Observable,oldValue,newValue)->{
		if(!newValue){
			String sif="";
			int i=4-tfSifraPJ.getText().length();
			for(int j=0;j<i;j++){
				sif="0"+sif;
			}
			sif=sif+tfSifraPJ.getText();
			tfSifraPJ.setText(sif);
		}
	});
	
	tfJIB.focusedProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue) {
			if(tfJIB.getText().trim().length()!=13){
				tfJIB.requestFocus();
				tfJIB.selectAll();
			}		
		}
	});
	
	tfJIB.textProperty().addListener((observable, oldValue, newValue) -> {
		System.out.println("tfJIB from "+oldValue+" to "+newValue);	
		if( newValue.equals("")||newValue.matches("[0-9]+")){
			tfJIB.setText(newValue);
		} else{
			tfJIB.setText(oldValue);
		}
		if (newValue.length()>13) {
			tfJIB.setText(oldValue);
		}
	});
	
	pdvUser.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
	    public void changed(ObservableValue<? extends Toggle> ov,
	        Toggle old_toggle, Toggle new_toggle) {
	            if (rbDaPDV.isSelected()) {
	              tfPDV.setEditable(true);
	              tfPDV.setDisable(false); 
	            } else{
	            	tfPDV.setText("");
	            	tfPDV.setEditable(false);
	            	tfPDV.setDisable(true);
	            }
	          
	        }
	});
	
	tfPDV.focusedProperty().addListener((observable, oldValue, newValue) -> {
		if (!newValue) {
			if(tfPDV.getText().trim().length()!=12){
				tfPDV.requestFocus();
				tfPDV.selectAll();
			}		
		}
	});
	
	tfPDV.textProperty().addListener((observable, oldValue, newValue) -> {
		System.out.println("tfPDV from "+oldValue+" to "+newValue);	
		if( newValue.equals("")|| newValue.matches("[0-9]+")){
			tfPDV.setText(newValue);
		} else{
			tfPDV.setText(oldValue);
		}
		if (newValue.length()>12) {
			tfPDV.setText(oldValue);
		}
	});
	
	}	
}
