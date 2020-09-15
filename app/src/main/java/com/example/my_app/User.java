package com.example.my_app;

public class User {
    String name,phone,log;

    public User() {
    }

    public User(String name, String phone ,String log) {
        this.name = name;
        this.phone = phone;
        this.log =log;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
