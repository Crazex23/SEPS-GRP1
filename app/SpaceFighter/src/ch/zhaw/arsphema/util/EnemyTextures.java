package ch.zhaw.arsphema.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyTextures {

	public static final TextureRegion BLOB;
	public static final TextureRegion UFO;
	public static final TextureRegion SAUCER_EASY;
	public static final TextureRegion SAUCER_MEDIUM;
	public static final TextureRegion BOITUMELO;
	public static final TextureRegion HIDAI;
	public static final TextureRegion UFO_BAD_BOY;

	static {
		UFO = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_UFO, Texture.class));
		SAUCER_EASY = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_SAUCER_EASY, Texture.class));
		SAUCER_MEDIUM = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_SAUCER_MEDIUM, Texture.class));
		BLOB = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_BLOB, Texture.class));
		BOITUMELO = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_BOITUMELO, Texture.class));
		HIDAI = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_HIDAI, Texture.class));
		UFO_BAD_BOY = new TextureRegion(SpaceAssetManager.getInstance().get(Paths.ENEMY_BAD_BOY, Texture.class));
	}
}
