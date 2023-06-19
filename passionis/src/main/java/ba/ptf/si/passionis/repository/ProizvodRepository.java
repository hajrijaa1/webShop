package ba.ptf.si.passionis.repository;

import ba.ptf.si.passionis.model.Proizvod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {
}
