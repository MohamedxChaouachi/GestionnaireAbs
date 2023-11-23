package Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
        
    
    @Override
    public void start(Stage primaryStage) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        String css= this.getClass().getResource("Login.css").toExternalForm();
        scene.getStylesheets().add(css);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Gestion des absences des étudiants");
        primaryStage.setScene(scene);
        primaryStage.show();
    }catch(Exception e){e.printStackTrace();}
    }

    public static void main(String[] args) {
        launch(args);
    }
}