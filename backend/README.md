# To-do App

Une application web de gestion de projets et de tâches, permettant aux utilisateurs de suivre l'évolution de leurs projets et de gérer les tâches associées. Ce projet utilise **Spring Boot** pour le back-end et **Angular** pour le front-end.

## Technologies utilisées

- **Back-End** : Spring Boot (Java)
- **Front-End** : Angular (TypeScript)
- **Base de données** : H2 (en développement) / MySQL (en production)
- **Gestion des dépendances** : Maven (pour le back-end) et npm (pour le front-end)

## Structure du projet

Le projet est organisé en deux parties principales :

- **`backend/`** : Contient le code du back-end Spring Boot
- **`frontend/`** : Contient le code du front-end Angular

### `backend/`
Le back-end est une API RESTful développée avec **Spring Boot**. Il comprend les entités `Project` et `Task` ainsi que des contrôleurs pour gérer les opérations CRUD sur ces entités.

### `frontend/`
Le front-end est une application **Angular** qui interagit avec l'API pour afficher et gérer les projets et les tâches. Elle permet de filtrer les tâches, de les ajouter, les modifier et les supprimer.

## Prérequis

Avant de pouvoir exécuter le projet, vous devez installer les outils suivants :

- **Java 11** ou supérieur (pour Spring Boot)
- **Node.js** et **npm** (pour Angular)
- **Maven** (pour gérer les dépendances du back-end)
- **MySQL** ou **H2 Database** pour la base de données

## Installation

### 1. Installation du Back-End

1. Clonez ce dépôt GitHub sur votre machine locale :
   ```bash
   git clone https://github.com/oussamabendaoud/To-do-app.git
   
   cd spring-boot-angular-test
   
cd backend
mvn spring-boot:run -Dserver.port=8080

cd frontend
npm install
ng serve --port 8081

