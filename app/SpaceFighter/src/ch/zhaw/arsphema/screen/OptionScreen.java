package ch.zhaw.arsphema.screen;

import ch.zhaw.arsphema.MyGdxGame;
import ch.zhaw.arsphema.model.PlayerProfile;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.UiCompNames;
import ch.zhaw.arsphema.util.UiStyles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class OptionScreen extends UiScreen {


    private Table wrapTable;
    private TextField tfDefaultName;
    private Slider slMusic, slSounds;
    private SlideListener slideListener;
    private PlayerProfile profile;

    public OptionScreen(MyGdxGame game) {
        super(game);
        slideListener = new SlideListener();
        setupGUI();
    }

    private void setupGUI() {
        //Layout Table
        wrapTable = new Table();
        wrapTable.setFillParent(true);
        wrapTable.top().padTop(50);
        stage.addActor(wrapTable);

        //Header
        wrapTable.add(new Label("Options", UiStyles.LABEL_SCREEN_HEADER)).padBottom(20);
        wrapTable.row();

        //Components
        Table compTable = new Table();

        compTable.add(new Label("Default Name", UiStyles.LABEL_DEFAULT)).align(Align.LEFT).padRight(20);
        tfDefaultName = new TextField("", "", UiStyles.TEXT_FIELD_DEFAULT, UiCompNames.TEXTFIELD_DEFAULT_NAME);
        compTable.add(tfDefaultName).pad(10);
        compTable.row();
        compTable.add(new Label("Music", UiStyles.LABEL_DEFAULT)).align(Align.LEFT).padRight(20);
        slMusic = new Slider(0f, 1f, 0.05f, UiStyles.SLIDER_STYLE, UiCompNames.SLIDER_MUSIC_VOL);
        slMusic.setValue(Services.getMusicManager().getVolume());
        slMusic.setValueChangedListener(slideListener);
        compTable.add(slMusic).pad(10);
        compTable.row();
        compTable.add(new Label("Sounds", UiStyles.LABEL_DEFAULT)).align(Align.LEFT).padRight(20);
        slSounds = new Slider(0f, 1f, 0.05f, UiStyles.SLIDER_STYLE, UiCompNames.SLIDER_SOUND_VOL);
        slSounds.setValue(Services.getSoundManager().getVolume());
        slSounds.setValueChangedListener(slideListener);
        compTable.add(slSounds);
        compTable.row();
        TextButton btnSave = new TextButton("Save", UiStyles.BUTTON_DEFAULT, UiCompNames.BUTTON_SAVE_SETTINGS);
        btnSave.setClickListener(new SaveButtonListener());
        compTable.add(btnSave).colspan(2);
        wrapTable.add(compTable);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setCatchBackKey(true);

        profile = Services.getProfileManager().loadPlayerProfile();
        tfDefaultName.setText(profile.getPlayerName());
        slMusic.setValue(profile.getMusicVolume());
        slSounds.setValue(profile.getSoundVolume());
    }

    private class SlideListener implements Slider.ValueChangedListener {

        @Override
        public void changed(Slider slider, float value) {
            if (slMusic.equals(slider)) {
                Services.getMusicManager().setVolume(value);
            } else if (slSounds.equals(slider)) {
                Services.getSoundManager().setVolume(value);
            }
        }
    }

    private class SaveButtonListener implements ClickListener {

        @Override
        public void click(Actor actor, float x, float y) {
            PlayerProfile profile = Services.getProfileManager().loadPlayerProfile();
            profile.setPlayerName(tfDefaultName.getText());
            profile.setMusicVolume(slMusic.getValue());
            profile.setSoundVolume(slSounds.getValue());
            Services.getProfileManager().savePlayerProfile();
            game.setScreen(game.getMainMenuScreen());
        }
    }
}
