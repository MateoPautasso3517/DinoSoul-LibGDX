package personajes;

import armas.Arma;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Personaje {
    private static final float ANCHO = 2f;
    private static final float ALTO = 2f;
    private static final float DURACION_FRAME = 0.12f;
    private static final int ABAJO = 0;
    private static final int IZQUIERDA = 1;
    private static final int DERECHA = 2;
    private static final int ARRIBA = 3;
    protected final Vector2 posicion;
    protected Arma arma;
    protected float velocidad;
    private final Vector2 direccion = new Vector2();
    private final Texture textura;
    private final TextureRegion[][] frames;
    private TextureRegion frame;
    private int fila = ABAJO;
    private float tiempoAnim = 0f;
    private int vida;
    private int vidaMax;
    private float bonusVelocidad = 0f;
    private float tiempoBonus = 0f;
    private float danio;
    private float bonusDanio = 0f;
    private float tiempoBonusDanio = 0f;
    private Color tinte = Color.WHITE;

    public Personaje(float x, float y, String rutaSheet, int frameAncho, int frameAlto, float velocidad, int vidaMax, int danio) {
        this.posicion = new Vector2(x, y);
        this.velocidad = velocidad;
        this.textura = new Texture(rutaSheet);
        this.frames = TextureRegion.split(textura, frameAncho, frameAlto);
        this.frame = frames[ABAJO][0];
        this.vidaMax = vidaMax;
        this.vida = 70;
        this.danio = danio;
    }

    public void actualizar(float delta, float mouseX, float mouseY, TiledMapTileLayer colisiones) {
        direccion.set(0f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) direccion.y += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) direccion.y -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) direccion.x -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) direccion.x += 1f;
        int columna;

        if (direccion.len2() > 0f) {
            if (Math.abs(direccion.x) > Math.abs(direccion.y)) {
                fila = direccion.x > 0f ? DERECHA : IZQUIERDA;
            } else {
                fila = direccion.y > 0f ? ARRIBA : ABAJO;
            }
            tiempoAnim += delta;
            columna = (int) (tiempoAnim / DURACION_FRAME) % 4;
            direccion.nor();
            float viejaX = posicion.x;
            posicion.x += direccion.x * velocidad * delta;

            if (hayColision(colisiones, posicion.x, posicion.y)) {
                posicion.x = viejaX;
            }
            float viejaY = posicion.y;
            posicion.y += direccion.y * velocidad * delta;

            if (hayColision(colisiones, posicion.x, posicion.y)) {
                posicion.y = viejaY;
            }
        } else {
            tiempoAnim = 0f;
            columna = 0;
        }

        frame = frames[fila][columna];
        if (arma != null) {
            arma.actualizar(delta, posicion.x, posicion.y, mouseX, mouseY);
        }

        if (tiempoBonus > 0f) {
            tiempoBonus -= delta;
            if (tiempoBonus <= 0f) {
                velocidad -= bonusVelocidad;
                bonusVelocidad = 0f;
                tiempoBonus = 0f;
                setTinte(Color.WHITE);
            }
        }

        if (tiempoBonusDanio > 0f) {
            tiempoBonusDanio -= delta;
            if (tiempoBonusDanio <= 0f) {
                danio -= bonusDanio - 0.01;
                bonusDanio = 0f;
                tiempoBonusDanio = 0f;
                setTinte(Color.WHITE);
            }
        }

    }

    private boolean hayColision(TiledMapTileLayer capa, float x, float y) {
        int tileX = (int) x;
        int tileY = (int) y;

        if (tileX < 0 || tileY < 0
            || tileX >= capa.getWidth()
            || tileY >= capa.getHeight()) {
            return true;
        }

        return capa.getCell(tileX, tileY) != null;
    }

    public void dibujar(Batch batch) {
        batch.setColor(tinte);
        batch.draw(frame, posicion.x - (ANCHO / 2f), posicion.y - (ALTO / 2f), ANCHO, ALTO);
        batch.setColor(Color.WHITE);
        if (arma != null) {
            arma.dibujar(batch);
        }
    }

    public Rectangle obtenerLimites() {
        return new Rectangle(
            posicion.x - (ANCHO / 2f),
            posicion.y - (ALTO / 2f),
            ANCHO,
            ALTO
        );
    }

    public void agregarVida(int cantidad) {
        this.vida += cantidad;
        if (this.vida > this.vidaMax) {
            this.vida = this.vidaMax;
        }
    }

    public void agregarVelocidad(float cantidad, float duracion) {
        velocidad -= bonusVelocidad;
        bonusVelocidad = cantidad;
        velocidad += bonusVelocidad;
        tiempoBonus = duracion;
    }

    public void agregarDanio(float cantidad, float duracion) {
        danio -= bonusDanio;
        bonusDanio = cantidad;
        danio += bonusDanio;
        tiempoBonusDanio = duracion;
    }

    public void dispose() {
        textura.dispose();
        if (arma != null) {
            arma.dispose();
        }
    }

    public float getX() {
        return posicion.x;
    }

    public float getY() {
        return posicion.y;
    }

    public Arma getArma() {
        return arma;
    }

    public float getVelocidad() {return velocidad;}

    public float getDanio() {return danio;}

    public void setTinte(Color tinte) {
        this.tinte = tinte;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public int getVida() {
        return vida;
    }
}
