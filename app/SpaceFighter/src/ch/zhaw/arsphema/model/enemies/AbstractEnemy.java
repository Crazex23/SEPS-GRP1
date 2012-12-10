package ch.zhaw.arsphema.model.enemies;

import ch.zhaw.arsphema.model.AbstractSprite;
import ch.zhaw.arsphema.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Abstrakter gegner von dem alle anderen abgeleitet werden
 */
public abstract class AbstractEnemy extends AbstractSprite {
	private static final long serialVersionUID = 1L;
	protected static float SHOT_FREQUENCY;
	protected int basePoints;
	protected float shotVelocity;
	protected float offsetY;
	protected float offsetX;
	protected final int collisionDamage;
	private float maxHealth;

	/**
	 * der konstruktor
	 * @param points zu erhaltene punkte
	 * @param collisionDamage schaden bei kollision
	 * @param health die lebenspunkte des gegners
	 */
	public AbstractEnemy(float x, float y, float offsetX, float offsetY
			, float width, float height, TextureRegion texture, final int points, 
			final int collisionDamage, int health) {
		super(x, y, width, height, texture);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.collisionDamage = collisionDamage;
		basePoints = points;
		maxHealth = health;
		this.health = health;
	}

	/**
	 * leben abziehen
	 * @param dagmage die anzahl lebenspunkte die abgezogen werden
	 */
	public boolean lowerHealth(int damage) {
		health -= damage;
		return health <= 0;
	}

	/**
	 * gibt punkte bei zerstoerung zurueck
	 */
	public int getBasePoints() {
		return basePoints;
	}

	/**
	 * setzt die zu erhaltenen Punkte
	 * @param basePoints die neuen punkte
	 */
	public void setBasePoints(int basePoints) {
		this.basePoints = basePoints;
	}

	/**
	 * gibt den schaden bei kollision zurueck
	 * @return den kollisionsschaden
	 */
	public int getCollisionDamage(){
		return collisionDamage;
	}
	
	/**
	 * zeichnet die healthbar
	 */
	public void drawHealthBar(ShapeRenderer shapeRenderer, float ppuX, float ppuY){

		if(isShowHealthBar())
		{
			//border
			shapeRenderer.setColor(Color.YELLOW);
			shapeRenderer.filledRect(x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuY - 1, 
					width * ppuX, 1);
			shapeRenderer.filledRect(x * ppuX, (Sizes.HEALTHBAR_HEIGHT + Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuY, 
					width * ppuX, 1);
			//Total Health
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.filledRect(x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuY, 
					width * ppuX, Sizes.HEALTHBAR_HEIGHT * ppuY);
			//health remaining
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.filledRect(x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuY, 
					(float)(width * ppuX * getHealth() / getMaxHealth()), Sizes.HEALTHBAR_HEIGHT * ppuY);
		}
	}

	private float getMaxHealth() {
		return maxHealth;
	}

	public abstract boolean isShowHealthBar();
}
