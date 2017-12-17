package com.rajat.e_subzi.Objects;

/**
 * Created by Rajat on 21-04-2016.
 */
public class NotificationObject {
String message;
int id;
    public NotificationObject(String message, int id) {

        this.message = message;
        this.id = id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
