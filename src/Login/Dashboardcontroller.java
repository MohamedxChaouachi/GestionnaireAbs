package Login;
import java.sql.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.mail.Transport;
public class Dashboardcontroller implements Initializable{
    @FXML private TableColumn<Person, Integer> id;
    @FXML private TableColumn<Person, String> nom;
    @FXML private TableColumn<Person, String> Prenom;

    @FXML private TableColumn<Absence, Integer> Matiere;
    @FXML private TableColumn<Absence, String> Enseignant;
    @FXML private TableColumn<Absence, String> Date;
    @FXML private TableColumn<Absence, Integer> Num;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Consult();
    

    }
    @FXML private Tab tab21;
    public void identity(String choice){
        if( choice=="etudiant" || choice=="enseignant" ){tab2.setDisable(true);print.setDisable(true);}
        if (choice=="etudiant"){tab21.setDisable(true);}
        if (choice=="responsable"){tab21.setDisable(true);}
    }



    @FXML public ComboBox<String> comboBox;
    public void Consult(){
        try{
            DataBaseConnection Info = new DataBaseConnection();
            Connection connectDB = Info.getConnection();
            Statement statement = connectDB.createStatement();
            String sql = "SELECT libelle FROM classe";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String data = resultSet.getString("libelle");
                ObservableList<String> items = comboBox.getItems();
                items.add(data);
            }
        } catch (Exception ee) {
        ee.printStackTrace();
        }
    }
    @FXML public TableView<Person> etu;
    @FXML public TableView<Absence> abs;
    //Imprimer
    @FXML private ImageView print;
    public void Print(){
        PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null && job.showPrintDialog(abs.getScene().getWindow())) {
        boolean success = job.printPage(abs);
        if (success) {
            job.endJob();
        }
    }
    }
    //Chart
    @FXML private ImageView chart;
    public void Chart(){
       
    }



    // Annuler absence 
    @FXML private TextField idetu;
    @FXML private TextField idmat;
    @FXML private TextField numsea;
    @FXML private DatePicker dateabs;
    @FXML private Text error;
    @FXML private Rectangle errorbox;
    public void Annuler(){
        try {
            DataBaseConnection Info = new DataBaseConnection();
            Connection connectDB = Info.getConnection();
            Statement statement = connectDB.createStatement();
            String id = idetu.getText();
            String idm = idmat.getText();
            String num = numsea.getText();
            LocalDate date = dateabs.getValue();
            if (id.isEmpty() || idm.isEmpty()|| num.isEmpty()|| date==null){error.setVisible(true);errorbox.setVisible(true);return;}
            String dateS = date.toString();
            String sql = "delete from absence where id_etudiant="+id+" and id_matiere="+idm+" and numseance="+num+" and datea='"+dateS+"'";
            int rs = statement.executeUpdate(sql);
            if (rs>0){error.setText("Annulation de l absence est terminé avec succes");error.setVisible(true);errorbox.setVisible(true);return;}else{error.setText("Annulation de l absence a echoué");error.setVisible(true);errorbox.setVisible(true);return;}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Ajour abs
    @FXML private TextField idens;
    @FXML private TextField idetu1;
    @FXML private TextField idmat1;
    @FXML private TextField numsea1;
    @FXML private DatePicker dateabs1;
    @FXML private Text error1;
    @FXML private Rectangle errorbox1;
    public void Ajout(){
        try {
            DataBaseConnection Info = new DataBaseConnection();
            Connection connectDB = Info.getConnection();
            Statement statement = connectDB.createStatement();
            String id = idetu1.getText();
            String idm = idmat1.getText();
            String num = numsea1.getText();
            String ide = idens.getText();
            LocalDate date = dateabs1.getValue();
            if (id.isEmpty() || idm.isEmpty()|| num.isEmpty()|| date==null){error.setVisible(true);errorbox.setVisible(true);return;}
            String dateS = date.toString();
            String sql = "Insert into absence values ("+id+","+ide+","+idm+","+num+",'"+dateS+"')";
            int rs = statement.executeUpdate(sql);
            if (rs>0){
                error1.setText("Ajout de l absence est terminé avec succes");
                error1.setVisible(true);errorbox1.setVisible(true);
                
                String sql2 = "select p.email from personne p, etudiant e where p.login = e.login and id_etudiant="+id;
                ResultSet rs2 = statement.executeQuery(sql2);
                while(rs2.next()){
                    String mail= rs2.getString("email");
                    String from="";
                    String emailpass="";
                    String Sujet="Absence le "+dateS;
                    
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");
                    
                    Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(from, emailpass);
                        }
                    });
                    try {
                        MimeMessage message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(from));
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                        message.setSubject(Sujet);
                        message.setText("Votre absence le "+dateS+" a ete enregistrer.. Pour annuler l absence il faut contacter l administration et presenter une justification valide ");
                        Transport.send(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;}
                else
                {error1.setText("Ajout de l absence a echoué");error1.setVisible(true);errorbox1.setVisible(true);return;}



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Logout
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;
    public void Logout(ActionEvent e){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            FXMLLoader loader= new FXMLLoader(getClass().getResource("Login.fxml"));
                root= loader.load();
                stage=(Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                String css= this.getClass().getResource("Login.css").toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
        } catch (Exception ee) {
            // TODO: handle exception
        }
    }
    //Load etudiant
    public void Getstudent(){
        try {
            DataBaseConnection Info = new DataBaseConnection();
            Connection connectDB = Info.getConnection();
            Statement statement = connectDB.createStatement();
            String selectedItem = comboBox.getSelectionModel().getSelectedItem();
            String sql = "select e.id_etudiant,p.nom,p.prenom from personne p, etudiant e, classe c where p.login=e.login and c.id_class=e.id_class and c.libelle='"+selectedItem+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            etu.getItems().clear();
            ObservableList<Person> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Person person = new Person();
                person.setNom(resultSet.getString("nom"));
                person.setPrenom(resultSet.getString("prenom"));
                person.setPersonId(resultSet.getInt("id_etudiant"));
                data.add(person);
            }
            id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
            Prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
            etu.setItems(data);

            
            etu.setRowFactory(tv -> {
                TableRow<Person> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Person rowData = row.getItem();
                        int idValue = id.getCellData(row.getIndex());
                        String nomValue = nom.getCellData(row.getIndex());
                        String prenomValue = Prenom.getCellData(row.getIndex());
                        try{
                            String sql2 = "Select m.libelle,p.nom, p.prenom,a.datea,a.numseance from matiere m ,personne p , absence a , enseignant e ,etudiant et where p.login=e.login and m.id_matiere=a.id_matiere and e.id_enseignant= a.id_enseignant and et.id_etudiant=a.id_etudiant and a.id_etudiant="+idValue;
                            Statement stt = connectDB.createStatement();
                            ResultSet rsa = stt.executeQuery(sql2);
                            ObservableList<Absence> data2 = FXCollections.observableArrayList();
                            while (rsa.next()) {
                                String ense=rsa.getString("prenom")+" "+rsa.getString("nom");
                                Absence absence = new Absence();
                                absence.setMatiere((rsa.getString("libelle")));
                                absence.setEnseignant(ense);
                                absence.setDate(rsa.getString("datea"));
                                absence.setNum(rsa.getInt("numseance"));
                                data2.add(absence);
                            }
                            Matiere.setCellValueFactory(new PropertyValueFactory<>("Matiere"));
                            Enseignant.setCellValueFactory(new PropertyValueFactory<>("Enseignant"));
                            Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
                            Num.setCellValueFactory(new PropertyValueFactory<>("Num"));

                            abs.setItems(data2);
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
                return row;
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML private Tab tab2;
    
    
    @FXML private Label label;
    public void Hello(String nom , String prenom){
        String labelname = "Bienvenue\n"+nom+"\n"+prenom+" !";
        label.setText(labelname);
    }
    

    //Close App

    public void Close(ActionEvent e){
        System.exit(0);
    }
    //dràg
    @FXML
    private BorderPane BorderPane;
     private double x = 0,y = 0;
     @FXML
    private void BorderPane_dragged(MouseEvent event){
        Stage stage=(Stage) BorderPane.getScene().getWindow();
        stage.setY(event.getScreenY()-y);
        stage.setX(event.getScreenX()-x);

    }
    @FXML
    private void BorderPane_pressed(MouseEvent event){
        x=event.getSceneX();
        y=event.getSceneY();
      
    }
}
