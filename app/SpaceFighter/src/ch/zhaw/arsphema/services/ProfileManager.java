package ch.zhaw.arsphema.services;


import ch.zhaw.arsphema.model.PlayerProfile;
import ch.zhaw.arsphema.util.Paths;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;


/**
 * JSON FileHandling for the PlayerProfile
 *
 * @author spoerriweb
 */
public class ProfileManager {

    private PlayerProfile profile;

    public ProfileManager() {
    }

    /**
     * Loads the players profile from the stored JSON File
     *
     * @return The players profile
     */
    public PlayerProfile loadPlayerProfile() {
        FileHandle profileFile = Gdx.files.local(Paths.PLAYER_PROFILE);

        // return the profile if loaded
        if (profile != null) {
            return profile;
        }

        //JSON Utility
        Json json = new Json();

        if (profileFile.exists()) {

            try {
                //read the JSON String
                String profileEncoded = profileFile.readString().trim();

                //Decode
                String profileString = Base64Coder.decodeString(profileEncoded);

                // restore player profile
                profile = json.fromJson(PlayerProfile.class, profileString);

            } catch (Exception e) {
                System.out.println("Error loading Profile -> create new Profile");
                //Create a new Profile if loading error occours
                profile = new PlayerProfile();
                savePlayerProfile(profile);
            }

        } else {
            // create a new player profile
            profile = new PlayerProfile();
            savePlayerProfile(profile);
        }

        return profile;
    }


    /**
     * Saves the players profile to the JSON file
     *
     * @param profile the player profile to save
     */
    protected void savePlayerProfile(PlayerProfile profile) {

        FileHandle profileDataFile = Gdx.files.local(Paths.PLAYER_PROFILE);

        //JSON Utility
        Json json = new Json();

        // convert to json
        String profileAsText = json.toJson(profile);

        //Encode Profile
        String profileEncoded = Base64Coder.encodeString(profileAsText);

        // write the profile to the JSON file
        profileDataFile.writeString(profileEncoded, false);
    }

    /**
     * Quick profile save
     */
    public void savePlayerProfile() {
        if (profile != null) {
            savePlayerProfile(profile);
        }
    }
}

