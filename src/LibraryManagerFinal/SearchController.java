/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Shah Ali
 */
public class SearchController implements Initializable {

    @FXML
    private TextField regbox;
    @FXML
    private TextField idbox;
    @FXML
    private TextField namebox;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<?,?> namecol;
    @FXML
    private TableColumn<?,?> datecol;
    @FXML
    private TableColumn<?,?> idcol;
    Connection con;
    ResultSet rs;
    ObservableList<String> list = FXCollections.observableArrayList();
    ObservableList<User> data = FXCollections.observableArrayList();
    EventHandler<Event> handler = new EventHandler<Event>() {
        @Override
        public void handle(Event event) {
            
        }
    };
    /**
     * Initializes the controller class.
     */
    public void shortcut(){
        Main.getScene().setOnKeyPressed(handler);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        shortcut();
        regbox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    searchReg();
                }
            }
        });
        idbox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    searchID();
                }
            }
        });
        namebox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    searchName();
                }
            }
        });
        
        rs = null;
        list.clear();
        try{
            con = Main.getConnection();
            rs = con.createStatement().executeQuery("select name from bookList where name like '%"+namebox.getText()+"%'");
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            rs.close();
            con.close();
        }
        catch(Exception e){
            ShowAlert.showAlert("Unexpected error !!!");
        }
        finally{
        TextFields.bindAutoCompletion(namebox, list);
        list.clear();
        }
        //cell value factory of column
    }    
    private void load(String s){
        idcol.setText("Book ID");
        namecol.setText("Book Name");
        datecol.setText("Borrowing Date");
        try{
            idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
            datecol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
            
            con = Main.getConnection();
            rs = con.createStatement().executeQuery(s);
            while(rs.next()){
//                2013337023
                data.add(new User(rs.getString("name"), rs.getInt("id"), rs.getString("borrowDate")));
//                System.out.println(rs.getString("name")+"     "+rs.getString("id")+"     "+rs.getString("borrowDate"));
            }
            table.setItems(data);
            rs.close();
            con.close();
        }
        catch(Exception e){
            ShowAlert.showAlert("Unexpected error !!!");
        }
    }
    
    @FXML
    private void searchReg() {
        data.clear();
        String s = "select bookList.name,borrow.id,borrow.borrowDate from borrow,bookList where borrow.reg='"+regbox.getText()+"' and bookList.id = borrow.id";
        load(s);
        regbox.setText("");
//        try {
//            con = Main.getConnection();
//            rs = null;
//            rs = con.createStatement().executeQuery("select borrow.id,bookList.name,borrow.borrowDate from borrow,bookList where borrow.reg='"+regbox.getText()+"' and bookList.id = borrow.id");
////            ResultSet r = con.createStatement().executeQuery("select");
//            while(rs.next()){
//                
//                //System.out.println(rs.getInt("id")+"     "+rs.getString("name")+"     "+rs.getString("borrowDate"));
//            }
//            con.close();
//        } catch (Exception ex) {
//            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void searchID() {
        data.clear();
        idcol.setCellValueFactory(new PropertyValueFactory<>("name"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("reg"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("session")); //we are sending it to session
        if(!idbox.getText().equals(""))
        try {
            String s = "select student.name,borrow.reg,borrow.borrowDate from borrow,Student where borrow.id="+Integer.parseInt(idbox.getText())+"and borrow.reg = student.reg";
            con = Main.getConnection();
            rs = con.createStatement().executeQuery(s);
            while(rs.next()){
                data.add(new User(rs.getString("name"), rs.getString("reg"), rs.getString("borrowDate")));
            }
            table.setItems(data);
            idcol.setText("Name");
            namecol.setText("Registration");
            datecol.setText("Borrowing Date");
            con.close();
            rs.close();
            idbox.setText("");
        } catch (Exception ex) {
            ShowAlert.showAlert("Please insert number in BookID field !!!");
        }
    }

    @FXML
    private void searchName() {
        rs = null;
        data.clear();
        idcol.setCellValueFactory(new PropertyValueFactory<>("name"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("reg"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("session")); //we are sending it to session
        idcol.setText("Name");
        namecol.setText("Registration");
        datecol.setText("Borrowing Date");
        try {
            String s = "select student.name,student.reg,borrow.borrowDate from student,borrow,bookList where bookList.name = '"+namebox.getText()+"' and borrow.reg = student.reg and borrow.id = bookList.id";
            con = Main.getConnection();
            rs = con.createStatement().executeQuery(s);
            while(rs.next()){
                data.add(new User(rs.getString("name"), rs.getString("reg"), rs.getString("borrowDate")));
            }
            table.setItems(data);
            con.close();
            rs.close();
            namebox.setText("");
        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void getList(){
//        rs = null;
//        list.clear();
//        try{
//            con = Main.getConnection();
//            rs = con.createStatement().executeQuery("select name from bookList where name like '%"+namebox.getText()+"%'");
//            while(rs.next()){
//                list.add(rs.getString("name"));
//            }
//            rs.close();
//            con.close();
//        }
//        catch(Exception e){
//            ShowAlert.showAlert("Unexpected error !!!");
//        }
//        finally{
////        TextFields.bindAutoCompletion(namebox, null);
//        TextFields.bindAutoCompletion(namebox, list);
//        list.clear();
//        }
    }
}
