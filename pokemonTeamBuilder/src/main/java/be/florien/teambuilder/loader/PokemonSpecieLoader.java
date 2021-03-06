
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBPokedexHelper;
import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.table.PokemonFormTable;
import be.florien.teambuilder.model.table.PokemonSpecieTable;
import be.florien.teambuilder.model.table.PokemonTable;

public class PokemonSpecieLoader extends AbstractAsyncTaskLoader<PokemonSpecie> {

    // TODO: select more datas

    private int mId;

    public PokemonSpecieLoader(Context context, int id) {
        super(context);
        mId = id;

    }

    @Override
    public PokemonSpecie loadInBackground() {
        JOQueryHelper dataQueryHelper = new JOQueryHelper(new DBPokedexHelper(getContext()));
        PokemonSpecieTable table = new PokemonSpecieTable().selectId().selectPokemonSpeciesNames(TranslationTableField.forPokemonSpecie())
                .selectPokemon(new PokemonTable().selectId()
                        .selectTypes(new TypeTableTmpForPokemon().selectId().selectName())
                        .selectPokemonForms(new PokemonFormTable().selectId().selectPokemonFormNames(TranslationTableField.forPokemonForm())));
        table.addWhere(new WhereStatement(PokemonSpecieTable.COLUMN_ID, mId));
        return dataQueryHelper.queryList(table).get(0);
    }
}
