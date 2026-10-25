package com.dinosoul;

import Personajes.Oliver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego extends ScreenAdapter {
    private float anchoMapa;
    private float altoMapa;
    private static final float ANCHO = 16f;
    private static final float ALTO = 9f;
    private final DinoSoulMain main;
    private final Batch batch;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Viewport viewportJuego;

    private final Oliver oliver;
    private final TiledMapTileLayer colisiones;

    public PantallaJuego(DinoSoulMain main, Batch batch) {
        this.main = main;
        this.batch = main.getBatch();

        float unidadMundo = 1 / 16f;

        this.viewportJuego = new ExtendViewport(ANCHO, ALTO);
        this.mapa = new TmxMapLoader().load("MapaDinosoul/mapaMARarena.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(mapa, unidadMundo);

        MapProperties props = mapa.getProperties();
        int anchoMapaTiles = props.get("width", Integer.class);
        int altoMapaTiles = props.get("height", Integer.class);
        int tileAncho = props.get("tilewidth", Integer.class);
        int tileAlto = props.get("tileheight", Integer.class);


        this.anchoMapa = anchoMapaTiles * tileAncho * unidadMundo;
        this.altoMapa = altoMapaTiles * tileAlto * unidadMundo;

        float centroX = this.anchoMapa / 2f;
        float centroY = this.altoMapa / 2f;

        this.oliver = new Oliver(centroX, centroY);

        colisiones = (TiledMapTileLayer) mapa.getLayers().get("Colisiones");
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, false);
    }

    @Override
    public void render(float delta) {

        final float ZOOM_CAMARA = 2.3f;
        OrthographicCamera camara = (OrthographicCamera) viewportJuego.getCamera();

        camara.zoom = ZOOM_CAMARA;

        camara.position.set(oliver.getX(), oliver.getY(), 0);


        float mitadAnchoCamara = camara.viewportWidth * camara.zoom / 2f;
        float mitadAltoCamara = camara.viewportHeight * camara.zoom / 2f;

        camara.position.x = MathUtils.clamp(camara.position.x, mitadAnchoCamara, anchoMapa - mitadAnchoCamara);
        camara.position.y = MathUtils.clamp(camara.position.y, mitadAltoCamara, altoMapa - mitadAltoCamara);

        camara.update();
        viewportJuego.apply();

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camara.unproject(mouse);

        oliver.actualizar(delta, mouse.x, mouse.y, colisiones);

        mapRenderer.setView(camara);
        mapRenderer.render();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        oliver.dibujar(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        mapa.dispose();
        oliver.dispose();
    }
}
