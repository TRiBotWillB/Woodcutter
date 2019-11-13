package scripts.woodcutter.tasks;

import obf.B;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import scripts.api.banking.Banking07;
import scripts.api.frameworks.task.Task;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.shared.helpers.BankHelper;
import scripts.utils.BankUtil;
import scripts.woodcutter.data.Axe;
import scripts.woodcutter.data.Variables;

import java.util.ArrayList;

public class Bank implements Task {

    ArrayList<String> itemsToWithdraw = new ArrayList<>();

    @Override
    public int priority() {
        return 20;
    }

    @Override
    public boolean validate() {
        itemsToWithdraw.clear();

        Axe bestAxe = Axe.getBestAxe();
        int level = Skills.SKILLS.WOODCUTTING.getActualLevel();

        if (Inventory.getCount(bestAxe.getName()) == 0 && (level < bestAxe.getLevelToEquip() || !Equipment.isEquipped(bestAxe.getName()))) {
            itemsToWithdraw.add(bestAxe.getName());
        }

        return itemsToWithdraw.size() > 0 || Inventory.isFull();
    }

    @Override
    public boolean alwaysExecute() {
        return false;
    }

    @Override
    public void execute() {
        if (BankHelper.isInBank()) {
            if (Banking.isBankScreenOpen()) {
                Timing.waitCondition(() -> BankUtil.isLoaded(), General.random(1000, 1500));

                if (Inventory.getAll().length > 1)
                    Banking.depositAllExcept(Axe.getBestAxe().getName());

                for (String s : itemsToWithdraw) {
                    BankUtil.withdraw(1, s);
                }

                Variables.get().setBankCache(BankUtil.getBankItems());
            } else {
                Banking.openBank();
            }
        } else {
            DaxWalker.walkToBank();
        }
    }

    @Override
    public String getStatus() {
        return "Banking";
    }
}
