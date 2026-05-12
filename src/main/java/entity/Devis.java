package entity;


import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "devis")
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devis")
    private int id_devis;

    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @Column(name = "date_creation", nullable = false)
    private Timestamp date_creation;

    @OneToMany(mappedBy = "devis")
    List<DevisMateriel> devis_materiesl;

    public Devis(Demande demande, Timestamp date_creation) {
        this.demande = demande;
        this.date_creation = date_creation;
    }

    public Devis() {}

    public int getId_devis() {
        return id_devis;
    }

    public void setId_devis(int id_devis) {
        this.id_devis = id_devis;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public List<DevisMateriel> getDevis_materiesl() {
        return devis_materiesl;
    }

    public void setDevis_materiesl(List<DevisMateriel> devis_materiesl) {
        this.devis_materiesl = devis_materiesl;
    }



}
