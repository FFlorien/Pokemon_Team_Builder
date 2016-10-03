
package be.florien.teambuilder.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.MoveListFragment;
import be.florien.teambuilder.fragment.PokemonSpecieListFragment;
import be.florien.teambuilder.fragment.TypeListFragment;

public class TopActivity extends AppCompatActivity {

    private static final String SAVED_MENUPOSITION = "SAVED_MENUPOSITION";
    public static final String UP_MENUPOSITION = "UP_MENUPOSITION";

    protected enum TopMenuItem {
        POKEDEX(R.string.pokemon_specie_list_title, PokemonSpecieListFragment.class),
        MOVE(R.string.move_list_title, MoveListFragment.class),
        TYPE(R.string.type_list_title, TypeListFragment.class);

        private int nameRes;
        private Class<? extends Fragment> fragmentClass;

        TopMenuItem(int nameRes, Class<? extends Fragment> fragmentClass) {
            this.nameRes = nameRes;
            this.fragmentClass = fragmentClass;
        }

        public int getNameRes() {
            return nameRes;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }
    }

    private class MenuAdapter extends RecyclerView.Adapter<MenuItemHolder> {

        @Override
        public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(MenuItemHolder holder, int position) {
            holder.setMenuItem(TopMenuItem.values()[position]);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return TopMenuItem.values().length;
        }

    }

    private class MenuItemHolder extends RecyclerView.ViewHolder {

        TextView textView;
        private TopMenuItem menuItem;

        MenuItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        void setMenuItem(TopMenuItem menu) {
            this.menuItem = menu;
            textView.setText(menu.getNameRes());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopMenuItem oldItem = menuSelected;
                    menuSelected = menuItem;
                    drawerLayout.closeDrawer(drawerNavigation);
                    setContentFragment(oldItem);
                }
            });
        }
    }

    private DrawerLayout drawerLayout;
    private RecyclerView drawerNavigation;
    private ActionBarDrawerToggle drawerToggle;

    private TopMenuItem menuSelected = TopMenuItem.values()[0];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerNavigation = (RecyclerView) findViewById(R.id.drawer_list);
        drawerNavigation.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        drawerNavigation.setAdapter(new MenuAdapter());
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.choose_a_move, R.string.choose_a_specie) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        drawerLayout.addDrawerListener(drawerToggle);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar == null) {
            throw new IllegalStateException("No actionBar defined !");
        }

        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);

        if (savedInstanceState != null) {

            menuSelected = TopMenuItem.values()[savedInstanceState.getInt(SAVED_MENUPOSITION)];
        } else {
            setContentFragment(null);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int menuPosition = intent.getIntExtra(UP_MENUPOSITION, -1);
        Log.d("POKEMON", "Value of menuPosition: " + menuPosition);
        if (menuPosition != -1) {
            TopMenuItem olditem = menuSelected;
            menuSelected = TopMenuItem.values()[menuPosition];
            setContentFragment(olditem);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_MENUPOSITION, menuSelected.ordinal());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        if(item.getItemId() == R.id.preference){
            startActivity(new Intent(this, PreferenceActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setContentFragment(TopMenuItem oldItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (oldItem != null) {
            transaction.detach(getSupportFragmentManager().findFragmentByTag(oldItem.getFragmentClass().getName()));
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(menuSelected.getFragmentClass().getName());
        if (fragment == null) {
            try {
                fragment = menuSelected.getFragmentClass().newInstance();
                transaction.add(R.id.drawer_content, fragment, menuSelected.getFragmentClass().getName());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            transaction.attach(fragment);
        }
        transaction.commit();
    }

}
