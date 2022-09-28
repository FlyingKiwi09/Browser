package application;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;


public class Browser extends Application {
	

	private String home = "www.google.com";
	private BorderPane root = new BorderPane(); // root
	private HBox mainMenu = new HBox(); //  top of root
	private TabPane tabPane = new TabPane(); // center of root
	private VBox themeChooser = new VBox(); // will be right of root
	private Style style = Style.DEFAULT;

	
	private ArrayList<MyTab> tabs = new ArrayList<MyTab>(); // list of tabs each contains it's own WebView
	
	
	@Override
	public void start(Stage primaryStage) {
		
		newTab(); // creates a new tab and adds to the tabPane
		createMainMenu(); // creates buttons and sets their on actions and adds these to the mainmenu
		createThemeChooser(); // creates a theme chooser VBox, does not add it to the root
		setStyles();
		
		root.setTop(mainMenu);
		root.setCenter(tabPane);
		
		// set color scheme
		
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
		setStyles();
	}
	
	public void createMainMenu() {
		
		// new tab button
		Button newTabButton = new Button("New Tab");
		newTabButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				newTab();
			}
			
		}) ;
		
		// display history button
		Button history = new Button("History");
		history.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				displayHistory();
			}
			
		}) ;
		
		Button theme = new Button("Theme");
		theme.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {	
				toggleThemesDisplay();
			}
			
		}) ;
		
		// add buttons to mainMenu
		mainMenu.getChildren().addAll(newTabButton, history, theme);
		Insets buttonInsets = new Insets(5, 0, 5, 0);
		mainMenu.setMargin(history, buttonInsets);
		mainMenu.setMargin(newTabButton, buttonInsets);
		mainMenu.setMargin(theme, buttonInsets);
	}
	

	public void createThemeChooser() {
		Button defaultTheme = new Button("Default");
		defaultTheme.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null))); // set to white of default theme
		defaultTheme.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				style = Style.DEFAULT;
				setStyles();
			}
			
		}) ;
		
		
		Button pink = new Button("Pink");
		defaultTheme.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, null, null))); // set to pink of default theme
		defaultTheme.setStyle(("-fx-border-color: red; -fx-background-color: lightpink;"));
		defaultTheme.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				style = Style.PINK;
				setStyles();
			}
			
		}) ;
		
		themeChooser.getChildren().addAll(defaultTheme, pink);
	}
	
	public void displayHistory() {
		
		Stage histroyStage = new Stage();
		VBox box = new VBox();
		
		// create a table view with a list of WebHistory entries
		TableView<WebHistory.Entry> historyTable = new TableView<>();
		
		// create a combined list of history entries from all tabs
		ArrayList<WebHistory.Entry> fullHistory = new ArrayList<>();
		for (MyTab tab: tabs) {
			for (WebHistory.Entry entry : tab.getEntries()) {
				fullHistory.add(entry);
			}
		}
		// add the full history (rows) to the TableView
		historyTable.getItems().addAll(fullHistory);
		
		// add columns to the TableView
		historyTable.getColumns().addAll(TableViewHelper.getURLColumn(), TableViewHelper.getDateColumn());
		
//		https://riptutorial.com/javafx/example/27946/add-button-to-tableview?fbclid=IwAR06vYPI2stH6LaHUsWEYOfIXoooWINfaGn0fX9OR6_R9Ea1GzeiMVBWYLA#:~:text=Example%23,setCellFactory(Callback%20value)%20method.&text=In%20this%20application%20we%20are,selected%20and%20its%20information%20printed
//		addButtonToTable(historyTable); // calls method to create buttons
		
		
		// Set the column resize policy to constrained resize policy
		historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Set the Placeholder for an empty table
		historyTable.setPlaceholder(new Label("No visible columns and/or data exist."));
		
		box.getChildren().add(historyTable);
		
		box.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
		
		Scene scene = new Scene(box);
		histroyStage.setScene(scene);
		histroyStage.show();
		
	}
	
	public void toggleThemesDisplay() {
		
		if (root.getRight() == null) {
			root.setRight(themeChooser);
		} else {
			root.setRight(null);
		}
		
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
	
	public void setStyles() {
		
		Color backgroundColor = null;
		Color buttonColor = null;
		
		// set colors based on style variable
		if (style == Style.PINK) {		
			backgroundColor = Color.PALEVIOLETRED;
			buttonColor = Color.LIGHTPINK;
		} else if (style == Style.BLUE) {
			backgroundColor = Color.DODGERBLUE;
			buttonColor = Color.SKYBLUE;
		} else if (style == Style.GREEN) {
			backgroundColor = Color.GREEN;
			buttonColor = Color.LIGHTGREEN;
		} else if (style == Style.DEFAULT) {
		}
		
		
		// set backgrounds and buttons to colors
		root.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
		tabPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
		for (Node node :mainMenu.getChildren()) {
			if (node instanceof Button) {
				((Button) node).setBackground(new Background(new BackgroundFill(buttonColor, null, null)));
				
			}
		}
		
		for (MyTab tab : tabs) {
			for (Node node : tab.getControlsHBox().getChildren()) {
				if (node instanceof Button) {
					((Button) node).setBackground(new Background(new BackgroundFill(buttonColor, null, null)));
					
				}
			}
		}
		
	}
	

}
