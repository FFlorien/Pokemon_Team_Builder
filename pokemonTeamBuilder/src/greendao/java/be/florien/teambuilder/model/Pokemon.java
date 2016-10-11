
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import be.florien.teambuilder.database.table.DaoSession;
import be.florien.teambuilder.database.table.PokemonMoveDao;
import be.florien.teambuilder.database.table.PokemonFormDao;
import be.florien.teambuilder.database.table.PokemonDao;

@Entity(nameInDb = "pokemon")
public class Pokemon implements Parcelable {

    @Id
    public int id;
    public String identifier;
    public int height;
    public int weight;
    public int order;
    public int base_experience;
    @Transient
    public List<String> abilities;
    @ToMany(referencedJoinProperty = "id")
    public List<PokemonForm> pokemon_forms;
//    @Transient()
//    public List<Type> types;
    @ToMany(referencedJoinProperty = "pokemon_id")
    public List<PokemonMove> pokemon_moves;

    public Pokemon() {
    }

    private Pokemon(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        height = in.readInt();
        weight = in.readInt();
        order = in.readInt();
        base_experience = in.readInt();
        abilities = new ArrayList<>();
//        types = new ArrayList<>();
        pokemon_moves = new ArrayList<>();
        in.readStringList(abilities);
//        in.readTypedList(types, Type.CREATOR);
        in.readTypedList(pokemon_moves, PokemonMove.CREATOR);
    }

    @Generated(hash = 1586884021)
    public Pokemon(int id, String identifier, int height, int weight, int order,
            int base_experience) {
        this.id = id;
        this.identifier = identifier;
        this.height = height;
        this.weight = weight;
        this.order = order;
        this.base_experience = base_experience;
    }

    @Transient
    public static Creator<Pokemon> CREATOR = new Creator<Pokemon>() {

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }

        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1047668923)
    private transient PokemonDao myDao;

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
//        dest.writeTypedList(types);
        dest.writeTypedList(pokemon_moves);
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

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getBase_experience() {
        return this.base_experience;
    }

    public void setBase_experience(int base_experience) {
        this.base_experience = base_experience;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 378878672)
    public List<PokemonForm> getPokemon_forms() {
        if (pokemon_forms == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonFormDao targetDao = daoSession.getPokemonFormDao();
            List<PokemonForm> pokemon_formsNew = targetDao
                    ._queryPokemon_Pokemon_forms(id);
            synchronized (this) {
                if (pokemon_forms == null) {
                    pokemon_forms = pokemon_formsNew;
                }
            }
        }
        return pokemon_forms;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1337242409)
    public synchronized void resetPokemon_forms() {
        pokemon_forms = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 995522612)
    public List<PokemonMove> getPokemon_moves() {
        if (pokemon_moves == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonMoveDao targetDao = daoSession.getPokemonMoveDao();
            List<PokemonMove> pokemon_movesNew = targetDao
                    ._queryPokemon_Pokemon_moves(id);
            synchronized (this) {
                if (pokemon_moves == null) {
                    pokemon_moves = pokemon_movesNew;
                }
            }
        }
        return pokemon_moves;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 557808030)
    public synchronized void resetPokemon_moves() {
        pokemon_moves = null;
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
    @Generated(hash = 783325239)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPokemonDao() : null;
    }

}
