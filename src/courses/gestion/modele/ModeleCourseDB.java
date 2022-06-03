package courses.gestion.modele;

import courses.metier.*;
import myconnections.DBConnection;

import java.sql.*;
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
    public List<Ville> listeVilles() {
        String req = "select * from APIVILLE  order by PAYS";
        List<Ville> lville = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idVille = rs.getInt("IDVILLE");
                String nom = rs.getString("NOM");
                String pays = rs.getString("PAYS");
                Double latitude = rs.getDouble("LATITUDE");
                Double longitude = rs.getDouble("LONGITUDE");
                lville.add(new Ville(idVille, nom, pays, latitude, longitude));
            }
            if (lville.isEmpty()) {
                return null;
            }
            return lville;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Classement> listeCoureursPlaceGain(Course co) {
        String req = "select * from PILOTE_COURSE where NOM_COURSE=?";
        List<Classement> lcla = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, co.getNom());
            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    String nom = rs.getString("NOM");
                    String prenom = rs.getString("PRENOM");
                    Coureur idc = new Coureur(nom, prenom);
                    int place = rs.getInt("PLACE");
                    Double gain = rs.getDouble("GAIN");
                    lcla.add(new Classement(idc, place, gain));
                }
                if (lcla.isEmpty()) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
            return lcla;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean resultat(Coureur coureur, int place, double gain, Course course) {
        String query = "select * from APICLASSEMENT where IDCOUREUR=? and IDCOURSE=? ";
        String query2 = "update APICLASSEMENT set PLACE=?, GAIN=? where IDCOUREUR=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm.setInt(1, coureur.getIdCoureur());
            pstm.setInt(2, course.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                pstm2.setInt(1, place);
                pstm2.setDouble(2, gain);
                pstm2.setInt(3, coureur.getIdCoureur());
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean modifResultat(Coureur c, int place, double gain) {
        String query = "select * from APICLASSEMENT where IDCOUREUR=?";
        String query2 = "update APICLASSEMENT set PLACE=?, GAIN=? where IDCOUREUR=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {
            pstm.setInt(1, c.getIdCoureur());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                pstm2.setInt(3, c.getIdCoureur());
                pstm2.setInt(1, place);
                pstm2.setDouble(2, gain);
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean addCoureur(Coureur coureur, Course course) {
        String req = "select * from APICLASSEMENT where IDCOURSE=?";
        String req2 = "insert into APICLASSEMENT(IDCOUREUR, IDCOURSE) values (?,?)";

        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
             PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {

            pstm.setInt(1, course.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                pstm2.setInt(1, coureur.getIdCoureur());
                pstm2.setInt(2, course.getIdCourse());
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean suppCoureur(Coureur c) {
        String req = "delete from APICLASSEMENT where IDCOUREUR=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, c.getIdCoureur());
            int n = pstm.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean addEtape(Etape e, Course c) {
        String req = "select * from APIETAPE where IDETAPE=?";
        String req2 = "update APIETAPE set IDCOURSE=? where IDETAPE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
             PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm.setInt(1, e.getIdEtape());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                pstm2.setInt(1, c.getIdCourse());
                pstm2.setInt(2, e.getIdEtape());
                pstm2.executeQuery();
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public boolean suppEtape(Etape e, Course c) {
        String req = "select * from APIETAPE where IDCOURSE=? and IDETAPE=?";
        String req2 = "update APIETAPE set IDCOURSE=null where IDETAPE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req);
             PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm.setInt(1, c.getIdCourse());
            pstm.setInt(2, e.getIdEtape());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                pstm2.setInt(1, e.getIdEtape());
                pstm2.executeQuery();
                return true;
            }

        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public boolean classementComplet(Course co) {
        String req = "select * from APICLASSEMENT where PLACE is null and IDCOURSE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req))
        {
            pstm.setInt(1, co.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public double gainTotal(Course co) {
        String req = "select * from GAIN_TOTAL_COURSE where NOM_COURSE=?";
        double gainTot = 0.0;
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, co.getNom());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Double gain = rs.getDouble("GAIN_TOTAL");
                gainTot += gain;
            }
            return gainTot;
        } catch (Exception e) {
            return 0;
        }
    }

    public Coureur vainqueur(Course co) {
        String req = "select * from PILOTE_COURSE where IDCOURSE=? and PLACE=1";

        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, co.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                Coureur c = new Coureur(nom, prenom);
                return c;
            }
        } catch (Exception e) {
            System.out.println("erreur : " + e);
            return null;
        }
        return null;
    }
}
