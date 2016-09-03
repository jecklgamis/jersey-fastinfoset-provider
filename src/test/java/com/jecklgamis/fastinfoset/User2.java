package com.jecklgamis.fastinfoset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "User2")
@XmlAccessorType(XmlAccessType.FIELD)
public class User2 {
    private String username;
    private String email;

    public User2() {
        this("user", "user@example.com");
    }

    public User2(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
