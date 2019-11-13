package scripts.woodcutter.data;

import org.tribot.api.util.abc.ABCUtil;
import scripts.api.antiban.Antiban;
import scripts.api.data.ScriptVars;
import scripts.api.frameworks.task.TaskScript;
import scripts.woodcutter.framework.WoodcutterTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Variables extends ScriptVars {

    public Variables(TaskScript script) {
        super(script);
    }

    public static Variables get() {
        return (Variables) ScriptVars.get();
    }

    private ArrayList<WoodcutterTask> tasks = new ArrayList<>();
    private WoodcutterTask currentTask;
    private Antiban antiban;
    private ABCUtil abcUtil;

    private HashMap<String, Integer> bankCache;

    public WoodcutterTask getCurrentTask() {
        if (currentTask != null) {
            if (currentTask.isComplete()) {
                currentTask = null;
            }
            return currentTask;
        }

        for (WoodcutterTask cookingTask : getTasks()) {
            if (!cookingTask.isComplete()) {
                //store start xp & level, set in task
                currentTask = cookingTask;
                break;
            }
        }
        return currentTask;
    }

    public ArrayList<WoodcutterTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<WoodcutterTask> tasks) {
        this.tasks = tasks;
    }

    public Antiban getAntiban() {
        return antiban;
    }

    public void setAntiban(Antiban antiban) {
        this.antiban = antiban;
    }

    public ABCUtil getABCUtil() {
        return abcUtil;
    }

    public void setABCUtil(ABCUtil abcUtil) {
        this.abcUtil = abcUtil;
    }

    public HashMap<String, Integer> getBankCache() {
        return bankCache;
    }

    public void setBankCache(HashMap<String, Integer> bankCache) {
        this.bankCache = bankCache;
    }
}
