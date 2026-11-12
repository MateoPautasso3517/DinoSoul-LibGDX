package com.dinosoul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
import personajes.Oliver;
import personajes.Personaje;
import potenciadores.*;

import java.util.ArrayList;
import java.util.List;

public class PantallaJuego extends ScreenAdapter {

    private static final float ANCHO = 16f;
    private static final float ALTO = 9f;
    private static final float ZOOM_CAMARA = 2.3f;
    private static final float UNIDAD_MUNDO = 1 / 16f;

    private final DinoSoulMain main;
    private final Batch batch;

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;

    private final Viewport viewportJuego;
    private final Viewport viewportHUD = new ScreenViewport();

    private float anchoMapa;
    private float altoMapa;
    private float tiempoJuego;

    private Personaje personaje;
    private TiledMapTileLayer colisiones;

    private final List<Consumible> consumibles = new ArrayList<>();

    private final Texture corazon;
    private final Texture reloj;
    private final Texture pixel;

    private Music musicaFondo;

    public PantallaJuego(DinoSoulMain main) {
        this.main = main;
        this.batch = main.getBatch();

        viewportJuego = new ExtendViewport(ANCHO, ALTO);

        inicializarMapa();
        inicializarPersonaje();

        corazon = new Texture("assets/Corazon.png");
        reloj = new Texture("assets/reloj.png");
        pixel = crearPixel();
    }

    private void inicializarMapa() {
        mapa = new TmxMapLoader().load(
            "MapaDinosoul/mapaMARarena.tmx"
        );

        mapRenderer = new OrthogonalTiledMapRenderer(
            mapa,
            UNIDAD_MUNDO
        );

        MapProperties props = mapa.getProperties();

        int anchoTiles = props.get("width", Integer.class);
        int altoTiles = props.get("height", Integer.class);

        int tileAncho = props.get("tilewidth", Integer.class);
        int tileAlto = props.get("tileheight", Integer.class);

        anchoMapa = anchoTiles * tileAncho * UNIDAD_MUNDO;
        altoMapa = altoTiles * tileAlto * UNIDAD_MUNDO;

        colisiones = (TiledMapTileLayer) mapa.getLayers().get("Colisiones");
    }

    private void inicializarPersonaje() {
        float centroX = anchoMapa / 2f;
        float centroY = altoMapa / 2f;

        personaje = new Oliver(centroX, centroY);

        consumibles.add(
            new Limon(personaje.getX() + 2f, personaje.getY())
        );
    }

    private Texture crearPixel() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        Texture textura = new Texture(pixmap);

        pixmap.dispose();

        return textura;
    }

    @Override
    public void resize(int width, int height) {
        viewportJuego.update(width, height, false);
        viewportHUD.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        actualizarJuego(delta);
        dibujarJuego();
        dibujarHUD();
    }

    private void actualizarJuego(float delta) {
        actualizarCamara();

        Vector3 mouse = new Vector3(
            Gdx.input.getX(),
            Gdx.input.getY(),
            0
        );

        viewportJuego.getCamera().unproject(mouse);

        personaje.actualizar(
            delta,
            mouse.x,
            mouse.y,
            colisiones
        );

        for (Consumible consumible : consumibles) {
            consumible.actualizarYVerificar(personaje);
        }

        tiempoJuego += delta;
    }

    private void actualizarCamara() {
        OrthographicCamera camara =
            (OrthographicCamera) viewportJuego.getCamera();

        camara.zoom = ZOOM_CAMARA;

        float mitadAncho = camara.viewportWidth * camara.zoom / 2f;
        float mitadAlto = camara.viewportHeight * camara.zoom / 2f;

        float x = personaje.getX();
        float y = personaje.getY();

        // Evitar que la cámara salga de los límites del mapa.
        if (anchoMapa > mitadAncho * 2) {
            x = MathUtils.clamp(x, mitadAncho, anchoMapa - mitadAncho);
        } else {
            x = anchoMapa / 2f;
        }

        if (altoMapa > mitadAlto * 2) {
            y = MathUtils.clamp(y, mitadAlto, altoMapa - mitadAlto);
        } else {
            y = altoMapa / 2f;
        }

        camara.position.set(x, y, 0);
        camara.update();

        viewportJuego.apply();
    }

    private void dibujarJuego() {
        OrthographicCamera camara =
            (OrthographicCamera) viewportJuego.getCamera();

        mapRenderer.setView(camara);
        mapRenderer.render();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        personaje.dibujar(batch);

        for (Consumible consumible : consumibles) {
            consumible.dibujar(batch);
        }

        batch.end();
    }

    private void dibujarHUD() {
        viewportHUD.apply();

        batch.setProjectionMatrix(
            viewportHUD.getCamera().combined
        );

        batch.begin();

        dibujarBarraVida();
        dibujarTemporizador();

        batch.end();
    }

    private void dibujarTemporizador() {
        BitmapFont fuente = main.getFuenteTemporizador();

        int total = (int) tiempoJuego;
        int minutos = total / 60;
        int segundos = total % 60;

        String texto = minutos + ":" +
            (segundos < 10 ? "0" : "") + segundos;

        float tamIcono = 32f;
        float espacio = 8f;
        float anchoTexto = 70f;

        float anchoGrupo = tamIcono + espacio + anchoTexto;

        float x = viewportHUD.getWorldWidth() / 2f - anchoGrupo / 2f;
        float y = 16f;

        batch.draw(reloj, x, y, tamIcono, tamIcono);

        fuente.draw(
            batch,
            texto,
            x + tamIcono + espacio,
            y + 24f
        );
    }

    private void dibujarBarraVida() {
        float anchoBarra = 200f;
        float altoBarra = 20f;

        float anchoGrupo = 32f + 8f + anchoBarra;

        float x = viewportHUD.getWorldWidth() / 2f - anchoGrupo / 2f;
        float y = viewportHUD.getWorldHeight() - 50f;

        float barraX = x + 40f;
        float barraY = y + 6f;

        float porcentaje = MathUtils.clamp(
            personaje.getVida() / (float) personaje.getVidaMax(),
            0f,
            1f
        );

        batch.draw(corazon, x, y, 32f, 32f);

        // Fondo de la barra
        batch.setColor(Color.DARK_GRAY);
        batch.draw(pixel, barraX, barraY, anchoBarra, altoBarra);

        // Vida actual
        batch.setColor(Color.RED);
        batch.draw(
            pixel,
            barraX,
            barraY,
            anchoBarra * porcentaje,
            altoBarra
        );

        batch.setColor(Color.WHITE);

        BitmapFont fuenteHUD = main.getFuenteHUD();

        fuenteHUD.draw(
            batch,
            personaje.getVida() + "/" + personaje.getVidaMax(),
            barraX + 8f,
            barraY + 19f
        );
    }

    @Override
    public void show() {
        musicaFondo = Gdx.audio.newMusic(
            Gdx.files.internal("sonidos/musica_fondo.wav")
        );

        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.2f);
        musicaFondo.play();
    }

    @Override
    public void hide() {
        if (musicaFondo != null) {
            musicaFondo.stop();
        }
    }

    @Override
    public void dispose() {
        if (musicaFondo != null) {
            musicaFondo.dispose();
        }

        mapRenderer.dispose();
        mapa.dispose();
        personaje.dispose();

        corazon.dispose();
        reloj.dispose();
        pixel.dispose();

        for (Consumible consumible : consumibles) {
            consumible.dispose();
        }
    }
}
