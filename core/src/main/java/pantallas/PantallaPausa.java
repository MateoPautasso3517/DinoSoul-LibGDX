package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dinosoul.DinoSoulMain;

public class PantallaPausa extends ScreenAdapter {

    private final DinoSoulMain main;
    private final Batch batch;
    private final com.badlogic.gdx.Screen pantallaAnterior;

    private final Viewport viewport;
    private final Texture pixel;
    private final GlyphLayout glyphLayout;

    public PantallaPausa(DinoSoulMain main, com.badlogic.gdx.Screen pantallaAnterior) {
        this.main = main;
        this.batch = main.getBatch();
        this.pantallaAnterior = pantallaAnterior;

        viewport = new ScreenViewport();
        pixel = crearPixel();
        glyphLayout = new GlyphLayout();
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
        viewport.update(width, height, true);
        pantallaAnterior.resize(width, height);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            main.setScreen(pantallaAnterior);
            return;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
            return;
        }

        pantallaAnterior.render(0f);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float anchoCaja = 300f;
        float altoCaja = 450f;
        float xCaja = (viewport.getWorldWidth() - anchoCaja) / 2f;
        float yCaja = (viewport.getWorldHeight() - altoCaja) / 2f;

        batch.setColor(0.91f, 0.84f, 0.60f, 1.0f);
        batch.draw(pixel, xCaja, yCaja, anchoCaja, altoCaja);

        batch.setColor(Color.TEAL);
        batch.draw(pixel, xCaja, yCaja, anchoCaja, 3f); // Borde inferior
        batch.draw(pixel, xCaja, yCaja + altoCaja - 3f, anchoCaja, 3f); // Borde superior
        batch.draw(pixel, xCaja, yCaja, 3f, altoCaja); // Borde izquierdo
        batch.draw(pixel, xCaja + anchoCaja - 3f, yCaja, 3f, altoCaja); // Borde derecho

        BitmapFont fuente = main.getFuenteHUD();
        fuente.setColor(Color.BLACK);



        fuente.getData().setScale(1.0f);
        fuente.setColor(new Color(0.25f, 0.18f, 0.10f, 1f));
        String textoTitulo = "JUEGO PAUSADO";
        glyphLayout.setText(fuente, textoTitulo);
        float xTitulo = xCaja + (anchoCaja - glyphLayout.width) / 2f;
        float yTitulo = yCaja + altoCaja - 60f;
        fuente.draw(batch, textoTitulo, xTitulo, yTitulo);


        fuente.getData().setScale(0.9f);
        String textoReanudar = "REANUDAR";
        glyphLayout.setText(fuente, textoReanudar);
        float xReanudar = xCaja + (anchoCaja - glyphLayout.width) / 2f;
        float yReanudar = yTitulo - 90f;
        fuente.draw(batch, textoReanudar, xReanudar, yReanudar);

        fuente.getData().setScale(0.65f);
        String textoEspacio = "[ ESPACIO ]";
        glyphLayout.setText(fuente, textoEspacio);
        float xEspacio = xCaja + (anchoCaja - glyphLayout.width) / 2f;
        fuente.draw(batch, textoEspacio, xEspacio, yReanudar - 30f);



        fuente.getData().setScale(0.9f);
        String textoSalir = "SALIR";
        glyphLayout.setText(fuente, textoSalir);
        float xSalir = xCaja + (anchoCaja - glyphLayout.width) / 2f;
        float ySalir = yReanudar - 110f;
        fuente.draw(batch, textoSalir, xSalir, ySalir);

        fuente.getData().setScale(0.65f);
        String textoEsc = "[ ESC ]";
        glyphLayout.setText(fuente, textoEsc);
        float xEsc = xCaja + (anchoCaja - glyphLayout.width) / 2f;
        fuente.draw(batch, textoEsc, xEsc, ySalir - 30f);


        fuente.getData().setScale(1.0f);
        batch.setColor(Color.WHITE);
        batch.end();
    }


    @Override
    public void dispose() {
        pixel.dispose();
    }
}
