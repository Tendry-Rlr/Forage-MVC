package entity;

import java.time.LocalDateTime;
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
    private LocalDateTime date_creation;

    @OneToMany(mappedBy = "devis")
    List<DevisMateriel> devis_materiels;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private TypeDevis typeDevis;

    public Devis(Demande demande, LocalDateTime date_creation, TypeDevis type) {
        this.demande = demande;
        this.date_creation = date_creation;
        this.typeDevis = type;
    }

    public Devis() {
    }

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

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public TypeDevis getTypeDevis() {
        return typeDevis;
    }

    public void setTypeDevis(TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }

    public void setDate_creation(LocalDateTime date_creation) {
        this.date_creation = date_creation;
    }

    public List<DevisMateriel> getDevis_materiels() {
        return devis_materiels;
    }

    public void setDevis_materiels(List<DevisMateriel> devis_materiesl) {
        this.devis_materiels = devis_materiesl;
    }

}
