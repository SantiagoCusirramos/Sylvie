
Mobile Aplication Strucutre
```
sylvie-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sylvie/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/
│   │   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   │   └── RetrofitInstance.kt
│   │   │   │   │   ├── models/
│   │   │   │   │   │   ├── Producto.kt
│   │   │   │   │   │   ├── AnalisisResponse.kt
│   │   │   │   │   │   ├── RecomendacionResponse.kt
│   │   │   │   │   │   └── Usuario.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── SylvieRepository.kt
│   │   │   │   │   └── database/
│   │   │   │   │       ├── AppDatabase.kt
│   │   │   │   │       ├── HistorialDao.kt
│   │   │   │   │       └── HistorialEntity.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── auth/
│   │   │   │   │   │   ├── LoginFragment.kt
│   │   │   │   │   │   └── RegistroFragment.kt
│   │   │   │   │   ├── scan/
│   │   │   │   │   │   └── ScanFragment.kt
│   │   │   │   │   ├── result/
│   │   │   │   │   │   └── ResultFragment.kt
│   │   │   │   │   ├── profile/
│   │   │   │   │   │   └── ProfileFragment.kt
│   │   │   │   │   └── history/
│   │   │   │   │       └── HistoryFragment.kt
│   │   │   │   ├── utils/
│   │   │   │   │   ├── SharedPrefsManager.kt
│   │   │   │   │   └── NetworkUtils.kt
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       │   ├── activity_main.xml
│   │   │       │   ├── fragment_scan.xml
│   │   │       │   ├── fragment_result.xml
│   │   │       │   ├── fragment_login.xml
│   │   │       │   ├── fragment_registro.xml
│   │   │       │   ├── fragment_profile.xml
│   │   │       │   └── fragment_history.xml
│   │   │       ├── menu/
│   │   │       │   └── bottom_nav_menu.xml
│   │   │       └── navigation/
│   │   │           └── nav_graph.xml
│   │   └── AndroidManifest.xml
```
