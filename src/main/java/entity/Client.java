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
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private int id_client;

    @Column(name = "nom_client", nullable = false, length = 20)
    private String nom_client;

    @Column(name = "contact", nullable = false)
    private double contact;

    @Column(name = "adresse", nullable = false, length = 20)
    private String adresse;

    @OneToMany(mappedBy = "client")
    List<Demande> demandes;


    public int getId_client() {
        return id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

}
