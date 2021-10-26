package com.example.apnakhata;

public class UserHelperClass {
    String name,shopname,email,phoneNo,username,password;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String shopname, String email, String phoneno, String username, String password) {
        this.name = name;
        this.shopname = shopname;
        this.email = email;
        this.phoneNo = phoneno;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneNo;
    }

    public void setPhoneno(String phoneno) {
        this.phoneNo = phoneno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
