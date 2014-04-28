
package be.florien.teambuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.MoveListForPokemonSpecieFragment;
import be.florien.teambuilder.model.Pokemon;

public class MoveListForPokemonSpecieActivity extends ActionBarActivity {

    private static final String ARG_INIT_POKEMON = "ARG_INIT_POKEMON";
    private static final String ARG_INIT_POKEMON_NAME = "ARG_INIT_POKEMON_NAME";

    public static Intent newIntent(Context context, Pokemon pokemon, String name) {
        Intent intent = new Intent(context, MoveListForPokemonSpecieActivity.class);
        intent.putExtra(ARG_INIT_POKEMON, pokemon);
        intent.putExtra(ARG_INIT_POKEMON_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_common);
        Pokemon pokemon = getIntent().getParcelableExtra(ARG_INIT_POKEMON);
        String name = getIntent().getStringExtra(ARG_INIT_POKEMON_NAME);
        MoveListForPokemonSpecieFragment fragment = (MoveListForPokemonSpecieFragment) getSupportFragmentManager().findFragmentByTag(MoveListForPokemonSpecieFragment.class.getName());
        if (fragment == null) {
            fragment = MoveListForPokemonSpecieFragment.newInstance(pokemon, name);
        }
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment, MoveListForPokemonSpecieFragment.class.getName()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
