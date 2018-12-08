import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GroupProject extends Application {
    public static void main(String[] args) throws IOException{
        launch(args);
    }


    private SubScene shapeScene;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int radius = 0;

    public void start(Stage primaryStage) throws IOException{
        Button addShape = new Button("Add Shapes");
        Menu menu = new Menu("Files");


        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuLoad = new MenuItem("Load");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(menuSave,menuLoad, new SeparatorMenuItem(), colorMenu());

        Group shapeGroup = new Group();
        shapeScene = new SubScene(shapeGroup,450,530);
        shapeScene.setFill(Color.AZURE);

        VBox shapeNode = new VBox(10);
        shapeNode.getChildren().addAll(shapeScene);

        BorderPane pane = new BorderPane();
        pane.setTop(menuBar);
        pane.setCenter(shapeNode);
        pane.setLeft(shapeScene);
        HBox buttonVbox = new HBox(10, addShape);
        buttonVbox.setPadding(new Insets(50));
        buttonVbox.setAlignment(Pos.CENTER);
        buttonVbox.setId("buttonVbox");

        pane.setBottom(buttonVbox);


        //This lambda expression loads in a .txt file
        menuLoad.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("txt", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    //Opening the txt file to read.
                    String filename = selectedFile.getPath();

                    File file = new File(filename);
                    Scanner fileReader = new Scanner(file);

                    //Reads in one line at a time
                    while(fileReader.hasNextLine()){

                        String[] str = fileReader.nextLine().split(" ", 10000);

                        switch (str[0]){
                            case "Sphere":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                radius = Integer.parseInt(str[3]);
                                break;

                            case "Square":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                width = Integer.parseInt(str[3]);
                                height = Integer.parseInt(str[4]);
                                break;

                            case "Box":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                width = Integer.parseInt(str[3]);
                                height = Integer.parseInt(str[4]);
                                break;
                        }
                    }
                }//catching IOException NOTE Have to do this for exceptions in Lambda expressions
                catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        });

        menuSave.setOnAction(event ->{
            //TODO
        });


        Scene scene = new Scene(pane, 800, 800);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Menu colorMenu(){
        Menu colorMenu = new Menu("Change BackGround Color");

        //add the menu items to a submenu
        //Note change variable name if changing color
        MenuItem red = new MenuItem("Red");
        MenuItem blue = new MenuItem("Blue");
        MenuItem green = new MenuItem("Green");
        MenuItem original = new MenuItem("Original");

        //Use "Color.web()" to use hex colors
        red.setOnAction(event -> {
            shapeScene.setFill(Color.web("FED6BB"));
        });

        blue.setOnAction(event -> {
            shapeScene.setFill(Color.web("84E5E5"));
        });

        green.setOnAction(event -> {
            shapeScene.setFill(Color.web("BBFEC2"));
        });

        original.setOnAction(event -> {
            shapeScene.setFill(Color.AZURE);
        });

        colorMenu.getItems().addAll(original,red,blue,green);
        return colorMenu;
    }



}
