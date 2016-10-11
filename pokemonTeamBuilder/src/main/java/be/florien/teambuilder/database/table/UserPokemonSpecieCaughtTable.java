
package be.florien.teambuilder.database.table;

import java.util.Collections;
import java.util.List;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.UserPokemonSpecieCaught;

public class UserPokemonSpecieCaughtTable extends DBTable<UserPokemonSpecieCaught> {

    public static final String TABLE_NAME = "pokemon_specie_catched";

    public static final String COLUMN_ID = "id";

    public UserPokemonSpecieCaughtTable() {
        super(TABLE_NAME);
    }

    @Override
    protected UserPokemonSpecieCaught createNewInstance() {
        return new UserPokemonSpecieCaught();
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
    public void setFieldValue(String fieldName, Object value) {
        if ("id".equals(fieldName)) {
            currentObject.id = (int) value;
        }
    }

    @Override
    public Object getFieldValue(String fieldName) {
        return currentObject.id;
    }

    @Override
    public List<String> getId() {
        return Collections.singletonList(COLUMN_ID);
    }

}
