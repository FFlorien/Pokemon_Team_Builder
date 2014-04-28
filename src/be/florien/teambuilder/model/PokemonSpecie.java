
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PokemonSpecie implements Parcelable {

    public int id;
    public List<Pokemon> pokemon;
    public String identifier;
    public Generation generation;
    public PokemonSpecie evolve_from_species_id;
    // public EvolutionChain evolutionChain;
    // public String color;
    // public String shape;
    // public Habitat habitat;
    public double gender_rate;
    public double capture_rate;
    public int base_hapiness;
    public int hatch_counter;
    // public GrowthRate growthRate;
    public int order;
    public DualStringTranslation pokemon_species_names;

    public PokemonSpecie() {
    }

    private PokemonSpecie(Parcel in) {
        pokemon = new ArrayList<Pokemon>();
        id = in.readInt();
        in.readTypedList(pokemon, Pokemon.CREATOR);
        identifier = in.readString();
        generation = in.readParcelable(Generation.class.getClassLoader());
        evolve_from_species_id = in.readParcelable(PokemonSpecie.class.getClassLoader());
        gender_rate = in.readDouble();
        capture_rate = in.readDouble();
        base_hapiness = in.readInt();
        hatch_counter = in.readInt();
        order = in.readInt();
        pokemon_species_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    public static Parcelable.Creator<PokemonSpecie> CREATOR = new Creator<PokemonSpecie>() {

        @Override
        public PokemonSpecie[] newArray(int size) {
            return new PokemonSpecie[size];
        }

        @Override
        public PokemonSpecie createFromParcel(Parcel source) {
            return new PokemonSpecie(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(pokemon);
        dest.writeString(identifier);
        dest.writeParcelable(generation, flags);
        dest.writeParcelable(evolve_from_species_id, flags);
        dest.writeDouble(gender_rate);
        dest.writeDouble(capture_rate);
        dest.writeInt(base_hapiness);
        dest.writeInt(hatch_counter);
        dest.writeInt(order);
        dest.writeParcelable(pokemon_species_names, flags);
    }

}
