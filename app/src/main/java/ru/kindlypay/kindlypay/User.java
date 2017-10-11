package ru.kindlypay.kindlypay;

import java.util.UUID;

/**
 * Created by User on 20.09.2017.
 */

public class User {

    private int id;
    private String name;
    private String phoneNumber;
    private String password;
    private String generateKod;

    public User(){
        generateKod=(Integer.valueOf((int) (Math.random()*1000000))).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGenerateKod() {
        return generateKod;
    }

    public void setGenerateKod(String generateKod) {
        this.generateKod = generateKod;
    }
}
