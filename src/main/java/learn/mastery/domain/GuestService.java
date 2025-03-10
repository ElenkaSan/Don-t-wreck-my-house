package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

import java.util.List;

//A Java method can return at most one value. If we need two or more values, we must create a new data type
//that encapsulates the values and return an instance of the type.
public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> findAll() throws DataException {
        return repository.findAll();
    }

    public Guest findById(String guest_id) throws DataException {
        return repository.findById(guest_id);
    }

    public List<Guest> findByGuestEmail(String email) throws DataException {
        return repository.findByGuestEmail(email);
    }
}
