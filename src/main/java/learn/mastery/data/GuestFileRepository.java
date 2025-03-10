package learn.mastery.data;

import learn.mastery.models.Guest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//It uses a file to store its data.
public class GuestFileRepository implements GuestRepository {

    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
 //   private static final String HEADER = "guest_id,first_name,last_name,email,phone,state";
    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
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

    //helper methods - private because they are only needed inside GuestFileRepository:
    //Other classes do not need to call these methods directly.
    //Each guests field is separated by a delimiter, If the file delimiter, a carriage return, or a newline was written to the file,
    //it would ruin our ability to read the Guest. Here, we ensure those characters don't end up in the file.
    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    //guest_id,first_name,last_name,email,phone,state
    //not sure, if i do not do a new guest, only do reservation
    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getId(),
                clean(guest.getFirstName()),
                clean(guest.getLastName()),
                clean(guest.getEmail()),
                clean(guest.getPhone()),
                clean(guest.getState()));
    }

    //here reading a CSV file and converts each line into a Guest object using this method
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
