
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.helper.DBUserHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCaughtTable;

public class DeletePokemonUserLoader extends AbstractAsyncTaskLoader<Void> {

    private List<Integer> mPokemonUser;

    public DeletePokemonUserLoader(Context context, int pkmn) {
        super(context);
        mPokemonUser = new ArrayList<>();
        mPokemonUser.add(pkmn);
    }

    public DeletePokemonUserLoader(Context context, List<Integer> pkmn) {
        super(context);
        mPokemonUser = pkmn;
    }

    @Override
    public Void loadInBackground() {
        JOQueryHelper dbHelper = new JOQueryHelper(new DBUserHelper(getContext()));
        UserPokemonSpecieCaughtTable table = new UserPokemonSpecieCaughtTable();
        for (int pkmn : mPokemonUser) {
            table.delete(pkmn);
        }
        dbHelper.delete(table);
        return null;
    }
}
