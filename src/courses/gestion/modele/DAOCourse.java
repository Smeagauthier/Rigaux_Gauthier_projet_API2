package courses.gestion.modele;

import courses.metier.*;

import java.util.List;

public interface DAOCourse extends DAO<Course>{

    List<Ville> listeVilles();
    List<Classement> listeCoureursPlaceGain(Course co);
    double gainTotal(Course co);
    boolean addCoureur(Coureur c, Course co);
    boolean suppCoureur(Coureur c);
    boolean resultat(Coureur c, int place, double gain, Course co);
    boolean modifResultat(Coureur c, int place, double gain);
    boolean addEtape(Etape e, Course c);
    boolean suppEtape(Etape e);
    boolean classementComplet(Course co);
    Coureur vainqueur(Course co);

}
