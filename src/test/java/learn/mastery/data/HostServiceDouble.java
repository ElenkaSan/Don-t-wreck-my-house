package learn.mastery.data;

import learn.mastery.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostServiceDouble implements HostRepository {

    private final ArrayList<Host> host = new ArrayList<>();

    public HostServiceDouble() {
        //id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
        //3edda6bc-ab95-49a8-8962-d50b53f84b15,Yearnes,eyearnes0@sfgate.com,(806) 1783815,3 Nova Trail,Amarillo,TX,79182,340,425
        Host host1 = new Host(
                "3edda6bc-ab95-49a8-8962-d50b53f84b15", "Yearnes", "eyearnes0@sfgate.com", "(806) 1783815", "3 Nova Trail", "Amarillo", "TX", "79182", new BigDecimal(340), new BigDecimal(425));
        addHost(host1);
        //a0d911e7-4fde-4e4a-bdb7-f047f15615e8,Rhodes,krhodes1@posterous.com,(478) 7475991,7262 Morning Avenue,Macon,GA,31296,295,368.75
        Host host2 = new Host("a0d911e7-4fde-4e4a-bdb7-f047f15615e8", "Rhodes", "krhodes1@posterous.com", "(478) 7475991", "7262 Morning Avenu", "Macon", "GA", "31296", new BigDecimal(295), new BigDecimal(368.75));
        addHost(host2);
    }

    //helper for create some guest data
    public void addHost(Host host) {
        this.host.add(host);
    }

    @Override
    public List<Host> findAll() throws DataException {
        return host;
    }

    @Override
    public Host findById(String host_id) throws DataException {
        return host.stream()
                .filter(g -> g.getId().equals(host_id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Host> findByHostEmail(String email) throws DataException {
        return host.stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
    }
}
