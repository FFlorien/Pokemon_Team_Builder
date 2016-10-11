
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
import be.florien.teambuilder.database.table.TypeEfficacyDao;
import be.florien.teambuilder.database.table.GenerationDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.TypeDao;

@Entity(nameInDb = "types")
public class Type implements Parcelable {

    @Id
    public int id;
    public String identifier;
    @Transient()
    public DualStringTranslation type_names;
    @ToOne(joinProperty = "id")
    public Generation generations;
    @ToMany(referencedJoinProperty = "damage_type_id")
    public List<TypeEfficacy> attack;
    @ToMany(referencedJoinProperty = "target_type_id")
    public List<TypeEfficacy> defense;

    public Type() {
    }

    private Type(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        type_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
        generations = in.readParcelable(Generation.class.getClassLoader());
        attack = new ArrayList<>();
        defense = new ArrayList<>();
        in.readTypedList(attack, TypeEfficacy.CREATOR);
        in.readTypedList(defense, TypeEfficacy.CREATOR);
    }

    @Generated(hash = 213528892)
    public Type(int id, String identifier) {
        this.id = id;
        this.identifier = identifier;
    }

    @Transient
    public static final Creator<Type> CREATOR = new Creator<Type>() {
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1125686680)
    private transient TypeDao myDao;
    @Generated(hash = 1710462768)
    private transient Integer generations__resolvedKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeParcelable(type_names, flags);
        dest.writeParcelable(generations, flags);
        dest.writeTypedList(attack);
        dest.writeTypedList(defense);
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 27045233)
    public List<TypeEfficacy> getAttack() {
        if (attack == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypeEfficacyDao targetDao = daoSession.getTypeEfficacyDao();
            List<TypeEfficacy> attackNew = targetDao._queryType_Attack(id);
            synchronized (this) {
                if (attack == null) {
                    attack = attackNew;
                }
            }
        }
        return attack;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1699827280)
    public synchronized void resetAttack() {
        attack = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 8070347)
    public List<TypeEfficacy> getDefense() {
        if (defense == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypeEfficacyDao targetDao = daoSession.getTypeEfficacyDao();
            List<TypeEfficacy> defenseNew = targetDao._queryType_Defense(id);
            synchronized (this) {
                if (defense == null) {
                    defense = defenseNew;
                }
            }
        }
        return defense;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1715675097)
    public synchronized void resetDefense() {
        defense = null;
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
    @Generated(hash = 627329482)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTypeDao() : null;
    }
}
