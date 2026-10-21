package armas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Arma {
    protected final Texture textura;
    protected final float ancho;
    protected final float alto;
    protected final float offsetX;
    protected final float offsetY;
    protected float x;
    protected float y;
    public Arma(String rutaTextura, float ancho, float alto, float offsetX, float offsetY) {
        this.textura = new Texture(rutaTextura);
        this.ancho = ancho;
        this.alto = alto;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    public void actualizar(float delta, float xDuenio, float yDuenio) {
        this.x = xDuenio + offsetX;
        this.y = yDuenio + offsetY;
    }
    public void dibujar(Batch batch) {
        batch.draw(textura, x - ancho / 2f, y - alto / 2f, ancho, alto);
    }
    public void dispose() {
        textura.dispose();
    }
}
