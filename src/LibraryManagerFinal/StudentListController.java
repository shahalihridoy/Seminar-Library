/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class StudentListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<User> table;
    @FXML
    private TableColumn<?,?> namecol;
    @FXML
    private TableColumn<?,?> regcol;
    @FXML
    private TableColumn<?,?> sessioncol;
    private static String s;
    private ObservableList<User> list = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    public static void setString( String s){
        StudentListController.s = s;
    }
    public static String getString(){
        return s;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        regcol.setCellValueFactory(new PropertyValueFactory<>("reg"));
        sessioncol.setCellValueFactory(new PropertyValueFactory<>("session"));
//        namecol.setOnEditCommit(null);
        loadData();
    }    
    private void loadData(){
        list.clear();
        try{
            Connection con = Main.getConnection();
            Statement stmn = con.createStatement();
            ResultSet rs = stmn.executeQuery(StudentListController.getString());
            while(rs.next()){
//                System.out.println(rs.getString("name")+"   "+rs.getString("reg")+"   "+rs.getString("session"));
                list.add(new User(rs.getString("name"), rs.getString("reg"), rs.getString("session")));
            }
            table.setItems(list);
            stmn.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
