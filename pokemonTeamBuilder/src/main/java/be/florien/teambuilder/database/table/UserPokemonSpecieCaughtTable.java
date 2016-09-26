
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

public class UserPokemonSpecieCaughtTable extends DBTable<UserPokemonSpecieCaught> {

    public static final String TABLE_NAME = "pokemon_specie_catched";

    public static final String COLUMN_ID = "id";

    public UserPokemonSpecieCaughtTable() {
        super(TABLE_NAME, UserPokemonSpecieCaught.class);
    }

    @Override
    public UserPokemonSpecieCaughtTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }
    
    public UserPokemonSpecieCaughtTable writeId(int id){
        writeInt(COLUMN_ID, id);
        return this;
    }

    public UserPokemonSpecieCaughtTable delete(int id){
        deleteId(id);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
