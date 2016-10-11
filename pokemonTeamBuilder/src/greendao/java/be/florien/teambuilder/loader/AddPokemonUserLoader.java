
package be.florien.teambuilder.loader;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import be.florien.teambuilder.database.helper.DBUserQueryHelper;
import be.florien.teambuilder.database.table.UserPokemonSpecieCaughtTable;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

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
        DBUserQueryHelper<UserPokemonSpecieCaught> dbHelper = new DBUserQueryHelper<UserPokemonSpecieCaught>(getContext());
        for (int pkmn : mPokemonUser) {
            dbHelper.insert(new UserPokemonSpecieCaughtTable().writeId(pkmn));
        }
        return null;
    }
}
