package learn.mastery.models;

//guest_id,first_name,last_name,email,phone,state
public class Guest {

    private String guest_id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String state;

    public Guest() {
    }

    public Guest(String guest_id, String firstName, String lastName, String email, String phone, String state) {
        this.guest_id = guest_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }

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
}
