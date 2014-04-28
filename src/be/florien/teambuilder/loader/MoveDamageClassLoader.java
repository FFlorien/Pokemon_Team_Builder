package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.MoveDamageClassTable;
import be.florien.teambuilder.model.MoveDamageClass;

import java.util.List;

public class MoveDamageClassLoader extends AbstractAsyncTaskLoader<List<MoveDamageClass>> {

    public MoveDamageClassLoader(Context context) {
        super(context);
    }

    @Override
    public List<MoveDamageClass> loadInBackground() {
        MoveDamageClassTable table = new MoveDamageClassTable().selectId().selectName();
        DBTableQueryHelper<MoveDamageClass> dbQueryHelper = new DBTableQueryHelper<MoveDamageClass>(getContext());
        return dbQueryHelper.query(table);
    }

}
