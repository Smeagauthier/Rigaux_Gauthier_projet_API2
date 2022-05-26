package courses.gestion.modele;

import courses.metier.Ville;
import myconnections.DBConnection;

import java.sql.Connection;
import java.util.List;

public class ModeleVilleDB implements DAOVille {

    protected Connection dbConnect;

    public ModeleVilleDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Ville create(Ville newObj) {
        return null;
    }

    @Override
    public boolean delete(Ville objRech) {
        return false;
    }

    @Override
    public Ville read(Ville objRech) {
        return null;
    }

    @Override
    public Ville update(Ville objRech) {
        return null;
    }

    @Override
    public List<Ville> readAll() {
        return null;
    }


}
