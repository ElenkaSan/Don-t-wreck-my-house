package learn.mastery.ui;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public String enterHostEmail() {
       return io.readString("Enter Host Email: ");
    }

    public void displayReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            io.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s, Phone: %s.%n",
                    reservation.getId(),
                    reservation.getStart_date(),
                    reservation.getEnd_date(),
                    reservation.getGuest().getLastName(),
                    reservation.getGuest().getFirstName(),
                    reservation.getGuest().getEmail(),
                    reservation.getGuest().getPhone());

        }
    }

    public void printHostDetails(Host host) {
        io.println("\n" + host.getLastName() + ": " + host.getCity() + ", " + host.getState());
        io.println("=================");
    }

    public String enterGuestEmail() {
        return io.readString("Enter Guest Email: ");
    }

    public Reservation makeReservationDate(){
        Reservation reservation = new Reservation();
        LocalDate startDate = io.readLocalDate("Enter Start date (MM/dd/yyyy): ");
        LocalDate endDate = io.readLocalDate("Enter End date (MM/dd/yyyy): ");
        reservation.setStart_date(startDate);
        reservation.setEnd_date(endDate);
        return reservation;
    }

    public void displaySummary(Reservation reservation, BigDecimal sumCost) {
        printHeader("Summary");
        io.println("Start: " + reservation.getStart_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.println("End: " + reservation.getEnd_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.println("Total: " + sumCost);
    }

    public boolean confirmation(String input){
        return io.readBoolean(input);
    }

    public Reservation editReservation(List<Reservation> reservations){
        displayReservations(reservations);
        int reservationId = io.readInt("Enter Reservation ID: ");
        Reservation reservation = reservations.stream().filter(r-> r.getId() == reservationId).findFirst().orElse(null);

        if(reservationId <= 0 || reservation == null) {
            return null;
        }

        printHeader("Editing Reservation " + reservation.getId());
        io.readString("Press [Enter] to keep original value.");

        String enterStart = String.format("Start (%s): ", reservation.getStart_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        String enterStartDate = io.readString(enterStart).trim();
        LocalDate startDate = enterStartDate.isEmpty() ? reservation.getStart_date() : LocalDate.parse(enterStartDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        String enterEnd = String.format("End (%s): ", reservation.getEnd_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        String enterEndDate = io.readString(enterEnd).trim();
        LocalDate endDate = enterEndDate.isEmpty() ? reservation.getEnd_date() : LocalDate.parse(enterEndDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        reservation.setStart_date(startDate);
        reservation.setEnd_date(endDate);

        return reservation;
    }

    public Reservation displayCancelReservation(List<Reservation> reservations) {
        displayReservations(reservations);
        int reservationId = io.readInt("Enter Reservation ID: ");
        Reservation reservation = reservations.stream().filter(r -> r.getId() == reservationId).findFirst().orElse(null);
        return reservation;
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
