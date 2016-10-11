
package be.florien.teambuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "pokemon_forms")
public class PokemonForm implements Parcelable {

    @Id
    public int id;
    public String identifier;
    public String form_identifier;
    // public Version version;
    public int order;
    public int form_order;
    public boolean is_mega;
    @Transient()
    public DualStringTranslation pokemon_form_names;

    public PokemonForm() {
    }

    private PokemonForm(Parcel in) {
        id = in.readInt();
        identifier = in.readString();
        form_identifier = in.readString();
        order = in.readInt();
        form_order = in.readInt();
        is_mega = in.readInt() == 1;
        pokemon_form_names = in.readParcelable(DualStringTranslation.class.getClassLoader());
    }

    @Generated(hash = 1245956375)
    public PokemonForm(int id, String identifier, String form_identifier, int order,
            int form_order, boolean is_mega) {
        this.id = id;
        this.identifier = identifier;
        this.form_identifier = form_identifier;
        this.order = order;
        this.form_order = form_order;
        this.is_mega = is_mega;
    }

    @Transient
    public static Creator<PokemonForm> CREATOR = new Creator<PokemonForm>() {

        @Override
        public PokemonForm[] newArray(int size) {
            return new PokemonForm[size];
        }

        @Override
        public PokemonForm createFromParcel(Parcel source) {
            return new PokemonForm(source);
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
        dest.writeString(form_identifier);
        dest.writeInt(order);
        dest.writeInt(form_order);
        dest.writeInt(is_mega ? 1 : 0);
        dest.writeParcelable(pokemon_form_names, flags);
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

    public String getForm_identifier() {
        return this.form_identifier;
    }

    public void setForm_identifier(String form_identifier) {
        this.form_identifier = form_identifier;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getForm_order() {
        return this.form_order;
    }

    public void setForm_order(int form_order) {
        this.form_order = form_order;
    }

    public boolean getIs_mega() {
        return this.is_mega;
    }

    public void setIs_mega(boolean is_mega) {
        this.is_mega = is_mega;
    }

}
