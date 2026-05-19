create database forage;

use forage;

create table clients (
    id_client int PRIMARY KEY AUTO_INCREMENT,
    nom_client VARCHAR(20),
    contact double,
    adresse VARCHAR(50)
);

create table users (
    id_user int PRIMARY KEY AUTO_INCREMENT,
    nom_user VARCHAR(20),
    password_user VARCHAR(20)
);

create table region (
    id_region int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table district (
    id_district int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table commune (
    id_commune int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table demande (
    id_demande int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20),
    id_commune int,
    FOREIGN KEY id_commune REFERENCES commune (id_commune)
);

create table statut (
    id_statut int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50)
);

create table demande_statut (
    id_demande_statut int PRIMARY KEY AUTO_INCREMENT,
    id_demande int,
    id_statut int
);

select d.*, ds.id_demande_statut, s.libelle, c.nom_client
from
    demande d
    join client c on c.id_client = d.id_client
    join demande_statut ds on d.id_demande = ds.id_demande
    join statut s on s.id_statut = ds.id_statut;

CREATE table devis (
    id_devis int AUTO_INCREMENT PRIMARY key,
    id_demande int REFERENCES demande(id_demande),
    date_creation DATETIME
);

create table devis_materiel (
    id_devis_materiel int AUTO_INCREMENT PRIMARY key,
    id_devis int,
    libelle VARCHAR(20),
    prix_unitaire double,
    quantite int
);

create table type_devis (
    id_type_devis int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table parametre(
    id
    id_statut_1
    id_statut_2
    duree_travaille (min)
    alerte
);