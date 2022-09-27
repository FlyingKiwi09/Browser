package application;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class MyTab extends Tab {
	

	private VBox banner = new VBox();
	private WebView webView;
	private Button go;
	private TextField textField = new TextField();
	private double zoomLevel;

	public MyTab() { 
		
		zoomLevel = 1;
		
		go = new Button();
		go.setText("go");
		go.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				load("http://"+textField.getText());
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
		
		textField = new TextField();
		webView = new WebView();
	
		HBox controlsHBox = new HBox();
		controlsHBox.getChildren().addAll(back, forward, reload, textField, go, zoomOut, zoomIn);
		
		
		
		// The WebEngine manages web pages non-visually (loading, reloading, error handling etc)

		this.setContent(banner);
		banner.getChildren().add(controlsHBox);
		banner.getChildren().add(this.webView);
		
	}
	
	public void load(String URL) {
		this.webView.getEngine().load(URL);
		this.setText(URL);
	}
	
	private void forward() {
		WebHistory history = webView.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		history.go(1);
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		setTabText();
	}
	
	private void reload() {
		webView.getEngine().reload();
	}
	
	private void backward() {
		WebHistory history = webView.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
		history.go(-1);
		textField.setText(entries.get(history.getCurrentIndex()).getUrl());
		setTabText();
		
	}
	
	private void setTabText() {
		WebHistory history = webView.getEngine().getHistory();
		ObservableList<WebHistory.Entry> entries = history.getEntries();
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
