
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.ItemTable;
import be.florien.teambuilder.database.table.MachineTable;
import be.florien.teambuilder.database.table.MoveDamageClassTable;
import be.florien.teambuilder.database.table.MoveMetaTable;
import be.florien.teambuilder.database.table.MoveTable;
import be.florien.teambuilder.database.table.PokemonMoveForPokemonTable;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.MoveListFilterDialogFragment.MoveFilter;
import be.florien.teambuilder.model.PokemonMoveForPokemon;

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

        DBTableQueryHelper<PokemonMoveForPokemon> dataSource = new DBTableQueryHelper<PokemonMoveForPokemon>(getContext());
        TypeTableTmpForPokemon typeTable = new TypeTableTmpForPokemon().selectId().selectName();
        MoveMetaTable metaTable = new MoveMetaTable().selectAilment();
        MoveTable moveTable = new MoveTable().selectName().selectPower().selectPP()
                .selectMachine(new MachineTable().selectId().selectItem(new ItemTable().selectId().selectName()))
                .selectMeta(metaTable)
                .selectDamageClass(new MoveDamageClassTable().selectId())
                .selectType(typeTable
                );
        PokemonMoveForPokemonTable table = new PokemonMoveForPokemonTable().selectLevel().selectMethod().selectMove(
                moveTable);
        if (mFilter != null) {
            mFilter.setFilter(typeTable, metaTable, moveTable);
        }
        table.addWhere(new WhereStatement(PokemonMoveForPokemonTable.COLUMN_POKEMON_ID, mId));
        List<PokemonMoveForPokemon> list = dataSource.query(table);
        return list;
    }

}
