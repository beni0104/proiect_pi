package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {

    @FXML
    private Button signUpButton;
    
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUserName;
    
    DataSingleton data = DataSingleton.getInstance();


    @FXML
    void login(ActionEvent event) {
    	String name = tfUserName.getText();
    	String password = tfPassword.getText();
    	
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;  	
    	try {
    		String sql = "SELECT * from Users where username = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, name);
    		rs = ps.executeQuery();
    		
    		if(!rs.isBeforeFirst())
    		{
    			AlertBox.display("Error", "User not found!");
    		}else
    		{
    			while(rs.next()) {
    				String retrievedPassword = rs.getString("password");
    				if(retrievedPassword.equals(password)) {
/* Implement login*/ 
						try {
							data.setUserName(rs.getString("Username"));
							Parent root = FXMLLoader.load(getClass().getResource("MainPageScene.fxml"));						
							Scene MainPageScene = new Scene(root);
							Stage MainPage = (Stage)((Node)event.getSource()).getScene().getWindow();
    					
							MainPage.setScene(MainPageScene);
							MainPage.show();
						} catch (IOException e) {
							e.printStackTrace();
						}
    				}

    				else
    					AlertBox.display("Error", "Incorrect password!");
    			}
    		}
    	}catch (SQLException e) {
    		System.out.println(e.toString());
    	}
  
    }
    
    @FXML
    void goToSignUp(ActionEvent event) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getResource("SignUpScene.fxml"));
    	Scene loginpageScene = new Scene(root);
		Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		loginStage.setScene(loginpageScene);
		loginStage.show();
    }

}
