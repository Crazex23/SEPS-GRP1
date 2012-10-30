package ch.zhaw.arsphema.model.shot;

import ch.zhaw.arsphema.model.AbstractSprite;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.Sizes;
import ch.zhaw.arsphema.util.Sounds;
import ch.zhaw.arsphema.util.TextureRegions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Shot extends AbstractSprite {
	private static final long serialVersionUID = 4015891462645256011L;
	private int damage = 1;
	private float speed;
	
	protected boolean isEnemyShot = false;
	private boolean shouldBeRemoved = false;
	private float rotation;
	
	
	public Shot(float x, float y, boolean isEnemyShot, final float speed) {
		super(x,y, 2f, 1f, TextureRegions.SHOT);
		this.isEnemyShot = isEnemyShot;
		this.speed = speed;
		Services.getSoundManager().play(Sounds.SHOT,false);
		rotation = 0f;
		if (isEnemyShot) rotation = 180f;
		
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void draw(SpriteBatch batch, float ppuX,
			float ppuY) {
		
		batch.draw(textureRegion, ppuX * this.x, ppuY * this.y,0,0, ppuX * this.width, ppuY * this.height,1,1,rotation);
	}
	
	@Override
	public boolean move(float delta) {
		x += speed * delta;
		if (this.x > Sizes.DEFAULT_WORLD_WIDTH) {
			shouldBeRemoved = true;
		}
		return true;
	}
	
	@Override
	public Array<Shot> shoot(float delta) {
		return null; //should never be called
	}
	
	public boolean shouldBeRemoved() {
		
		return shouldBeRemoved;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
