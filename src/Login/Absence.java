package Login;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Absence implements Observable {

    private SimpleStringProperty matiere;
    private SimpleStringProperty enseignant;
    private SimpleStringProperty date;
    private SimpleIntegerProperty num;


    private final List<InvalidationListener> listeners = new ArrayList<>();

    public Absence(){
        this.matiere = new SimpleStringProperty();
        this.enseignant = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.num = new SimpleIntegerProperty();
    }
// This is for subject ID
    public String getMatiere(){
        return matiere.get();
    }
    public void setMatiere(String id){
        this.matiere.set(id);
    }


    public SimpleStringProperty getMatiereID(){
        return matiere;
    }
// This is for person name

    public String getEnseignant(){
        return enseignant.get();
    }
    public void setEnseignant(String nom){
        this.enseignant.set(nom);
    }


    public SimpleStringProperty getEnseignantNom(){
        return enseignant;
    }

// This is for date

public String getDate(){
    return date.get();
}
public void setDate(String date){
    this.date.set(date);
}


public SimpleStringProperty getDateAbs(){
    return date;
}
// Thisis for Button abs
public int getNum(){
    return num.get();
}
public void setNum(int num){
    this.num.set(num);
}

public SimpleIntegerProperty getNumS(){
    return num;
}


@Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

}
