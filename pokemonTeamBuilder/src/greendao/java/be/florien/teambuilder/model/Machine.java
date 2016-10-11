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
import be.florien.teambuilder.database.table.MoveDao;
import org.greenrobot.greendao.annotation.NotNull;
import be.florien.teambuilder.database.table.ItemDao;
import be.florien.teambuilder.database.table.MachineDao;

@Entity(nameInDb = "machines")
public class Machine implements Parcelable {

    public int machine_number;
    public int version_group_id;
    public int item_id;
    @Id
    public int move_id;
    @ToOne(joinProperty = "item_id")
    public Item items;
    @ToOne(joinProperty = "move_id")
    public Move moves;

    public Machine(){
    }
    
    private Machine(Parcel in){
        machine_number = in.readInt();
        version_group_id = in.readInt();
        item_id = in.readInt();
        move_id = in.readInt();
        items = in.readParcelable(Item.class.getClassLoader());
        moves = in.readParcelable(Move.class.getClassLoader());
    }

    @Generated(hash = 562392458)
    public Machine(int machine_number, int version_group_id, int item_id,
            int move_id) {
        this.machine_number = machine_number;
        this.version_group_id = version_group_id;
        this.item_id = item_id;
        this.move_id = move_id;
    }

    @Transient
    public static Creator<Machine> CREATOR = new Creator<Machine>() {
        
        @Override
        public Machine[] newArray(int size) {
            return new Machine[size];
        }
        
        @Override
        public Machine createFromParcel(Parcel source) {
            return new Machine(source);
        }
    };
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 544067251)
    private transient MachineDao myDao;
    @Generated(hash = 2070111423)
    private transient Integer items__resolvedKey;
    @Generated(hash = 1528852117)
    private transient Integer moves__resolvedKey;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(machine_number);
        dest.writeInt(version_group_id);
        dest.writeInt(item_id);
        dest.writeInt(move_id);
        dest.writeParcelable(items, flags);
        dest.writeParcelable(moves, flags);
    }

    public int getMachine_number() {
        return this.machine_number;
    }

    public void setMachine_number(int machine_number) {
        this.machine_number = machine_number;
    }

    public int getVersion_group_id() {
        return this.version_group_id;
    }

    public void setVersion_group_id(int version_group_id) {
        this.version_group_id = version_group_id;
    }

    public int getItem_id() {
        return this.item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getMove_id() {
        return this.move_id;
    }

    public void setMove_id(int move_id) {
        this.move_id = move_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2113098656)
    public Item getItems() {
        int __key = this.item_id;
        if (items__resolvedKey == null || !items__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ItemDao targetDao = daoSession.getItemDao();
            Item itemsNew = targetDao.load(__key);
            synchronized (this) {
                items = itemsNew;
                items__resolvedKey = __key;
            }
        }
        return items;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1763189114)
    public void setItems(@NotNull Item items) {
        if (items == null) {
            throw new DaoException(
                    "To-one property 'item_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.items = items;
            item_id = items.getId();
            items__resolvedKey = item_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 927581475)
    public Move getMoves() {
        int __key = this.move_id;
        if (moves__resolvedKey == null || !moves__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoveDao targetDao = daoSession.getMoveDao();
            Move movesNew = targetDao.load(__key);
            synchronized (this) {
                moves = movesNew;
                moves__resolvedKey = __key;
            }
        }
        return moves;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 671335215)
    public void setMoves(@NotNull Move moves) {
        if (moves == null) {
            throw new DaoException(
                    "To-one property 'move_id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.moves = moves;
            move_id = moves.getId();
            moves__resolvedKey = move_id;
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
    @Generated(hash = 756452084)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMachineDao() : null;
    }

}
