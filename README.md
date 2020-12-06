# Projet DAAR3 : ElasticSearch

Binome : Mathieu George et Anis Benalla

Ce projet est un exemple d'utilisation d'ElasticSearch pour indexer des données avec Spring Boot (Java 8). On utilise Vue.js pour le client.

Utilisation : 
- Avoir ElasticSearch et le lancer
- Avoir npm et lancer la commande `npm install` puis `npm run start` dans `client/daar_es`
- Avoir Maven et lancer la commande `mvnw spring-boot:run` dans le dossier principal (là où est le `pom.xml`)

L'application se lance sur le port 8081.

L'application permet d'envoyer des CVs au format \*.pdf, \*.doc et \*.docx, ils sont indexés toutes les 10 secondes.
L'application permet également de chercher des termes présents dans les CV, des compétences par exemples.
