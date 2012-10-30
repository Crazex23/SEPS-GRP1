package ch.zhaw.arsphema.model.enemies;

import java.util.Random;

import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.model.shot.ShotFactory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Blob extends AbstractEnemy {
	private static final long serialVersionUID = -2931309233637206858L;
	private static final int COLLISION_DAMAGE = 2;
	protected float shotVelocity = -10;
	private float shootFrequency = 1;
	private Random shotRandom = new Random();
	
	
	public Blob(float x, float y, float offsetX, float offsetY, float width, float height,
			TextureRegion texture, final int points) {
		super(x, y, offsetX, offsetY, width, height, texture, points, COLLISION_DAMAGE);
		resetShotFrequency();
		health = 8;
	}
	
	@Override
	public boolean move(float delta) {
//		x -= xMovement * delta;
//		y -= yMovement * delta;
//		if(y < height || y > Sizes.DEFAULT_WORLD_HEIGHT)
//			yMovement *= -1;
//		//TODO yMovement
//		return x < -width;
		return false;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		shootFrequency -= delta;
		if(shootFrequency < 0)
		{
			final Array<Shot> shot = ShotFactory.createShotInArray(x - shotVelocity * delta, y, shotVelocity, ShotFactory.Type.GREEN, true);
			resetShotFrequency();
			return shot;
		}
		return null;
	}
	
	private void resetShotFrequency(){
		shootFrequency = 1 + (5 * shotRandom.nextFloat());
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(textureRegion, x, y, width, height);
	}


}
