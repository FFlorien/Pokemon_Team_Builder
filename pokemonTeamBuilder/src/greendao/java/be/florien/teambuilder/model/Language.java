package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "languages")
public class Language implements Parcelable {

    @Id
    public int id;
    public String iso639;
    public String iso3166;
    public String identifier;
    public int order;
    @Transient
    public DualStringTranslation language_names;
    
    public Language(){
    }
    
    private Language(Parcel in){
        id = in.readInt();
        iso639 = in.readString();
        iso3166 = in.readString();
        identifier = in.readString();
        order = in.readInt();
        language_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Generated(hash = 1787681497)
    public Language(int id, String iso639, String iso3166, String identifier, int order) {
        this.id = id;
        this.iso639 = iso639;
        this.iso3166 = iso3166;
        this.identifier = identifier;
        this.order = order;
    }

    @Transient
    public static Parcelable.Creator<Language> CREATOR = new Creator<Language>() {
        
        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
        
        @Override
        public Language createFromParcel(Parcel source) {
            return new Language(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(iso639);
        dest.writeString(iso3166);
        dest.writeString(identifier);
        dest.writeInt(order);
        dest.writeParcelable(language_names, flags);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso639() {
        return this.iso639;
    }

    public void setIso639(String iso639) {
        this.iso639 = iso639;
    }

    public String getIso3166() {
        return this.iso3166;
    }

    public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    

}
