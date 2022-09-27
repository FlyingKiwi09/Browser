package application;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
				load("http://"+textField.getText());
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
		
		
		// set up the textField

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
			    	load("http://"+textField.getText());
					textField.textProperty().bind(webView.getEngine().locationProperty()); // rebind the textField to the webpage URL          
			    }     
			}
			
		});
	
	
		HBox controlsHBox = new HBox();
		controlsHBox.getChildren().addAll(back, forward, reload, textField, go, zoomOut, zoomIn);
		
		
		
		// The WebEngine manages web pages non-visually (loading, reloading, error handling etc)

		this.setContent(banner);
		banner.getChildren().add(controlsHBox);
		banner.getChildren().add(this.webView);
		
	}
	
	public void load(String URL) {
		this.webView.getEngine().load(URL);
//		this.setText(URL);
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
	
	
	
}
