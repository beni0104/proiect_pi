package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import offers_reservations.Oferta;

public class OfertaDeschisaController{
	DataSingleton data = DataSingleton.getInstance();
	private String img;
	private int lt;
	
    @FXML
    private Button btnFav;
    @FXML
    private Button btnRezervare;
    @FXML
    private Button deleteOfferBtn;
    @FXML
    private ImageView offerImage;
    @FXML
    private Label labelLocatie;
    String locatie;
    @FXML
    private Label labelPret;
    int pret;
    @FXML
    private Label labelDescriere;
    String descriere;
    
    @FXML
    private Spinner<Integer> spinner;
    int spinnerValue;
    
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    void reserve(ActionEvent event) {
    	
    	if(startDate.getValue() == null || endDate.getValue() == null)
    		AlertBox.display("Error", "Introduceti o perioada de timp valida!");
    	else {
    		LocalDate ldStart = startDate.getValue();
        	LocalDate ldEnd = endDate.getValue();
        	
        	ZoneId defaultZoneId = ZoneId.systemDefault();
        	Date start = Date.from(ldStart.atStartOfDay(defaultZoneId).toInstant());
        	Date end = Date.from(ldEnd.atStartOfDay(defaultZoneId).toInstant());
        	
        	Date now = new Date();
        	SimpleDateFormat df = new SimpleDateFormat("DD/MM/YYYY");
        	
    		if((df.format(now).compareTo(df.format(start)) > 0)
    				|| (startDate.getValue().toString().compareTo(endDate.getValue().toString()) > 0))
    			AlertBox.display("Error", "Introduceti o perioada de timp valida!");
    		else
    		{
            	spinnerValue = spinner.getValue();
            	if(start.compareTo(end) > 0)
            		AlertBox.display("Error", "Introduceti o perioada de timp valida!");
            	else
            	{
            		int locuriOcupate = 0;
            		Connection conn = dbConnection.connect();
            		PreparedStatement ps = null;
            		ResultSet rs = null;
            		
            		try {
            			String idselect ="SELECT max(id) from rezervari";
                		PreparedStatement psid = conn.prepareStatement(idselect);
                		ResultSet rsid = psid.executeQuery();
                		int resid = rsid.getInt("max(id)");
                		psid.close();
                		rsid.close();
                		
            			String sql = "SELECT * from rezervari WHERE loc = ?";
            			ps = conn.prepareStatement(sql);
            			ps.setString(1, locatie);
            			rs = ps.executeQuery();
            			boolean duplicate = false;
            			
            			while(rs.next())
            			{
            				Date retrievedStartDate = rs.getDate("startdate");
            				Date retrievedEndDate = rs.getDate("enddate");
            				int uid = rs.getInt("uid");
            				if((retrievedStartDate.compareTo(start) >= 0 && retrievedStartDate.compareTo(end) < 0 ) ||
            						(retrievedEndDate.compareTo(start) > 0 && retrievedEndDate.compareTo(end) <= 0 ))
            					locuriOcupate += rs.getInt("nrlocuri");
            				if(retrievedStartDate.compareTo(start) == 0 && retrievedEndDate.compareTo(end) == 0 && uid == data.getUid())
            					duplicate = true;
            			}

            			if(!duplicate)
            			{
            				if(lt - locuriOcupate >= spinnerValue)
                			{
                				PreparedStatement psinsert = null;
                				rs.close();
                				ps.close();
                				String sqlinsert = "INSERT INTO rezervari (id, uid, loc, startdate, enddate, nrlocuri, pret, image) VALUES(?,?,?,?,?,?,?,?)";
                				psinsert = conn.prepareStatement(sqlinsert);
                				psinsert.setInt(1, ++resid);
                				psinsert.setInt(2, data.getUid());
                				psinsert.setString(3, locatie);
                				psinsert.setDate(4, java.sql.Date.valueOf(ldStart));
                				psinsert.setDate(5, java.sql.Date.valueOf(ldEnd));
                				psinsert.setInt(6, spinnerValue);
                				psinsert.setInt(7, pret);
                				psinsert.setString(8, img);
                				psinsert.execute();
                					
                					
                				AlertBox.display("Rezervat!",
                						"Rezervarea din:\n" + ldStart + " / " + ldEnd
                						+ "\npentru " + spinnerValue + " persoane a fost facuta cu succes!");
                				psinsert.close();
                				conn.close();
                			}
                			else
                				AlertBox.display("Error", "Nu sunt destule locuri disponibile in data aleasa!");
            			}
            			else
            			{
            				AlertBox.display("Error", "Aveti deja o rezervare pentru data selectata!");
            			}
            			
            			
            		}catch (SQLException e) {
            			System.out.println(e.toString());
            		}
            		
            	}
        	}	
    	}
    }
    
    void setData(Oferta oferta) {
		btnFav.setOnMouseEntered(e -> {if(btnFav.getText().equals("Sterge de la favorite"))
			btnFav.setStyle("-fx-background-color: -fx-shadow-highlight-color; -fx-text-fill: red");
		else
			btnFav.setStyle("-fx-background-color: -fx-shadow-highlight-color; -fx-text-fill: black");
		});
		btnFav.setOnMouseExited(e -> {if(btnFav.getText().equals("Sterge de la favorite"))
			btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: red");
		else
			btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
		});
		
		deleteOfferBtn.setStyle("-fx-background-color: #DC0000;");
		deleteOfferBtn.setOnMouseEntered(e -> {deleteOfferBtn.setStyle("-fx-background-color: #850000;");});
		deleteOfferBtn.setOnMouseExited(e -> {deleteOfferBtn.setStyle("-fx-background-color: #DC0000;");});
		
		if(data.getUid() != 1) {
			deleteOfferBtn.setDisable(true);
			deleteOfferBtn.setVisible(false);
    	}
		
		btnRezervare.setOnMouseEntered(e -> btnRezervare.setStyle("-fx-background-color: -fx-shadow-highlight-color; -fx-text-fill: black"));
		btnRezervare.setOnMouseExited(e ->  btnRezervare.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black"));
		
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
		valueFactory.setValue(1);
		spinner.setValueFactory(valueFactory);
		
    	img = oferta.getImage();
    	//"file:///C:/Users/szekr/eclipse-workspace/ProiectBooking/src/images/no-photo-available.png"
    	Image image = new Image(oferta.getImage());
    	offerImage.setImage(image);
    	locatie = oferta.getLocatie();
    	labelLocatie.setText(oferta.getLocatie());
    	pret = oferta.getPret();
    	labelPret.setText("$" + Integer.toString(oferta.getPret()) + "/noapte");
    	labelDescriere.setText("	"+ oferta.getDescriere());
    	labelDescriere.setWrapText(true);
    	descriere = oferta.getDescriere();
    	lt = oferta.getLocuriTotale();
    	
    	Connection conn = dbConnection.connect();   	
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		try {
			String sqlread = "SELECT username, loc from favorite where username = ? and loc = ?";
			ps = conn.prepareStatement(sqlread);
			ps.setString(1, data.getUserName());
			ps.setString(2, locatie);
			rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()) {
				btnFav.setText("Sterge de la favorite");
				btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: red");
				
			}
			else {
				btnFav.setText("Adauga la favorite");
				btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
				
			}
			rs.close();
			ps.close();
			conn.close();
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
    }

    @FXML
    void manageFav(ActionEvent event) {
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		try {
			String sqlread = "SELECT username, loc from favorite where username = ? and loc = ?";
			ps = conn.prepareStatement(sqlread);
			ps.setString(1, data.getUserName());
			ps.setString(2, locatie);
			rs = ps.executeQuery();
			
			if(!rs.isBeforeFirst())
			{
				rs.close();
				ps.close();
				PreparedStatement psinsert = null;
				String sqlinsert = "INSERT INTO favorite(username, loc, descriere, image, ltotal, pret) VALUES(?,?,?,?,?,?)";
				psinsert = conn.prepareStatement(sqlinsert);
				psinsert.setString(1, data.getUserName());
				psinsert.setString(2, locatie);
				psinsert.setString(3, descriere);
				psinsert.setString(4, img);
				psinsert.setInt(5, lt);
				psinsert.setInt(6, pret);
				psinsert.execute();
				AlertBox.display("Added to favourites", "Oferta a fost adaugata la favorite!");
				psinsert.close();
				conn.close();
				btnFav.setText("Sterge de la favorite");
				btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: red");
				
			}
			else {
				rs.close();
				ps.close();
				PreparedStatement psdelete = null;
				String sqldelete = "DELETE FROM favorite where username = ? AND loc = ?";
				psdelete = conn.prepareStatement(sqldelete);
				psdelete.setString(1, data.getUserName());
				psdelete.setString(2, locatie);
				psdelete.execute();
				AlertBox.display("Deleted from favourites", "Oferta a fost stearsa de la favorite");
				psdelete.close();
				conn.close();	
				btnFav.setText("Adauga la favorite");
				btnFav.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
				
			}
			
		} catch(SQLException e) {
			System.out.println(e.toString());
		}
    }

    @FXML
    void deleteOffer(ActionEvent event) {
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		try {
			String sqlread = "SELECT loc from offers where loc = ?";
			ps = conn.prepareStatement(sqlread);
			ps.setString(1, locatie);
			rs = ps.executeQuery();
			
			if(rs.isBeforeFirst()){
				rs.close();
				ps.close();
				PreparedStatement psdelete = null;
				String sqldelete = "DELETE FROM offers where loc = ?";
				String sqldelete1 = "DELETE FROM favorite where loc = ?";
				psdelete = conn.prepareStatement(sqldelete);
				psdelete.setString(1, locatie);
				psdelete.execute();
				
				psdelete = conn.prepareStatement(sqldelete1);
				psdelete.setString(1, locatie);
				psdelete.execute();
				
				AlertBox.display("Succes!", "Oferta a fost stearsa!");
				psdelete.close();
				conn.close();
			}
			
		} catch(SQLException e) {
			System.out.println(e.toString());
		}
    }
}
