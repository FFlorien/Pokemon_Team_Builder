
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DBSimpleQueryHelper {

    public static final int INT_NOT_FOUND = -404;
    private DBFixedHelper mDBHelper;

    public DBSimpleQueryHelper(Context context) {
        mDBHelper = new DBFixedHelper(context);
    }

    @SuppressWarnings("deprecation")
    public int queryInt(String projection, String tables, String where, String orderBy) {
        SQLiteQueryBuilder query = new SQLiteQueryBuilder();
        String projections[] = new String[1];
        projections[0] = projection;
        query.setTables(tables);
        Cursor cursor = query.query(mDBHelper.getDatabase(), projections, where, null, null, null, orderBy);
        Log.d("POKEMON",
                query.buildQuery(projections, where, null, null, null, orderBy, null));
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            return cursor.getInt(0);
        } else {
            return INT_NOT_FOUND;
        }
    }

    public void close() {
        mDBHelper.close();
    }
}
