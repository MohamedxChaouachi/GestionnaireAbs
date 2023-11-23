package Login;

import java.sql.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import java.io.IOException;
import javafx.scene.Node;

public class Controller implements Initializable{
    //remove selector default
    @FXML
    private TextField username;
    @FXML
    private PasswordField pwd;

    // deselect fields
    @FXML
    private void deselectTextField(){
        username.deselect();
    }
    @FXML
    private void deselectPwdField(){
        pwd.deselect();
    }
    //Prompt

    //initiliztion -
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        deselectPwdField();
        deselectTextField();
        pwd.setFocusTraversable(false);
        username.setFocusTraversable(false);
    }
    
   

    @FXML
    private AnchorPane AnchorPane;
     private double x = 0,y = 0;
     @FXML
    private void AnchorPane_dragged(MouseEvent event){
        Stage stage=(Stage) AnchorPane.getScene().getWindow();
        stage.setY(event.getScreenY()-y);
        stage.setX(event.getScreenX()-x);

    }
    @FXML
    private void AnchorPane_pressed(MouseEvent event){
        x=event.getSceneX();
        y=event.getSceneY();
        deselectPwdField();
        deselectTextField();
    }

    @FXML
    private Text error;
    @FXML
    private Rectangle errorbox;
    
    //Getchoice
    

    //Login


    @FXML private Button etudiant;
    @FXML private Button enseignant;
    @FXML private Button responsable;
    @FXML private Button goback;
    @FXML private ImageView back;
    @FXML private Text qst;
    @FXML public String choice;

    public void gologin(ActionEvent e) throws IOException{
        if (e.getSource()==etudiant){choice="etudiant";}
        else if (e.getSource()==enseignant){choice="enseignant";}
        else if (e.getSource()==responsable){choice="responsable";}
        username.setVisible(true);username.setDisable(false);
        pwd.setVisible(true);pwd.setDisable(false);
        qst.setVisible(false);
        etudiant.setVisible(false);
        enseignant.setVisible(false);
        responsable.setVisible(false);
        goback.setVisible(true);goback.setDisable(false);
        back.setVisible(true);back.setDisable(false);

    }

    public void prelogin(ActionEvent e) throws IOException{
        username.setVisible(false);username.setDisable(true);
        pwd.setVisible(false);pwd.setDisable(true);
        qst.setVisible(true);
        etudiant.setVisible(true);
        enseignant.setVisible(true);
        responsable.setVisible(true);
        goback.setVisible(false);goback.setDisable(true);
        back.setVisible(false);back.setDisable(true);
        error.setVisible(false);errorbox.setVisible(false);

    }






    public void Login(ActionEvent e ){
        DataBaseConnection Login = new DataBaseConnection();
        Connection connectDB = Login.getConnection();
        String user = username.getText();
        String pass = pwd.getText();
        if (user.isEmpty() || pass.isEmpty()){error.setVisible(true);errorbox.setVisible(true);return;}
        String LoginQuery = "Select c.id_"+choice+",p.login,p.pwd from personne p , "+choice+" c where c.login=p.login and p.login='"+user+"' and p.pwd='"+pass+"'";
        String helloQuery ="SELECT nom, prenom from personne where login='"+user+"' and pwd='"+pass+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(LoginQuery);
            if (rs.next()){
                error.setVisible(false);errorbox.setVisible(false);
                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                ResultSet rs2 = statement.executeQuery(helloQuery);
                while(rs2.next()){
                String nom = rs2.getString("nom");
                String prenom = rs2.getString("prenom");
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                root= loader.load();
                Dashboardcontroller Dashboardcontroller= loader.getController();
                Dashboardcontroller.Hello(nom, prenom);
                Dashboardcontroller.identity(choice);
                }
                stage=(Stage)((Node)e.getSource()).getScene().getWindow();
                 scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{error.setVisible(true);errorbox.setVisible(true);}
           

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        
    }

   
   
    //Szitch back
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;
    //Close App
    public void Close(ActionEvent e){
        System.exit(0);
    }
}
