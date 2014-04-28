
package be.florien.teambuilder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import be.florien.teambuilder.R;
import be.florien.teambuilder.model.MoveDamageClass;
import be.florien.teambuilder.model.MoveDamageClassEnum;

public class MoveDamageClassAdapter extends AbstractBaseAdapter<MoveDamageClass> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_text, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.text);
        ImageView image = ViewHolder.get(convertView, R.id.image);
        MoveDamageClass damageClass = getItem(position);
        text.setText(damageClass.move_damage_class_prose.first);
        image.setImageResource(MoveDamageClassEnum.getDrawableRes(damageClass.id));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_text, parent, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.text);
        ImageView image = ViewHolder.get(convertView, R.id.image);
        MoveDamageClass damageClass = getItem(position);
        text.setText(damageClass.move_damage_class_prose.first);
        image.setImageResource(MoveDamageClassEnum.getDrawableRes(damageClass.id));
        return convertView;
    }

}
