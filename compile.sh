#!/bin/bash

echo "Compilation des classes avec Maven..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Erreur lors de la compilation"
    exit 1
fi

echo "Compilation terminée avec succès!"
echo "Les classes compilées se trouvent dans: target/classes/"
