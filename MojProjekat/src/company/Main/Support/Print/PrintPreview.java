package company.Main.Support.Print;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PrintPreview {
	private StringProperty rb;
	private StringProperty promjena;
	private StringProperty komit;
	private StringProperty dug;
	private StringProperty potr;
	private StringProperty kont;
	private StringProperty kontSif;


	public PrintPreview(){
		this.rb= new SimpleStringProperty();
		this.promjena=new SimpleStringProperty();
		this.komit=new SimpleStringProperty();
		this.dug=new SimpleStringProperty();
		this.potr=new SimpleStringProperty();
		this.kont=new SimpleStringProperty();
		this.kontSif=new SimpleStringProperty();
	}


	public String getRBroj(){
		return rb.get();
	}

	public void setRBroj(String rebr){
		this.rb.set(rebr);
	}

	public StringProperty RBrojProperty () {
		return rb;
	}
	public String getPromj(){
		return promjena.get();
	}

	public void setPromj (String pro){
		this.promjena.set(pro);
	}

	public StringProperty PromjProperty () {
		return promjena;
	}

	public String getKomit(){
		return komit.get();
	}

	public void setKomit(String komit){
		this.komit.set(komit);
	}

	public StringProperty KomitProperty () {
		return komit;
	}
	public String getDug(){
		return dug.get();
	}

	public void setDug(String duguje){
		this.dug.set(duguje);
	}

	public StringProperty DugProperty () {
		return dug;
	}
	public String getPotr(){
		return potr.get();
	}

	public void setPotr(String potrazuje){
		this.potr.set(potrazuje);
	}

	public StringProperty PotrProperty () {
		return potr;
	}
	public String getKont(){
		return kont.get();
	}

	public void setKont(String konto){
		this.kont.set(konto);
	}

	public StringProperty KontProperty () {
		return kont;
	}

	public String getKontSif(){
		return kontSif.get();
	}

	public void setKontSif(String kSifra){
		this.kontSif.set(kSifra);
	}

	public StringProperty KontSifProperty () {
		return kontSif;
	}

}
