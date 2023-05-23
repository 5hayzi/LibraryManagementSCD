package com.mycompany.librarymanagement;

import javax.swing.JOptionPane;

public class LibraryController {
    DbHandler data;
    private int mCurrentIndex;
    
    LibraryController(){
        data = new DbHandler();
    }
    
    
    public void addbook(String bName, String bId, int bCopies, String bGenre){
        data.addBook(bName, bId, bCopies, bGenre);
    }
    public void addUser(String uName, String userID, String uPass, String gender){
        data.addUser(uName, userID, uPass, gender);
    }
    public void updateBook(String bookId, String newName, int newCopy){
        data.updateBook(bookId, newName, newCopy);       
    }
    public void issueBook(String StuName, String StuId, int issueDate, String bookId){
        data.issueBook(StuName, StuId, issueDate, bookId);       
    }
    public void returnBook(String bookId){
        data.returnBook(bookId);       
    }
}
