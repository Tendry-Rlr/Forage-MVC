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
@Table(name = "type_devis")
public class TypeDevis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_devis")
    private int id_type;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @OneToMany(mappedBy = "typeDevis")
    private List<Devis> devis;

    public TypeDevis() {
    }

    public TypeDevis(String libelle) {
        this.libelle = libelle;
    }

    public int getId_type() {
        return id_type;
    }

    public List<Devis> getDevis() {
        return devis;
    }

    public void setDevis(List<Devis> devis) {
        this.devis = devis;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
