
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
import be.florien.teambuilder.database.table.PokemonMoveDao;
import be.florien.teambuilder.database.table.MachineDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.MoveMetaDao;
import be.florien.teambuilder.database.table.MoveEffectDao;
import be.florien.teambuilder.database.table.MoveDamageClassDao;
import be.florien.teambuilder.database.table.TypeDao;
import be.florien.teambuilder.database.table.GenerationDao;
import be.florien.teambuilder.database.table.MoveDao;

@Entity(nameInDb = "moves")
public class Move implements Comparable<Move>, Parcelable {

    @Id
    public int id = -10;
    public String identifier;
    public int power;
    public int pp;
    public int accuracy;
    public int priority;
    public int effect_chance;
    //    public MoveTarget move_targets;
    @ToOne(joinProperty = "id")
    public Generation generations;
    @ToOne(joinProperty = "id")
    public Type types;
    @ToOne(joinProperty = "id")
    public MoveDamageClass move_damage_classes;
    @ToOne(joinProperty = "id")
    public MoveEffect move_effects;
    @Transient()
    public DualStringTranslation move_names;
    @ToOne(joinProperty = "id")
    public MoveMeta move_meta;
    @ToMany(referencedJoinProperty = "pokemon_id")
    public List<PokemonMove> pokemon_moves;
    @ToOne(joinProperty = "id")
    public Machine machines;

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
        pokemon_moves = new ArrayList<>();
        in.readTypedList(pokemon_moves, PokemonMove.CREATOR);
    }

    @Generated(hash = 2074755087)
    public Move(int id, String identifier, int power, int pp, int accuracy, int priority,
            int effect_chance) {
        this.id = id;
        this.identifier = identifier;
        this.power = power;
        this.pp = pp;
        this.accuracy = accuracy;
        this.priority = priority;
        this.effect_chance = effect_chance;
    }

    @Override
    public int compareTo(@NonNull Move another) {
        if (move_names != null && another.move_names != null) {
            return move_names.first.compareTo(another.move_names.first);
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

    public int getPower() {
        return this.power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPp() {
        return this.pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getEffect_chance() {
        return this.effect_chance;
    }

    public void setEffect_chance(int effect_chance) {
        this.effect_chance = effect_chance;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 308075139)
    public Generation getGenerations() {
        int __key = this.id;
        if (generations__resolvedKey == null || !generations__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GenerationDao targetDao = daoSession.getGenerationDao();
            Generation generationsNew = targetDao.load(__key);
            synchronized (this) {
                generations = generationsNew;
                generations__resolvedKey = __key;
            }
        }
        return generations;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 572717613)
    public void setGenerations(@NotNull Generation generations) {
        if (generations == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.generations = generations;
            id = generations.getId();
            generations__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 400112668)
    public Type getTypes() {
        int __key = this.id;
        if (types__resolvedKey == null || !types__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypeDao targetDao = daoSession.getTypeDao();
            Type typesNew = targetDao.load(__key);
            synchronized (this) {
                types = typesNew;
                types__resolvedKey = __key;
            }
        }
        return types;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 573353056)
    public void setTypes(@NotNull Type types) {
        if (types == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.types = types;
            id = types.getId();
            types__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 538401815)
    public MoveDamageClass getMove_damage_classes() {
        int __key = this.id;
        if (move_damage_classes__resolvedKey == null
                || !move_damage_classes__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoveDamageClassDao targetDao = daoSession.getMoveDamageClassDao();
            MoveDamageClass move_damage_classesNew = targetDao.load(__key);
            synchronized (this) {
                move_damage_classes = move_damage_classesNew;
                move_damage_classes__resolvedKey = __key;
            }
        }
        return move_damage_classes;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 675286365)
    public void setMove_damage_classes(@NotNull MoveDamageClass move_damage_classes) {
        if (move_damage_classes == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.move_damage_classes = move_damage_classes;
            id = move_damage_classes.getId();
            move_damage_classes__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 559778043)
    public MoveEffect getMove_effects() {
        int __key = this.id;
        if (move_effects__resolvedKey == null || !move_effects__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoveEffectDao targetDao = daoSession.getMoveEffectDao();
            MoveEffect move_effectsNew = targetDao.load(__key);
            synchronized (this) {
                move_effects = move_effectsNew;
                move_effects__resolvedKey = __key;
            }
        }
        return move_effects;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1903496178)
    public void setMove_effects(@NotNull MoveEffect move_effects) {
        if (move_effects == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.move_effects = move_effects;
            id = move_effects.getId();
            move_effects__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1999373044)
    public MoveMeta getMove_meta() {
        int __key = this.id;
        if (move_meta__resolvedKey == null || !move_meta__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoveMetaDao targetDao = daoSession.getMoveMetaDao();
            MoveMeta move_metaNew = targetDao.load(__key);
            synchronized (this) {
                move_meta = move_metaNew;
                move_meta__resolvedKey = __key;
            }
        }
        return move_meta;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2082052024)
    public void setMove_meta(@NotNull MoveMeta move_meta) {
        if (move_meta == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.move_meta = move_meta;
            id = move_meta.getMove_id();
            move_meta__resolvedKey = id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1302320618)
    public Machine getMachines() {
        int __key = this.id;
        if (machines__resolvedKey == null || !machines__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MachineDao targetDao = daoSession.getMachineDao();
            Machine machinesNew = targetDao.load(__key);
            synchronized (this) {
                machines = machinesNew;
                machines__resolvedKey = __key;
            }
        }
        return machines;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 719877874)
    public void setMachines(@NotNull Machine machines) {
        if (machines == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.machines = machines;
            id = machines.getMove_id();
            machines__resolvedKey = id;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1345774960)
    public List<PokemonMove> getPokemon_moves() {
        if (pokemon_moves == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PokemonMoveDao targetDao = daoSession.getPokemonMoveDao();
            List<PokemonMove> pokemon_movesNew = targetDao._queryMove_Pokemon_moves(id);
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
    @Generated(hash = 380504211)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMoveDao() : null;
    }

    @Transient
    public static final Creator<Move> CREATOR = new Creator<Move>() {
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        public Move[] newArray(int size) {
            return new Move[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 4265165)
    private transient MoveDao myDao;
    @Generated(hash = 1710462768)
    private transient Integer generations__resolvedKey;
    @Generated(hash = 1159351486)
    private transient Integer types__resolvedKey;
    @Generated(hash = 727531776)
    private transient Integer move_damage_classes__resolvedKey;
    @Generated(hash = 1950424407)
    private transient Integer move_effects__resolvedKey;
    @Generated(hash = 443048050)
    private transient Integer move_meta__resolvedKey;
    @Generated(hash = 501751673)
    private transient Integer machines__resolvedKey;
}
