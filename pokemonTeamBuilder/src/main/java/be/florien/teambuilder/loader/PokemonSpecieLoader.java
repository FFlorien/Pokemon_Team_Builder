
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.PokemonFormTable;
import be.florien.teambuilder.database.table.PokemonSpecieTable;
import be.florien.teambuilder.database.table.PokemonTable;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.PokemonSpecie;

public class PokemonSpecieLoader extends AbstractAsyncTaskLoader<PokemonSpecie> {

    // TODO: select more datas

    private int mId;

    public PokemonSpecieLoader(Context context, int id) {
        super(context);
        mId = id;

    }

    @Override
    public PokemonSpecie loadInBackground() {
        DBTableQueryHelper<PokemonSpecie> dataQueryHelper = new DBTableQueryHelper<PokemonSpecie>(getContext());
        PokemonSpecieTable table = new PokemonSpecieTable().selectId().selectName()
                .selectPokemon(new PokemonTable().selectId()
                        .selectType(new TypeTableTmpForPokemon().selectId().selectName())
                        .selectForm(new PokemonFormTable().selectId().selectName()));
        table.addWhere(new WhereStatement(PokemonSpecieTable.COLUMN_ID, mId));
        return dataQueryHelper.query(table).get(0);
    }
}
