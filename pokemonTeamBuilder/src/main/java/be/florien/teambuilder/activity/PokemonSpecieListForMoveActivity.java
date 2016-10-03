
package be.florien.teambuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.PokemonSpecieListForMoveFragment;
import be.florien.teambuilder.model.Move;

public class PokemonSpecieListForMoveActivity extends AppCompatActivity {

    private static final String ARG_INIT_MOVE = "ARG_INIT_MOVE";

    public static Intent newIntent(Context context, Move move) {
        Intent intent = new Intent(context, PokemonSpecieListForMoveActivity.class);
        intent.putExtra(ARG_INIT_MOVE, move);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Move move = getIntent().getParcelableExtra(ARG_INIT_MOVE);
        PokemonSpecieListForMoveFragment fragment = (PokemonSpecieListForMoveFragment) getSupportFragmentManager().findFragmentByTag(
                PokemonSpecieListForMoveFragment.class.getName());
        if (fragment == null) {
            fragment = PokemonSpecieListForMoveFragment.newInstance(move);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, PokemonSpecieListForMoveFragment.class.getName()).commit();
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
