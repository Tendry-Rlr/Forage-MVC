package entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_statut")
public class DemandeStatut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_statut")
    private int id_demande_statut;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "id_demande", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_statut", nullable = false)
    private Statut statut;

    @Column(name = "observation", length = 100)
    private String observation;

    @Column(name = "duree_travaille")
    private double duree_travaille;

    public DemandeStatut(Timestamp date, Demande demande, Statut statut) {
        this.date = date;
        this.demande = demande;
        this.statut = statut;
    }

    public DemandeStatut(Timestamp date, Demande demande, Statut statut, String obs) {
        this.date = date;
        this.demande = demande;
        this.observation = obs;
        this.statut = statut;
    }

    public DemandeStatut() {

    }

    public double getDuree_travaille() {
        return duree_travaille;
    }

    public void setDuree_travaille(double duree_travaille) {
        this.duree_travaille = duree_travaille;
    }

    public int getId_demande_statut() {
        return id_demande_statut;
    }

    public void setId_demande_statut(int id_demande_statut) {
        this.id_demande_statut = id_demande_statut;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
