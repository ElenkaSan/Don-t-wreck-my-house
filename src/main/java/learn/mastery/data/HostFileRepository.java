package learn.mastery.data;

import learn.mastery.models.Host;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//It uses a file to store its data.
public class HostFileRepository implements HostRepository {
    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
//    private static final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String filePath;

    public HostFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Host> findAll() throws DataException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                Host host = deserialize(line);
                if(host != null){
                    result.add(host);
                }
            }
        } catch(IOException ex){
            // don't throw on read
        }
        return result;
    }

    @Override
    public Host findById(String id) throws DataException {
        return findAll().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findByHostEmail(String email) throws DataException {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }

    //helper methods - private because they are only needed inside HostFileRepository:
    //Other classes do not need to call these methods directly.
    //Each hosts field is separated by a delimiter, If the file delimiter, a carriage return, or a newline was written to the file,
    //it would ruin our ability to read the Host. Here, we ensure those characters don't end up in the file.
    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    //id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
    //not sure, if i do not do a new guest, only do reservation
    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                clean(host.getLastName()),
                clean(host.getEmail()),
                clean(host.getPhone()),
                clean(host.getAddress()),
                clean(host.getCity()),
                clean(host.getState()),
                clean(host.getPostal_code()),
                host.getStandard_rate(),
                host.getWeekend_rate());
    }

    //here reading a CSV file and converts each line into a Host object using this method
    private Host deserialize(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length == 10) {
            Host result = new Host();
            result.setId(fields[0]);
            result.setLastName(restore(fields[1]));
            result.setEmail(restore(fields[2]));
            result.setPhone(restore(fields[3]));
            result.setAddress(restore(fields[4]));
            result.setCity(restore(fields[5]));
            result.setState(restore(fields[6]));
            result.setPostal_code(restore(fields[7]));
            result.setStandard_rate(new BigDecimal(fields[8]));
            result.setWeekend_rate(new BigDecimal(fields[9]));
            return result;
        }
        return null;
    }
}
