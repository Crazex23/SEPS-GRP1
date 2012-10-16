package ch.zhaw.arsphema.model.shot;

import ch.zhaw.arsphema.util.TextureRegions;

import com.badlogic.gdx.utils.Array;

public class ShotFactory
{
    public static final int STANDARD = 0;
	static ShotFactory instance;
    
    
    private ShotFactory()
    {
    }
    
    public static void createInstance()
    {
        instance = new ShotFactory();
    }
    
    public static ShotFactory getInstance()
    {
    	if (instance == null){
    		loadTextures();
    		createInstance();
    	}
        return instance;
    }

    //SHOT LASER
    private static Shot createShot(float x, float y, int type, boolean isEnemyShot)
    {
    	//@stoeffel: type?
    	Shot shot = new Shot(x, y, isEnemyShot);
    	shot.setTextureRegion(TextureRegions.SHOT);
        return shot;
    }
    
    
    private static Array<Shot> reuseArray = new Array<Shot>();
    public static Array<Shot> createShotInArray(float x, float y, int type, boolean isEnemyShot)
    {
    	reuseArray.clear();
    	reuseArray.add(createShot(x, y, type, isEnemyShot));
    	return reuseArray;
    }

    
	public static void loadTextures(){
		
	}

}