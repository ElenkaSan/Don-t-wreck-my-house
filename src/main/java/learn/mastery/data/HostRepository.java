package learn.mastery.data;

import learn.mastery.models.Host;

import java.util.List;

public interface HostRepository {
    List<Host> findAll() throws DataException;

    Host findById(String id) throws DataException;

    List<Host> findByHostEmail(String email) throws DataException;
}
