# DuoSecreto — Juego de memoria por parejas
## Ingeniería en Sistemas | luisa bernal y erica cardenas

---ejecucion del juego ------
### para jugarlo y no ay imagenes cargadas por favor entra a administración y carga las imagenes que se encuentran en la carpeta datos dentro de imagenes.
----------------------------

## Requisitos
- Java 11 o superior (JRE o JDK)
- Maven 3.6+ (solo si quieres recompilar)

---

## Ejecutar el juego

```bash
java -jar DuoSecreto.jar
```

En Windows: doble clic en `DuoSecreto.jar` si Java está instalado.

---

## Recompilar desde fuentes (con Maven)

```bash
mvn package
java -jar target/DuoSecreto.jar
```

---

## Estructura del proyecto

```
DuoSecreto/
├── src/main/java/duosecreto/
│   ├── Main.java                        ← Punto de entrada
│   ├── dto/
│   │   ├── Dificultad.java              ← DTO serializable
│   │   └── Resultado.java               ← DTO serializable (top 10)
│   ├── model/
│   │   ├── Carta.java                   ← Entidad carta
│   │   ├── EstadoCarta.java             ← Enum: OCULTA/DESCUBIERTA/EMPAREJADA
│   │   ├── EstadoJuego.java             ← Puntaje y tiempo en curso
│   │   ├── Juego.java                   ← Lógica central (emparejamiento, timer)
│   │   └── Jugador.java                 ← Nickname, puntaje acumulado, partidas
│   ├── util/
│   │   ├── Estilos.java                 ← Paleta de colores y fuentes
│   │   ├── GestorDatos.java             ← Fachada de persistencia binaria
│   │   └── UtilidadArchivos.java        ← ObjectStream leer/guardar
│   └── ventana/
│       ├── VentanaMenu.java             ← Menú principal
│       ├── VentanaIngresoNickname.java  ← Nickname + selección dificultad
│       ├── VentanaJuego.java            ← Tablero de juego
│       └── VentanaAdministracion.java   ← Admin: dificultades, imágenes, top10
├── pom.xml
└── DuoSecreto.jar                       ← Ejecutable listo
```

Los datos se guardan automáticamente en la carpeta `datos/` junto al JAR.

