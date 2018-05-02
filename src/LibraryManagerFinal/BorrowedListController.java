/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */

public class BorrowedListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<?,?> namecol;
    @FXML
    private TableColumn<?,?> datecol;
    @FXML
    private TableColumn<?,?> idcol;
    Connection con;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        load();
    }    
    private void load(){
        try{
            con = Main.getConnection();
//            ResultSet rs = con.createStatement().executeQuery("select borrow.id,bookList.name,borrow.borrowDate from borrow,bookList where borrow.reg='"+regbox.getText()+"' and bookList.id = borrow.id");
        }
        catch(Exception e){
            
        }
    }
    
}
