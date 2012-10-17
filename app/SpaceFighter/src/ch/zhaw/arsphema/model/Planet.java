package ch.zhaw.arsphema.model;

import java.util.Random;

import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Planet extends AbstractSprite {

	private static final int ROWS = 4;
	private static final int COLS = 3;
	private TextureRegion[] planets;
	private int whichPlanet;
	private boolean shouldBeRemoved;

	public Planet(float x, float y, float width, float height,
			TextureRegion texture) {
		super(x, y, width, height, texture);
		TextureRegion[][] tmp = textureRegion.split(textureRegion.getRegionWidth() / COLS, textureRegion.getRegionHeight() / ROWS);
		planets = new TextureRegion[COLS * ROWS];

		int index = 0;
        for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                	planets[index++] = tmp[i][j];
                }
        }
        whichPlanet = new Random().nextInt(planets.length-1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 749176604995858563L;

	@Override
	public boolean move(float delta) {
		x -= speed * delta;
		if (this.x + this.width < 0) {
			shouldBeRemoved = true;
		}
		return true;
	}
	public boolean shouldBeRemoved() {
		
		return shouldBeRemoved;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		batch.draw(planets[whichPlanet], ppuX * this.x, ppuY * this.y, ppuX * this.width, ppuY * this.height);
	}

}
