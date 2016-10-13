
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.model.PokemonMoveForPokemon;
import be.florien.teambuilder.model.table.ItemTable;
import be.florien.teambuilder.model.table.MachineTable;
import be.florien.teambuilder.model.table.MoveDamageClassTable;
import be.florien.teambuilder.model.table.MoveMetaAilmentTable;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.PokemonMoveForPokemonTable;
import be.florien.teambuilder.model.table.TypeTable;

public class PokemonMoveForPokemonListLoader extends AbstractAsyncTaskLoader<List<PokemonMoveForPokemon>> {

    private MoveFilter mFilter;
    private int mId;

    public PokemonMoveForPokemonListLoader(Context context, MoveFilter where, int id) {
        super(context);
        this.mFilter = where;
        this.mId = id;
    }

    @Override
    public List<PokemonMoveForPokemon> loadInBackground() {

        DBTableQueryHelper<PokemonMoveForPokemon> dataSource = new DBTableQueryHelper<>(getContext());
        TypeTable typeTable = new TypeTable().selectId().selectTypeNames(TranslationTableField.forType());
        MoveMetaTable metaTable = new MoveMetaTable().selectMoveMetaAilments(new MoveMetaAilmentTable().selectId());
        MoveTable moveTable = new MoveTable().selectMoveNames(TranslationTableField.forMove()).selectPower().selectPp()
                .selectMachines(new MachineTable().selectId().selectItems(new ItemTable().selectId().selectItemNames(TranslationTableField.forItem())))
                .selectMoveMeta(metaTable)
                .selectMoveDamageClasses(new MoveDamageClassTable().selectId())
                .selectTypes(typeTable
                );
        PokemonMoveForPokemonTable table = new PokemonMoveForPokemonTable().selectLevel().selectPokemonMoveMethodId().selectMoves(
                moveTable);
        if (mFilter != null) {
            mFilter.setFilter(typeTable, metaTable, moveTable);
        }
        table.addWhere(new WhereStatement(PokemonMoveForPokemonTable.COLUMN_POKEMON_ID, mId));
        return dataSource.query(table);
    }

}
