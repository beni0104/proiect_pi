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

public class SignUp {
	public static int uid = 1000;
    @FXML
    private Button goToLoginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUserName;

    @FXML
    void goToLogin(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
    	Scene loginpageScene = new Scene(root);
		Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		loginStage.setScene(loginpageScene);
		loginStage.show();
    }

    @FXML
    void signUp(ActionEvent event) {
    	String name = tfUserName.getText();
    	String password = tfPassword.getText();
    	
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		String idselect ="SELECT max(id) from users";
    		PreparedStatement psid = conn.prepareStatement(idselect);
    		ResultSet rsid = psid.executeQuery();
    		int uid = rsid.getInt("max(id)");
    		
    		String sql = "SELECT * from Users where username = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, name);
    		rs = ps.executeQuery();
    		
    		
//Inserting a user in db    		
    		if(!rs.isBeforeFirst())
    		{
    			PreparedStatement psinsert = null;
    			try {
    				String sqlinsert = "INSERT INTO Users(ID, username, Password) VALUES(?,?,?)";
    				psinsert = conn.prepareStatement(sqlinsert);
    				psinsert.setInt(1, ++uid);
    				psinsert.setString(2, name);
    				psinsert.setString(3, password);
    				psinsert.execute();
    				AlertBox.display("Sign up", "You signed up succesfully!");
    			} catch(SQLException e) {
    				System.out.println(e.toString());
    			}
    		}else
    		{
    			AlertBox.display("Error", "Username already exists!");
    		}
    	}catch (SQLException e) {
    		System.out.println(e.toString());
    	}
    }

}
