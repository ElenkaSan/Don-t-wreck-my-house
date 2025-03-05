package learn.mastery.models;

import java.time.LocalDate;

//guest_id,first_name,last_name,email,phone,state
public class Guest {

    private String guest_id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String state;
    private Reservation reservation;

    public String getId() {
        return guest_id;
    }

    public void setId(String id) {
        this.guest_id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
