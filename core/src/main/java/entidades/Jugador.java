package entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Jugador {
    // el spritesheet de cada personaje mide 381x381. Ignorando el pixel de sobra, cada frame mide 95 pixels.
public final int FRAME_ALTO = 143;
public final int FRAME_ANCHO = 143;

private static final float ANCHO = 2f;
private static final float ALTO = 2f;
private static final float VELOCIDAD = 8f;

    private final Vector2 posicion;
    private final Vector2 direccion = new Vector2();
    private final Texture textura;
    private final TextureRegion frame;

    public Jugador(float x, float y) {
        this.posicion = new Vector2(x, y);
        this.textura = new Texture("assets/Oliver_Spritesheet.jpg");
        this.frame = new TextureRegion(textura, 0, 0, FRAME_ALTO, FRAME_ANCHO);
    }

    public void actualizar(float delta) {
        direccion.set(0f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) direccion.y += 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) direccion.y -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) direccion.x -= 1f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) direccion.x += 1f;

        if (direccion.len2() > 0f) {
            direccion.nor();
            posicion.mulAdd(direccion, VELOCIDAD * delta);
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
