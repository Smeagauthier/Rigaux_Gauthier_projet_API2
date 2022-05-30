package courses.gestion.modele;

import courses.metier.Course;
import courses.metier.Ville;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeleCourseDB implements DAOCourse {

    protected Connection dbConnect;

    public ModeleCourseDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Course create(Course newCourse) {
        String req1 = "insert into APICOURSE(NOM, DATEDEBUT, DATEFIN, KMTOTAL, PRICEMONEY) values (?,?,?,?,?)";
        String req2 = "select IDCOURSE from APICOURSE where NOM=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1); PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, newCourse.getNom());
            pstm1.setDate(2, Date.valueOf(newCourse.getDateDebut()));
            pstm1.setDate(3, Date.valueOf(newCourse.getDateFin()));
            pstm1.setInt(4, newCourse.getKmTotal());
            pstm1.setDouble(5, newCourse.getPriceMoney());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, newCourse.getNom());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idcourse = rs.getInt(1);
                newCourse.setIdCourse(idcourse);
                return newCourse;
            } else {
                throw new Exception("Aucune course n'a été trouvée");
            }

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean delete(Course courseRech) {
        String req = "delete from APICOURSE where IDCOURSE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, courseRech.getIdCourse());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Course read(Course courseRech) {
        String req = "select * from APICOURSE where IDCOURSE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, courseRech.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                Date dateDebut = rs.getDate("DATEDEBUT");
                Date dateFin = rs.getDate("DATEFIN");
                int kmtot = rs.getInt("KMTOTAL");
                double pricemoney = rs.getDouble("PRICEMONEY");
                Course co = new Course(courseRech.getIdCourse(), nom, dateDebut.toLocalDate(), dateFin.toLocalDate(), kmtot, pricemoney);
                return co;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Course update(Course courseRech) {
        String req = "update APICOURSE set DATEDEBUT=?, DATEFIN=?, KMTOTAL=?, PRICEMONEY=? where NOM=? ";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(5, courseRech.getNom());
            pstm.setDate(1, Date.valueOf(courseRech.getDateDebut()));
            pstm.setDate(2, Date.valueOf(courseRech.getDateFin()));
            pstm.setInt(3, courseRech.getKmTotal());
            pstm.setDouble(4, courseRech.getPriceMoney());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("Aucune course n'a été mise à jour");
            }
            return read(courseRech);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Course> readAll() {
        String req = "select * from APICOURSE order by IDCOURSE";
        List<Course> lcourse = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idCourse = rs.getInt("IDCOURSE");
                String nom = rs.getString("NOM");
                Date dateDebut = rs.getDate("DATEDEBUT");
                Date dateFin = rs.getDate("DATEFIN");
                int kmtot = rs.getInt("KMTOTAL");
                Double pricemoney = rs.getDouble("PRICEMONEY");
                lcourse.add(new Course(idCourse, nom, dateDebut.toLocalDate(), dateFin.toLocalDate(), kmtot, pricemoney));
            }
            if (lcourse.isEmpty()) {
                return null;
            }
            return lcourse;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Ville> listeVilles(Ville villeRech) {
        String req = "select * from APIVILLE where idville=?";
        List<Ville> lville = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, villeRech.getIdVille());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idVille = rs.getInt("IDVILLE");
                String nom = rs.getString("NOM");
                String pays = rs.getString("PAYS");
                Double latitude = rs.getDouble("LATITUDE");
                Double longitude = rs.getDouble("LONGITUDE");
                lville.add(new Ville(idVille, nom, pays, latitude, longitude));
            }
            return lville;

        } catch (Exception e) {
            return null;
        }
    }
}
