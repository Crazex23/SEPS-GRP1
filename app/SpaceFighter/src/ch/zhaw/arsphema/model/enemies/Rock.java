package ch.zhaw.arsphema.model.enemies;

import java.util.Random;

import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.model.shot.ShotFactory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Rock extends AbstractEnemy {
	private static final long serialVersionUID = -8679196122359337868L;
	private static final int COLLISION_DAMAGE = 2;

	private Random rotationRandom = new Random();
	private float rotation;
	private float velocity;
	
	
	public Rock(float x, float y, float offsetX, float offsetY, float width, float height,
			TextureRegion texture) {
		super(x, y, offsetX, offsetY, width, height, texture, 0, COLLISION_DAMAGE,1);
		rotation = rotationRandom.nextFloat() * 360;
	}
	
	@Override
	public boolean move(float delta) {
		y += velocity * delta;
		return false;
	}


	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		batch.draw(textureRegion, x * ppuX, y * ppuY,0,0, width * ppuX, height * ppuY,1,1,rotation);
	}

	@Override
	public boolean isShowHealthBar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		// TODO Auto-generated method stub
		return null;
	}

}
