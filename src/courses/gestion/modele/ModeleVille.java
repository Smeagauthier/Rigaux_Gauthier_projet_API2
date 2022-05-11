package courses.gestion.modele;

import courses.metier.Coureur;
import courses.metier.Ville;

import java.util.ArrayList;
import java.util.List;

public class ModeleVille implements DAOVille{

    private List<Ville> mesVilles = new ArrayList<>();

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
        return mesVilles;
    }
}
