package com.ateam.onpoint.gui.components;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public class TaskList extends ListView<TaskList.Task> {
    public TaskList() {
        super();
    }

    public static class Task {
          private String description;
          private boolean isComplete;

          public Task() { }
    }

    public static class TaskCell extends ListCell<TaskList.Task> {
        private final HBox root;
        private final TextField descriptionField;
        private final CheckBox checkBox;
        private final ContextMenu contextMenu;

        public TaskCell() {
            super();

            this.root = new HBox();

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setAlignment(Pos.CENTER);

            this.checkBox = createCheckBox();
            this.descriptionField = createTextField();

            this.contextMenu = createContextMenu();

            this.root.setAlignment(Pos.CENTER_LEFT);
            this.root.prefHeight(15);
            this.root.prefWidth(450);

            this.root.setOnContextMenuRequested(e -> {
                this.contextMenu.show(this.root.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            });

            this.root.getChildren().addAll(this.checkBox, this.descriptionField);
        }
        private @NotNull ContextMenu createContextMenu() {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem changeDescriptionMenuItem = new MenuItem("Change Description");
            changeDescriptionMenuItem.setOnAction(e -> {
                this.descriptionField.setEditable(true);
                this.descriptionField.setMouseTransparent(false);
                this.descriptionField.requestFocus();
            });

            MenuItem deleteTaskMenuItem = new MenuItem("Delete");
            deleteTaskMenuItem.setOnAction(e -> {
                //TaskManager.getInstance().deleteTaskFromInbox(this.getItem());
                this.getListView().getItems().remove(this.getItem());
            });

            contextMenu.getItems().addAll(changeDescriptionMenuItem, deleteTaskMenuItem);
            return contextMenu;
        }

        private @NotNull CheckBox createCheckBox() {
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener(e -> {
                //TaskManager.getInstance().setTaskCompleted(this.getItem(), checkBox.isSelected());
            });
            return checkBox;
        }

        private @NotNull TextField createTextField() {
            TextField descriptionField = new TextField();

            descriptionField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    descriptionField.setEditable(false);
                    descriptionField.setMouseTransparent(true);

                    //TaskManager.getInstance().setTaskDescription(this.getItem(), descriptionField.getText());
                }
            });

            descriptionField.setOnMousePressed(e -> {
                this.getListView().getSelectionModel().select(this.getItem());
            });

            descriptionField.setOnMouseExited(e -> {
                //TaskManager.getInstance().setTaskDescription(this.getItem(), descriptionField.getText());
            });

            descriptionField.setStyle("-fx-font-weight: 600;");
            descriptionField.setPrefWidth(350);

            return descriptionField;
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (task == null || empty) {
                this.descriptionField.setText(null);
                this.setGraphic(null);
            } else {
                /*if (index >= 0 && index < TaskManager.getInstance().getInboxTaskList().size()) {
                    this.descriptionField.setText(TaskManager.getInstance().getTaskDescription(index));
                    this.checkBox.setSelected(TaskManager.getInstance().getTaskCompleted(index));
                }*/

                Platform.runLater(descriptionField::requestFocus);

                this.setGraphic(this.root);
            }
        }
    }
}
