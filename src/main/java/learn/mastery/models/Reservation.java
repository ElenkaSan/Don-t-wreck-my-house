package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

//id,start_date,end_date,guest_id,total
public class Reservation {

    private int id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Guest guest;
    private BigDecimal total;
    private Host host; //filed saved under host uuid //-> city & state

    public Reservation() {
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, start_date, end_date, guest, total, host);
    }

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
