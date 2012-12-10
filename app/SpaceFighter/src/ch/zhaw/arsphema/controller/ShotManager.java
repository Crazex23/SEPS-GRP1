package ch.zhaw.arsphema.controller;

import ch.zhaw.arsphema.model.Hero;
import ch.zhaw.arsphema.model.shot.Shot;

import com.badlogic.gdx.utils.Array;

/**
 * Schuss Kontroller:
 * kontrolliert alle sch�sse
 * @author schtoeffel
 *
 */
public class ShotManager {

	private Array<Shot> heroShots, enemyShots, shotsToRemove;
	
	/**
	 * Konstruktor
	 */
	public ShotManager()
	{
		heroShots = new Array<Shot>();
		enemyShots = new Array<Shot>();
		shotsToRemove = new Array<Shot>();
	}

	/**
	 * r�umt sch�sse auf
	 */
	public void cleanUpShots() {
		removeShots(heroShots);
		removeShots(enemyShots);
		shotsToRemove.clear();		
	}
	
	/**
	 * entfernt sch�sse
	 * @param shotArray
	 */
	private void removeShots(final Array<Shot> shotArray) {
		for(final Shot shot : shotsToRemove)
		{
			shotArray.removeValue(shot, false);
		}
	}

	/**
	 * test ob der held getroffen wurde
	 * @param hero
	 * @return
	 */
	public boolean heroSuffering(final Hero hero) {
		for(final Shot shot : enemyShots)
		{
			if(shot.overlaps(hero))
			{
				hero.lowerHealth(shot.getDamage());
				shotsToRemove.add(shot);
			}
		}
		return false;
	}

	/**
	 * f�gt hero sch�sse hinzu
	 * @param shots
	 */
	public void heroShoots(final Array<Shot> shots) {
		if(shots  != null )
			heroShots.addAll(shots);			
	}
	
	/**
	 * bewegt sch�sse
	 * @param delta
	 */
	public void moveShots(final float delta) {
		for(final Shot shot : enemyShots)
			shot.move(delta);
		for(final Shot shot : heroShots)
			shot.move(delta);
	}
	
	/**
	 * getter f�r herosch�sse
	 * @return
	 */
	public Array<Shot> getHeroShots() {
		return heroShots;
	}

	/**
	 * setter f�r heroShots
	 * @param heroShots
	 */
	public void setHeroShots(Array<Shot> heroShots) {
		this.heroShots = heroShots;
	}

	/**
	 * getter f�r enemyshots
	 * @return
	 */
	public Array<Shot> getEnemyShots() {
		return enemyShots;
	}

	/**
	 * setter f�r enemyshots
	 */
	public void setEnemyShots(Array<Shot> enemyShots) {
		this.enemyShots = enemyShots;
	}

	/**
	 * getter f�r zul�schende sch�sse
	 * @return
	 */
	public Array<Shot> getShotsToRemove() {
		return shotsToRemove;
	}

	/**
	 * setter f�r zul�schende sch�sse
	 * @param shotsToRemove
	 */
	public void setShotsToRemove(Array<Shot> shotsToRemove) {
		this.shotsToRemove = shotsToRemove;
	}
	
}
