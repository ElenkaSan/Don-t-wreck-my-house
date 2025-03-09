package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.util.List;

//The Controller accepts a View and MemoryService as constructor dependencies. CRUD
public class Controller {
    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void runApp() {
        try{
            runMenu();
        } catch (DataException ex) {
            view.printHeader("Error!!! " + ex);
        }
        view.printHeader("Goodbye!");
    }

    private void runMenu() throws DataException {
        MainMenuOption option;
        do{
            option = view.chooseOptionFromMenu();
            switch (option){
                case VIEW_RESERVATION_FOR_HOST:
                    viewReservation();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewReservation() {
        view.printHeader(MainMenuOption.VIEW_RESERVATION_FOR_HOST.getMessage());
        String email = view.enterHostEmail();
        List<Reservation> reservations = reservationService.findByHostEmail(email);
        if(reservations == null){
            String noExist = String.format("Sorry, host does not exist.");
            view.displayStatus(false, noExist);
        }
        else if (reservations.isEmpty()) {
            String noSuccessMessage = String.format("No reservations were found for that host.");
            view.displayStatus(false, noSuccessMessage);
        } else {
            Host host = reservations.get(0).getHost();
            view.printHostDetails(host);
            view.displayReservations(reservations);
        }
        view.enterToContinue();
    }

    //used no exist reservation for host
    //Enter Guest Email: iganter9@privacy.gov.au
    //Enter Host Email: arentcome3t@shutterfly.com
    private void makeReservation() {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = getGuest();
        Guest guest = guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);

        String hostEmail = view.enterHostEmail();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);
        if (reservations == null || reservations.isEmpty()) {
            view.printHeader("There are currently no reservations for this host. Enter this email again to create a reservation.");
            String hostEmail2 = view.enterHostEmail();
            List<Host> hosts = hostService.findByHostEmail(hostEmail2);
            if (hosts == null) {
                String noExist = String.format("Sorry, host does not exist.");
                view.displayStatus(false, noExist);
                return;
            }
            Host host = hosts.get(0);
            view.printHostDetails(host);

            Reservation reservation = view.makeReservationDate();
            reservation.setGuest(guest);
            reservation.setHost(host);

            BigDecimal sumTotal = reservationService.summaryTotal(reservation);
            view.displaySummary(reservation, sumTotal);
            boolean createOrNo = view.confirmation("Is this okay? [y/n]: ");
            if(!createOrNo) {
                String cancelMessage = "Reservation not created.";
                view.displayStatus(false, cancelMessage);
                view.enterToContinue();
                return;
            }

            Result<Reservation> result = reservationService.add(reservation);
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
            } else {
                String successMessage = String.format("Reservation %s created for guest %s.", result.getPayload().getId(), reservation.getGuest().getLastName());
                view.displayStatus(true, successMessage);
                view.enterToContinue();
            }
        } else {
            Host host = reservations.get(0).getHost();
            view.printHostDetails(host);
            view.displayReservations(reservations);

            Reservation reservation = view.makeReservationDate();
            reservation.setGuest(guest);
            reservation.setHost(host);

            BigDecimal sumTotal = reservationService.summaryTotal(reservation);
            view.displaySummary(reservation, sumTotal);
            boolean createOrNo = view.confirmation("Is this okay? [y/n]: ");
            if (!createOrNo) {
                String cancelMessage = "Reservation not created.";
                view.displayStatus(false, cancelMessage);
                view.enterToContinue();
                return;
            }

            Result<Reservation> result = reservationService.add(reservation);
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
            } else {
                String successMessage = String.format("Reservation %s created for guest %s.", result.getPayload().getId(), reservation.getGuest().getLastName());
                view.displayStatus(true, successMessage);
                view.enterToContinue();
            }
        }
    }

    private void editReservation() {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        String guestEmail = getGuest();
        Guest guest = guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);

        String hostEmail = getHost();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);

        Host host = reservations.get(0).getHost();
        view.printHostDetails(host);

        Reservation reservation = view.editReservation(reservations);

        if (reservation == null) {
            String wrongId = String.format("Was enter no existed reservation ID. Return back to Main Menu.");
            view.displayStatus(false, wrongId);
            return;
        }

        reservation.setGuest(guest);
        reservation.setHost(reservations.get(0).getHost());

        BigDecimal sumTotal = reservationService.summaryTotal(reservation);
        view.displaySummary(reservation, sumTotal);
        boolean createOrNo = view.confirmation("Is this okay? [y/n]: ");
        if(!createOrNo) {
            String cancelMessage = String.format("Reservation was not updated.");
            view.printHeader(cancelMessage);
            view.enterToContinue();
            return;
        }

        Result<Reservation> result = reservationService.update(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation %s updated for guest %s.", result.getPayload().getId(), reservation.getGuest().getLastName());
            view.displayStatus(true, successMessage);
            view.enterToContinue();
        }

    }

    private void cancelReservation() {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String guestEmail = getGuest();
        guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);

        String hostEmail = getHost();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);

        Host host = reservations.get(0).getHost();
        view.printHostDetails(host);
        Reservation reservation = view.displayCancelReservation(reservations);
        if (reservation != null) {
            Result<Reservation> result = reservationService.deleteById(reservation.getId(), host.getId());
            if (result.isSuccess()) {
                view.printHeader("[Success!] \nReservation " + reservation.getId() + " - for guest " + reservation.getGuest().getLastName() + " with email: " + reservation.getGuest().getEmail() + " removed.");
            } else {
                view.printHeader(result.getErrorMessages().get(0));
            }
        } else {
            String wrongId = String.format("Was enter no existed reservation ID. Return back to Main Menu.");
            view.displayStatus(false, wrongId);
        }
    }

    // support methods
    private String getHost() {
        String hostEmail;
        List<Reservation> reservations;
        while (true) {
            hostEmail = view.enterHostEmail();
            reservations = reservationService.findByHostEmail(hostEmail);
            if (reservations == null || reservations.isEmpty()) {
                String noExist = String.format("Sorry, host does not exist.");
                view.displayStatus(false, noExist);
            } else {
                break;
            }
        }
        return hostEmail;
    }

    private String getGuest(){
        String guestEmail;
        Guest guest = null;
        while (true) {
            guestEmail = view.enterGuestEmail();
            guest = guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);
            if (guest == null) {
                String noExist = String.format("Sorry, guest does not exist.");
                view.displayStatus(false, noExist);
            } else {
                break;
            }
        }
        return guestEmail;
    }
}
