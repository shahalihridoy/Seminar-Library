/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Shah Ali
 */
public class Main extends Application {

    @FXML
    private static BorderPane root;
    private static Scene scene;

    public static BorderPane get() {
        return root;
    }

    public static Scene getScene() {
        return scene;
    }
    static Connection con;
    static ResultSet rs;

    @Override
    public void start(Stage stage) throws Exception {
        File f = new File("lib/xoxRxt.exe");
        if (!f.exists()) {
            TextInputDialog dialogue = new TextInputDialog();
            dialogue.setTitle("Password Recovery");
            dialogue.setContentText("Enter your recovery password");
            dialogue.setHeaderText("Enter some text such as nickname,\nbirth date,graduate school etc.\nThis text will further be used \nto recover your password");
            Optional<String> result = dialogue.showAndWait();

            if (!result.get().equals("")) {
                BufferedWriter rite = new BufferedWriter(new FileWriter(f));
                rite.write(result.get());
                rite.newLine();
                DirectoryChooser folder = new DirectoryChooser();
                folder.setTitle("Choose Backup Folder");
                rite.write(folder.showDialog(null).getAbsolutePath());
                rite.close();
            } else {
                ShowAlert.showAlert("Insert recovery code & Try Again");
                System.exit(0);
            }
        }
        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//        root.setTop(FXMLLoader.load(getClass().getResource("header.fxml")));
        root.setCenter(FXMLLoader.load(getClass().getResource("login.fxml")));
//        root.setCenter(FXMLLoader.load(getClass().getResource("header.fxml")));
//        ShowAlert.showConfirmation("test is running");
//        root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//        root.setCenter(FXMLLoader.load(getClass().getResource("book.fxml")));
//        Parent root = FXMLLoader.load(getClass().getResource("student.fxml"));
//        MenuBar box = FXMLLoader.load(getClass().getResource("menu.fxml"));
//        BorderPane pan = FXMLLoader.load(getClass().getResource("Main_Window.fxml"));
//        root.setTop(box);
//        root.setCenter(pan);
        scene = new Scene(root);
//        stage.centerOnScreen();
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        stage.setMaximized(true);
        stage.setTitle("Fet Saminar Library");
        stage.setMinWidth(950);
        stage.setMinHeight(650);
//        stage.setMaximized(true);
        stage.show();
    }

    public static Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        con = DriverManager.getConnection("jdbc:derby:fet;create=false");
        //delete from here create table definition
//            Statement stmn = con.createStatement();
//            stmn.execute("create table student ( name varchar(30) not null,reg varchar(12) not null primary key,session varchar(10) not null)");
//            stmn.execute("create table booklist ( name varchar(30) not null,id int  not null primary key, numberofbook int not null,available int not null)");
//            stmn.execute("create table borrow ( reg varchar(12) not null, id int not null,foreign key(reg) references student(reg),foreign key (id) references booklist(id),borrowDate varchar(12) not null)");
        //end here
//        ShowAlert.showAlert("done");
//            stmn.execute("alter table bookList drop available");
//            stmn.close();
        return con;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File fet = new File("fet");
        if (!fet.exists()) {
            //DATABASE
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                con = DriverManager.getConnection("jdbc:derby:fet;create=true");
                Statement stmn = con.createStatement();
                stmn.execute("create table student ( name varchar(150) not null,reg varchar(12) not null primary key,session varchar(10) not null)");
                stmn.execute("create table booklist ( name varchar(550) not null,id int  not null primary key, numberofbook int not null)");
                stmn.execute("create table borrow ( reg varchar(12) not null, id int not null,foreign key(reg) references student(reg)on delete cascade,foreign key (id) references booklist(id)on delete cascade,primary key(reg,id),borrowDate varchar(12) not null)");
                stmn.execute("create table login ( username varchar(50) not null,password varchar(50) not null)");
                stmn.execute("create table date (reg varchar(12),id int,borrowDate varchar(12),foreign key(reg) references student(reg)on delete cascade,foreign key (id) references booklist(id)on delete cascade)");
                stmn.execute("insert into login ( username,password ) values('admin','1234')");
                stmn.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                    
            }
        }

        launch(args);
        try {
            if (LoginController.getA() == 1) {
                BufferedReader read = new BufferedReader(new FileReader(new File("lib/xoxRxt.exe")));
                read.readLine();
                File f = new File("fet");
//                    String s= f.getAbsolutePath();
//                    s = s.substring(0, s.length()-4);
                File exist = new File(read.readLine() + "\\fet");
                if (!exist.exists()) {
                    exist.mkdir();
                }
                Copy copy = new Copy(f, exist);
            }
        } catch (Exception e) {
            ShowAlert.showAlert("Unknown error occured while updating !!!");
        }
    }

}
