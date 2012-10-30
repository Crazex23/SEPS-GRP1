package ch.zhaw.arsphema.model.enemies;

import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Saucer extends AbstractEnemy {
	private static final long serialVersionUID = -8679196122359337868L;
	private static final int COLLISION_DAMAGE = 1;

	private float ySpeed = 5f, yTopEnd, yBottomEnd;
	private float innerYOffset;
	
	public Saucer(float x, float y, float offsetX, float offsetY, float width, float height,
			TextureRegion texture, final int points) {
		super(x, y, offsetX, offsetY, width, height, texture, points, COLLISION_DAMAGE);
		yBottomEnd = - Sizes.SAUCER_HEIGHT / 2;
		yTopEnd = Sizes.SAUCER_HEIGHT / 2;
	}
	
	public void redirectYSpeed(){
		ySpeed *= -1;
	}
	
	@Override
	public boolean move(final float delta) {
		final float move = ySpeed * delta;
		innerYOffset += move;
		offsetY += move;
		if(innerYOffset < yBottomEnd || innerYOffset > yTopEnd){
			redirectYSpeed();
		}
		return false;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		return null;
	}
	
	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		batch.draw(textureRegion, x * ppuX, y * ppuY, width * ppuX, height * ppuY);
	}

}
