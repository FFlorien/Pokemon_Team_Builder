
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.model.PokemonMoveForPokemon;
import be.florien.teambuilder.model.table.ItemTable;
import be.florien.teambuilder.model.table.MachineTable;
import be.florien.teambuilder.model.table.MoveDamageClassTable;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.PokemonMoveForPokemonTable;
import be.florien.teambuilder.model.table.TypeTable;

import java.util.List;

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
        TypeTable typeTable = new TypeTable().selectId().selectTypeNames(TranslationTableField.forLanguage()/*todo*/);
        MoveMetaTable metaTable = new MoveMetaTable().selectMetaAilmentId();
        MoveTable moveTable = new MoveTable().selectMoveNames(TranslationTableField.forGeneration()/*todo*/).selectPower().selectPp()
                .selectMachines(new MachineTable().selectId().selectItems(new ItemTable().selectId().selectItemNames(TranslationTableField.forGeneration()/*todo*/)))
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
