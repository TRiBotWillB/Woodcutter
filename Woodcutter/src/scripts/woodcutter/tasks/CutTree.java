package scripts.woodcutter.tasks;

import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.api.Timing07;
import scripts.api.frameworks.task.Task;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.woodcutter.data.Axe;
import scripts.woodcutter.data.Variables;
import scripts.woodcutter.framework.WoodcutterTask;

import java.util.function.Predicate;

public class CutTree implements Task {

    WoodcutterTask task;

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public boolean validate() {
        task = Variables.get().getCurrentTask();

        if (task == null)
            return false;

        return Inventory.getCount(Axe.getBestAxe().getName()) > 0
                || Equipment.isEquipped(Axe.getBestAxe().getName());
    }

    @Override
    public boolean alwaysExecute() {
        return false;
    }

    @Override
    public void execute() {
        RSTile centerTile = task.getLocationToChopAt().getCenterTile();

        if (Player.getPosition().distanceTo(centerTile) > 15) {
            DaxWalker.walkTo(centerTile);
        } else if (Player.getAnimation() == -1) {
            RSObject[] trees = Objects.findNearest(Player.getPosition().distanceTo(centerTile) + task.getLocationToChopAt().getRadius(),
                    ((Predicate<RSObject>) o -> o.getPosition().distanceTo(centerTile) < 15)
                            .and(Filters.Objects.nameEquals(task.getLocationToChopAt().getTreeType().getTreeNames())));

            if (trees.length > 0 && trees[0] != null) {
                if (Player.getPosition().distanceTo(trees[0]) > 5)
                    Walking.walkTo(trees[0]);

                if (!trees[0].isOnScreen() || !trees[0].isClickable())
                    Camera.turnToTile(trees[0]);

                if (trees[0].click("Chop down")) {
                    Timing07.waitCondition(() -> Player.getAnimation() != -1, General.random(3000, 4000));
                }
            }
        }
    }

    @Override
    public String getStatus() {
        return "Cutting tree";
    }
}
