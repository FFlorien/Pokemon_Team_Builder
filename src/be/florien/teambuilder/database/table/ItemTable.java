
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.Item;

public class ItemTable extends DBTable<Item> {

    public static final String TABLE_NAME = "items";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_FLING_POWER = "fling_power";
    public static final String COLUMN_FLING_EFFECT_ID = "fling_effect_id";

    public static final String TABLE_LANGUAGE_NAME = "item_names";

    public static final String COLUMN_LANGUAGE_ITEM_ID = "item_id";
    public static final String COLUMN_LANGUAGE_NAME = "name";

    public ItemTable() {
        super(TABLE_NAME, Item.class);
    }

    @Override
    public ItemTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public ItemTable selectName() {
        selectTable(new TranslationTableField(TABLE_LANGUAGE_NAME, COLUMN_LANGUAGE_ITEM_ID, COLUMN_LANGUAGE_NAME));
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
