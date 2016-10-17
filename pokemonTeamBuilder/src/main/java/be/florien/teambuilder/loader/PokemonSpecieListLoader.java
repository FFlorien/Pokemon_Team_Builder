
package be.florien.teambuilder.loader;

import android.content.Context;
import android.util.Log;

import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.table.PokemonFormTable;
import be.florien.teambuilder.model.table.PokemonSpecieTable;
import be.florien.teambuilder.model.table.PokemonTable;

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
        JOQueryHelper dataQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        PokemonFormTable pokemonFormTable = new PokemonFormTable().selectId().selectPokemonFormNames(TranslationTableField.forPokemonForm());
        PokemonTable pokemonTable = new PokemonTable().selectId()
                .selectTypes(new TypeTableTmpForPokemon().selectId())
                .selectPokemonForms(pokemonFormTable);
        PokemonSpecieTable specieTable = new PokemonSpecieTable().selectId().selectPokemonSpeciesNames(TranslationTableField.forPokemonSpecie())
                .selectPokemon(pokemonTable);
        if (mFilter != null) {
            mFilter.setFilter(pokemonTable, specieTable, pokemonFormTable);
        }
        long start = System.nanoTime();
        List<PokemonSpecie> query = dataQueryHelper.queryList(specieTable);
        long stop = System.nanoTime();
        Log.d("PKMN", "Duration for loading of species with reflection == " + (stop - start));
        return query;
    }
}
