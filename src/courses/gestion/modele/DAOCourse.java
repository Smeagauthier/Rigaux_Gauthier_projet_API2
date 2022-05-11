package courses.gestion.modele;

import courses.metier.Course;
import courses.metier.Ville;

import java.util.List;

public interface DAOCourse extends DAO<Course>{

    List<Ville> listeVilles(Ville villeRech);
}
