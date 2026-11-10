package potenciadores;
import personajes.*;

public class Frutilla extends Consumible {
    private int vidaARecuperar = 20;

    public Frutilla(float x, float y) {
        super("assets/frutilla.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarVida(20);
    }
}
