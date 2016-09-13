
package be.florien.teambuilder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.Type;
import be.florien.teambuilder.model.TypeEnum;

public class TypeAdapter extends AbstractBaseAdapter<Type> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        }
        Type type = getItem(position);
        convertView.getBackground().setColorFilter(TypeEnum.getColorFilter(type.id, parent.getContext()));
        ((TextView) convertView).setText(type.type_names.first);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        }
        Type type = getItem(position);
        convertView.getBackground().setColorFilter(TypeEnum.getColorFilter(type.id, parent.getContext()));
        ((TextView) convertView).setText(type.type_names.first);
        return convertView;
    }

}
