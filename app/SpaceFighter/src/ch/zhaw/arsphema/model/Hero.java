package ch.zhaw.arsphema.model;

import ch.zhaw.arsphema.model.shot.OverHeatBar;
import ch.zhaw.arsphema.model.shot.Shot;
import ch.zhaw.arsphema.model.shot.ShotFactory;
import ch.zhaw.arsphema.util.Effects;
import ch.zhaw.arsphema.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Hero extends AbstractSprite {
	private static final long serialVersionUID = 1L;
	private static final int UP = 1;
	private static final int DOWN = -1;
	
	private static final int ROWS = 1;
	private static final int COLS = 1;
	
	private boolean stopped = true;
	private boolean movingUp = false;
	private boolean movingDown = false;
	private boolean fire = false;
	
	private TextureRegion[] textures;
	
	private OverHeatBar overheatbar;
	private float coolSpeed;
	private float heatSpeed;
	private float shootSpeed = 160;
	private boolean isHurt = false;
	private boolean emitterStarted = false;
	private Array<ParticleEmitter> emitters_burn_baby_burn;
	private Array<ParticleEmitter> emitters_jet;
	private TextureRegion currentTexture;

	public Hero(float x, float y, TextureRegion texture) {
		super(x, y, Sizes.SHIP_WIDTH, Sizes.SHIP_HEIGHT, texture);
		health = 3;
		speed = 66;
		shootingFrequency = 0.1f;
		lastShot=0;
		coolSpeed = 4;
		heatSpeed = 2;
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
        
        overheatbar = OverHeatBar.getInstance();
        
        emitters_burn_baby_burn = new Array(Effects.EXPLOSION_1.getEmitters());
		
		Effects.EXPLOSION_1.getEmitters().add(emitters_burn_baby_burn.get(0));
        
		emitters_jet = new Array(Effects.JET.getEmitters());
		
		Effects.JET.getEmitters().add(emitters_jet.get(0));
		emitters_jet.get(0).start();
		
	}

	public boolean move(float delta){
		if (movingUp){
			this.move(UP, delta);
		} else if (movingDown) {
			this.move(DOWN, delta);
		}
		return true;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		if (fire && lastShot > shootingFrequency && !overheatbar.isOverheated()) {
			lastShot = 0;
			return ShotFactory.createShotInArray(x + width, y + height/3, shootSpeed, ShotFactory.STANDARD, false);
		}
		if (!fire)
		{
			overheatbar.cool(coolSpeed);
		} else {
			if(overheatbar.heat(heatSpeed))
			{
				//TODO delta for overheat (I die to fast :'( ) 
				lowerHealth(1);
			}

		}
		lastShot += delta;
		return null;
	}

	public void moveUp() {
		stop();
		stopped = false;
		movingUp = true;
		
	}
	
	public void moveDown() {
		stop();
		stopped = false;
		movingDown = true;
		
	}
	
	private void move(int direction, float delta) {
		if (this.y + this.height >= Sizes.DEFAULT_WORLD_HEIGHT && movingUp || this.y <= 0 && movingDown) {
			this.stop();
		}
		if (!stopped)
			this.y += direction * this.speed * delta;
		
	}

	public void stop() {
		this.setStopped(true);
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
		this.movingUp = false;
		this.movingDown = false;
	}	
	
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		
		Effects.JET.setPosition( (x)*ppuX,(y+height/2)*ppuY );
		Effects.JET.draw(batch, Gdx.graphics.getDeltaTime());
		batch.draw(currentTexture, ppuX * this.x, ppuY * this.y, ppuX * this.width, ppuY * this.height);
		if (isHurt){
			showExplotion(batch, Gdx.graphics.getDeltaTime(),ppuX, ppuY);
		}
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public boolean lowerHealth(int damage) {
		health -= damage; //TODO shield and stuff check
	    isHurt = true;
	    
		return health <= 0;
	}
	
	private void showExplotion(SpriteBatch batch, float delta, float ppuX, float ppuY){
		if (emitters_burn_baby_burn.get(0).isComplete()){
			isHurt = false;
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

}
