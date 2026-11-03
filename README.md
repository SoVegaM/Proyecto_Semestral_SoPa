# Master Martini - Centro Técnico Gastronómico

App Android con recetas, videos y cursos para panaderías, pastelerías, heladerías y restaurantes.

## Integrantes

- Paula Barjacoba
- Sofia Vega

## Funcionalidades

- Ingreso y registro con validación de formularios
- Perfil de negocio y perfil de usuario con foto desde la galería
- Inicio con buscador y filtros, adaptado a celular, tablet y pantalla girada
- Detalle de video con reproductor de YouTube
- Mis guardados con filtros y notas personales
- Cursos con módulos y progreso
- Eventos con inscripción y contacto por WhatsApp
- Recordatorio de videos con notificaciones
- Accesibilidad: tamaño de texto, alto contraste y negrita

## Tecnologías

- Kotlin y Jetpack Compose (Material 3)
- Arquitectura MVVM con StateFlow
- Navigation Compose
- Room y DataStore para guardar datos en el celular
- Coil para las imágenes

## Estructura del proyecto

El código está en `app/src/main/java/com/example/app_gruposopa/`:

```text
app_gruposopa/
├── data/
│   ├── local/db/        # Room (tablas y consultas) y DataStore (ajustes y perfil)
│   └── repository/      # Videos, cursos, guardados y vistos
├── domain/
│   ├── model/           # Modelos de datos: videos y cursos
│   └── usecase/         # Reglas de negocio: filtro de contenidos
├── navigation/          # Rutas, pantallas y eventos de navegación
├── notificaciones/      # Recordatorios de videos
├── ui/
│   ├── components/      # Partes reutilizables: barra inferior, menú lateral
│   ├── layouts/         # Diseños de Inicio según el tamaño de pantalla
│   ├── screens/         # Pantallas de la app
│   ├── theme/           # Colores, tipografía y tema
│   ├── utils/           # Cálculo del tamaño de pantalla
│   └── viewmodels/      # Estado y validación de cada pantalla
├── MainActivity.kt      # Punto de entrada de la interfaz
└── MasterMartiniApp.kt  # Inicia la base de datos y las notificaciones
```

## Flujo de la app

```text
Bienvenida → Ingreso o registro → Perfil de negocio → Inicio
```

Desde Inicio se llega a:

- **Barra inferior:** Inicio, Cursos, Eventos y Perfil
- **Un video:** Detalle, donde se puede guardar o pedir un recordatorio
- **Menú lateral:** Mis guardados, Accesibilidad, asesoría y Sobre Master Martini

## Flujo de los datos

```text
Pantalla → ViewModel → ContenidosRepository → Room
Pantalla → ViewModel → PreferencesManager → DataStore
```

La pantalla avisa lo que hace el usuario y el ViewModel actualiza el estado. Compose redibuja la pantalla cuando cambia ese estado.

## Cómo ejecutar

Requisitos: Android Studio, JDK 21 y un emulador o celular con Android 7.0 o superior. Se necesita internet para los videos y las miniaturas.

1. Clonar el repositorio:
   
   ```bash
   git clone https://github.com/SoVegaM/Proyecto_Semestral_SoPa.git
   ```

2. Abrir la carpeta en Android Studio y esperar a que termine la sincronización de Gradle.

3. Si Gradle falla por la versión de Java, elegir JDK 21 en **Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK**.

4. Elegir el emulador o celular y presionar **Run**.
