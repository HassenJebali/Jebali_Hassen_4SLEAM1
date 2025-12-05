# 🚀 Projet DevOps 

## 📄 Description du projet
Dans ce projet, nous allons appliquer les bonnes pratiques DevOps afin de garantir un processus de développement moderne, efficace et durable. 
Cela inclut l’intégration continue (CI), le déploiement continu (CD), l’utilisation d’outils d’automatisation pour les tests et les pipelines, la gestion optimisée du code source via Git, ainsi qu’un suivi régulier de la performance et de la qualité du produit. 
---

## 🎯 Objectifs
- Mettre en place une architecture DevOps complète  
- Automatiser les tests et les déploiements  
- Améliorer la qualité du code grâce aux outils CI/CD  
- Suivre la performance et garantir la fiabilité des services  
- Accélérer le cycle de développement et de livraison  

---

## 🛠️ Technologies et outils utilisés
- **Git / GitHub**
- **Docker**
- **GitHub Actions** (CI/CD)
- **Tests automatisés**  
- **Monitoring & Logging** 
- **Outils de qualité** (SonarQube)

---
# Test Unitaires 
## 🏗️ **Annotations JUnit 5**

| Annotation              | Objectif                                      | Exemple d'utilisation                                |
|-------------------------|-----------------------------------------------|-----------------------------------------------------|
| **`@Test`**              | Déclare une méthode comme test unitaire       | `@Test void testGetAllStudents_EmptyList()`         |
| **`@ParameterizedTest`** | Exécute un test avec différentes valeurs      | `@ParameterizedTest @ValueSource(ints = {1, 2, 5, 10})` |
| **`@ValueSource`**       | Fournit un tableau de valeurs pour tests paramétrés | `@ValueSource(strings = {"Dupont", "Martin"})`    |
| **`@ExtendWith`**        | Intègre des extensions externes à JUnit      | `@ExtendWith(MockitoExtension.class)`              |

---

## 🎭 **Annotations Mockito**

| Annotation           | Objectif                              | Exemple d'utilisation                                |
|----------------------|---------------------------------------|-----------------------------------------------------|
| **`@Mock`**           | Crée un objet simulé pour une dépendance | `@Mock private StudentRepository studentRepository` |
| **`@InjectMocks`**    | Injecte automatiquement les mocks     | `@InjectMocks private StudentService studentService` |

---

## ✅ **Assertions**

| Assertion                             | Objectif                             | Cas d'utilisation                          |
|---------------------------------------|--------------------------------------|-------------------------------------------|
| **`assertEquals(expected, actual)`**  | Vérifie l'égalité entre deux valeurs | Taille de liste, valeurs d'attributs      |
| **`assertNotNull(object)`**           | Vérifie qu'un objet n'est pas null   | Après récupération d'un étudiant         |
| **`assertNull(object)`**              | Vérifie qu'un objet est null         | Quand un étudiant n'existe pas            |
| **`assertTrue(condition)`**           | Vérifie qu'une condition est vraie   | Vérifier qu'une liste est vide           |
| **`assertFalse(condition)`**          | Vérifie qu'une condition est fausse  | (Non utilisé dans ce code)               |

---

## 🎪 **Configuration des Mocks**

| Méthode Mockito                          | Objectif                                  | Exemple                                      |
|------------------------------------------|-------------------------------------------|---------------------------------------------|
| **`when(mock.method()).thenReturn(value)`** | Définit la valeur de retour d'un mock      | `when(repo.findAll()).thenReturn(students)` |
| **`doNothing().when(mock).method()`**    | Configure un mock pour une méthode void   | `doNothing().when(repo).deleteById(id)`     |

| Méthode Mockito                          | Objectif                                  | Exemple                                      |
|------------------------------------------|-------------------------------------------|---------------------------------------------|
| **`verify(mock).method()`**              | Vérifie qu'une méthode a été appelée     | `verify(repository).findAll()`              |
| **`verify(mock, times(n)).method()`**    | Vérifie le nombre d'appels exacts        | `verify(repository, times(1)).findAll()`    |
| **`verify(mock, never()).method()`**     | Vérifie qu'une méthode n'a pas été appelée | (Non utilisé dans ce code)                  |

---

---

## 🎯 **Pattern de Test AAA**

Le pattern **Arrange-Act-Assert** structure clairement les tests.

### Exemple
```java
@Test
void testGetAllStudents_EmptyList() {
    // Arrange - Préparation
    when(studentRepository.findAll()).thenReturn(new ArrayList<>());
    
    // Act - Exécution
    List<Student> result = studentService.getAllStudents();
    
    // Assert - Vérification
    assertTrue(result.isEmpty());
    assertEquals(0, result.size());
}
```

# 🧩 Pipeline Jenkins CI/CD
## ⚙️ Initialisation des environnements Maven et Docker

Le pipeline Jenkins définit plusieurs variables d’environnement pour s’assurer que **Maven** et **Docker** fonctionnent correctement.

```
M2_HOME = "/usr/share/maven"
PATH = "${env.M2_HOME}/bin:${env.PATH}"

DOCKER_USERNAME = "jebalih"
DOCKER_IMAGE = "${env.DOCKER_USERNAME}/student-management"
DOCKER_TAG = "build-${env.BUILD_NUMBER}"
```
### Maven
- M2_HOME : répertoire où Maven est installé (/usr/share/maven)
- PATH : inclut ${M2_HOME}/bin pour rendre les commandes Maven (mvn) disponibles dans le pipeline
- ✅ Résultat : Jenkins peut exécuter mvn clean package, mvn test, etc.

### Docker
- DOCKER_USERNAME : nom d’utilisateur Docker Hub pour authentification et push
- DOCKER_IMAGE : nom complet de l’image Docker (<username>/student-management)
- DOCKER_TAG : tag unique par build (build-<BUILD_NUMBER>)

- ✅ Résultat : Jenkins peut créer et pousser des images Docker versionnées automatiquement

---

## 🧱 Build & Tests Maven
Le projet est construit et testé automatiquement grâce à **Maven**, orchestré par **Jenkins**.

---

### 🔍 Tests automatisés
Le pipeline exécute les tests unitaires avec la commande :  

```bash
mvn test -Dspring.profiles.active=test
```
- Utilise le profil test
- Exécute les tests JUnit & Mockito
- Garantit que le code est fonctionnel avant de continuer
- Empêche la création d’une image Docker si les tests échouent
- 👉 Cette étape sécurise la qualité du code avant toute livraison

### 🔨 Compilation et génération du `.jar`

La commande suivante est utilisée pour compiler le projet et générer le package :

```bash
mvn clean package -DskipTests
```
- Nettoie l’ancien build
- Compile le projet
- Génère le fichier .jar dans le répertoire target/
- ℹ️ Ce .jar sera intégré dans l’image Docker.

---

## 🐳 Construction de l'image Docker

Après le build Maven, Jenkins utilise le **Dockerfile** du projet pour créer l’image.

---

### 🔧 Construction de l’image

Commande utilisée :  

```bash
docker build -t <username>/student-management:<tag> .
```
- Copie le .jar dans l'image
- Prépare l’environnement d’exécution
- Crée une version portable de l’application

### 📝 Dockerfile
```bash
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/student-management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
```
- **Base image**  Java 17
- **WORKDIR** répertoire de travail /app
- **COPY** copie le JAR généré par Maven
- **EXPOSE** port de l’application
- **ENTRYPOINT** commande pour lancer l’application

### 🏷 Tagging automatique
- Deux tags sont générés :
- **`build-<BUILD_NUMBER>`** → version unique
- **`latest`** → version la plus récente
 
 ### 🚀 Publication sur Docker Hub
 
```bash
docker push <image>:<tag>
docker push <image>:latest
```
- Authentification via Jenkins Credentials
- Chaque commit génère une version déployable

## Stages du Jenkinsfile :

- 1. **Checkout** : Récupération du code depuis GitHub et Déclenchement automatique à chaque commit .
- 2. **Test** : Exécution des tests unitaires et Arrêt du pipeline si erreur.
- 3. **Package** : Génération du **`.jar`** via**` mvn clean package**`**.
- 4. **Build Docker Image **: Construction de l’image Docker et Création des tags **`(build-<BUILD_NUMBER>*`**, **`latest`**.
- 5. **Push to Docker Hub** : Authentification sécurisée et Push automatique.
- 6. **Post-actions** : Logout Docker, Archivage du **`.jar`**  et Logs détaillés





