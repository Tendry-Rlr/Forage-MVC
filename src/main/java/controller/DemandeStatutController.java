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

import entity.Demande;
import entity.DemandeStatut;
import entity.Statut;
import service.DemandeService;
import service.DemandeStatutService;
import service.StatutService;

@Controller
public class DemandeStatutController {
    private final DemandeStatutService demandeStatutService;
    private final DemandeService demandeService;
    private final StatutService statutService;

    public DemandeStatutController(DemandeStatutService demandeStatutService, DemandeService demandeService,
            StatutService statutService) {
        this.demandeStatutService = demandeStatutService;
        this.demandeService = demandeService;
        this.statutService = statutService;
    }

    @GetMapping("/listeDemandeStatut")
    public String liste(Model modele) {
        List<DemandeStatut> ds = demandeStatutService.findAll();
        modele.addAttribute("demandeStatuts", ds);
        return "liste-demandeStatut";
    }

    @GetMapping("/updateForm/{id_demande_statut}")
    public String updateForm(Model modele, @PathVariable("id_demande_statut") Integer demandeStatutID) {

        DemandeStatut ds = demandeStatutService.findById(demandeStatutID);
        List<Statut> statuts = statutService.findAll();
        modele.addAttribute("demandeStatut", ds);
        modele.addAttribute("statuts", statuts);
        return "demandeStatut-update";
    }

    @PostMapping("/update-demandeStatut")
    public String update(@RequestParam("id_demande") Integer demandeID,
            @RequestParam("date") String dateStr,
            @RequestParam("id_demande_statut") Integer demandeStatutID) {

        // Parser la date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);

        DemandeStatut dsModifie = demandeStatutService.findById(demandeStatutID);
        dsModifie.setDate(localDateTime);

        demandeStatutService.update(dsModifie);

        List<DemandeStatut> liste = demandeStatutService.findByDemandeOrderByDate(demandeID);

        // Parcourir pour trouver la paire à recalculer
        for (int i = 0; i < liste.size() - 1; i++) {
            DemandeStatut current = liste.get(i);
            DemandeStatut next = liste.get(i + 1);

            // Recalculer le DT entre current et next
            demandeStatutService.update(next, current);
        }

        return "redirect:/listeDemandeStatut";
    }
}
