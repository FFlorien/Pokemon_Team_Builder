
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBPokedexHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "veekun-pokedex.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DBPokedexHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}
