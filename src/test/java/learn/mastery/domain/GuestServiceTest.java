package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.models.Guest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {
    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindAllGuests() throws DataException {
        List<Guest> all = service.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        assertTrue(service.findAll().size() == 2);
    }

    @Test
    void shouldFindByGuestId() throws DataException {
        Guest gecks = service.findById("2");
        assertNotNull(gecks);
        assertEquals("Olympie", gecks.getFirstName());
        assertEquals("Gecks", gecks.getLastName());
        assertEquals("ogecks1@dagondesign.com", gecks.getEmail());
        assertEquals("(202) 2528316", gecks.getPhone());
        assertEquals("DC", gecks.getState());
    }
    @Test
    void shouldNotFindNotExistingGuestId() throws DataException {
        Guest noId = service.findById("220");
        assertNull(noId);
    }

    @Test
    void shouldFindByGuestEmail() throws DataException {
        assertTrue(service.findByGuestEmail("slomas0@mediafire.com").size() == 1);
        assertTrue(service.findByGuestEmail("ogecks1@dagondesign.com").size() == 1);
    }

    @Test
    void  shouldNotFindNotExistingGuestEmail() throws DataException {
        assertFalse(service.findByGuestEmail("ogecNo1@dag.com").size() == 1);
    }
}