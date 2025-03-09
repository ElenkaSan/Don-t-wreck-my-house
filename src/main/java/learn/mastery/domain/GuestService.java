package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;

import java.util.List;

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
