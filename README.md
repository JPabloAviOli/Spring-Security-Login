# SecureLoginApp

## Descripción

SecureLoginApp es una solución de autenticación robusta y segura que gestiona el inicio de sesión de usuarios, el registro y la seguridad de las cuentas. Utiliza JSON Web Tokens (JWT) para garantizar el acceso seguro, validación de correo electrónico para la activación de cuentas, y funciones para cambiar y restablecer contraseñas. Este proyecto demuestra mis habilidades en desarrollo backend con Spring Boot y seguridad de aplicaciones.

## Características Principales

- **Autenticación Segura**: Implementación de JWT para proteger rutas y gestionar sesiones mediante roles.
- **Registro y Activación de Cuentas**: Verificación de cuentas a través de correo electrónico para asegurar la autenticidad del usuario.
- **Gestión de Contraseñas**: Funcionalidades para cambiar y restablecer contraseñas, mejorando la experiencia y seguridad.
- **Manejo de Errores Basado en Aspectos**: Uso de `@RestControllerAdvice` para un manejo centralizado y uniforme de excepciones.
- **Arquitectura Sólida**: Uso del patrón Builder para DTOs, garantizando un código limpio y mantenible.
- **Documentación de API**: Integración con SpringDoc OpenAPI para documentar y probar APIs de manera sencilla.
- **Contenedorización**: Uso de Docker y Docker Compose para una configuración y despliegue simplificados.

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

### Configuración del Entorno

- **Docker Daemon**: Asegúrate de que Docker Daemon (dockerd) esté instalado y en funcionamiento en tu máquina. Este componente es esencial para la ejecución de contenedores Docker y se encarga de las operaciones relacionadas con los contenedores.

- **Docker Desktop (para Windows y macOS)**: Si estás utilizando Windows o macOS, Docker Desktop es necesario para proporcionar una interfaz gráfica y facilitar la gestión de Docker. Docker Desktop incluye Docker Daemon y otros componentes necesarios para el funcionamiento de Docker en estos sistemas operativos.

[Docker Desktop - Página Principal](https://www.docker.com/products/docker-desktop)

## Ejecutar la Aplicación

### Clonar el Repositorio y Usar Docker Compose

Clona el repositorio y usa Docker Compose para construir y gestionar los servicios, sigue estos pasos:

1. **Clonar el Repositorio**

    ```bash
    git clone https://github.com/JPabloAviOli/Spring-Security-Login.git
    ```

2. **Navegar al Directorio del Proyecto o abrir en el IDE de tu preferencia**

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

5. **Acceder a la Aplicación**

- La aplicación estará disponible en [http://localhost:8080](http://localhost:8080).
- La base de datos MySQL estará disponible en el puerto 3308.
- La interfaz de MailDev estará disponible en [http://localhost:1080](http://localhost:1080) y su servidor SMTP en el puerto 1025.

6. **Detener los Servicios**
   
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

- **Registro de Usuario Exitoso**: Ejemplo de solicitud exitosa para registrar un usuario.

![registro-usuario](https://github.com/user-attachments/assets/52a67607-c223-46c6-b21c-73536c393a54)

- **MailDev**: Conéctate al servidor de MailDev en el puerto [1080](http://localhost:1080/) para verificar el token de validación de correo electrónico.

![email-token](https://github.com/user-attachments/assets/36276fba-bfe9-4cac-92c7-2c903325bad5)

- **Activación de Cuenta**: Usa el token recibido por correo electrónico para validar la cuenta.

![activar-cuenta](https://github.com/user-attachments/assets/8e9cd3bc-64e4-44cd-aad3-53d24080ff93)

## Documentación con Swagger

- Nuestra API está documentada con Swagger. Puedes acceder a la documentación interactiva a través de la siguiente URL cuando la aplicación esté en ejecución: [Swagger UI](http://localhost:8080/api/v1/swagger-ui.html).

- A continuación, se muestra un ejemplo de cómo luce la interfaz de Swagger:

![interfaz-swagger](https://github.com/user-attachments/assets/bf6dd555-f36d-47e8-b4be-1cfabcb65009)

### Prueba de Autenticación

- En esta prueba, enviaremos una contraseña incorrecta para verificar cómo se manejan los errores:

![autenticar-error](https://github.com/user-attachments/assets/f064da70-cf00-4c88-af88-b0e4d303ba07)

### Mensaje de Error Personalizado

- En caso de un error de autenticación (como el ingreso de una contraseña incorrecta), el sistema responde con un mensaje de error personalizado. Esto asegura que el usuario reciba información clara sobre el problema y cómo puede resolverlo.

![mensaje-error](https://github.com/user-attachments/assets/57f07d99-4839-4e94-850b-d6bc6e243038)

### Prueba de autenticación exitosa

- En esta prueba, enviaremos las credenciales correctas para autenticarnos como un usuario válido de la aplicación:

![autenticar-exito](https://github.com/user-attachments/assets/2fa46a78-4341-4b11-aa2a-aea852691748)

- Al autenticarnos exitosamente, recibimos nuestro JSON Web Token (JWT), el cual utilizaremos para acceder a las rutas protegidas de la aplicación:

![mensaje-ok](https://github.com/user-attachments/assets/f85f40bf-fdcf-480d-96b7-0b2269ceab89)

### Prueba de permisos que tiene el usuario

1. **Agrega el JWT recibido en la sección de "Authorize" en Swagger, ya que los endpoints protegidos requieren el JSON Web Token que se genera para cada usuario**:

![authorize](https://github.com/user-attachments/assets/b1c428d8-695a-4fc2-bd7d-6e8d0ca6f326)

2. **Si todo sale bien, deberías ver un mensaje exitoso que diga "Test successfully"**:

![test-successfully](https://github.com/user-attachments/assets/3f406115-e173-4162-ba2a-03e707d8e180)

### Proceso de Recuperación de Contraseña

1. **Ingreso del Correo Electrónico Asociado:**
- Introduce el correo electrónico del usuario para iniciar el proceso de recuperación de la contraseña.
     
![olvide-contra](https://github.com/user-attachments/assets/0f312e7d-4ee8-4f33-9115-db198829188d)

2. **Envío de Token de Recuperación:**
- Si el correo electrónico está registrado, se enviará un token de verificación al correo proporcionado.

![token](https://github.com/user-attachments/assets/6f9f9966-cfbf-42b9-bec3-a155f53da74a)
   
3. **Validación del Token y Establecimiento de Nueva Contraseña:**
- Introduce el token recibido junto con la nueva contraseña. Si el token es incorrecto, se mostrará un mensaje de error.

![error-token](https://github.com/user-attachments/assets/0a562da7-3751-435a-a202-372e10b595b3)

5. **Confirmación de Restablecimiento Exitoso:**
- Si todo el proceso es exitoso, se recibirá una respuesta con el estado 200 OK, confirmando el restablecimiento de la contraseña.

![respuesta-exitosa](https://github.com/user-attachments/assets/fd5cf2f3-ace0-4c9d-a209-c55dfe9cae31)

### Proceso para cambiar contraseña de un usuario autenticado

- **Agrega el Encabezado de Autenticación**

![bearer-token](https://github.com/user-attachments/assets/f664990b-1e12-4fdc-bc6d-6754d67d2c71)

- **Envía la solicitud, si todo sale bien recibiras una respuesta 200 OK**:

![cambiar-contra](https://github.com/user-attachments/assets/1fbdce71-b87e-49a9-803d-c04795fa85fd)

### Proceso logout
- Descripción: El método logout invalida el token de sesión del usuario. Siempre devuelve un código 200 OK para indicar que la solicitud ha sido procesada, independientemente del estado del token.

![logout](https://github.com/user-attachments/assets/beefeb89-3ea9-446f-a9e6-c0141a7706e3)

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

## Autor

- **Jesús Pablo Avila Olivar** - Autor principal [LinkedIn](https://www.linkedin.com/in/pablo-avila-olivar/)

## Enlaces Útiles

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Docker](https://docs.docker.com/)
- [Documentación de Docker Compose](https://docs.docker.com/compose/)
- [Documentación de SpringDoc](https://springdoc.org/#getting-started)
- [JWT](https://jwt.io/)


