package ch.zhaw.arsphema.model.shot;

import ch.zhaw.arsphema.model.AbstractSprite;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.Sizes;
import ch.zhaw.arsphema.util.Sounds;
import ch.zhaw.arsphema.util.TextureRegions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class OverHeatBar extends AbstractSprite {
	private static final long serialVersionUID = 7293341756258539914L;
	private static final int COLS = 1;
	private static final int ROWS = 2;
	private static OverHeatBar instance;
	private float level;
	private TextureRegion[][] regions;
	private TextureRegion border;
	private TextureRegion bar;

	
	private OverHeatBar(float x, float y, TextureRegion texture) {
		super(x, y, Sizes.OVERHEATBAR_WIDTH, Sizes.OVERHEATBAR_HEIGHT, texture);
		this.level =0;
		regions = texture.split(texture.getRegionWidth() / COLS, textureRegion.getRegionHeight() / ROWS);
		bar = regions[1][0];
		border = regions[0][0];

	}
	
	private static void createInstance()
    {
        instance = new OverHeatBar(Sizes.DEFAULT_WORLD_WIDTH - 2, Sizes.DEFAULT_WORLD_HEIGHT/5*4, TextureRegions.OVERHEATBAR);
    }
    
    public static OverHeatBar getInstance()
    {
    	if (instance == null){
    		createInstance();
    	}
        return instance;
    }

	@Override
	public boolean move(float delta) {
		return true;
	}

	@Override
	public Array<Shot> shoot(float delta) {
		return null;
	}

	@Override
	public void draw(SpriteBatch batch, float ppuX, float ppuY) {
		batch.draw(bar, x * ppuX, y * ppuY,0,0, width * ppuX * level, height * ppuY,1,1,90f);
		batch.draw(border, x * ppuX, y * ppuY,0,0, (width) * ppuX * 10, (height) * ppuY,1,1,90f);
	}

	public float getLevel() {
		return level;
	}

	public void setLevel(float level) {
		this.level = level;
	}

	public boolean heat(float speed) {
		if (this.getLevel() < 10) {
			this.setLevel(this.getLevel() + speed * Gdx.graphics.getDeltaTime());
			if (this.getLevel() > 8) {
				if (!Services.getSoundManager().isPlaying(Sounds.DANGER)) {
					Services.getSoundManager().play(Sounds.DANGER, true);
				}
			}
		} else {
			this.setLevel(8);
			return true;
		}
		return false;
	}
	
	public void cool(float speed) {
		if (this.getLevel() > 0) {
			this.setLevel(this.getLevel() - speed * Gdx.graphics.getDeltaTime());
			if (this.getLevel() < 8) {
				Services.getSoundManager().stop(Sounds.DANGER);
			}
		} else {
			this.setLevel(0);
		}
	}



}
