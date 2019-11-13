package scripts.woodcutter.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import scripts.api.frameworks.task.Task;
import scripts.woodcutter.data.Axe;

public class EquipAxe implements Task {

    Axe bestAxe;

    @Override
    public int priority() {
        return 19;
    }

    @Override
    public boolean validate() {
        int level = Skills.SKILLS.WOODCUTTING.getActualLevel();
        bestAxe = Axe.getBestAxe();

        return level >= bestAxe.getLevelToEquip() && Inventory.getCount(bestAxe.getName()) > 0
                && !Equipment.isEquipped(bestAxe.getName());
    }

    @Override
    public boolean alwaysExecute() {
        return false;
    }

    @Override
    public void execute() {
        if (Banking.isBankScreenOpen()) Banking.close();

        RSItem[] axes = Inventory.find(bestAxe.getName());

        if (axes.length > 0 && axes[0] != null) {
            if (axes[0].click("Wield")) {
                Timing.waitCondition(() -> Equipment.isEquipped(bestAxe.getName()), General.random(3000, 4000));
            }
        }
    }

    @Override
    public String getStatus() {
        return "Equipping axe";
    }
}
