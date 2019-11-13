package scripts.woodcutter.data;

import org.tribot.api2007.types.RSTile;

public enum Location {

    LUMBRDIGE_TREE(Tree.TREE, new RSTile(3170, 3228, 0), 15),
    LUMBRIDGE_OAK(Tree.OAK, new RSTile(3157, 3233, 0), 15),
    LUMBRIDBE_WEST_YEW(Tree.YEW, new RSTile(3150, 3241, 0), 15),

    VARROCK_WEST_TREE(Tree.TREE, new RSTile(3166, 3410, 0), 15),
    VARROCK_WEST_OAK(Tree.OAK, new RSTile(3168, 3419, 0), 15),
    VARROCK_CASTE_YEW(Tree.YEW, new RSTile(3215, 3501, 0), 15),

    SEERS_TREE(Tree.TREE, new RSTile(2718, 3441, 0), 15),
    SEERS_OAK(Tree.OAK, new RSTile(2719, 3480, 0), 20),
    SEERS_WILLOW(Tree.WILLOW, new RSTile(2712, 3510, 0), 15),
    SEERS_MAPLE(Tree.MAPLE, new RSTile(2726, 3501, 0), 15),
    SEERS_YEW(Tree.YEW, new RSTile(2711, 3462, 0), 10),
    SEERS_MAGIC(Tree.YEW, new RSTile(2702, 3398, 0), 10),

    CATHERBY_WILLOW(Tree.WILLOW, new RSTile(2776, 3427, 0), 20),
    CATHERBY_YEW(Tree.YEW, new RSTile(2763, 3432, 0), 20),

    DRAYNOR_WILLOW(Tree.WILLOW, new RSTile(3086, 3233, 0), 15),

    BARBARIAN_OUTPOST_WILLOW(Tree.WILLOW, new RSTile(2516, 3570, 0), 25),

    RANGE_GUILD_MAGIC(Tree.MAGIC, new RSTile(2694, 3426, 0), 10);


    private Tree treeType;
    private RSTile centerTile;
    private int radius;

    Location(Tree treeType, RSTile centerTile, int radius) {
        this.treeType = treeType;
        this.centerTile = centerTile;
        this.radius = radius;
    }

    public Tree getTreeType() {
        return treeType;
    }

    public RSTile getCenterTile() {
        return centerTile;
    }

    public int getRadius() {
        return radius;
    }
}
