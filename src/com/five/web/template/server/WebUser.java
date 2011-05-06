package com.five.web.template.server;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class WebUser {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String firstName;

    @Persistent
    private String lastName;

    @Persistent
    private Date enrollDate;

    public WebUser(String firstName, String lastName, Date enrollDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.enrollDate = enrollDate;
    }
    
    public Key getKey() {
        return key;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }
    
    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }
}
