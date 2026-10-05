# Prueba inditex

## Technologies

- Java 17
- Spring Boot 3.3.4
- Maven 3.10.0

---

## Architecture

Arquitectura hexagonal (Puertos y Adaptadores):

```
com.gfx
│
├── common/
│   ├── exception/          ← PriceNotFoundException
│   └── mapper/             ← mapea entity ↔ domain ↔ response
│
├── domain/
│   ├── model/              ← record Price (sin dependencias de framework)
│   └── repository/         ← puerto de salida (interfaz de dominio)
│
├── application/
│   ├── usecase/            ← puerto de entrada
│   └── service/            ← implementa el caso de uso, llama al puerto de dominio
│
└── infrastructure/
    ├── controller/         ← adaptador REST de entrada
    ├── dto/                ← como se genera con API first, no se implementa en este caso
    ├── persistence/
    │   ├── entity/         ← entidad JPA
    │   ├── PriceJpaRepository
    │   └── PricePersistenceAdapter  ← implementa el puerto de dominio
    └── openapi/            ← fuentes generadas (target/)
```

---

## Database

La tabla `PRICES` no tiene claves foráneas — es autocontenida. Columnas que podrían referenciar otras tablas en un modelo más amplio:

| Column       | Note |
|--------------|------|
| `BRAND_ID`   | Podría referenciar una tabla de marcas |
| `PRODUCT_ID` | Podría referenciar una tabla de productos |
| `PRICE_LIST` | Se consideró usar `Short` dado el número habitualmente bajo de tarifas, pero `Integer` evita conversiones innecesarias en Java |
| `CURR`       | Código de moneda. Podría ser un `ENUM` si la lista de monedas es cerrada |

---

## API First

El contrato REST está definido en `src/main/resources/openapi/prices-api.yaml`. La interfaz del controlador y los DTOs de respuesta se generan a partir de él en tiempo de compilación.

Compilar antes de ejecutar o importar en el IDE:

```bash
mvn compile
```

**Nota:** En `PriceResponse`, `price` se define con `type: number` sin `format`, por lo que el generador produce `BigDecimal` en lugar de `Double`.

---

## Notable POM decisions

- **springdoc-openapi** — usado para Swagger UI; no requiere configuración adicional más allá de la dependencia.
- **Lombok** — usado principalmente para la generación de constructores (`@RequiredArgsConstructor`, `@NoArgsConstructor`).

---

## REST Client (VS Code)

Algunos ejemplos de peticiones están disponibles en la carpeta `http/` y pueden ejecutarse con la extensión REST Client de VS Code.

---

## Docker

La aplicación corre en el puerto **25000** (sobreescribiendo el 8080 por defecto de Spring Boot).

Construir y ejecutar:

```bash
docker build -t prices-api .
docker run -p 25000:25000 prices-api
```