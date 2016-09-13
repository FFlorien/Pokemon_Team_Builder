
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.GenerationTable;
import be.florien.teambuilder.database.table.ItemTable;
import be.florien.teambuilder.database.table.MachineTable;
import be.florien.teambuilder.database.table.MoveDamageClassTable;
import be.florien.teambuilder.database.table.MoveMetaTable;
import be.florien.teambuilder.database.table.MoveTable;
import be.florien.teambuilder.database.table.TypeTable;
import be.florien.teambuilder.model.Move;

public class MoveLoader extends AbstractAsyncTaskLoader<Move> {

    private int mId;

    public MoveLoader(Context context, int id) {
        super(context);
        mId = id;
    }

    @Override
    public Move loadInBackground() {
        MoveTable table = new MoveTable()
                .selectAccuracy()
                .selectEffect()
                .selectEffectChance()
                .selectId()
                .selectIdentifier()
                .selectName()
                .selectPower()
                .selectPP()
                .selectPriority()
                .selectMachine(
                        new MachineTable().selectId().selectItem(new ItemTable().selectId().selectName()))
                .selectGeneration(
                        new GenerationTable().selectId().selectIdentifier())
                .selectDamageClass(
                        new MoveDamageClassTable().selectId().selectName())
                .selectMeta(
                        new MoveMetaTable().selectAilment().selectId().selectAilmentChance().selectCritRate().selectFlinchChance().selectHealing()
                                .selectMaxHits().selectMaxTurns().selectMinHits().selectMinTurns().selectRecoil().selectStatChance())
                .selectType(
                        new TypeTable()
                                .selectId()
                                .selectName()
                );
        table.addWhere(new WhereStatement(MoveTable.COLUMN_ID, String.valueOf(mId)));
        DBTableQueryHelper<Move> queryHelper = new DBTableQueryHelper<Move>(getContext());
        return queryHelper.query(table).get(0);
    }
}
