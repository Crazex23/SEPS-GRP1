package ch.zhaw.arsphema.screen;


import ch.zhaw.arsphema.MyGdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;


public abstract class AbstractScreen implements Screen
{

	protected final MyGdxGame game;
    protected final Stage stage;

    public AbstractScreen(MyGdxGame game)
    {
        this.game = game;
        this.stage = new Stage( 0, 0, true );
    }

	@Override
	public void resize(int width, int height) 
	{
		stage.setViewport(width, height, true);
	}

    @Override
    public void render(
        float delta )
    {
        // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // update and draw the stage actors
        stage.act( delta );
        stage.draw();
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        // dispose the collaborators
        stage.dispose();
    }

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}