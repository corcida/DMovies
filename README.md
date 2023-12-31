# Flujo general de la aplicación:

https://github.com/corcida/DMovies/assets/21995913/4a20f6e8-44ed-499c-9a3f-9597610fc8b4

# Capturas de consola de Firestore:

<img width="1424" alt="Captura de pantalla 2023-11-13 a la(s) 22 59 31" src="https://github.com/corcida/DMovies/assets/21995913/db186156-79a5-4eba-bd3e-6221a098e898">

<img width="1437" alt="Captura de pantalla 2023-11-13 a la(s) 22 59 14" src="https://github.com/corcida/DMovies/assets/21995913/56fe97f3-2a50-4c69-acb9-6e8bc11de749">

# Información general del proyecto:

# Features

1. Kotlin
1. Clean architecture
1. MVVM
1. Retrofit para conexiones http
1. Room database 
1. Use cases
1. Kotlin Flow
1. Inyección de dependencias mediante Hilt
1. Manejo de dependencias con Kotlin KTS

# Arquitectura

## Clean architecture

El proyecto está construido bajo el marco clean architecture, dividido en 5 capas modularizadas en 4 módulos, estas son:

- Domain: Contiene todo los modelos de negocio.
- Data: En esta capa se encuentra una definición abstracta de las diferentes fuentes de datos, y la forma en que se deben utilizar. Usando un patrón de diseño de repositorio que, para una determinada solicitud, es capaz de decidir dónde encontrar la información.
- Use cases: Capa que interactúa con la capa de data, definiendo así los procesos que deben ser realizados y pasos para hacer a los mismos. Es también la capa a la cual tiene acceso la capa de presentación desde los view models.
- Framework: Primera capa ya perteneciente al módulo android, y la misma define las implementaciones de los local source y remote data source provenientes de la capa de data.
- Presentation (UI): Segunda capa del módulo android, en esta se definen las pantallas, view models y navegación, accediendo a la data mediante los use cases. 

## MVVM

Toda la capa de presentación está construída bajo el patrón MVVM contando con una comunicación entre el View Model y el Modelo, mediante los use case inyectados al constructor gracias a Hilt, y entre la View y View Model, a través de livedata comunicandole los cambios que emitía el flow que colecta los valores emitidos por el use case.

# Principales librerías

## Data

En la capa de framwork se sustentan los archivos de implementación en las librerías de Retrofit para el caso de la data de fuente remota, y de Room para la data local. 

## Inyección de dependencias

La inyección de dependencias, necesaria para la escalabilidad del proyecto y facilitamiento a la hora del testing, se realizó gracias a Hilt, mediante la construcción de 5 módulos, los cuales son:

- AppModule: Construye todo lo correspondiente a la capa de Framework de la app.
- DataModule: Define todo los repositorios de la app.
- LocalDataSourceModule: Construye todo los local data source, además de los DAO necesitarios para inyectar las implementaciones.
- RemoteDataSourceModule: Crea todo los remote data source, además de los service, y demás recursos necesitados para inyectar las implementaciones.
- UseCasesModule: Encargado de construir todos los casos de uso los cuales son inyectados luego a nuestros view models.

# Mejoras

## Testing

No tuve el tiempo necesario para construir test unitarios. Tiendo a realizarlos en los use cases y view models. Lastimosamente no logré el cometido.

## Mayor depuración

Realicé varias pruebas end-to-end, pero no tuve el tiempo necesario para depurar del todo la app. Disculpas por adelantado si existen algunos bugs.



