package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/hosts-seed.csv";
    static final String TEST_FILE_PATH = "./data/hosts-test.csv";
   // static final int NEXT_ID = ;

            HostFileRepository repository = new HostFileRepository(TEST_FILE_PATH);
    // All of our tests are pre-arranged to a known good state with setupTest
    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataException {
        assertTrue(repository.findAll().size() == 5);
        List<Host> actual = repository.findAll();
        assertEquals(5, actual.size());
    }

    //3edda6bc-ab95-49a8-8962-d50b53f84b15,Yearnes,eyearnes0@sfgate.com,(806) 1783815,3 Nova Trail,Amarillo,TX,79182,340,425
    @Test
    void shouldFindByIdYearnes() throws DataException {
        Host yearnes = repository.findById("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertNotNull(yearnes);
        assertEquals("Yearnes", yearnes.getLastName());
        assertEquals("eyearnes0@sfgate.com", yearnes.getEmail());
        assertEquals("(806) 1783815", yearnes.getPhone());
        assertEquals("3 Nova Trail", yearnes.getAddress());
        assertEquals("Amarillo", yearnes.getCity());
        assertEquals("TX", yearnes.getState());
        assertEquals("79182", yearnes.getPostal_code());
        assertEquals(new BigDecimal(340), yearnes.getStandard_rate());
        assertEquals(new BigDecimal(425),yearnes.getWeekend_rate());
    }

    @Test
    void findByHostEmail() throws DataException {
        assertTrue(repository.findByHostEmail("krhodes1@posterous.com").size() == 1);
    }
}