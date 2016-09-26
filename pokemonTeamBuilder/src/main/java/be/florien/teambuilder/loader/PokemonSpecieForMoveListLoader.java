
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.PokemonFormTable;
import be.florien.teambuilder.database.table.PokemonMoveForPokemonTable;
import be.florien.teambuilder.database.table.PokemonSpecieTable;
import be.florien.teambuilder.database.table.PokemonTable;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.model.PokemonSpecie;

import java.util.List;

public class PokemonSpecieForMoveListLoader extends AbstractAsyncTaskLoader<List<PokemonSpecie>> {

    private SpecieFilter mFilter;
    private int mMoveId;

    public PokemonSpecieForMoveListLoader(Context context, SpecieFilter filter, int moveId) {
        super(context);
        this.mFilter = filter;
        this.mMoveId = moveId;
    }

    @Override
    public List<PokemonSpecie> loadInBackground() {

        DBTableQueryHelper<PokemonSpecie> dataSource = new DBTableQueryHelper<PokemonSpecie>(getContext());

        TypeTableTmpForPokemon typeTable = new TypeTableTmpForPokemon().selectId();
        PokemonFormTable pokemonFormTable = new PokemonFormTable().selectId().selectName();
        PokemonMoveForPokemonTable pokemonMoveForPokemonTable = new PokemonMoveForPokemonTable().selectId().selectLevel().selectMethod();
        PokemonTable pokemonTable = new PokemonTable().selectId()
                .selectType(typeTable)
                .selectForm(pokemonFormTable)
                .selectPokemonMove(pokemonMoveForPokemonTable);
        PokemonSpecieTable table = new PokemonSpecieTable().selectId().selectName().selectPokemon(
                pokemonTable);
        if (mFilter != null) {
            mFilter.setFilter(pokemonTable, table, pokemonFormTable);
        }
        pokemonMoveForPokemonTable.addWhere(new WhereStatement(PokemonMoveForPokemonTable.COLUMN_MOVE_ID, mMoveId));
        List<PokemonSpecie> list = dataSource.query(table);
        return list;
    }

}
