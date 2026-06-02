package entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parametre")
public class Parametre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private int id_parametre;

    @ManyToOne
    @JoinColumn(name = "id_statut1", nullable = false)
    private Statut statut1;

    @ManyToOne
    @JoinColumn(name = "id_statut2", nullable = false)
    private Statut statut2;

    @Column(name = "duree_travaille")
    private double duree_travaille;

    @Column(name = "alerte", length = 20)
    private String alerte;

    public int getId_parametre() {
        return id_parametre;
    }


    public void setId_parametre(int id_parametre) {
        this.id_parametre = id_parametre;
    }


    public Statut getStatut1() {
        return statut1;
    }


    public void setStatut1(Statut statut1) {
        this.statut1 = statut1;
    }


    public Statut getStatut2() {
        return statut2;
    }


    public void setStatut2(Statut statut2) {
        this.statut2 = statut2;
    }


    public double getDuree_travaille() {
        return duree_travaille;
    }


    public void setDuree_travaille(double duree_travaille) {
        this.duree_travaille = duree_travaille;
    }


    public String getAlerte() {
        return alerte;
    }


    public void setAlerte(String alerte) {
        this.alerte = alerte;
    }
}
