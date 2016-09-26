
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.table.PokemonFormTable;
import be.florien.teambuilder.model.table.PokemonMoveForPokemonTable;
import be.florien.teambuilder.model.table.PokemonSpecieTable;
import be.florien.teambuilder.model.table.PokemonTable;

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

        DBTableQueryHelper<PokemonSpecie> dataSource = new DBTableQueryHelper<>(getContext());

        TypeTableTmpForPokemon typeTable = new TypeTableTmpForPokemon().selectId();
        PokemonFormTable pokemonFormTable = new PokemonFormTable().selectId().selectPokemonFormNames(TranslationTableField.forPokemonForm());
        PokemonMoveForPokemonTable pokemonMoveForPokemonTable = new PokemonMoveForPokemonTable().selectId().selectLevel().selectPokemonMoveMethodId();
        PokemonTable pokemonTable = new PokemonTable().selectId()
                .selectTypes(typeTable)
                .selectPokemonForms(pokemonFormTable)
                .selectPokemonMoves(pokemonMoveForPokemonTable);
        PokemonSpecieTable table = new PokemonSpecieTable().selectId().selectPokemonSpeciesNames(TranslationTableField.forPokemonSpecie())/*.select(
                pokemonTable)todo*/;
        if (mFilter != null) {
            mFilter.setFilter(pokemonTable, table, pokemonFormTable);
        }
        pokemonMoveForPokemonTable.addWhere(new WhereStatement(PokemonMoveForPokemonTable.COLUMN_MOVE_ID, mMoveId));
        return dataSource.query(table);
    }

}
