package courses.gestion.modele;

import courses.metier.Course;
import courses.metier.Etape;
import courses.metier.Ville;
import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeleEtapeDB implements DAOEtape {

    protected Connection dbConnect;

    public ModeleEtapeDB() {
        dbConnect = DBConnection.getConnection();
    }


    @Override
    public Etape create(Etape etape) {
        String req1 = "insert into APIETAPE(numero, description, dateetape, km, idcourse, idville, idville_1) values (?,?,?,?,?,?,?)";
        String req2 = "select IDETAPE from APIETAPE where NUMERO=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(req1); PreparedStatement pstm2 = dbConnect.prepareStatement(req2)) {
            pstm1.setInt(1, etape.getNumero());
            pstm1.setString(2, etape.getDescription());
            pstm1.setDate(3, Date.valueOf(etape.getDateEtape()));
            pstm1.setInt(4, etape.getKm());
            pstm1.setInt(5, etape.getCourse().getIdCourse());
            pstm1.setString(6, String.valueOf(etape.getVilleDepart()));
            pstm1.setString(7, String.valueOf(etape.getVilleArrivee()));

            //le execute update ne se fait pas --> dû aux lignes 34-35 ? (ORA-02291 : violation de contrainte d'integrité)
            int n = pstm1.executeUpdate();
            if (n == 0) {
                return null;
            }

            pstm2.setInt(1, etape.getNumero());
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int idetape = rs.getInt(1);
                etape.setIdEtape(idetape);
                return etape;
            } else {
                throw new Exception("aucune étape de trouvée");
            }
        } catch (Exception e) {
            System.out.println("voici l'erreur : "+e);
            return null;
        }
    }

    @Override
    public boolean delete(Etape etapeRech) {
        String req = "delete from APIETAPE where numero=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, etapeRech.getNumero());
            int n = pstm.executeUpdate();
            if (n == 0) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Etape read(Etape etapeRech) {
        String req = "select * from APIETAPE where numero=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(1, etapeRech.getNumero());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String description = rs.getString("DESCRIPTION");
                Date dateetape = rs.getDate("DATEETAPE");
                int km = rs.getInt("KM");
                int idcourse = rs.getInt("IDCOURSE");
                Course idc = new Course(idcourse);
                String idville = rs.getString("IDVILLE");
                Ville idv = new Ville(idville);
                String idville_1 = rs.getString("IDVILLE_1");
                Ville idv_1 = new Ville(idville_1);
                Etape et = new Etape(etapeRech.getIdEtape(), etapeRech.getNumero(), description, dateetape.toLocalDate(), km, idc, idv, idv_1);
                return et;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Etape update(Etape etapeRech) {
        String req = "update from APIETAPE set DESCRIPTION=?, DATEETAPE=?, KM=? where NUMERO=?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(req)) {
            pstm.setInt(4, etapeRech.getNumero());
            pstm.setString(1, etapeRech.getDescription());
            pstm.setDate(2, Date.valueOf(etapeRech.getDateEtape()));
            pstm.setInt(3, etapeRech.getKm());
            int n = pstm.executeUpdate();
            if (n == 0) {
                throw new Exception("aucune étape n'a été mise à jour");
            }
            return read(etapeRech);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Etape> readAll() {
        String req = "select * from APIETAPE order by IDETAPE";
        List<Etape> letape = new ArrayList<>();
        try (PreparedStatement pstm = dbConnect.prepareStatement(req); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                int idetape = rs.getInt("IDETAPE");
                int numero = rs.getInt("NUMERO");
                String description = rs.getString("DESCRIPTION");
                Date dateEt = rs.getDate("DATEETAPE");
                int km = rs.getInt("KM");
                int idcourse = rs.getInt("IDCOURSE");
                Course idc = new Course(idcourse);
                String idville = rs.getString("IDVILLE");
                Ville villeDep = new Ville(idville);
                String idville_1 = rs.getString("IDVILLE_1");
                Ville villeArr = new Ville(idville_1);
                letape.add(new Etape(idetape, numero, description, dateEt.toLocalDate(), km, idc, villeDep, villeArr));
            }
            if (letape.isEmpty()) {
                return null;
            }
            return letape;
        } catch (Exception e) {
            return null;
        }
    }
}
