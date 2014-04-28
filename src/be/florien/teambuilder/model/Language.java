package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {
    
    public int id;
    public String iso639;
    public String iso3166;
    public String identifier;
    public int order;
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
    

}
