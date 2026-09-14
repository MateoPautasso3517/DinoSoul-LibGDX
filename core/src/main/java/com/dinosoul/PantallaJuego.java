package com.dinosoul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego extends ScreenAdapter {
    private static final float ANCHO = 16f;
    private static final float ALTO = 9f;
    private final DinoSoulMain main;
    private final Batch batch;
    private final Texture texturaMapa = new Texture(Gdx.files.internal("mapa.png"));
    private final Viewport viewportJuego = new ExtendViewport(ANCHO, ALTO);

    public PantallaJuego(DinoSoulMain main, Batch batch) {
        this.main = main;
        this.batch = main.getBatch();

        texturaMapa.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);

        viewportJuego.apply();
        batch.setProjectionMatrix(viewportJuego.getCamera().combined);
        batch.begin();

        float u2 = viewportJuego.getWorldWidth() / ANCHO;
        float v2 = viewportJuego.getWorldHeight() / ALTO;
        batch.draw(texturaMapa, 0, 0, viewportJuego.getWorldWidth(), viewportJuego.getWorldHeight(), 0, 0, u2, v2);
        batch.end();
    }

    @Override
    public void dispose() {
        texturaMapa.dispose();
    }
}
