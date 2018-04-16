package com.example.askar.KanatTole.LoginRegistration;

/**
 * Created by Kanat on 07.04.2018.
 */

public class AccountData {
    private String id;



    private String name;
    private String surname;
    private String nameOFSellPoint;
    private String email;
    private String phoneNumber;
    private String password;

    public AccountData(String id,String name, String surname,String email, String nameOFSellPoint, String phoneNumber, String password) {
        this.id=id;
        this.name = name;
        this.surname = surname;
        this.nameOFSellPoint = nameOFSellPoint;
        this.email=email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNameOFSellPoint() {
        return nameOFSellPoint;
    }

    public void setNameOFSellPoint(String nameOFSellPoint) {
        this.nameOFSellPoint = nameOFSellPoint;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nameOFSellPoint='" + nameOFSellPoint + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
