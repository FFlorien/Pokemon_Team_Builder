
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.appconfiguration.Constant;
import be.florien.teambuilder.model.PokemonMoveForPokemon;

public class PokemonMoveForPokemonTable extends DBTable<PokemonMoveForPokemon> {

    public static final String TABLE_NAME = "pokemon_moves";

    public static final String COLUMN_POKEMON_ID = "pokemon_id";
    public static final String COLUMN_VERSION_GROUP_ID = "version_group_id";
    public static final String COLUMN_MOVE_ID = "move_id";
    public static final String COLUMN_POKEMON_MOVE_METHOD_ID = "pokemon_move_method_id";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_ORDER = "order";

    public static final String TABLE_LANGUAGE_NAME = "pokemon_move_methods";

    public static final String COLUMN_LANGUAGE_ID = "id";
    public static final String COLUMN_LANGUAGE_IDENTIFIER = "version_group_id";

    public PokemonMoveForPokemonTable() {
        super(TABLE_NAME, PokemonMoveForPokemon.class);
        addWhere(new WhereStatement(COLUMN_VERSION_GROUP_ID, Constant.getVersionGroupId()));
    }

    @Override
    public PokemonMoveForPokemonTable selectId() {
        selectId(COLUMN_MOVE_ID, COLUMN_POKEMON_MOVE_METHOD_ID);
        return this;
    }

    public PokemonMoveForPokemonTable selectMove(MoveTable table) {
        selectTable(table);
        return this;
    }

    public PokemonMoveForPokemonTable selectMethod() {
        selectInt(COLUMN_POKEMON_MOVE_METHOD_ID);
        return this;
    }

    public PokemonMoveForPokemonTable selectLevel() {
        selectInt(COLUMN_LEVEL);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> innerTable) {
        if (innerTable instanceof MoveTable) {
            return getJoinOnRef(innerTable, COLUMN_MOVE_ID, false);
        }
        return null;
    }

    @Override
    protected String getOrderByForThis() {
        return TABLE_NAME + "." + COLUMN_POKEMON_MOVE_METHOD_ID + ", " + TABLE_NAME + "." + COLUMN_LEVEL;
    }

    @Override
    public String getId() {
        return COLUMN_MOVE_ID;
    }

}
