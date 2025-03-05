package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostServiceDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {
    HostService service = new HostService(new HostServiceDouble());

    @Test
    void shouldFindAllHosts() {
        assertTrue(service.findAll().size() == 2);
    }

    @Test
    void shouldFindByHostId() {
        Host yearnes = service.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(yearnes);
        assertEquals("Yearnes", yearnes.getLastName());
        assertEquals("(806) 1783815", yearnes.getPhone());
        assertEquals("Amarillo", yearnes.getCity());
    }

    @Test
    void shouldNotFindNotExistingHostUuid() throws DataException {
        Host noId = service.findById("220-eknwcm");
        assertNull(noId);
    }

    @Test
    void shouldFindByHostEmail() {
        assertTrue(service.findByHostEmail("eyearnes0@sfgate.com").size() == 1);
        assertTrue(service.findByHostEmail("krhodes1@posterous.com").size() == 1);
    }

    @Test
    void shouldNotFindNotExistingHostEmail() throws DataException {
        assertFalse(service.findByHostEmail("ogecNo1@dag.com").size() == 1);
    }
}