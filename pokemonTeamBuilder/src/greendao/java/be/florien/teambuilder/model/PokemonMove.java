
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import be.florien.teambuilder.database.table.DaoSession;
import be.florien.teambuilder.database.table.PokemonDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.PokemonMoveDao;

@Entity(nameInDb = "pokemon_moves")
public class PokemonMove implements Parcelable {

    @Id
    public int pokemon_id;
    public int move_id;
    public int pokemon_move_method_id;
    public int level;
    public int order;
    @ToOne(joinProperty = "pokemon_id")
    public Pokemon pokemon;

    @Id
    public static Creator<PokemonMove> CREATOR = new Creator<PokemonMove>() {

        @Override
        public PokemonMove[] newArray(int size) {
            return new PokemonMove[size];
        }

        @Override
        public PokemonMove createFromParcel(Parcel source) {
            return new PokemonMove(source);
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 567366813)
    private transient PokemonMoveDao myDao;
    @Generated(hash = 1928681228)
    private transient Integer pokemon__resolvedKey;

    public PokemonMove() {
    }

    private PokemonMove(Parcel in) {
        pokemon_id = in.readInt();
        move_id = in.readInt();
        pokemon_move_method_id = in.readInt();
        level = in.readInt();
        order = in.readInt();
        pokemon = in.readParcelable(Pokemon.class.getClassLoader());
    }

    @Generated(hash = 799343974)
    public PokemonMove(int pokemon_id, int move_id, int pokemon_move_method_id,
            int level, int order) {
        this.pokemon_id = pokemon_id;
        this.move_id = move_id;
        this.pokemon_move_method_id = pokemon_move_method_id;
        this.level = level;
        this.order = order;
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

    public int getPokemon_id() {
        return this.pokemon_id;
    }

    public void setPokemon_id(int pokemon_id) {
        this.pokemon_id = pokemon_id;
    }

    public int getMove_id() {
        return this.move_id;
    }

    public void setMove_id(int move_id) {
        this.move_id = move_id;
    }

    public int getPokemon_move_method_id() {
        return this.pokemon_move_method_id;
    }

    public void setPokemon_move_method_id(int pokemon_move_method_id) {
        this.pokemon_move_method_id = pokemon_move_method_id;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1168986918)
    public Pokemon getPokemon() {
        int __key = this.pokemon_id;
        if (pokemon__resolvedKey == null || !pokemon__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonDao targetDao = daoSession.getPokemonDao();
            Pokemon pokemonNew = targetDao.load(__key);
            synchronized (this) {
                pokemon = pokemonNew;
                pokemon__resolvedKey = __key;
            }
        }
        return pokemon;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2012972248)
    public void setPokemon(@NotNull Pokemon pokemon) {
        if (pokemon == null) {
            throw new DaoException(
                    "To-one property 'pokemon_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.pokemon = pokemon;
            pokemon_id = pokemon.getId();
            pokemon__resolvedKey = pokemon_id;
        }
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
    @Generated(hash = 827167096)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPokemonMoveDao() : null;
    }

}
