package ch.zhaw.arsphema.screen;

import ch.zhaw.arsphema.MyGdxGame;
import ch.zhaw.arsphema.model.PlayerProfile;
import ch.zhaw.arsphema.services.Services;
import ch.zhaw.arsphema.util.UiStyles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class OptionScreen extends UiScreen {

    private Table compTable;
    private Label lbTitle;
    private Label lbPlayerName;
    private Label lbMusic;
    private Label lbSound;
    private TextField tfDefaultName;
    private Slider slMusic, slSounds;
    private Button btnAccept;
    private Button btnBack;
    private PlayerProfile profile;

    public OptionScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        ClickListener buttonListener = new SettingsButtonListener();

        lbTitle = new Label("Options", UiStyles.LABEL_SCREEN_HEADER);

        compTable = new Table();
        lbPlayerName = new Label("Player Name", UiStyles.LABEL_DEFAULT);
        tfDefaultName = new TextField("", "", UiStyles.TEXT_FIELD_DEFAULT);
        lbMusic = new Label("Music Volume", UiStyles.LABEL_DEFAULT);
        slMusic = new Slider(0f, 1f, 0.05f, UiStyles.SLIDER_STYLE);
        lbSound = new Label("Sound Volume", UiStyles.LABEL_DEFAULT);
        slSounds = new Slider(0f, 1f, 0.05f, UiStyles.SLIDER_STYLE);

        btnAccept = new Button(new TextureRegion(UiStyles.UI_ICON_TEXTURE_REGION, 0, 600, 300, 300));
        btnAccept.setClickListener(buttonListener);
        btnBack = new Button(new TextureRegion(UiStyles.UI_ICON_TEXTURE_REGION, 600, 0, 300, 300));
        btnBack.setClickListener(buttonListener);
    }

    @Override
    protected void setupGui() {
        super.setupGui();

        //Header
        wrapTable.add(lbTitle).padBottom((int) (5 * ppuY)).padTop((int) (5 * ppuY));
        wrapTable.row();

        compTable.clear();
        compTable.add(lbPlayerName).align(Align.LEFT);
        compTable.add(tfDefaultName).align(Align.RIGHT);
        compTable.row();
        compTable.add(lbMusic).align(Align.LEFT);
        compTable.add(slMusic).align(Align.RIGHT);
        compTable.row();
        compTable.add(lbSound).align(Align.LEFT);
        compTable.add(slSounds).align(Align.RIGHT);

        compTable.width((int) (60 * ppuX));
        wrapTable.add(compTable);

        //Setup Button Row
        addToButtonRow(btnBack);
        addToButtonRow(btnAccept);
        wrapTable.row();
        wrapTable.add(buttonTable).bottom().expandY();
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

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        setupGui();
    }

    private class SettingsButtonListener implements ClickListener {

        @Override
        public void click(Actor actor, float x, float y) {
            if (btnAccept.equals(actor)) {
                //Set Music/Sound
                Services.getMusicManager().setVolume(slMusic.getValue());
                Services.getSoundManager().setVolume(slSounds.getValue());

                //Save to Profile
                profile.setPlayerName(tfDefaultName.getText());
                profile.setMusicVolume(slMusic.getValue());
                profile.setSoundVolume(slSounds.getValue());
                Services.getProfileManager().savePlayerProfile();
                game.setScreen(game.getMainMenuScreen());
            } else if (btnBack.equals(actor)) {
                uiController.keyDown(Input.Keys.BACK);
            }
        }
    }
}
