package ch.zhaw.arsphema.screen;

import ch.zhaw.arsphema.MyGdxGame;
import ch.zhaw.arsphema.controller.InGameController;
import ch.zhaw.arsphema.model.Hero;
import ch.zhaw.arsphema.model.NavigationOverlay;
import ch.zhaw.arsphema.util.Paths;
import ch.zhaw.arsphema.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen extends AbstractScreen {

	
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private Hero hero;
	private InGameController controller;
	private NavigationOverlay overlay;
	private float elapsed = 0;
	

	public GameScreen(MyGdxGame game) {
		super(game);
		
	}


	@Override
	public void show() {
		loadTextures();
		controller = new InGameController(hero);
		
		Gdx.input.setInputProcessor(controller);
	}

	private void loadTextures() {
		hero = new Hero(5, Sizes.DEFAULT_WORLD_HEIGHT / 2 + Sizes.SHIP_HEIGHT / 2, new Texture(Gdx.files.internal(Paths.HERO)));
		overlay = new NavigationOverlay(new Texture(Gdx.files.internal(Paths.OVERLAY_SPRITE)));
	}

	@Override
	public void render(float delta) {
		if (elapsed < 2){
			elapsed += delta;
		}
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
		hero.move(delta);
		controller.update(delta);
		batch.begin();
//		batch.disableBlending();
		
		batch.draw(hero.getTexture(), ppuX * hero.x, ppuY * hero.y, ppuX * hero.width, ppuY * hero.height);
		// start overlay wird 2 sec angezeigt
		if (elapsed >= 2){ 
			batch.draw(overlay.getTexture(overlay.GAME), ppuX * overlay.x, ppuY * overlay.y, ppuX * overlay.width, ppuY * overlay.height);
		} else {
			batch.draw(overlay.getTexture(overlay.START), ppuX * overlay.x, ppuY * overlay.y, ppuX * overlay.width, ppuY * overlay.height);
		}
		
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		controller.resize(width, height);
		
		ppuX = (float) width / Sizes.DEFAULT_WORLD_WIDTH;
		ppuY = (float) height / Sizes.DEFAULT_WORLD_HEIGHT;
		
		
	}

	@Override
	public void hide() {
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}