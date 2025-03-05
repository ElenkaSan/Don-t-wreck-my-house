package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservations-seed-9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    final LocalDate start_date = LocalDate.of(2020, 07, 01);
    final LocalDate end_date = LocalDate.of(2020, 07, 02);

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    //in revervation_test folder has file name ander host id as 9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv where inside
    //id,start_date,end_date,guest_id,total
    //1,2020-07-01,2020-07-02,18,870

    @Test //find file host_id.cvs
    void findByHostId() throws DataException {
        List<Reservation> host = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertNotNull(host);
        assertEquals(2, host.size());
    }

    //no sure i need this

    @Test
    void findById() {
        Reservation result = repository.findById(1, "9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertNotNull(result);
        //assertEquals(1, result.getId());
        assertEquals(start_date, result.getStart_date());
        assertEquals(end_date, result.getEnd_date());
        assertEquals("18", result.getGuest().getId());
        assertEquals(new BigDecimal(870), result.getTotal());
    }


    @Test
    void shouldFindByDateAndHostIdFileName() {
        List<Reservation> date = repository.findByDate("9d469342-ad0b-4f5a-8d28-e81e690ba29a", start_date);
        assertNotNull(date);
        assertEquals(2, date.size());
    }

    //file hosts-test.cvs has inside
    //id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
    //9d469342-ad0b-4f5a-8d28-e81e690ba29a,Wigfield,kwigfieldiy@php.net,(305) 8769397,88875 Miller Parkway,Miami,FL,33185,435,543.75

    //18,Jacquenetta,Judgkins,jjudgkinsh@goo.gl,(802) 9364252,VT
    @Test
    void shouldFindFileByGuestId() throws DataException {
        List<Reservation> reservations = repository.findByGuestId("18");
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
    }

    /* no sure need it here
    @Test
    void shouldFindFileByGuestEmail() throws DataException {
        List<Reservation> reservations = repository.findByGuestEmail("jjudgkinsh@goo.gl");
        assertNotNull(reservations);
        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
    }
     */

    //file guests-test.csv has
    //guest_id,first_name,last_name,email,phone,state
    //3,Tremain,Carncross,tcarncross2@japanpost.jp,(313) 2245034,MI
    @Test
    void shouldAdd() {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setTotal(new BigDecimal(870));

        Guest guest = new Guest();
        guest.setId("3"); //should create reservation for guest id
        reservation.setGuest(guest);

        Host host = new Host();
        host.setId("9d469342-ad0b-4f5a-8d28-e81e690ba29a"); //should create file name under host id
        reservation.setHost(host);

        reservation = repository.add(reservation);
        assertEquals(3, reservation.getId()); //should add inside host id file 9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv
                                                        //id,start_date,end_date,guest_id,total
                                                        //2,2020-07-01,2020-07-02,3,870
    }

    @Test
    void update() {
        Reservation reservation = repository.findById(2, "9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        reservation.setStart_date(LocalDate.of(2025, 03, 3));
        reservation.setEnd_date(LocalDate.of(2025, 03, 5));
        reservation.setTotal(new BigDecimal(1305));
        assertTrue(repository.update(reservation));

       // List<Reservation> guest = repository.findByGuestId("18");
        Reservation actual = repository.findById(2, "9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertNotNull(actual);
        assertEquals(LocalDate.of(2025, 3, 3), actual.getStart_date());
        assertEquals(LocalDate.of(2025, 3, 5), actual.getEnd_date());
        assertEquals(new BigDecimal(1305), actual.getTotal());
    }

    //
    @Test
    void deleteById() {
        Reservation reservation = repository.findById(1, "9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertNotNull(reservation);
        assertTrue(repository.deleteById(1,"9d469342-ad0b-4f5a-8d28-e81e690ba29a"));
        assertFalse(repository.deleteById(10242, "9d469342-ad0b-4f5a-8d28-e81e690ba29a"));
        Reservation actual = repository.findById(1, "9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertNull(actual);
        List<Reservation> resultDeleted = repository.findByHostId("9d469342-ad0b-4f5a-8d28-e81e690ba29a");
        assertEquals(1, resultDeleted.size());

    }
}