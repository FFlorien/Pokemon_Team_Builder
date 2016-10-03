
package be.florien.teambuilder.adapter;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Pokemon;
import be.florien.teambuilder.model.PokemonForm;
import be.florien.teambuilder.model.PokemonSpecie;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEnum;

abstract class AbsPokemonSpecieAdapter extends AbstractBaseAdapter<PokemonSpecie> implements SectionIndexer {
    private HashMap<String, Integer> indexer;
    private String[] sections;
    SparseArray<Type> types;

    public void setTypes(List<Type> types) {
        this.types = new SparseArray<>();
        for (Type type : types) {
            this.types.put(type.id, type);
        }
        notifyDataSetChanged();
    }

    public void setItems(List<PokemonSpecie> items) {
        if (items == null) {
            super.setItems(null);
            return;
        }
        indexer = new HashMap<>(); // stores the positions for the start of each letter

        int size = items.size();
        for (int i = size - 1; i >= 0; i--) {
            PokemonSpecie pokemonSpecie = items.get(i);
            int j = pokemonSpecie.id / 10;
            String element = String.format(Locale.US, "%02d0", j);
            // We store the first letter of the word, and its index.
            indexer.put(element, i);
        }
        Set<String> keys = indexer.keySet(); // set of letters

        Iterator<String> keyIt = keys.iterator();
        ArrayList<String> keyList = new ArrayList<>();

        while (keyIt.hasNext()) {
            String key = keyIt.next();
            keyList.add(key);
        }
        Collections.sort(keyList);// sort the keylist
        sections = new String[keyList.size()]; // simple conversion to array
        keyList.toArray(sections);
        super.setItems(items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            return getSingleRowView(position, convertView, parent);
        } else {
            return getMultipleRowsView(position, convertView, parent);
        }
    }

    private View getSingleRowView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_specie, parent, false);
        }

        PokemonSpecie specie = getItem(position);
        TextView number = ViewHolder.get(convertView, R.id.number);

        number.setText(String.format("%03d", specie.id));

        Pokemon pokemon = specie.pokemon.get(0);
        PokemonForm pokemonForm = pokemon.pokemon_forms.get(0);

        String name = (TextUtils.isEmpty(pokemonForm.pokemon_form_names.second) || specie.pokemon.size() <= 1
                ? specie.pokemon_species_names.first
                : pokemonForm.pokemon_form_names.second);
        setPokemonForm(convertView, parent, pokemon, name);

        setSpecialContent(convertView, specie);

        return convertView;
    }

    protected abstract void setSpecialContent(View convertView, PokemonSpecie specie);

    private View getMultipleRowsView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_specie, parent, false);
        }
        LinearLayout workingview = ViewHolder.get(convertView, R.id.forms);

        PokemonSpecie specie = getItem(position);
        int speciePokemonCount = specie.pokemon.size();

        int childCount = workingview.getChildCount();
        if (childCount > speciePokemonCount) {
            for (int i = childCount; i > speciePokemonCount; i--) {
                workingview.getChildAt(i - 1).setVisibility(View.GONE);
            }
        } else if (childCount < speciePokemonCount) {
            for (int i = childCount; i < speciePokemonCount; i++) {
                View child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_specie_row, workingview, false);
                workingview.addView(child);
            }
        }

        int childIndex = 0;
        for (Pokemon pokemon : specie.pokemon) {
            View childAt = workingview.getChildAt(childIndex);
            String name = (TextUtils.isEmpty(pokemon.pokemon_forms.get(0).pokemon_form_names.second) ? specie.pokemon_species_names.first
                    : pokemon.pokemon_forms.get(0).pokemon_form_names.second);
            setPokemonForm(childAt, parent, pokemon, name);
            childAt.setVisibility(View.VISIBLE);
            childIndex++;
        }
        TextView number = ViewHolder.get(convertView, R.id.number);
        setSpecialContent(convertView, specie);

        number.setText(String.format("%03d", specie.id));

        return convertView;
    }

    private void setPokemonForm(View convertView, ViewGroup parent, Pokemon pokemon, String pokemonName) {
        TextView typeSticker1 = ViewHolder.get(convertView, R.id.type_sticker1);
        TextView typeSticker2 = ViewHolder.get(convertView, R.id.type_sticker2);
        TextView name = ViewHolder.get(convertView, R.id.name);

        typeSticker1.getBackground().setColorFilter(TypeEnum.getColorFilter(pokemon.types.get(0).id, parent.getContext()));
        typeSticker1.setText(types.get(pokemon.types.get(0).id).type_names.first);
        if (pokemon.types.size() > 1) {
            typeSticker2.setVisibility(View.VISIBLE);
            typeSticker2.getBackground().setColorFilter(TypeEnum.getColorFilter(pokemon.types.get(1).id, parent.getContext()));
            typeSticker2.setText(types.get(pokemon.types.get(1).id).type_names.first);
        } else {
            typeSticker2.setVisibility(View.GONE);
        }
        name.setText(pokemonName);
        setSpecialContentForForm(convertView, parent, pokemon);
    }

    protected abstract void setSpecialContentForForm(View convertView, ViewGroup parent, Pokemon pokemon);

    @Override
    public int getPositionForSection(int sectionIndex) {
        String letter = sections[sectionIndex];
        return indexer.get(letter);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        return sections; // to string will be called to display the letter
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        PokemonSpecie specie = getItem(position);
        return (specie.pokemon.size() > 1 ? 1 : 0);
    }
}
