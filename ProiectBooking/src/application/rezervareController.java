package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import offers_reservations.Rezervare;

public class rezervareController {
	DataSingleton data = DataSingleton.getInstance();
	int id;
    @FXML
    private Button cancelBtn;

    @FXML
    private Label fromDate;
    
    @FXML
    private Label labelLocatie;

    @FXML
    private Label nrPersoane;

    @FXML
    private ImageView offerImage;

    @FXML
    private Label toDate;
    @FXML
    private Label totalPrice;
    
    void setData(Rezervare res) {
    	Image image = new Image(res.getImagine());
    	long diff;
    	if(res.getEndDate().compareTo(res.getStartDate()) == 0) {
    		diff = 1;
    	}
    	else {
    		long diffInMillies = Math.abs(res.getEndDate().getTime() - res.getStartDate().getTime());
    		diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    	}
    	
    	offerImage.setImage(image);
    	labelLocatie.setText(res.getLocatie());
    	nrPersoane.setText(Integer.toString(res.getNrLocuri()));
    	totalPrice.setText(Integer.toString((int) (res.getPret() * diff * res.getNrLocuri())) + "$");
    	fromDate.setText(res.getStartDate().toString());
    	toDate.setText(res.getEndDate().toString());
    	id = res.getId();
    	
    	cancelBtn.setOnMouseEntered(e -> {cancelBtn.setStyle("-fx-background-color: #850000;");
		});
		cancelBtn.setOnMouseExited(e -> {cancelBtn.setStyle("-fx-background-color: #DC0000;");
		});
    }
    
    
    @FXML
    void cancelReservation(ActionEvent event) {
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	
    	try {
    		String sql = "DELETE FROM rezervari where id = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, id);
    		ps.execute();
    		AlertBox.display("Reservation cancelled!", "Rezervarea a fost anulata cu succes!\nPentru a vedea lista cu rezervari actualizate\napasati pe butonul Rezervari.");
    		ps.close();
    		conn.close();
    	}catch(SQLException e) {
    		System.out.println(e.toString());
    	}
    }

}

