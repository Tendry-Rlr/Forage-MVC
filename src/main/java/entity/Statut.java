package entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "statut")
public class Statut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut")
    private int id_statut;

    @Column(name = "libelle", nullable = false, length = 20)
    private String libelle;

    @OneToMany(mappedBy = "statut")
    List<DemandeStatut> demandeStatuts;

    public int getId_statut() {
        return id_statut;
    }

    public void setId_statut(int id_statut) {
        this.id_statut = id_statut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<DemandeStatut> getDemandeStatuts() {
        return demandeStatuts;
    }

    public void setDemandeStatuts(List<DemandeStatut> demandeStatuts) {
        this.demandeStatuts = demandeStatuts;
    }

}
