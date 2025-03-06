package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    final LocalDate start_date = LocalDate.of(2020, 07, 01);
    final LocalDate end_date = LocalDate.of(2020, 07, 02);

    //id,start_date,end_date,guest_id,total
    //1,2020-07-01,2020-07-02,18,870
    //2,2020-07-01,2020-07-02,3,870
    public ReservationRepositoryDouble() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1);
        reservation.setTotal(new BigDecimal(870));
        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");  // Matching host_id
        reservation.setHost(host);
        reservations.add(reservation);
    }


    @Override
    public List<Reservation> findByHostId(String host_id) throws DataException {
        return reservations.stream()
                .filter(r->r.getHost().getId().equals(host_id)).collect(Collectors.toList());
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
        return reservations.stream()
                .filter(r->r.getId() ==id && r.getHost().getId().equals(host_id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservation.setId(reservations.size() + 1); // making next new id
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return reservation.getId() > 0;
        //findById(reservation.getId(), reservation.getHost().getId()) != null;
    }

    @Override
    public boolean deleteById(int id, String host_id) throws DataException {
        return findById(id, host_id) != null;
    }
}
