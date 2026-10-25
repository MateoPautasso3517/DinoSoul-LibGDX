package com.dinosoul;

import Personajes.Oliver;
import Personajes.Personaje;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

    private final Personaje personaje;
    private final TiledMapTileLayer colisiones;
    private final Viewport viewportHUD = new ScreenViewport();
    private final Texture corazon = new Texture("assets/Corazon.png");
    private final Texture pixel;
    private float tiempoJuego = 0f;
    private final Texture reloj = new Texture("assets/reloj.png");


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

        this.personaje = new Oliver(centroX, centroY);

        colisiones = (TiledMapTileLayer) mapa.getLayers().get("Colisiones");

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        this.pixel = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, false);
        viewportHUD.update(width, height, true);
    }

    @Override
    public void render(float delta) {

        final float ZOOM_CAMARA = 2.3f;
        OrthographicCamera camara = (OrthographicCamera) viewportJuego.getCamera();

        camara.zoom = ZOOM_CAMARA;

        camara.position.set(personaje.getX(), personaje.getY(), 0);


        float mitadAnchoCamara = camara.viewportWidth * camara.zoom / 2f;
        float mitadAltoCamara = camara.viewportHeight * camara.zoom / 2f;

        camara.position.x = MathUtils.clamp(camara.position.x, mitadAnchoCamara, anchoMapa - mitadAnchoCamara);
        camara.position.y = MathUtils.clamp(camara.position.y, mitadAltoCamara, altoMapa - mitadAltoCamara);

        camara.update();
        viewportJuego.apply();

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camara.unproject(mouse);

        personaje.actualizar(delta, mouse.x, mouse.y, colisiones);
        tiempoJuego += delta;
        int total = (int) tiempoJuego;
        int minutos = total / 60;
        int segundos = total % 60;
        String texto = minutos + ":" + (segundos < 10 ? "0" + segundos : "" + segundos);

        mapRenderer.setView(camara);
        mapRenderer.render();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        personaje.dibujar(batch);
        AplicarViewport();
        DibujarBarraVida();
        DibujarTemporizador(texto);
        batch.end();
    }

    private void AplicarViewport() {
        viewportHUD.apply();
        batch.setProjectionMatrix(viewportHUD.getCamera().combined);
    }

    private void DibujarTemporizador(String texto) {

        BitmapFont fuente = main.getFuenteTemporizador();
        float tamIcono = 32f;
        float espacio = 8f;
        float anchoTexto = 70f;
        float anchoGrupo = tamIcono + espacio + anchoTexto;
        float x = viewportHUD.getWorldWidth() / 2f - anchoGrupo / 2f;
        float y = 16f;

        batch.draw(reloj, x, y, tamIcono, tamIcono);
        fuente.draw(batch, texto, x + tamIcono + espacio, y + 24f);
    }

    private void DibujarBarraVida() {
        float anchoGrupo = 32f + 8f + 200f;
        float x = viewportHUD.getWorldWidth() / 2f - anchoGrupo / 2f;
        float y = viewportHUD.getWorldHeight() - 50f;
        float anchoBarra = 200f;
        float altoBarra = 20f;
        float barraX = x + 40f;
        float barraY = y + 6f;
        float porcentaje = personaje.getVida() / (float) personaje.getVidaMax();
        batch.draw(corazon, x, y, 32f, 32f);
        batch.setColor(Color.DARK_GRAY);
        batch.draw(pixel, barraX, barraY, anchoBarra, altoBarra);
        batch.setColor(Color.RED);
        batch.draw(pixel, barraX, barraY, anchoBarra * porcentaje, altoBarra);
        batch.setColor(Color.WHITE);
        BitmapFont fuenteHUD = main.getFuenteHUD();
        fuenteHUD.draw(batch, personaje.getVida() + "/" + personaje.getVidaMax(), barraX + 8f, barraY + 19f);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        mapa.dispose();
        personaje.dispose();
        corazon.dispose();
        pixel.dispose();
    }
}
