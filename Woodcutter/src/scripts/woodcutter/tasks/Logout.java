package scripts.woodcutter.tasks;

import org.tribot.api2007.Login;
import scripts.api.frameworks.task.Task;
import scripts.api.frameworks.task.TaskScript;
import scripts.woodcutter.data.Variables;

public class Logout implements Task {

    @Override
    public int priority() {
        return 500;
    }

    @Override
    public boolean validate() {
        return Variables.get().getCurrentTask() == null;
    }

    @Override
    public boolean alwaysExecute() {
        return false;
    }

    @Override
    public void execute() {
        Login.logout();
        Variables.get().getScript().setRunning(false);
    }

    @Override
    public String getStatus() {
        return "Out of tasks, logging out...";
    }
}
