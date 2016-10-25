package com.example.prashanthmudhelli.trackpack;

public class Travellers {
    private String fullName, fromCity, fromDate, toCity, toDate, email, mobile;

    public Travellers() {
    }

    public Travellers(String fullName, String fromCity, String fromDate, String toCity, String toDate, String email, String mobile) {
        this.fullName = fullName;
        this.fromCity = fromCity;
        this.fromDate = fromDate;
        this.toCity = toCity;
        this.toDate = toDate;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
