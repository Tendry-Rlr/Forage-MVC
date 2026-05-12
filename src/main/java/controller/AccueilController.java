package controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.District;
import entity.Commune;
import entity.Demande;
import entity.DemandeStatut;
import service.*;

@Controller
public class AccueilController {
    private final ClientService clientService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final CommuneService communeService;

    public AccueilController(ClientService clientService, RegionService regionService, DistrictService districtService,
            CommuneService communeService) {
        this.clientService = clientService;
        this.regionService = regionService;
        this.districtService = districtService;
        this.communeService = communeService;
    }
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clients", this.clientService.findAll());
        model.addAttribute("regions", this.regionService.findAll());
        model.addAttribute("districts", this.districtService.findAll());
        model.addAttribute("communes", this.communeService.findAll());
        
        model.addAttribute("demande", new Demande());
        model.addAttribute("demandeStatut", new DemandeStatut());

        return "index";
    }

    @GetMapping("/districts/{id_region}")
    @ResponseBody
    public List<District> getDistrictsByRegion(@PathVariable("id_region") Integer regionId) {
        return districtService.findByRegion(regionId);
    }

    @GetMapping("/communes/{id_district}")
    @ResponseBody
    public List<Commune> getCommunesByDistrict(@PathVariable("id_district") Integer districtId) {
        return communeService.findByDistrict(districtId);
    }

}

