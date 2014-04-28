
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.PokemonFormTable;
import be.florien.teambuilder.database.table.PokemonSpecieTable;
import be.florien.teambuilder.database.table.PokemonTable;
import be.florien.teambuilder.database.table.TypeTable;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.model.PokemonSpecie;

import java.util.List;

public class PokemonSpecieListLoader extends AbstractAsyncTaskLoader<List<PokemonSpecie>> {

    private SpecieFilter mFilter;

    public PokemonSpecieListLoader(Context context, SpecieFilter filter) {
        super(context);
        mFilter = filter;
        // mFilter.addWhere(new WhereStatement(new PokemonSpecieTable().getId(), 10, WhereCondition.LESS));
    }

    @Override
    public List<PokemonSpecie> loadInBackground() {
        DBTableQueryHelper<PokemonSpecie> dataQueryHelper = new DBTableQueryHelper<PokemonSpecie>(getContext());
        PokemonFormTable pokemonFormTable = new PokemonFormTable().selectId().selectName();
        PokemonTable pokemonTable = new PokemonTable().selectId()
                .selectType(new TypeTable().selectId())
                .selectForm(pokemonFormTable);
        PokemonSpecieTable specieTable = new PokemonSpecieTable().selectId().selectName()
                .selectPokemon(pokemonTable);
        if (mFilter != null) {
            mFilter.setFilter(pokemonTable, specieTable, pokemonFormTable);
        }
        return dataQueryHelper.query(specieTable);
    }
}
