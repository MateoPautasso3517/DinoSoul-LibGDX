package Personajes;
import armas.Lanza;

public class Oliver extends Personaje {
    public Oliver(float x, float y) {
        super(x, y, "assets/Oliver_Spritesheet.png", 256, 256, 8f);
        this.arma = new Lanza();
    }


}
