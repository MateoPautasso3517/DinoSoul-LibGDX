package com.dinosoul;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaControl extends ScreenAdapter {
    private final DinoSoulMain main;
    private final Batch batch;
    private final BitmapFont font;
    private final Viewport viewport = new ScreenViewport();


    public PantallaControl(DinoSoulMain main) {
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
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "BRITANY BASURA VOS SOS LA DICTADURA", 100f, 100f);
        batch.end();
    }
}
