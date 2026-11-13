package potenciadores;

import com.badlogic.gdx.graphics.Color;
import personajes.Personaje;

public class Limon extends Consumible{

    public Limon(float x, float y) {
        super("assets/limon.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarVelocidad(personaje.getVelocidad() * 0.25f, 10f);
        if (personaje.getTinte() == Color.PURPLE) {
            personaje.setTinte(Color.BROWN);
        } else {
            personaje.setTinte(Color.YELLOW);
        }
    }
}
