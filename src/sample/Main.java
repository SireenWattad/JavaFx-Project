package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.util.*;

public class Main extends Application {

    private final ToggleGroup group = new ToggleGroup();
    private File to;
    private int counter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("javaFx");

        // Layout:
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.setPrefSize(400, 350);
        layout.setHgap(5);
        //layout.setGridLinesVisible(true);

        //TextField - Location
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setMaxSize(300, 100);
        layout.add(textArea, 1, 5, 4, 3);

        // textField Style
        textArea.getStyleClass().add("text-field-font");

        // number of rows and columns
        final int numCols = 6;
        final int numRows = 10;
        for (int i = 0; i < numCols; i++) {
            layout.getColumnConstraints().add(new ColumnConstraints(58.33));
        }
        for (int i = 0; i < numRows; i++) {
            layout.getRowConstraints().add(new RowConstraints(40));
        }

        // PROGRESS BAR
        ProgressBar progressBar = new ProgressBar(0);
        layout.add(progressBar, 1, 9, 3, 1);
        progressBar.setPrefSize(150, 20);
        progressBar.setVisible(false);

        // Button: Run & Browse & choose & CANCEL
        Button run = new Button("Run");
        Button choose = new Button("Choose a file");
        Button browse = new Button("Browse");
        Button cancel = new Button("Cancel");
        run.setMaxWidth(75);
        choose.setMaxWidth(250);
        layout.add(run, 1, 8, 1, 1);
        layout.add(choose, 2, 8, 2, 1);
        layout.add(browse, 4, 8, 2, 1);
        layout.add(cancel, 4, 9, 1, 1);
        cancel.setVisible(false);

        // ArrayList Files
        ArrayList<File> files = new ArrayList<>();

        // file chooser:
        FileChooser fileChooser = new FileChooser();
        choose.setOnAction(event -> {
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (!files.contains(file)) {
                files.add(file);
                textArea.appendText(file.getAbsolutePath() + "\n");
            }
        });

        // run Button On Action
        run.setOnAction(event -> {
            //Progress Bar:
            progressBar.setVisible(true);
            progressBar.setProgress(0);
            run.setDisable(true);
            progressBar.progressProperty().unbind();
            files.stream().forEach((i) -> {
                if (i != null) {
                    System.out.println(i.toString());
                }
            });
            counter = 0;
//          Task - ProgressBar
            for (File i : files) {
                String fileName = i.getPath().substring(i.getPath().lastIndexOf("\\", i.toString().length()));
                i.renameTo(new File(to.getPath() + "\\" + fileName));
                counter++;
                progressBar.setProgress(counter / files.size());
            }

            if (progressBar.getProgress() == 1.0) {
                progressBar.setStyle("-fx-accent: forestgreen;");
                cancel.setVisible(false);
                Label label = new Label("Done!!");
                layout.add(label, 4, 9, 1, 1);
            }

            // CANCEL
            if (progressBar.getProgress() < 1.0) {
                cancel.setVisible(true);
                cancel.setOnAction(event1 -> {
                    run.setDisable(false);
                    progressBar.progressProperty().unbind();
                    progressBar.setProgress(0);
                    progressBar.setVisible(false);
                    cancel.setVisible(false);
                    to = new File("");
                    textArea.setText("");
                });
            }
        });

        // Directory Chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));

        browse.setOnAction(event -> {
            File selectedDirectory = directoryChooser.showDialog(primaryStage);
            System.out.println(selectedDirectory.getAbsolutePath());
            to = selectedDirectory;
        });

        // Labels
        Label labelMain = new Label("בדק מתחכם DRoPShip");
        Label label = new Label("בדיקת מתחכם למוצר עד");
        label.setMaxWidth(200);
        labelMain.setMinHeight(200);
        label.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        labelMain.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // set labels location
        layout.add(labelMain, 2, 0, 4, 2);
        layout.add(label, 2, 4, 3, 1);

        // Label Style:
        labelMain.getStyleClass().add("label-bold");
        label.getStyleClass().add("label-font");

        //radioButton:
        RadioButton radioButton1 = new RadioButton("בדיקת כותרת מלאה");
        RadioButton radioButton2 = new RadioButton("בדיקת כותרת חלקית ומלאה");
        radioButton1.setMaxWidth(200);
        radioButton2.setMaxWidth(200);
        radioButton1.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        radioButton2.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // radioButtons Group
        radioButton1.setToggleGroup(group);
        radioButton1.setSelected(true);
        radioButton2.setToggleGroup(group);

        // VBox and RadioButtons location
        VBox vBox = new VBox(3);
        vBox.getChildren().addAll(radioButton1, radioButton2);
        layout.add(vBox, 2, 2, 3, 1);

        // Spinner - Location
        Spinner<Integer> spinner = new Spinner<>(0, 100, 0, 1);
        spinner.setMaxSize(70, 20);
        layout.add(spinner, 1, 4, 1, 1);

        // Scene:
        Scene scene = new Scene(layout);
        layout.setAlignment(Pos.CENTER);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
