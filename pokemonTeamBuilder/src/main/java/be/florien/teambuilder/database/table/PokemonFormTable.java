
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.PokemonForm;

public class PokemonFormTable extends DBTable<PokemonForm> {

    public static final String TABLE_NAME = "pokemon_forms";

    public static final String TABLE_LANGUAGE_NAME = "pokemon_form_names";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_FORM_IDENTIFIER = "form_identifier";
    public static final String COLUMN_POKEMON_ID = "pokemon_id";
    public static final String COLUMN_VERSION_GROUP = "introduced_in_version_group_id";
    public static final String COLUMN_FORM_ORDER = "form_order";
    public static final String COLUMN_ORDER = "order";
    public static final String COLUMN_IS_MEGA = "is_mega";

    public static final String COLUMN_LANGUAGE_ID = "pokemon_form_id";
    public static final String COLUMN_LANGUAGE_FORM = "form_name";
    public static final String COLUMN_LANGUAGE_POKEMON = "pokemon_name";

    public PokemonFormTable() {
        super(TABLE_NAME, PokemonForm.class);
    }

    @Override
    public PokemonFormTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public PokemonFormTable selectIdentifier() {
        selectString(COLUMN_IDENTIFIER);
        return this;
    }

    public PokemonFormTable selectFormIdentifier() {
        selectString(COLUMN_FORM_IDENTIFIER);
        return this;
    }

    public PokemonFormTable selectFormOrder() {
        selectInt(COLUMN_FORM_ORDER);
        return this;
    }

    public PokemonFormTable selectOrder() {
        selectInt(COLUMN_ORDER);
        return this;
    }

    public PokemonFormTable selectIsMega() {
        selectBoolean(COLUMN_IS_MEGA);
        return this;
    }

    public PokemonFormTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ID, COLUMN_LANGUAGE_FORM, COLUMN_LANGUAGE_POKEMON));
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> field) {
        if (field instanceof TranslationTableField) {
            return ((TranslationTableField) field).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
