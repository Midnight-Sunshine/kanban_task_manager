# Projet Kanban

Ce projet est une application web et mobile de gestion de projet Kanban. L'application permet aux utilisateurs de créer, visualiser et gérer des tableaux Kanban ainsi que d'attribuer des tâches à des sprints.
 développée avec Angular pour le frontend et Spring Boot pour le backend et pour mobile Java et Android Studio.

## Fonctionnalités

- Création, édition et suppression de tableaux Kanban
- Ajout, édition et suppression de tâches dans les tableaux Kanban
- Attribution de tâches à des utilisateurs
- Affectation de tâches à des sprints
- Visualisation des sprints et de leurs tâches associées
- Authentification des utilisateurs avec différents rôles (administrateur, utilisateur)

## Installation

1. **Cloner le dépôt :**
    ```
    git clone <url-du-dépôt>
    ```

2. **Backend :**
    - Importez le projet Spring Boot dans votre IDE
    - Configurez la base de données et les détails d'authentification (voir `application.properties`)
    - Lancez l'application Spring Boot

3. **Frontend :**
    - Accédez au répertoire `frontend` dans le projet cloné
    - Installez les dépendances :
        ```
        npm install
        ```
    - Démarrez l'application :
        ```
        npm start
        ```

## Configuration

### Backend

- **Base de données :** Le projet utilise une base de données MySQL par défaut. Vous pouvez modifier les paramètres de base de données dans le fichier `application.properties`.

### Frontend

- **Environnement :** Assurez-vous de configurer correctement l'URL du backend dans le fichier `environment.ts` pour que le frontend puisse communiquer avec le backend.

## Technologies utilisées

- Angular
- Spring Boot
- MySQL
- HTML, CSS, TypeScript

## Déploiement avec Docker

1. **pull backend**
    ```
    docker pull mouadcherrat/kanban:v2
    ```

2. **pull frontend**
    ```
    docker pull mouadcherrat/kanban:v1
    ```
## Contributeurs

- SOUIDI Khalil
- CHERRAT Mouad
- ELALJI Imane
- KATIB Zakaria
- BENSALEK Abderrahmane
