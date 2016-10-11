
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
            List<T> result = table.getResult(cursor);
            return result;
        } finally {
            mDBHelper.close();
        }
    }
}
