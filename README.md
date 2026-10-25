# App Comercio

Aplicación de consola desarrollada en Java 21 para el proyecto práctico final. El
objetivo es demostrar fundamentos de Java y programación orientada a objetos mediante
la gestión de productos, stock y el carrito de un cliente.

## Requisitos

- JDK 21 (Temurin recomendado).
- No se utilizan frameworks ni dependencias externas de ejecución.
- Las pruebas utilizan un arnés Java propio, sin JUnit ni librerías externas.

## Alcance final

Este repositorio se cierra como una entrega académica y de portfolio, no como una
aplicación comercial completa. El objetivo es demostrar que los conceptos indicados en
el enunciado están implementados de forma funcional, legible y verificable.

Se incluyen el modelo de productos, herencia, interfaces, polimorfismo, gestión de
stock, carrito, ordenación, entrada por consola, JavaDoc, pruebas Java puras y
documentación de ejecución.

Quedan fuera de alcance de forma intencionada:

- Spring, Spring Boot, API REST y aplicaciones web.
- Persistencia, bases de datos, autenticación y usuarios.
- Docker, despliegue cloud y configuración de producción.
- Nuevas funcionalidades de negocio no solicitadas en el PDF.

## Funcionalidad

- `Producto` implementa `GestionProductos`.
- `Perecedero` y `NoPerecedero` heredan de `Producto` y sobrescriben sus descuentos.
- `Cliente` implementa `GestionClientes`.
- `Persona` encapsula los datos del cliente.
- `ComercioUtils` crea el almacén, añade productos al carrito y muestra listados.
- El carrito se ordena por precio de mayor a menor.
- El almacén puede mostrarse ordenado por código o en su orden actual.
- La compra actualiza los descuentos y el stock.
- Cuando no hay stock, se repone automáticamente en cinco unidades, según el enunciado.
- Las entradas no numéricas y las respuestas distintas de `s`/`n` se solicitan de nuevo.
- Se rechazan precios y stocks negativos para evitar estados inválidos.

## Ejecutar

Compilar y ejecutar manualmente con Java puro:

```bash
mkdir -p out
javac --release 21 -encoding UTF-8 -d out $(find src/main/java -name '*.java')
java -cp out es.accenture.appcomercio.AppComercio
```

Para compilar y ejecutar las pruebas Java:

```bash
mkdir -p out
javac --release 21 -encoding UTF-8 -d out $(find src/main/java src/test/java -name '*.java')
java -cp out es.accenture.appcomercio.AppComercioTest
```

Las pruebas comprueban los descuentos, los límites del stock, la reposición automática,
la validación de entradas, el orden del carrito y el cálculo del total.

También se puede compilar directamente con JDK 21:

```bash
mkdir -p out
javac -d out $(find src/main/java -name '*.java')
java -cp out es.accenture.appcomercio.AppComercio
```

Durante la ejecución se introducen códigos de producto y `s` para continuar o `n` para
terminar la compra.

## Estructura

```text
src/main/java/es/accenture/appcomercio/
├── AppComercio.java
├── Cliente.java
├── ComercioUtils.java
├── GestionClientes.java
├── GestionProductos.java
├── NoPerecedero.java
├── Perecedero.java
├── Persona.java
└── Producto.java
```

## Especificación seguida

La implementación sigue el proyecto práctico final de Java compartido en los materiales
de la entrega. El repositorio contiene `AppComercio`, una aplicación Java de consola sin
Spring.

## Convenciones

- El código fuente incluye JavaDoc en clases y métodos públicos.
- La implementación se mantiene en Java 21, tal como requiere el contexto entregado.
- No deben subirse `target/`, `out/`, archivos de IDE ni binarios generados.

## Criterio de cierre

La implementación se considerará finalizada cuando:

1. El flujo completo de compra funcione desde consola.
2. Las entradas inválidas y los casos de stock estén controlados.
3. Las pruebas Java puras pasen sin dependencias externas.
4. Las clases y métodos públicos relevantes estén documentados con JavaDoc.
5. La compilación y las pruebas manuales con `javac` y `java` terminen correctamente.
6. El README permita a una persona externa compilar, ejecutar y entender el alcance.
