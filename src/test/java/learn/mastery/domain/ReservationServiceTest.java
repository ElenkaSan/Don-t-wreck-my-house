package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());

    final LocalDate start_date = LocalDate.of(2020, 07, 01);
    final LocalDate end_date = LocalDate.of(2020, 07, 02);

    //host:
    //f92aa2ac-5370-4c61-87e3-3f18a81ce2e6,Moorcroft,bmoorcroftj@topsy.com,(352) 5465804,28 Badeau Avenue,Ocala,FL,34479,477,596.25
    @Test
    void shouldAddReservation() throws DataException {
        Reservation reservation = new Reservation();
        //id,start_date,end_date,guest_id,total
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest2); //id 2
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6"); //should create file name under host id
        host.setEmail("charley4@apple.com");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("2", result.getPayload().getGuest().getId());
        assertEquals(2, result.getPayload().getId());
    }

    @Test
    void shouldNotAddDuplicate() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1); //id 1
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        host.setEmail("charley4@apple.com");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("Duplicate Reservation is not allowed"));
    }

    @Test
    void shouldNotAddTheSameStartEndDates() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(start_date);
        reservation.setEnd_date(start_date);
      //  reservation.setEnd_date(LocalDate.of(2020, 07, 01));
        reservation.setGuest(GuestRepositoryDouble.guest1); //id 1
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        host.setEmail("charley4@apple.com");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        System.out.println(result.getErrorMessages());
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
    }
/* no need anymore
    @Test
    void shouldNotAddNull() throws DataException {
        Reservation reservation = new Reservation();
        reservation.setStart_date(null);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1); //id 1
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        host.setEmail("charley4@apple.com");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertFalse(result.isSuccess());
        assertNull(result.getPayload());
        assertTrue(result.getErrorMessages().contains("Reservation start and end dates are required."));
    }
 */

    @Test
    void shouldUpdate() throws DataException {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        reservation.setStart_date(LocalDate.of(2025, 03, 3));
        reservation.setEnd_date(LocalDate.of(2025, 03, 5));
        reservation.setTotal(new BigDecimal(1305));

        Result<Reservation> result = service.update(reservation);
        assertTrue(result.isSuccess());

        Reservation actual = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNotNull(actual);
        assertEquals(LocalDate.of(2025, 3, 3), actual.getStart_date());
        assertEquals(LocalDate.of(2025, 3, 5), actual.getEnd_date());
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

    //need more work on it - It's Working on UI!
    @Test
    void shouldNotUpdateOverlapDates() throws DataException {
        List<Reservation> allReservations = service.findByHostId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        System.out.println("Reservations before update: " + allReservations);

        Reservation existing = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        existing.setStart_date(LocalDate.of(2025, 03, 4));
        existing.setEnd_date(LocalDate.of(2025, 03, 6));
        existing.setTotal(new BigDecimal(1305));
        service.add(existing);
       /*
        Reservation existing = new Reservation();
            existing.setId(1);
            existing.setStart_date(LocalDate.of(2025, 03, 4));
            existing.setEnd_date(LocalDate.of(2025, 03, 6));
            existing.setHost(new Host());
            existing.setTotal(new BigDecimal(1200));
            existing.getHost().setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
            service.add(existing);
        */

            Reservation reservation = new Reservation();
            reservation.setId(2);
            reservation.setStart_date(LocalDate.of(2025, 03, 3));
            reservation.setEnd_date(LocalDate.of(2025, 03, 5)); // Overlaps with existing reservation
            reservation.setHost(new Host());
            reservation.setTotal(new BigDecimal(1305));
            reservation.getHost().setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");

            Result<Reservation> result = service.update(reservation);
            System.out.println(result.getErrorMessages());
            assertFalse(result.isSuccess());
            assertNull(result.getPayload());
            assertTrue(result.getErrorMessages().contains("The reservation may not overlap with existing reservation dates."));
    }

    //need to fix
    @Test
    void shouldDeleteById() throws DataException {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
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

}