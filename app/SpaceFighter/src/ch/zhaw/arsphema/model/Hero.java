package ch.zhaw.arsphema.model;

import ch.zhaw.arsphema.model.powerup.AbstractPowerUp;
import ch.zhaw.arsphema.model.shot.OverHeatBar;
import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.model.shot.ShotFactory;
import ch.zhaw.arsphema.model.shot.ShotFactory.Type;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.Effects;
import ch.zhaw.arsphema.util.Sizes;
import ch.zhaw.arsphema.util.Sounds;
import ch.zhaw.arsphema.util.TextureRegions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * klasse fuer den spieler avatar aka hero
 */
public class Hero extends AbstractSprite {
	private static final long serialVersionUID = 1L;
	private static final int UP = 1;
	private static final int DOWN = -1;
	
	private static final int ROWS = 3;
	private static final int COLS = 1;
	
	private boolean stopped = true;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean fire = false;
	private boolean dead = false;

	private TextureRegion[] textures;
	
	private OverHeatBar overheatbar;
	private float coolSpeed;
	private float heatSpeed;
	private float shootSpeed = 160;
	private ShotFactory.Type shotType;
	private boolean isHurt = false;
	private boolean emitterStarted = false;
	private Array<ParticleEmitter> emitters_burn_baby_burn;
	private Array<ParticleEmitter> emitters_jet;
	private TextureRegion currentTexture;
	private LifeCounter lifeCounter;
	private TextureRegion[] blinkFrames;
	private Animation blinkAnimation;
	private float stateTime;
	private AbstractPowerUp powerUp;
	private boolean oneTimeKillerShot;
	public float lastY;

	/**
	 * konstruktor
	 */
	public Hero(float x, float y, TextureRegion texture) {
		super(x, y, Sizes.SHIP_WIDTH, Sizes.SHIP_HEIGHT, texture);
		setUpAttributes();
		extractTexture(texture);

        overheatbar = new OverHeatBar(Sizes.DEFAULT_WORLD_WIDTH - 2, Sizes.DEFAULT_WORLD_HEIGHT/5*4, TextureRegions.OVERHEATBAR);
        createJetStream();
		createLifeCounter(texture);
		shotType = ShotFactory.Type.STANDARD;
		overheatbar.setShootingFrequency(shootingFrequency);
	}


	public Hero(float x, float y){
		super(x, y, Sizes.SHIP_WIDTH, Sizes.SHIP_HEIGHT, null);
		setUpAttributes();
	}

	/**
	 * stellt die initialen standard attribute ein
	 */
	private void setUpAttributes() {
		health = 3;
		speed = 66;
		shootingFrequency = 0.1f;
		lastShot=0;
		coolSpeed = 4;
		heatSpeed = 2;
		lastY = 0;
		oneTimeKillerShot = false;
	}

	/**
	 * erstellt den jetstream hinter dem schiff
	 */
	private void createJetStream() {
		emitters_burn_baby_burn = new Array<ParticleEmitter>(Effects.EXPLOSION_1.getEmitters());
		Effects.EXPLOSION_1.getEmitters().add(emitters_burn_baby_burn.get(0));
		emitters_jet = new Array<ParticleEmitter>(Effects.JET.getEmitters());
		Effects.JET.getEmitters().add(emitters_jet.get(0));
		emitters_jet.get(0).start();
	}

	private void createLifeCounter(TextureRegion texture) {
		lifeCounter = new LifeCounter(Sizes.DEFAULT_WORLD_WIDTH/20, Sizes.DEFAULT_WORLD_HEIGHT - Sizes.DEFAULT_WORLD_HEIGHT/20, width/3, height/3, texture);
		lifeCounter.setLifes(health);
		lifeCounter.setMaxLifes(health);
	}

	private void extractTexture(TextureRegion texture) {
		TextureRegion[][] tmp = texture.split( 
				texture.getRegionWidth() / COLS, texture.getRegionHeight() / ROWS);
		textures = new TextureRegion[COLS * ROWS];

		int index = 0;
        for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                        textures[index++] = tmp[i][j];
                }
        }
        currentTexture = textures[0];
        
        // blink animation
        blinkFrames = new TextureRegion[2];
        blinkFrames[0] = textures[0];
        blinkFrames[1] = textures[2];
        blinkAnimation = new Animation(0.25f, blinkFrames);
        blinkAnimation.setPlayMode(Animation.LOOP);
	}
	
	
	/**
	 * bewegt den hero
	 * @param delta das rendering delta
	 */
	public boolean move(float delta){
		if (movingUp){
			move(UP, delta);
		} else if (movingDown) {
			move(DOWN, delta);
		}
		return true;
	}

	/**
	 * laesst den hero schiessen
	 * @param delta das renderings delta
	 */
	@Override
	public Array<Shot> shoot(float delta) {
		if (fire && lastShot > shootingFrequency) {
			lastShot = 0;
			heatGun(delta);
			if (oneTimeKillerShot) {
				oneTimeKillerShot = false;
				return ShotFactory.createShotInArray(x + width*5/6, 0, shootSpeed, Type.ULTIMATE, false);
			} else {
				return ShotFactory.createShotInArray(x + width*5/6, y + height/3, shootSpeed, shotType, false);
			}
		}
		if (!fire)
		{
			overheatbar.cool(coolSpeed);
		} else {
			heatGun(delta);
		}
		lastShot += delta;
		return null;
	}
	
	private void heatGun(final float delta)
	{
		if (overheatbar.heat(heatSpeed)) {
			lowerHealth(1);
		}
	}

	/**
	 * bewegt den hero hinauf
	 */
	public void moveUp() {
		stop();
		stopped = false;
		movingUp = true;
		
	}
	
	/**
	 * bewegt den hero hinunter
	 */
	public void moveDown() {
		stop();
		stopped = false;
		movingDown = true;
		
	}
	
	private void move(int direction, float delta) {
		if (y + height >= Sizes.DEFAULT_WORLD_WIDTH && movingUp || y <= 0 && movingDown) {
			stop();
		}
		if (!stopped)
		{
			y += direction * speed * delta;
			if(y + height >= Sizes.DEFAULT_WORLD_HEIGHT)
				y = Sizes.DEFAULT_WORLD_HEIGHT - height;
			else if(y <= 0 && movingDown)
				y = 0;
		}
		
	}

	/**
	 * stoped das spiel
	 */
	public void stop() {
		this.setStopped(true);
	}
	/**
	 * gibt zurueck ob das spiel gestopt ist
	 * @return stopped den zustand des spiels
	 */
	public boolean isStopped() {
		return stopped;
	}
	/**
	 * setzt den stop zustand
	 * @param stopped den zustand
	 */
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
		this.movingUp = false;
		this.movingDown = false;
	}	
	
	/**
	 * zeichnet den hero
	 */
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		
		Effects.JET.setPosition( (x)*ppuX,(y+height/2)*ppuY );
		Effects.JET.draw(batch, Gdx.graphics.getDeltaTime());

		lastY = Sizes.DEFAULT_WORLD_HEIGHT * ppuY - ppuY * y;
		if (isHurt && stateTime < 2){
			stateTime += Gdx.graphics.getDeltaTime();
			showExplotion(batch, Gdx.graphics.getDeltaTime(),ppuX, ppuY);
			batch.draw(blinkAnimation.getKeyFrame(stateTime), ppuX * x, ppuY * this.y, ppuX * this.width, ppuY * this.height);
		} else {
			isHurt = false;
			stateTime = 0;
			batch.draw(currentTexture, ppuX * x, ppuY * this.y, ppuX * this.width, ppuY * this.height);
		}
		lifeCounter.draw(batch, ppuX, ppuY);
		overheatbar.draw(batch, ppuX, ppuY);
	}

	/**
	 * setzt schuss
	 * @param fire zustand des schusses
	 */
	public void setFire(boolean fire) {
		this.fire = fire;
	}

	/**
	 * zieht dem hero leben ab
	 * @param damage die anzahl lebenspunkte die abgezogen werden
	 */
	public void lowerHealth(int damage) {
		if (isHurt) return; // don't hurt him again
		isHurt = true;
		handlePowerUp();
		health -= damage;
		lifeCounter.setLifes(health);
		Services.getSoundManager().play(Sounds.HURT, false);
	    
		dead = health <= 0;
	}


	/**
	 * @return true if had powerups
	 */
	private boolean handlePowerUp() {
		if(powerUp != null){
			powerUp.undoSomething(this);
			powerUp = null;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * addiert ein leben
	 */
	public void oneUp() {
		health++;
		lifeCounter.oneUp();
	}
	
	private void showExplotion(SpriteBatch batch, float delta, float ppuX, float ppuY){
		if (emitters_burn_baby_burn.get(0).isComplete()){
			emitters_burn_baby_burn.get(0).reset();
			emitterStarted = false;
			return;
		}
		Effects.EXPLOSION_1.setPosition( (x+width/2)*ppuX,(y+height)*ppuY );

		if (!emitterStarted){
			emitters_burn_baby_burn.get(0).start();
			emitterStarted = true;
		}

		Effects.EXPLOSION_1.draw(batch, Gdx.graphics.getDeltaTime());
		
	}

	/**
	 * gibt zurueck ob der hero tot ist
	 * @return dead der zustand des heroes
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * erweitert die schussfrequenz
	 */
	public void enhanceShot(){
		if (shootingFrequency > 0.02f)
			shootingFrequency -= 0.01f;
		setShotType();
	}
	
	/**
	 * setzt die schussfrequenz um 1 eins zurueck
	 */
	public void undoEnhancements(){
		if (shootingFrequency < 0.1f)
			shootingFrequency += 0.02f;
		setShotType();
	}
	/**
	 * setzt die schussart
	 */
	public void setShotType() {
		overheatbar.setShootingFrequency(shootingFrequency);
		if (shootingFrequency <= 0.1 && shootingFrequency >= 0.08) {
			shotType = ShotFactory.Type.STANDARD;
		} else if (shootingFrequency < 0.08 && shootingFrequency >= 0.05) {
			shotType = ShotFactory.Type.GREEN;
		} else if (shootingFrequency < 0.05) {
			shotType = ShotFactory.Type.BLUE;
		}
	}

	/**
	 * addiert ein power up dem hero hinzu
	 */
	public void addPowerUps(AbstractPowerUp powerUp) {
		
		if (powerUp.doSomething(this)) {
			this.powerUp = powerUp;
		}
	}

	/**
	 * benutz das killAll powerup
	 */
	public void useTheUltimateWeapon() {
		oneTimeKillerShot = true;
	}

}
