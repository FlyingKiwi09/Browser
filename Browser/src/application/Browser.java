package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
//	private HBox tabBar = new HBox(); 
	private TabPane tabPane = new TabPane();
	private HashMap<Tab, WebView> tabViewMap = new HashMap<Tab, WebView >();
	private ArrayList<MyTab> tabs = new ArrayList<MyTab>();
	
	
	@Override
	public void start(Stage primaryStage) {
	
		
		
		newTab();
		HBox mainMenu = new HBox();
		Button newTabButton = new Button("New Tab");
		newTabButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				newTab();
			}
			
		}) ;
		
		mainMenu.getChildren().add(newTabButton);
		
		VBox root = new VBox();
		root.getChildren().addAll (mainMenu, tabPane);
		
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
		MyTab newTab = new MyTab();
		newTab.load("http://"+home);
		tabs.add(newTab);
		tabPane.getTabs().add(newTab);

	}
	
	public void reloadTabs(WebView view, String URL) {
//		activeURL = textField.getText();
		active = view;
		
	}
	
	
	
	private void load(WebView webView) {
		String newURL = "http://" + textField.getText();
		webView.getEngine().load(newURL);
//		tabButtonMap.get(active).setText(newURL);
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
