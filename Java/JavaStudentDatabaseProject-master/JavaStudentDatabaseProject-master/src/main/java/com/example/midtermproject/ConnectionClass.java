package com.example.midtermproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConnectionClass {

    public static void main(String[] args) {
        ConnectionClass connections = new ConnectionClass();
        List<Student> studentsList = connections.getStudents();
        for (Student student : studentsList) {
            System.out.println(student);
        }
    }

    static List<Student> getStudents() {
        List<Student> studentsList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://database-1.ctq6s0sqoqgq.us-east-1.rds.amazonaws.com:3306/midterm", "admin", "TomChimorinTomTom0101");
            System.out.println("Database connection successful!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                Student student = new Student(
                        resultSet.getInt("StudentNum"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Telephone"),
                        resultSet.getString("homeAddress"),
                        resultSet.getString("Province"),
                        resultSet.getInt("AvgGrade"),
                        resultSet.getString("Major")
                );
                System.out.println("Retrieved student: " + student);
                studentsList.add(student);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentsList;
    }
}
