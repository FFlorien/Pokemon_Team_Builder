
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBUserQueryHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCaughtTable;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

import java.util.List;

public class UserPokemonSpecieCatchedListLoader extends AbstractAsyncTaskLoader<List<UserPokemonSpecieCaught>> {

    public UserPokemonSpecieCatchedListLoader(Context context) {
        super(context);
        // mWheres.addWhere(new WhereStatement(new PokemonSpecieTable().getId(), 10, WhereCondition.LESS));
    }

    @Override
    public List<UserPokemonSpecieCaught> loadInBackground() {
        DBUserQueryHelper<UserPokemonSpecieCaught> dataQueryHelper = new DBUserQueryHelper<UserPokemonSpecieCaught>(getContext());
        UserPokemonSpecieCaughtTable table = new UserPokemonSpecieCaughtTable().selectId();
        return dataQueryHelper.query(table);
    }
}
