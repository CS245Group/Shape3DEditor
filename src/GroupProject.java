import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class GroupProject extends Application {
    public static void main(String[] args) throws IOException{
        launch(args);
    }

    private Label shapeLabel;
    private SubScene shapeScene;
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;
    private int radius = 0;
    private int depth = 0;
    private Group shapeGroup;
    private Stage window;
    private Scene addShapeScene;
    private VBox shapeVbox;

    public void start(Stage primaryStage) throws IOException{
        window = primaryStage;
        Button addShape = new Button("Add Shapes");
        Button submitButton = new Button("Submit");
        Menu menu = new Menu("Files");


        MenuItem menuSave = new MenuItem("Save");
        MenuItem menuLoad = new MenuItem("Load");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(menuSave,menuLoad, new SeparatorMenuItem(), colorMenu());

        shapeGroup = new Group();
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

                        String[] str = fileReader.nextLine().split(" ");

                        switch (str[0]){
                            case "Sphere":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                radius = Integer.parseInt(str[3]);
                                createSphere(x,y,radius);
                                break;

                            case "Cylinder":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                radius = Integer.parseInt(str[3]);
                                depth = Integer.parseInt(str[4]);
                                createCylinder(x,y,radius,depth);
                                break;

                            case "Box":
                                x = Integer.parseInt(str[1]);
                                y = Integer.parseInt(str[2]);
                                width = Integer.parseInt(str[3]);
                                height = Integer.parseInt(str[4]);
                                depth = Integer.parseInt(str[5]);
                                createBox(x,y,width,height,depth);
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

        });


        Scene scene = new Scene(pane, 800, 800);

        //  addShape scene
        BorderPane shapePane = new BorderPane();
        addShape.setOnAction(e -> window.setScene(addShapeScene));

        RadioButton sphereButton = new RadioButton("Sphere");
        RadioButton boxButton = new RadioButton("Box");
        RadioButton cylinderButton = new RadioButton("Cylinder");

        ToggleGroup shapeToggle = new ToggleGroup();
        sphereButton.setToggleGroup(shapeToggle);
        boxButton.setToggleGroup(shapeToggle);
        cylinderButton.setToggleGroup(shapeToggle);
        sphereButton.setSelected(true);

        Label shapeLabel = new Label("Select a Shape:");
        HBox shapeHbox = new HBox(20, sphereButton, boxButton, cylinderButton);
        shapeHbox.setAlignment(Pos.CENTER);

        //Sphere scene
        Text sphereRadiusText = new Text("Radius: ");
        Text sphereXText = new Text("X Translate: ");
        Text sphereYText = new Text("Y Translate: ");
        TextField sphereRadius = new TextField();
        TextField sphereTranslateX = new TextField();
        TextField sphereTranslateY = new TextField();

        //Box scene
        Text boxWidthText = new Text("Width: ");
        Text boxHeightText = new Text("Height: ");
        Text boxDepthText = new Text("Depth: ");
        Text boxXText = new Text("X Translate: ");
        Text boxYText = new Text("Y Translate: ");
        TextField boxWidth = new TextField();
        TextField boxHeight = new TextField();
        TextField boxDepth = new TextField();
        TextField boxTranslateX = new TextField();
        TextField boxTranslateY = new TextField();

        //Cylinder scene
        Text cylinderDepthText = new Text("Depth: ");
        Text cylinderRadiusText = new Text("Radius: ");
        Text cylinderXText = new Text("X Translate: ");
        Text cylinderYText = new Text("Y Translate: ");
        TextField cylinderDepth = new TextField();
        TextField cylinderRadius = new TextField();
        TextField cylinderTranslateX = new TextField();
        TextField cylinderTranslateY = new TextField();



        sphereButton.setOnAction(event ->{
            shapeVbox = new VBox(20, shapeLabel, shapeHbox, new HBox(10, sphereRadiusText, sphereRadius), new HBox(10, sphereXText, sphereTranslateX), new HBox(10, sphereYText, sphereTranslateY), submitButton);
            shapeVbox.setPadding(new Insets(50));
            shapeVbox.setAlignment(Pos.CENTER);
            shapePane.setTop(shapeVbox);
        });

        boxButton.setOnAction(event -> {
            shapeVbox = new VBox(20, shapeLabel, shapeHbox, new HBox(10, boxWidthText, boxWidth), new HBox(10, boxHeightText, boxHeight), new HBox(10, boxDepthText, boxDepth), new HBox(10, boxXText, boxTranslateX), new HBox(10, boxYText, boxTranslateY), submitButton);
            shapeVbox.setPadding(new Insets(50));
            shapeVbox.setAlignment(Pos.CENTER);
            shapePane.setTop(shapeVbox);
        });
        cylinderButton.setOnAction(event ->{
            shapeVbox = new VBox(20, shapeLabel, shapeHbox, new HBox(10, cylinderDepthText, cylinderDepth), new HBox(10, cylinderRadiusText, cylinderRadius), new HBox(10, cylinderXText, cylinderTranslateX), new HBox(10, cylinderYText, cylinderTranslateY), submitButton);
            shapeVbox.setPadding(new Insets(50));
            shapeVbox.setAlignment(Pos.CENTER);
            shapePane.setTop(shapeVbox);
        });
        shapeVbox = new VBox(20, shapeLabel, shapeHbox, new HBox(10, sphereRadiusText, sphereRadius), new HBox(10, sphereXText, sphereTranslateX), new HBox(10, sphereYText, sphereTranslateY), submitButton);
        shapeVbox.setPadding(new Insets(50));
        shapePane.setTop(shapeVbox);
        shapeVbox.setAlignment(Pos.CENTER);
        addShapeScene = new Scene(shapePane, 600, 600);
        submitButton.setOnAction(e ->{
            if(boxButton.isSelected())
                createBox(Integer.parseInt(boxTranslateX.getText()),Integer.parseInt(boxTranslateY.getText()),Integer.parseInt(boxWidth.getText()), Integer.parseInt(boxHeight.getText()),Integer.parseInt(boxDepth.getText()));
            if(sphereButton.isSelected())
                createSphere(Integer.parseInt(sphereTranslateX.getText()), Integer.parseInt(sphereTranslateY.getText()), Integer.parseInt(sphereRadius.getText()));
            if(cylinderButton.isSelected())
                createCylinder(Integer.parseInt(cylinderTranslateX.getText()),Integer.parseInt(cylinderTranslateY.getText()),Integer.parseInt(cylinderRadius.getText()),Integer.parseInt(cylinderDepth.getText()));

            window.setScene(scene);

        });


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


    public void createBox(int x, int y, int width, int height, int depth){
        Box box = new Box(width,height,depth);
        box.getTransforms().add(new Translate(x,y,0));

        box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        });
        shapeGroup.getChildren().add(box);
    }

    public void createSphere(int x, int y, int radius){
        Sphere sphere = new Sphere(radius);
        sphere.getTransforms().add(new Translate(x,y,0));
        sphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        });
        shapeGroup.getChildren().add(sphere);
    }

    public void createCylinder(int x, int y, int radius, int depth){
        Cylinder cylinder = new Cylinder(radius,depth);
        cylinder.getTransforms().add(new Translate(x,y,0));
        cylinder.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        });
        shapeGroup.getChildren().add(cylinder);
    }



}
