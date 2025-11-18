
# Descripción del Proyecto



## Microservicio de usuarios 

📌 **Descripción General**

El User Microservice es responsable de la gestión de usuarios dentro de la plataforma.
Incluye todas las funcionalidades relacionadas con:

* Registro de usuarios

* Autenticación (login)

* Emisión y validación de tokens JWT

* Actualización de tokens (refresh)

* Gestión de roles

* Seguridad con Spring Security

🧾 Registro de Usuario – POST /api/auth/register

Cuerpo de la petición
```
{
    "name": "user",
    "email": "test@hotmail.com",
    "password": "mypassword",
    "phone": "+573333333",
    "address": "cra42123 12# 14 20",
    "roles": ["admin", "client"],
    "client": {
        "customerName": "customer",
        "customerAddress": "cra42123 12# 14 20",
    }
}
```

Flujo para registrar usuarios
![img_1.png](img_1.png)
