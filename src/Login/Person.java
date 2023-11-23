package Login;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Person implements Observable {

    private SimpleIntegerProperty Id;
    private SimpleStringProperty nom;
    private SimpleStringProperty Prenom;
    private Button button;

    private final List<InvalidationListener> listeners = new ArrayList<>();

    public Person(){
        this.Id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.Prenom = new SimpleStringProperty();
        this.button = new Button("abs");
    }
// This is for person ID
    public int getId(){
        return Id.get();
    }
    public void setPersonId(int id){
        this.Id.set(id);
    }


    public SimpleIntegerProperty getPersonId(){
        return Id;
    }
// This is for person name

    public String getNom(){
        return nom.get();
    }
    public void setNom(String nom){
        this.nom.set(nom);
    }


    public SimpleStringProperty getPersonNom(){
        return nom;
    }

// This is for person Last name

public String getPrenom(){
    return Prenom.get();
}
public void setPrenom(String Prenom){
    this.Prenom.set(Prenom);
}


public SimpleStringProperty getPersonPrenom(){
    return Prenom;
}
/*// Thisis for Button abs
public void setButton(Button button){
    this.button= button;
}
public Button getButton(){
    return button;
}*/

@Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

}
