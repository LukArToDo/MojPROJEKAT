package company.Main.Support.Calculator;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;

public class CalculatorController implements Initializable{
	@FXML
	private AnchorPane apCalculator;
	@FXML
	private HBox hbValuta;
	@FXML
	private HBox hb1;
	@FXML
	private HBox hb2;
	@FXML
	private HBox hb3;
	@FXML
	private HBox hb4;
	@FXML
	private HBox hb5;
	@FXML
	private Label lbMemo;
	@FXML
	private Label lbValuta;
	@FXML
	private Label lbKonverzija;
	@FXML
	private Label lbDisplay;
	@FXML
	private RadioButton rbKM;
	@FXML
	private RadioButton rbEUR;
	@FXML
	private Button btnMC;
	@FXML
	private Button btnMR;
	@FXML
	private Button btnMminus;
	@FXML
	private Button btnMplus;
	@FXML
	private Button btnDivide;
	@FXML
	private Button btnSQR;
	@FXML
	private Button btn3;
	@FXML
	private Button btn2;
	@FXML
	private Button btn1;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnPrecent;
	@FXML
	private Button btn0;
	@FXML
	private Button btnDot;
	@FXML
	private Button btnResult;
	@FXML
	private Button btnPosition;
	@FXML
	private Button btn7;
	@FXML
	private Button btn8;
	@FXML
	private Button btn9;
	@FXML
	private Button btnMultiple;
	@FXML
	private Button btnPrefix;
	@FXML
	private Button btn6;
	@FXML
	private Button btn5;
	@FXML
	private Button btn4;
	@FXML
	private Button btnSubstract;
	@FXML
	private Button btnC;
	@FXML
	private Button btnAC;
	@FXML
	private Button btnClose;
	@FXML
	private ToggleGroup currency;

	public String display="";
	public double x=0;
	public double y=0;
	public double q=0;
	public String operater;
	public String subString="";
	public double memo;
	public String res="";
	
	
	// Event Listener on Button[#btnC].onAction
	@FXML
	public void clearLast(ActionEvent event) {
		lbDisplay.setText(subString);
	}
	
	// Event Listener on Button[#btnAC].onAction
	@FXML
	public void clearAll(ActionEvent event) {
		x=0;
		y=0;
		display="";
		subString="";
		lbDisplay.setText("0");
		lbKonverzija.setText("0");
	}
	
	// Event Listener on Button[#btnClose].onAction
	@FXML
	public void closeCalculator(ActionEvent event) {
		company.Main.Main.closeCalc();
	}

	
	public void setListener (HBox hb){
		for (Node node : hb.getChildren()){
			((Button)node).setOnAction(new EventHandler<ActionEvent>(){
			 @Override
			 public void handle(ActionEvent event){
				 System.out.println("Presed button "+((Button)node).getId());
				 String btnClicked=((Button)node).getId();
				 validateButtons(btnClicked);
			 }
		 });        
		}
	}
	
	protected void validateButtons(String btnClicked) {
	
	display=lbDisplay.getText();
	if (display.equals("ERROR")){
		display="";
		lbDisplay.setText(display);
	}
	try{
		Integer.parseInt(btnClicked);
		if (res.equals("=")){
			res="";
			display="";
			subString="";
			x=0;
			y=0;
			lbDisplay.setText("");
		}
		if (display.equals("0"))display="";
		lbDisplay.setText(display.concat(btnClicked));
	}catch(Exception e){
		
	if(display.equals("ERROR")||display.equals("")){
	}else{
		if (res.equals("=")){
			subString="";
		}
	res="";
	switch(btnClicked){
	case ".":
		if (!display.substring(subString.length()).contains(".")){
			lbDisplay.setText(display.concat(btnClicked));
			}
			if (display.substring(subString.length()).equals("")){
				lbDisplay.setText(display+"0"+btnClicked);
			}
		break;
	case "+":
	case "x":
	case "/":
	case "-":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
			subString=subString.substring(0, subString.length()-1).concat(btnClicked);
			display=display.substring(0, display.length()-1).concat(btnClicked);
			lbDisplay.setText(display);
			System.out.println("substring kod zamjene"+subString);
		} else {
	 	if(x==0 && subString.equals("")){
	 	x=Double.parseDouble(display);
	 	lbDisplay.setText(display.concat(btnClicked));
	 	subString=lbDisplay.getText();
	 	} else {
	 		showCalculate (btnClicked);
	 	} 
		}
		break;
	case "result":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
		}else{
		if(x==0&& subString.equals("")){
			res="=";
		 	q=Double.parseDouble(display);
		 	lbDisplay.setText(display);
		 	subString=lbDisplay.getText();		
		 	showCurrency(q);
		 	x=0;
		 	} else {
		 		res="=";
		 		showCalculate (btnClicked);
		 	}
		} 
			break;
	case "sqr":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
		}else{
		if(x==0){
		 	q=Math.sqrt(Double.parseDouble(display));
		 	lbDisplay.setText(""+setNumberFormat(q));
		 	subString="";	
		 	showCurrency(q);
		 	x=0;
		 	} else if (x!=0 && y==0){
		 		showCalculate (btnClicked);
		 	} 
		}
			break;
	case "precent":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
		}else{
		if (x==0){
			q=(Double.parseDouble(display))/100;
			lbDisplay.setText(""+setNumberFormat(q));
		 	subString="";	
		 	showCurrency(q);
		 	x=0;
		 	} else if (x!=0 && y==0){
		 		showCalculate (btnClicked);
		 	} 
		}
			break;
	case "arrow":
		display=lbDisplay.getText();
		System.out.println("btn goRight is pressed");
		if (subString.length()>0 && display.length()>subString.length()){
		display=display.substring(0, display.length()-1);
		lbDisplay.setText(display);
		}else{
			if (subString.equals("")){
				display=display.substring(0, display.length()-1);
				lbDisplay.setText(display);	
			}
		}
		break;
	case "prefix":
		if (x==0){
			if (display.equals("")){
			lbDisplay.setText("-");
			}
			else if (display.equals("-")){
				lbDisplay.setText("");
			}
			else {
				x=Double.parseDouble(display);
			}
			if (x>0){
			lbDisplay.setText("-"+display);
			} else if (x<0){
				lbDisplay.setText(display.substring(1));
			}
			x=0;
			//subString=lbDisplay.getText();
		}else  if (x!=0 && y==0){
			if (display.substring(subString.length()).equals("")){
				lbDisplay.setText(subString+"-");
				}
				else if (display.substring(subString.length()).equals("-")){
					lbDisplay.setText(subString+"");
				}
				else {
					y=Double.parseDouble(display.substring(subString.length()));
				}
				if (y>0){
				lbDisplay.setText(subString+"-"+display.substring(subString.length()));
				} else if (y<0){
					lbDisplay.setText(subString+display.substring(subString.length()+1));
				}
				y=0;	
		}
				break;
	case "mPlus":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
		}else{
		lbMemo.setText("M");
		if(x==0){
		 	q=Double.parseDouble(display);
		 	lbDisplay.setText(display);
		 	subString=lbDisplay.getText();		
		 	showCurrency(q);
		 	memo +=q;
		 	x=0;
		 	res="=";
		 	} else if (x!=0 && y==0){
		 		showCalculate (btnClicked);
		 		res="=";
		 	} 
		}
			break;
	case "m-":
		if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")){
		}else{
		lbMemo.setText("M");
		if(x==0){
		 	q=Double.parseDouble(display);
		 	lbDisplay.setText(display);
		 	subString=lbDisplay.getText();		
		 	showCurrency(q);
		 	memo -=q;
		 	x=0;
		 	res="=";
		 	} else if (x!=0 && y==0){
		 		showCalculate (btnClicked);
		 		res="=";
		 	} 
		}
			break;	
	case "mr":
		if (lbMemo.getText().equals("M")){
			if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")||display.equals("")||display.equals("-")){
				lbDisplay.setText(display+memo);
			} 
			else{ 
				lbDisplay.setText(""+memo);
				res="=";
			}
			}
			break;
	case "mc":
		if (lbMemo.getText().equals("M")){
			if (display.endsWith("+")||display.endsWith("-")||display.endsWith("x")||display.endsWith("/")||display.equals("")||display.equals("-")){
				lbDisplay.setText(display+memo);
			} 
			else{ 
				lbDisplay.setText(""+memo);
				res="=";
			}
			}
		lbMemo.setText("");
		memo=0;
		break;
	}
	}	
	}
	}

	private void showCalculate (String btnClicked){
		if (btnClicked.equals("sqr")) {
			y=Math.sqrt(Double.parseDouble(display.substring(subString.length())));
		} else if (btnClicked.equals("precent")) {
			y=(((Double.parseDouble(display.substring(subString.length()))))/100)*x;
		} else {
		y=Double.parseDouble(display.substring(subString.length()));
		}
 		q=setNumberFormat(calculateNumbers(x,y,subString.substring(subString.length()-1)));
 		if (q==-123456789.12){
 			x=0;
			y=0;
			lbDisplay.setText("ERROR");
 		}else{
 		if (btnClicked.equals("result")||btnClicked.equals("sqr")||btnClicked.equals("precent")||btnClicked.equals("mPlus")||btnClicked.equals("m-")){
 			lbDisplay.setText(""+q);
	 		x=0;y=0;
 		} else {
 		lbDisplay.setText(""+q+btnClicked);
 		x=q;y=0;
 		}
 		if (btnClicked.equals("mPlus")){
 			memo += q;
 		}
 		if (btnClicked.equals("m-")){
 			memo -= q;
 		}
 		System.out.println("x=q= "+x);
 		subString=lbDisplay.getText();
 		showCurrency(q);
	}
	}
	
	private void showCurrency(double q){
		if (rbKM.isSelected()) {
 			lbKonverzija.setText(""+setNumberFormat(q/1.95583));
 		}else{
 			lbKonverzija.setText(""+setNumberFormat(q*1.95583));
 		}
	}
	
	private double setNumberFormat(double n){
		BigDecimal bd = new BigDecimal(n).setScale(6, RoundingMode.HALF_EVEN);
		return   bd.doubleValue();
	}
	
	private Double calculateNumbers(double n1, double n2, String opr) {
		if (opr.equals("+")) return n1+n2;
		if (opr.equals("-")) return n1-n2;
		if (opr.equals("/")) {
			if (n2!=0) return n1/n2;
			else {
				System.out.println("Broj je nemoguce dijeliti sa nulom");
				return -123456789.12;
			}
		} 
		return n1*n2;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setListener(hb1);
		setListener(hb2);
		setListener(hb3);
		setListener(hb4);
		setListener(hb5);
		lbValuta.setText("EUR");
		
		currency.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		            Toggle old_toggle, Toggle new_toggle) {
		                if (currency.getSelectedToggle().equals(rbKM) ) {
		                   lbValuta.setText("EUR");  
		                   lbKonverzija.setText(""+setNumberFormat(q/1.95583));
		               } else{
		            	   lbValuta.setText("KM");
		            	   lbKonverzija.setText(""+setNumberFormat(q*1.95583));
		               }
		            }
		    });
		
		apCalculator.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {  
            @Override  
            public void handle(KeyEvent ke) {  
                	System.out.println("Key pressed "+ke.getCode());
                	if (ke.getCode().equals(KeyCode.ENTER)){
                		btnResult.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.BACK_SPACE)){
                		btnPosition.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.ADD)){
                		btnAdd.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.SUBTRACT)){
                		btnSubstract.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.DIVIDE)){
                		btnDivide.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.MULTIPLY)){
                		btnMultiple.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.DECIMAL) || ke.getCode().equals(KeyCode.COMMA)){
                		btnDot.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD0)){
                		btn0.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD1)){
                		btn1.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD2)){
                		btn2.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD3)){
                		btn3.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD4)){
                		btn4.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD5)){
                		btn5.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD6)){
                		btn6.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD7)){
                		btn7.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD8)){
                		btn8.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.NUMPAD9)){
                		btn9.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.ESCAPE)){
                		btnClose.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.RIGHT)){
                		rbEUR.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.LEFT)){
                		rbKM.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.DELETE)){
                		btnC.fire();
                		ke.consume();
                	}
                	if (ke.getCode().equals(KeyCode.HOME)){
                		btnAC.fire();
                		ke.consume();
                	}
            }  
        });  
  
	}
}
