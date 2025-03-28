package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//It uses a file to store its data.
public class ReservationFileRepository implements ReservationRepository {
    //Each reservations field is separated by a delimiter, If the file delimiter, a carriage return, or a newline was written to the file,
    //it would ruin our ability to read the Reservation. Here, we ensure those characters don't end up in the file.
    private static final String DELIMITER = ",";
  //  private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    //find file by host_id
    @Override
    public List<Reservation> findByHostId(String host_id) throws DataException {
        ArrayList<Reservation> result = new ArrayList<>(); //create a list of reservation
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host_id)))) { //try with the resources
            reader.readLine(); // read header, skip the headerline
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(DELIMITER, -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, host_id));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    //under host_id...
    @Override
    public Reservation findById(int id, String host_id) throws DataException {
        return findByHostId(host_id).stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //taking reservation by host_id with date
    @Override
    public List<Reservation> findByDate(String host_id, LocalDate date) throws DataException {
        return findByHostId(host_id).stream() //getting all reservation for this host
                .filter(r -> !r.getStart_date().isAfter(date) && !r.getEnd_date().isBefore(date)) //filter entered dates by before & after
                .collect(Collectors.toList()); //making a list
    }

    @Override
    public List<Reservation> findByGuestId(String guest_id) throws DataException {
        List<Reservation> reservations = new ArrayList<>(); //holding reservations
        File folder = new File(directory);
        File[] files = folder.listFiles(); //returning each host_id file in the directory

        if (files != null) {
            for (File file : files) {
                // System.out.println("Checking file: " + file.getName());
                List<Reservation> hostReservations =
                        findByHostId(file.getName().replace(".csv", "")); //keeping just host_id to find all reservations for this host
                reservations.addAll(hostReservations.stream()
                        .filter(r -> r.getGuest().getId().equals(guest_id))
                        .collect(Collectors.toList()));
            }
        }
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        getHostReservationFilePath(reservation.getHost().getId());
        List<Reservation> all = findByHostId(reservation.getHost().getId()); //getting all existing reservations
        reservation.setId(all.size() + 1); //need next id inside reservation-test
        all.add(reservation); //adding the new reservation to the existing ones or create a new one via  getHostReservationFilePath()
        writeAll(all, reservation.getHost().getId()); //adding reservation and write changes to the data file
        return reservation; //return with new generated id
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        if (reservation == null) {
            return false;
        }
        //imperative solution 'how'
        List<Reservation> all = findByHostId(reservation.getHost().getId());
        for (int i = 0; i < all.size(); i++) {
            if (reservation.getId() == all.get(i).getId()) { //checking each reservation id to mach
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getId()); //after finding then update reservation and write changes
                return true;
            }
        }
        /* //or the same, but declarative solution 'what', focusing on what should be done rather than how to do it.
         List<Reservation> reservations = findByHostId(reservation.getHost().getId());
         for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId() == reservation.getId() &&
                    reservations.get(i).getHost().getId().equals(reservation.getHost().getId())) {
                reservations.set(i, reservation);
                return true;
            }
        }
         */
        return false;
    }

    @Override
    public boolean deleteById(int id, String host_id) throws DataException {
        List<Reservation> all = findByHostId(host_id);
        boolean removed = all.removeIf(r -> r.getId() == id);
        if (removed) {
            writeAll(all, host_id); //writing changes
        }
        return removed;
    }

    private String getFilePath(String host_id) {
        return Paths.get(directory, host_id + ".csv").toString(); //making file path together with directory path and host_id
    }

    public void getHostReservationFilePath(String host_id) throws DataException {
        String filePath = getFilePath(host_id); // making the path
        File file = new File(filePath);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(HEADER); // Writing/printing header so the file is in proper format
            } catch (FileNotFoundException ex) {
                throw new DataException("Could not create reservation file for host: " + host_id, ex);
            }
        }
    }

    //writing to the file for add, update, delete ->
    private void writeAll(List<Reservation> reservations, String host_id) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host_id))) { //here replace a single file
            writer.println(HEADER);
            for (Reservation guest : reservations) {
                writer.println(serialize(guest));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    //id,start_date,end_date,guest_id,total
    //All serialization and deserialization methods are private. They are implementation details that should not be shared with the outside world.
    //Serialization is the process of converting an object's data to a string or byte sequence that can then be saved or transmitted.
    //Reservation is converted to a line of text. The reverse process, deserialization, converts the string back into a Reservation.
    private String serialize(Reservation guest) {
        return String.format("%d,%s,%s,%s,%s",
                guest.getId(),
                guest.getStart_date(),
                guest.getEnd_date(),
                guest.getGuest().getId(),
                guest.getTotal());
    }

    private Reservation deserialize(String[] fields, String host_id) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        Guest guest = new Guest();
        guest.setId(fields[3]);
        result.setGuest(guest);

        Host host = new Host();
        host.setId(host_id);
        result.setHost(host);

        return result;
    }
}
