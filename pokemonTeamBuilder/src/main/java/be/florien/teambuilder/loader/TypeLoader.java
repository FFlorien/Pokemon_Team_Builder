
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TypeEfficacityAsAttackTable;
import be.florien.teambuilder.database.table.TypeEfficacityAsDefenseTable;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.Type;

public class TypeLoader extends AbstractAsyncTaskLoader<Type> {

    private int mId;

    public TypeLoader(Context context, int id) {
        super(context);
        mId = id;
    }

    @Override
    public Type loadInBackground() {
        TypeTableTmpForPokemon table =
                new TypeTableTmpForPokemon()
                        .selectId()
                        .selectIdentifier()
                        .selectName()
                        .selectTypeEfficacityAsAttack(
                                new TypeEfficacityAsAttackTable().selectId().selectDamageFactor()
                                        .selectTargetType(new TypeTableTmpForPokemon().selectId()))
                        .selectTypeEfficacityAsDefense(
                                new TypeEfficacityAsDefenseTable().selectId().selectDamageFactor()
                                        .selectDamageType(new TypeTableTmpForPokemon().selectId())
                        );
        table.addWhere(new WhereStatement(TypeTableTmpForPokemon.COLUMN_TYPE_ID, String.valueOf(mId)));
        DBTableQueryHelper<Type> queryHelper = new DBTableQueryHelper<Type>(getContext());
        List<Type> list = queryHelper.query(table);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
