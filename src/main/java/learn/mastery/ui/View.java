package learn.mastery.ui;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public void printHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public MainMenuOption chooseOptionFromMenu() {
        printHeader("Welcome Don't Wreck My House");
        printHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(MainMenuOption option : MainMenuOption.values()){
            if(!option.isHidden()){
                io.printf("%s. %s%n", option.getValue(), option.getMessage());
            }
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }
        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displayException(Exception ex) {
        printHeader("An error occurred:");
        io.println(ex.getMessage());
    }

    public String enterHostEmail() {
       return io.readString("\nEnter Host Email: ");
    }

    //Host Email: bcharon56@storify.com
    //
    //Charon: Tampa, FL
    //=================
    //ID: 8, 08/12/2020 - 08/18/2020, Guest: Carncross, Tremain, Email: tcarncross2@japanpost.jp
    //or equeyeiro2n@seattletimes.com
    public void displayReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            io.println("No reservations found.");
            return;
        }
        for (Reservation reservation : reservations) {
       //     Guest guest = reservation.getGuest();
            io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s, Phone: %s.%n",
                    reservation.getId(),
                    reservation.getStart_date(),
                    reservation.getEnd_date(),
                     /*
                    guest.getLastName(),
                    guest.getFirstName(),
                    guest.getEmail(),
                    guest.getPhone());
                    */
                    reservation.getGuest().getLastName(), //getting null ....
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getEmail(),
                    reservation.getGuest().getPhone());

        }
    }

    public void printHostDetails(Host host) {
        io.println("\n" + host.getLastName() + ": " + host.getCity() + ", " + host.getState());
        io.println("=================");
    }

    //NEED to change
    public GenerateRequest getGenerateRequest() {
        printHeader(MainMenuOption.GENERATE.getMessage());

        LocalDate start = io.readLocalDate("Select a start date [MM/dd/yyyy]: ");
        if (start.isAfter(LocalDate.now())) {
            displayStatus(false, "Start date must be in the past.");
            return null;
        }

        LocalDate end = io.readLocalDate("Select an end date [MM/dd/yyyy]: ");
        if (end.isAfter(LocalDate.now()) || end.isBefore(start)) {
            displayStatus(false, "End date must be in the past and after the start date.");
            return null;
        }

        GenerateRequest request = new GenerateRequest();
        request.setStart(start);
        request.setEnd(end);
        request.setCount(io.readInt("Generate how many random forages [1-500]?: ", 1, 500));
        return request;
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        printHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

}
