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

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHost().getId());
        //declarative solution cannot be duplicated
        //id,start_date,end_date,guest_id,total
        boolean duplicate =  reservations.stream()
                .anyMatch(r -> reservation.getStart_date().equals(r.getStart_date())
                        && reservation.getEnd_date().equals(r.getEnd_date())
                        && reservation.getGuest().getId().equals(r.getGuest().getId())
                        && reservation.getTotal().equals(r.getTotal()));
        if(duplicate){
            result.addErrorMessage("Duplicate Reservation is not allowed");
            return result;
        }

        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if(reservation.getId() <= 0) {
            result.addErrorMessage("Reservation `id` is required.");
        }
        if (!result.isSuccess()) {
            return result;
        }
        List<Reservation> existing = reservationRepository.findByHostId(reservation.getHost().getId());
        if (existing == null) {
         result.addErrorMessage("The Host Id " + reservation.getHost().getId() + " is not found.");
            return result;
        }
        boolean duplicate =  existing.stream()
                .anyMatch(r -> reservation.getId() != r.getId()
                        && reservation.getStart_date().equals(r.getStart_date())
                        && reservation.getEnd_date().equals(r.getEnd_date())
                        && reservation.getGuest().getId().equals(r.getGuest().getId())
                        && reservation.getTotal().equals(r.getTotal()));
        if(duplicate){
            result.addErrorMessage("Duplicate Reservation is not allowed");
            return result;
        }
        if (!result.isSuccess()) {
            return result;
        }
        if(result.isSuccess()){
            if(reservationRepository.update(reservation)) {
                result.setPayload(reservation);
            } else {
                String message = String.format("Reservation id %s was not found.", reservation.getId());
                result.addErrorMessage(message);
            }
        }
        return result;
    }

    public Result<Reservation> deleteById(int id, String host_id) throws DataException {
        Result<Reservation> result = new Result<>();
        List<Reservation> all = reservationRepository.findByHostId(host_id);
        if(all == null){
            result.addErrorMessage("Could not find Panel Id" + id);
            return result;
        }
        reservationRepository.deleteById(id, host_id);
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation) {
        Result<Reservation> result = new Result<>();
        if(reservation == null) {
            result.addErrorMessage("Reservation can not be null");
            return result;
        }

        if(reservation.getStart_date() == null || reservation.getEnd_date() == null) {
            result.addErrorMessage("Reservation start and end dates are required.");
        }

    //    if(reservation.getGuest().getEmail() == null || reservation.getGuest().getEmail().trim().length() == 0) {
    //        result.addErrorMessage("Guest email is required.");
    //    }

    //    if(reservation.getHost().getEmail() == null || reservation.getHost().getEmail().trim().length() == 0) {
     //       result.addErrorMessage("Host email is required.");
     //   }

        if(reservation.getStart_date() == reservation.getEnd_date()) {
            result.addErrorMessage("Reservation start and end dates can be the same dates.");
        }

        return result;
    }
}

