
package be.florien.teambuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import be.florien.teambuilder.R;
import be.florien.teambuilder.activity.TopActivity.TopMenuItem;
import be.florien.teambuilder.fragment.PokemonSpecieDetailFragment;
import be.florien.teambuilder.model.PokemonSpecie;

public class PokemonSpecieDetailActivity extends AppCompatActivity {

    private static final String ARG_INIT_SPECIE = "ARG_INIT_SPECIE";

    public static Intent newIntent(Context context, PokemonSpecie specie) {
        Intent intent = new Intent(context, PokemonSpecieDetailActivity.class);
        intent.putExtra(ARG_INIT_SPECIE, specie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PokemonSpecie specie = getIntent().getParcelableExtra(ARG_INIT_SPECIE);
        PokemonSpecieDetailFragment fragment = (PokemonSpecieDetailFragment) getSupportFragmentManager().findFragmentByTag(
                PokemonSpecieDetailFragment.class.getName());
        if (fragment == null) {
            fragment = PokemonSpecieDetailFragment.newInstance(specie);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, PokemonSpecieDetailFragment.class.getName()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.putExtra(TopActivity.UP_MENUPOSITION, TopMenuItem.POKEDEX.ordinal());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
