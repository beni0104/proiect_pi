package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import offers_reservations.Oferta;
import offers_reservations.Rezervare;

public class MainPage implements Initializable{

	DataSingleton data = DataSingleton.getInstance();
	@FXML
    private Button favButton;
    @FXML
    private Button addOfferBtn;
    @FXML
    private Button signOutBtn;
	@FXML
	private Label lUser;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchBtn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private GridPane offersContainer;
    private List<Oferta> lsOffers;
    private List<Oferta> favOffers;
    private List<Rezervare> rezervari;
    
    @FXML
    private Button homePageButton;
    @FXML
    private Button reservationsButton;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	if(data.getUid() != 1) {
    		addOfferBtn.setDisable(true);
    		addOfferBtn.setVisible(false);
    	}
    	
    	ObservableList<String> comboBoxList = FXCollections.observableArrayList("Nume" , "Pret crescator", "Pret descrescator");
		comboBox.setItems(comboBoxList);
		
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
				
				Button openOfferBtn = new Button(offer.getLocatie());
				openOfferBtn.setPrefHeight(165);
				openOfferBtn.setPrefWidth(150);
				openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
				spane.getChildren().add(openOfferBtn);
				openOfferBtn.setOnAction(openOffer -> {
					try {		
					Object obj = openOffer.getSource();
            		Button b = (Button)obj;
            		
            		
            				
            		
            		offersContainer.getChildren().clear();
            		FXMLLoader loader1 = new FXMLLoader();
        			loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
        			VBox vpane;
					vpane = loader1.load();
					OfertaDeschisaController odc = loader1.getController();
					for(Oferta of1: lsOffers)
            			if(of1.getLocatie().equals(b.getText()))
            				odc.setData(of1);
					offersContainer.add(vpane, 0, 1);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				});
				
				
				
				if(column == 3) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(8));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		offersContainer.setHgap(20);
	}
    
    @FXML
    void addOffer(ActionEvent event) {
    	try {		
    		offersContainer.getChildren().clear();
    		FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("addOfferScene.fxml"));
			VBox vpane;
			vpane = loader.load();
			AddOfferController aoc = loader.getController();
			aoc.set();
			offersContainer.add(vpane, 0, 1);
			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    private List<String> searchList(String searchWords, List<Oferta> list){
    	
    	List<String> searchWordsArray =  Arrays.asList(searchWords.trim().split(" "));
    	List<String> listOfWords = new ArrayList<String>();
    	for(Oferta o: list)
    		listOfWords.add(o.getLocatie());
    	
    	return listOfWords.stream().filter(input ->{
    		return searchWordsArray.stream().allMatch((word -> input.toLowerCase().contains(word.toLowerCase())));
    	}).collect(Collectors.toList());
    }
    
    @FXML
    void search(ActionEvent event) {
    	offersContainer.getChildren().clear();
        List<String> searchList = new ArrayList<>();
        searchList = searchList(searchBar.getText(), lsOffers);
        
    	int column = 0;
		int row = 1;
		try {
			for(Oferta offer : lsOffers) {
				if(searchList.contains(offer.getLocatie()))
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
					StackPane spane = loader.load();
					Oferte oferte = loader.getController();
					oferte.setData(offer);
				
					Button openOfferBtn = new Button(offer.getLocatie());
					openOfferBtn.setPrefHeight(165);
					openOfferBtn.setPrefWidth(150);
					openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
					spane.getChildren().add(openOfferBtn);
					openOfferBtn.setOnAction(openOffer -> {
						try {		
						Object obj = openOffer.getSource();
						Button b = (Button)obj;
            				
						offersContainer.getChildren().clear();
						FXMLLoader loader1 = new FXMLLoader();
						loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
						VBox vpane;
						vpane = loader1.load();
						OfertaDeschisaController odc = loader1.getController();
						for(Oferta of1: lsOffers)
							if(of1.getLocatie().equals(b.getText()))
								odc.setData(of1);
						offersContainer.add(vpane, 0, 1);
					
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});	
				
					if(column == 3) {
						column=0;
						row++;
					}
				
					offersContainer.add(spane, column++, row);
			
					GridPane.setMargin(spane, new Insets(8));
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		offersContainer.setHgap(20);
    }
    
    @FXML
    void enterPressedOnSearch(KeyEvent event) {
    		if(event.getCode() == KeyCode.ENTER) {
    			offersContainer.getChildren().clear();
    	        List<String> searchList = new ArrayList<>();
    	        searchList = searchList(searchBar.getText(), lsOffers);
    	        
    	    	int column = 0;
    			int row = 1;
    			try {
    				for(Oferta offer : lsOffers) {
    					if(searchList.contains(offer.getLocatie()))
    					{
    						FXMLLoader loader = new FXMLLoader();
    						loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
    						StackPane spane = loader.load();
    						Oferte oferte = loader.getController();
    						oferte.setData(offer);
    					
    						Button openOfferBtn = new Button(offer.getLocatie());
    						openOfferBtn.setPrefHeight(165);
    						openOfferBtn.setPrefWidth(150);
    						openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
    						spane.getChildren().add(openOfferBtn);
    						openOfferBtn.setOnAction(openOffer -> {
    							try {		
    							Object obj = openOffer.getSource();
    							Button b = (Button)obj;
    	            				
    							offersContainer.getChildren().clear();
    							FXMLLoader loader1 = new FXMLLoader();
    							loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
    							VBox vpane;
    							vpane = loader1.load();
    							OfertaDeschisaController odc = loader1.getController();
    							for(Oferta of1: lsOffers)
    								if(of1.getLocatie().equals(b.getText()))
    									odc.setData(of1);
    							offersContainer.add(vpane, 0, 1);
    						
    							} catch (IOException e1) {
    								e1.printStackTrace();
    							}
    						});	
    					
    						if(column == 3) {
    							column=0;
    							row++;
    						}
    					
    						offersContainer.add(spane, column++, row);
    				
    						GridPane.setMargin(spane, new Insets(8));
    					}
    				}
    			} catch(IOException e) {
    				e.printStackTrace();
    			}
    			offersContainer.setHgap(20);
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
    	    	//System.out.println(of.getImage());
    	    	of.setLocatie(rs.getString("Loc"));
    	    	of.setLocuriTotale(rs.getInt("ltotal"));
    	    	of.setPret(rs.getInt("pret"));
    	    	
    	    	ls.add(of);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		rs.close();
    		ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	Collections.sort(ls, new Comparator<Oferta>() {

            public int compare(Oferta o1, Oferta o2) {
                return o1.getLocatie().compareTo(o2.getLocatie());
            }
        });
    	
    	
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
    	    	of.setLocuriTotale(rs.getInt("ltotal"));
    	    	of.setPret(rs.getInt("pret"));
    	    	
    	    	ls.add(of);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	try {
    		rs.close();
    		ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return ls;   	
    }

    private List<Rezervare> reservationsList() {
    	List<Rezervare> ls = new ArrayList<>();
    	Connection conn = dbConnection.connect();
    	PreparedStatement ps = null;
    	ResultSet rs = null;  	
    	
    	try {
    		String sql = ("SELECT * FROM rezervari where uid = ?");
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, data.getUid());
    		rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			Rezervare res = new Rezervare();
    			res.setId(rs.getInt("id"));
    	    	res.setImagine(rs.getString("image"));
    	    	res.setLocatie(rs.getString("Loc"));
    	    	res.setPret(rs.getInt("pret"));
    	    	res.setStartDate(rs.getDate("startdate"));
    	    	res.setEndDate(rs.getDate("enddate"));
    	    	res.setNrLocuri(rs.getInt("nrlocuri"));
    	    	ls.add(res);
    		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	try {
    		rs.close();
    		ps.close();
			conn.close();
		} catch (SQLException e) {
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
				
				Button openOfferBtn = new Button(offer.getLocatie());
				openOfferBtn.setPrefHeight(165);
				openOfferBtn.setPrefWidth(150);
				openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
				spane.getChildren().add(openOfferBtn);
				openOfferBtn.setOnAction(openOffer -> {
					try {		
					Object obj = openOffer.getSource();
            		Button b = (Button)obj;
            		
            		
            				
            		
            		offersContainer.getChildren().clear();
            		FXMLLoader loader1 = new FXMLLoader();
        			loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
        			VBox vpane;
					vpane = loader1.load();
					OfertaDeschisaController odc = loader1.getController();
					for(Oferta of1: lsOffers)
            			if(of1.getLocatie().equals(b.getText()))
            				odc.setData(of1);
					offersContainer.add(vpane, 0, 1);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				});
				
				if(column == 3) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(8));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		offersContainer.setHgap(20);
    }

    @FXML
    void goToHome(ActionEvent event) {
    	comboBox.getSelectionModel().selectFirst();
    	offersContainer.getChildren().clear();
        lsOffers = offerList();
    	int column = 0;
		int row = 1;
		try {
			for(Oferta offer : lsOffers) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
				StackPane spane = loader.load();
				Oferte oferte = loader.getController();
				oferte.setData(offer);
				
				Button openOfferBtn = new Button(offer.getLocatie());
				openOfferBtn.setPrefHeight(165);
				openOfferBtn.setPrefWidth(150);
				openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
				spane.getChildren().add(openOfferBtn);
				openOfferBtn.setOnAction(openOffer -> {
					try {		
					Object obj = openOffer.getSource();
            		Button b = (Button)obj;
            		
            		
            				
            		
            		offersContainer.getChildren().clear();
            		FXMLLoader loader1 = new FXMLLoader();
        			loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
        			VBox vpane;
					vpane = loader1.load();
					OfertaDeschisaController odc = loader1.getController();
					for(Oferta of1: lsOffers)
            			if(of1.getLocatie().equals(b.getText()))
            				odc.setData(of1);
					offersContainer.add(vpane, 0, 1);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				});
				
				
				
				if(column == 3) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(8));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		offersContainer.setHgap(20);
    }

    @FXML
    void goToReservations(ActionEvent event) throws IOException {
    	offersContainer.getChildren().clear();
    	rezervari = new ArrayList<>(reservationsList());
		int column = 0;
		int row = 1;
		try {
			for(Rezervare rezervare : rezervari) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("RezervareTemplate.fxml"));
				HBox hbox = loader.load();
				rezervareController res = loader.getController();
				res.setData(rezervare);
				
				if(column == 1) {
					column=0;
					row++;
				}
				
				offersContainer.add(hbox, column++, row);
				GridPane.setMargin(hbox, new Insets(10));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		offersContainer.setHgap(20);
    }
       
    @FXML
    void selectComboBox(ActionEvent event) {
    	String selectedOption = comboBox.getSelectionModel().getSelectedItem().toString();
    	offersContainer.getChildren().clear();
    	switch(selectedOption){
    		case "Nume":
    		{
    	    	Collections.sort(lsOffers, new Comparator<Oferta>() {

    	            public int compare(Oferta o1, Oferta o2) {
    	                return o1.getLocatie().compareTo(o2.getLocatie());
    	            }
    	        });
    	    	break;
    		}
    		case "Pret crescator":
    		{
    			Collections.sort(lsOffers, new Comparator<Oferta>() {

    	            public int compare(Oferta o1, Oferta o2) {
    	                return o1.getPret() - o2.getPret();
    	            }
    	        });
    			break;
    		}
    		case "Pret descrescator":
    		{
    			Collections.sort(lsOffers, new Comparator<Oferta>() {

    	            public int compare(Oferta o1, Oferta o2) {
    	                return o2.getPret() - o1.getPret();
    	            }
    	        });
    			break;
    		}
    		
    	}
    	
    	int column = 0;
		int row = 1;
		try {
			for(Oferta offer : lsOffers) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("OferteTemplate.fxml"));
				StackPane spane = loader.load();
				Oferte oferte = loader.getController();
				oferte.setData(offer);
				
				Button openOfferBtn = new Button(offer.getLocatie());
				openOfferBtn.setPrefHeight(165);
				openOfferBtn.setPrefWidth(150);
				openOfferBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent");
				spane.getChildren().add(openOfferBtn);
				openOfferBtn.setOnAction(openOffer -> {
					try {		
					Object obj = openOffer.getSource();
            		Button b = (Button)obj;
            		
            		
            				
            		
            		offersContainer.getChildren().clear();
            		FXMLLoader loader1 = new FXMLLoader();
        			loader1.setLocation(getClass().getResource("OfertaDeschisaScene.fxml"));
        			VBox vpane;
					vpane = loader1.load();
					OfertaDeschisaController odc = loader1.getController();
					for(Oferta of1: lsOffers)
            			if(of1.getLocatie().equals(b.getText()))
            				odc.setData(of1);
					offersContainer.add(vpane, 0, 1);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				});
				
				
				
				if(column == 3) {
					column=0;
					row++;
				}
				
				offersContainer.add(spane, column++, row);
				GridPane.setMargin(spane, new Insets(8));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
    	
    	
    		
    }
    
    @FXML
    void signOut(ActionEvent event) throws IOException {
    	data.setUserName("");
    	data.setLocatie("");
    	data.setUid(-1);
    	Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
    	Scene loginpageScene = new Scene(root);
		Stage loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		loginStage.setScene(loginpageScene);
		loginStage.show();
    }

}
