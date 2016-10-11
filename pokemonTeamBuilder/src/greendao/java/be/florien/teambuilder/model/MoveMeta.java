
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
import be.florien.teambuilder.database.table.MoveMetaAilmentDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.MoveMetaDao;

@Entity(nameInDb = "move_meta")
public class MoveMeta implements Parcelable {

    @Id
    public int move_id;
    public int meta_category_id;
    public int meta_ailment_id;
    public int min_hits;
    public int max_hits;
    public int min_turns;
    public int max_turns;
    public int recoil;
    public int healing;
    public int crit_rate;
    public int ailment_chance;
    public int flinch_chance;
    public int stat_chance;
    @ToOne(joinProperty = "meta_ailment_id")
    public MoveMetaAilment move_meta_ailments;

    @Transient
    public static Creator<MoveMeta> CREATOR = new Creator<MoveMeta>() {

        @Override
        public MoveMeta[] newArray(int size) {
            return new MoveMeta[size];
        }

        @Override
        public MoveMeta createFromParcel(Parcel source) {
            return new MoveMeta(source);
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1158991585)
    private transient MoveMetaDao myDao;
    @Generated(hash = 1363702752)
    private transient Integer move_meta_ailments__resolvedKey;

    public MoveMeta() {

    }

    private MoveMeta(Parcel in) {
        move_id = in.readInt();
        meta_category_id = in.readInt();
        meta_ailment_id = in.readInt();
        min_hits = in.readInt();
        max_hits = in.readInt();
        min_turns = in.readInt();
        max_turns = in.readInt();
        recoil = in.readInt();
        healing = in.readInt();
        crit_rate = in.readInt();
        ailment_chance = in.readInt();
        flinch_chance = in.readInt();
        stat_chance = in.readInt();
        move_meta_ailments = in.readParcelable(MoveMetaAilment.class.getClassLoader());
    }

    @Generated(hash = 1594199872)
    public MoveMeta(int move_id, int meta_category_id, int meta_ailment_id, int min_hits,
            int max_hits, int min_turns, int max_turns, int recoil, int healing,
            int crit_rate, int ailment_chance, int flinch_chance, int stat_chance) {
        this.move_id = move_id;
        this.meta_category_id = meta_category_id;
        this.meta_ailment_id = meta_ailment_id;
        this.min_hits = min_hits;
        this.max_hits = max_hits;
        this.min_turns = min_turns;
        this.max_turns = max_turns;
        this.recoil = recoil;
        this.healing = healing;
        this.crit_rate = crit_rate;
        this.ailment_chance = ailment_chance;
        this.flinch_chance = flinch_chance;
        this.stat_chance = stat_chance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(move_id);
        dest.writeInt(meta_category_id);
        dest.writeInt(meta_ailment_id);
        dest.writeInt(min_hits);
        dest.writeInt(max_hits);
        dest.writeInt(min_turns);
        dest.writeInt(max_turns);
        dest.writeInt(recoil);
        dest.writeInt(healing);
        dest.writeInt(crit_rate);
        dest.writeInt(ailment_chance);
        dest.writeInt(flinch_chance);
        dest.writeInt(stat_chance);
        dest.writeParcelable(move_meta_ailments, flags);
    }

    public int getMove_id() {
        return this.move_id;
    }

    public void setMove_id(int move_id) {
        this.move_id = move_id;
    }

    public int getMeta_category_id() {
        return this.meta_category_id;
    }

    public void setMeta_category_id(int meta_category_id) {
        this.meta_category_id = meta_category_id;
    }

    public int getMeta_ailment_id() {
        return this.meta_ailment_id;
    }

    public void setMeta_ailment_id(int meta_ailment_id) {
        this.meta_ailment_id = meta_ailment_id;
    }

    public int getMin_hits() {
        return this.min_hits;
    }

    public void setMin_hits(int min_hits) {
        this.min_hits = min_hits;
    }

    public int getMax_hits() {
        return this.max_hits;
    }

    public void setMax_hits(int max_hits) {
        this.max_hits = max_hits;
    }

    public int getMin_turns() {
        return this.min_turns;
    }

    public void setMin_turns(int min_turns) {
        this.min_turns = min_turns;
    }

    public int getMax_turns() {
        return this.max_turns;
    }

    public void setMax_turns(int max_turns) {
        this.max_turns = max_turns;
    }

    public int getRecoil() {
        return this.recoil;
    }

    public void setRecoil(int recoil) {
        this.recoil = recoil;
    }

    public int getHealing() {
        return this.healing;
    }

    public void setHealing(int healing) {
        this.healing = healing;
    }

    public int getCrit_rate() {
        return this.crit_rate;
    }

    public void setCrit_rate(int crit_rate) {
        this.crit_rate = crit_rate;
    }

    public int getAilment_chance() {
        return this.ailment_chance;
    }

    public void setAilment_chance(int ailment_chance) {
        this.ailment_chance = ailment_chance;
    }

    public int getFlinch_chance() {
        return this.flinch_chance;
    }

    public void setFlinch_chance(int flinch_chance) {
        this.flinch_chance = flinch_chance;
    }

    public int getStat_chance() {
        return this.stat_chance;
    }

    public void setStat_chance(int stat_chance) {
        this.stat_chance = stat_chance;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1980886388)
    public MoveMetaAilment getMove_meta_ailments() {
        int __key = this.meta_ailment_id;
        if (move_meta_ailments__resolvedKey == null
                || !move_meta_ailments__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoveMetaAilmentDao targetDao = daoSession.getMoveMetaAilmentDao();
            MoveMetaAilment move_meta_ailmentsNew = targetDao.load(__key);
            synchronized (this) {
                move_meta_ailments = move_meta_ailmentsNew;
                move_meta_ailments__resolvedKey = __key;
            }
        }
        return move_meta_ailments;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 251504047)
    public void setMove_meta_ailments(@NotNull MoveMetaAilment move_meta_ailments) {
        if (move_meta_ailments == null) {
            throw new DaoException(
                    "To-one property 'meta_ailment_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.move_meta_ailments = move_meta_ailments;
            meta_ailment_id = move_meta_ailments.getId();
            move_meta_ailments__resolvedKey = meta_ailment_id;
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
    @Generated(hash = 14157960)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMoveMetaDao() : null;
    }

}
