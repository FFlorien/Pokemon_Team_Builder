
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereCondition;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.Type;

import java.util.List;

public class TypeListLoader extends AbstractAsyncTaskLoader<List<Type>> {

    public TypeListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Type> loadInBackground() {
        JOQueryHelper helper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        List<Type> list = helper.queryList(new TypeTableTmpForPokemon().selectId().selectName()
                .addWhere(new WhereStatement(TypeTableTmpForPokemon.COLUMN_TYPE_ID, 100, WhereCondition.LESS)));
        return list;
    }

}
