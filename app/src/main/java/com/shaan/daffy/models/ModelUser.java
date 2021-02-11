package com.shaan.daffy.models;

public class ModelUser {
    String name, email, privacy, bio, url, prof, uid, web;

    public  ModelUser()
    {

    }

    public ModelUser(String name, String email, String privacy, String bio, String url, String prof, String uid, String web) {
        this.name = name;
        this.email = email;
        this.privacy = privacy;
        this.bio = bio;
        this.url = url;
        this.prof = prof;
        this.uid = uid;
        this.web = web;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
