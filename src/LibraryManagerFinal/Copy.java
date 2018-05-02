/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.scene.control.Alert;

/**
 *
 * @author Shah Ali
 */
public class Copy implements Runnable{
    File src;
    File dest;
    Thread t = new Thread(this);
//    public static void main(String[] args)
//    {
//    	File srcFolder = new File("c:\\mkyong");
//    	File destFolder = new File("c:\\mkyong-new");
//
//    	//make sure source exists
//    	if(!srcFolder.exists()){
//
//           System.out.println("Directory does not exist.");
//           //just exit
//           System.exit(0);
//
//        }else{
//
//           try{
//        	copyFolder(srcFolder,destFolder);
//           }catch(IOException e){
//        	e.printStackTrace();
//        	//error, just exit
//                System.exit(0);
//           }
//        }
//
//    	System.out.println("Done");
//    }
    Copy(){
        
    }
    public static void copyFolder(File src, File dest) throws IOException{
    	if(src.isDirectory()){

    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		}
    		//list all the directory contents
    		String files[] = src.list();

    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}

    	}
        else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest);

    	        byte[] buffer = new byte[1024];

    	        int length;
    	        //copy the file content in bytes
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
    	        in.close();
    	        out.close();
    	}
    }
    Copy(File src, File dest){
        this.src = src;
        this.dest = dest;
        t.start();
    }

    @Override
    public void run() {
        try {
            copyFolder(src, dest);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR).setTitle("Unexpected error occured !!!\n"
                    + "Backup Failed");
        }
    }
}
