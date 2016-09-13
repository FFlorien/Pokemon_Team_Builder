
package be.florien.teambuilder.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import be.florien.joinorm.architecture.DBDelete;
import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.architecture.DBWrite;

import java.util.ArrayList;
import java.util.List;

public class DBUserQueryHelper<T> {

    private DBUserHelper mDBHelper;

    public DBUserQueryHelper(Context context) {
        mDBHelper = new DBUserHelper(context);
    }

    @SuppressWarnings("deprecation")
    public List<T> query(DBTable<T> table) {
        try {
            mDBHelper.open();
            SQLiteQueryBuilder query = new SQLiteQueryBuilder();
            query.setTables(table.getJoinComplete());
            Cursor cursor = query.query(mDBHelper.getDatabase(), table.getProjection(), table.getWhere(), null, null, null, table.getOrderBy());
            Log.d("POKEMON",
                    query.buildQuery(table.getProjection(), table.getWhere(), null, null, null, table.getOrderBy(), null));
            return table.getResult(cursor);
        } finally {
            mDBHelper.close();
        }
    }

    public void insert(DBTable<T> table) {
        mDBHelper.getDatabase().beginTransaction();
        try {
            List<DBWrite> write = new ArrayList<DBWrite>();
            table.getValuesToWrite(write, "");
            for (DBWrite toWrite : write) {
                mDBHelper.getDatabase().insert(toWrite.getTableName(), null, toWrite.getValue());
            }
            mDBHelper.getDatabase().setTransactionSuccessful();
        } finally {
            mDBHelper.getDatabase().endTransaction();
            mDBHelper.close();
        }
    }

    public void delete(DBTable<T> table) {
        mDBHelper.getDatabase().beginTransaction();
        try {
            List<DBDelete> deletes = table.getDelete();
            for (DBDelete delete : deletes) {
                mDBHelper.getDatabase().delete(delete.getTableName(), delete.getWhereClause(), new String[] {
                        delete.getWhereArgs()
                });
            }
            mDBHelper.getDatabase().setTransactionSuccessful();
        } finally {
            mDBHelper.getDatabase().endTransaction();
            mDBHelper.close();
        }
    }
}
