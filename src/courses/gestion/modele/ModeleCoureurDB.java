package courses.gestion.modele;

import courses.metier.Coureur;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;

import oracle.jdbc.proxy.annotation.Pre;

import java.util.List;

public class ModeleCoureurDB implements DAOCoureur {

    protected Connection dbConnect;

    public ModeleCoureurDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Coureur create(Coureur newCour) {
        String req1 = "insert into APICOUREUR(MATRICULE, NOM, PRENOM, DATENAISS, NATIONALITE) values (?,?,?,?,?)";
        String req2 = "select IDCLIENT from APICLIENT where matricule =? and nom=? and prenom =? ";
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
            pstm2.setString(2, newCour.getNom());
            pstm2.setString(3, newCour.getPrenom());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idclient = rs.getInt(1);
                newCour.setIdCoureur(idclient);
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

        String req = "delete from APICOUREUR where IDCOUREUR =?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, cour.getIdCoureur());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Coureur read(Coureur courRech) {
        /*String req = "select * from APIVUE1_PILOTES_PLACE_GAIN where NOM=? and PRENOM=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setString(1, courRech.getNom());
            pstm.setString(2, courRech.getPrenom());
            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                String nationalite = rs.getString("NATIONALITE");
                int place = rs.getInt("PLACE");
                double gain = rs.getDouble("GAIN");

                Coureur co = new Coureur(courRech.getNom(), courRech.getPrenom(), nationalite);

            }
        } catch (Exception e) {
         return null;
        }*/
        return null;
    }

    @Override
    public Coureur update(Coureur cour) {

        String req = "update APICOUREUR set ";
        return null;
    }

    @Override
    public List<Coureur> readAll() {
        return null;
    }
}
