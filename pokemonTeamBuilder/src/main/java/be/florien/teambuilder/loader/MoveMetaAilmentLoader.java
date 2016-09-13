package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.MoveMetaAilmentTable;
import be.florien.teambuilder.model.MoveMetaAilment;

import java.util.List;

public class MoveMetaAilmentLoader extends AbstractAsyncTaskLoader<List<MoveMetaAilment>> {

    public MoveMetaAilmentLoader(Context context) {
        super(context);
    }

    @Override
    public List<MoveMetaAilment> loadInBackground() {
        MoveMetaAilmentTable table = new MoveMetaAilmentTable().selectId();
        DBTableQueryHelper<MoveMetaAilment> DBQueryHelper = new DBTableQueryHelper<MoveMetaAilment>(getContext());
        return DBQueryHelper.query(table);
    }

}
