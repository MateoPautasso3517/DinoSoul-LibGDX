package potenciadores;
import personajes.*;

public class Cereza extends Consumible {
    private int vidaARecuperar = 20;

    public Cereza(float x, float y) {
        super("assets/cereza.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarVida(20);
    }
}
