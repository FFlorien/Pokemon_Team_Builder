
package be.florien.teambuilder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Generation;

public class GenerationAdapter extends AbstractBaseAdapter<Generation> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        }
        Generation generation = getItem(position);
        ((TextView) convertView).setText(generation.generation_names.first);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        }
        Generation generation = getItem(position);
        ((TextView) convertView).setText(generation.generation_names.first);
        return convertView;
    }

}
