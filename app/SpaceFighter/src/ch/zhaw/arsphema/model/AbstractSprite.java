package ch.zhaw.arsphema.model;

import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.screen.Renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * @author schtoeffel
 *
 */
public abstract class AbstractSprite extends Rectangle {
	
	private static final long serialVersionUID = -5268095085491479865L;
	protected int health;
	protected float shootingFrequency;
	protected float lastShot;
	protected TextureRegion textureRegion;
	
	protected float speed;
	
	
	/*
	 * ABSTRACT METHODES
	 */
	/**
	 * @param delta
	 * @return true if still alive
	 */
	abstract public boolean move(final float delta);
	abstract public Array<Shot> shoot(final float delta);
	abstract public void draw(final SpriteBatch batch);
	
	public AbstractSprite(final float x, final float y, final float width, 
			final float height, final TextureRegion texture, final float speed, final boolean a) {
		super(x, y, width, height);
		this.speed = speed * Renderer.getPpuX();
		this.textureRegion = texture;
	}
	
	public AbstractSprite(final float x, final float y, final float width, 
			final float height, final TextureRegion texture, final float speed) {
		super(x * Renderer.getPpuX(), y * Renderer.getPpuY(), width, height);
		this.speed = speed * Renderer.getPpuX();
		this.textureRegion = texture;
	}
	public AbstractSprite(final float x, final float y, final float width, 
			final float height, final TextureRegion texture) {
		super(x * Renderer.getPpuX(), y * Renderer.getPpuY(), width, height);
		this.speed = speed * Renderer.getPpuX();
		this.textureRegion = texture;
	}
	
	

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public float getShootingFrequency() {
		return shootingFrequency;
	}
	public void setShootingFrequency(float shootingFrequency) {
		this.shootingFrequency = shootingFrequency;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(final float speed) {
		this.speed = speed;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setTextureRegion(final TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public void resize(final float oldPpuX, final float oldPpuY, final float newPpuX, final float newPpuY){
		x = x / oldPpuX * newPpuX;
		y = y / oldPpuY * newPpuY;
		height = height / oldPpuY * newPpuY;
		width = width / oldPpuX * newPpuX;
	}

}
