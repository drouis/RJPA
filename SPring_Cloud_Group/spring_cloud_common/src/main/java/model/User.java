package model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -5483813460898947262L;
    String userName;
    String password;
    int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}