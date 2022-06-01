package courses.gestion.modele;

import courses.metier.*;

import java.util.List;

public interface DAOCourse extends DAO<Course>{

    List<Ville> listeVilles(Course villeRech);
    List<Classement> listeCoureursPlaceGain(Course co);
    double gainTotal(Course co);
    boolean addCoureur(Coureur c);
    boolean suppCoureur(Coureur c);
    boolean resultat(Coureur c, int place, double gain);
    boolean modifResultat(Coureur c, int place, double gain);
    boolean addEtape(Etape e);
    boolean suppEtape(Etape e);
    boolean classementComplet();
    Coureur vainqueur(Course co);

}
