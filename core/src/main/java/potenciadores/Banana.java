package potenciadores;
import personajes.*;

public class Banana extends Consumible {
    private int vidaARecuperar = 20;

    public Banana(float x, float y) {
        super("assets/banana.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarVida(20);
    }
}
