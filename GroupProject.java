import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GroupProject extends Application {
    public static void main(String[] args) throws IOException{
        launch(args);
    }


    private SubScene shapeScene;

    public void start(Stage primaryStage) throws IOException{
        Button addShape = new Button("Add Shapes");
        Menu menu = new Menu("Files");


        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuLoad = new MenuItem("Load");
        MenuItem menuChangeColor = new MenuItem("Change Background Color");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(menuSave,menuLoad, new SeparatorMenuItem(), colorMenu());

        Group shapeGroup = new Group();
        shapeScene = new SubScene(shapeGroup,340,340);
        shapeScene.setFill(Color.AZURE);

        VBox shapeNode = new VBox(10);
        shapeNode.getChildren().addAll(shapeScene);

        BorderPane pane = new BorderPane();
        pane.setTop(menuBar);
        pane.setCenter(shapeNode);

        menuLoad.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                String filename = selectedFile.getPath();

            }
        });

        menuSave.setOnAction(event ->{
            //TODO
        });

        menuChangeColor.setOnAction(event -> {

        });



        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private Menu colorMenu(){
        Menu colorMenu = new Menu("Change BackGround Color");

        MenuItem red = new MenuItem("Red");
        MenuItem blue = new MenuItem("Blue");
        MenuItem green = new MenuItem("Green");
        MenuItem original = new MenuItem("Original");

        red.setOnAction(event -> {
            shapeScene.setFill(Color.RED);
        });

        blue.setOnAction(event -> {
            shapeScene.setFill(Color.BLUE);
        });

        green.setOnAction(event -> {
            shapeScene.setFill(Color.GREEN);
        });

        original.setOnAction(event -> {
            shapeScene.setFill(Color.AZURE);
        });

        colorMenu.getItems().addAll(original,red,blue,green);
        return colorMenu;
    }



}
