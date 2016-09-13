
package be.florien.teambuilder.appconfiguration;

import android.content.SharedPreferences.Editor;

import be.florien.teambuilder.application.PokedexApplication;
import be.florien.teambuilder.model.LanguageEnum;

import java.util.Locale;

public class Constant {

    private static final String PREF_LANGUAGE = "PREF_LANGUAGE";
    private static final String PREFERENCES = "PREFERENCES";
    private static final int VERSION_GROUP_ID = 14;
    
    private static int mLanguageId = -1;

    // TODO add form change of motisma(rotom) in pokemon_moves for version group 15

    public static int getVersionGroupId() {
        return VERSION_GROUP_ID;
    }

    public static int getLanguageId() {
        if(mLanguageId != -1){
            return mLanguageId;
        }
        int language = PokedexApplication.getContext().getSharedPreferences(PREFERENCES, 0).getInt(PREF_LANGUAGE, -1);
        if (language == -1) {
            String languageCode = Locale.getDefault().getLanguage();
            LanguageEnum value = LanguageEnum.getLanguageForIso(languageCode);
            if (value != LanguageEnum.NONE) {
                language = value.getid();
            } else {
                language = 9;
            }
            setLanguageId(language);
        }
        mLanguageId = language;
        return language;
    }

    public static void setLanguageId(int languageId) {
        mLanguageId = languageId;
        Editor editor = PokedexApplication.getContext().getSharedPreferences(PREFERENCES, 0).edit();
        editor.putInt(PREF_LANGUAGE, languageId);
        editor.commit();
    }

}
