package ch.zhaw.arsphema.screen;

import ch.zhaw.arsphema.MyGdxGame;
import ch.zhaw.arsphema.services.MusicManager;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.services.SoundManager;
import ch.zhaw.arsphema.util.Paths;
import ch.zhaw.arsphema.util.Sizes;
import ch.zhaw.arsphema.util.SpaceAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class LoadingScreen extends AbstractScreen {

    private static final int FRAME_COLS = 12;
    private static final int FRAME_ROWS = 1;
    private SpaceAssetManager assetManager;
    private Texture loader;
    private SpriteBatch batch;
    private float ppuX;
    private float ppuY;
    private TextureRegion[] loaderFrames;
    private Animation loaderAnimation;
    private float stateTime;

    public LoadingScreen(MyGdxGame game) {
        super(game);
        assetManager = SpaceAssetManager.getInstance();
        Services.initProfileManager();
        Services.setSoundManager(new SoundManager());
        Services.setMusicManager(new MusicManager());
    }

    @Override
    public void show() {
        // Atlas
        assetManager.load(Paths.ATLAS, TextureAtlas.class);
        // Fonts
        assetManager.load(Paths.SPACE_FONT_SMALL, BitmapFont.class);
        assetManager.load(Paths.SPACE_FONT_BIG, BitmapFont.class);
        assetManager.load(Paths.TEXT_FONT_SMALL, BitmapFont.class);
        assetManager.load(Paths.TEXT_FONT_BIG, BitmapFont.class);
        assetManager.load(Paths.TITLE_FONT_SMALL, BitmapFont.class);
        assetManager.load(Paths.TITLE_FONT_BIG, BitmapFont.class);
        assetManager.load(Paths.POINT_FONT_BIG, BitmapFont.class);
        assetManager.load(Paths.POINT_FONT_SMALL, BitmapFont.class);
        // SOUNDS
        assetManager.load(Paths.SFX_JET, Sound.class);
        assetManager.load(Paths.SFX_SHOT, Sound.class);
        assetManager.load(Paths.SFX_DANGER, Sound.class);
        assetManager.load(Paths.SFX_EXPLOSION, Sound.class);
        assetManager.load(Paths.SFX_HURT, Sound.class);
        assetManager.load(Paths.SFX_BEEP, Sound.class);
        //MUSIC
        assetManager.load(Paths.TRACK_01, Music.class);
        assetManager.load(Paths.TRACK_02, Music.class);
        assetManager.load(Paths.TRACK_03, Music.class);
        assetManager.load(Paths.TRACK_04, Music.class);

        assetManager.load(Paths.GAME_OVER, Music.class);

        batch = new SpriteBatch();
        loader = new Texture(Gdx.files.internal("images/loader.png"));

        TextureRegion[][] tmp = TextureRegion.split(loader, loader.getWidth()
                / FRAME_COLS, loader.getHeight() / FRAME_ROWS); // #10
        loaderFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                loaderFrames[index++] = tmp[i][j];
            }
        }
        loaderAnimation = new Animation(0.025f, loaderFrames); // #11
        loaderAnimation.setPlayMode(Animation.LOOP);

        stateTime = 0f;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        ppuX = (float) width / Sizes.DEFAULT_WORLD_WIDTH;
        ppuY = (float) height / Sizes.DEFAULT_WORLD_HEIGHT;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stateTime += delta;
        batch.begin();
        batch.draw(loaderAnimation.getKeyFrame(stateTime), ppuX * (Sizes.DEFAULT_WORLD_WIDTH / 2 - 5), ppuY
                * (Sizes.DEFAULT_WORLD_HEIGHT / 2 - 2), ppuX * 10, ppuY * 4f);
        batch.end();

        if (assetManager.update()) {
            game.initScreens();
            game.setScreen(game.getMainMenuScreen());
        }

    }

}
