
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Move implements Comparable<Move>, Parcelable {

    public int id = -10;
    public String identifier;
    public Generation generations;
    public Type types;
    public int power;
    public int pp;
    public int accuracy;
    public int priority;
    // public MoveTarget move_targets;
    public MoveDamageClass move_damage_classes;
    public MoveEffect move_effects;
    public int effect_chance;
    public DualStringTranslation move_names;
    public MoveMeta move_meta;
    public Machine machines;
    public List<PokemonMoveForMove> pokemon_moves;

    public Move() {
    }

    private Move(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        generations = in.readParcelable(Generation.class.getClassLoader());
        types = in.readParcelable(Type.class.getClassLoader());
        power = in.readInt();
        pp = in.readInt();
        accuracy = in.readInt();
        priority = in.readInt();
        move_damage_classes = in.readParcelable(MoveDamageClass.class.getClassLoader());
        move_effects = in.readParcelable(MoveEffect.class.getClassLoader());
        effect_chance = in.readInt();
        move_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
        move_meta = in.readParcelable(MoveMeta.class.getClassLoader());
        machines = in.readParcelable(Machine.class.getClassLoader());
        pokemon_moves = new ArrayList<PokemonMoveForMove>();
        in.readTypedList(pokemon_moves, PokemonMoveForMove.CREATOR);
    }

    @Override
    public int compareTo(Move another) {
        if (move_names != null && move_names != null) {
            return move_names.first.compareTo(another.move_names.first);
        } else if (another.move_names != null && another.move_names != null) {
            return another.move_names.first.compareTo(null);
        } else {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeParcelable(generations, flags);
        dest.writeParcelable(types, flags);
        dest.writeInt(power);
        dest.writeInt(pp);
        dest.writeInt(accuracy);
        dest.writeInt(priority);
        // dest.writeMoveTarget move_targets);
        dest.writeParcelable(move_damage_classes, flags);
        dest.writeParcelable(move_effects, flags);
        dest.writeInt(effect_chance);
        dest.writeParcelable(move_names, flags);
        dest.writeParcelable(move_meta, flags);
        dest.writeParcelable(machines, flags);
        dest.writeTypedList(pokemon_moves);
    }

    public static final Parcelable.Creator<Move> CREATOR = new Parcelable.Creator<Move>() {
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        public Move[] newArray(int size) {
            return new Move[size];
        }
    };
}
