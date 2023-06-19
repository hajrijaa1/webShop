package ba.ptf.si.passionis.service;

import ba.ptf.si.passionis.model.Proizvod;

import java.util.List;

public interface ProizvodService {
    List<Proizvod> getAll();
    void save(Proizvod proizvod);
    Proizvod getById(Integer id);
}
