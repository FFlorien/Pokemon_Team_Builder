
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereCondition;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.Type;

import java.util.List;

public class TypeListLoader extends AbstractAsyncTaskLoader<List<Type>> {

    public TypeListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Type> loadInBackground() {
        DBTableQueryHelper<Type> helper = new DBTableQueryHelper<Type>(getContext());
        List<Type> list = helper.query(new TypeTableTmpForPokemon().selectId().selectName()
                .addWhere(new WhereStatement(TypeTableTmpForPokemon.COLUMN_TYPE_ID, 100, WhereCondition.LESS)));
        return list;
    }

}
