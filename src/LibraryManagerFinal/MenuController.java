/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import LibraryManagerFinal.StudentListController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Shah Ali
 */
public class MenuController{

    @FXML
    private void home() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("book.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void student() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("student.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void viewStudentList() throws Exception{
        StudentListController.setString("select * from student");
        TableView pan = FXMLLoader.load(getClass().getResource("studentList.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void viewList() throws Exception{
        StudentListController.setString("select * from student");
        TableView pan = FXMLLoader.load(getClass().getResource("bookList.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void search() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("search.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void addBook() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("addBook.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void history() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("history.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void clear() throws Exception{
        Connection con = Main.getConnection();
        Statement stmn = con.createStatement();
        stmn.execute("delete from date");
        stmn.close();
        con.close();
        ShowAlert.showInformation("Successfully Cleared History !!!");
    }
    @FXML
    private void admin() throws Exception{
        AnchorPane pan = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void about() throws Exception{
        StackPane pan = FXMLLoader.load(getClass().getResource("about.fxml"));
        Main.get().setCenter(pan);
    }
    @FXML
    private void help() throws Exception{
        TextArea pan = FXMLLoader.load(getClass().getResource("help.fxml"));
        Main.get().setCenter(pan);
    }
    
    @FXML
    private void changeBackup(){
        try{
        BufferedReader read = new BufferedReader(new FileReader(new File("lib/xoxRxt.exe")));
        String scode = read.readLine();
        String newfolder = new DirectoryChooser().showDialog(null).getAbsolutePath();
        if(!newfolder.equals("")){
            BufferedWriter rite = new BufferedWriter(new FileWriter(new File("lib/xoxRxt.exe")));
            rite.write(scode);
            rite.newLine();
            rite.write(newfolder);
            ShowAlert.showInformation("Backup folder has been changed successfully !!!");
            read.close();
            rite.close();
        }
    }
        catch(Exception es){  
//            ShowAlert.showAlert("Unknown error !!!");
        }
    }
    
    @FXML
    private void restoreDatabase() throws Exception{
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Choose backup folder to restore");
        File f = choose.showDialog(null).getAbsoluteFile();
        if(f.getName().equals("fet")){
//        BufferedReader read = new BufferedReader(new FileReader(new File("lib/xoxRxt.exe")));
//        read.readLine();
//        String s = read.readLine();
//        File f = new File(s+"\\fet");
            Copy.copyFolder(f, new File("fet"));
            ShowAlert.showInformation("Data has been restored successfully !!!\nPlease, restart your application !!!");

    }
        else
            ShowAlert.showAlert("Folder doesn't contain backup file !!!");
    }
}
