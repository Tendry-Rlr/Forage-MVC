insert into client(nom_client, contact, adresse)
VALUES
('Rabe', 911, 'Amboanjobe'),
('Rado', 911, 'Analakely');

-- Données pour la table region
INSERT INTO region (libelle) VALUES
('Analamanga'),
('Vakinankaratra'),
('Atsinanana');

-- Données pour la table district
INSERT INTO district (libelle, id_region) VALUES
('Antananarivo', 1),
('Antsirabe I', 2),
('Toamasina I', 3);

-- Données pour la table commune
INSERT INTO commune (libelle, id_district) VALUES
('Ankadifotsy', 1),
('Ambohimanambola', 1),
('Antsirabe', 2),
('Toamasina', 3);

insert into statut(libelle) VALUES
('Creee'),
('Devis accepte'),
('Devis decline'),
('Debut Forage'),
('Fin Forage');


