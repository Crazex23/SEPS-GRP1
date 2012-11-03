package ch.zhaw.arsphema.model.shot;

import ch.zhaw.arsphema.model.AbstractSprite;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.Sizes;
import ch.zhaw.arsphema.util.Sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class OverHeatBar extends AbstractSprite {
	private static final long serialVersionUID = 7293341756258539914L;
	private static final int COLS = 1;
	private static final int ROWS = 2;
	private float level;
	private TextureRegion[][] regions;
	private TextureRegion border;
	private TextureRegion bar;

	
	public OverHeatBar(final float x, final float y, final TextureRegion texture) {
		super(x, y, Sizes.OVERHEATBAR_WIDTH, Sizes.OVERHEATBAR_HEIGHT, texture);
		this.level =0;
		regions = texture.split(texture.getRegionWidth() / COLS, textureRegion.getRegionHeight() / ROWS);
		bar = regions[1][0];
		border = regions[0][0];

	}
	
	

	@Override
	public boolean move(final float delta) {
		return true;
	}

	@Override
	public Array<Shot> shoot(final float delta) {
		return null;
	}

	@Override
	public void draw(final SpriteBatch batch, final float ppuX, final float ppuY) {
		batch.draw(bar, x * ppuX, y * ppuY,0,0, width * ppuX * level, height * ppuY,1,1,90f);
		batch.draw(border, x * ppuX, y * ppuY,0,0, (width) * ppuX * 10, (height) * ppuY,1,1,90f);
	}

	public boolean heat(final float speed) {
		if (level < 10) {
			level += speed * Gdx.graphics.getDeltaTime();
			if (level > 8) {
				if (!Services.getSoundManager().isPlaying(Sounds.DANGER)) {
					Services.getSoundManager().play(Sounds.DANGER, true);
				}
			}
		} else {
			level = 8;
			return true;
		}
		return false;
	}
	
	public void cool(final float speed) {
		if (level > 0) {
			level -= speed * Gdx.graphics.getDeltaTime();
			if (level < 8) {
				Services.getSoundManager().stop(Sounds.DANGER);
			}
		} else {
			level = 0;
		}
	}

}
