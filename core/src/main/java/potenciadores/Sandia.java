package potenciadores;

import personajes.Personaje;

public class Sandia extends Consumible {


    public Sandia(float x, float y) {
        super("assets/sandia.png", x, y, 1f, 1f);
    }

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.agregarVida(60);
    }

    }

