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

    final LocalDate start_date = LocalDate.of(2025, 03, 13);
    final LocalDate end_date = LocalDate.of(2025, 03, 14);

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
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");  // Matching host_id; rates 477,596.25
        reservation.setHost(host);
        reservations.add(reservation);

        Reservation reservationTwo = new Reservation();
        reservationTwo.setId(2);
        reservationTwo.setStart_date(LocalDate.of(2020, 07, 01));
        reservationTwo.setEnd_date(LocalDate.of(2020, 07, 02));
        reservationTwo.setGuest(GuestRepositoryDouble.guest2);  // Different guest
        reservationTwo.setTotal(new BigDecimal(680));
        Host host2 = new Host();
        host2.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");  // Same host_id; rates 340,425
        host2.setEmail("eyearnes0@sfgate.com");
        reservationTwo.setHost(host2);
        reservations.add(reservationTwo);
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
        return reservations.removeIf(reservation -> reservation.getId() == id && reservation.getHost().getId().equals(host_id));
    }

    /*
      public ReservationRepositoryDouble() {
        Reservation reservationOne = new Reservation();
        reservationOne.setId(1);
        reservationOne.setStart_date(start_date);
        reservationOne.setEnd_date(end_date);
        reservationOne.setGuest(GuestRepositoryDouble.guest1);
        reservationOne.setTotal(new BigDecimal(870));
        Host host1 = new Host();
        host1.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");  // Matching host_id
        reservationOne.setHost(host1);
        reservations.add(reservationOne);

        Reservation reservationTwo = new Reservation();
        reservationTwo.setId(2);
        reservationTwo.setStart_date(start_date);
        reservationTwo.setEnd_date(end_date);
        reservationTwo.setGuest(GuestRepositoryDouble.guest2);  // Different guest
        reservationTwo.setTotal(new BigDecimal(870));
        Host host2 = new Host();
        host2.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");  // Same host_id
        reservationTwo.setHost(host2);
        reservations.add(reservationTwo);
    }
     */
}
