
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import be.florien.joinorm.architecture.DBTable;

import java.util.List;

public class DBTableQueryHelper<T> {

    private DBFixedHelper mDBHelper;

    public DBTableQueryHelper(Context context) {
        mDBHelper = new DBFixedHelper(context);
    }

    @SuppressWarnings("deprecation")
    public List<T> query(DBTable<T> table) {
        try {
            SQLiteQueryBuilder query = new SQLiteQueryBuilder();
            query.setTables(table.getJoinComplete());
            Cursor cursor = query.query(mDBHelper.getDatabase(), table.getProjection(), table.getWhere(), null, null, null, table.getOrderBy());
            Log.d("POKEMON",
                    query.buildQuery(table.getProjection(), table.getWhere(), null, null, null, table.getOrderBy(), null));
            long start = System.nanoTime();
            List<T> result = table.getResult(cursor);
            long stop = System.nanoTime();
            Log.d("PKMN", "Duration for parsing with reflection: " + (stop - start));
            return result;
        }catch (Exception exc) {
            throw exc;
        } finally {
            mDBHelper.close();
        }
    }
}
