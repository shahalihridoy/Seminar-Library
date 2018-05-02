/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibraryManagerFinal;

/**
 *
 * @author Shah Ali
 */
public class User {
    String name;
    String reg;
    String session;
    String borrowDate;
    int id;
    
    int numberofbook;
    User(String name,String reg,String session){
        this.name = name;
        this.reg = reg;
        this.session = session;
    }
    
    User(String name,int id,int number){
        this.name = name;
        this.id = id;
        this.numberofbook = number;
    }
    
    User(String name,int id,String borrowDate){
        this.name = name;
        this.id = id;
        this.borrowDate = borrowDate;
    }
    
    public String getBorrowDate(){
        return borrowDate;
    }
    public int getId(){
        return id;
    }
    public int getNumberofbook(){
        return numberofbook;
    }
    
    public String getName(){
        return name;
    }

    
    public String getReg(){
        return reg;
    }
   
    public String getSession(){
        return session;
    }

}

