
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.teambuilder.database.helper.DBUserQueryHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCatchedTable;
import be.florien.teambuilder.model.UserPokemonSpecieCatched;

import java.util.ArrayList;
import java.util.List;

public class DeletePokemonUserLoader extends AbstractAsyncTaskLoader<Void> {

    private List<Integer> mPokemonUser;

    public DeletePokemonUserLoader(Context context, int pkmn) {
        super(context);
        mPokemonUser = new ArrayList<Integer>();
        mPokemonUser.add(pkmn);
    }

    public DeletePokemonUserLoader(Context context, List<Integer> pkmn) {
        super(context);
        mPokemonUser = pkmn;
    }

    @Override
    public Void loadInBackground() {
        DBUserQueryHelper<UserPokemonSpecieCatched> dbHelper = new DBUserQueryHelper<UserPokemonSpecieCatched>(getContext());
        UserPokemonSpecieCatchedTable table = new UserPokemonSpecieCatchedTable();
        for (int pkmn : mPokemonUser) {
            table.delete(pkmn);
        }
        dbHelper.delete(table);
        return null;
    }
}
