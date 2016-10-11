
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.fragment.PokemonSpecieListFilterDialogFragment.SpecieFilter;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.table.PokemonFormTable;
import be.florien.teambuilder.model.table.PokemonSpecieTable;
import be.florien.teambuilder.model.table.PokemonTable;

public class PokemonSpecieListLoader extends AbstractAsyncTaskLoader<List<PokemonSpecie>> {

    private SpecieFilter mFilter;

    public PokemonSpecieListLoader(Context context, SpecieFilter filter) {
        super(context);
        mFilter = filter;
        // mFilter.addWhere(new WhereStatement(new PokemonSpecieTable().getId(), 10, WhereCondition.LESS));
    }

    @Override
    public List<PokemonSpecie> loadInBackground() {
        DBTableQueryHelper<PokemonSpecie> dataQueryHelper = new DBTableQueryHelper<>(getContext());
        PokemonFormTable pokemonFormTable = new PokemonFormTable().selectId().selectPokemonFormNames(TranslationTableField.forPokemonForm());
        PokemonTable pokemonTable = new PokemonTable().selectId()
                .selectTypes(new TypeTableTmpForPokemon().selectId())
                .selectPokemonForms(pokemonFormTable);
        PokemonSpecieTable specieTable = new PokemonSpecieTable().selectId().selectPokemonSpeciesNames(TranslationTableField.forPokemonSpecie())
                .selectPokemon(pokemonTable);
        if (mFilter != null) {
            mFilter.setFilter(pokemonTable, specieTable, pokemonFormTable);
        }
        return dataQueryHelper.query(specieTable);
    }
}
