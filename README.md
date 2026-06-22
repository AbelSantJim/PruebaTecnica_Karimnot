# PruebaTecnica — Sistema de Gestión de Usuarios

Aplicación fullstack para la administración de usuarios con autenticación JWT, desarrollada como prueba técnica.

---

## Tecnologías

**Backend**
- Java 21
- Spring Boot 4.1
- Spring Security + JWT (jjwt)
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

**Frontend**
- Angular 21
- TypeScript
- FormsModule / HttpClient
- Tabler Icons

---

## Objetivo

Proveer una plataforma web para la gestión completa de usuarios, con control de acceso mediante autenticación JWT y una interfaz moderna que permita consultar, crear, editar y eliminar registros de usuarios desde un panel de administración.

---

## Funcionalidades

### Autenticación
- Login con email y contraseña
- Generación y validación de token JWT
- Rutas protegidas — todas las peticiones requieren token válido
- Contraseñas hasheadas con BCrypt

### Panel de usuarios
- Tabla paginada (10 registros por página)
- Búsqueda en tiempo real por nombre o email
- Filtro por rol (Admin / Usuario)
- Filtro por estado (Activo / Inactivo)
- Crear nuevo usuario
- Editar usuario existente
- Eliminar usuario con confirmación

### Gestión de usuarios
Cada usuario contiene:
- Nombre y apellido
- Email único
- Teléfono
- Rol: `Admin` o `User`
- Estado: `Active` o `Inactive`
- Dirección (calle, número, ciudad, código postal)
- Foto de perfil (URL)
- Contraseña (almacenada con BCrypt)

---

## Estructura del proyecto

```
mi-proyecto/
├── backend/                  # Spring Boot
│   └── src/main/java/com/example/PruebaTecnica/
│       ├── Controllers/      # UsersController, AuthController
│       ├── Services/         # UsersService
│       ├── Repositories/     # UsersRepository
│       ├── modelos/          # Users, Address
│       ├── security/         # JwtUtil, JwtFilter
│       └── config/           # SecurityConfig, DataSeeder
│
└── frontend/                 # Angular
    └── src/app/
        ├── features/
        │   ├── dashboard/    # Tabla principal de usuarios
        │   └── user-form/    # Formulario crear / editar
        └── core/
            ├── services/     # UsersService (HTTP)
            └── interceptors/ # jwtInterceptor
```

---

## Instalación y uso

### Requisitos previos
- Java 21
- Node.js 18+
- PostgreSQL
- Maven

### Backend

1. Crear la base de datos en PostgreSQL:

```sql
CREATE DATABASE prueba_tecnica;
```

2. Configurar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/prueba_tecnica
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=update
jwt.secret=tuClaveSecretaMuyLargaParaJWT1234567890
```

3. Ejecutar el backend:

```bash
cd backend
mvn spring-boot:run
```

El servidor inicia en `http://localhost:8080`.
Al arrancar se crea automáticamente un usuario administrador:
- **Email:** `admin@example.com`
- **Password:** `password`

### Frontend

```bash
cd frontend
npm install
ng serve
```

La aplicación estará disponible en `http://localhost:4200`.

---

## Endpoints principales

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/api/pruebaTecnica/login` | Login, retorna JWT | No |
| GET | `/api/pruebaTecnica/usuarios` | Lista paginada y filtrada | Sí |
| GET | `/api/pruebaTecnica/usuarios/:id` | Usuario por ID | Sí |
| POST | `/api/pruebaTecnica/usuarios` | Crear usuario | Sí |
| PUT | `/api/pruebaTecnica/usuarios/:id` | Editar usuario | Sí |
| DELETE | `/api/pruebaTecnica/usuarios/:id` | Eliminar usuario | Sí |

### Parámetros de consulta para GET /usuarios

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `page` | number | Número de página (inicia en 0) |
| `limit` | number | Registros por página |
| `role` | string | Filtrar por `Admin` o `User` |
| `status` | string | Filtrar por `Active` o `Inactive` |
| `search` | string | Búsqueda por nombre o email |

---

## Base de datos

El proyecto incluye un seed de 50 usuarios de ejemplo que se pueden insertar con el script SQL incluido. La base de datos se crea automáticamente al iniciar el backend gracias a `ddl-auto=update`.

```
users
├── idUsuario (PK)
├── firstName
├── lastName
├── email (unique)
├── phoneNumber
├── role
├── status
├── password
├── profilePicture
└── idAddress (FK → address)

address
├── idAddress (PK)
├── street
├── number
├── city
└── postalCode
```
