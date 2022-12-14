package offers_reservations;

public class Oferta {
	int pret;
	String locatie;
	String image;
	String descriere;
	int locuriTotale;
	int locuriDisponibile;
	
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public int getPret() {
		return pret;
	}
	public void setPret(int pret) {
		this.pret = pret;
	}
	public String getLocatie() {
		return locatie;
	}
	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}
	public String getDescriere() {
		return descriere;
	}
	public void setDescriere(String descriere) {
		this.descriere = descriere;
	}
	public int getLocuriTotale() {
		return locuriTotale;
	}
	public void setLocuriTotale(int locuriTotale) {
		this.locuriTotale = locuriTotale;
	}
	public int getLocuriDisponibile() {
		return locuriDisponibile;
	}
	public void setLocuriDisponibile(int locuriDisponibile) {
		this.locuriDisponibile = locuriDisponibile;
	}
	
	
}
