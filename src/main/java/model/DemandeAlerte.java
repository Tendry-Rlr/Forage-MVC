package model;

import entity.DemandeStatut;
import entity.Statut;

public class DemandeAlerte {
    private DemandeStatut demandeStatut;
    private String alerte;
    private Statut statut;

    public DemandeAlerte(DemandeStatut demandeStatut, String alerte, Statut statut) {
        this.demandeStatut = demandeStatut;
        this.alerte = alerte;
        this.statut = statut;
    }

    public DemandeAlerte(DemandeStatut demandeStatut, String alerte) {
        this.demandeStatut = demandeStatut;
        this.alerte = alerte;
    }

    public DemandeAlerte() {

    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public DemandeStatut getDemandeStatut() {
        return demandeStatut;
    }

    public void setDemandeStatut(DemandeStatut demandeStatut) {
        this.demandeStatut = demandeStatut;
    }

    public String getAlerte() {
        return alerte;
    }

    public void setAlerte(String alerte) {
        this.alerte = alerte;
    }

}
