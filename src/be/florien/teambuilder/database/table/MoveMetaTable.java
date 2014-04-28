
package be.florien.teambuilder.database.table;

import be.florien.databasecomplexjoins.architecture.DBTable;
import be.florien.teambuilder.model.MoveMeta;

public class MoveMetaTable extends DBTable<MoveMeta> {

    public static final String TABLE_NAME = "move_meta";

    public static final String COLUMN_ID = "move_id";
    public static final String COLUMN_CATEGORY_ID = "meta_category_id";
    public static final String COLUMN_AILMENT_ID = "meta_ailment_id";
    public static final String COLUMN_MIN_HITS = "min_hits";
    public static final String COLUMN_MAX_HITS = "max_hits";
    public static final String COLUMN_MIN_TURNS = "min_turns";
    public static final String COLUMN_MAX_TURNS = "max_turns";
    public static final String COLUMN_RECOIL = "recoil";
    public static final String COLUMN_HEALING = "healing";
    public static final String COLUMN_CRIT_RATE = "crit_rate";
    public static final String COLUMN_AILMENT_CHANCE = "ailment_chance";
    public static final String COLUMN_FLINCH_CHANCE = "flinch_chance";
    public static final String COLUMN_STAT_CHANCE = "stat_chance";

    public MoveMetaTable() {
        super(TABLE_NAME, MoveMeta.class);
    }

    @Override
    public MoveMetaTable selectId() {
        selectId(COLUMN_ID);
        return this;
    }

    public MoveMetaTable selectAilment() {
        selectTable(new MoveMetaAilmentTable());
        return this;
    }

    public MoveMetaTable selectMinHits() {
        selectInt(COLUMN_MIN_HITS);
        return this;
    }

    public MoveMetaTable selectMaxHits() {
        selectInt(COLUMN_MAX_HITS);
        return this;
    }

    public MoveMetaTable selectMinTurns() {
        selectInt(COLUMN_MIN_TURNS);
        return this;
    }

    public MoveMetaTable selectMaxTurns() {
        selectInt(COLUMN_MAX_TURNS);
        return this;
    }

    public MoveMetaTable selectRecoil() {
        selectInt(COLUMN_RECOIL);
        return this;
    }

    public MoveMetaTable selectHealing() {
        selectInt(COLUMN_HEALING);
        return this;
    }

    public MoveMetaTable selectCritRate() {
        selectInt(COLUMN_CRIT_RATE);
        return this;
    }

    public MoveMetaTable selectAilmentChance() {
        selectInt(COLUMN_AILMENT_CHANCE);
        return this;
    }

    public MoveMetaTable selectFlinchChance() {
        selectInt(COLUMN_FLINCH_CHANCE);
        return this;
    }

    public MoveMetaTable selectStatChance() {
        selectInt(COLUMN_STAT_CHANCE);
        return this;
    }

    @Override
    public String getJoinToInnerTable(DBTable<?> foreignTable) {
        if (foreignTable instanceof MoveMetaAilmentTable) {
            return getJoinOnRef(foreignTable, COLUMN_AILMENT_ID, false);
        }
        return "";
    }

    @Override
    public String getId() {
        return COLUMN_ID;
    }

}
