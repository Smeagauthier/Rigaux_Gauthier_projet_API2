package courses.gestion.modele;

import courses.metier.Coureur;
import courses.metier.Course;
import courses.metier.Ville;

import java.util.ArrayList;
import java.util.List;

public class ModeleCourse implements DAOCourse{

    private List<Course> mesCourses = new ArrayList<>();

    @Override
    public Course create(Course newObj) {
        return null;
    }

    @Override
    public boolean delete(Course objRech) {
        return false;
    }

    @Override
    public Course read(Course objRech) {
        return null;
    }

    @Override
    public Course update(Course objRech) {
        return null;
    }

    @Override
    public List<Course> readAll() {
        return mesCourses;
    }

    @Override
    public List<Ville> listeVilles(Ville villeRech) {
        return null;
    }
}
