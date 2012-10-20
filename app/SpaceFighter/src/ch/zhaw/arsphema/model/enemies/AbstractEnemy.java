package ch.zhaw.arsphema.model.enemies;

import ch.zhaw.arsphema.model.AbstractSprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class AbstractEnemy extends AbstractSprite {
	private static final long serialVersionUID = 1L;
	protected int basePoints;
	protected float shotVelocity;


	public AbstractEnemy(float x, float y, float width, float height, TextureRegion texture) {
		super(x, y, width, height, texture);
	}

	public boolean lowerHealth(int damage) {
		health -= damage;
		return health <= 0;
	}

	public int getBasePoints() {
		return basePoints;
	}

	public void setBasePoints(int basePoints) {
		this.basePoints = basePoints;
	}
}
