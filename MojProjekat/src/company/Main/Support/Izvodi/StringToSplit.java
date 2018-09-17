package company.Main.Support.Izvodi;
import java.io.IOException;
import java.util.ArrayList;
import company.Main.Support.Finansije.FinasnijeDAO;

public class StringToSplit {

public static String[] splitStr ;
public static ArrayList<String>listIzvod;
public static int index;
public static String bankaNaziv="";
public static String vrstaNaloga;
public static String kontoIzvodaBanke;
public static String brIzvoda;
public static String datumIzvoda;
public static String bankaRacun;
public static String prethStanje;
public static String prometDuguje;
public static String prometPotrazuje;
public static String novoStanje;
public static String neiskLimit;
public static String rezervIznos;
public static String raspolozivo;
public static String dospPotrazivanja;
public static String brPromjena;
public static int  brProm;
public static int size;
public static final int pocSize = 34;
public static int zadSize ;
public static String opisPromjene;
public static String opisKomitenta;
public static ArrayList<Integer>pocIn= new ArrayList<Integer>();
public static ArrayList<Integer>racIn= new ArrayList<Integer>();
public static ArrayList<Integer>zadIn= new ArrayList<Integer>();
public static ArrayList<Integer>podIn= new ArrayList<Integer>();
public static ArrayList<String> listOpisPromjene= new ArrayList<String>();
public static ArrayList<String> listKomitent= new ArrayList<String>();
public static ArrayList<String> listKomitentZR= new ArrayList<String>();
public static ArrayList<String> listDuguje= new ArrayList<String>();
public static ArrayList<String> listPotrazuje= new ArrayList<String>();
public static ArrayList <String> br= new ArrayList<String>();
public static ArrayList<String> listKonto= new ArrayList<String>();
public static ArrayList<String> listKontoSifra= new ArrayList<String>();
public static ArrayList <String> mergeArrays= new ArrayList<String>();
public static int ukSize;
public static int piROW;



public static void splitString (String string, int bs) throws IOException, ClassNotFoundException{
try{	
		splitStr=string.trim().split("\\s+");
		size= splitStr.length;	
		zadSize=size-44;
		int rf=0;
		for (int a=0; a<size;a++){
		System.out.println("["+a+"]"+splitStr[a].toString());
		if (splitStr[a].equals("www.raiffeisenbank.ba")){
			rf=rf+1;
		}
		}
/* *************************************DEVIZNI IZVOD  Nova Banka *****************************/

if (splitStr[23].trim().startsWith("5551101000127872-EUR")){
			System.out.println("DEVIZNI IZVOD NOVE BANKE");
			String[] splitDev=string.split("\r\n");
			for(int r=0; r<splitDev.length;r++){
				System.out.println("["+r+"]  "+splitDev[r]);
				
				if(splitDev[r].toLowerCase().startsWith("prethodno stanje")){
					prethStanje=splitDev[r+3].trim();
				}
				if(splitDev[r].toLowerCase().trim().startsWith("closing balance")){
					novoStanje=splitDev[r-2].trim();
				}			
			}
			
			vrstaNaloga="24";
			kontoIzvodaBanke="242000";
			bankaNaziv="DEVIZNO-NOVA BANKA";
			brIzvoda=splitDev[2].trim();
			datumIzvoda=splitDev[3].trim();
			
			brProm=0;
			for(int i=4;i<splitDev.length;i++){
				if (splitDev[i].trim().toLowerCase().startsWith("ukupan promet")){
					String[]promet=splitDev[i+3].split("\\s+");
					prometDuguje=promet[0];
					prometPotrazuje=promet[1];
					break;
				}
				if(splitDev[i].trim().matches("\\d{2}.\\d{2}.\\d{4}")){
				opisKomitenta="";
				brProm=brProm+1;
				br.add(""+brProm);
				listKomitentZR.add("---");
				for (int r=i; r<i+20;r++){
					if (splitDev[r].toLowerCase().trim().startsWith("nalog za pla")){
						String komit=splitDev[r].substring(19);
						String[]splitKom=komit.split("\\s+");
						String komitent= splitKom[0];
						opisKomitenta=opisKomitenta+" "+splitDev[r].substring(19);
						listOpisPromjene.add("Nalog za placanje");
						listKonto.add("435000");
						listKontoSifra.add(FinasnijeDAO.getKontoDev(komitent));
						
						for(int w=r+1; w<r+10;w++){
							if (splitDev[w].trim().equals("EUR")||splitDev[w].trim().equals("KM")){
								r=w;
								break;
							}else{
								opisKomitenta=opisKomitenta+" "+splitDev[w];
							}
						}
						listKomitent.add(opisKomitenta.trim());
					}
					else if (splitDev[r].toLowerCase().trim().startsWith("konverz")||splitDev[r].toLowerCase().trim().contains("konv")||splitDev[r].toLowerCase().trim().contains("prenos")&&!splitDev[r].toLowerCase().trim().contains("proviz")){
							listKonto.add("242900");
							listKontoSifra.add("-");
							listKomitent.add(" N.Banka ");
							listOpisPromjene.add("KONVERZIJA");
					} 
					else if(splitDev[r].toLowerCase().trim().startsWith("provizija")||splitDev[r].toLowerCase().trim().contains("naknade")||splitDev[r].toLowerCase().trim().contains("proviz")){
							listKonto.add("553100");
							listKontoSifra.add("-");
							listKomitent.add(" NB ");
							listOpisPromjene.add("PROVIZIJA");
							}
					
					else if(splitDev[r].trim().equals("KM")){
					listDuguje.add(splitDev[r+2]);
					listPotrazuje.add(splitDev[r+4]);
					i=r+4;
					break;
					}
				}	
				} 
			}
			
			System.out.println("Broj izvoda je "+brIzvoda+", a datum izvoda: "+datumIzvoda+". Prethodno stanje: "+prethStanje+", a novo stanje: "+novoStanje);
			System.out.println("Promjene: "+br);
			System.out.println("Ukupan promet duguje: "+prometDuguje + ", a potrazuje: "+prometPotrazuje);
			company.Main.Main.getIzvod("MainView/Login/Ostalo/Izvodi/InputIzvod.fxml");
}



/* **************************************NOVA BANKA****************************************** */
		
		else if (splitStr[14].startsWith("555-00701032074")){
	brojPromjenaNovaBanka();
for (int i=1; i<brProm+1;i++){
	String q="";
	q=""+i;
	br.add(q);
}

promjenaNovaBanka(pocSize, zadSize, bs);
System.out.println("Opis promjene: "+listOpisPromjene);
System.out.println("list komitent: "+listKomitent);
System.out.println("list duguje: "+listDuguje);
System.out.println("list potrazuje"+listPotrazuje);
podglavljeNovaBanka();
blankKontoAndKontoSifra ('k',0,listOpisPromjene.size(),"","","","");
getKontoNB();

bankaNaziv="NOVA BANKA ad";
vrstaNaloga="10";
kontoIzvodaBanke="241010";
brIzvoda = splitStr[2];
datumIzvoda= splitStr[8];
bankaRacun=splitStr[14];  // ciji je izvod uploudovan
prethStanje=splitStr[23];
prometDuguje=splitStr[podIn.get(5)];
String prDugSt=splitStr[podIn.get(5)+1];
prometPotrazuje=prDugSt.substring(0, prDugSt.length()-6);
novoStanje=splitStr[podIn.get(4)];
neiskLimit=splitStr[podIn.get(3)];
rezervIznos=splitStr[podIn.get(2)];
raspolozivo=splitStr[podIn.get(1)];
dospPotrazivanja=splitStr[podIn.get(0)];
company.Main.Main.getIzvod("MainView/Login/Ostalo/Izvodi/InputIzvod.fxml");
}


/* ******************************* NLB RAZVOJNA *************************************************** */
else if (splitStr[9].startsWith("562-099-00014895")){
System.out.println("Uvezen je izvod NLB Razvojne banke");

bankaNaziv="NLB BANKA ad";
vrstaNaloga="11";
kontoIzvodaBanke="241011";
splitStr=null;
splitStr=string.split("[\\r\\n]+");
size= splitStr.length;	
int k=0;
for (int a=0; a<size;a++){
	splitStr[a]=splitStr[a].trim();
	System.out.println("["+a+"]"+splitStr[a].toString());
	if (splitStr[a].matches("\\d{3}-\\d{3}-\\d{8}-\\d{2}")){
		listKomitentZR.add(splitStr[a].replace("-", ""));
		 k=a+3;
	}
	if (splitStr[a].matches(".*\\,\\d\\d")){
		zadIn.add(a);		
	}
	System.out.println("indexi sa brojevima su : "+zadIn);
}

for (int i=k; i<splitStr.length;i++){
	if(splitStr[i].equals("Svrha doznake")) break;
	listKomitent.add(splitStr[i]);
}

brProm=listKomitentZR.size();
for (int i=1; i<brProm+1;i++){
	String q="";
	q=""+i;
	br.add(q);
}

brIzvoda=splitStr[2].replaceAll("\\D+","");
int l = splitStr[3].length()-7;
datumIzvoda=splitStr[3].substring(0, l);

System.out.println("broj izvoda : "+brIzvoda);
System.out.println("datum izvoda : "+datumIzvoda);
System.out.println("redni brojevi promjena : "+br);
System.out.println("listaKominetnt: "+listKomitent);

int j=(zadIn.size()-2)/2;
String ukupno = null;
for (int i=0; i<zadIn.size();i++) {
	if (i==0) {prethStanje=splitStr[zadIn.get(i)];
	prethStanje=prethStanje.replace(",", ".");
	}
	else if(i==zadIn.size()-1) ukupno=splitStr[zadIn.get(i)];
	else if (i>0 && i<j+1) listDuguje.add(splitStr[zadIn.get(i)].replace(",", "."));		
	else listPotrazuje.add(splitStr[zadIn.get(i)].replace(",", "."));
	}
ukupno=ukupno.replace(",", ".");
String[] ul = ukupno.trim().split("\\s+");
prometDuguje= ul[0];
prometPotrazuje=ul[1];
novoStanje=ul[2];

int s=zadIn.size();
for (int i=zadIn.get(s-2)+2;i<zadIn.get(s-1);i++) {
	listOpisPromjene.add(splitStr[i]);
}

blankKontoAndKontoSifra ('k',0,listOpisPromjene.size(),"","","", "");
getKontoNLBandRFB();
System.out.println("prethodno stanje : "+prethStanje);
System.out.println("listDuguje : "+listDuguje);
System.out.println("listPotrazuje : "+listPotrazuje);
System.out.println("prometDuguje : "+prometDuguje);
System.out.println("prometPotrazuje : "+prometPotrazuje);
System.out.println("novoStanje : "+novoStanje);
System.out.println("opis promjene : "+listOpisPromjene);
company.Main.Main.getIzvod("MainView/Login/Ostalo/Izvodi/InputIzvod.fxml");
}

/* **************************************RAIFFEISEN BANK************************************************ */
else if(rf>0){
	System.out.println("Uvezen je izvod Raiffeisen bank");
	//int swift = 0;
	bankaNaziv="RAIFFEISEN BANK";
	vrstaNaloga="12";
	kontoIzvodaBanke="241012";
	splitStr=null;
	splitStr=string.split("[\\r\\n]+");
	size= splitStr.length;
	
	for (int a=0; a<size;a++){
		splitStr[a]=splitStr[a].trim();
		System.out.println("["+a+"]"+splitStr[a].toString());
		
		if (splitStr[a].toUpperCase().startsWith("PRETHODNI SALDO")) {
			prethStanje=splitStr[a].substring(16).trim();		
		}
		
		if (splitStr[a].toUpperCase().startsWith("NOVI SALDO")) {
			novoStanje=splitStr[a].substring(11).trim();		
			prometPotrazuje=splitStr[a-1].substring(8).trim();
			prometDuguje=splitStr[a-2].substring(23).trim();
		}
		if (splitStr[a].startsWith("Izvod za komitenta")){
			brIzvoda=splitStr[a].substring(24, splitStr[a].length()-18).trim();	
			datumIzvoda=splitStr[a].substring(splitStr[a].length()-10, splitStr[a].length()).trim();
		}
		
		if (splitStr[a].length()>10 && !splitStr[a].matches(".*www.*")){
			
			if (splitStr[a].substring(0, 10).matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {	
				System.out.println("Pocetak promjene:" +splitStr[a]);
				
				if (splitStr[a].substring(splitStr[a].length()-4).matches("\\d\\.\\d{2}")){
					opisPromjene="";
					String [] newSplit= null;
					newSplit=splitStr[a].split("\\s+");
					
					for (int j=1; j<newSplit.length-4; j++){
						opisPromjene=opisPromjene+" "+newSplit[j];
					}
					
					if (opisPromjene.contains("provizij")) { 
						listKomitent.add("Raiffeisen Bank");
						listKomitentZR.add("1610450011840051");
					} else { 
					listKomitent.add("?");	
					listKomitentZR.add("?");
					}
					
					listDuguje.add(newSplit[newSplit.length-3]);
					listPotrazuje.add(newSplit[newSplit.length-2]);
					listOpisPromjene.add(opisPromjene.trim());
					System.out.println("opis promjene= "+listOpisPromjene);
				} 
				else {
				opisKomitenta="";	
				opisPromjene="";
				opisPromjene=opisPromjene+splitStr[a].substring(10).trim();
				
					for(int j=1;j<10; j++){
						try{
						if (splitStr[a+j].matches(".*\\d\\.\\d{2}$")){ 
							break;
							}
						
						if (splitStr[a+j].startsWith("/")){
							listKomitentZR.add(splitStr[a+j].substring(1, 17));
							opisKomitenta=opisKomitenta+""+splitStr[a+j].replaceAll("\\/\\d{16}", "");
							
							for(int q=1;q<10;q++){
								
								if (splitStr[a+j+q].substring(splitStr[a+j+q].length()-4).matches("\\d\\.\\d{2}")){
									String [] newSplit= null;
									newSplit=splitStr[a+j+q].split("\\s+");
									
									for (int x=1; x<newSplit.length-4; x++){
										opisKomitenta=opisKomitenta+" "+newSplit[x];
									}
			
									listDuguje.add(newSplit[newSplit.length-3]);
									listPotrazuje.add(newSplit[newSplit.length-2]);
									break;
								}
								else {
									opisKomitenta=opisKomitenta+" "+splitStr[a+j+q];
								}
							}
							listKomitent.add(opisKomitenta.trim());	
						}
						/*else {
							opisPromjene=opisPromjene+" "+splitStr[a+j];
						}*/
						}catch (Exception ex){
							System.out.println(ex.getMessage());
						}
					}
					listOpisPromjene.add(opisPromjene.trim());	
				}
			}
		}	
	}
	
	for(int q=0; q<listDuguje.size();q++){
	br.add(""+(q+1));}
	System.out.println("broj izvoda je: "+ brIzvoda);
	System.out.println("Datum izvoda je: "+ datumIzvoda);
	System.out.println("Prethodno stanje= " + prethStanje);	
	System.out.println("Ukupno duguje= " + prometDuguje);
	System.out.println("Ukupno potrazuje= " + prometPotrazuje);
	System.out.println("Novo stanje= " + novoStanje);
	System.out.println("Redni broj= " + br);
	System.out.println("opis promjene= "+listOpisPromjene);
	System.out.println("komitentZR racun= "+listKomitentZR);
	System.out.println("komitenti= "+listKomitent);
	
	blankKontoAndKontoSifra ('k',0,listOpisPromjene.size(),"","","", "");
	getKontoNLBandRFB();
	System.out.println("konto= "+listKonto);
	System.out.println("konto-sifra: "+listKontoSifra);

	company.Main.Main.getIzvod("MainView/Login/Ostalo/Izvodi/InputIzvod.fxml");
}

/* **************************************************************************************************** */

else{ 
	company.Main.Main.showAlertError("POGREŠAN TIP IZVODA!","Pokusajte ponovo ili unesite izvod rucno.");

}

/* ************************************************************************************************* */

ukSize= br.size()+listOpisPromjene.size()+listKomitentZR.size()+listKomitent.size()+listDuguje.size()+listPotrazuje.size()+listKonto.size()+listKontoSifra.size();

setMergeArrays();

}catch(RuntimeException e){
	System.out.println(e.getMessage());
}
}

public static void  setMergeArrays(){
	mergeArrays.clear();
	mergeArrays.addAll(br);
	mergeArrays.addAll(listOpisPromjene);
	mergeArrays.addAll(listKomitentZR);
	mergeArrays.addAll(listKomitent);
	mergeArrays.addAll(listDuguje);
	mergeArrays.addAll(listPotrazuje);
	mergeArrays.addAll(listKonto);
	mergeArrays.addAll(listKontoSifra);
	System.out.println("MeregeArrays: " + mergeArrays);
	
}


public static void brojPromjenaNovaBanka(){

		for (int i=size-1; i>0; i--){
			if (splitStr[i].equals("/"))
				
			{ 
				/*if(splitStr[i-2].matches(".*[a-z A-Z].*"))
			}
			{}
			else{brProm=Integer.parseInt(splitStr[i-2])+1;			
			break;}  */
				if (splitStr[i-2].matches("[0-9]+") && splitStr[i-2].length() > 0) {
					brProm=Integer.parseInt(splitStr[i-2])+1;			
					break;
				}
				if (splitStr[i-4].matches("[0-9]+") && splitStr[i-4].length() > 0) {
					brProm=Integer.parseInt(splitStr[i-4])+1;			
					break;
				}
			}
		//	}				
	}	
	brPromjena=""+brProm;
	System.out.println("BROJ PROMJENA JE (/): "+brProm);
	for (int i=size-1; i>0;i--){
		if(splitStr[i].equals(brPromjena)){
			index=i+1;
			break;
		}
	}
}	


public static void promjenaNovaBanka (int poc, int zad, int bs){

	int a=0;
	if (bs==1){
	for (int i=poc; i<zad; i++){
		if (splitStr[i].equals("/")){
		pocIn.add(i);
		}		
		if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
			racIn.add(i);
			
			}
		if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
			zadIn.add(i);		
			}		
		}
	}else if (bs==2){	
		int second=0;
		for(int i=poc;i<zad; i++){
			if(splitStr[i].toLowerCase().equals("izvod")){
				second=i;
				break;
			}
		}
		for (int i=poc; i<second; i++){
			if (splitStr[i].equals("/")){
			pocIn.add(i);
			}		
			if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
				racIn.add(i);
				
				}
			if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
				zadIn.add(i);		
				}		
			}
		for (int i=second+34; i<zad; i++){
			if (splitStr[i].equals("/")){
			pocIn.add(i);
			}		
			if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
				racIn.add(i);
				
				}
			if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
				zadIn.add(i);		
				}		
			}
		}else if (bs==3){	
				ArrayList<Integer> listSecond =new ArrayList<Integer>();
				for(int i=poc;i<zad; i++){
					if(splitStr[i].toLowerCase().equals("izvod")){
						listSecond.add(i);
					}
				}
				for (int i=poc; i<listSecond.get(0); i++){
					if (splitStr[i].equals("/")){
					pocIn.add(i);
					}		
					if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
						racIn.add(i);
						
						}
					if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
						zadIn.add(i);		
						}		
					}
				for (int i=listSecond.get(0)+34; i<zad; i++){
					if (splitStr[i].equals("/")){
					pocIn.add(i);
					}		
					if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
						racIn.add(i);
						
						}
					if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
						zadIn.add(i);		
						}		
					}
				for (int i=listSecond.get(1)+34; i<zad; i++){
					if (splitStr[i].equals("/")){
					pocIn.add(i);
					}		
					if (splitStr[i].length()==16 && splitStr[i].matches("[0-9]+") && !splitStr[i-2].equals("Overdraft")){
						racIn.add(i);
						
						}
					if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i].matches(".*[a-zA-Z]+.*")){  
						zadIn.add(i);		
						}		
					}
	}
	
	for (int i =0; i<brProm;i++ ){
		opisPromjene="";
		opisKomitenta="";
	
		for (int j=pocIn.get(i)+1; j<racIn.get(i); j++){
			opisPromjene=opisPromjene.concat(splitStr[j]+" ");
		}
		listOpisPromjene.add(opisPromjene);
		
		
		 a=(i*2)+1;
		for (int j=1+racIn.get(i); j<zadIn.get(a)-1; j++){
			opisKomitenta=opisKomitenta.concat(splitStr[j]+ " ");
		}
		listKomitent.add(opisKomitenta);
		
		int y=racIn.get(i);
		listKomitentZR.add(splitStr[y]);
		
		int q=zadIn.get(i*2);
		listDuguje.add(splitStr[q]);
		
		int w=zadIn.get(i*2+1);
		listPotrazuje.add(splitStr[w]);
	}
	
	}

public static void podglavljeNovaBanka (){

	for (int i=size-1; i>0; i--){
		
		if (splitStr[i].matches(".*\\.\\d\\d")&& !splitStr[i-2].equals("PRETHODNO"))
		{
			podIn.add(i);
			if (podIn.size()>6) break;
		}
		}

	}	

/* ************************************************************************************************************************** */

public static  ArrayList<String> blankKontoAndKontoSifra (char c,int pocI, int zadI, String komitent, String promjena, String ziroracun,String duguje) throws ClassNotFoundException, IOException{
	ArrayList <String>newPopulate=new ArrayList<>();
	newPopulate.clear();
	piROW=pocI;
		if (komitent.split("\\s+").length<2) komitent=komitent.concat(" ;");
	
	for (int i =pocI; i<zadI;i++ ){
		String[]OProm=null;
		String[] LKom=new String[10];
		if (c=='k'){
			
		LKom=listKomitent.get(i).toString().toUpperCase().split("\\s+");}
		else{
			LKom=komitent.toUpperCase().split("\\s+");
		}
		
		
		if (LKom[0].endsWith("-")) {LKom[0]=LKom[0].replaceAll("-", "");}
		if (LKom[1].startsWith("-")|| LKom[1].endsWith("-")){LKom[1]=LKom[1].replaceAll("-", "");}
		String lk=LKom[0]+" "+LKom[1];
		System.out.println("Korigovani string LK je: "+lk);
		
		if (LKom[0].toString().toUpperCase().equals("MAX")&&LKom[1].toString().toUpperCase().equals("PAPIR") || LKom[0].toUpperCase().equals("MAXPAPIR")){
			if (c=='k') OProm=listOpisPromjene.get(i).toString().toUpperCase().split("\\s+");
			if (c=='t') OProm=promjena.toUpperCase().split("\\s+");
			lk="";
			for(int j=0; j<OProm.length;j++){
				if (OProm[j].toString().toUpperCase().equals("KOMPENZACIJA")||OProm[j].toString().toUpperCase().equals("KOMPEZACIJA")||OProm[j].toString().toUpperCase().equals("BLUKA")||OProm[j].toString().toUpperCase().equals("B.")
						||OProm[j].toString().toUpperCase().equals("B.LUKA")||OProm[j].toString().toUpperCase().equals("MAX")|| OProm[j].toString().toUpperCase().equals("MAKS") || OProm[j].toString().toUpperCase().equals("MAXPAPIR")
						|| OProm[j].toString().toUpperCase().equals("PAPIR")|| OProm[j].toString().toUpperCase().equals("DOO")|| OProm[j].toString().toUpperCase().equals("D.O.O.")|| OProm[j].toString().toUpperCase().equals("BANJALUKA")
						|| OProm[j].toString().toUpperCase().equals("BANJA")|| OProm[j].toString().toUpperCase().equals("LUKA")||OProm[j].toString().toUpperCase().equals("B")||OProm[j].toString().toUpperCase().equals("SA")){
					lk=lk.concat("");
					}
				else{
					if (OProm[j].toString().trim().startsWith("-")||OProm[j].toString().trim().endsWith("-")){OProm[j]=OProm[j].toString().replaceAll("-", "").trim();}
					lk=lk.concat(OProm[j].toString()+" ");
					}
		}
		
		lk=lk.trim();
		}
		
		System.out.println("NAZIV KOMITENTA pRIJE DB :"+lk);
		if (c=='k'){
		FinasnijeDAO.getKontoAndSifra(lk, listKomitentZR.get(i).toString(),listDuguje.get(i).toString());
		newPopulate=null;
		} else if (c=='t') {
			// company.Main.PupUp.IzvodKomitentPopUP.setupTable(FinasnijeDAO.getTableKonto(pocI, lk, ziroracun,duguje));
			company.Main.KomitentAndKontoTable.KomitentAndKontoTableController.setupTable(FinasnijeDAO.getTableKonto(pocI, lk, ziroracun,duguje));
		//	company.Main.Main.showPopUpKonto("PupUp/KomitentAndKontoTable.fxml");
			newPopulate.addAll(company.Main.Main.showPopUpKonto("KomitentAndKontoTable/KomitentAndKontoTable.fxml"));
		}
		}
	return newPopulate;
}

private static void getKontoNLBandRFB() {
	for (int i =0; i<listKomitent.size();i++ ){
		
	if (listKomitent.get(i).toString().toUpperCase().startsWith("NLB") || listKomitentZR.get(i).toString().equals("5620000000000000")
			|| listKomitent.get(i).toString().toUpperCase().startsWith("RAIFFEIS") || listKomitentZR.get(i).toString().equals("1610450011840051")){
		String[]OProm=listOpisPromjene.get(i).toString().toUpperCase().split("\\s+");
		for (int nb =0; nb<OProm.length; nb++){
			if (OProm[nb].toString().toUpperCase().contains("PROVIZIJA")) {
				listKonto.set(i,"553000"); 
				listKontoSifra.set(i," ");
				break;
			}
			else if (OProm[nb].toString().toUpperCase().contains("KAMATA")){
				listKonto.set(i,"567000");
				listKontoSifra.set(i," ");
				break;
			}
		}
	}
	}
}

public static void getKontoNB(){
	
	for (int i =0; i<listKomitent.size();i++ ){
		String[]OProm=null;
		String[] LKom=listKomitent.get(i).toString().split("\\s+");
	if (LKom[0].toString().toUpperCase().equals("NOVA") && LKom[1].toString().toUpperCase().equals("BANKA")|| LKom[0].toString().toUpperCase().equals("NOVA BANKA")){
		OProm=listOpisPromjene.get(i).toString().toUpperCase().split("\\s+");
		for (int nb =0; nb<OProm.length; nb++){
			if (OProm[nb].toString().toUpperCase().contains("OVERDRAFT")) {
				listKonto.set(i,"422010"); 
				listKontoSifra.set(i," ");
				break;
			}
			if (OProm[nb].toString().toUpperCase().contains("KAMATA")){
				listKonto.set(i,"567000");
				listKontoSifra.set(i," ");
				break;
			}
			if (OProm[nb].toString().toUpperCase().contains("PROVIZIJA")|| OProm[nb].toString().toUpperCase().contains("PROVIZIJE")){
				listKonto.set(i,"553000");
				listKontoSifra.set(i," ");
				break;
			}
			if (OProm[nb].toString().toUpperCase().contains("TRAJNI")||OProm[nb].toString().toUpperCase().contains("KREDIT")){
				listKonto.set(i,"422000");
				listKontoSifra.set(i," ");
				break;
			}
			if (OProm[nb].toString().toUpperCase().contains("KARTICA")){
				listKonto.set(i,"241920");
				listKontoSifra.set(i," ");
				break;
			}
			if (OProm[nb].toString().toUpperCase().contains("TROŠKOVI")){
				if (listKomitentZR.get(i).toString().equals("5550002800000078")){
					listKonto.set(i,"201000");
					listKontoSifra.set(i,"59");
					break;
				}
				else if(listKomitentZR.get(i).toString().equals("5550002820000023")){
					listKonto.set(i,"203000");
					listKontoSifra.set(i,"5905");
					break;
				}
				else if(listKomitentZR.get(i).toString().equals("5550002810000002")){
					listKonto.set(i,"202000");
					listKontoSifra.set(i,"5901");
					break;
				}
				
			}
		}
	} else if(LKom[0].toString().toUpperCase().equals("MAX") && LKom[1].toString().toUpperCase().equals("PAPIR")|| LKom[0].toString().toUpperCase().equals("MAXPAPIR")){
		if (listOpisPromjene.get(i).toString().toUpperCase().contains("KONVERZIJA")) {
			listKonto.set(i,"242900");
			listKontoSifra.set(i," ");
			break;
		}
		
	}
 }
}



public static void clearAllArrayLists(){
	bankaNaziv="";
	kontoIzvodaBanke="";
	vrstaNaloga="";
	StringToSplit.br.clear();
	StringToSplit.listKomitentZR.clear();
	StringToSplit.listOpisPromjene.clear();
	StringToSplit.listKomitent.clear();
	StringToSplit.listDuguje.clear();
	StringToSplit.listPotrazuje.clear();
	StringToSplit.listKonto.clear();
	StringToSplit.listKontoSifra.clear();
	StringToSplit.pocIn.clear();
	StringToSplit.podIn.clear();
	StringToSplit.racIn.clear();
	StringToSplit.zadIn.clear();		
	StringToSplit.mergeArrays.clear();
}

public static void getNewKotnoFromTable(String newKomitent, String newKonto,String newSifra){
	System.out.println("New populate PODACI u STRINGTOSPLIT su:"+ newKomitent+", "+ newKonto+" i "+ newSifra);
	listKonto.set(piROW, newKonto);
	listKomitent.set(piROW, newKomitent);
	listKontoSifra.set(piROW, newSifra);
	System.out.println("NOVA lista KOMITENATA : "+listKomitent);
}

}
