package model;

import entity.DemandeStatut;

public class DemandeAlerte {
    private DemandeStatut demandeStatut;
    private String alerte;

    public DemandeAlerte(DemandeStatut demandeStatut, String alerte) {
        this.demandeStatut = demandeStatut;
        this.alerte = alerte;
    }

    public DemandeAlerte() {

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
