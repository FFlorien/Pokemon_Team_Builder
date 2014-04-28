
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.MoveMetaAilment;

public class MoveMetaAilmentTable extends DBTable<MoveMetaAilment> {

    public static final String TABLE_NAME = "move_meta_ailments";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IDENTIFIER = "identifier";

    public static final String TABLE_NAME_NAME = "move_meta_ailment_names";

    public static final String COLUMN_NAME_ID = "move_meta_ailment_id";
    public static final String COLUMN_NAME_LANGUAGE_ID = "local_language_id";
    public static final String COLUMN_NAME_NAME = "name";

    public MoveMetaAilmentTable() {
        super(TABLE_NAME, MoveMetaAilment.class);
        selectTable(new TranslationTableField(TABLE_NAME_NAME, COLUMN_NAME_ID, COLUMN_NAME_NAME));
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

    @Override
    public MoveMetaAilmentTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

}
