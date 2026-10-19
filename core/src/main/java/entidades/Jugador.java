package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Jugador {
    // el spritesheet de cada personaje mide 381x381. Ignorando el pixel de sobra, cada frame mide 95 pixels.
public final int FRAME_ALTO = 256;
public final int FRAME_ANCHO = 256;

private static final float ANCHO = 2f;
private static final float ALTO = 2f;
private static final float VELOCIDAD = 8f;
    private static final float DURACION_FRAME = 0.12f;

    private static final int ABAJO = 0;
    private static final int IZQUIERDA = 1;
    private static final int DERECHA = 2;
    private static final int ARRIBA = 3;

    private final Vector2 posicion;
    private final Vector2 direccion = new Vector2();
    private final Texture textura;
    private final TextureRegion[][] frames;
    private TextureRegion frame;
    private int fila = ABAJO;
    private float tiempoAnim = 0f;

    public Jugador(float x, float y) {
        this.posicion = new Vector2(x, y);
        this.textura = new Texture("assets/Oliver_Spritesheet.png");
        this.frames = TextureRegion.split(textura, FRAME_ANCHO, FRAME_ALTO);
        this.frame = frames[ABAJO][0];
    }

    public void actualizar(float delta) {
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
            posicion.mulAdd(direccion, VELOCIDAD * delta);
        } else {
            tiempoAnim = 0f;
            columna = 0;
        }
        frame = frames[fila][columna];
    }

    public void dibujar(Batch batch) {
        batch.draw(
            frame,
            posicion.x - ANCHO / 2f,
            posicion.y - ALTO / 2f,
            ANCHO,
            ALTO
        );
    }

    public float getX() {
        return posicion.x;
    }
    public float getY() {
        return posicion.y;
    }
    public void dispose() {
        textura.dispose();
    }





}
