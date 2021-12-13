package com.example.zingmp3fake.Model;

import java.io.Serializable;

public class Music_Off implements Serializable
{
    private String path;
    private String Name;

    public Music_Off(String path, String Name) {
        this.path = path;
        this.Name = Name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
