package ba.ptf.si.passionis.controller;

import ba.ptf.si.passionis.model.Kategorija;
import ba.ptf.si.passionis.model.Proizvod;
import ba.ptf.si.passionis.model.ProizvodDto;
import ba.ptf.si.passionis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private KategorijaServiceImpl kategorijaService;

    @Autowired
    private ProizvodServiceImpl proizvodService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        return "index";
    }

    @GetMapping("/ponuda")
    public String viewMenu(Model model, HttpSession session){
        List<Proizvod> rezultat = proizvodService.getAll();
        model.addAttribute("ponuda", rezultat);

        List<Proizvod> korpa = (List<Proizvod>) session.getAttribute("korpa");
        if(korpa == null){
            korpa = new ArrayList<Proizvod>();
        }

        model.addAttribute("korpa", korpa);

        return "ponuda";
    }

    @GetMapping("/azuriraj")
    public String viewAzuriraj(Model model){
        List<Proizvod> rezultat = proizvodService.getAll();
        model.addAttribute("ponuda", rezultat);

        return "azuriraj";
    }

    @GetMapping("/azuriraj/jelo")
    public String viewAzurirajJelo(@RequestParam String id, Model model, HttpSession session){
        Proizvod rezultat = proizvodService.getById(Integer.parseInt(id));

        List<Kategorija> kategorijaList = kategorijaService.getAll();
        model.addAttribute("kategorije", kategorijaList);
        model.addAttribute("trenutniArtikal", rezultat);
        model.addAttribute("noviArtikal", new ProizvodDto());
        session.setAttribute("artikalId", rezultat.getId());

        return "azuriraj-artikal";
    }

    @GetMapping("/ponuda/artikal")
    public String viewMenuItem(@RequestParam String id, Model model){

        Proizvod rezultat = proizvodService.getById(Integer.parseInt(id));
        model.addAttribute("artikal", rezultat);
        return "artikal";
    }

    @GetMapping("/ponuda/dodaj")
    public String viewDodajProizvod(Model model){
        List<Kategorija> kategorijaList = kategorijaService.getAll();
        model.addAttribute("kategorije", kategorijaList);
        model.addAttribute("noviArtikal", new ProizvodDto());
        return "dodaj";
    }

    @RequestMapping(value = "/save", method= RequestMethod.POST)
    public String sacuvajProizvod(@ModelAttribute("noviProizvod") ProizvodDto proizvodDto, HttpSession session){
        Proizvod proizvod = new Proizvod(
          proizvodDto.getId(),
          proizvodDto.getNaziv(),
          proizvodDto.getCijena(),
          kategorijaService.getById(proizvodDto.getKategorija_id())
        );

        Integer artikalId = (Integer) session.getAttribute("proizvodId");
        if(artikalId != null){
            proizvod.setId(artikalId);
        }

        session.setAttribute("artikalId", null);

        proizvodService.save(proizvod);
        return "redirect:/ponuda";
    }


    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }


    @GetMapping("/korpa")
    public String viewKorpa(Model model, HttpSession session){

        List<Proizvod> rezultat;

        rezultat = (List<Proizvod>) session.getAttribute("korpa");
        if(rezultat == null){
            rezultat = new ArrayList<Proizvod>();
            session.setAttribute("korpa", rezultat);
        }

        Float cijena = 0.0f;
        for(Proizvod proizvod : rezultat){
            cijena += proizvod.getCijena();
        }

        model.addAttribute("cijena", cijena);
        model.addAttribute("korpa", rezultat);

        return "korpa";
    }

    @GetMapping("/korpa/dodaj")
    public String dodajUKorpu(@RequestParam String id, Model model, HttpSession session){

        List<Proizvod> rezultat;

        rezultat = (List<Proizvod>) session.getAttribute("korpa");
        if(rezultat == null){
            rezultat = new ArrayList<Proizvod>();
            session.setAttribute("korpa", rezultat);
        }

        rezultat.add(proizvodService.getById(Integer.parseInt(id)));

        this.viewMenu(model, session);
        return "ponuda";
    }

    @GetMapping("/korpa/kraj")
    public String zavrsiKorpu(HttpSession session){
        session.setAttribute("korpa", new ArrayList<Proizvod>());
        return "kraj-kupovine";
    }

    @GetMapping("/korpa/izbrisi")
    public String izbrisiKorpu(Model model, HttpSession session){
        session.setAttribute("korpa", new ArrayList<Proizvod>());
        this.viewKorpa(model, session);
        return "korpa";
    }

    @GetMapping("/error")
    public String setSesija(Model model){
        return "error";
    }
}
