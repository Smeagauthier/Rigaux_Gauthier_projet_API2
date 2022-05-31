package courses.gestion.modele;

import courses.metier.Classement;
import courses.metier.Course;
import courses.metier.Ville;

import java.util.ArrayList;
import java.util.List;

public class ModeleCourse implements DAOCourse {

    private List<Course> mesCourses = new ArrayList<>();

    @Override
    public Course create(Course newCourse) {
        if (mesCourses.contains(newCourse)) return null;
        mesCourses.add(newCourse);
        return newCourse;
    }

    @Override
    public boolean delete(Course courseRech) {
        Course cr = read(courseRech);
        if (cr != null) {
            mesCourses.remove(cr);
            return true;
        }
        return false;
    }

    @Override
    public Course read(Course courseRech) {
        int idCourse = courseRech.getIdCourse();
        for (Course c : mesCourses) {
            if (c.getIdCourse() == idCourse) return c;
        }
        return null;
    }

    @Override
    public Course update(Course courseRech) {
        Course co = read(courseRech);
        if (co == null) {
            return null;
        }
        co.setNom(courseRech.getNom());
        co.setDateDebut(courseRech.getDateDebut());
        co.setDateFin(courseRech.getDateFin());
        co.setKmTotal(courseRech.getKmTotal());
        co.setPriceMoney(courseRech.getPriceMoney());

        return co;
    }

    @Override
    public List<Course> readAll() {
        return mesCourses;
    }

    @Override
    public List<Ville> listeVilles(Ville villeRech) {
        return null;
    }

    @Override
    public List<Classement> listeCoureursPlaceGain(Course co) {
        return null;
    }

    @Override
    public double gainTotal(Course co) {
        return 0;
    }

}
