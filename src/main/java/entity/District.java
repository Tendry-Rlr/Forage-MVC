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
@Table(name = "district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_district")
    private int id_district;

    @Column(name = "libelle", nullable = false, length = 20)
    private String libelle;

    @ManyToOne
    @JoinColumn(name="id_region", nullable=false)
    @JsonIgnore
    private Region region;

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    List<Commune> communes;
    
    public int getId_district() {
        return id_district;
    }

    public void setId_district(int id_district) {
        this.id_district = id_district;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }
}

