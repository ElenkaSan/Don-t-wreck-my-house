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
                case GENERATE:
               //     generate();
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

    private void makeReservation() {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String hostEmail = getHost();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);

        String guestEmail = getGuest();
        Guest guest = guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);
        Host host = reservations.get(0).getHost();
        view.printHostDetails(host);
        view.displayReservations(reservations);

        Reservation reservation = view.makeReservationDate();
        reservation.setGuest(guest);
        reservation.setHost(reservations.get(0).getHost());

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
    }

    private void editReservation() {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        String hostEmail = getHost();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);

        String guestEmail = getGuest();
        Guest guest = guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);
        Host host = reservations.get(0).getHost();
        view.printHostDetails(host);

        Reservation reservation = view.editReservation(reservations);
        /*
        if (reservation == null || reservation.getId() <= 0) {
            String invalidIdMessage = "Please enter a valid numeric reservation ID.";
            view.displayStatus(false, invalidIdMessage);
            return;
        }
         */
        reservation.setGuest(guest);
        reservation.setHost(reservations.get(0).getHost());

        BigDecimal sumTotal = reservationService.summaryTotal(reservation);
        view.displaySummary(reservation, sumTotal);
        boolean createOrNo = view.confirmation("Is this okay? [y/n]: ");
        if(!createOrNo) {
            String cancelMessage = "Reservation was not updated.";
            view.displayStatus(false, cancelMessage);
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

    //vduffil2m@naver.com jhulson8@auda.org.au
    private void cancelReservation() {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String hostEmail = getHost();
        List<Reservation> reservations = reservationService.findByHostEmail(hostEmail);

        String guestEmail = getGuest();
        guestService.findByGuestEmail(guestEmail).stream().findFirst().orElse(null);

        Host host = reservations.get(0).getHost();
        view.printHostDetails(host);
        view.displayReservations(reservations);
        Reservation reservation = view.displayCancelReservation(reservations);
        if (reservation != null) {
            Result<Reservation> result = reservationService.deleteById(reservation.getId(), host.getId());
            if (result.isSuccess()) {
                view.printHeader("[Success!] \nReservation " + reservation.getId() + " - for guest " + reservation.getGuest().getLastName() + " with email: " + reservation.getGuest().getEmail() + " removed.");
            } else {
                view.printHeader(result.getErrorMessages().get(0));
            }
        } else {
            view.printHeader("Return back to Main Menu");
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


    /*
    private void generate() throws DataException {
        GenerateRequest request = view.getGenerateRequest();
        if (request != null) {
            int count = reservationService.generate(request.getStart(), request.getEnd(), request.getCount());
            view.displayStatus(true, String.format("%s reservation generated.", count));
        }
    }
     */
}
