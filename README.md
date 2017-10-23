# GameRanks - dokumentáció

Készítette: Kovács Dávid

## 1. Követelményanalízis

### 1.1 A program célja

A GameRanks egy játékosoknak szánt webes alkalmazás, ahol lehetőség nyílik arra, hogy megosszuk egymással véleményünket, kedvenc játékainkkal kapcsolatban. Legyen szó PC -s, vagy konzolos játékról, itt lehetőséged nyílik, hogy te magad alkoss véleményt, kiemeld mi jó, vagy rossz a játékban és hogy megadd azt a pontszámot, amennyit szerinted a játék ér. A pontszámok összesítésre kerülnek, így mindeki nyomonkövetheti, hogy melyek is azon játékok, amik leginkább elnyerték a játékosok tetszését.

#### 1.1.1 Funkcionális követelmények

- Regisztráció
- Bejelentkezés
- Az oldalon található játékok értékeléseinek böngészése
- Csak bejelentkezett felhasználóknak elérhető funkciók
  - Profil szerkesztése
    - Avatar módosítása
    - Saját értékelések megtekintése, módosítása, törlése
    - Alap adatok megtekintése és módosítása
  - Értékelések írása
  - Más értékelések véleményezése

#### 1.1.2 Nem funkcionális követelmények

- A játékok gyors közvetlen elérése
  - A játékokat úgy kell megjeleníteni, hogy a felhasználó minnél gyorsabban elérhesse azt, amelyiket szeretné
    - Kategóriákba osztás
    - Vizuális elkülönítés
    - Átlátható listázás
- Lényegretörő értékelési módszer
  - Az értékelések legfontosabb részeinek (Pro/Kontra/Pontszám) kiemelése
- A felület könnyű áttekinthetősége
  - Letisztult, rendezett, lényegretörő felület, a minnél hatékonyabb és gyorsabb használat érdekében
- Felhasználóbarát működés
  - Hibák, gondok, információk jelzése a felhasználónak
    - Rosszul kitöltött mezők jelzése
    - Módosítások eredményének jelzése
  - Megerősítés kérése, módosítások, vagy törlések esetén
- Bővíthetőség, karbantarthatóság
  - A programnak úgy kell felépülnie, hogy azt a későbbiekben is könnyen lehessen bővíteni és az esetleges hibákat könnyen lehessen javítani



### 1.2 Szakterületi fogalomjegyzék

#### 1.2.1 Játék kategóriák
- **Action**: akció játékok
- **Adventure**: kaland játékok
- **Casual**: alkalmi játékok
- **Indie**: indie játékok
- **MMO**: online többjátékos játékok
- **Racing**: versenyzős játékok
- **RPG**: szerepjátékok
- **Simulation**: szimulátor játékok
- **Sports**: sport játékok
- **Strategy**: stratégiai játékok



### 1.3 Használatieset-modell

#### 1.3.1 Szerepkörök
**Látogató**: Azon funkciókat érheti el, melyek nem igényelnek bejelentkezést
  - Regisztráció
  - Bejelentkezés
  - Játékok listázása
  - Kiválasztott játék adatlapjának és mások értékeléseinek megtekintése

**Tag**: Regisztrált felhasználó, aki a bejelentkezést igénylő funkciókhoz is hozzáfér
  - Profil megtekintése és szerkesztése
  - Saját értékelések szerkesztése
  - Játékok értékelése
  - Mások értékeléseinek véleményezése
  
#### 1.3.2 Használati eset diagramok
**Látogató**

![](docs/images/Szerepkor-latogato.png)

**Tag**

![](docs/images/Szerepkor-tag.png)

#### 1.3.3 Példa folyamat jellemzése
**Új értékelés írása**
  1. A felhasználó megkeresi az értékelni kívánt játékot, a játékok listájából
  2. Ha még nem tette meg, akkor belép a felhasználó
  3. A játékot kiválasztva, ha még nem írt értékelést, akkor lehetősége nyílik azt megtenni
  4. Az értékelés megírása után, azt véglegesíti, így az meg is jelenik a játék és a saját értékelései közt
  
  ![](docs/images/Pelda-folyamat.png)
  
## 2. Tervezés


## 3. Implementáció

### 3.1 Fejlesztői környezet

#### 3.1.1 Szerver oldal
A szerver megvalósításához a JAVA Spring keretrendszerét használtam, az alábbi technológiákkal kiegészítve:
  - H2
  - JPA
  - Lombok
  - Thymeleaf
  - DevTools

A szervert futtatás után, a localhost:8080 címen érhetjük el.

### 3.2 Könyvtárstruktúra
#### 3.2.1 Szerver oldal
<details>
<summary>Click to expand</summary>
<pre>
GameRanks
│   .gitignore
│   mvnw
│   mvnw.cmd
│   pom.xml
│   
├───.mvn
│   └───wrapper
│           maven-wrapper.jar
│           maven-wrapper.properties
│           
├───src
│   ├───main
│   │   ├───java
│   │   │   └───GameRanks
│   │   │       └───GameRanks
│   │   │           │   GameRanksApplication.java
│   │   │           │   
│   │   │           ├───annotation
│   │   │           │       AccessBy.java
│   │   │           │       
│   │   │           ├───api
│   │   │           │       DeveloperApiController.java
│   │   │           │       GameApiController.java
│   │   │           │       GeneralApiController.java
│   │   │           │       PublisherApiController.java
│   │   │           │       UserApiController.java
│   │   │           │       
│   │   │           ├───config
│   │   │           │       WebMvcConfig.java
│   │   │           │       
│   │   │           ├───controller
│   │   │           │       DeveloperController.java
│   │   │           │       GameController.java
│   │   │           │       GeneralController.java
│   │   │           │       PublisherController.java
│   │   │           │       UserController.java
│   │   │           │       
│   │   │           ├───exception
│   │   │           │       EmailInUseException.java
│   │   │           │       UserNotValidException.java
│   │   │           │       
│   │   │           ├───interceptor
│   │   │           │       AuthInterceptor.java
│   │   │           │       
│   │   │           ├───model
│   │   │           │       BaseModel.java
│   │   │           │       Developer.java
│   │   │           │       Game.java
│   │   │           │       Publisher.java
│   │   │           │       Review.java
│   │   │           │       User.java
│   │   │           │       
│   │   │           ├───repository
│   │   │           │       DeveloperRepository.java
│   │   │           │       GameRepository.java
│   │   │           │       PublisherRepository.java
│   │   │           │       ReviewRepository.java
│   │   │           │       UserRepository.java
│   │   │           │       
│   │   │           ├───responseStruct
│   │   │           │       GameStruct.java
│   │   │           │       
│   │   │           └───service
│   │   │                   DeveloperService.java
│   │   │                   GameService.java
│   │   │                   PublisherService.java
│   │   │                   UserService.java
│   │   │                   
│   │   └───resources
│   │       │   application.properties
│   │       │   import.sql
│   │       │   
│   │       ├───static
│   │       └───templates
│   │               developer.html
│   │               developerList.html
│   │               game.html
│   │               gameList.html
│   │               login.html
│   │               main.html
│   │               notFound.html
│   │               publisher.html
│   │               publisherList.html
│   │               register.html
│   │               user.html
│   │               
│   └───test
│       └───java
│           └───GameRanks
│               └───GameRanks
│                       GameRanksApplicationTests.java
│                       
└───target
    │   GameRanks-0.0.1-SNAPSHOT.jar
    │   GameRanks-0.0.1-SNAPSHOT.jar.original
    │   
    ├───classes
    │   │   .netbeans_automatic_build
    │   │   application.properties
    │   │   import.sql
    │   │   
    │   ├───GameRanks
    │   │   └───GameRanks
    │   │       │   GameRanksApplication.class
    │   │       │   
    │   │       ├───annotation
    │   │       │       AccessBy.class
    │   │       │       
    │   │       ├───api
    │   │       │       DeveloperApiController.class
    │   │       │       GameApiController.class
    │   │       │       GeneralApiController.class
    │   │       │       PublisherApiController.class
    │   │       │       UserApiController.class
    │   │       │       
    │   │       ├───config
    │   │       │       WebMvcConfig.class
    │   │       │       
    │   │       ├───controller
    │   │       │       DeveloperController.class
    │   │       │       GameController.class
    │   │       │       GeneralController.class
    │   │       │       PublisherController.class
    │   │       │       UserController.class
    │   │       │       
    │   │       ├───exception
    │   │       │       EmailInUseException.class
    │   │       │       UserNotValidException.class
    │   │       │       
    │   │       ├───handler
    │   │       ├───interceptor
    │   │       │       AuthInterceptor.class
    │   │       │       
    │   │       ├───model
    │   │       │       BaseModel.class
    │   │       │       Developer.class
    │   │       │       Game$Genre.class
    │   │       │       Game.class
    │   │       │       Publisher.class
    │   │       │       Review.class
    │   │       │       User$AccessLevel.class
    │   │       │       User.class
    │   │       │       
    │   │       ├───repository
    │   │       │       DeveloperRepository.class
    │   │       │       GameRepository.class
    │   │       │       PublisherRepository.class
    │   │       │       ReviewRepository.class
    │   │       │       UserRepository.class
    │   │       │       
    │   │       ├───responseStruct
    │   │       │       GameStruct.class
    │   │       │       
    │   │       └───service
    │   │               DeveloperService.class
    │   │               GameService.class
    │   │               PublisherService.class
    │   │               UserService.class
    │   │               
    │   └───templates
    │           developer.html
    │           developerList.html
    │           game.html
    │           gameList.html
    │           login.html
    │           main.html
    │           notFound.html
    │           publisher.html
    │           publisherList.html
    │           register.html
    │           user.html
    │           
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-archiver
    │       pom.properties
    │       
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       │           createdFiles.lst
    │       │           inputFiles.lst
    │       │           
    │       └───testCompile
    │           └───default-testCompile
    │                   createdFiles.lst
    │                   inputFiles.lst
    │                   
    ├───surefire-reports
    │       GameRanks.GameRanks.GameRanksApplicationTests.txt
    │       TEST-GameRanks.GameRanks.GameRanksApplicationTests.xml
    │       
    └───test-classes
        │   .netbeans_automatic_build
        │   
        └───GameRanks
            └───GameRanks
                    GameRanksApplicationTests.class
</pre>
</details>

## 4. Tesztelés


## 5. Felhasználói dokumentáció
