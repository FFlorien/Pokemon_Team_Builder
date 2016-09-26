
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
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
        DBTableQueryHelper<PokemonSpecie> dataQueryHelper = new DBTableQueryHelper<>(getContext());
        PokemonSpecieTable table = new PokemonSpecieTable().selectId().selectPokemonSpeciesNames(TranslationTableField.forGeneration()/*todo*/)
                /*.selectPokemon(new PokemonTable().selectId()
                        .selectTypes(new TypeTableTmpForPokemon().selectId().selectName())
                        .selectForm(new PokemonFormTable().selectId().selectName()))todo*/;
        table.addWhere(new WhereStatement(PokemonSpecieTable.COLUMN_ID, mId));
        return dataQueryHelper.query(table).get(0);
    }
}
