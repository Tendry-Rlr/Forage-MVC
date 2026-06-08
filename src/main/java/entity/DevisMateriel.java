package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "devis_materiel")
public class DevisMateriel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devis_materiel")
    private int id_devis_materiel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_devis", nullable = false)
    private Devis devis;

    @Column(name = "libelle", nullable = false, length = 50)
    private String libelle;

    @Column(name = "prix_unitaire", nullable = false)
    private double prix_unitaire;

    @Column(name = "quantite", nullable = false)
    private double quantite;

    public DevisMateriel(Devis devis, String libelle, double prix_unitaire, double quantite) {
        this.devis = devis;
        this.libelle = libelle;
        this.prix_unitaire = prix_unitaire;
        this.quantite = quantite;
    }

    public DevisMateriel() {
    }

    public int getId_devis_materiel() {
        return id_devis_materiel;
    }

    public void setId_devis_materiel(int id_devis_materiel) {
        this.id_devis_materiel = id_devis_materiel;
    }

    public Devis getDevis() {
        return devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

}
