
package be.florien.teambuilder.loader;

import android.content.Context;

import be.florien.joinorm.architecture.WhereStatement;
import be.florien.teambuilder.database.table.TranslationTableField;
import be.florien.teambuilder.model.table.GenerationTable;
import be.florien.teambuilder.database.helper.DBTableQueryHelper;
import be.florien.teambuilder.database.table.TypeTableTmpForPokemon;
import be.florien.teambuilder.model.Move;
import be.florien.teambuilder.model.table.ItemTable;
import be.florien.teambuilder.model.table.MachineTable;
import be.florien.teambuilder.model.table.MoveDamageClassTable;
import be.florien.teambuilder.model.table.MoveMetaAilmentTable;
import be.florien.teambuilder.model.table.MoveMetaTable;
import be.florien.teambuilder.model.table.MoveTable;
import be.florien.teambuilder.model.table.TypeTable;

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
//                .selectMoveEffects()
                .selectEffectChance()
                .selectId()
                .selectIdentifier()
                .selectMoveNames(TranslationTableField.forGeneration()/*todo*/)
                .selectPower()
                .selectPp()
                .selectPriority()
                .selectMachines(
                        new MachineTable().selectId().selectItems(new ItemTable().selectId().selectItemNames(TranslationTableField.forGeneration()/*todo*/)))
                .selectGenerations(
                        new GenerationTable().selectId().selectIdentifier())
                .selectMoveDamageClasses(
                        new MoveDamageClassTable().selectId().selectMoveDamageClassProse(TranslationTableField.forGeneration()/*todo*/))
                .selectMoveMeta(
                        new MoveMetaTable().selectMoveMetaAilments(new MoveMetaAilmentTable()/*todo*/).selectId().selectAilmentChance().selectCritRate().selectFlinchChance().selectHealing()
                                .selectMaxHits().selectMaxTurns().selectMinHits().selectMinTurns().selectRecoil().selectStatChance())
                .selectTypes(
                        new TypeTable()
                                .selectId()
                                .selectTypeNames(TranslationTableField.forLanguage()/*todo*/)
                );
        table.addWhere(new WhereStatement(MoveTable.COLUMN_ID, String.valueOf(mId)));
        DBTableQueryHelper<Move> queryHelper = new DBTableQueryHelper<Move>(getContext());
        return queryHelper.query(table).get(0);
    }
}
