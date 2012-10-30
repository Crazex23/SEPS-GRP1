package ch.zhaw.arsphema.model.powerup;

import ch.zhaw.arsphema.controller.PowerUpManager;
import ch.zhaw.arsphema.model.shot.ShotFactory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShotGreen extends AbstractPowerUp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3440934168948200706L;

	public ShotGreen(float x, float y, float width, float height,
			TextureRegion texture) {
		super(x, y, width, height, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSomething(PowerUpManager pum) {
		pum.setShot(ShotFactory.Type.GREEN);
	}

	@Override
	public void undoSomething(PowerUpManager pum) {
		// nothing to undo
	}

}
