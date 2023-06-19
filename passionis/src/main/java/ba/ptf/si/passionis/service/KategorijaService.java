package ba.ptf.si.passionis.service;

import ba.ptf.si.passionis.model.Kategorija;

import java.util.List;

public interface KategorijaService {
    List<Kategorija> getAll();
    void save(Kategorija kategorija);
    Kategorija getById(Integer id);
}
