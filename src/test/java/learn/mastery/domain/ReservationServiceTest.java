package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service;
    final LocalDate start_date = LocalDate.of(2025, 03, 13);
    final LocalDate end_date = LocalDate.of(2025, 03, 14);

    @BeforeEach
    void setup() {
        ReservationRepositoryDouble resRepository = new ReservationRepositoryDouble();
        GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();
        HostRepositoryDouble hostRepository = new HostRepositoryDouble();
        service = new ReservationService(resRepository, guestRepository, hostRepository);
    }

    @Test
    void shouldFindByHostEmail() throws DataException {
        List<Reservation> result = service.findByHostEmail("eyearnes0@sfgate.com"); //from reservationTwo

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(36, result.get(0).getHost().getId().length());
        assertEquals("Yearnes", result.get(0).getHost().getLastName());
        assertEquals("(806) 1783815", result.get(0).getHost().getPhone());
        assertEquals("Amarillo", result.get(0).getHost().getCity());
    }

    @Test
    void shouldNotFindByWrongEmail() throws DataException {
        List<Reservation> result = service.findByHostEmail("eyear@ss.com");
        assertNull(result);
    }

    @Test
    void shouldFindByHostId() throws  DataException {
        List<Reservation> result = service.findByHostId("3edda6bc-ab95-49a8-8962-d50b53f84b15");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals( LocalDate.of(2020, 07, 01), result.get(0).getStart_date());
        assertEquals( LocalDate.of(2020, 07, 02), result.get(0).getEnd_date());
        assertEquals("2", result.get(0).getGuest().getId()); //from guest2 id2
        assertEquals(new BigDecimal(680), result.get(0).getTotal());
    }

    //id,start_date,end_date,guest_id,total
    //host:
    //f92aa2ac-5370-4c61-87e3-3f18a81ce2e6,Moorcroft,bmoorcroftj@topsy.com,(352) 5465804,28 Badeau Avenue,Ocala,FL,34479,477,596.25
    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest2); //id 2
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6"); //should create file name under host id
        host.setStandard_rate(new BigDecimal(176)); //standard 176
        host.setWeekend_rate(new BigDecimal(220)); //weekend 220
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("2", result.getPayload().getGuest().getId());
        assertEquals(3, result.getPayload().getId());
    }

    @Test
    void shouldNotAddNull() throws DataException {
        Result<Reservation> result = service.add(null);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddDuplicate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1); //id 2
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        host.setStandard_rate(new BigDecimal(176)); //standard 176
        host.setWeekend_rate(new BigDecimal(220)); //weekend 220
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("Duplicate Reservation is not allowed"));
    }

    @Test
    void shouldNotAddStartDateAfterEndDate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(LocalDate.of(2025, 03, 6));
        reservation.setEnd_date(LocalDate.of(2025, 03, 5));
        reservation.setTotal(new BigDecimal(1305));

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("The start date must come before the end date."));
    }

    @Test
    void shouldNotAddTheSameStartEndDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(start_date);
        reservation.setGuest(GuestRepositoryDouble.guest2); //id 2
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("The start date must come before the end date."));
    }

    @Test
    void shouldNotAddToBookPastDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(LocalDate.of(2025, 03, 3));
        reservation.setEnd_date(LocalDate.of(2025, 03, 5));
        reservation.setGuest(GuestRepositoryDouble.guest1);
        reservation.setTotal(new BigDecimal(954));

        Host host = new Host("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6","Moorcroft","bmoorcroftj@topsy.com","(352) 5465804","28 Badeau Avenue","Ocala","FL","34479",new BigDecimal(477),new BigDecimal(596.25));
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("Reservations must be made for today or a future date."));
    }

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        reservation.setStart_date(LocalDate.of(2025, 03, 17));
        reservation.setEnd_date(LocalDate.of(2025, 03, 19));
        reservation.setTotal(new BigDecimal(1305));

        Result<Reservation> result = service.update(reservation);
        assertTrue(result.isSuccess());

        Reservation actual = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNotNull(actual);
        assertEquals(LocalDate.of(2025, 3, 17), actual.getStart_date());
        assertEquals(LocalDate.of(2025, 3, 19), actual.getEnd_date());
        assertEquals(new BigDecimal(1305), actual.getTotal());
    }

    @Test
    void shouldNotUpdateStartDateAfterEndDate() throws DataException {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        reservation.setStart_date(LocalDate.of(2025, 03, 6));
        reservation.setEnd_date(LocalDate.of(2025, 03, 5));
        reservation.setTotal(new BigDecimal(1305));

        Result<Reservation> result = service.update(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("The start date must come before the end date."));
    }

    @Test
    void shouldDeleteById() throws DataException {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6"); //future date
        assertNotNull(reservation);

        Result<Reservation> removed = service.deleteById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertTrue(removed.isSuccess());

        Reservation actual = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNull(actual);

        List<Reservation> resultDeleted = service.findByHostId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertEquals(0, resultDeleted.size());
    }

    @Test
    void shouldNotFindDeleteNoExistingId() throws DataException {
        Result<Reservation> result = service.deleteById(1042, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessages().contains("Reservation 1042 not found for this host f92aa2ac-5370-4c61-87e3-3f18a81ce2e6"));

        Reservation nonExistentReservation = service.findById(1042, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNull(nonExistentReservation);
    }


    @Test
    void shouldNotDeletePastReservation() throws DataException {
        Reservation reservation = service.findById(2, "3edda6bc-ab95-49a8-8962-d50b53f84b15"); //past date
        assertNotNull(reservation);
        Result<Reservation> removed = service.deleteById(2, "3edda6bc-ab95-49a8-8962-d50b53f84b15");

        assertFalse(removed.isSuccess());
        assertNull(removed.getPayload());
        assertTrue(removed.getErrorMessages().contains("Error, cannot cancel past reservations."));
    }
}