package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.xml.StaxUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import entity.Client;
import entity.Commune;
import entity.Demande;
import entity.DemandeStatut;
import entity.Devis;
import entity.Statut;
import service.ClientService;
import service.CommuneService;
import service.DemandeService;
import service.DemandeStatutService;
import service.DevisMaterielService;
import service.DevisService;
import service.DistrictService;
import service.RegionService;
import service.StatutService;

@Controller
public class DemandeController {
    private final DemandeService demandeService;
    private final DemandeStatutService demandeStatutService;
    private final StatutService statutService;
    private final ClientService clientService;
    private final CommuneService communeService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final DevisService devisService;
    private final DevisMaterielService devisMaterielService;

    public DemandeController(DemandeService demandeService, DemandeStatutService demandeStatutService,
            StatutService statutService, ClientService clientService, CommuneService communeService,
            RegionService regionService, DistrictService districtService, DevisService devisService,
            DevisMaterielService devisMaterielService) {
        this.demandeService = demandeService;
        this.demandeStatutService = demandeStatutService;
        this.statutService = statutService;
        this.clientService = clientService;
        this.communeService = communeService;
        this.regionService = regionService;
        this.districtService = districtService;
        this.devisService = devisService;
        this.devisMaterielService = devisMaterielService;
    }

    @PostMapping("/insertDemande")
    public String save(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("communeId") Integer communeId,
            @RequestParam("lieu") String lieu,
            @RequestParam("date") String dateStr) {

        try {
            // Valider les paramètres
            if (clientId == null || clientId <= 0) {
                throw new IllegalArgumentException("Client doit être sélectionné");
            }
            if (communeId == null || communeId <= 0) {
                throw new IllegalArgumentException("Commune doit être sélectionnée");
            }
            if (lieu == null || lieu.trim().isEmpty()) {
                throw new IllegalArgumentException("Lieu ne peut pas être vide");
            }
            if (dateStr == null || dateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Date ne peut pas être vide");
            }

            // Récupérer les entités
            Client client = clientService.findById(clientId);
            if (client == null) {
                throw new IllegalArgumentException("Client non trouvé avec id: " + clientId);
            }

            Commune commune = communeService.findById(communeId);
            if (commune == null) {
                throw new IllegalArgumentException("Commune non trouvée avec id: " + communeId);
            }

            // Parser la date
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);

            // Créer et sauvegarder la demande
            Demande demande = new Demande();
            demande.setClient(client);
            demande.setCommune(commune);
            demande.setLieu(lieu);
            Demande saved = demandeService.save(demande);

            // Créer le statut initial
            Statut statut = statutService.findById(1);
            if (statut == null) {
                throw new IllegalArgumentException("Statut 'Creee' non trouvé en base de données");
            }

            // Sauvegarder l'état de la demande
            DemandeStatut st = new DemandeStatut();
            st.setDate(Timestamp.valueOf(localDateTime));
            st.setDemande(saved);
            st.setStatut(statut);
            demandeStatutService.save(st);

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/?error=" + e.getMessage();
        }
    }

    @GetMapping("/listeDemande")
    public String listeDemandes(Model model) {
        model.addAttribute("demandes", demandeService.findAllWithStatuts());

        return "liste-demande";
    }

    @GetMapping("/supprimer/{id_demande}")
    public String removeDemande(@PathVariable("id_demande") Integer id_demande) {
        List<DemandeStatut> demandeStatus = demandeStatutService.findByDemande(id_demande);
        for (DemandeStatut ds : demandeStatus) {
            this.demandeStatutService.deleteById(ds.getId_demande_statut());
        }

        Devis devis = devisService.findByIdDemande(id_demande);
        devisMaterielService.deleteById(devis.getId_devis());
        devisService.delete(devis);

        this.demandeService.deleteById(id_demande);
        return "redirect:/listeDemande";
    }

    @GetMapping("/update/{id_demande}/{id_demande_statut}")
    public String showUpdate(@PathVariable("id_demande") Integer id_demande,
            @PathVariable("id_demande_statut") Integer id_demande_statut, Model model) {

        Demande demande = this.demandeService.findById(id_demande);
        DemandeStatut ds = this.demandeStatutService.findDemandeStatutByDemande(id_demande, id_demande_statut);

        model.addAttribute("demande", demande);
        model.addAttribute("demande_statut", ds);

        model.addAttribute("clients", this.clientService.findAll());
        model.addAttribute("regions", this.regionService.findAll());
        model.addAttribute("districts", this.districtService.findAll());
        model.addAttribute("communes", this.communeService.findAll());
        model.addAttribute("statuts", this.statutService.findAll());

        return "demande-update";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("communeId") Integer communeId,
            @RequestParam("lieu") String lieu,
            @RequestParam("date") String dateStr,
            @RequestParam("id_demande") Integer demandeId,
            @RequestParam("id_statut") Integer statutId,
            @RequestParam("id_demande_statut") Integer demandeStatutId) {

        Client client = clientService.findById(clientId);
        Commune commune = communeService.findById(communeId);

        // Parser la date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);

        Demande demande = new Demande();
        demande.setId_demande(demandeId);
        demande.setClient(client);
        demande.setCommune(commune);
        demande.setLieu(lieu);

        Statut statut = statutService.findById(statutId);

        DemandeStatut st = new DemandeStatut();
        st.setId_demande_statut(demandeStatutId);
        st.setDate(Timestamp.valueOf(localDateTime));
        st.setDemande(demande);
        st.setStatut(statut);

        demandeService.update(demande);
        demandeStatutService.update(st);

        return "redirect:/listeDemande";
    }

    @GetMapping("/demandeJSON/{id_demande}")
    @ResponseBody
    public String toJSON(@PathVariable("id_demande") Integer id_demande) {
        Demande demande = demandeService.findById(id_demande);
        DemandeStatut demandeStatut = demandeStatutService.findByDemandeStatutByStatut(id_demande, 7);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        ObjectNode demandeNode = mapper.createObjectNode();
        demandeNode.put("id_demande", demande.getId_demande());
        demandeNode.put("lieu", demande.getLieu());

        ObjectNode clientNode = mapper.createObjectNode();
        if (demande.getClient() != null) {
            clientNode.put("nom_client", demande.getClient().getNom_client());
            clientNode.put("adresse", demande.getClient().getAdresse() == null ? "" : demande.getClient().getAdresse());
        } else {
            clientNode.put("nom_client", "");
            clientNode.put("adresse", "");
        }
        demandeNode.set("client", clientNode);
        response.set("demande", demandeNode);

        // ajout de l'attribut `devis_rejete` (1 si existe, sinon 0)
        response.put("devis_rejete", demandeStatut != null ? 1 : 0);

        String json = null;

        try {
            json = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping("/demandeStatutForm")
    public String update_demande_form(Model model) {
        List<Statut> liste = statutService.findAll();
        model.addAttribute("status", liste);
        return "demande-updateForm";
    }

    @GetMapping("/demandeUpdateJSON/{id_demande}")
    @ResponseBody
    public String toJSON_update(@PathVariable("id_demande") Integer id_demande) {
        Demande demande = demandeService.findById(id_demande);
        DemandeStatut demandeStatut = demandeStatutService.findByCurrentStatut(id_demande);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();

        // Ajouter les informations de la demande
        response.put("id_demande", demande.getId_demande());
        response.put("lieu", demande.getLieu());

        // Ajouter les informations du statut
        if (demandeStatut != null) {
            response.put("date", demandeStatut.getDate().toString());
            response.put("statut", demandeStatut.getStatut().getLibelle());
        } else {
            response.put("date", "");
            response.put("statut", "");
        }

        // Ajouter les informations du client
        if (demande.getClient() != null) {
            response.put("nom_client", demande.getClient().getNom_client());
            response.put("adresse", demande.getClient().getAdresse() == null ? "" : demande.getClient().getAdresse());
        } else {
            response.put("nom_client", "");
            response.put("adresse", "");
        }

        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    @PostMapping("/update-demandeForm")
    public String update_demande(@RequestParam("id_demande") Integer id_demande,
            @RequestParam("statutId") Integer id_statut, @RequestParam("observation") String observation,
            @RequestParam("date_creation") String dateStr) {

        // parser la date
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);

        Statut statut = statutService.findById(id_statut);
        Demande demande = demandeService.findById(id_demande);

        DemandeStatut ds = new DemandeStatut(Timestamp.valueOf(localDateTime), demande, statut, observation);
        demandeStatutService.save(ds);
        
        return "redirect:/listeDemande";
    }
}