package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import offers_reservations.Oferta;

public class Oferte {
    @FXML
    private Button offerButton;

    @FXML
    private ImageView offerImage;

    @FXML
    private Label offerLocation;

    @FXML
    private Label offerPrice;
    
    public Oferta offer;
    DataSingleton data = DataSingleton.getInstance();

    @FXML
    void openOffer(ActionEvent event) throws IOException {
    	
    	data.setLocatie(offer.getLocatie());
    	Parent root = FXMLLoader.load(getClass().getResource("OpenOfferScene.fxml"));						
		Scene MainPageScene = new Scene(root);
		Stage MainPage = (Stage)((Node)event.getSource()).getScene().getWindow();
		MainPage.setScene(MainPageScene);
		MainPage.show();

    }
    
    public void setData(Oferta oferta){
    	
    	Image image = new Image(getClass().getResourceAsStream(oferta.getImage()));
    	offerImage.setImage(image);
    	offerLocation.setText(oferta.getLocatie());
    	offerPrice.setText(Integer.toString(oferta.getPret()));
    	offer = oferta;
    	
    }
	
    
    
    
    
    
    
    
    
    
    
    
}
