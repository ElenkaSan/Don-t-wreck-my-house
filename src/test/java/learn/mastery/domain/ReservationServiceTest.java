package learn.mastery.domain;

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

/*
    @Test
    void shouldFindByHostId() {
        List<Reservation> host = service.findByHostId(HostRepositoryDouble.host1.getId());
        assertNotNull(host);
        assertEquals(2, host.size());
    }
    @Test
    void shouldFindById() {
        Reservation result = service.findById(1, HostRepositoryDouble.host2.getId());
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(start_date, result.getStart_date());
        assertEquals(end_date, result.getEnd_date());
        assertEquals("18", result.getGuest().getId());
        assertEquals(new BigDecimal(870), result.getTotal());
    }
     @Test
    void shouldFindFileByGuestId() {
    }
     @Test
    void shouldFindByDateAndHostIdFileName() {
    }

 */

    //host:
    //f92aa2ac-5370-4c61-87e3-3f18a81ce2e6,Moorcroft,bmoorcroftj@topsy.com,(352) 5465804,28 Badeau Avenue,Ocala,FL,34479,477,596.25
    @Test
    void shouldAddReservation() {
        Reservation reservation = new Reservation();
        //id,start_date,end_date,guest_id,total
        reservation.setStart_date(start_date);
        reservation.setEnd_date(end_date);
        reservation.setGuest(GuestRepositoryDouble.guest1); //id 1
        reservation.setTotal(new BigDecimal(870));

        Host host = new Host();
        host.setId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6"); //should create file name under host id
        host.setEmail("charley4@apple.com");
        reservation.setHost(host);

        Result<Reservation> result = service.add(reservation);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals("1", result.getPayload().getGuest().getId());
        assertEquals(1, result.getPayload().getId());
    }


    @Test
    void shouldUpdate() {
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

    //need to fix
    @Test
    void shouldDeleteById() {
        Reservation reservation = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNotNull(reservation);

        Result<Reservation> removed = service.deleteById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertTrue(removed.isSuccess());

        Reservation actual = service.findById(1, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        System.out.println("ERROR" + actual);
        assertNull(actual);

        List<Reservation> resultDeleted = service.findByHostId("f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertEquals(0, resultDeleted.size());
    }

    //need to add negative cases
    @Test
    void shouldNotFindDeleteNoExistingId() {
        Result<Reservation> noId = service.deleteById(1042, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        System.out.println("ERROR" + noId);
        assertFalse(noId.isSuccess());

        Reservation nonExistentReservation = service.findById(1042, "f92aa2ac-5370-4c61-87e3-3f18a81ce2e6");
        assertNull(nonExistentReservation);
    }
}