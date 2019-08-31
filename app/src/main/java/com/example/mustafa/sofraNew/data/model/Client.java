package com.example.mustafa.sofraNew.data.model;

public class Client  {

    private String name, email , phone,password , confirm_password,city_id,region_id;

    public Client() {
    }

    public Client(String name, String email, String phone, String password, String confirm_password, String city_id, String region_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirm_password = confirm_password;
        this.city_id = city_id;
        this.region_id = region_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
}
