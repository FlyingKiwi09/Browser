package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class MyTab extends Tab {
	

	private VBox banner = new VBox();
	private WebView webView = new WebView();;
	private Button go;
	private TextField textField = new TextField();
	private double zoomLevel;
	private WebHistory history;
	private ObservableList<WebHistory.Entry> entries;
	private HBox controlsHBox = new HBox();
	
	String URL;

	public MyTab() { 
		history = webView.getEngine().getHistory();
		entries = history.getEntries();
		zoomLevel = 1;
		
		go = new Button();
		go.setText("go");
		go.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				load(textField.getText());
				textField.textProperty().bind(webView.getEngine().locationProperty()); // rebind the textField to the webpage URL
			}
			
		}) ;

		
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
		
		Button zoomIn = new Button("+");
		zoomIn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				zoomIn();
			}
			
		});
		
		Button zoomOut = new Button("-");
		zoomOut.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				zoomOut();
			}
			
		});
		
		
		
		
		// bind the title of the webpage to the tab text
		this.textProperty().bind(webView.getEngine().titleProperty());
		
		//Set growth parameters
		
		
		
		
		// set up the textField
		
		// always grow
		HBox.setHgrow(textField, Priority.ALWAYS);

		// bind the url of the webpage to the textField
		textField.textProperty().bind(webView.getEngine().locationProperty());
		
		// unbind when the user clicks the textField to enter a URL
		textField.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				textField.textProperty().unbind();
			}
			
		});
		
		// set textField to search on enter
		textField.setOnKeyPressed(new EventHandler<KeyEvent>() { // used KeyPressed because KeyCode for enter is undefined when using KeyTyped
			@Override
			public void handle(KeyEvent evt) {
			    if(evt.getCode() == KeyCode.ENTER) {
			    	load(textField.getText());
					textField.textProperty().bind(webView.getEngine().locationProperty()); // rebind the textField to the webpage URL          
			    }     
			}
			
		});
	
	
		
		controlsHBox.getChildren().addAll(back, forward, reload, textField, go, zoomOut, zoomIn);
		controlsHBox.getStyleClass().add("menu");
		
		
		// The WebEngine manages web pages non-visually (loading, reloading, error handling etc)

		this.setContent(banner);
		banner.getChildren().add(controlsHBox);
		banner.getChildren().add(this.webView);
		
	}
	
	public void load(String userInput) {
		
		// if the input doesn't already contain http: or https: add this and store it to a new variable
		String addedInput = userInput;
		if (!(userInput.contains("http://") || userInput.contains("https://"))) {
			addedInput = "http://"+ userInput;
		} 
		
		// attempt to open the a connection with a URL using the addedInput 
		// note this may or may not have had http / https added depending on whether it was already present
		// if the URL is valid the webView loads with that URL
		// if not valid the webView opens with a google search of the ORIGINAL userInput
		try {
	        new URL(addedInput).openStream().close();
	        this.webView.getEngine().load(addedInput);
		} catch (MalformedURLException | UnknownHostException e) {
    		this.webView.getEngine().load("http://www.google.com/search?q=" + userInput);       
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void forward() {
		
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		history.go(1);
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		setTabText();
	}
	
	private void reload() {
		webView.getEngine().reload();
	}
	
	private void backward() {
	 
		
		history.go(-1);
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		setTabText();
		
	}
	
	private void setTabText() {
		
		
		String text = entries.get(history.getCurrentIndex()).getTitle();
		if (text.length() > 10) {
			text = text.substring(0, 10);
			text = text+"...";
		}
		this.setText(text);
	}
	
	private void zoomIn() {
		zoomLevel = zoomLevel+0.1;
		this.webView.setZoom(zoomLevel);
	}
	
	private void zoomOut() {
		zoomLevel = zoomLevel-0.1;
		this.webView.setZoom(zoomLevel);
	}

	
	// getters and setters
	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public ObservableList<WebHistory.Entry> getEntries() {
		return entries;
	}

	public void setEntries(ObservableList<WebHistory.Entry> entries) {
		this.entries = entries;
	}

	public HBox getControlsHBox() {
		return controlsHBox;
	}

	public void setControlsHBox(HBox controlsHBox) {
		this.controlsHBox = controlsHBox;
	}
	
	
	
	
}
