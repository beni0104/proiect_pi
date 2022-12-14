package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import offers_reservations.Oferta;

public class OpenOfferController {

    @FXML
    private Button btnFav;
    @FXML
    private Button btnRezervare; 
    @FXML
    private Button favButton;
    @FXML
    private Button homePageButton;
    @FXML
    private Button reservationsButton;

    
    @FXML
    private Label labelLocatie;
    @FXML
    private Label labelPret;
    @FXML
    private ImageView offerImage;
    @FXML
    private TextField tfDescriere; 

    DataSingleton data = DataSingleton.getInstance();
    private Oferta of;
    @FXML
	void setData() {
    	of = new Oferta();
    	String loc = data.getLocatie();
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;  	
    	
    	try {
    		String sql = ("SELECT * FROM offers where loc = ?");
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, loc);
    		rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			
    			of.setDescriere(rs.getString("descriere"));
    	    	of.setImage(rs.getString("image"));
    	    	of.setLocatie(rs.getString("Loc"));
    	    	of.setLocuriDisponibile(rs.getInt("ldisponibil"));
    	    	of.setLocuriTotale(rs.getInt("ltotal"));
    	    	of.setPret(rs.getInt("pret"));
    	    	
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	Image image = new Image(getClass().getResourceAsStream(of.getImage()));
    	offerImage.setImage(image);
    	labelLocatie.setText(of.getLocatie());
    	labelPret.setText(Integer.toString(of.getPret()));
    	tfDescriere.setText(of.getDescriere());
    	
    }

    @FXML
    void addToFav(ActionEvent event) {
    	Connection conn = dbConnection.connect();
    	PreparedStatement psinsert = null;
		try {
			String sqlinsert = "INSERT INTO favorite(username, loc, descriere, image, ltotal, ldisponibil, pret) VALUES(?,?,?,?,?,?,?)";
			psinsert = conn.prepareStatement(sqlinsert);
			psinsert.setString(1, data.getUserName());
			psinsert.setString(2, of.getLocatie());
			psinsert.setString(3, of.getDescriere());
			psinsert.setString(4, of.getImage());
			psinsert.setInt(5, of.getLocuriTotale());
			psinsert.setInt(6, of.getLocuriDisponibile());
			psinsert.setInt(7, of.getPret());
			psinsert.execute();
			AlertBox.display("Added to favourites", "Offer added to favourites!");
		} catch(SQLException e) {
			System.out.println(e.toString());
		}
    }

    @FXML
    void reserve(ActionEvent event) {

    }

    @FXML
    void goToHome(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("MainPageScene.fxml"));						
		Scene MainPageScene = new Scene(root);
		Stage MainPage = (Stage)((Node)event.getSource()).getScene().getWindow();
		MainPage.setScene(MainPageScene);
		MainPage.show();
	
    }

    @FXML
    void goToReservations(ActionEvent event) {
    	
    }
    @FXML
    void goToFav(ActionEvent event) {

    }


    

}
