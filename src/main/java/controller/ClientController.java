package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import service.ClientService;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/listeClient")
    public String listeClient(Model model) {
        model.addAttribute("clients", this.clientService.findAll());
        return "liste-client";
    }
}
