
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.MoveDamageClass;

public class MoveDamageClassTable extends DBTable<MoveDamageClass> {

    public static final String TABLE_NAME = "move_damage_classes";

    public static final String COLUMN_ID = "id";

    public static final String TABLE_PROSE_NAME = "move_damage_class_prose";

    public static final String COLUMN_PROSE_ID = "move_damage_class_id";
    public static final String COLUMN_PROSE_LANGUAGE_ID = "local_language_id";
    public static final String COLUMN_PROSE_NAME = "name";
    public static final String COLUMN_PROSE_DESCRIPTION = "description";

    public MoveDamageClassTable() {
        super(TABLE_NAME, MoveDamageClass.class);
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> innerTable) {
        if(innerTable instanceof TranslationTableField){
            return ((TranslationTableField)innerTable).getJoinToTranslatedTable(getDataName() + "." + getId());
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

    public MoveDamageClassTable selectName() {
        selectTable(new TranslationTableField(TABLE_PROSE_NAME, COLUMN_PROSE_ID, COLUMN_PROSE_NAME, COLUMN_PROSE_DESCRIPTION));
        return this;
    }

    @Override
    public MoveDamageClassTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

}
