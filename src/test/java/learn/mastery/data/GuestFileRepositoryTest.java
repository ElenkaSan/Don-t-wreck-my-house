package learn.mastery.data;

import learn.mastery.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);
    // All of our tests are pre-arranged to a known good state with setupTest
    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() throws DataException {
        List actual = repository.findAll();
        assertEquals(6, actual.size());

        assertTrue(repository.findAll().size() == 6);
    }

    //2,Olympie,Gecks,ogecks1@dagondesign.com,(202) 2528316,DC
    @Test
    void findByIdGecks() throws DataException{
        Guest gecks = repository.findById("2");
        assertNotNull(gecks);
        assertEquals("Olympie", gecks.getFirstName());
        assertEquals("Gecks", gecks.getLastName());
        assertEquals("ogecks1@dagondesign.com", gecks.getEmail());
        assertEquals("(202) 2528316", gecks.getPhone());
        assertEquals("DC", gecks.getState());
    }

    @Test
    void findByGuestEmail() throws DataException {
        List<Guest> result = repository.findByGuestEmail("slomas0@mediafire.com");
        assertNotNull(result);
        assertEquals(1, result.size());

        assertTrue(repository.findByGuestEmail("slomas0@mediafire.com").size() == 1);
    }
}