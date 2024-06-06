Simulador de Créditos Universitarios - Cookie Clicker

Este proyecto es una simulación del popular juego Cookie Clicker, pero adaptado al contexto académico. En lugar de hacer clic para obtener galletas, los jugadores hacen clic para acumular créditos universitarios. El objetivo es obtener la mayor cantidad posible de créditos y desbloquear mejoras para aumentar la cantidad de créditos obtenidos por clic o automáticamente con el tiempo.
Tecnologías Utilizadas

    Lenguaje: Java
    Patrón de Diseño: Modelo-Vista-Controlador (MVC)
    Interfaz Gráfica: Swing
    Base de Datos: MySQL

Características Principales

    Acumulación de Créditos:
        Haz clic para ganar créditos universitarios.
        Visualización en tiempo real del progreso.

    Mejoras y Upgrades:
        Desbloquea mejoras que incrementan los créditos por clic.
        Obtén mejoras automáticas que generan créditos con el tiempo.

    Interfaz de Usuario Atractiva:
        Interfaz gráfica intuitiva y visualmente atractiva.
        Indicadores y gráficos para mostrar tu progreso.

    Persistencia de Datos:
        Guarda y carga tu progreso para continuar donde lo dejaste.

Instalación y Configuración
Prerrequisitos

    Instalar XAMPP:
        XAMPP es necesario para tener PhpMyAdmin y gestionar la base de datos.
        Puedes descargarlo desde aquí.

Configuración de la Base de Datos

    Iniciar XAMPP:
        Abre XAMPP y enciende el servidor Apache y MySQL.

    Crear la Base de Datos:
        Abre PhpMyAdmin desde XAMPP.
        En el menú de la izquierda, selecciona "Nueva" para crear una nueva base de datos.
        Nombra la base de datos como dpoo-cookieclicker y deja el collation como utf8mb4_general_ci.

    Importar el Esquema de la Base de Datos:
        Selecciona la base de datos dpoo-cookieclicker.
        Ve a la pestaña SQL e importa el archivo dpoo-cookieclicker.sql ubicado en la carpeta BaseDeDades.

    Configurar Usuario de la Base de Datos:
        En la pestaña de Privilegios, añade un nuevo usuario con los siguientes detalles:
            Nombre de usuario: cc3
            Contraseña: cc3
        Otorga todos los privilegios globales al usuario cc3.

Configuración del Proyecto en IntelliJ IDEA

    Abrir el Proyecto:
        Abre el proyecto en IntelliJ IDEA.

    Configurar config.json:
        Navega a la carpeta files y abre config.json.
        Asegúrate de que los campos dbname, username, y password coinciden con los datos de la base de datos configurada.

    Añadir Librerías Externas:
        Añade las siguientes librerías al proyecto:
            gson-2.10.1.jar
            jfreechart-1.5.4.jar
            mysql-connector-j-8.3.0.jar
        Ve a File -> Project Structure -> Modules y añade las librerías, luego aplica los cambios y presiona "OK".

Ejecutar el Proyecto

    Una vez completados los pasos anteriores, cierra la pestaña de configuración y haz clic en "Run" para ejecutar el proyecto.

Contribuciones

¡Contribuciones son bienvenidas! Por favor, sigue estos pasos:

    Haz un fork del proyecto.
    Crea una rama de características (git checkout -b feature/nueva-caracteristica).
    Confirma tus cambios (git commit -m 'Agrega nueva característica').
    Haz un push a la rama (git push origin feature/nueva-caracteristica).
    Abre un Pull Request.

Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para obtener más información.
Contacto

Armand López - LinkedIn - Correo Electrónico
