
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MaxValue;
import be.florien.teambuilder.model.table.MoveTable;

public class MoveMaxValuesLoader extends AbstractAsyncTaskLoader<MaxValue> {

    public MoveMaxValuesLoader(Context context) {
        super(context);

    }

    @Override
    public MaxValue loadInBackground() {
        MaxValue values = new MaxValue();
        JOQueryHelper dataQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        String projection = "MAX(moves." + MoveTable.COLUMN_PP + ")";
        String table = "moves";
        values.pp = dataQueryHelper.queryInt(projection, table, null, null);
        projection = "MAX(moves." + MoveTable.COLUMN_POWER + ")";
        values.power = dataQueryHelper.queryInt(projection, table, null, null);
        dataQueryHelper.close();
        return values;
    }
}
