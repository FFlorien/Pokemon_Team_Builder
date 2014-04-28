
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Parcelable {

    public int id;
    public String identifier;
    public int height;
    public int weight;
    public int order;
    public int base_experience;
    public List<PokemonForm> pokemon_forms;
    public List<String> abilities;
    public List<Type> types;
    public List<PokemonMoveForPokemon> pokemon_moves;

    public Pokemon() {
    }

    private Pokemon(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        height = in.readInt();
        weight = in.readInt();
        order = in.readInt();
        base_experience = in.readInt();
        abilities = new ArrayList<String>();
        types = new ArrayList<Type>();
        pokemon_moves = new ArrayList<PokemonMoveForPokemon>();
        in.readStringList(abilities);
        in.readTypedList(types, Type.CREATOR);
        in.readTypedList(pokemon_moves, PokemonMoveForPokemon.CREATOR);
    }

    public static Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>() {

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }

        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
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
        dest.writeInt(height);
        dest.writeInt(weight);
        dest.writeInt(order);
        dest.writeInt(base_experience);
        dest.writeStringList(abilities);
        dest.writeTypedList(types);
        dest.writeTypedList(pokemon_moves);
    }

}
