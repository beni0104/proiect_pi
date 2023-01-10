package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import offers_reservations.Oferta;

public class Oferte {

    @FXML
    private ImageView offerImage;

    @FXML
    private Label offerLocation;

    @FXML
    private Label offerPrice;
    
    public Oferta offer;
    DataSingleton data = DataSingleton.getInstance();

 
    
    public void setData(Oferta oferta){
    	Image image = new Image(oferta.getImage());
    	offerImage.setImage(image);
    	offerLocation.setText(oferta.getLocatie());
    	offerPrice.setText("$"+ Integer.toString(oferta.getPret()));
    	offer = oferta;
    }
	
    
    
    
    
    
    
    
    
    
    
    
}
