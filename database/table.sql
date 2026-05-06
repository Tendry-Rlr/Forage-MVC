create database forage;
use forage;

create table clients(
    id_client int PRIMARY KEY AUTO_INCREMENT,
    nom_client VARCHAR(20),
    contact double,
    adresse VARCHAR(50)
);

create table users(
    id_user int PRIMARY KEY AUTO_INCREMENT,
    nom_user VARCHAR(20),
    password_user VARCHAR(20)
);

create table region(
    id_region int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);
create table district(
    id_district int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table commune(
    id_commune int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table demande(
    id_demande int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20),
    id_commune int,
    FOREIGN KEY id_commune REFERENCES commune(id_commune)
);

create table statut(
    id_statut int PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(20)
);

create table demande_statut(
    id_demande_statut int PRIMARY KEY AUTO_INCREMENT,
    id_demande int,
    id_statut int
);
