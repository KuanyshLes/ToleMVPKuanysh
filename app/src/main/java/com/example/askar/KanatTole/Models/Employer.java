package com.example.askar.KanatTole.Models;




public class Employer {
    private int id=0;
    private String name;
    private String phone;
    private boolean access=false;

    public Employer(int id, String name, String phone, boolean access){
        this.id=id;
        this.name=name;
        this.phone=phone;
        this.access=access;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", access=" + access +
                '}';
    }
}
