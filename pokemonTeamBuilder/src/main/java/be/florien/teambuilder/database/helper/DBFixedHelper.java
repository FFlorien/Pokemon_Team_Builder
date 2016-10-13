
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBFixedHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "veekun-pokedex.sqlite";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDatabase;

    public void open() {
        mDatabase = getReadableDatabase();
    }

    public void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public DBFixedHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase getDatabase() {
        if (mDatabase == null) {
            open();
        }
        return mDatabase;
    }

}
