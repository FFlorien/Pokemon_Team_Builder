
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.teambuilder.model.UserPokemon;

public class UserPokemonTable extends DBTable<UserPokemon> {

    public static final String TABLE_NAME = "pokemon_user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POKEMON_ID = "pokemon_id";
    public static final String COLUMN_LEVEL = "'level'";
    public static final String COLUMN_HAS_EVOLVED = "has_evolved";
    public static final String COLUMN_ABILITY_ID = "ability_id";
    public static final String COLUMN_MOVE1_ID = "move1_id";
    public static final String COLUMN_MOVE2_ID = "move2_id";
    public static final String COLUMN_MOVE3_ID = "move3_id";
    public static final String COLUMN_MOVE4_ID = "move4_id";

    public UserPokemonTable() {
        super(TABLE_NAME, UserPokemon.class);
    }

    @Override
    public UserPokemonTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public UserPokemonTable selectPokemonId() {
        selectInt(COLUMN_POKEMON_ID);
        return this;
    }

    public UserPokemonTable writeId() {
        writeNull(COLUMN_ID);
        return this;
    }

    public UserPokemonTable writePokemonId(int id) {
        writeInt(COLUMN_POKEMON_ID, id);
        return this;
    }

    public UserPokemonTable writeLevel(int level) {
        writeInt(COLUMN_LEVEL, level);
        return this;
    }

    public UserPokemonTable writeHasEvolved(boolean hasEvolved) {
        writeBoolean(COLUMN_HAS_EVOLVED, hasEvolved);
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
