
Mobile Aplication Strucutre
```
sylvie-app/app/src/main/
├── java/com/sylvie/app/
│   ├── data/
│   │   ├── api/
│   │   │   ├── ApiService.kt
│   │   │   └── RetrofitInstance.kt
│   │   ├── models/
│   │   │   ├── Producto.kt
│   │   │   ├── AnalisisResponse.kt
│   │   │   ├── AnalisisRequest.kt
│   │   │   ├── RecomendacionResponse.kt
│   │   │   ├── RecomendacionRequest.kt
│   │   │   ├── RecomendacionHistorial.kt
│   │   │   ├── Usuario.kt
│   │   │   ├── LoginResponse.kt
│   │   │   ├── RestriccionResponse.kt
│   │   │   └── RestriccionRequest.kt
│   │   └── repository/
│   │       └── SylvieRepository.kt
│   ├── ui/
│   │   ├── auth/
│   │   │   └── LoginFragment.kt
│   │   ├── scan/
│   │   │   └── ScanFragment.kt
│   │   ├── result/
│   │   │   └── ResultFragment.kt
│   │   ├── profile/
│   │   │   ├── ProfileFragment.kt
│   │   │   └── RestriccionAdapter.kt
│   │   └── history/
│   │       ├── HistoryFragment.kt
│   │       └── HistoryAdapter.kt
│   ├── utils/
│   │   └── SharedPrefsManager.kt
│   └── MainActivity.kt
│
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── fragment_scan.xml
    │   ├── fragment_result.xml
    │   ├── fragment_login.xml
    │   ├── fragment_profile.xml
    │   ├── fragment_history.xml
    │   ├── item_restriccion.xml
    │   └── item_history.xml
    ├── menu/
    │   └── bottom_nav_menu.xml
    └── navigation/
        └── nav_graph.xml
```
