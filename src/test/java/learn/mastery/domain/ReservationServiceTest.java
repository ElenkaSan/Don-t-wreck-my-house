package learn.mastery.domain;

import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.Test;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new ReservationRepositoryDouble(),
            new GuestRepositoryDouble(),
            new HostRepositoryDouble());


    @Test
    void shouldFindByHostId() {
    }

    @Test
    void shouldFindById() {
    }

    @Test
    void shouldFindByDateAndHostIdFileName() {
    }

    @Test
    void shouldFindFileByGuestId() {
    }

    @Test
    void shouldAddReservation() {
    }

    @Test
    void shouldUpdate() {
    }

    @Test
    void shouldDeleteById() {
    }
}