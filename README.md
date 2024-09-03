# SecureLoginApp

## Descripción

SecureLoginApp es un sistema de autenticación que gestiona el inicio de sesión de usuarios, el registro y la seguridad de las cuentas. Utiliza JSON Web Tokens (JWT) para garantizar el acceso seguro, validación de correo electrónico para la activación de cuentas, y funciones para cambiar y restablecer contraseñas. Este proyecto demuestra mis habilidades en desarrollo backend con Spring Boot y seguridad de aplicaciones.

## Características Principales

- **Autenticación Segura**: Implementación de JWT para proteger las rutas y gestionar sesiones de usuario mediante roles.
- **Registro y Activación de Cuentas**: Verificación de cuenta a través de correo electrónico para garantizar la autenticidad del usuario.
- **Gestión de Contraseñas**: Funcionalidades para cambiar y restablecer contraseñas, mejorando la experiencia de usuario y la seguridad.
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

### Clonar el Repositorio y Usar Docker Compose

1. **Clonar el Repositorio**

    ```bash
    git clone https://github.com/tuusuario/SecureLoginApp.git
    ```

2. **Navegar al Directorio del Proyecto**

    ```bash
    cd SecureLoginApp
    ```

3. **Configurar Variables de Entorno**

    Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

    ```env
    MYSQL_ROOT_PASSWORD=your_root_password
    MYSQL_DATABASE=your_database_name
    MYSQL_USER=your_username
    MYSQL_PASSWORD=your_password
    ```

4. **Construir y Levantar los Servicios**

    Usa Docker Compose para construir la imagen de la aplicación y levantar todos los servicios definidos en el archivo `docker-compose.yml`:

    ```bash
    docker-compose up -d
    ```

    Esto iniciará todos los servicios necesarios para tu aplicación en modo "detach" (en segundo plano).

### Descargar la Imagen de Docker Hub

1. **Descargar la Imagen**

    ```bash
    docker pull <tu-usuario>/secure-login-app:latest
    ```

2. **Ejecutar la Imagen**

    Después de descargar la imagen, puedes ejecutar un contenedor basado en ella con el siguiente comando:

    ```bash
    docker run -d -p 8080:8080 --name secure-login-app <tu-usuario>/secure-login-app:latest
    ```

## Acceder a la Aplicación

- La aplicación estará disponible en [http://localhost:8080](http://localhost:8080).
- La base de datos MySQL estará disponible en el puerto 3308.
- La interfaz de MailDev estará disponible en [http://localhost:1080](http://localhost:1080) y su servidor SMTP en el puerto 1025.

## Detener los Servicios

Para detener y eliminar todos los contenedores, redes y volúmenes definidos en el archivo `docker-compose.yml`, usa:

```bash
docker-compose down
