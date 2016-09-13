
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PokemonMoveForMove implements Parcelable {

    public int pokemon_id;
    public int move_id;
    public int pokemon_move_method_id;
    public int level;
    public int order;
    public Pokemon pokemon;

    public static Parcelable.Creator<PokemonMoveForMove> CREATOR = new Creator<PokemonMoveForMove>() {

        @Override
        public PokemonMoveForMove[] newArray(int size) {
            return new PokemonMoveForMove[size];
        }

        @Override
        public PokemonMoveForMove createFromParcel(Parcel source) {
            return new PokemonMoveForMove(source);
        }
    };

    public PokemonMoveForMove() {
    }

    private PokemonMoveForMove(Parcel in) {
        pokemon_id = in.readInt();
        move_id = in.readInt();
        pokemon_move_method_id = in.readInt();
        level = in.readInt();
        order = in.readInt();
        pokemon = in.readParcelable(Pokemon.class.getClassLoader());
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
        dest.writeParcelable(pokemon, flags);
    }

}
