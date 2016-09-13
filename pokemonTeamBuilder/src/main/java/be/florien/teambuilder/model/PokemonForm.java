
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonForm implements Parcelable {

    public int id;
    public String identifier;
    public String form_identifier;
    // public Version version;
    public int order;
    public int form_order;
    public boolean is_mega;
    public DualStringTranslation pokemon_form_names;

    public PokemonForm() {
    }

    private PokemonForm(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        form_identifier = in.readString();
        order = in.readInt();
        form_order = in.readInt();
        is_mega = in.readInt() == 1;
        pokemon_form_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    public static Parcelable.Creator<PokemonForm> CREATOR = new Creator<PokemonForm>() {

        @Override
        public PokemonForm[] newArray(int size) {
            return new PokemonForm[size];
        }

        @Override
        public PokemonForm createFromParcel(Parcel source) {
            return new PokemonForm(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeString(form_identifier);
        dest.writeInt(order);
        dest.writeInt(form_order);
        dest.writeInt(is_mega ? 1 : 0);
        dest.writeParcelable(pokemon_form_names, flags);
    }

}