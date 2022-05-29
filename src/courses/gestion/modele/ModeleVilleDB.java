package courses.gestion.modele;

import courses.metier.Ville;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeleVilleDB implements DAOVille {

    protected Connection dbConnect;

    public ModeleVilleDB() {
        dbConnect = DBConnection.getConnection();
    }

    @Override
    public Ville create(Ville newVille) {
        String req1 = "insert into APIVILLE (nom, latitude, longitude, pays) values (?,?,?,?)";
        String req2 = "select IDVILLE from APIVILLE where latitude=? and longitude=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1); PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setString(1, newVille.getNom());
            pstm1.setDouble(2, newVille.getLatitude());
            pstm1.setDouble(3, newVille.getLongitude());
            pstm1.setString(4, newVille.getPays());
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }
            pstm2.setDouble(1, newVille.getLatitude());
            pstm2.setDouble(2, newVille.getLongitude());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idville = rs.getInt(1);
                newVille.setIdVille(idville);
                return newVille;
            } else {
                throw new Exception("aucune ville de trouvée");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Ville vilRech) {
        String req = "delete from APIVILLE where idville=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, vilRech.getIdVille());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Ville read(Ville vilRech) {
        String req = "select * from APIVILLE where idville = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, vilRech.getIdVille());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("NOM");
                double latitude = rs.getDouble("LATITUDE");
                double longitude = rs.getDouble("LONGITUDE");
                String pays = rs.getString("PAYS");
                Ville vi = new Ville(vilRech.getIdVille(), nom, pays, latitude, longitude);
                return vi;
            } else return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Ville update(Ville vilRech) {
        String req = "update APIVILLE set nom = ?, latitude = ?, longitude = ?, pays = ? where idville = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(5, vilRech.getIdVille());
            pstm.setString(1, vilRech.getNom());
            pstm.setDouble(2, vilRech.getLatitude());
            pstm.setDouble(3, vilRech.getLongitude());
            pstm.setString(4, vilRech.getPays());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("aucune ville n'a été mise à jour");
            }
            return read(vilRech);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Ville> readAll() {
        String req = "select * from APIVILLE";
        List<Ville> lvil = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idville = rs.getInt("IDVILLE");
                String nom = rs.getString("NOM");
                Double latitude = rs.getDouble("LATITUDE");
                Double longitude = rs.getDouble("LONGITUDE");
                String pays = rs.getString("PAYS");
                lvil.add(new Ville(idville, nom, pays, latitude, longitude));
            }
            if (lvil.isEmpty()) return null;
            return lvil;
        } catch (Exception e) {
            return null;
        }
    }


}
