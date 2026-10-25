package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Arma {
    protected final Texture textura;
    protected final float ancho;
    protected final float alto;
    protected final float offsetX;
    protected final float offsetY;
    protected float x;
    protected float y;
    protected float angulo;
    protected TextureRegion region;
    protected boolean atacando = false;
    protected float tiempoAtaque = 0f;
    protected float avance = 0f;
    private static final float DURACION_ATAQUE = 0.18f;
    private static float distanciaAtaque = 0;
    private final Vector2 haciaMouse = new Vector2();

    public Arma(String rutaTextura, float ancho, float alto, float offsetX, float offsetY, float distanciaAtaque) {
        this.textura = new Texture(rutaTextura);
        this.ancho = ancho;
        this.alto = alto;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.region = new TextureRegion(textura);
        this.distanciaAtaque = distanciaAtaque;
    }

    public void actualizar(float delta, float xDuenio, float yDuenio, float mouseX, float mouseY) {
        this.x = xDuenio + offsetX;
        this.y = yDuenio + offsetY;
        this.angulo = MathUtils.atan2(mouseY - y, mouseX - x) * MathUtils.radiansToDegrees - 90f;

        if (!atacando && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            atacando = true;
            tiempoAtaque = 0f;
        }
        avance = 0f;
        if (atacando) {
            tiempoAtaque += delta;
            avance = distanciaAtaque;
            if (tiempoAtaque >= DURACION_ATAQUE) {
                atacando = false;
                avance = 0f;
            }
        }
        this.x = xDuenio + offsetX;
        this.y = yDuenio + offsetY;
        haciaMouse.set(mouseX - x, mouseY - y);
        if (haciaMouse.len2() > 0f) {
            haciaMouse.nor();
            this.x += haciaMouse.x * avance;
            this.y += haciaMouse.y * avance;
        }

    }

    public void dibujar(Batch batch) {
        float origenX = ancho / 2f;
        float origenY = 0f;
        batch.draw(
            region,
            x - origenX,
            y - origenY,
            origenX,
            origenY,
            ancho,
            alto,
            1f,
            1f,
            angulo
        );
    }

    public void dispose() {
        textura.dispose();
    }
}
