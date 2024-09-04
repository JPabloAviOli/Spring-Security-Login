# SecureLoginApp

## Descripción

SecureLoginApp es un sistema de autenticación que gestiona el inicio de sesión de usuarios, el registro y la seguridad de las cuentas. Utiliza JSON Web Tokens (JWT) para garantizar el acceso seguro, validación de correo electrónico para la activación de cuentas, y funciones para cambiar y restablecer contraseñas. Este proyecto demuestra mis habilidades en desarrollo backend con Spring Boot y seguridad de aplicaciones.

## Características Principales

- **Autenticación Segura**: Implementación de JWT para proteger las rutas y gestionar sesiones de usuario mediante roles.
- **Registro y Activación de Cuentas**: Verificación de cuenta a través de correo electrónico para garantizar la autenticidad del usuario.
- **Gestión de Contraseñas**: Funcionalidades para cambiar y restablecer contraseñas, mejorando la experiencia de usuario y la seguridad.
- **Manejo de Errores Basado en Aspectos**: Implementación de una arquitectura orientada a aspectos utilizando `@RestControllerAdvice`, lo que permite un manejo de excepciones centralizado y coherente en toda la aplicación. Esto asegura que las respuestas a errores sean uniformes y alineadas con el protocolo HTTP, mejorando la mantenibilidad y robustez del sistema.
- **Arquitectura Sólida**: Uso del patrón Builder para la creación de DTOs, asegurando un código limpio y mantenible.
- **Documentación de API**: Integración de SpringDoc OpenAPI para documentar y probar las API de forma sencilla.
- **Contenedorización**: Docker y Docker Compose para facilitar la configuración y el despliegue del proyecto en diferentes entornos.

## Tecnologías Utilizadas

- **Backend**: Spring Boot, Spring Security, Spring MVC, Validation
- **Base de Datos**: MySQL
- **Seguridad**: JWT, Validación de Correo Electrónico
- **Templating**: Thymeleaf
- **Documentación**: SpringDoc OpenAPI
- **Contenedorización**: Docker, Docker Compose
- **Construcción y Despliegue**: Maven, Spring Boot Maven Plugin
- **Otras Herramientas**: Lombok, Spring Boot DevTools

## Instalación y Configuración

### Pre requisitos

- **Docker Daemon**: Asegúrate de que Docker Daemon (dockerd) esté instalado y en funcionamiento en tu máquina. Este componente es esencial para la ejecución de contenedores Docker y se encarga de las operaciones relacionadas con los contenedores.

- **Docker Desktop (para Windows y macOS)**: Si estás utilizando Windows o macOS, Docker Desktop es necesario para proporcionar una interfaz gráfica y facilitar la gestión de Docker. Docker Desktop incluye Docker Daemon y otros componentes necesarios para el funcionamiento de Docker en estos sistemas operativos.

## Ejecutar la Aplicación

Si has descargado la imagen de la aplicación desde Docker Hub o has clonado el repositorio, puedes usar Docker Compose para levantar toda la aplicación y sus servicios asociados.

### Descargar la Imagen de Docker Hub

Si deseas descargar y ejecutar la imagen directamente desde Docker Hub, sigue estos pasos:

1. **Descargar la Imagen**

    ```bash
    docker pull <tu-usuario>/secure-login-app:latest
    ```

2. **Ejecutar la Imagen**

    Después de descargar la imagen, puedes ejecutar un contenedor basado en ella con el siguiente comando:

    ```bash
    docker run -d -p 8080:8080 --name secure-login-app <tu-usuario>/secure-login-app:latest
    ```

    Esto iniciará un contenedor en segundo plano y mapeará el puerto 8080 del contenedor al puerto 8080 de tu máquina local.

### Clonar el Repositorio y Usar Docker Compose

Si prefieres clonar el repositorio y usar Docker Compose para construir y gestionar los servicios, sigue estos pasos:

1. **Clonar el Repositorio**

    ```bash
    git clone https://github.com/tuusuario/SecureLoginApp.git
    ```

2. **Navegar al Directorio del Proyecto**

    ```bash
    cd SecureLoginApp
    ```

3. **Configurar Variables de Entorno**

    Crea un archivo `.env` en la raíz del proyecto con las siguientes variables y utiliza como guia el archivo `.env-template`:

    ```env
    MYSQL_ROOT_PASSWORD=your_root_password
    MYSQL_DATABASE=your_database_name
    MYSQL_USER=your_username
    MYSQL_PASSWORD=your_password
    ```

4. **Construir y Levantar los Servicios**

    Usa Docker Compose para construir la imagen de la aplicación y levantar todos los servicios definidos en el archivo `docker-compose.yml`:

    ```bash
    docker compose up -d
    ```

    Esto iniciará todos los servicios necesarios para tu aplicación en modo "detach" (en segundo plano).

### Acceder a la Aplicación

- La aplicación estará disponible en [http://localhost:8080](http://localhost:8080).
- La base de datos MySQL estará disponible en el puerto 3308.
- La interfaz de MailDev estará disponible en [http://localhost:1080](http://localhost:1080) y su servidor SMTP en el puerto 1025.

## Detener los Servicios
Para detener y eliminar todos los contenedores, redes y volúmenes definidos en el archivo `docker-compose.yml`, usa:
    
```bash
docker compose down
```
## Pruebas con Postman

Para facilitar la prueba de nuestra API, hemos creado una colección de Postman. Puedes descargarla [aquí](postman/SecureLogin-REST-API.postman_collection.json).

### Ejemplo de Prueba en Postman

Aquí se muestra una captura de una solicitud de autenticación en Postman:

![Prueba en Postman](ruta/a/la/imagen.png)

## Documentación con Swagger

Nuestra API está documentada con Swagger. Puedes acceder a la documentación interactiva en la siguiente URL cuando la aplicación esté corriendo: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
### Cómo Importar la Colección en Postman

1. Abre Postman.
2. Haz clic en el botón **Import** en la esquina superior izquierda.
3. Selecciona **Upload Files**.
4. Navega al archivo JSON de la colección que descargaste y selecciónalo.
5. La colección se importará a Postman y aparecerá en tu panel de colecciones.

### Ejemplo de la Interfaz de Swagger

A continuación, se muestra un ejemplo de cómo luce la interfaz de Swagger:

![Interfaz de Swagger](ruta/a/la/imagen.png)        
## Desafíos y Aprendizajes

- **Integración de Docker**: Configurar Docker y Docker Compose para crear un entorno de desarrollo completo y fácilmente desplegable.
- **Implementación de JWT**: Profundización en la seguridad web mediante JWT.
- **Validación de Correo Electrónico**: Mejora en la integración de servicios externos.

## Próximos Pasos

- **Autenticación OAuth 2.0**: Implementar un sistema de autenticación basado en OAuth 2.0 para mejorar la seguridad y permitir la integración con servicios de terceros.
- **Auditoría de Actividad**: Implementar un sistema de auditoría para registrar y revisar acciones importantes realizadas por los usuarios. Esto incluirá la integración de registros de eventos críticos en la aplicación para mejorar la seguridad y el cumplimiento normativo.
- **Integración con Frontend**: Conectar la aplicación con un frontend desarrollado en ReactJS para ofrecer una interfaz de usuario moderna y dinámica.

## Contribuciones

Si deseas contribuir al proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama para tu feature o bug fix (`git checkout -b feature/mi-feature`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añadida nueva feature'`).
4. Push a la rama (`git push origin feature/mi-feature`).
5. Abre un pull request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## Autores

- Jesús Pablo Avila Olivar - Autor principal [LinkedIn](https:/https://www.linkedin.com/in/pablo-avila-olivar/)

## Enlaces Útiles

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Docker](https://docs.docker.com/)
- [Documentación de Docker Compose](https://docs.docker.com/compose/)
- [Documentación de Docker Compose](https://springdoc.org/#getting-started)
