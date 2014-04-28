
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBUserQueryHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCatchedTable;
import be.florien.teambuilder.model.UserPokemonSpecieCatched;

import java.util.List;

public class UserPokemonSpecieCatchedListLoader extends AbstractAsyncTaskLoader<List<UserPokemonSpecieCatched>> {

    public UserPokemonSpecieCatchedListLoader(Context context) {
        super(context);
        // mWheres.addWhere(new WhereStatement(new PokemonSpecieTable().getId(), 10, WhereCondition.LESS));
    }

    @Override
    public List<UserPokemonSpecieCatched> loadInBackground() {
        DBUserQueryHelper<UserPokemonSpecieCatched> dataQueryHelper = new DBUserQueryHelper<UserPokemonSpecieCatched>(getContext());
        UserPokemonSpecieCatchedTable table = new UserPokemonSpecieCatchedTable().selectId();
        return dataQueryHelper.query(table);
    }
}
