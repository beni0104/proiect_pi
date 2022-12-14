package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import offers_reservations.Oferta;

public class MainPage implements Initializable{

	DataSingleton data = DataSingleton.getInstance();
	@FXML
    private Button favButton;
	@FXML
	    private Label lUser;
    @FXML
    private GridPane offersContainer;
    private List<Oferta> lsOffers;
    private List<Oferta> favOffers;
    
    @FXML
    private Button homePageButton;
    @FXML
    private Button reservationsButton;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	lUser.setText(data.getUserName());
    	
		lsOffers = new ArrayList<>(offerList());
		int column = 0;
		int row = 1;
		try {
			for(Oferta offer : lsOffers) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
				StackPane spane = loader.load();
				Oferte oferte = loader.getController();
				oferte.setData(offer);
				
				if(column == 5) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(10));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
    
    private List<Oferta> offerList() {
    	List<Oferta> ls = new ArrayList<>();
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;  	
    	
    	try {
    		String sql = ("SELECT * FROM offers");
    		ps = conn.prepareStatement(sql);
    		rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			Oferta of = new Oferta();
    			of.setDescriere(rs.getString("descriere"));
    	    	of.setImage(rs.getString("image"));
    	    	of.setLocatie(rs.getString("Loc"));
    	    	of.setLocuriDisponibile(rs.getInt("ldisponibil"));
    	    	of.setLocuriTotale(rs.getInt("ltotal"));
    	    	of.setPret(rs.getInt("pret"));
    	    	
    	    	ls.add(of);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	

    	return ls;   	
    }
    
    private List<Oferta> favList() {
    	List<Oferta> ls = new ArrayList<>();
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;  	
    	
    	try {
    		String sql = ("SELECT * FROM favorite where username = ?");
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, data.getUserName());
    		rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			Oferta of = new Oferta();
    			of.setDescriere(rs.getString("descriere"));
    	    	of.setImage(rs.getString("image"));
    	    	of.setLocatie(rs.getString("Loc"));
    	    	of.setLocuriDisponibile(rs.getInt("ldisponibil"));
    	    	of.setLocuriTotale(rs.getInt("ltotal"));
    	    	of.setPret(rs.getInt("pret"));
    	    	
    	    	ls.add(of);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	

    	return ls;   	
    }

    @FXML
    void goToFav(ActionEvent event) throws IOException {
    	offersContainer.getChildren().clear();
    	favOffers = new ArrayList<>(favList());
		int column = 0;
		int row = 1;
		try {
			for(Oferta offer : favOffers) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
				StackPane spane = loader.load();
				Oferte oferte = loader.getController();
				oferte.setData(offer);
				
				if(column == 5) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(10));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    public void deschideOferta()
    {
    	System.out.println("OK");
    }

    @FXML
    void goToHome(ActionEvent event) {
    	offersContainer.getChildren().clear();
    	lsOffers = new ArrayList<>(offerList());
		int column = 0;
		int row = 1;
		try {
			for(Oferta offer : lsOffers) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
				StackPane spane = loader.load();
				Oferte oferte = loader.getController();
				oferte.setData(offer);
				
				if(column == 5) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(10));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void goToReservations(ActionEvent event) {

    }

}
