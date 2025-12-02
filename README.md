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
