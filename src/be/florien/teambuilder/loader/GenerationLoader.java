package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.GenerationTable;
import be.florien.teambuilder.model.Generation;

import java.util.List;

public class GenerationLoader extends AbstractAsyncTaskLoader<List<Generation>> {

    public GenerationLoader(Context context) {
        super(context);
    }

    @Override
    public List<Generation> loadInBackground() {
        GenerationTable table = new GenerationTable().selectId().selectName();
        DBTableQueryHelper<Generation> dbQueryHelper = new DBTableQueryHelper<Generation>(getContext());
        return dbQueryHelper.query(table);
    }

}
