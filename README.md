
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



Flujo de proceso para Autententicar usuarios

![img_2.png](img_2.png)


Flujo para solicitar un nuevo refresh token
![img_3.png](img_3.png)


# Microservicio de inventario
El **Inventory Microservice** es el responsable de todo lo relacionado con **productos** y **stock** en el e-commerce:

- Gestionar **marcas** (`Brand`).
- Gestionar **categorías** (`Category`).
- Gestionar **productos** (`Product`) con:
    - `stock`
    - `reservedStock`
    - `availableStock`
    - `minStock` (stock mínimo para alertas)
- Exponer endpoints REST para CRUD y operaciones de stock.
- Reaccionar a eventos de otros microservicios:
    - **Order**: cuando una orden es confirmada, se descuenta el stock.
    - **Cart/Order cancelado**: se libera el stock reservado.

Todo esto está implementado usando **Clean Architecture** y **DDD**
La arquitectura se organiza en tres capas principales:

- `domain`
- `application`
- `infrastructure`

La idea central es:

> **El dominio manda**.  
> La lógica de negocio vive en `domain` y no sabe nada de frameworks ni tecnologías externas.  
> `application` orquesta casos de uso.  
> `infrastructure` adapta el mundo externo al dominio.

### Diagrama simplificado (Clean Architecture)

```mermaid
flowchart LR
    subgraph Domain
        D1[Entities: Product, Brand, Category]
        D2[UseCases: CreateProductUseCase, ReserveStockUseCase, ...]
        D3[Gateways: ProductGateway, BrandGateway, CategoryGateway]
        D4[Exceptions: BusinessException, ValidationException, NotFoundException]
    end

    subgraph Application
        A1[InventoryConfig: Beans y wiring]
        A2[EventConsumer: interfaz para eventos]
    end

    subgraph Infrastructure
        I1[Controllers REST]
        I2[Repositories reactivos (R2DBC)]
        I3[Entities DB]
        I4[RabbitMQ Config + Listeners]
        I5[DTOs y Mappers]
    end

    I1 --> A2
    I4 --> A2

    A1 --> D2
    D2 --> D3

    D3 --> I2
    I2 --> I3