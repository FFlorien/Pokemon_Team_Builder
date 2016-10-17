
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.table.ItemTable;
import be.florien.teambuilder.model.table.MachineTable;
import be.florien.teambuilder.model.table.MoveDamageClassTable;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.TypeTable;

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

        JOQueryHelper dataSource = new JOQueryHelper(new DBPokedexHelper(getContext()));
        TypeTable typeTable = new TypeTable().selectId().selectTypeNames(TranslationTableField.forType());
        MoveMetaTable metaTable = new MoveMetaTable().selectMetaAilmentId();
        MoveTable table =
                new MoveTable().selectMoveNames(TranslationTableField.forMove()).selectPower().selectPp()
                        .selectMachines(new MachineTable().selectId().selectItems(new ItemTable().selectId().selectItemNames(TranslationTableField.forItem())))
                        .selectMoveMeta(metaTable)
                        .selectMoveDamageClasses(new MoveDamageClassTable().selectId())
                        .selectTypes(typeTable
                        );
        if (mFilter != null) {
            mFilter.setFilter(typeTable, metaTable, table);
        }
        List<Move> list = dataSource.queryList(table);
        Collections.sort(list);
        return list;
    }

}
