package courses.gestion.modele;

import courses.metier.Coureur;
import courses.metier.Ville;

import java.util.ArrayList;
import java.util.List;

public class ModeleVille implements DAOVille {

    private List<Ville> mesVilles = new ArrayList<>();

    @Override
    public Ville create(Ville newVille) {
        if (mesVilles.contains(newVille)) return null;
        mesVilles.add(newVille);
        return newVille;
    }

    @Override
    public boolean delete(Ville villeRech) {
        Ville e = read(villeRech);
        if (e != null) {
            mesVilles.remove(villeRech);
            return true;
        }
        return false;
    }

    @Override
    public Ville read(Ville villeRech) {
        int idVille = villeRech.getIdVille();
        for (Ville v : mesVilles) {
            if (v.getIdVille() == idVille) {
                return v;
            }
        }
        return null;
    }

    @Override
    public Ville update(Ville villeRech) {
        Ville v = read(villeRech);
        if (v == null) {
            return null;
        }
        v.setNom(villeRech.getNom());
        v.setLatitude(villeRech.getLatitude());
        v.setLongitude(villeRech.getLongitude());
        v.setPays(villeRech.getPays());
        return v;
    }

    @Override
    public List<Ville> readAll() {
        return mesVilles;
    }
}
