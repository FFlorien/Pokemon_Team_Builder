
package be.florien.teambuilder.database.table;

import be.florien.joinorm.architecture.DBTable;
import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.appconfiguration.Constant;
import be.florien.teambuilder.model.Machine;

public class MachineTable extends DBTable<Machine> {

    public static final String TABLE_NAME = "machines";

    public static final String COLUMN_ID = "machine_number";
    public static final String COLUMN_VERSION_GROUP_ID = "version_group_id";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_MOVE_ID = "move_id";

    public MachineTable() {
        super(TABLE_NAME, Machine.class);
        addWhere(new WhereStatement(COLUMN_VERSION_GROUP_ID, Constant.getVersionGroupId()));
        WhereStatement orNull = new WhereStatement(COLUMN_VERSION_GROUP_ID, true);
        orNull.setOr(true);
        addWhere(orNull);
    }

    @Override
    public MachineTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public MachineTable selectMove(MoveTable table) {
        selectTable(table);
        return this;
    }

    public MachineTable selectItem(ItemTable table) {
        selectTable(table);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        if (foreignTable instanceof MoveTable) {
            return getJoinOnRef(foreignTable, COLUMN_MOVE_ID, false);
        } else if (foreignTable instanceof ItemTable) {
            return getJoinOnRef(foreignTable, COLUMN_ITEM_ID, true);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
