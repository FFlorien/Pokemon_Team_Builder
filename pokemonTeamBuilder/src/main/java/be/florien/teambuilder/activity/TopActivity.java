
package be.florien.teambuilder.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.fragment.MoveListFragment;
import be.florien.teambuilder.fragment.PokemonSpecieListFragment;
import be.florien.teambuilder.fragment.TypeListFragment;

public class TopActivity extends ActionBarActivity {

    private static final String SAVED_MENUPOSITION = "SAVED_MENUPOSITION";
    public static final String UP_MENUPOSITION = "UP_MENUPOSITION";

    protected enum TopMenuItem {
        POKEDEX(R.string.pokemon_specie_list_title, PokemonSpecieListFragment.class),
        MOVE(R.string.move_list_title, MoveListFragment.class),
        TYPE(R.string.type_list_title, TypeListFragment.class);

        private int mNameRes;
        private Class<? extends Fragment> mFragmentClass;

        private TopMenuItem(int nameRes, Class<? extends Fragment> fragmentClass) {
            mNameRes = nameRes;
            mFragmentClass = fragmentClass;
        }

        public int getNameRes() {
            return mNameRes;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return mFragmentClass;
        }
    }

    private class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return TopMenuItem.values().length;
        }

        @Override
        public Object getItem(int position) {
            return TopMenuItem.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            }
            TopMenuItem item = (TopMenuItem) getItem(position);
            ((TextView) convertView).setText(item.getNameRes());
            return convertView;
        }

    }

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private TopMenuItem mMenuSelected = TopMenuItem.values()[0];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerList.setAdapter(new MenuAdapter());
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopMenuItem oldItem = mMenuSelected;
                mMenuSelected = (TopMenuItem) mDrawerList.getAdapter().getItem(position);
                mDrawerLayout.closeDrawer(mDrawerList);
                setContentFragment(oldItem);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.choose_a_move, R.string.choose_a_specie) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {

            mMenuSelected = TopMenuItem.values()[savedInstanceState.getInt(SAVED_MENUPOSITION)];
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
            TopMenuItem olditem = mMenuSelected;
            mMenuSelected = TopMenuItem.values()[menuPosition];
            setContentFragment(olditem);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_MENUPOSITION, mMenuSelected.ordinal());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
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
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(mMenuSelected.getFragmentClass().getName());
        if (fragment == null) {
            try {
                fragment = mMenuSelected.getFragmentClass().newInstance();
                transaction.add(R.id.drawer_content, fragment, mMenuSelected.getFragmentClass().getName());
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
