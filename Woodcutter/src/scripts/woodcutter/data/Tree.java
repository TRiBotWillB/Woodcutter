package scripts.woodcutter.data;

public enum Tree {

    TREE(new String[]{"Tree", "Dead tree"}),
    OAK(new String[]{"Oak"}),
    WILLOW(new String[]{"Willow"}),
    MAPLE(new String[]{"Maple tree"}),
    YEW(new String[]{"Yew"}),
    MAGIC(new String[]{"Magic"});

    private String[] treeNames;

    Tree(String[] treeNames) {
        this.treeNames = treeNames;
    }

    public String[] getTreeNames() {
        return treeNames;
    }
}
