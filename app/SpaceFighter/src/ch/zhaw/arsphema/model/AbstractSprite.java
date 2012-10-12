/**
 * 
 */
package ch.zhaw.arsphema.model;

import java.util.List;

import ch.zhaw.arsphema.model.shot.Shot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author schtoeffel
 *
 */
public abstract class AbstractSprite extends Rectangle {
	
	protected int health;
	protected float shootingFrequency;
	protected float lastShot;
	protected TextureRegion textureRegion;
	protected float speed;
	
	
	/*
	 * ABSTRACT METHODES
	 */
	abstract public void move(float delta);
	abstract public List<Shot> shoot(float delta);
	
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public AbstractSprite(float x, float y, float width, float height, TextureRegion texture) {
		super(x, y, width, height);
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
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
