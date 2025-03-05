package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String host_id) throws DataException;

    List<Reservation> findByGuestId(String guest_id)  throws DataException;

   // List<Reservation> findByGuestEmail(String email) throws DataException;

    List<Reservation> findByDate(String host_id, LocalDate date) throws DataException;

    Reservation findById(int id, String host_id) throws DataException;

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean deleteById(int id, String host_id) throws DataException;
}
