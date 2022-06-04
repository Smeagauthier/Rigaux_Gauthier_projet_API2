package courses.gestion.modele;

import courses.metier.Coureur;
import courses.metier.Course;
import courses.metier.Etape;

import java.util.ArrayList;
import java.util.List;

public class ModeleEtape implements DAOEtape {

    private List<Etape> mesEtapes = new ArrayList<>();


    @Override
    public Etape create(Etape newEtape) {
        if (mesEtapes.contains(newEtape)) return null;
        mesEtapes.add(newEtape);
        return newEtape;
    }


    @Override
    public boolean delete(Etape etapeRech) {
        Etape e = read(etapeRech);
        if (e != null) {
            mesEtapes.remove(e);
            return true;
        }
        return false;
    }

    @Override
    public Etape read(Etape etapeRech) {
        int idEtape = etapeRech.getIdEtape();
        for (Etape e : mesEtapes) {
            if (e.getIdEtape() == idEtape) {
                return etapeRech;
            }
        }
        return null;
    }

    @Override
    public Etape update(Etape etapeRech) {
        Etape e = read(etapeRech);
        if (e == null) {
            return null;
        }
        e.setNumero(etapeRech.getNumero());
        e.setDescription(etapeRech.getDescription());
        e.setDateEtape(etapeRech.getDateEtape());
        e.setKm(etapeRech.getKm());

        return e;
    }

    @Override
    public List<Etape> readAll() {
        return mesEtapes;
    }
}
