
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.UserPokemonSpecieCatched;

public class UserPokemonSpecieCatchedTable extends DBTable<UserPokemonSpecieCatched> {

    public static final String TABLE_NAME = "pokemon_specie_catched";

    public static final String COLUMN_ID = "id";

    public UserPokemonSpecieCatchedTable() {
        super(TABLE_NAME, UserPokemonSpecieCatched.class);
    }

    @Override
    public UserPokemonSpecieCatchedTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }
    
    public UserPokemonSpecieCatchedTable writeId(int id){
        writeInt(COLUMN_ID, id);
        return this;
    }

    public UserPokemonSpecieCatchedTable delete(int id){
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
