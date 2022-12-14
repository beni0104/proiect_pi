package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import offers_reservations.Oferta;

public class OfertaDeschisaController {

    @FXML
    private Button btnFav;

    @FXML
    private Button btnRezervare;

    @FXML
    private ImageView offerImage;

    @FXML
    private Label labelLocatie;

    @FXML
    private Label labelPret;

    @FXML
    private TextField tfDescriere;

    @FXML
    void addToFav(ActionEvent event) {
    	
    }

    @FXML
    void reserve(ActionEvent event) {
    	
    }
    
    void setData(Oferta oferta) {
    	Image image = new Image(getClass().getResourceAsStream(oferta.getImage()));
    	offerImage.setImage(image);
    	labelLocatie.setText(oferta.getLocatie());
    	labelPret.setText(Integer.toString(oferta.getPret()));
    	tfDescriere.setText("asdfdsc");
    }

	@Override
	public String toString() {
		return "OfertaDeschisaController [btnFav=" + btnFav + ", btnRezervare=" + btnRezervare + ", offerImage="
				+ offerImage + ", labelLocatie=" + labelLocatie + ", labelPret=" + labelPret + ", tfDescriere="
				+ tfDescriere + "]";
	}
    
    
}
