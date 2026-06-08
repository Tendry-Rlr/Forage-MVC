package controller;

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
import entity.DemandeStatut;
import entity.Devis;
import entity.DevisMateriel;
import entity.Statut;
import entity.TypeDevis;
import service.ClientService;
import service.DemandeService;
import service.DemandeStatutService;
import service.DevisMaterielService;
import service.DevisService;
import service.StatutService;
import service.TypeDevisService;

@Controller
public class DevisController {
    private final DevisService devisService;
    private final ClientService clientService;
    private final DemandeService demandeService;
    private final DemandeStatutService demandeStatutService;
    private final DevisMaterielService devisMaterielService;
    private final TypeDevisService typeDevisService;
    private final StatutService statutService;

    public DevisController(DevisService devisService, ClientService client, DemandeService demandeService,
            DevisMaterielService devisMaterielService, TypeDevisService typeDevisService, StatutService statutService,
            DemandeStatutService demandeStatutService) {
        this.devisService = devisService;
        this.clientService = client;
        this.demandeService = demandeService;
        this.devisMaterielService = devisMaterielService;
        this.typeDevisService = typeDevisService;
        this.statutService = statutService;
        this.demandeStatutService = demandeStatutService;
    }

    @GetMapping("/listeDevis")
    public String liste(Model model) {
        List<Devis> devis = devisService.findAll();
        model.addAttribute("devis", devis);
        return "liste-devis";
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
            @RequestParam("typeId") Integer id_type,
            @RequestParam("nom") String[] nom,
            @RequestParam("pU") Double[] pU,
            @RequestParam("quantite") Double[] quantite, Model model) {

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
        TypeDevis type = typeDevisService.findById(id_type);

        // ajout dans devis
        Devis devis = new Devis(demande, localDateTime, type);
        Devis saved = devisService.save(devis);

        // ajoute dans devisMateriel
        for (int i = 0; i < nom.length; i++) {
            DevisMateriel devisMateriel = new DevisMateriel(saved, nom[i], pU[i], quantite[i]);
            devisMaterielService.save(devisMateriel);
        }

        int statut_id = 0;
        if (id_type == 1) {
            statut_id = 2;
        } else {
            statut_id = 5;
        }

        // ajout dans demande statut
        Statut statut = statutService.findById(statut_id);
        DemandeStatut demandeStatut = new DemandeStatut(localDateTime, demande, statut);
        demandeStatutService.save(demandeStatut);

        message = "Devis inseree";
        model.addAttribute("message", message);
        return "redirect:/to-devis";
    }

    @GetMapping("/update-devis/{id_devis}")
    public String udpate_form(@PathVariable("id_devis") Integer id_devis, Model model) {

        Devis devis = devisService.findById(id_devis);
        List<TypeDevis> types = typeDevisService.findAll();

        model.addAttribute("types", types);
        model.addAttribute("devis", devis);

        return "devis-update";
    }

    @PostMapping("/update-devis")
    public String update(
            @RequestParam("id_devis") Integer id_devis,
            @RequestParam("id_demande") Integer id_demande,
            @RequestParam("date_creation") String dateStr,
            @RequestParam("typeId") Integer id_type,
            @RequestParam("nom") String[] nom,
            @RequestParam("pU") Double[] pU,
            @RequestParam("quantite") Double[] quantite, Model model) {

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
        TypeDevis type = typeDevisService.findById(id_type);

        // update du devis
        Devis devis = new Devis(demande, localDateTime, type);
        devis.setId_devis(id_devis);

        Devis saved = devisService.update(devis);

        // remove dans devisMateriel
        devisMaterielService.deleteById(saved.getId_devis());

        // ajoute dans devisMateriel
        for (int i = 0; i < nom.length; i++) {
            DevisMateriel devisMateriel = new DevisMateriel(saved, nom[i], pU[i], quantite[i]);
            devisMaterielService.save(devisMateriel);
        }

        int statut_id = 0;
        if (id_type == 1) {
            statut_id = 2;
        } else {
            statut_id = 5;
        }

        // ajout dans demande statut
        Statut statut = statutService.findById(statut_id);
        DemandeStatut demandeStatut = new DemandeStatut(localDateTime, demande, statut);
        demandeStatutService.save(demandeStatut);

        message = "Devis mise a jour";
        model.addAttribute("message", message);
        return "redirect:/listeDevis";
    }

    @GetMapping("/accepter-devis/{id_devis}")
    public String accept(@PathVariable("id_devis") Integer id_devis) {
        Devis devis = devisService.findById(id_devis);
        Statut statut = statutService.findById(3);

        LocalDateTime now = LocalDateTime.now();
        DemandeStatut demandeStatut = new DemandeStatut(now, devis.getDemande(), statut);
        demandeStatutService.save(demandeStatut);

        return "redirect:/listeDevis";
    }

    @GetMapping("/refuser-devis/{id_devis}")
    public String reject(@PathVariable("id_devis") Integer id_devis) {
        Devis devis = devisService.findById(id_devis);
        Statut statut = statutService.findById(4);

        LocalDateTime now = LocalDateTime.now();
        DemandeStatut demandeStatut = new DemandeStatut(now, devis.getDemande(), statut);
        demandeStatutService.save(demandeStatut);

        return "redirect:/listeDevis";
    }

}
