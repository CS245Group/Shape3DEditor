import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    Shape3D selectedShape;
    Rotate boxRotateX = new Rotate(0, Rotate.X_AXIS);
    Rotate boxRotateY = new Rotate(0, Rotate.Y_AXIS);
    Rotate cylinderRotateX = new Rotate(0, Rotate.X_AXIS);
    Rotate cylinderRotateY = new Rotate(0, Rotate.Y_AXIS);

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        Box box = new Box(30, 50, 30);
        box.setLayoutY(50);
        box.setLayoutX(100);
        selectedShape = box;
        box.setOnMouseClicked(event -> {
            selectedShape = box;
        });
        box.getTransforms().addAll(boxRotateX, boxRotateY);

        Cylinder cylinder = new Cylinder(20, 50, 10);
        cylinder.setLayoutX(100);
        cylinder.setLayoutY(120);
        cylinder.setOnMouseClicked(event -> {
            selectedShape = cylinder;
        });

        cylinder.getTransforms().addAll(cylinderRotateX, cylinderRotateY);

        Pane pane = new Pane(box, cylinder);
        SubScene subScene = new SubScene(pane, 200, 200);

        Label hLabel = new Label("Rotate Horizontally");
        Slider horizontalSlider = new Slider();
        horizontalSlider.setShowTickMarks(true);
        horizontalSlider.setMin(0);
        horizontalSlider.setMax(360);

        horizontalSlider.valueProperty().addListener((o, oldVal, newVal) ->
        {
            if(selectedShape.equals(box))
            {
                boxRotateX.setAngle((double)newVal);
            }
            else
            {
                cylinderRotateX.setAngle((double)newVal);
            }
        });

        Label vLabel = new Label("Rotate Vertically");
        Slider verticalSlider = new Slider();
        verticalSlider.setShowTickMarks(true);
        verticalSlider.setMin(0);
        verticalSlider.setMax(360);

        verticalSlider.valueProperty().addListener((o, oldVal, newVal) ->
        {
            if(selectedShape.equals(box))
            {
                boxRotateY.setAngle((double)newVal);
            }
            else
            {
                cylinderRotateY.setAngle((double)newVal);
            }
        });

        VBox sliderVBox = new VBox(10, hLabel, horizontalSlider, vLabel, verticalSlider);
        sliderVBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox(10, subScene, sliderVBox);
        vBox.setPadding(new Insets(25));

        Scene myScene = new Scene(vBox);
        primaryStage.setScene(myScene);
        primaryStage.show();
        }
    }

