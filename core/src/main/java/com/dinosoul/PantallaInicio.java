package com.dinosoul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaInicio extends ScreenAdapter {

    private final DinoSoulMain main;
    private final Batch batch;
    private final BitmapFont font;

    private final Viewport viewport = new ScreenViewport();
    private final GlyphLayout layout = new GlyphLayout();

    public PantallaInicio(DinoSoulMain main) {
        this.main = main;
        this.batch = main.getBatch();
        this.font = main.getFuente();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        manejarEntrada();
        dibujarPantalla();
    }

    private void manejarEntrada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            main.setScreen(new PantallaJuego(main));
        }
    }

    private void dibujarPantalla() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        float centroX = viewport.getWorldWidth() / 2;
        float y = viewport.getWorldHeight() / 2 + 100;

        batch.begin();

        dibujarTextoCentrado("CONTROLES", centroX, y);
        y -= 50;

        dibujarTextoCentrado("W / A / S / D - Moverse", centroX, y);
        y -= 40;

        dibujarTextoCentrado("ESPACIO - Atacar", centroX, y);
        y -= 40;

        dibujarTextoCentrado("R - Reiniciar (al morir)", centroX, y);
        y -= 70;

        dibujarTextoCentrado("Presiona ENTER para empezar", centroX, y);

        batch.end();
    }

    private void dibujarTextoCentrado(String texto, float centroX, float y) {
        layout.setText(font, texto);

        float x = centroX - layout.width / 2;

        font.draw(batch, layout, x, y);
    }

}
