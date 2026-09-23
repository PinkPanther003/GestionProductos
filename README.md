# Sistema de Gestión de Productos - Prueba Técnica Java Jr.

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java (JDK 8+)
- **Interfaz Gráfica:** Java Swing (Código plano en `FormProducto.java`)
- **Base de Datos:** MySQL / MariaDB
- **Persistencia:** JDBC (Java Database Connectivity)
- **Patrón de Diseño:** MVC (Modelo - Vista - Controlador) / DAO

## 📌 Estado de la Aplicación
- [x] Diseño de interfaz limpia en Java Swing (Vista única `FormProducto.java`).
- [x] Conexión a Base de Datos mediante clase `ConexionDB.java`.
- [x] Modelo de dominio `Producto.java`.
- [x] Implementación de operaciones CRUD (Capa `ProductoDAO.java`).
- [x] Integración de Búsqueda / Consulta por código o ID.
- [ ] *Pendiente:* Migración completa de consultas JDBC tradicionales a Procedimientos Almacenados (Stored Procedures).

## 🚀 Instrucciones de Ejecución
1. Crear la base de datos `bd_tienda` e importar el script SQL `script.sql`.
2. Configurar credenciales en `src/config/ConexionDB.java`.
3. Ejecutar la clase principal `src/gestionproductos/GestionProductos.java`.
