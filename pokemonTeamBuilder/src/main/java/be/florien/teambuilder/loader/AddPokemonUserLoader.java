
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import be.florien.joinorm.queryhandling.JOQueryHelper;
import be.florien.teambuilder.database.helper.DBUserHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCaughtTable;

public class AddPokemonUserLoader extends AbstractAsyncTaskLoader<Void> {

    private List<Integer> mPokemonUser;

    public AddPokemonUserLoader(Context context, int pkmn) {
        super(context);
        mPokemonUser = new ArrayList<Integer>();
        mPokemonUser.add(pkmn);
    }

    public AddPokemonUserLoader(Context context, List<Integer> pkmn) {
        super(context);
        mPokemonUser = pkmn;
    }

    @Override
    public Void loadInBackground() {
        JOQueryHelper dbHelper = new JOQueryHelper(new DBUserHelper(getContext()));
        for (int pkmn : mPokemonUser) {
            dbHelper.insert(new UserPokemonSpecieCaughtTable().writeId(pkmn));
        }
        return null;
    }
}
