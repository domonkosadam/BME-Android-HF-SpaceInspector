# Házi feladat specifikáció

## Android alapú szoftverfejlesztés
### 2022 őszi félév
### Space Inspector - [Letöltés](/APK/SpaceInspector.apk "download")
### Domonkos Ádám - (CWGYWC)
### domonkosadam01@gmail.com
### Laborvezető: Kövesdán Gábor

## Bemutatás

A NASA REST API-jával szeretném elkészíteni az alkalmazást. Az űr iránt érdeklődők használhatják 
érdekes információk közötti böngészésre.
Ismeretterjesztő alkalmazás, ahol rengeteg információ között lehet keresni. A fő célja, hogy űrrel 
kapcsolatos érdekes információkat adjon át a felhasználónak.

## Főbb funkciók

Minden nap kikerül egy robusztus kép az űrrel kapcsolatban és egy leírás hozzá. (APOD)
A Föld körüli aszteroidákat jelenítem meg egy listában, amikre kattintva pontosabb részletek lesznek 
láthatóak. (Asteroids - NeoWs)
A különböző marsjárók által készített képeket lehet megnézni, ahol különböző szűrőkkel lehet a 
különböző képeket lekérni. (Mars Rover Photos)
Egy űrrel kapcsolatos eseményre keresve nézhetünk képeket, videókat. (NASA Image and Video 
Library)
NASA API: https://api.nasa.gov/


## Technológiák

- UI: Fragmentek NavigationComponent
- Recyclerview: Az alkalmazás képes lesz listák megjelenítésére
- Hálózat kezelés: Az alkalmazás a https://api.nasa.gov/ API-t használja, és REST végponton keresztül tölti le az adatokat.
- Adatbáziskezelés: Kedvencek képek elmenthetőek, ezeket lokálisan tárolja az alkalmazás.
- Notification: Az alkalmazás indításkor értesítést küld, ha egy megjelent egy új napi kép az 
APOD szolgáltatásban.

# Házi feladat dokumentáció

## Felhasználói kézikönyv

### Menü

|| |
| -------------- | -------------- |
| ![Menü](/Images/Menu_framed.png) | ![Kedvencek](/Images/Favourites_framed.png) |

A főmenüből minden további főbb funkció elérhető. A jobb felső csillag menü elemre kattintva megnyílik a kedvencek közé mentett napi képek, amik offline is elérhetőek.

### APOD

|| |
| -------------- | -------------- |
| ![APOD](/Images//Apod_framed.png) | ![APOD](/Images/Apod_save_framed.png) |

Az APOD menüpont alatt található szolgáltatásban nézhetjük meg a napi képeket. A képre kattintva megnyílik teljes képernyős változatban, ahol akár bele is nagyíthatunk. A jobb felső csillag gombbal hozzáadhatjuk, vagy eltávolíthatjuk az offline is elérhető kedvencek közül.

### NeoWS

| | |
| -------------- | -------------- |
| ![NeoWs](/Images/Neows_framed.png) | ![NeoWs](/Images/NeoWs_details_framed.png) |

A NeoWs menüpontban érhetjük a NASA aszteroida adatbázisát. Itt egy nagy lapozható listát jelenít meg az alkalmazás, amiben megjeleníti a meteor nevét és becsült méretét. Az ikonok azt jelzik, hogy veszélyt jelent-e a Földre a 
pályája alapján. Egy elemet kiválasztva láthatjuk a részletes adatait, illetve, a múlt-, illetve jövőbeli időpontokat, amikor megközelíti a Földet.

### Mars Rover

| | |
| -------------- | -------------- |
| ![Rover](/Images/Rovers_menu_framed.png) | ![Rover](/Images/Rovers_Last_framed.png) |

A Mars Rover menüpontot választva a NASA különböző marsjárói által készített képeket nézhetjük meg. Először ki kell választanunk, hogy melyik jármű képeire vagyunk kíváncsiak. Ha rákattintottunk egy adott elemre, megnyílnak a legutoljára készített képei. A képre kattintva megnyílik teljes képernyős változatban, ahol akár bele is nagyíthatunk.

| | |
| -------------- | -------------- |
| ![Rover](/Images/Rovers_selected_framed.png) | ![Rover](/Images/Rovers_no_images_framed.png) |

A Mars Rover menüponton belül lehetőségünk van szűrni a képek között. Kiválaszthatjuk egy listából, hogy melyik kamera képére vagyunk kíváncsiak. A ’-’ jelet választva az összes kamera képét mutatni fogja. Az API 
limitációjából adódóan mindig kötelező kiválasztani egy dátumot a jobb felső dátum mezőre kattintva. Ha nem választunk ki semmit, a legutolsó lehetséges napot veszi figyelembe. Ha a kiválasztott paramétereknek nem felel meg egy 
kép sem, azt egy hibaüzenettel jelzi a felhasználó számára.

### NASA Images

| | |
| -------------- | -------------- |
| ![Images](/Images/Images_framed.png) | ![Images](/Images/images_details_framed.png) |

Az Images menüpontot választva a NASA kép gyűjteményében böngészhetünk. Ha beírunk egy keresési kulcsszót, akkor az ahhoz kapcsolódó összes képet visszaadja egy rövid leírással. A képre kattintva megnyílik teljes 
képernyős változatban, ahol akár bele is nagyíthatunk.

## Felhasznált technológiák

- Minden képernyő optimalizált **fekvő-**, illetve **álló módr**a is. Az alkalmazás támogatja a **sötét módban** való megjelenítést is.
- Az alkalmazás kijelzői **fragmentekből** állnak, amit **NavigationComponenttel** végzek
- [**Retrofit**](https://github.com/square/retrofit) library az **API hívásokhoz**
- [**Glide**](https://github.com/bumptech/glide) osztálykönyvtár használata a **képek** megjelenítésére
- [**LottieProgressDialog**](https://github.com/welcome2c/LottieProgressDialog) a **töltőképernyők** megjeleítésére
- [**Awesome Dialog**](https://github.com/chnouman/AwesomeDialog) a **visszajelzése**k megjelenítéséhez
- [**zoomage**](https://github.com/chnouman/AwesomeDialog) segítségével **nagyíthatóak a képek**
- **WebView** használata **videó lejátszására**
- **SQLite** alapú adattárolás **ROOM** libraryval
- **SharedPreference** az utolsó megnyitás dátumának tárolásához
- **Notification** küldése az alkalmazás indulásakor, ha van új kép
- **Implicit intent** használata a részletes aszteroida adatokhoz
- **ViewModel + Repository architektúra** szerint készült a projekt

## Fontosabb technológiai megoldások

A legnagyobb kihívást a dátumokkal való műveletek jelentették. Az API a dátumokat sztringként kezeli, amit először dátum formátumúvá kellett alakítani, hogy el lehessen rajta végezni a műveleteket. Utána pedig vissza kellett konvertálni az API használatához. A beépített dátum konvertáló csak a legújabb Android verziókat támogatja, így nekem kézzel kellett megírni a konverziókat.

A ViewModel architektúra implementálásával felhasználóbarát módon le tudtam kezelni a töltőképernyőket, mert a NASA API bizonyos esetekben lassú lehet. Ez segített elmenteni a képernyők állapotát, is.

A Repository minta használatával egyszerűen el tudom érni a külső adatokat.

A videólejátszót először a VideoView implementálásával akartam megvalósítani, de a visszakapott url nem egy videóra, hanem egy videószolgáltató oldalra mutatott. Így kénytelen voltam megjeleníteni az egész oldalt, mert 
nem lehetett kiszedni csak a videófájl elérési útvonalát.