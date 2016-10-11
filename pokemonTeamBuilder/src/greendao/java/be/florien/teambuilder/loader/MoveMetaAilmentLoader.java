package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.model.MoveMetaAilment;
import be.florien.teambuilder.model.table.MoveMetaAilmentTable;

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
