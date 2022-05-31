package courses.gestion.modele;

import courses.metier.Classement;
import courses.metier.Course;
import courses.metier.Ville;

import java.util.List;

public interface DAOCourse extends DAO<Course>{

    List<Ville> listeVilles(Ville villeRech);
    List<Classement> listeCoureursPlaceGain(Course co);
    double gainTotal(Course co);
}
