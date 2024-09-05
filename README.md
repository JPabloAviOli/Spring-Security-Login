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

### Cómo Importar la Colección en Postman

1. Abre Postman.
2. Haz clic en el botón **Import** en la esquina superior izquierda.
3. Selecciona **Upload Files**.
4. Navega al archivo JSON de la colección que descargaste y selecciónalo.
5. La colección se importará a Postman y aparecerá en tu panel de colecciones.
    
### Ejemplos de Prueba en Postman

### Solicitud Exitosa para Registrar un Usuario

Aquí se muestra una captura de una solicitud que fue exitosa para registrar un usuario:

![registro-usuario](https://github.com/user-attachments/assets/52a67607-c223-46c6-b21c-73536c393a54)

### MailDev
Conectate al servidor de maildev en el puerto [1080](http://localhost:1080/) para verificar que te ha llegado el token de validacion de email: 

![email-token](https://github.com/user-attachments/assets/36276fba-bfe9-4cac-92c7-2c903325bad5)

### Activar cuenta

Ahora podemos probar el token para validar nuestra cuenta con el token recibido en nuestro email:

![activar-cuenta](https://github.com/user-attachments/assets/8e9cd3bc-64e4-44cd-aad3-53d24080ff93)


## Documentación con Swagger

Nuestra API está documentada con Swagger. Puedes acceder a la documentación interactiva a través de la siguiente URL cuando la aplicación esté en ejecución: [Swagger UI](http://localhost:8080/api/v1/swagger-ui.html).

A continuación, se muestra un ejemplo de cómo luce la interfaz de Swagger:

![interfaz-swagger](https://github.com/user-attachments/assets/bf6dd555-f36d-47e8-b4be-1cfabcb65009)

### Prueba de Autenticación de usuario

En esta prueba, enviaremos una contraseña incorrecta para verificar cómo se manejan los errores:
![autenticar-error](https://github.com/user-attachments/assets/f064da70-cf00-4c88-af88-b0e4d303ba07)

### Mensaje de Error Personalizado

En caso de un error de autenticación (como el ingreso de una contraseña incorrecta), el sistema responde con un mensaje de error personalizado. Esto asegura que el usuario reciba información clara sobre el problema y cómo puede resolverlo.

![mensaje-error](https://github.com/user-attachments/assets/57f07d99-4839-4e94-850b-d6bc6e243038)

    
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
