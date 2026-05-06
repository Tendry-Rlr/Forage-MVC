#!/bin/bash

echo "Nettoyage et installation des dépendances..."
mvn clean install package -DskipTests

if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation avec Maven"
    exit 1
fi

FILE="target/hello.war"
TOMCAT_WEBAPPS="/home/tendry/ITU/tomcat/webapps"

if [ ! -f "$FILE" ]; then
    echo "Erreur: Le fichier WAR n'a pas été généré: $FILE"
    exit 1
fi

echo "Copie du WAR vers Tomcat..."
cp "$FILE" "$TOMCAT_WEBAPPS/"

echo "Déploiement terminé avec succès!"