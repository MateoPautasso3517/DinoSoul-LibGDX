package potenciadores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import personajes.Personaje;


public abstract class Consumible {

    protected Texture textura;
    protected Rectangle rectangulo;
    protected boolean recolectado;
    private static Sound sonido;

    public Consumible(String rutaImagen, float x, float y, float ancho, float alto) {
        this.textura = new Texture(rutaImagen);
        this.recolectado = false;
        this.rectangulo = new Rectangle(x, y, ancho, alto);
        if (sonido == null) {
            sonido = Gdx.audio.newSound(Gdx.files.internal("sonidos/sonido_consumible.wav"));
        }
    }

    public abstract void aplicarEfecto(Personaje personaje);

    public void actualizarYVerificar(Personaje personaje) {
        if (recolectado) return;

        if (rectangulo.overlaps(personaje.obtenerLimites())) {
            aplicarEfecto(personaje);
            sonido.play();
            recolectado = true;
        }
    }

    public void dibujar(Batch batch) {
        if (recolectado) return;
        batch.draw(textura, rectangulo.x, rectangulo.y, rectangulo.width, rectangulo.height);
    }
    public void dispose() {
        textura.dispose();
    }

}

