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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SignUp {
    @FXML
    private PasswordField tfPasswordConfirmation;
    @FXML
    private Button goToLoginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUserName;

    @FXML
    void enterPressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    		tfPassword.requestFocus();
    }
    
    @FXML
    void enterPressedOnPassword(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER)
    		tfPasswordConfirmation.requestFocus();
    }
    
    @FXML
    void enterPressedOnConfirm(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		String name = tfUserName.getText();
        	String password = tfPassword.getText();
        	String passwordConfirm =  tfPasswordConfirmation.getText();

        	if(name.equals("") || password.equals("") || passwordConfirm.equals(""))
        	{
        		AlertBox.display("Error", "Introduceti username, parola si confirmarea parolei!");
        	}
        	else
        	{
        		Connection conn = dbConnection.connect();
            	PreparedStatement ps = null;
            	ResultSet rs = null;
            	try {
            		String idselect ="SELECT max(id) from users";
            		PreparedStatement psid = conn.prepareStatement(idselect);
            		ResultSet rsid = psid.executeQuery();
            		int uid = rsid.getInt("max(id)");
            		rsid.close();
            		psid.close();
            		
            		String sql = "SELECT * from Users where username = ?";
            		ps = conn.prepareStatement(sql);
            		ps.setString(1, name);
            		rs = ps.executeQuery();
            		
            		
        //Inserting a user in db    		
            		if(!rs.isBeforeFirst())
            		{
            			if(password.equals(passwordConfirm))
            			{
            				PreparedStatement psinsert = null;
            				try {
            					rs.close();
            					ps.close();
            					String sqlinsert = "INSERT INTO Users(ID, username, Password) VALUES(?,?,?)";
            					psinsert = conn.prepareStatement(sqlinsert);
            					psinsert.setInt(1, ++uid);
            					psinsert.setString(2, name);
            					psinsert.setString(3, password);
            					psinsert.execute();
            					AlertBox.display("Sign up", "You signed up succesfully!");
            					psinsert.close();
            					conn.close();
            				} catch(SQLException e) {
            					System.out.println(e.toString());
            				}
            			}else
            				AlertBox.display("Error", "Passwords doesn't match!");
            		}else
            		{
            			AlertBox.display("Error", "Username already exists!");
            		}
            	}catch (SQLException e) {
            		System.out.println(e.toString());
            	}
        	}
        	
        }
    }
    
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
    	String passwordConfirm =  tfPasswordConfirmation.getText();
    	if(name.equals("") || password.equals("") || passwordConfirm.equals(""))
    	{
    		AlertBox.display("Error", "Introduceti username, parola si confirmarea parolei!");
    	}
    	else
    	{
    		Connection conn = dbConnection.connect();
        	PreparedStatement ps = null;
        	ResultSet rs = null;
        	try {
        		String idselect ="SELECT max(id) from users";
        		PreparedStatement psid = conn.prepareStatement(idselect);
        		ResultSet rsid = psid.executeQuery();
        		int uid = rsid.getInt("max(id)");
        		rsid.close();
        		psid.close();
        		
        		String sql = "SELECT * from Users where username = ?";
        		ps = conn.prepareStatement(sql);
        		ps.setString(1, name);
        		rs = ps.executeQuery();
        		
        		
    //Inserting a user in db    		
        		if(!rs.isBeforeFirst())
        		{
        			if(password.equals(passwordConfirm))
        			{
        				PreparedStatement psinsert = null;
        				try {
        					rs.close();
        					ps.close();
        					String sqlinsert = "INSERT INTO Users(ID, username, Password) VALUES(?,?,?)";
        					psinsert = conn.prepareStatement(sqlinsert);
        					psinsert.setInt(1, ++uid);
        					psinsert.setString(2, name);
        					psinsert.setString(3, password);
        					psinsert.execute();
        					AlertBox.display("Sign up", "You signed up succesfully!");
        					psinsert.close();
        					conn.close();
        				} catch(SQLException e) {
        					System.out.println(e.toString());
        				}
        			}else
        				AlertBox.display("Error", "Passwords doesn't match!");
        		}else
        		{
        			AlertBox.display("Error", "Username already exists!");
        		}
        	}catch (SQLException e) {
        		System.out.println(e.toString());
        	}
    	} 	
    }

}
