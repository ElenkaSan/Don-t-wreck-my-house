package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    final LocalDate start_date = LocalDate.of(2020, 07, 01);
    final LocalDate end_date = LocalDate.of(2020, 07, 02);

    //id,start_date,end_date,guest_id,total
    //1,2020-07-01,2020-07-02,18,870
    //2,2020-07-01,2020-07-02,3,870
    /*
    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1);
        reservation.setTotal(new BigDecimal(870));
        reservation.add(reservation);
    }
     */

    @Override
    public List<Reservation> findByHostId(String host_id) throws DataException {
        return List.of();
    }

    @Override
    public List<Reservation> findByGuestId(String guest_id) throws DataException {
        return List.of();
    }

    @Override
    public List<Reservation> findByDate(String host_id, LocalDate date) throws DataException {
        return List.of();
    }

    @Override
    public Reservation findById(int id, String host_id) throws DataException {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean deleteById(int id, String host_id) throws DataException {
        return false;
    }




}
