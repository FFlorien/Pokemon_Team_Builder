
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonMoveForPokemon implements Parcelable {

    public int pokemon_id;
    public int move_id;
    public int pokemon_move_method_id;
    public int level;
    public int order;
    public Move moves;

    public static Parcelable.Creator<PokemonMoveForPokemon> CREATOR = new Creator<PokemonMoveForPokemon>() {

        @Override
        public PokemonMoveForPokemon[] newArray(int size) {
            return new PokemonMoveForPokemon[size];
        }

        @Override
        public PokemonMoveForPokemon createFromParcel(Parcel source) {
            return new PokemonMoveForPokemon(source);
        }
    };

    public PokemonMoveForPokemon() {
    }

    private PokemonMoveForPokemon(Parcel in) {
        pokemon_id = in.readInt();
        move_id = in.readInt();
        pokemon_move_method_id = in.readInt();
        level = in.readInt();
        order = in.readInt();
        moves = in.readParcelable(Move.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pokemon_id);
        dest.writeInt(move_id);
        dest.writeInt(pokemon_move_method_id);
        dest.writeInt(level);
        dest.writeInt(order);
        dest.writeParcelable(moves, flags);
    }

}
