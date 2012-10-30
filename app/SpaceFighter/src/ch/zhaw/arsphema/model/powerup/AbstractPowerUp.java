package ch.zhaw.arsphema.model.powerup;

import ch.zhaw.arsphema.controller.PowerUpManager;
import ch.zhaw.arsphema.model.AbstractSprite;
import ch.zhaw.arsphema.model.shot.Shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractPowerUp extends AbstractSprite {
	public AbstractPowerUp(float x, float y, float width, float height, TextureRegion texture) {
		super(x, y, width, height, texture, 10);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public boolean move(float delta) {
		x -= speed * delta;
		return true;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		return null;
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(textureRegion, x, y, width, height);
	}
	
	/**
	 * does whatever the powerup does 
	 * @param pum
	 */
	abstract public void doSomething(PowerUpManager pum);
	
	/**
	 * undoes whatever the powerup did
	 * @param pum
	 */
	abstract public void undoSomething(PowerUpManager pum);

}
