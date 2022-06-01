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
    public List<Ville> listeVilles(Course course) {
        String req = "select * from APIVILLE where idville=? and IDCOURSE=? order by PAYS";
        List<Ville> lville = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(2, course.getIdCourse());
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
                System.out.println("l'erreur : " + e);
            }
            return lcla;
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public boolean addCoureur(Coureur coureur) {
        String req = "insert into APICLASSEMENT(IDCOUREUR) values (?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, coureur.getIdCoureur());
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
    public boolean suppCoureur(Coureur c) {
        String req = "delete from APICLASSEMENT where IDCOUREUR=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, c.getIdCoureur());
            int n = pstm.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("erreur sql : " + e);
            return false;
        }
        return false;
    }

    @Override
    public boolean resultat(Coureur coureur, int place, double gain) {
        String query = "insert into APICLASSEMENT(IDCOUREUR, PLACE, GAIN) values(?,?,?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, coureur.getIdCoureur());
            pstm.setInt(2, place);
            pstm.setDouble(3, gain);
            int i = pstm.executeUpdate();
            if (i != 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean modifResultat(Coureur c, int place, double gain) {
        String query2 = "select * from APICLASSEMENT cla join APICOUREUR cour on cla.IDCOUREUR = cour.IDCOUREUR where cla.IDCOURSE=? ";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query2)) {
            Course co = new Course();
            pstm.setInt(1, co.getIdCourse());
            pstm.setInt(2, c.getIdCoureur());
            pstm.setInt(3, place);
            pstm.setDouble(4, gain);
            int i = pstm.executeUpdate();
            if (i != 0) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean addEtape(Etape e) {
        String req = "insert into APIETAPE(IDCOURSE) values (?)";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, e.getCourse().getIdCourse());
            int n = pstm.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public boolean suppEtape(Etape e) {
        String req = "delete from APIETAPE where IDCOURSE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, e.getCourse().getIdCourse());
            int n = pstm.executeUpdate();
            if (n != 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("erreur sql : " + ex);
            return false;
        }
        return false;
    }

    @Override
    public boolean classementComplet() {
        return false;
    }

    public Coureur vainqueur(Course co) {
        String req = "select * from vainqueur where IDCOURSE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, co.getIdCourse());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
