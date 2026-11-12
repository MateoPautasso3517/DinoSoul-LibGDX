package com.dinosoul;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class DinoSoulMain extends Game {

    private SpriteBatch batch;

    private BitmapFont fuente;
    private BitmapFont fuenteHUD;
    private BitmapFont fuenteTemporizador;

    @Override
    public void create() {
        inicializarBatch();
        inicializarFuentes();

        setScreen(new PantallaInicio(this));
    }

    private void inicializarBatch() {
        batch = new SpriteBatch();
    }

    private void inicializarFuentes() {
        fuente = generarFuente("CherryCreamSoda-Regular.ttf", 24, Color.WHITE);
        fuenteHUD = generarFuente("Mikey.ttf", 16, Color.BLACK);
        fuenteTemporizador = generarFuente("Mikey.ttf", 24, Color.BLACK);
    }

    private BitmapFont generarFuente(String archivo, int tamaño, Color color) {
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal(archivo));

        FreeTypeFontGenerator.FreeTypeFontParameter parametros =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametros.size = tamaño;
        parametros.color = color;

        BitmapFont font = generator.generateFont(parametros);

        generator.dispose();

        return font;
    }

    @Override
    public void dispose() {
        super.dispose();

        batch.dispose();

        fuente.dispose();
        fuenteHUD.dispose();
        fuenteTemporizador.dispose();

    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFuente() {
        return fuente;
    }

    public BitmapFont getFuenteHUD() {
        return fuenteHUD;
    }

    public BitmapFont getFuenteTemporizador() {
        return fuenteTemporizador;
    }
}
