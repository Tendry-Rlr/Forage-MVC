package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entity.Client;
import entity.Demande;
import entity.Devis;
import entity.DevisMateriel;
import entity.TypeDevis;
import service.ClientService;
import service.DemandeService;
import service.DevisMaterielService;
import service.DevisService;
import service.TypeDevisService;

@Controller
public class DevisController {
    private final DevisService devisService;
    private final ClientService clientService;
    private final DemandeService demandeService;
    private final DevisMaterielService devisMaterielService;
    private final TypeDevisService typeDevisService;

    public DevisController(DevisService devisService, ClientService client, DemandeService demandeService,
            DevisMaterielService devisMaterielService, TypeDevisService typeDevisService) {
        this.devisService = devisService;
        this.clientService = client;
        this.demandeService = demandeService;
        this.devisMaterielService = devisMaterielService;
        this.typeDevisService = typeDevisService;
    }

    @GetMapping("/to-devis")
    public String voir_devis(Model model) {
        List<TypeDevis> types = typeDevisService.findAll();
        model.addAttribute("types", types);

        return "devis-form";
    }

    @GetMapping("/voir-devis/{id_client}/{id_demande}")
    public String to_devis(@PathVariable("id_client") Integer id_client,
            @PathVariable("id_demande") Integer id_demande, Model model) {

        Client client = clientService.findById(id_client);
        Demande demande = this.demandeService.findById(id_demande);

        model.addAttribute("client", client);
        model.addAttribute("demande", demande);

        return "devis";
    }

    @PostMapping("/insert-devis")
    public String save(
            @RequestParam("id_demande") Integer id_demande,
            @RequestParam("date_creation") String dateStr,
            @RequestParam("nom") String[] nom,
            @RequestParam("pU") Double[] pU,
            @RequestParam("quantite") Integer[] quantite, Model model) {

        String message = "";
        if (nom.length != pU.length || nom.length != quantite.length
                || quantite.length != pU.length) {
            message = "Champs vide present";
            model.addAttribute("message", message);
            return "redirect:/to-devis";
        }

        // caster la date-time
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);

        Demande demande = demandeService.findById(id_demande);

        Devis devis = new Devis(demande, Timestamp.valueOf(localDateTime));
        Devis saved = devisService.save(devis);

        for (int i = 0; i < nom.length; i++) {
            DevisMateriel devisMateriel = new DevisMateriel(saved, nom[i], pU[i], quantite[i]);
            devisMaterielService.save(devisMateriel);
        }

        message = "Devis inseree";
        model.addAttribute("message", message);
        return "redirect:/to-devis";
    }

}
