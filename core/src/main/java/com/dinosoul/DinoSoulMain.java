package com.dinosoul;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class DinoSoulMain extends Game {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont fuente;
    private BitmapFont fuenteHUD;
    private BitmapFont fuenteTemporizador;

    @Override
    public void create() {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CherryCreamSoda-Regular.ttf"));
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Mikey.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter fontParams2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter fontParams3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = 24;
        fontParams.color = Color.WHITE;
        fontParams2.color = Color.BLACK;
        fontParams3.size = 24;
        fontParams3.color = Color.BLACK;
        fuente = generator.generateFont(fontParams);
        fuenteHUD = generator2.generateFont(fontParams2);
        fuenteTemporizador = generator2.generateFont(fontParams3);
        generator.dispose();
        generator2.dispose();
        setScreen((Screen) new PantallaControles(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        fuente.dispose();
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

    public SpriteBatch getBatch() {
        return batch;
    }
}
