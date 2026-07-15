# Hogar360 - Microservicio de Casas

Microservicio encargado de gestionar las propiedades inmobiliarias dentro de la plataforma **Hogar360**. Permite a vendedores publicar inmuebles, gestionar su ciclo de vida de publicacion y a compradores buscar propiedades con filtros avanzados de ubicacion, precio y caracteristicas.

## Descripcion

Este microservicio hace parte de una arquitectura de microservicios y es consumido por el front-end Angular de Hogar360, ademas de otros microservicios como el [Microservicio de Visitas](https://github.com) (`visits-ms`).

**Funcionalidades principales:**

- Crear y gestionar propiedades inmobiliarias (solo vendedores)
- Buscar y filtrar propiedades por departamento, ciudad, sector, categoria, precio, habitaciones y banos
- Gestionar categorias de propiedades (solo admin)
- Gestionar ubicaciones: departamentos, ciudades y sectores (solo admin)
- Publicacion automatica programada de propiedades segun fecha de activacion
- Consulta de propiedades por publicador/propietario

## Stack Tecnologico

| Componente         | Tecnologia                |
| ------------------ | ------------------------- |
| Lenguaje           | Java 17                   |
| Framework          | Spring Boot 3.4.3         |
| Arquitectura       | Hexagonal (Puertos y Adaptadores) |
| Base de datos      | MySQL 8                   |
| ORM                | Spring Data JPA / Hibernate |
| Seguridad          | Spring Security + JWT     |
| Mapeo de Objetos   | MapStruct 1.6.3           |
| Construccion       | Gradle                    |
| Documentacion API  | SpringDoc OpenAPI 2.8.5   |
| Pruebas            | JUnit 5 + Mockito 5.11.0  |
| Cobertura          | JaCoCo 0.8.8              |

## Arquitectura

El proyecto sigue el patron de **Arquitectura Hexagonal** con las siguientes capas:

```
src/main/java/com/hogar360/houses/
├── domain/                          ← Logica de negocio pura (sin dependencias de framework)
│   ├── model/                       ← Modelos de dominio (HouseModel, CategoryModel, etc.)
│   ├── usecases/                    ← Casos de uso y reglas de negocio
│   ├── ports/
│   │   ├── in/                      ← Puertos de entrada (ServicePort)
│   │   └── out/                     ← Puertos de salida (PersistencePort)
│   ├── criteria/                    ← Objetos de valor para busqueda (HouseSearchCriteria)
│   ├── exceptions/                  ← Excepciones de dominio (~17 clases)
│   └── utils/                       ← Constantes, enums y utilidades de dominio
│
├── application/                     ← Orquestacion y capa de servicios
│   ├── services/                    ← Interfaces de servicio
│   │   └── impl/                    ← Implementaciones de servicios
│   ├── dto/
│   │   ├── request/                 ← DTOs de entrada (records)
│   │   └── response/                ← DTOs de salida (records)
│   └── mappers/                     ← Mappers MapStruct (DTO <-> Dominio)
│
└── infrastructure/                  ← Implementaciones framework-especificas
    ├── endpoints/rest/              ← Controladores REST
    ├── entities/                    ← Entidades JPA
    ├── repositories/mysql/          ← Repositorios Spring Data
    ├── specifications/              ← JPA Specifications para consultas dinamicas
    ├── adapters/persistence/        ← Adaptadores de persistencia (Puerto -> Repositorio)
    ├── mappers/                     ← Mappers MapStruct (Entidad <-> Dominio)
    ├── security/                    ← Filtros JWT, configuracion de seguridad
    ├── exceptionhandlers/           ← Manejo global de excepciones
    └── schedulers/                  ← Tareas programadas (publicacion automatica)
```

## Prerequisitos

- Java 17 o superior
- Gradle (incluido via wrapper)
- MySQL 8 corriendo en `localhost:3306`
- Base de datos `hogar360_houses` creada

## Inicio Rapido

1. **Clonar el repositorio:**

```bash
git clone https://github.com/tu-usuario/hogar360-houses-ms.git
cd hogar360-houses-ms
```

2. **Configurar las variables de entorno** (ver seccion siguiente).

3. **Crear la base de datos en MySQL:**

```sql
CREATE DATABASE hogar360_houses;
```

4. **Ejecutar la aplicacion:**

```bash
# Windows
gradlew.bat bootRun

# Linux / macOS
./gradlew bootRun
```

La aplicacion estara disponible en `http://localhost:8090`.

## Variables de Entorno

| Variable                | Descripcion                                  | Ejemplo                        |
| ----------------------- | -------------------------------------------- | ------------------------------ |
| `DB_USER`               | Usuario de MySQL                             | `root`                         |
| `DB_PASSWORD`           | Contrasena de MySQL                          | `password123`                  |
| `JWT_SECRET_KEY`        | Clave HMAC para firmar y verificar tokens JWT| `mySecretKey1234567890...`     |
| `JWT_EXPIRATION_MILLIS` | Tiempo de expiracion del token en milisegundos| `180000` (3 minutos)          |

## Endpoints API

### Houses

| Metodo | Ruta                                       | Autenticacion   | Descripcion                                      |
| ------ | ------------------------------------------ | --------------- | ------------------------------------------------ |
| `POST`   | `/api/v1/house/`                         | JWT (VENDEDOR)  | Crear una propiedad                              |
| `GET`    | `/api/v1/house/search`                   | Publico         | Buscar propiedades con filtros y paginacion      |
| `GET`    | `/api/v1/house/{houseId}`                | Publico         | Obtener propiedad por ID                         |
| `GET`    | `/api/v1/house/{houseId}/owner`          | Publico         | Obtener el ID del propietario de una propiedad   |
| `GET`    | `/api/v1/house/publisher/{publisherId}`  | Publico         | Listar propiedades por publicador                |
| `GET`    | `/api/v1/house/search-ids-by-location`   | Publico         | Buscar IDs de propiedades por ciudad y sector    |

### Categories

| Metodo | Ruta                                       | Autenticacion   | Descripcion                                      |
| ------ | ------------------------------------------ | --------------- | ------------------------------------------------ |
| `POST`   | `/api/v1/category/`                      | JWT (ADMIN)     | Crear categoria                                  |
| `GET`    | `/api/v1/category/`                      | Publico         | Listar categorias (paginado)                     |

### Locations

| Metodo | Ruta                                       | Autenticacion   | Descripcion                                      |
| ------ | ------------------------------------------ | --------------- | ------------------------------------------------ |
| `POST`   | `/api/v1/location/`                      | JWT (ADMIN)     | Crear ubicacion                                  |
| `GET`    | `/api/v1/location/search`                | Publico         | Buscar ubicaciones por termino                   |
| `GET`    | `/api/v1/location/city/{cityId}`         | Publico         | Obtener ubicaciones por ciudad                   |

### Departments & Cities

| Metodo | Ruta                                       | Autenticacion   | Descripcion                                      |
| ------ | ------------------------------------------ | --------------- | ------------------------------------------------ |
| `GET`    | `/api/v1/department`                     | Publico         | Listar departamentos                             |
| `GET`    | `/api/v1/city/department/{departmentId}` | Publico         | Obtener ciudades por departamento                |

### Ejemplo: Crear propiedad

```http
POST /api/v1/house/
Content-Type: application/json
Authorization: Bearer <token>

{
  "name": "Apartamento Norte",
  "description": "Apartamento de 3 habitaciones en el norte",
  "categoryId": 1,
  "bedrooms": 3,
  "bathrooms": 2,
  "price": 350000000,
  "locationId": 5,
  "publisherId": 101,
  "publicationDate": "2025-06-01",
  "activePublicationDate": "2025-06-15"
}
```

### Ejemplo: Buscar propiedades

```http
GET /api/v1/house/search?department=1&city=1&sector=Norte&category=1&bedrooms=3&price=350000000&sortBy=price&sortDirection=asc&page=0&size=10
```

## Estructura del Proyecto

```
hogar360-houses-ms/
├── build.gradle                          # Configuracion de dependencias y plugins
├── settings.gradle                       # Nombre del proyecto
├── gradlew / gradlew.bat                 # Gradle wrapper
├── src/
│   ├── main/
│   │   ├── java/com/hogar360/houses/
│   │   │   ├── HousesApplication.java
│   │   │   ├── commons/configurations/   # Configuracion de beans, Swagger y constantes
│   │   │   └── houses/
│   │   │       ├── domain/               # Modelos, casos de uso, puertos, excepciones
│   │   │       ├── application/          # Servicios, DTOs, mappers
│   │   │       └── infrastructure/       # Controladores, entidades, repositorios, adaptadores
│   │   └── resources/
│   │       └── application.yml           # Configuracion de la aplicacion
│   └── test/
│       └── java/com/hogar360/houses/
│           └── houses/domain/usecases/   # Pruebas unitarias de casos de uso
```

## Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
gradlew.bat test

# Ejecutar pruebas con reporte de cobertura
gradlew.bat test jacocoTestReport
```

Los reportes de cobertura se generan en:
- HTML: `build/reports/jacoco/test/html/index.html`
- XML: `build/reports/jacoco/test/jacocoTestReport.xml`

## Documentacion API

Una vez ejecutada la aplicacion, la documentacion interactiva esta disponible en:

- **Swagger UI:** `http://localhost:8090/swagger-ui/index.html`
- **OpenAPI Docs:** `http://localhost:8090/api-docs`

## Reglas de Negocio

- Solo usuarios con rol **VENDEDOR** pueden crear propiedades.
- Solo usuarios con rol **ADMIN** pueden crear categorias y ubicaciones.
- Una propiedad debe tener minimo **1 dormitorio** y **1 baño**.
- El precio debe ser mayor a **0**.
- El nombre de la propiedad mas su ubicacion deben ser **unicos** (no duplicados en la misma ubicacion).
- La fecha de publicacion activa debe ser **hoy o dentro de maximo 30 dias**.
- Nombre de categoria: maximo **50 caracteres**; descripcion: maximo **90 caracteres**.
- Sector de ubicacion: maximo **50 caracteres**.
- Las propiedades con estado **PAUSED** se publican automaticamente cuando llega su fecha de activacion.

## Licencia

Este proyecto es privado. Todos los derechos reservados.
