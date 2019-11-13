package scripts.woodcutter;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MessageListening07;
import org.tribot.script.interfaces.Painting;
import scripts.api.data.ScriptVars;
import scripts.api.frameworks.task.TaskScript;
import scripts.api.gui.GUI;
import scripts.api.painting.Painter;
import scripts.api.painting.components.PaintComponent;
import scripts.api.painting.components.PaintListComponent;
import scripts.api.painting.components.PaintTextComponent;
import scripts.woodcutter.data.Location;
import scripts.woodcutter.data.Variables;
import scripts.woodcutter.framework.WoodcutterTask;
import scripts.woodcutter.tasks.*;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


@ScriptManifest(name = "Woodcutter", authors = "WillB", category = "Local")
public class Main extends TaskScript implements MessageListening07, Painting {

    private Painter painter;

    private ArrayList<URL> stylesheets = new ArrayList<>();
    private URL fxml;

    private GUI gui;

    @Override
    public void onStart() {
        Variables.set(getVars());

        General.useAntiBanCompliance(true);

        try {
            fxml = new URL("https://www.willbscripting.com/scripts/woodcutter/WCGUI.fxml");
            stylesheets.add(new URL("https://www.willbscripting.com/scripts/woodcutter/Woodcutter-Style.css"));
            stylesheets.add(new URL("https://fonts.googleapis.com/css?family=Quicksand"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        gui = new GUI(fxml, stylesheets.toArray(new URL[stylesheets.size()]), true);
        gui.show();

        while (gui.isOpen()) sleep(500);


        this.painter = new Painter(this);

        this.getTaskSet().addAll(new LoadBankCache(), new CutTree(), new Bank(), new EquipAxe(), new Logout());
    }

    @Override
    public void onStop() {
        Variables.destroy();
    }

    @Override
    public ScriptVars getVars() {
        return new Variables(this);
    }

    @Override
    public PaintComponent[] getPaintComponents() {
        return new PaintComponent[]{
                new PaintTextComponent.Builder("Run Time: " + Timing.msToString(getRunningTime())).setMarginTop(25).build(),
                new PaintTextComponent.Builder("Status: " + getStatus()).setMarginTop(50).build()
        };
    }

    @Override
    public void onPaint(Graphics g) {

        g.drawRect(235, 275, 100, 20);
        if (painter != null)
            painter.paint(g);
    }

    @Override
    public void serverMessageReceived(String s) {
        super.serverMessageReceived(s);

        WoodcutterTask task = Variables.get().getCurrentTask();

        if (task != null) {
            if (s.contains("You get some")) {
                task.incrementFoodCooked();
            }
        }
    }
}
