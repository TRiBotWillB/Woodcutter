package scripts.woodcutter.framework;

import org.tribot.api2007.Login;
import org.tribot.api2007.Skills;
import scripts.woodcutter.data.Location;

/**
 * Created by willb on 05/09/2017.
 */
public class WoodcutterTask {

    public enum StopCondition {XP_GAINED, LEVELS_GAINED, LOGS_CHOPPED}

    private StopCondition stopCondition;

    private Location locationToChopAt;
    private int logsChopped;
    private int startLevel, startXp;
    private int stoppingConditionAmount;

    public static class Builder {

        private WoodcutterTask.StopCondition stopCondition;

        private Location locationToChopAt;
        private int stoppingConditionAmount;

        public Builder stopCondition(WoodcutterTask.StopCondition condition) {
            this.stopCondition = condition;
            return this;
        }

        public Builder locationToChopAt(Location location) {
            this.locationToChopAt = location;

            return this;
        }

        public Builder stoppingConditionAmt(int amount) {
            this.stoppingConditionAmount = amount;

            return this;
        }

        public WoodcutterTask build() {
            return new WoodcutterTask(this);
        }
    }

    public WoodcutterTask(Builder builder) {
        this.stopCondition = builder.stopCondition;
        this.locationToChopAt = builder.locationToChopAt;
        this.stoppingConditionAmount = builder.stoppingConditionAmount;
    }

    public boolean isComplete() {
        if (stoppingConditionAmount == 0)
            return false;

        if ((startXp == 0 || startLevel == 0)) {
            if (Login.getLoginState() == Login.STATE.INGAME) {
                startXp = Skills.SKILLS.WOODCUTTING.getXP();
                startLevel = Skills.SKILLS.WOODCUTTING.getActualLevel();
            } else {
                return false;
            }
        }

        switch (stopCondition) {
            case XP_GAINED:
                return Skills.SKILLS.WOODCUTTING.getXP() - startXp >= stoppingConditionAmount;
            case LEVELS_GAINED:
                return Skills.SKILLS.WOODCUTTING.getActualLevel() - startLevel >= stoppingConditionAmount;
            case LOGS_CHOPPED:
                return logsChopped >= stoppingConditionAmount;
        }
        return false;
    }

    public void incrementFoodCooked() {
        logsChopped++;
    }

    public Location getLocationToChopAt() {
        return locationToChopAt;
    }

    public StopCondition getStopCondition() {
        return stopCondition;
    }

    public String getDisplayString() {
        return stopCondition + " >= " + stoppingConditionAmount;
    }

    public int getLogsChopped() {
        return logsChopped;
    }

}
