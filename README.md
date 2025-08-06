# Fitneservice

**Fitneservice** es una plataforma integral de seguimiento y gestión de actividades físicas, desarrollada para la Hackathon Desafío Latam 2025. La solución está construida íntegramente sobre el ecosistema Oracle Academy, utilizando Oracle APEX como entorno único de desarrollo (frontend y backend) y Oracle Autonomous Database como motor de datos. Además, integra servicios de inteligencia artificial generativa y una app móvil en Flutter conectada a los servicios RESTful de APEX.

---

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Arquitectura](#arquitectura)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación y Despliegue](#instalación-y-despliegue)
- [Estructura de la Base de Datos](#estructura-de-la-base-de-datos)
- [APIs RESTful en APEX](#apis-restful-en-apex)
- [Integración de IA](#integración-de-ia)
- [App Móvil Flutter](#app-móvil-flutter)
- [Pruebas Unitarias y de Integración](#pruebas-unitarias-y-de-integración)
- [Documentación Técnica](#documentación-técnica)
- [Contribuciones](#contribuciones)
- [Contacto](#contacto)

---

## 📝 Descripción General

Fitneservice permite a los usuarios:
- Registrar y visualizar actividades físicas (correr, caminar, ciclismo, etc.).
- Consultar estadísticas, rankings y reportes personalizados.
- Acceder a recomendaciones inteligentes mediante IA generativa.
- Usar la app móvil para registrar actividades y consultar datos en tiempo real.
- Administrar su perfil y progreso desde cualquier dispositivo.

---

## 🏗️ Arquitectura

- **Frontend y Backend:** Oracle APEX (100% low-code, sin servicios externos).
- **Base de Datos:** Oracle Autonomous Database.
- **APIs RESTful:** Oracle REST Data Services (ORDS) expuestos desde APEX.
- **App móvil:** Flutter, conectada a los endpoints RESTful de APEX.
- **IA Generativa:** Integración vía RESTful Web Service desde APEX (ejemplo: OpenAI).

![Arquitectura](docs/arquitectura.png) <!-- Agrega tu diagrama aquí -->

---

## 🛠️ Tecnologías Utilizadas

- **Oracle APEX** (Application Express)
- **Oracle Autonomous Database**
- **Oracle REST Data Services (ORDS)**
- **PL/SQL**
- **Flutter (Dart)**
- **Figma** (prototipado UI/UX)
- **OpenAI API** (o similar, para IA generativa)
- **GitHub** (control de versiones)

---

## 🚀 Instalación y Despliegue

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/fitneservice.git
cd fitneservice
2. Crear la base de datos
Ingresa a Oracle APEX > SQL Workshop > SQL Scripts.
Ejecuta el script database/fitneservice.sql para crear tablas, secuencias y datos de ejemplo.
3. Crear el workspace y la app en Oracle APEX
Accede a Oracle APEX.
Crea un nuevo workspace (ej: FITNESERVICE).
Importa el archivo de la app (apex/fitneservice_app.sql) desde App Builder.
Configura los servicios RESTful en SQL Workshop > RESTful Services (puedes importar el módulo OpenAPI si lo tienes).
4. Configurar la integración de IA (opcional)
Ve a SQL Workshop > RESTful Services.
Crea un módulo para la API de IA (ejemplo: /ia/chat).
Agrega tu API Key de OpenAI o el servicio que uses en el handler PL/SQL.
5. Desplegar la app Flutter
Ve a la carpeta flutter_app/.
Instala dependencias:
bash
Copy
flutter pub get
Configura la URL base de la API en lib/config.dart.
Ejecuta la app:
bash
Copy
flutter run
🗄️ Estructura de la Base de Datos
USERS: Usuarios registrados.
ACTIVITIES: Actividades físicas.
TRACK_POINTS: Puntos GPS de cada actividad.
(Opcional) RANKINGS, LOGS, etc.
El script completo está en database/fitneservice.sql.

🌐 APIs RESTful en APEX
GET /users/: Listar usuarios.
POST /users/: Crear usuario.
GET /activities/: Listar actividades.
POST /activities/: Crear actividad.
GET /activities/:id/track-points: Obtener puntos GPS.
POST /ia/chat: Enviar mensaje a la IA y recibir respuesta.
Puedes probar los endpoints con Postman, cURL o desde la app Flutter.

🤖 Integración de IA
El módulo /ia/chat en APEX permite enviar preguntas y recibir respuestas de IA generativa (ejemplo: OpenAI).
El handler PL/SQL realiza la llamada REST y retorna la respuesta al frontend.
Ejemplo de uso: recomendaciones personalizadas, chatbot, análisis de texto.
📱 App Móvil Flutter
Consume los endpoints RESTful de APEX.
Permite registrar actividades, ver estadísticas, rankings y chatear con la IA.
El diseño está basado en el prototipo de Figma (/figma/).
🧪 Pruebas Unitarias y de Integración
APEX: Pruebas de procesos y lógica PL/SQL.
Flutter: Pruebas de widgets y lógica de negocio (flutter test).
APIs: Pruebas con Postman y scripts automatizados.
📚 Documentación Técnica
Manual de usuario
Guía de endpoints
Guía de despliegue
Prototipo Figma
🤝 Contribuciones
¡Contribuciones y sugerencias son bienvenidas!
Por favor, abre un issue o pull request en este repositorio.

📬 Contacto
Nombre: Benjamín Monasterio
Correo: b.monasterio@gmail.com
GitHub: github.com/tu-usuario
