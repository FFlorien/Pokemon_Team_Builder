
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import be.florien.teambuilder.database.table.DaoSession;
import be.florien.teambuilder.database.table.PokemonDao;
import be.florien.teambuilder.database.table.PokemonSpecieDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.GenerationDao;

@Entity(nameInDb = "pokemon_species")
public class PokemonSpecie implements Parcelable {

    @Id
    public int id;
    public String identifier;
    // public EvolutionChain evolutionChain;
    // public String color;
    // public String shape;
    // public Habitat habitat;
    public double gender_rate;
    public double capture_rate;
    public int base_hapiness;
    // public GrowthRate growthRate;
    public int order;
    public int hatch_counter;
    @ToMany(referencedJoinProperty = "id")
    public List<Pokemon> pokemon;
    @ToOne(joinProperty = "id")
    public Generation generation;
    @ToOne(joinProperty = "id")
    public PokemonSpecie evolve_from_species_id;
    @Transient()
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

    @Generated(hash = 1932974929)
    public PokemonSpecie(int id, String identifier, double gender_rate, double capture_rate,
            int base_hapiness, int order, int hatch_counter) {
        this.id = id;
        this.identifier = identifier;
        this.gender_rate = gender_rate;
        this.capture_rate = capture_rate;
        this.base_hapiness = base_hapiness;
        this.order = order;
        this.hatch_counter = hatch_counter;
    }

    @Transient
    public static Creator<PokemonSpecie> CREATOR = new Creator<PokemonSpecie>() {

        @Override
        public PokemonSpecie[] newArray(int size) {
            return new PokemonSpecie[size];
        }

        @Override
        public PokemonSpecie createFromParcel(Parcel source) {
            return new PokemonSpecie(source);
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1100517115)
    private transient PokemonSpecieDao myDao;
    @Generated(hash = 1915039860)
    private transient Integer generation__resolvedKey;
    @Generated(hash = 1115175529)
    private transient Integer evolve_from_species_id__resolvedKey;

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getGender_rate() {
        return this.gender_rate;
    }

    public void setGender_rate(double gender_rate) {
        this.gender_rate = gender_rate;
    }

    public double getCapture_rate() {
        return this.capture_rate;
    }

    public void setCapture_rate(double capture_rate) {
        this.capture_rate = capture_rate;
    }

    public int getBase_hapiness() {
        return this.base_hapiness;
    }

    public void setBase_hapiness(int base_hapiness) {
        this.base_hapiness = base_hapiness;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getHatch_counter() {
        return this.hatch_counter;
    }

    public void setHatch_counter(int hatch_counter) {
        this.hatch_counter = hatch_counter;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1100475657)
    public Generation getGeneration() {
        int __key = this.id;
        if (generation__resolvedKey == null || !generation__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GenerationDao targetDao = daoSession.getGenerationDao();
            Generation generationNew = targetDao.load(__key);
            synchronized (this) {
                generation = generationNew;
                generation__resolvedKey = __key;
            }
        }
        return generation;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1615914916)
    public void setGeneration(@NotNull Generation generation) {
        if (generation == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.generation = generation;
            id = generation.getId();
            generation__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 292867904)
    public PokemonSpecie getEvolve_from_species_id() {
        int __key = this.id;
        if (evolve_from_species_id__resolvedKey == null
                || !evolve_from_species_id__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonSpecieDao targetDao = daoSession.getPokemonSpecieDao();
            PokemonSpecie evolve_from_species_idNew = targetDao.load(__key);
            synchronized (this) {
                evolve_from_species_id = evolve_from_species_idNew;
                evolve_from_species_id__resolvedKey = __key;
            }
        }
        return evolve_from_species_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 568606164)
    public void setEvolve_from_species_id(@NotNull PokemonSpecie evolve_from_species_id) {
        if (evolve_from_species_id == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.evolve_from_species_id = evolve_from_species_id;
            id = evolve_from_species_id.getId();
            evolve_from_species_id__resolvedKey = id;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1218998639)
    public List<Pokemon> getPokemon() {
        if (pokemon == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonDao targetDao = daoSession.getPokemonDao();
            List<Pokemon> pokemonNew = targetDao._queryPokemonSpecie_Pokemon(id);
            synchronized (this) {
                if (pokemon == null) {
                    pokemon = pokemonNew;
                }
            }
        }
        return pokemon;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1973112100)
    public synchronized void resetPokemon() {
        pokemon = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1798307619)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPokemonSpecieDao() : null;
    }

}
