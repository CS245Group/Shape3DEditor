import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GroupProject extends Application {
    public static void main(String[] args) throws IOException{
        launch(args);
    }


    private Label shapeLabel;
    private SubScene shapeScene;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double width = 0;
    private double height = 0;
    private double radius = 0;
    private double depth = 0;
    private Group shapeGroup;
    private Stage window;
    private Scene addShapeScene;
    private VBox shapeVbox;
    private File savedFile = new File("");

    private Slider XSliderRotate;
    private Slider YSliderRotate;
    private Slider ZSliderRotate;

    private Rotate X_Rotate;
    private Rotate Y_Rotate;
    private Rotate Z_Rotate;

    private Shape3D selectedShape;

    private TextField XtranslateField;
    private TextField YtranslateField;
    private TextField ZtranslateField;
    private TextField XscaleField;
    private TextField YscaleField;
    private TextField ZscaleField;
    private TextField colorField;

    private ChoiceBox<String> colorChoice;

    private Scale scale;

    public void start(Stage primaryStage) throws IOException{

        window = primaryStage;
        Button addShape = new Button("Add Shapes");
        Button submitButton = new Button("Submit");
        Menu menu = new Menu("Files");


        MenuItem menuSave = new MenuItem("Save As");
        MenuItem save = new MenuItem("Save");
        MenuItem menuLoad = new MenuItem("Load");
        MenuItem menuChangeColor = new MenuItem("Change Background Color");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(menuSave, save, menuLoad, new SeparatorMenuItem(), colorMenu());

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

        Label Xrotate = new Label("X Rotate");
        XSliderRotate = new Slider(0.0,360,0);
        XSliderRotate.setShowTickLabels(true);
        XSliderRotate.setShowTickMarks(true);

        Label Yrotate = new Label("Y Rotate");
        YSliderRotate = new Slider(0.0,360,0);
        YSliderRotate.setShowTickLabels(true);
        YSliderRotate.setShowTickMarks(true);

        Label Zrotate = new Label("Z Rotate");
        ZSliderRotate = new Slider(0.0,360,0);
        ZSliderRotate.setShowTickLabels(true);
        ZSliderRotate.setShowTickMarks(true);

        VBox rotateVbox = new VBox(5,Xrotate,XSliderRotate,Yrotate,YSliderRotate,Zrotate,ZSliderRotate);

        Label Xtranslate = new Label("X Translate");
        XtranslateField = new TextField("");
        HBox XtranslateBox = new HBox(10,Xtranslate, XtranslateField);


        Label Ytranslate = new Label("Y Translate");
        YtranslateField = new TextField("");
        HBox YtranslateBox = new HBox(10,Ytranslate,YtranslateField);


        Label Ztranslate = new Label("Z Translate");
        ZtranslateField = new TextField("");
        HBox ZtranslateBox = new HBox(10,Ztranslate,ZtranslateField);
        VBox translateVbox = new VBox(10,XtranslateBox,YtranslateBox,ZtranslateBox);

        Label Xscale = new Label("X Scale");
        XscaleField = new TextField("1");
        HBox XscaleBox = new HBox(10,Xscale,XscaleField);

        Label Yscale = new Label("Y Scale");
        YscaleField = new TextField("1");
        HBox YscaleBox = new HBox(10,Yscale,YscaleField);

        Label Zscale = new Label("Z Scale");
        ZscaleField = new TextField("1");
        HBox ZscaleBox = new HBox(10,Zscale,ZscaleField);

        VBox scaleVbox = new VBox(10,XscaleBox,YscaleBox,ZscaleBox);


        Label colorLabel = new Label("Colors");
        colorChoice = getColorChoices();
        HBox colorHbox = new HBox(5,colorLabel,colorChoice);


        VBox toolBox = new VBox(10,rotateVbox,translateVbox,scaleVbox,colorHbox);

        pane.setCenter(toolBox);

        pane.setBottom(buttonVbox);

        menuLoad.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("txt", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            savedFile = selectedFile;
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
                                x = Double.parseDouble(str[1]);
                                y = Double.parseDouble(str[2]);
                                z = Double.parseDouble(str[3]);
                                radius = Double.parseDouble(str[4]);
                                createSphere(x,y,radius);
                                break;

                            case "Cylinder":
                                x = Double.parseDouble(str[1]);
                                y = Double.parseDouble(str[2]);
                                z = Double.parseDouble(str[3]);
                                radius = Double.parseDouble(str[4]);
                                depth = Double.parseDouble(str[5]);
                                createCylinder(x,y,radius,depth);
                                break;

                            case "Box":
                                x = Double.parseDouble(str[1]);
                                y = Double.parseDouble(str[2]);
                                z = Double.parseDouble(str[3]);
                                width = Double.parseDouble(str[4]);
                                height = Double.parseDouble(str[5]);
                                depth = Double.parseDouble(str[6]);
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

        //Calls save file method
        save.setOnAction(event ->{
            try
            {
                saveFile(shapeGroup);
            }
            catch (IOException e1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: File not found");
                alert.show();
            }
        });
        menuSave.setOnAction(event ->{
            try
            {
                buildNewFile(primaryStage);
            }
            catch (IOException e1)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: File not found");
                alert.show();
            }
        });

        menuChangeColor.setOnAction(event -> {

        });



        Scene scene = new Scene(pane, 800, 800);

        //addShape scene
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
        TextField sphereTranslateX = new TextField("");
        TextField sphereTranslateY = new TextField("");

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

    //Saves a new file
    private void buildNewFile(Stage primaryStage) throws IOException
    {
        FileChooser save = new FileChooser();
        save.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
        savedFile = save.showSaveDialog(primaryStage);
        try
        {
            PrintWriter pw = new PrintWriter(savedFile);
            pw.close();
        }
        catch(FileNotFoundException e)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error: File cannot be created");
            error.show();
        }
        saveFile(shapeGroup);

    }

    //Updates save file
    private void saveFile(Group shapes) throws IOException
    {
        ObservableList<Node> list = shapes.getChildren();
        Shape3D testShape;
        double x = 0;
        double y = 0;
        double z = 0;
        double radius = 0;
        double depth = 0;
        double height = 0;
        double width = 0;
        FileWriter fileWriter = new FileWriter(savedFile.getPath(), true);
        PrintWriter outputFile = new PrintWriter(fileWriter);

        for(int i = 0; i < list.size(); i++)
        {
            testShape = (Shape3D) list.get(i);

            if(testShape instanceof Cylinder)
            {
                x = testShape.getTranslateX();
                y = testShape.getTranslateY();
                z = testShape.getTranslateZ();
                radius = ((Cylinder) testShape).getRadius();
                height = ((Cylinder) testShape).getHeight();
                try
                {
                    outputFile.println("Cylinder " + x + " " + y + " " + z + " " + radius + " " + height);

                }
                catch(Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Could not save file");
                    alert.show();
                }
            }
            else if(testShape instanceof Box)
            {
                x = testShape.getTranslateX();
                y = testShape.getTranslateY();
                z = testShape.getTranslateZ();
                width = ((Box) testShape).getWidth();
                height = ((Box) testShape).getHeight();
                depth = ((Box) testShape).getDepth();
                try
                {
                    outputFile.println("Box " + x + " " + y + " " + z + " " + width + " " + height + " " + depth);
                }
                catch(Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Could not save file");
                    alert.show();
                }
            }
            else if(testShape instanceof Sphere)
            {
                x = testShape.getTranslateX();
                y = testShape.getTranslateY();
                z = testShape.getTranslateZ();
                radius = ((Sphere) testShape).getRadius();
                try
                {
                    outputFile.println("Sphere " + x + " " + y + " " + z + " " + radius);
                }
                catch(Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Could not save file");
                    alert.show();
                }
            }

        }
        outputFile.close();

    }

    public void createBox(double x, double y, double width, double height, double depth){
        Box box = new Box(width,height,depth);
        box.getTransforms().add(new Translate(x,y,0));

        box.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {

                if (XtranslateField.getText() != "")
                    box.setTranslateX(Double.parseDouble(XtranslateField.getText()));
                if (YtranslateField.getText() != "")
                    box.setTranslateY(Double.parseDouble(YtranslateField.getText()));
                if (ZtranslateField.getText() != "")
                    box.setTranslateZ(Double.parseDouble(ZtranslateField.getText()));

            }
            catch (NumberFormatException e){

            }
            try {
                scale = new Scale(Double.parseDouble(XscaleField.getText()), Double.parseDouble(YscaleField.getText()), Double.parseDouble(ZscaleField.getText()));
                box.getTransforms().addAll(scale);
            }
            catch (NumberFormatException e){

            }
            if(colorChoice.getSelectionModel().getSelectedItem() == "BLUE")
                box.setMaterial(new PhongMaterial(Color.BLUE));
            if(colorChoice.getSelectionModel().getSelectedItem() == "GREEN")
                box.setMaterial(new PhongMaterial(Color.GREEN));
            if(colorChoice.getSelectionModel().getSelectedItem() == "RED")
                box.setMaterial(new PhongMaterial(Color.RED));
        });
        shapeGroup.getChildren().add(box);
    }

    public void createSphere(double x, double y, double radius){
        Sphere sphere = new Sphere(radius);
        sphere.getTransforms().add(new Translate(x,y,0));
        sphere.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if (XtranslateField.getText() != "")
                    sphere.setTranslateX(Double.parseDouble(XtranslateField.getText()));
                if (YtranslateField.getText() != "")
                    sphere.setTranslateY(Double.parseDouble(YtranslateField.getText()));
                if (ZtranslateField.getText() != "")
                    sphere.setTranslateZ(Double.parseDouble(ZtranslateField.getText()));
            }
            catch (NumberFormatException e){}

            try {
                scale = new Scale(Double.parseDouble(XscaleField.getText()), Double.parseDouble(YscaleField.getText()), Double.parseDouble(ZscaleField.getText()));
                sphere.getTransforms().addAll(scale);
            }
            catch (NumberFormatException e){}


            if(colorChoice.getSelectionModel().getSelectedItem() == "BLUE")
                sphere.setMaterial(new PhongMaterial(Color.BLUE));
            if(colorChoice.getSelectionModel().getSelectedItem() == "GREEN")
                sphere.setMaterial(new PhongMaterial(Color.GREEN));
            if(colorChoice.getSelectionModel().getSelectedItem() == "RED")
                sphere.setMaterial(new PhongMaterial(Color.RED));
        });
        shapeGroup.getChildren().add(sphere);
    }

    public void createCylinder(double x, double y, double radius, double depth){
        Cylinder cylinder = new Cylinder(radius,depth);
        cylinder.getTransforms().add(new Translate(x,y,0));
        cylinder.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                if (XtranslateField.getText() != "")
                    cylinder.setTranslateX(Double.parseDouble(XtranslateField.getText()));
                if (YtranslateField.getText() != "")
                    cylinder.setTranslateY(Double.parseDouble(YtranslateField.getText()));
                if (ZtranslateField.getText() != "")
                    cylinder.setTranslateZ(Double.parseDouble(ZtranslateField.getText()));
            }
            catch (NumberFormatException e){}

            try {
                scale = new Scale(Double.parseDouble(XscaleField.getText()), Double.parseDouble(YscaleField.getText()), Double.parseDouble(ZscaleField.getText()));
                cylinder.getTransforms().addAll(scale);
            }
            catch (NumberFormatException e){}

            if(colorChoice.getSelectionModel().getSelectedItem() == "BLUE")
                cylinder.setMaterial(new PhongMaterial(Color.BLUE));
            if(colorChoice.getSelectionModel().getSelectedItem() == "GREEN")
                cylinder.setMaterial(new PhongMaterial(Color.GREEN));
            if(colorChoice.getSelectionModel().getSelectedItem() == "RED")
                cylinder.setMaterial(new PhongMaterial(Color.RED));
        });
        shapeGroup.getChildren().add(cylinder);
    }

    ChoiceBox<String> getColorChoices()
    {
        ChoiceBox<String> colorChoice = new ChoiceBox<>();
        colorChoice.getItems().addAll("RED", "BLUE", "GREEN");
        return colorChoice;
    }



}
