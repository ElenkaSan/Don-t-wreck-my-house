package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.models.Host;

import java.util.List;

public class HostService {
    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findAll() throws DataException {
        return repository.findAll();
    }

    public Host findById(String host_id) throws DataException {
        return repository.findById(host_id);
    }

    public List<Host> findByHostEmail(String email) throws DataException {
        return repository.findByHostEmail(email);
    }
}
