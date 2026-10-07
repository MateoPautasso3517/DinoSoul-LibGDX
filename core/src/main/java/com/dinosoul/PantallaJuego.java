package com.dinosoul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego extends ScreenAdapter {
    private static final float ANCHO = 16f;
    private static final float ALTO = 9f;
    private final DinoSoulMain main;
    private final Batch batch;
    TiledMap mapa = new TmxMapLoader().load("MapaDinosoul/mapaMARarena.tmx");
    OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(mapa);
    private Viewport viewportJuego = new ExtendViewport(ANCHO, ALTO);

    private float personajeX;
    private float personajeY;

    public PantallaJuego(DinoSoulMain main, Batch batch) {
        this.main = main;
        this.batch = main.getBatch();

        float unitScale = 1 / 16f;

        this.viewportJuego = new ExtendViewport(ANCHO, ALTO);
        this.mapa = new TmxMapLoader().load("MapaDinosoul/mapaMARarena.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(mapa, 1 / 16f);

        MapProperties props = mapa.getProperties();
        int AnchoMapa = props.get("width", Integer.class);
        int AltoMapa = props.get("height", Integer.class);
        int tileAncho = props.get("tilewidth", Integer.class);
        int tileAlto = props.get("tileheight", Integer.class);

        this.personajeX = (AnchoMapa * tileAncho * unitScale) / 2f;
        this.personajeY = (AltoMapa * tileAlto * unitScale) / 2f;
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, true);


    }

    @Override
    public void render(float delta) {
        OrthographicCamera camara = (OrthographicCamera) viewportJuego.getCamera();

        camara.zoom = 1.5f;

        camara.position.set(personajeX, personajeY, 0);
        camara.update();
        viewportJuego.apply();
        mapRenderer.setView(camara);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        mapa.dispose();
    }
}
