# LearningRoom
## Szakdolgozat

### Telepítés
- A telepítéshez Maven projektkezelő szoftverre van szükség, amely innen letölthető: *https://maven.apache.org/download.cgi*.
- Az adatbázishoz a *XAMPP* szofvercsomagra lesz szükség, innen letölthető: *https://www.apachefriends.org/hu/download.html*.
- A telepítés után el kell indítani az Apache-ot, és a MySQL-t. Ezek után tudunk belépni a *http://localhost/phpmyadmin/server_import.php*   oldalra ahol a **learning_room.sql** fájlt kell importálnunk az adatbázishoz.
- Következő lépés, hogy egy konzolt kell elindítani, majd be kell lépni egy tetszőleges könyvtárba.
- Ezután ki kell adni a következő parancsot: *git clone https://github.com/eddiefrom/LearningRoom.git* . 
- Miután ez megvan, a konzolban belépünk a *LearningRoom* mappába, és kiadjuk az *mvn install* parancsot.
- A program *.jar* kiterjesztésű indítóikonja elérhető a *LearningRoom\target* mappában.
