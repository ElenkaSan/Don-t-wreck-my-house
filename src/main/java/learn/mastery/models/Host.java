package learn.mastery.models;

import java.math.BigDecimal;

//id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
public class Host {

    private String id;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postal_code;
    private BigDecimal standard_rate;
    private BigDecimal weekend_rate;

    //Constructor for creating a new host for the reservation.
    public Host(){}

    //Constructor for representing an existing host stored in a file.
    public Host(String id, String lastName, String email, String phone, String address, String city, String state, String postal_code, BigDecimal standard_rate, BigDecimal weekend_rate) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standard_rate = standard_rate;
        this.weekend_rate = weekend_rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public BigDecimal getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(BigDecimal standard_rate) {
        this.standard_rate = standard_rate;
    }

    public BigDecimal getWeekend_rate() {
        return weekend_rate;
    }

    public void setWeekend_rate(BigDecimal weekend_rate) {
        this.weekend_rate = weekend_rate;
    }
}
