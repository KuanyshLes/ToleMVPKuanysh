package com.example.askar.KanatTole.LoginRegistration;

/**
 * Created by Kanat on 14.04.2018.
 */

public class EmployeeData {
    private String name;
    private String surname;
    private String code;
    private String phoneNumber;
    private String password;

    public EmployeeData(String name, String surname, String code, String phoneNumber, String password) {
        this.name = name;
        this.surname = surname;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.password = password;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


}
