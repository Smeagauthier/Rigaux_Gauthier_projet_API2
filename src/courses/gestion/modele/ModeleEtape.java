package courses.gestion.modele;

import courses.metier.Coureur;
import courses.metier.Etape;

import java.util.ArrayList;
import java.util.List;

public class ModeleEtape implements DAOEtape {

    private List<Etape> mesEtapes = new ArrayList<>();


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
        return mesEtapes;
    }
}
