package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "items")
public class Item implements Parcelable {

    @Id
    public int id;
    public String identifier;
    public int category_id;
    public int cost;
    public int fling_power;
    public int fling_effect_id;

    @Transient
    public DualStringTranslation item_names;
    
    public Item(){
    }
    
    private Item(Parcel in){
        id = in.readInt();
        identifier = in.readString();
        category_id = in.readInt();
        cost = in.readInt();
        fling_power = in.readInt();
        fling_effect_id = in.readInt();
        item_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Generated(hash = 1935206644)
    public Item(int id, String identifier, int category_id, int cost, int fling_power,
            int fling_effect_id) {
        this.id = id;
        this.identifier = identifier;
        this.category_id = category_id;
        this.cost = cost;
        this.fling_power = fling_power;
        this.fling_effect_id = fling_effect_id;
    }

    @Transient
    public static Parcelable.Creator<Item> CREATOR = new Creator<Item>() {
        
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
        
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeInt(category_id);
        dest.writeInt(cost);
        dest.writeInt(fling_power);
        dest.writeInt(fling_effect_id);
        dest.writeParcelable(item_names, flags);
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

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getFling_power() {
        return this.fling_power;
    }

    public void setFling_power(int fling_power) {
        this.fling_power = fling_power;
    }

    public int getFling_effect_id() {
        return this.fling_effect_id;
    }

    public void setFling_effect_id(int fling_effect_id) {
        this.fling_effect_id = fling_effect_id;
    }

}
