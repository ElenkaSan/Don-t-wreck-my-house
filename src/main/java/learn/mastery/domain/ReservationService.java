package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;


public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHostId(String host_id) throws DataException {
        return reservationRepository.findByHostId(host_id);
    }

    public Reservation findById(int id, String host_id) throws DataException {
        return reservationRepository.findById(id, host_id);
    }

    public List<Reservation> findByDate(String host_id, LocalDate date) throws DataException {
        return reservationRepository.findByDate(host_id, date);
    }

    public List<Reservation> findByGuestId(String guest_id) throws DataException {
        return reservationRepository.findByGuestId(guest_id);
    }

    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    public boolean deleteById(int id, String host_id) throws DataException {
        return false;
    }
}

