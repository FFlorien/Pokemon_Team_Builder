package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import be.florien.teambuilder.database.table.DaoSession;
import be.florien.teambuilder.database.table.TypeDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.TypeEfficacyDao;

@Entity(nameInDb = "type_efficacy")
public class TypeEfficacy implements Parcelable {

    @ToOne(joinProperty = "target_type_id")
    public Type typeTargeted;
    @ToOne(joinProperty = "damage_type_id")
    public Type typeAttacking;
    @Id
    public int damage_factor;
    @Id
    public int target_type_id;
    @Id
    public int damage_type_id;

    public TypeEfficacy() {
    }

    private TypeEfficacy(Parcel in) {
        typeTargeted = in.readParcelable(Type.class.getClassLoader());
        typeAttacking = in.readParcelable(Type.class.getClassLoader());
        damage_factor = in.readInt();
        target_type_id = in.readInt();
    }

    @Generated(hash = 1291932039)
    public TypeEfficacy(int damage_factor, int target_type_id, int damage_type_id) {
        this.damage_factor = damage_factor;
        this.target_type_id = target_type_id;
        this.damage_type_id = damage_type_id;
    }

    @Transient
    public static final Creator<TypeEfficacy> CREATOR = new Creator<TypeEfficacy>() {
        public TypeEfficacy createFromParcel(Parcel in) {
            return new TypeEfficacy(in);
        }

        public TypeEfficacy[] newArray(int size) {
            return new TypeEfficacy[size];
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1471919733)
    private transient TypeEfficacyDao myDao;
    @Generated(hash = 1752935784)
    private transient Integer typeTargeted__resolvedKey;
    @Generated(hash = 666903539)
    private transient Integer typeAttacking__resolvedKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(typeTargeted, flags);
        dest.writeParcelable(typeAttacking, flags);
        dest.writeInt(damage_factor);
        dest.writeInt(target_type_id);
    }

    public int getDamage_factor() {
        return this.damage_factor;
    }

    public void setDamage_factor(int damage_factor) {
        this.damage_factor = damage_factor;
    }

    public int getTarget_type_id() {
        return this.target_type_id;
    }

    public void setTarget_type_id(int target_type_id) {
        this.target_type_id = target_type_id;
    }

    public int getDamage_type_id() {
        return this.damage_type_id;
    }

    public void setDamage_type_id(int damage_type_id) {
        this.damage_type_id = damage_type_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1043555798)
    public Type getTypeTargeted() {
        int __key = this.target_type_id;
        if (typeTargeted__resolvedKey == null || !typeTargeted__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypeDao targetDao = daoSession.getTypeDao();
            Type typeTargetedNew = targetDao.load(__key);
            synchronized (this) {
                typeTargeted = typeTargetedNew;
                typeTargeted__resolvedKey = __key;
            }
        }
        return typeTargeted;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 461787584)
    public void setTypeTargeted(@NotNull Type typeTargeted) {
        if (typeTargeted == null) {
            throw new DaoException(
                    "To-one property 'target_type_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.typeTargeted = typeTargeted;
            target_type_id = typeTargeted.getId();
            typeTargeted__resolvedKey = target_type_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2037624029)
    public Type getTypeAttacking() {
        int __key = this.damage_type_id;
        if (typeAttacking__resolvedKey == null || !typeAttacking__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TypeDao targetDao = daoSession.getTypeDao();
            Type typeAttackingNew = targetDao.load(__key);
            synchronized (this) {
                typeAttacking = typeAttackingNew;
                typeAttacking__resolvedKey = __key;
            }
        }
        return typeAttacking;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1929764298)
    public void setTypeAttacking(@NotNull Type typeAttacking) {
        if (typeAttacking == null) {
            throw new DaoException(
                    "To-one property 'damage_type_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.typeAttacking = typeAttacking;
            damage_type_id = typeAttacking.getId();
            typeAttacking__resolvedKey = damage_type_id;
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
    @Generated(hash = 1929289037)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTypeEfficacyDao() : null;
    }

}
