package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll() throws DataException;

    Guest findById(String id) throws DataException;

    List<Guest> findByGuestEmail(String email) throws DataException;


}
