devis : id, type, date, id_demande

verification type : etude - forage

insertion : (ok)
ajout dans devis
ajout dans devisMateriel
mise a jour du statut de la demande

modification : (ok)
backend
mise a jour de devis
ajout dans DemandeStatut
supprimer tous les lignes de devis et ajouter les modif : devisMateriel

DETECTION ERREURS :
catalina.out / catalina-date
localhost-date.log

Erreur :
javascript
insertion demande : commune-district

A faire :  
 Supprimer demande -> supprimer devis (ok)
DemandeStatus : observation (ok)
duree_travaille

Integration DT : 8h a 16h --> decalage HGMT
creer une fonction dans DemandeStatutService et le mettre dans save pour calculer DT automatiquement a chaque insertion
datediff : retourne nb_jours
dateDeb et dateFin : recuperer la duree si inclut dans 8 et 16 sinon 0
DT : dureeDeb + dateDiff(16-8) + dureeFin

API (php) : recuperer les alertes pour une demande
