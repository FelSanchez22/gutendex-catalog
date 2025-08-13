# 📚 Catálogo de Libros - Gutendex API
Aplicación de consola que busca, registra y gestiona libros desde la API de [Gutendex](https://gutendex.com/), almacenándolos en PostgreSQL.

## ✨ Características

- 🔍 Buscar libros por título (conexión a API Gutendex)
- 💾 Almacenamiento en base de datos PostgreSQL
- 📊 Listado de libros y autores registrados
- 🎂 Filtrado de autores vivos por año
- 🌍 Búsqueda de libros por idioma (es, en, fr, etc.)
- 🔄 Evita duplicados mediante Gutendex ID

## 🛠️ Tecnologías

- **Backend**: Java 17 + Spring Boot 3.3.3
- **Base de datos**: PostgreSQL
- **API Externa**: Gutendex (libros clásicos gratuitos)
- **Persistencia**: Spring Data JPA + Hibernate

## 🚀 Cómo ejecutar

### Requisitos previos
- Java 17 JDK instalado
- PostgreSQL 14+ corriendo localmente
- Conexión a internet (para acceder a Gutendex API)

### Pasos de instalación

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/FelSanchez22/gutendex-catalog.git
   cd gutendex-catalog
Crea la base de datos:

sql
CREATE DATABASE gutendex_catalog;
Configura las credenciales (en application.properties o variables de entorno):

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gutendex_catalog
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
Ejecuta la aplicación:

bash
mvn spring-boot:run
🖥️ Uso
Al iniciar, verás este menú interactivo:

text
--------------------
Elija la opción a través de su número:
1- Buscar libro por título.
2- Listar libros registrados.
3- Listar autores registrados.
4- Listar autores vivos en un determinado año.
5- Listar libros por idioma.
0- Salir.
Ejemplo de salida al buscar un libro:

text
----- LIBRO -----
Titulo: Pride and Prejudice
Autor: Jane Austen
Idioma: en
Numero de descargas: 5000
------------------
🗃️ Estructura del proyecto
text
gutendex-catalog/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/gutendexcatalog/
│   │   │       ├── console/       # Lógica de la interfaz de consola
│   │   │       ├── model/         # Entidades (Book, Author)
│   │   │       ├── repository/    # Repositorios JPA
│   │   │       ├── service/       # Lógica de negocio
│   │   │       └── GutendexCatalogApplication.java
│   │   └── resources/
│   │       └── application.properties
├── .gitignore
├── pom.xml
└── README.md
📄 Licencia
Este proyecto está bajo la licencia MIT. Ver LICENSE para más detalles.

Hecho con ❤️ por FelSanchez22 | ¡Contribuciones son bienvenidas!
