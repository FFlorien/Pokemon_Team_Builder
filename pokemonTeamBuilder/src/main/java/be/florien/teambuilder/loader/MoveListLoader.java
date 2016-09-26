
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.ItemTable;
import be.florien.teambuilder.database.table.MachineTable;
import be.florien.teambuilder.database.table.MoveDamageClassTable;
import be.florien.teambuilder.database.table.MoveMetaTable;
import be.florien.teambuilder.database.table.MoveTable;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.model.Move;

import java.util.Collections;
import java.util.List;

public class MoveListLoader extends AbstractAsyncTaskLoader<List<Move>> {

    private MoveFilter mFilter;

    public MoveListLoader(Context context, MoveFilter filter) {
        super(context);
        this.mFilter = filter;
    }

    @Override
    public List<Move> loadInBackground() {

        DBTableQueryHelper<Move> dataSource = new DBTableQueryHelper<Move>(getContext());
        TypeTableTmpForPokemon typeTable = new TypeTableTmpForPokemon().selectId().selectName();
        MoveMetaTable metaTable = new MoveMetaTable().selectAilment();
        MoveTable table =
                new MoveTable().selectName().selectPower().selectPP()
                        .selectMachine(new MachineTable().selectId().selectItem(new ItemTable().selectId().selectName()))
                        .selectMeta(metaTable)
                        .selectDamageClass(new MoveDamageClassTable().selectId())
                        .selectType(typeTable
                        );
        if (mFilter != null) {
            mFilter.setFilter(typeTable, metaTable, table);
        }
        List<Move> list = dataSource.query(table);
        Collections.sort(list);
        return list;
    }

}
