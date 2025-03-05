package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
    void findAll() throws DataException{
        assertTrue(repository.findAll().size() == 4);
        List<Host> actual = repository.findAll();
        assertEquals(4, actual.size());
    }

    @Test
    void findById() throws DataException {
    }

    @Test
    void findByHostEmail() throws DataException {
    }
}