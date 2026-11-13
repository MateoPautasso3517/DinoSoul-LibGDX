package armas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Arma {

    private static final float DURACION_ATAQUE = 0.18f;
    private static final float CORRECCION_ANGULO = -90f;

    private static Sound sonido;

    protected final Texture textura;
    protected final TextureRegion region;

    protected final float ancho;
    protected final float alto;

    protected final float offsetX;
    protected final float offsetY;

    protected float x;
    protected float y;
    protected float angulo;

    protected boolean atacando = false;

    protected float tiempoAtaque = 0f;
    protected float avance = 0f;

    private final float distanciaAtaque;

    private final Vector2 haciaMouse = new Vector2();

    public Arma(
        String rutaTextura,
        float ancho,
        float alto,
        float offsetX,
        float offsetY,
        float distanciaAtaque,
        String rutaSonido
    ) {
        this.textura = new Texture(rutaTextura);
        this.region = new TextureRegion(textura);

        this.ancho = ancho;
        this.alto = alto;

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.distanciaAtaque = distanciaAtaque;

        sonido = inicializarSonido(rutaSonido);
    }

    private Sound inicializarSonido(String rutaSonido) {
        if (sonido == null) {
            sonido = Gdx.audio.newSound(
                Gdx.files.internal(rutaSonido)
            );
        }
        return sonido;
    }

    public void actualizar(
        float delta,
        float xDuenio,
        float yDuenio,
        float mouseX,
        float mouseY
    ) {
        actualizarPosicion(xDuenio, yDuenio);

        actualizarOrientacion(mouseX, mouseY);

        actualizarAtaque(delta);

        actualizarAvance(mouseX, mouseY);
    }

    private void actualizarPosicion(float xDuenio, float yDuenio) {
        x = xDuenio + offsetX;
        y = yDuenio + offsetY;
    }

    private void actualizarOrientacion(float mouseX, float mouseY) {
        angulo = MathUtils.atan2(
            mouseY - y,
            mouseX - x
        ) * MathUtils.radiansToDegrees + CORRECCION_ANGULO;
    }

    private void actualizarAtaque(float delta) {
        if (!atacando && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            iniciarAtaque();
            sonido.play();
        }

        if (atacando) {
            tiempoAtaque += delta;

            if (tiempoAtaque >= DURACION_ATAQUE) {
                atacando = false;
            }
        }
    }

    private void iniciarAtaque() {
        atacando = true;
        tiempoAtaque = 0f;

        if (sonido != null) {
            sonido.play();
        }
    }

    private void actualizarAvance(float mouseX, float mouseY) {
        avance = atacando ? distanciaAtaque : 0f;

        haciaMouse.set(mouseX - x, mouseY - y);

        if (haciaMouse.len2() > 0f) {
            haciaMouse.nor();

            x += haciaMouse.x * avance;
            y += haciaMouse.y * avance;
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
