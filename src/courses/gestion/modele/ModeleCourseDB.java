package courses.gestion.modele;

import courses.metier.Course;
import courses.metier.Ville;
import myconnections.DBConnection;

import java.sql.Connection;
import java.util.List;

public class ModeleCourseDB implements DAOCourse{

    protected Connection dbConnect;
    public ModeleCourseDB(){ dbConnect = DBConnection.getConnection(); }

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
        return null;
    }

    @Override
    public List<Ville> listeVilles(Ville villeRech) {
        return null;
    }
}
