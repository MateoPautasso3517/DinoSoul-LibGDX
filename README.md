# DinoSoul

## Integrantes del grupo
Mateo Pautasso
Micaela Piccione

## Descripción del proyecto
un videojuego 2D top-down de supervivencia por oleadas desarrollado en libGDX. Está inspirado en “Vampire Survivors”. El jugador controla a Oliver M. Micovich y la historia gira alrededor de recuperar una parte de su alma que fue robada por Britany, su contraparte dinosaurio. El escenario es una playa tropical donde el jugador deberá sobrevivir a sus enemigos, recolectar objetos y derrotar al jefe final.

**Enlace a la Wiki del Proyecto (Propuesta Detallada):**
[Ver la Propuesta Completa del Proyecto aquí](https://github.com/MateoPautasso3517/DinoSoul-LibGDX/wiki)

## Tecnologías utilizadas

- Java 8 (compatibilidad del código fuente)
- JDK 21 (probado para ejecutar Gradle 9.6.1)
- Gradle 9.6.1
- LibGDX 1.14.2
- Plataforma objetivo: Escritorio

## Cómo compilar y ejecutar

### Clonar el repositorio

```bash
git clone https://github.com/MateoPautasso3517/DinoSoul-LibGDX.git
cd DinoSoul-LibGDX
```
### Ejecutar en Windows

```bash
gradlew.bat lwjgl3:run
```
### Ejecutar en Linux/macOS

```bash
chmod +x gradlew
./gradlew lwjgl3:run
```
## Requisitos

- Tener instalado JDK 21 (comprobado para Gradle 9.6.1).
- El proyecto compila el código fuente con compatibilidad Java 8.

## Base de datos

El proyecto tiene prevista la incorporación de una base de dato utilizando:

- Motor: MySQL
- Conectividad: JDBC
- Lenguaje SQL

**Actualmente esta funcionalidad aún no se encuentra implementada en el proyecto.**
Su incorporación está prevista para etapas posteriores del desarrollo.

### Tablas principales

#### Personajes

| Campo | Tipo | Clave |
|-------|------|-------|
| id_personaje | INT | PK |
| nombre | VARCHAR(50) | |
| vida | INT | |
| daño | INT | |
| velocidad | INT | |
| velocidad_ataque | FLOAT | |
| alcance | INT | |

#### Enemigos

| Campo | Tipo | Clave |
|-------|------|-------|
| id_enemigo | INT | PK |
| nombre | VARCHAR(50) | |
| vida | INT | |
| daño | INT | |
| velocidad | INT | |
| velocidad_ataque | FLOAT | |

#### Jefes

| Campo | Tipo | Clave |
|-------|------|-------|
| id_jefe | INT | PK |
| nombre | VARCHAR(50) | |
| vida | INT | |
| daño | INT | |
| velocidad | INT | |

#### Objetos

| Campo | Tipo | Clave |
|-------|------|-------|
| id_objeto | INT | PK |
| nombre | VARCHAR(50) | |
| tipo | VARCHAR(30) | |
| efecto | VARCHAR(100) | |

#### Partidas

| Campo | Tipo | Clave |
|-------|------|-------|
| id_partida | INT | PK |
| personaje_id | INT | FK |
| tiempo_supervivencia | INT | |
| enemigos_derrotados | INT | |
| daño_total | INT | |
| resultado | VARCHAR(20) | |

#### Estadísticas_Partida

| Campo | Tipo | Clave |
|-------|------|-------|
| id_estadistica | INT | PK |
| partida_id | INT | FK |
| experiencia | INT | |
| dificultad | VARCHAR(20) | |

### Relaciones

- Un personaje puede estar asociado a muchas partidas (1:N).
- Una partida posee un único personaje.
- Una partida posee un registro de estadísticas (1:1).

### Consultas previstas

Al iniciar el juego:

- Obtener las estadísticas base de personajes.
- Obtener las estadísticas de enemigos.
- Obtener las estadísticas de jefes.
- Obtener los atributos de los objetos.

Al abrir el menú principal:

- Consultar el mejor tiempo de supervivencia.
- Consultar la mayor cantidad de enemigos derrotados.

Al finalizar una partida:

- Registrar el tiempo de supervivencia.
- Registrar la cantidad de enemigos derrotados.
- Registrar el daño total realizado.
- Registrar el resultado de la partida.

Como mejora futura también se quiere almacenar:

- Dificultad seleccionada.
- Experiencia obtenida.
- Cantidad de enemigos derrotados por tipo.
- Registrar el personaje utilizado.
