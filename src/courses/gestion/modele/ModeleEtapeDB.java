package courses.gestion.modele;

import courses.metier.Etape;
import myconnections.DBConnection;

import java.sql.Connection;
import java.util.List;

public class ModeleEtapeDB implements DAOEtape{

    protected Connection dbConnect;
    public ModeleEtapeDB(){ dbConnect = DBConnection.getConnection(); }


    @Override
    public Etape create(Etape newObj) {
        return null;
    }

    @Override
    public boolean delete(Etape objRech) {
        return false;
    }

    @Override
    public Etape read(Etape objRech) {
        return null;
    }

    @Override
    public Etape update(Etape objRech) {
        return null;
    }

    @Override
    public List<Etape> readAll() {
        return null;
    }
}
