package courses.gestion.modele;

import courses.metier.Coureur;
import myconnections.DBConnection;

import java.sql.Connection;
import java.util.List;

public class ModeleCoureurDB implements DAOCoureur{

    protected Connection dbConnect;
    public ModeleCoureurDB(){ dbConnect = DBConnection.getConnection(); }

    @Override
    public Coureur create(Coureur newObj) {
        String req1 = "insert into APICOUREUR(MATRICULE, NOM, PRENOM, DATENAISS, NATIONALITE) values (?,?,?,?,?)";
        return null;
    }

    @Override
    public boolean delete(Coureur objRech) {
        return false;
    }

    @Override
    public Coureur read(Coureur objRech) {
        return null;
    }

    @Override
    public Coureur update(Coureur objRech) {
        return null;
    }

    @Override
    public List<Coureur> readAll() {
        return null;
    }
}
