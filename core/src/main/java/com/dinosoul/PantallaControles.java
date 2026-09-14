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

public class PantallaControles extends ScreenAdapter {
    private final DinoSoulMain main;
    private final Batch batch;
    private final BitmapFont font;
    private final Viewport viewport = new ScreenViewport();
    private final GlyphLayout layout = new GlyphLayout();

    public PantallaControles(DinoSoulMain main) {
        this.main = main;
        this.batch = main.getBatch();
        this.font = main.getFont();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            main.setScreen(new PantallaJuego(main, main.getBatch()));
            dispose();
            return;
        }
        ScreenUtils.clear(Color.BLACK);

        float centroX = viewport.getWorldWidth() / 2;
        float y = viewport.getWorldHeight() / 2 + 100;

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        layout.setText(font, "CONTROLES");
        font.draw(batch, layout, centroX - layout.width / 2, y);
        y -= 50;

        layout.setText(font, "W / A / S / D - Moverse");
        font.draw(batch, layout, centroX - layout.width / 2, y);
        y -= 40;

        layout.setText(font, "R - Reiniciar (al morir)");
        font.draw(batch, layout, centroX - layout.width / 2, y);
        y -= 70;

        layout.setText(font, "Presiona ENTER para empezar");
        font.draw(batch, layout, centroX - layout.width / 2, y);

        batch.end();
    }
}
