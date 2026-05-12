package entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "commune")
public class Commune {
    public Commune() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commune")
    private int id_commune;

    @Column(name = "libelle", nullable = false, length = 20)
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "id_district", nullable = false)
    @JsonIgnore
    private District district;

    @OneToMany(mappedBy = "commune")
    private List<Demande> demandes;

    public int getId_commune() {
        return id_commune;
    }

    public void setId_commune(int id_commune) {
        this.id_commune = id_commune;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
