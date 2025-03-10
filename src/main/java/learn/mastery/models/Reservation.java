package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

//id,start_date,end_date,guest_id,total
public class Reservation {
//Here I have store is int id, but guest and host have string id,
//where I create reservation file name after host id, which is a UUID as 3edda6bc-ab95-49a8-8962-d50b53f84b15.csv where reservation csv stored
//from hosts.csv file (id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate 3edda6bc-ab95-49a8-8962-d50b53f84b15,Yearnes,eyearnes0@sfgate.com,(806) 1783815,3 Nova Trail,Amarillo,TX,79182,340,425),
//and guest id goes inside to the reservation file from guests.csv file (inside guest_id,first_name,last_name,email,phone,state).
    private int id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Guest guest;
    private BigDecimal total;
    private Host host; //filed saved under host uuid //-> city & state

    //two ways initialise constructors:
    //The empty constructor allows us to create an empty reservation and then populate it field-by-field.
    public Reservation() {
    }

    //Constructor for representing an existing reservation stored in a file.
    public Reservation(int id, LocalDate start_date, LocalDate end_date, Guest guest, BigDecimal total, Host host) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.guest = guest;
        this.total = total;
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    //make this two Override for be sure that two Reservation objects with identical id, start_date, end_date, guest, total, and host are treated as equal,
    //and they function correctly when storing and retrieving Reservation objects.
    //after having equals should do hashCode() for to ensure that equal objects produce the same hash code
    @Override
    public int hashCode() {
        return Objects.hash(id, start_date, end_date, guest, total, host);
    }

    //two objects are equal only if they are the same instance, including equal if their internal states/field values are the same
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return id == reservation.id &&
                Objects.equals(start_date, reservation.start_date) &&
                Objects.equals(end_date, reservation.end_date) &&
                Objects.equals(guest, reservation.guest) &&
                Objects.equals(total, reservation.total) &&
                Objects.equals(host, reservation.host);
    }
}
