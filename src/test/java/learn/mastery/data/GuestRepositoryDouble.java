package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GuestRepositoryDouble implements GuestRepository {

    private final ArrayList<Guest> guest = new ArrayList<>();

    public final static Guest guest1 = new Guest("1", "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", "NV");
    public final static Guest guest2 = new Guest("2", "Olympie", "Gecks", "ogecks1@dagondesign.com", "(202) 2528316", "DC");

    public GuestRepositoryDouble() {
        //guest_id,first_name,last_name,email,phone,state
        //1,Sullivan,Lomas,slomas0@mediafire.com,(702) 7768761,NV
      //  Guest guest1 = new Guest("1", "Sullivan", "Lomas", "slomas0@mediafire.com", "(702) 7768761", "NV");
        addGuest(guest1);
        //2,Olympie,Gecks,ogecks1@dagondesign.com,(202) 2528316,DC
      //  Guest guest2 = new Guest("2", "Olympie", "Gecks", "ogecks1@dagondesign.com", "(202) 2528316", "DC");
        addGuest(guest2);
    }

    //helper for create some guest data
    public void addGuest(Guest guest) {
        this.guest.add(guest);
    }

    @Override
    public List<Guest> findAll() throws DataException {
        return guest;
    }

    @Override
    public Guest findById(String guest_id) throws DataException {
        return guest.stream()
                .filter(g -> g.getId().equals(guest_id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Guest> findByGuestEmail(String email) throws DataException {
        return guest.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}
