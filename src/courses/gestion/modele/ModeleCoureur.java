package courses.gestion.modele;

import courses.metier.Classement;
import courses.metier.Coureur;

import java.util.ArrayList;
import java.util.List;

public class ModeleCoureur implements DAOCoureur{

    private List<Coureur> mesCoureurs = new ArrayList<>();

    @Override
    public Coureur create(Coureur newCour) {
        if(mesCoureurs.contains(newCour)) return null;
        mesCoureurs.add(newCour);
        return newCour;
    }

    @Override
    public boolean delete(Coureur CourRech) {
        Coureur co = read(CourRech);
        if(co != null){
            mesCoureurs.remove(co);
            return true;
        }
        else return false;
    }

    @Override
    public Coureur read(Coureur CourRech) {
        int idRech = CourRech.getIdCoureur();
        for(Coureur coureur : mesCoureurs){
            if(coureur.getIdCoureur() == idRech){
                return coureur;
            }
        }
        return null;
    }

    @Override
    public Coureur update(Coureur CourRech) {
        Coureur co = read(CourRech);
        if(co == null){
            return null;
        }
        co.setNom(CourRech.getNom());
        co.setPrenom(CourRech.getPrenom());
        co.setDateNaiss(CourRech.getDateNaiss());
        co.setNationalite(CourRech.getNationalite());
        co.setMatricule(CourRech.getMatricule());
        return co;
    }

    @Override
    public List<Coureur> readAll() {
        return mesCoureurs;
    }
}
