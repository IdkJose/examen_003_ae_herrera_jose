# examen_003_ae_herrera_jose

Microservicio Inventory Config Service con Spring Boot (Kotlin), seguridad con AWS Cognito y auditoría de cambios.

## Requisitos

- Java 17
- PostgreSQL (u otra BD indicada en clase)

## Variables de entorno

- DB_URL: URL JDBC de la base de datos
- DB_USERNAME: usuario de la base de datos
- DB_PASSWORD: contraseña de la base de datos
- COGNITO_ISSUER_URI: issuer-uri del User Pool de Cognito
- SERVER_PORT (opcional): puerto del servicio, por defecto 8080

## Cómo ejecutar

1) Configurar variables de entorno.
2) Ejecutar:

```
./gradlew bootRun
```

## Seguridad

- Recurso protegido con JWT (Resource Server).
- Claim usado para updatedBy: sub
- Claim usado para rol ADMIN: cognito:groups (debe incluir ADMIN)

## Endpoints

### Público (sin token)

- GET /public/health

### Autenticado (JWT válido)

- GET /api/rules
- GET /api/rules/{id}

### Solo ADMIN

- POST /api/rules
- PUT /api/rules/{id}
- PATCH /api/rules/{id}/toggle
- DELETE /api/rules/{id}

## Auditoría

Cada operación de escritura extrae el userId desde el JWT y lo persiste en updatedBy.
Si el token no contiene el claim sub, la operación falla con error controlado.

## Requests de ejemplo

Ver archivo [requests.http](requests.http).