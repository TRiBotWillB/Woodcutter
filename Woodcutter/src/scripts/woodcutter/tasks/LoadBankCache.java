package scripts.woodcutter.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import scripts.api.banking.Banking07;
import scripts.api.frameworks.task.Task;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.shared.helpers.BankHelper;
import scripts.utils.BankUtil;
import scripts.woodcutter.data.Axe;
import scripts.woodcutter.data.Variables;

public class LoadBankCache implements Task {

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public boolean validate() {
        return Variables.get().getBankCache() == null;
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

                Variables.get().setBankCache(Banking07.getBankCache());
            } else {
                Banking.openBank();
            }
        } else {
            DaxWalker.walkToBank();
        }
    }

    @Override
    public String getStatus() {
        return "Loading bank cache";
    }
}
