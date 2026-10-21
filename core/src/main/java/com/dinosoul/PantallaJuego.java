package com.dinosoul;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entidades.Jugador;

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

    private final Jugador jugador;

    public PantallaJuego(DinoSoulMain main, Batch batch) {
        this.main = main;
        this.batch = main.getBatch();

        float unitScale = 1 / 16f;

        this.viewportJuego = new ExtendViewport(ANCHO, ALTO);
        this.mapa = new TmxMapLoader().load("MapaDinosoul/mapaMARarena.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(mapa, unitScale);

        MapProperties props = mapa.getProperties();
        int anchoMapaTiles = props.get("width", Integer.class);
        int altoMapaTiles = props.get("height", Integer.class);
        int tileAncho = props.get("tilewidth", Integer.class);
        int tileAlto = props.get("tileheight", Integer.class);

        // Guardamos los valores en las variables de clase reales
        this.anchoMapa = anchoMapaTiles * tileAncho * unitScale;
        this.altoMapa = altoMapaTiles * tileAlto * unitScale;

        float centroX = this.anchoMapa / 2f;
        float centroY = this.altoMapa / 2f;

        this.jugador = new Jugador(centroX, centroY);
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, false);
    }

    @Override
    public void render(float delta) {
        final float ZOOM_CAMARA = 2.5f;
        OrthographicCamera camara = (OrthographicCamera) viewportJuego.getCamera();

        jugador.actualizar(delta);

        camara.zoom = ZOOM_CAMARA;

        camara.position.set(jugador.getX(), jugador.getY(), 0);


        float mitadAnchoCamara = camara.viewportWidth * camara.zoom / 2f;
        float mitadAltoCamara = camara.viewportHeight * camara.zoom / 2f;

        camara.position.x = MathUtils.clamp(camara.position.x, mitadAnchoCamara, anchoMapa - mitadAnchoCamara);
        camara.position.y = MathUtils.clamp(camara.position.y, mitadAltoCamara, altoMapa - mitadAltoCamara);

        camara.update();
        viewportJuego.apply();
        mapRenderer.setView(camara);
        mapRenderer.render();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        jugador.dibujar(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        mapa.dispose();
        jugador.dispose();
    }
}
