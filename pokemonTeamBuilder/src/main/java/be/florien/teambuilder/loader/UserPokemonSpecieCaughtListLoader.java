
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.List;

import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.helper.DBUserHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCaughtTable;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

public class UserPokemonSpecieCaughtListLoader extends AbstractAsyncTaskLoader<List<UserPokemonSpecieCaught>> {

    public UserPokemonSpecieCaughtListLoader(Context context) {
        super(context);
    }

    @Override
    public List<UserPokemonSpecieCaught> loadInBackground() {
        JOQueryHelper dbHelper = new JOQueryHelper(new DBUserHelper(getContext()));
        UserPokemonSpecieCaughtTable table = new UserPokemonSpecieCaughtTable().selectId();
        return dbHelper.queryList(table);
    }
}
