package com.example.midtermproject;

import java.util.Arrays;
import java.util.List;

public class Student {
    private int StudentNumber;
    private String FirstName;
    private String LastName;
    private String Telephone;
    private String Address;
    private String Province;
    private int AvgGrade;
    private String Major;

    public Student(int StudentNumber, String FirstName, String LastName, String Telephone, String Address, String Province, int AvgGrade, String Major) {
        this.StudentNumber = StudentNumber;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Telephone = Telephone;
        this.Address = Address;
        this.Province = Province;
        this.AvgGrade = AvgGrade;
        this.Major = Major;
    }

    private static final List<String> validProvinces = Arrays.asList(
            "AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT"
    );

    public int getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        if (studentNumber > 200034000) {
            StudentNumber = studentNumber;
        } else {
            throw new IllegalArgumentException("Student number must be greater than 200034000.");
        }
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && firstName.length() > 1) {
            FirstName = firstName;
        } else {
            throw new IllegalArgumentException("First name must be more than 1 character.");
        }
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && lastName.length() > 1) {
            LastName = lastName;
        } else {
            throw new IllegalArgumentException("Last name must be more than 1 character.");
        }
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone != null && telephone.matches("^[2-9]\\d{2}-[2-9]\\d{2}-\\d{4}$")) {
            Telephone = telephone;
        } else {
            throw new IllegalArgumentException("Invalid telephone number format.");
        }
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        if (address != null && address.length() > 6) {
            Address = address;
        } else {
            throw new IllegalArgumentException("Address must be more than 6 characters.");
        }
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        if (province != null && validProvinces.contains(province.toUpperCase())) {
            Province = province.toUpperCase();
        } else {
            throw new IllegalArgumentException("Invalid province.");
        }
    }

    public int getAvgGrade() {
        return AvgGrade;
    }

    public void setAvgGrade(int avgGrade) {
        if (avgGrade >= 0 && avgGrade <= 100) {
            AvgGrade = avgGrade;
        } else {
            throw new IllegalArgumentException("Average grade must be in the range of 0-100.");
        }
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        if (major != null && major.length() > 5) {
            Major = major;
        } else {
            throw new IllegalArgumentException("Major must be more than 5 characters.");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "StudentNumber=" + StudentNumber +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Address='" + Address + '\'' +
                ", Province='" + Province + '\'' +
                ", AvgGrade=" + AvgGrade +
                ", Major='" + Major + '\'' +
                '}';
    }
}
