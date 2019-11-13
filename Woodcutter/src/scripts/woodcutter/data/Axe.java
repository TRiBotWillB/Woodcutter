package scripts.woodcutter.data;

import org.tribot.api.General;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;

import java.util.HashMap;

public enum Axe {

    BRONZE("Bronze axe", 1, 1),
    IRON("Iron axe", 1, 1),
    STEEL("Steel axe", 5, 6),
    BLACK("Black axe", 10, 11),
    MITHRIL("Mithril axe", 20, 21),
    ADAMANT("Adamant axe", 30, 31),
    RUNE("Rune axe", 40, 41),
    DRAGON("Dragon axe", 60, 61);

    private String name;
    private int levelToEquip;
    private int levelToUse;


    Axe(String name, int levelToEquip, int levelToUse) {
        this.name = name;
        this.levelToEquip = levelToEquip;
        this.levelToUse = levelToUse;
    }

    public String getName() {
        return name;
    }

    public int getLevelToEquip() {
        return levelToEquip;
    }

    public int getLevelToUse() {
        return levelToUse;
    }


    public static Axe getBestAxe() {
        HashMap<String, Integer> bankCache = Variables.get().getBankCache();
        int level = Skills.SKILLS.WOODCUTTING.getActualLevel();

        for (int i = values().length - 1; i > -1; i--) {
            Axe axe = values()[i];

            if (level > axe.getLevelToUse()) {
                if ((bankCache != null && bankCache.containsKey(axe.getName()))
                        || (Equipment.isEquipped(axe.getName()))
                        || (Inventory.getCount(axe.getName()) > 0)) {
                    return axe;
                }
            }
        }

        return null;
    }
}
