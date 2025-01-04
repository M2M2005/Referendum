# Projet Référendum

## Contexte
Ce projet a été réalisé dans le cadre de la **deuxième année de BUT Informatique**. L'objectif était de simuler un projet professionnel où un professeur jouait le rôle de client. Nous avons appliqué les principes de la **méthodologie Agile** pour répondre à ses besoins et développer une solution fonctionnelle.

Le projet consiste à créer un système de **référendums sécurisés**, permettant à une entreprise de gérer des votes de manière cryptée et confidentielle.

## Fonctionnalités
### Utilisateurs principaux
1. **Administrateur** :
    - Création de nouveaux référendums.
    - Gestion des rôles et des accès (employés et scrutateurs).
2. **Employés** :
    - Participation aux votes pour les référendums actifs.
    - Envoi des votes cryptés au serveur.
3. **Scrutateur** :
    - Utilisation de la clé privée El Gamal pour décrypter les votes.
    - Publication des résultats du référendum.

### Sécurité
Le système utilise le **cryptosystème El Gamal** pour garantir la confidentialité et la sécurité des votes :
- Les votes sont chiffrés par chaque employé avant d'être envoyés au serveur.
- Seul le scrutateur possède la **clé privée** nécessaire pour déchiffrer les votes et afficher les résultats.

### Communication client-serveur
- **Serveur** : Gère les référendums, stocke les votes chiffrés et assure la gestion des utilisateurs.
- **Clients** : Interfaces pour les administrateurs, employés et scrutateurs permettant d'intéragir avec le système.

## Technologies utilisées
- **Langage principal** : Java
- **Méthodologie** : Agile (Scrum)

## Installation et lancement
### Prérequis
- Java 11 ou supérieur

### Installation
1. Clonez le dépôt :
   ```bash
   git clone https://github.com/killianrms/Referendum.git
   ```
2. Importez le projet dans votre IDE Java préféré (par exemple IntelliJ IDEA ou Eclipse).
3. Compilez le projet et configurez les paramètres nécessaires (par exemple, clés El Gamal).

### Lancement
1. Lancez le serveur.
2. Exécutez les clients pour les différents utilisateurs (admin, employé, scrutateur).

## Expérience personnelle
Ce projet m'a permis de :
- **Collaborer en équipe** : Une mise en pratique concrète des concepts de la méthodologie Agile.
- **Explorer la cryptographie** : Implémentation et utilisation de l'algorithme El Gamal.
- **Renforcer mes compétences en développement client-serveur** : Gestion des communications et des utilisateurs dans un environnement distribué.

## Ce que j'ai appris
- Gestion de projet Agile
- Implémentation d'algorithmes cryptographiques
- Conception et développement d'applications client-serveur
- Communication efficace avec un client pour comprendre et répondre à ses besoins

## Contributeurs
- **Killian R.** - Product Owner
- **Mael N.** - Scrum Master
- **Cyprien B.** - Développeur
- **Raphael R.** - Développeur
