
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.databasecomplexjoins.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TypeEfficacityAsAttackTable;
import be.florien.teambuilder.database.table.TypeEfficacityAsDefenseTable;
import be.florien.teambuilder.database.table.TypeTable;
import be.florien.teambuilder.model.Type;

public class TypeLoader extends AbstractAsyncTaskLoader<Type> {

    private int mId;

    public TypeLoader(Context context, int id) {
        super(context);
        mId = id;
    }

    @Override
    public Type loadInBackground() {
        TypeTable table =
                new TypeTable()
                        .selectId()
                        .selectIdentifier()
                        .selectName()
                        .selectTypeEfficacityAsAttack(
                                new TypeEfficacityAsAttackTable().selectId().selectDamageFactor()
                                        .selectTargetType(new TypeTable().selectId()))
                        .selectTypeEfficacityAsDefense(
                                new TypeEfficacityAsDefenseTable().selectId().selectDamageFactor()
                                        .selectDamageType(new TypeTable().selectId())
                        );
        table.addWhere(new WhereStatement(TypeTable.COLUMN_TYPE_ID, String.valueOf(mId)));
        DBTableQueryHelper<Type> queryHelper = new DBTableQueryHelper<Type>(getContext());
        List<Type> list = queryHelper.query(table);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
