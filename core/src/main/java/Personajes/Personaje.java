package Personajes;
import armas.Arma;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected final float velocidad;
    private final Vector2 direccion = new Vector2();
    private final Texture textura;
    private final TextureRegion[][] frames;
    private TextureRegion frame;
    private int fila = ABAJO;
    private float tiempoAnim = 0f;

    public Personaje(float x, float y, String rutaSheet, int frameAncho, int frameAlto, float velocidad) {
        this.posicion = new Vector2(x, y);
        this.velocidad = velocidad;
        this.textura = new Texture(rutaSheet);
        this.frames = TextureRegion.split(textura, frameAncho, frameAlto);
        this.frame = frames[ABAJO][0];
    }

    public void actualizar(float delta, float mouseX, float mouseY) {
        direccion.set(0f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direccion.y += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) direccion.y -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direccion.x -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) direccion.x += 1f;
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
            posicion.mulAdd(direccion, velocidad * delta);
        } else {
            tiempoAnim = 0f;
            columna = 0;
        }

        frame = frames[fila][columna];
        if (arma != null) {
            arma.actualizar(delta, posicion.x, posicion.y, mouseX, mouseY);
        }
    }
    public void dibujar(Batch batch) {
        batch.draw(
            frame,
            posicion.x - ANCHO / 2f,
            posicion.y - ALTO / 2f,
            ANCHO,
            ALTO
        );
        if (arma != null) {
            arma.dibujar(batch);
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
    public void dispose() {
        textura.dispose();
        if (arma != null) {
            arma.dispose();
        }
    }
}
