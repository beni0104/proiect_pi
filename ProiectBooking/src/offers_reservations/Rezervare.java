package offers_reservations;

import java.sql.Date;

public class Rezervare {
	String imagine;
	String locatie;
	Date StartDate;
	Date EndDate;
	int pret;
	int nrLocuri;
	int id;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImagine() {
		return imagine;
	}
	public void setImagine(String imagine) {
		this.imagine = imagine;
	}
	public String getLocatie() {
		return locatie;
	}
	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public int getPret() {
		return pret;
	}
	public void setPret(int pret) {
		this.pret = pret;
	}
	public int getNrLocuri() {
		return nrLocuri;
	}
	public void setNrLocuri(int nrLocuri) {
		this.nrLocuri = nrLocuri;
	}
	
	
}
