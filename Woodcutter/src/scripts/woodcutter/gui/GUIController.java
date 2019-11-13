package scripts.woodcutter.gui;

import com.allatori.annotations.DoNotRename;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import org.tribot.api2007.WebWalking;
import scripts.api.gui.AbstractGUIController;
import scripts.woodcutter.data.Location;
import scripts.woodcutter.data.Tree;
import scripts.woodcutter.data.Variables;
import scripts.woodcutter.framework.WoodcutterTask;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by willb on 18/08/2018.
 */
@DoNotRename
public class GUIController extends AbstractGUIController {

    @FXML
    @DoNotRename
    public JFXComboBox cmbTree;

    @FXML
    @DoNotRename
    public JFXComboBox cmbLocation;

    @FXML
    @DoNotRename
    public JFXComboBox cmbStopCondition;

    @FXML
    @DoNotRename
    public JFXTextField txtStopAmount;

    @FXML
    @DoNotRename
    public JFXTreeTableView tblTasks;

    @FXML
    @DoNotRename
    public JFXTreeTableColumn<WoodcutterTask, String> colTree;

    @FXML
    @DoNotRename
    public JFXTreeTableColumn<WoodcutterTask, String> colLocation;

    @FXML
    @DoNotRename
    public JFXTreeTableColumn<WoodcutterTask, String> colStopCondition;

    @FXML
    @DoNotRename
    public Label label1;

    @FXML
    @DoNotRename
    public Label label2;

    private double xOffset, yOffset;


    final TreeItem<WoodcutterTask> root = new TreeItem<WoodcutterTask>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Please enter a value");

        cmbTree.getItems().addAll(Tree.values());
        cmbTree.getSelectionModel().select(0);

        cmbLocation.getItems().addAll(Location.values());
        cmbLocation.getSelectionModel().select(0);

        cmbStopCondition.getItems().addAll(WoodcutterTask.StopCondition.values());
        cmbStopCondition.getSelectionModel().select(0);


        colTree.setCellValueFactory(param -> {
            if (param.getValue().getValue().getLocationToChopAt().getTreeType() != null) {
                return new SimpleStringProperty(param.getValue().getValue().getLocationToChopAt().getTreeType().toString());
            }
            return new SimpleStringProperty("NULL");
        });

        colLocation.setCellValueFactory(param -> {
            if (param.getValue().getValue().getLocationToChopAt() != null) {
                return new SimpleStringProperty(param.getValue().getValue().getLocationToChopAt().toString());
            }
            return new SimpleStringProperty("NULL");
        });

        colStopCondition.setCellValueFactory(param -> {
            return new SimpleStringProperty(param.getValue().getValue().getDisplayString());
        });

        txtStopAmount.getValidators().add(validator);
        label1.setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 52;");
        label2.setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 52;");
        tblTasks.setRoot(root);
        tblTasks.setShowRoot(false);
    }

    public void updateTable() {
        root.getChildren().clear();

        for (WoodcutterTask t : Variables.get().getTasks()) {
            root.getChildren().add(new TreeItem<>(t));
        }
    }

    @FXML
    @DoNotRename
    public void addTask(ActionEvent actionEvent) {
        if (!txtStopAmount.validate()) return;

        Tree tree = (Tree) cmbTree.getSelectionModel().getSelectedItem();
        Location location = (Location) cmbLocation.getSelectionModel().getSelectedItem();
        WoodcutterTask.StopCondition condition = (WoodcutterTask.StopCondition) cmbStopCondition.getSelectionModel().getSelectedItem();
        int amount = Integer.parseInt(txtStopAmount.getText());

        WoodcutterTask task = new WoodcutterTask.Builder()
                .locationToChopAt(location)
                .stopCondition(condition)
                .stoppingConditionAmt(amount)
                .build();

        Variables.get().getTasks().add(task);
        updateTable();
    }

    @FXML
    @DoNotRename
    public void removeTask(ActionEvent actionEvent) {
        int selectedItem = tblTasks.getSelectionModel().getSelectedIndex();

        Variables.get().getTasks().remove(selectedItem);
        updateTable();
    }

    @FXML
    @DoNotRename
    public void start(ActionEvent actionEvent) {
        this.getGUI().close();
    }

    @FXML
    @DoNotRename
    public void close(ActionEvent actionEvent) {
        Variables.get().getTasks().clear();
        this.getGUI().close();
    }

    @FXML
    @DoNotRename
    public void onMouseDragged(MouseEvent mouseEvent) {
        this.getGUI().getStage().setX(mouseEvent.getScreenX() + xOffset);
        this.getGUI().getStage().setY(mouseEvent.getScreenY() + yOffset);
    }

    @FXML
    @DoNotRename
    public void onMousePressed(MouseEvent mouseEvent) {
        xOffset = this.getGUI().getStage().getX() - mouseEvent.getScreenX();
        yOffset = this.getGUI().getStage().getY() - mouseEvent.getScreenY();
    }
}
