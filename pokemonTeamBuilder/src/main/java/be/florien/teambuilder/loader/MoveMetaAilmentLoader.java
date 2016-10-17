package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.model.MoveMetaAilment;
import be.florien.teambuilder.model.table.MoveMetaAilmentTable;

import java.util.List;

public class MoveMetaAilmentLoader extends AbstractAsyncTaskLoader<List<MoveMetaAilment>> {

    public MoveMetaAilmentLoader(Context context) {
        super(context);
    }

    @Override
    public List<MoveMetaAilment> loadInBackground() {
        MoveMetaAilmentTable table = new MoveMetaAilmentTable().selectId();
        JOQueryHelper DBQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        return DBQueryHelper.queryList(table);
    }

}
