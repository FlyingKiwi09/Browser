package application;
	
import java.util.HashMap;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;


public class Browser extends Application {
	
	private WebView active = new WebView();
	private String home = "www.google.com";
	private TextField textField = new TextField();
	private HBox tabBar = new HBox(); 
	
	private HashMap<WebView, Button> tabButtonMap = new HashMap<WebView, Button>();
	
	@Override
	public void start(Stage primaryStage) {
	
		Button launch = new Button();
		launch.setText("launch");
		launch.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				load();
			}
			
		}) ;
		
		newTab();
		
		Button forward = new Button("Forward");
		forward.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				forward();
			}
			
		});
		
		Button back = new Button("Back");
		back.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				backward();
			}
			
		});
		
		Button reload = new Button("Reload");
		reload.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				reload();
			}
			
		});
		
		HBox controlsHBox = new HBox();
		controlsHBox.getChildren().addAll(back, forward, reload, textField, launch);
		
		VBox root = new VBox();
		root.getChildren().addAll(tabBar, controlsHBox, active);
		
		//Set growth parameters
		VBox.setVgrow(active, Priority.ALWAYS);
		HBox.setHgrow(textField, Priority.ALWAYS);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void newTab() {
		WebView newWebView = new WebView();
		// The WebEngine manages web pages non-visually (loading, reloading, error handling etc)
		String newURL = "http://" + home;
		newWebView.getEngine().load(newURL);
		active = newWebView;
		
		// create a tabButton
		Button newTabButton = new Button(newURL); // sets button text to the title of the current page.
		tabBar.getChildren().add(newTabButton);
		
		tabButtonMap.put(newWebView, newTabButton);
		
	}
	
	private void load() {
		String newURL = "http://" + textField.getText();
		active.getEngine().load(newURL);
		tabButtonMap.get(active).setText(newURL);
	}
	
	private void forward() {
		WebHistory history = active.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		history.go(1);
	}
	
	private void reload() {
		active.getEngine().reload();
	}
	
	private void backward() {
		WebHistory history = active.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		history.go(-1);
	}
	
}
