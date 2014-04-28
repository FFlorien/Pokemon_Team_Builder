
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBSimpleQueryHelper;
import be.florien.teambuilder.database.table.MoveTable;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MaxValue;

public class MoveMaxValuesLoader extends AbstractAsyncTaskLoader<MaxValue> {

    public MoveMaxValuesLoader(Context context) {
        super(context);

    }

    @Override
    public MaxValue loadInBackground() {
        MaxValue values = new MaxValue();
        DBSimpleQueryHelper dataQueryHelper = new DBSimpleQueryHelper(getContext());
        String projection = "MAX(" + MoveTable.TABLE_NAME + "." + MoveTable.COLUMN_PP + ")";
        String table = MoveTable.TABLE_NAME;
        values.pp = dataQueryHelper.queryInt(projection, table, null, null);
        projection = "MAX(" + MoveTable.TABLE_NAME + "." + MoveTable.COLUMN_POWER + ")";
        values.power = dataQueryHelper.queryInt(projection, table, null, null);
        dataQueryHelper.close();
        return values;
    }
}
