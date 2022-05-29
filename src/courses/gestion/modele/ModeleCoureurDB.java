package courses.gestion.modele;

import courses.metier.Coureur;
import myconnections.DBConnection;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ModeleCoureurDB implements DAOCoureur {

    protected Connection dbConnect;

    public ModeleCoureurDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Coureur create(Coureur newCour) {
        String req1 = "insert into APICOUREUR(MATRICULE, NOM, PRENOM, DATENAISS, NATIONALITE) values (?,?,?,?,?)";
        String req2 = "select IDCOUREUR from APICLIENT where MATRICULE=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1); PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, newCour.getMatricule());
            pstm1.setString(2, newCour.getNom());
            pstm1.setString(3, newCour.getPrenom());
            pstm1.setDate(4, Date.valueOf(newCour.getDateNaiss()));
            pstm1.setString(5, newCour.getNationalite());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setString(1, newCour.getMatricule());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idcour = rs.getInt(1);
                newCour.setIdCoureur(idcour);
                return newCour;
            } else {
                throw new Exception("Aucun coureur n'a été trouvé");
            }

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean delete(Coureur cour) {
        String req = "delete from APICOUREUR where MATRICULE =?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, cour.getMatricule());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Coureur read(Coureur courRech) {
        String req = "select * from APICOUREUR where MATRICULE=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, courRech.getMatricule());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                Date datenaiss = rs.getDate("DATENAISS");
                String nationalite = rs.getString("NATIONALITE");
                Coureur co = new Coureur(courRech.getIdCoureur(), courRech.getMatricule(), nom, prenom, nationalite, datenaiss.toLocalDate());
                return co;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Coureur update(Coureur cour) {
        String req = "update APICOUREUR set nom=?, prenom=?, datenaiss=?, nationalite=? where matricule =?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {

            pstm.setString(5, cour.getMatricule());
            pstm.setString(1, cour.getNom());
            pstm.setString(2, cour.getPrenom());
            pstm.setDate(3, Date.valueOf(cour.getDateNaiss()));
            pstm.setString(4, cour.getNationalite());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("Aucun coureur n'a été mis à jour");
            }
            return read(cour);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Coureur> readAll() {
        String req = "select * from APICOUREUR order by IDCOUREUR";
        List<Coureur> lcour = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idCour = rs.getInt("IDCOUREUR");
                String matricule = rs.getString("MATRICULE");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                Date dateNaiss = rs.getDate("DATENAISS");
                String nationalite = rs.getString("NATIONALITE");
                lcour.add(new Coureur(idCour, matricule, nom, prenom, nationalite, dateNaiss.toLocalDate()));
            }
            if (lcour.isEmpty()) {
                return null;
            }
            return lcour;
        } catch (Exception e) {
            return null;
        }
    }
}
