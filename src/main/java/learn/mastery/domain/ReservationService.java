package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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


    public List<Reservation> findByHostEmail(String hostEmail) throws DataException {
        Host host = hostRepository.findByHostEmail(hostEmail).stream().findFirst().orElse(null);
        if (host == null) {
   //         System.out.println("\nNo host found for email: " + hostEmail);
            return Collections.emptyList(); // No host found
        }
     //   System.out.println("\n" + host.getLastName() + ": " + host.getCity() + ", " + host.getState());
        List<Reservation> reservations = reservationRepository.findByHostId(host.getId());
        reservations.sort(Comparator.comparing(Reservation::getStart_date)); // Sort by start date
  //      System.out.println("\n" + host.getId());
        for (Reservation reservation : reservations) {
            reservation.setHost(host);
            Guest guest = guestRepository.findById(reservation.getGuest().getId());
            reservation.setGuest(guest);
        }
        return reservations;
    }

  //  public Host findHostByEmail(String hostEmail) throws DataException {
  //      return hostRepository.findByHostEmail(hostEmail).stream().findFirst().orElse(null);
  //  }

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
        if(all == null || all.isEmpty()){
            result.addErrorMessage("Could not found the reservation");
            return result;
        }
        Reservation remove = reservationRepository.findById(id, host_id);
        if (remove == null) {
            result.addErrorMessage("Reservation " + id + " not found for this host " + host_id);
            return result;
        }
        reservationRepository.deleteById(id, host_id);
        result.setPayload(remove);
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

        if (!reservation.getStart_date().isBefore(reservation.getEnd_date())) {
            result.addErrorMessage("The start date must come before the end date.");
            return result;
        }

        //need more work on it
        List<Reservation> overlappingDates = reservationRepository.findByDate(reservation.getHost().getId(), reservation.getStart_date()).stream()
                .filter(existing -> reservation.getId() != existing.getId())
                .filter(existing ->
                        !reservation.getEnd_date().isBefore(existing.getStart_date()) &&
                                !reservation.getStart_date().isAfter(existing.getEnd_date())
                )
                .collect(Collectors.toList());

                /*
                .anyMatch(existing ->reservation.getId() !=  existing.getId()
                && existing.getEnd_date().isBefore(reservation.getEnd_date())
                && existing.getStart_date().isAfter(reservation.getEnd_date()));
                 */
        System.out.println("Checking for overlaps with start: " + reservation.getStart_date() + ", end: " + reservation.getEnd_date());
        System.out.println("Existing reservations: " + overlappingDates);

        if(!overlappingDates.isEmpty()) {
            result.addErrorMessage("The reservation may never overlap existing reservation dates.");
            return result;
        }

        //    if(reservation.getGuest().getEmail() == null || reservation.getGuest().getEmail().trim().length() == 0) {
    //        result.addErrorMessage("Guest email is required.");
    //    }

        //    if(reservation.getStart_date() == null || reservation.getEnd_date() == null) {
        //            result.addErrorMessage("Reservation start and end dates are required.");
        //        }

    //    if(reservation.getHost().getEmail() == null || reservation.getHost().getEmail().trim().length() == 0) {
     //       result.addErrorMessage("Host email is required.");
     //   }

  //      if (reservation.getStart_date().isBefore(LocalDate.now()) || reservation.getEnd_date().isAfter(LocalDate.now())) {
   //         result.addErrorMessage("Reservation start and end date cannot be in the past.");
    //    }

    //    if(reservation.getStart_date() == reservation.getStart_date() || reservation.getEnd_date() == reservation.getEnd_date() ) {
      //      result.addErrorMessage("Reservation start and end dates can be the same dates.");
       // }

        return result;
    }
}

