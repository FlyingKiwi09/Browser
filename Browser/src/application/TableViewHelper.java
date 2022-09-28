package application;

import java.util.Date;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebHistory;

public class TableViewHelper {

//	public TableViewHelper() {
//		// TODO Auto-generated constructor stub
//	}
	
	
	// returns Title TableColumn
	public static TableColumn<WebHistory.Entry, String> getTitleColumn() 
    {
        TableColumn<WebHistory.Entry, String> titleCol = new TableColumn<>("Title");
        PropertyValueFactory<WebHistory.Entry, String> titleCellValueFactory = new PropertyValueFactory<>("title");
        titleCol.setCellValueFactory(titleCellValueFactory);
        return titleCol;
    }
	// returns Date TableColumn
	public static TableColumn<WebHistory.Entry, Date> getDateColumn() 
    {
        TableColumn<WebHistory.Entry, Date> dateCol = new TableColumn<>("Date");
        PropertyValueFactory<WebHistory.Entry, Date> titleCellValueFactory = new PropertyValueFactory<>("lastVisitedDate");
        dateCol.setCellValueFactory(titleCellValueFactory);
        return dateCol;
    }

}
