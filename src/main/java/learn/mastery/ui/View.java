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

    public void displayException(Exception ex) {
        printHeader("An error occurred:");
        io.println(ex.getMessage());
    }

    public String enterHostEmail() {
       return io.readString("Enter Host Email: ");
    }

    //Host Email: vduffil2m@naver.com
    //Queyeiro: Arlington, VA
    //=================
    //ID: 2, 2021-03-29 - 2021-04-03, Guest: Milham, Mari, Email: mmilhampp@usatoday.com, Phone: (518) 9967730.

    //Host Email: sgeorghioua1@fema.gov
    //Georghiou: Sarasota, FL
    //=================
    //ID: 5, 2021-04-12 - 2021-04-15, Guest: Olner, Bradly, Email: bolnerny@amazon.co.jp, Phone: (702) 6972609.
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

    //Make a Reservation
    //==================
    //Enter Host Email: vduffil2m@naver.com
    //Enter Guest Email: jhulson8@auda.org.au
    //Summary
    //=======
    //Start: 03/03/2025
    //End: 03/05/2025
    //Total: 566
    //Is this okay? [y/n]: y
    //Checking for overlaps with start: 2025-03-03, end: 2025-03-05
    //Existing reservations: []
    //
    //Success
    //=======
    //Reservation 18 created.
    //Press [Enter] to continue.
    public void displaySummary(Reservation reservation, BigDecimal sumCost) {
        printHeader("Summary");
        io.println("Start: " + reservation.getStart_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.println("End: " + reservation.getEnd_date().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.println("Total: " + sumCost);
    }

    public boolean confirmation(String input){
        return io.readBoolean(input);
    }

    //host vduffil2m@naver.com
    //guest jhulson8@auda.org.au
    public Reservation editReservation(List<Reservation> reservations){
        displayReservations(reservations);
        int reservationId = Integer.parseInt(io.readString("Enter Reservation ID: "));
        Reservation reservation = reservations.stream().filter(r-> r.getId() == reservationId).findFirst().orElse(null);

        /* if not correct id input
        if(reservation == null || reservation.getId() == 0) {
            io.println("Please enter a valid reservation ID.");
            return null;
        }
         */

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
        int reservationId = Integer.parseInt(io.readString("Enter Reservation ID: "));
        Reservation reservation = reservations.stream().filter(r -> r.getId() == reservationId).findFirst().orElse(null);
        return reservation;
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
