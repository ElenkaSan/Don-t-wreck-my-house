package learn.mastery.data;

import learn.mastery.models.Guest;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//guest_id,first_name,last_name,email,phone,state
public class GuestFileRepository implements GuestRepository {

    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;
    //private final String directory;

   // public GuestFileRepository(String directory) {
   //     this.directory = directory;
   // }

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    //    try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(date)))) {
            reader.readLine();
                    for(String line = reader.readLine(); line != null; line = reader.readLine()){
                        Guest guest = deserialize(line);
                        if(guest != null){
                            result.add(guest);
                        }
                    }
                } catch(IOException ex){
            // don't throw on read
        }
        return result;
    }

    @Override
    public Guest findById(String id) throws DataException {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findByGuestEmail(String email) throws DataException {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    /*
    private String getFilePath(LocalDate date) {
        return Paths.get(directory, date + ".csv").toString();
    }
     */

    //helper methods - private because they are only needed inside ForagerFileRepository:
    //Other classes do not need to call these methods directly.
    private void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);
            for (Guest reservation : guests) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    //guest_id,first_name,last_name,email,phone,state
    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                clean(guest.getFirstName()),
                clean(guest.getLastName()),
                clean(guest.getEmail()),
                clean(guest.getPhone()),
                clean(guest.getState()));
    }

    //not sure, if i do not do a new guest, only do reveration
    private Guest deserialize(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length == 6) {
            Guest result = new Guest();
            result.setId(fields[0]);
            result.setFirstName(restore(fields[1]));
            result.setLastName(restore(fields[2]));
            result.setEmail(restore(fields[3]));
            result.setPhone(restore(fields[4]));
            result.setState(restore(fields[5]));
            return result;
        }
        return null;
    }
}
