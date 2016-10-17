package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.model.MoveDamageClass;
import be.florien.teambuilder.model.table.MoveDamageClassTable;

import java.util.List;

public class MoveDamageClassLoader extends AbstractAsyncTaskLoader<List<MoveDamageClass>> {

    public MoveDamageClassLoader(Context context) {
        super(context);
    }

    @Override
    public List<MoveDamageClass> loadInBackground() {
        MoveDamageClassTable table = new MoveDamageClassTable().selectId().selectMoveDamageClassProse(TranslationTableField.forMoveDamageClass());
        JOQueryHelper dbQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        return dbQueryHelper.queryList(table);
    }

}
