package com.mycompany.librarymanagement;

import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DbHandler {
   private Connection connection;
   private Statement statement;

    public DbHandler() {
        String Table1 = "Issue";
        String Table2 = "Users";
        String Table3 = "Books";
        connection = connect();
        if (connection == null)return;
        String query1 = "CREATE TABLE IF NOT EXISTS "+ Table1 +" (" +
            "StudentId VARCHAR(255) NOT NULL," +
            "StudentName VARCHAR(255) NOT NULL," +
            "IssueDate INT NOT NULL PRIMARY KEY," +
            "BookId VARCHAR(255) NOT NULL);";
        String query2 = "CREATE TABLE IF NOT EXISTS "+ Table2 +" (" +
            "UserId VARCHAR(255) NOT NULL PRIMARY KEY," +
            "UserName VARCHAR(255) NOT NULL," +
            "Password INT NOT NULL," +
            "Gender VARCHAR(255) NOT NULL);";
        String query3 = "CREATE TABLE IF NOT EXISTS "+ Table3 +" (" +
            "BookId VARCHAR(255) NOT NULL PRIMARY KEY," +
            "BookName VARCHAR(255) NOT NULL," +
            "BookCopy INT NOT NULL," +
            "BookGenre VARCHAR(255) NOT NULL);";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);
            stmt.executeUpdate(query3);
        } catch (SQLException e) {
            System.out.println("Could not create table! Error: " + e.getMessage());
        }
        }

        private Connection connect() {
             System.out.println("Connecting...");

             try {
                 Class.forName("com.mysql.cj.jdbc.Driver");
                 String url = "jdbc:mysql://localhost:3306/mydatabase";
                 Connection conn = DriverManager.getConnection(url, "root", "");
                 statement = conn.createStatement();
                 System.out.println("Connected");
                 return conn; // Return the Connection object
             } catch (ClassNotFoundException e) {
                 e.printStackTrace();
             } catch (SQLException e) {
                 e.printStackTrace();
             }

    return null; // Return null if there's an exception
}

    public void addBook(String bName, String bId, int bCopies, String bGenre) {
        String addBookQuery = "INSERT INTO Books (BookName, BookId, BookCopy, BookGenre) VALUES ('" + bName + "', '" + bId + "', '" + bCopies + "', '" + bGenre + "')";

        try {
            statement.executeUpdate(addBookQuery);
            JOptionPane.showMessageDialog(null,"Book added successfully." );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String uName, String userID, String uPass, String gender){
        String addUserQuery = "INSERT INTO Users (UserName, UserId, Password, Gender) VALUES ('" + uName + "', '" + userID + "', '" + uPass + "', '" + gender + "')";

        try {
            statement.executeUpdate(addUserQuery);
         JOptionPane.showMessageDialog(null,"User added successfully." );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showUsers() {
        String showUsersQuery = "SELECT * FROM users";

        try {
            ResultSet resultSet = statement.executeQuery(showUsersQuery);

            Vector<String> columnNames = new Vector<>();
            columnNames.add("User ID");
            columnNames.add("Name");
            columnNames.add("Gender");

            Vector<Vector<String>> data = new Vector<>();

            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("UserId"));
                row.add(resultSet.getString("UserName"));
                row.add(resultSet.getString("Gender"));

                data.add(row);
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(null, scrollPane, "Users", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showBooks() {
        String showBooksQuery = "SELECT * FROM books";

        try {
            ResultSet resultSet = statement.executeQuery(showBooksQuery);

            Vector<String> columnNames = new Vector<>();
            columnNames.add("Book ID");
            columnNames.add("Book Name");
            columnNames.add("Book Genre");
            columnNames.add("Total Copies");

            Vector<Vector<String>> data = new Vector<>();

            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("BookId"));
                row.add(resultSet.getString("BookName"));
                row.add(resultSet.getString("BookGenre"));
                row.add(String.valueOf(resultSet.getInt("BookCopy")));

                data.add(row);
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(null, scrollPane, "Books", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(String bookId, String newName, int newCopy) {
        String updateBookQuery = "UPDATE books SET BookName = '" + newName + "', BookCopy = '" + newCopy + "' WHERE BookId = " + bookId;

        try {
            int rowsAffected = statement.executeUpdate(updateBookQuery);
            if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null,"Book Updated successfully." );
            } else {
            JOptionPane.showMessageDialog(null,"Book Not Found" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void issueBook(String StuName, String StuId, int issueDate, String bookId) {
        String issueBookQuery = "INSERT INTO Issue (BookId, StudentName, StudentId, IssueDate) VALUES ('" + bookId + "', '" + StuName + "', '" + StuId + "', '" + issueDate + "')";

        try {
            statement.executeUpdate(issueBookQuery);
            JOptionPane.showMessageDialog(null,"Book Issued successfully." );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(String bookId) {
        String returnBookQuery = "DELETE FROM Issue WHERE BookId = " + bookId;

        try {
            int rowsAffected = statement.executeUpdate(returnBookQuery);
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Book Returned" );
            } else {
                JOptionPane.showMessageDialog(null,"Book Not Issued" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showIssuedBooks() {
        String showIssuedBooksQuery = "SELECT * FROM Issue";

        try {
            ResultSet resultSet = statement.executeQuery(showIssuedBooksQuery);

            Vector<String> columnNames = new Vector<>();
            columnNames.add("Book ID");
            columnNames.add("Student Name");
            columnNames.add("Student ID");
            columnNames.add("Issue Date");

            Vector<Vector<String>> data = new Vector<>();

            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("BookId"));
                row.add(resultSet.getString("StudentName"));
                row.add(resultSet.getString("StudentId"));
                row.add(String.valueOf(resultSet.getInt("IssueDate")));

                data.add(row);
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            JOptionPane.showMessageDialog(null, scrollPane, "Issued Books", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

