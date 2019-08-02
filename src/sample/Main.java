package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class Main extends Application {

    private final ToggleGroup group = new ToggleGroup();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("javaFx");

        // Layout:
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(15,15,15,15));
        layout.setPrefSize(400,350);
        layout.setHgap(5);
        //layout.setGridLinesVisible(true);

        // number of rows and columns
        final int numCols = 6 ;
        final int numRows = 10 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            layout.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            layout.getRowConstraints().add(rowConst);
        }

        // Button: Run
        Button run = new Button("Run");
        Button choose = new Button("Choose a file");
        run.setMaxWidth(75);
        choose.setMaxWidth(250);
        layout.add(run,1,8,1,1);
        layout.add(choose,2,8,2,1);

        ArrayList<File> flise=new ArrayList<>();
        // file chooser:
        FileChooser fileChooser = new FileChooser();
        choose.setOnAction(event -> {
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(primaryStage);
            flise.add(file);

        });
        run.setOnAction(event -> {
            flise.stream().forEach((i)->{if (i != null) {
                System.out.println(i.toString());
            }});

        });

        // Labels
        Label labelMain = new Label("בדק מתחכם DRoPShip");
        Label label = new Label("בדיקת מתחכם למוצר עד");
        label.setMaxWidth(200);
        labelMain.setMinHeight(200);
        label.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        labelMain.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // set labels location
        layout.add(labelMain,2,0,4,2);
        layout.add(label,2,4,3,1);

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
        vBox.getChildren().addAll(radioButton1,radioButton2);
        layout.add(vBox,2,2,3,1);

        // Spinner - Location
        Spinner<Integer> spinner = new Spinner<>(0,100,0,1);
        spinner.setMaxSize(70,20);
        layout.add(spinner,0,4,1,1);

        //TextField - Location
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setMaxSize(300,100);
        layout.add(textField,1,5,4,3);

        // textField Style
        //textField.getStyleClass().add("text-field-font");

        // Scene:
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}


