package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        if (reservations.isEmpty()) {
            String noSuccessMessage = String.format("No reservation found for this email.");
            view.displayStatus(false, noSuccessMessage);
        } else {
            Host host = reservations.get(0).getHost();
        //    Host host = reservationService.findHostByEmail(email); // no need
            view.printHostDetails(host);
            view.displayReservations(reservations);
        }
        view.enterToContinue();
    }

    private void makeReservation() {

    }

    private void editReservation() {

    }

    private void cancelReservation() {

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
