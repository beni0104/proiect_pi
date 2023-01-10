package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddOfferController {

    @FXML
    private Button addOfferBtn;

    @FXML
    private Spinner<Integer> spinnerNrLocuri;

    @FXML
    private Spinner<Integer> spinnerPret;

    @FXML
    private TextArea taDescriere;

    @FXML
    private TextField tfImagine;

    @FXML
    private TextField tfLocatie;

    void set() {
    	addOfferBtn.setStyle("-fx-background-color: #379237;");
    	addOfferBtn.setOnMouseEntered(e -> {addOfferBtn.setStyle("-fx-background-color: #379237;"); });
    	addOfferBtn.setOnMouseExited(e -> {addOfferBtn.setStyle("-fx-background-color: #54B435;"); });
    	
    	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 100, 10);
		spinnerPret.setValueFactory(valueFactory);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 30, 1);
		spinnerNrLocuri.setValueFactory(valueFactory1);
		
    }
    @FXML
    void addOffer(ActionEvent event) {
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	try {
    		String sql = "SELECT loc from offers where loc = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, tfLocatie.getText());
    		rs = ps.executeQuery();
    		    		
    		if(!rs.isBeforeFirst())
    		{
    			PreparedStatement psinsert = null;
				try {
					rs.close();
					ps.close();
					String sqlinsert = "INSERT INTO offers(loc, descriere, image, ltotal, pret) VALUES(?,?,?,?,?)";
					psinsert = conn.prepareStatement(sqlinsert);
					psinsert.setString(1, tfLocatie.getText());
					psinsert.setString(2, taDescriere.getText());
					psinsert.setString(3, tfImagine.getText());
					psinsert.setInt(4, spinnerNrLocuri.getValue());
					psinsert.setInt(5, spinnerPret.getValue());
					psinsert.execute();
					AlertBox.display("Succes!", "Ati adaugat oferta cu succes!");
					psinsert.close();
					conn.close();
				} catch(SQLException e) {
					System.out.println(e.toString());
				}
    		}else {
    			AlertBox.display("Error", "Locatia exista deja!");
    		}
    	}catch (SQLException e) {
    		System.out.println(e.toString());
    	}
    }

}
