package potenciadores;

import com.badlogic.gdx.graphics.Color;
import personajes.Personaje;

public class Uva extends Consumible{

    public Uva(float x, float y) {
        super("assets/uva.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarDanio(personaje.getDanio() * 0.40f, 15f);
        personaje.setTinte(Color.PURPLE);
    }

}
