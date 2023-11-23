package Login;
import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databseName = "gestionabsence";
        String databaseUser = "root";
        String databasePassword ="AssFace1591";
        String url ="jdbc:mysql://localhost/" + databseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        
        }catch(Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
