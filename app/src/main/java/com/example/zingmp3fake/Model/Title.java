package com.example.zingmp3fake.Model;

import java.io.Serializable;

public class Title implements Serializable {
    private String Image;
    private String Name;
    private String linkStorage;

    public Title(String Image, String Name, String linkStorage) {
        this.Image = Image;
        this.Name = Name;
        this.linkStorage = linkStorage;
    }
    public Title() {
        this.Image = "none";
        this.Name = "none";
        this.linkStorage = "none";
    }


    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getImage() {
        return Image;
    }

    public void setLinkImage(String Image) {
        this.Image = Image;
    }

    public String getLinkStorage() {
        return linkStorage;
    }

    public void setLinkStorage(String linkStorage) {
        this.linkStorage = linkStorage;
    }
}
