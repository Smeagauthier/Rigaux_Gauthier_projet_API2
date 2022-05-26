package courses.gestion;

import courses.gestion.modele.*;
import courses.gestion.presenter.PresenterCoureur;
import courses.gestion.presenter.PresenterCourse;
import courses.gestion.presenter.PresenterEtape;
import courses.gestion.presenter.PresenterVille;
import courses.gestion.vue.*;
import courses.metier.Coureur;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Gestion {

    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;

    private PresenterCoureur pcoureur;
    private PresenterCourse pcourse;
    private PresenterVille pv;
    private PresenterEtape pe;

    public void gestion(String modeVue, String modeData) {

        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }

        VueCoureurInterface vuecour;
        VueCourseInterface vuecourse;
        VueVilleInterface vuev;
        VueEtapeInterface vuee;
        VueCommuneInterface vcm;

        DAOCoureur mdcoureur;
        DAOCourse mdcourse;
        DAOVille mdv;
        DAOEtape mde;

        if (modeVue.equals("console")) {
            vuecour = new VueCoureur();
            vuecourse = new VueCourse();
            vuev = new VueVille();
            vuee = new VueEtape();
            vcm = new VueCommune();
        } else {
            vuecour = new VueCoureurGraph();
            vuecourse = new VueCourseGraph();
            vuev = new VueVilleGraph();
            vuee = new VueEtapeGraph();
            vcm = new VueCommuneGraph();
        }

        if (modeData.equals("db")) {
            mdcoureur = new ModeleCoureurDB();
            mdcourse = new ModeleCourseDB();
            mdv = new ModeleVilleDB();
            mde = new ModeleEtapeDB();
        } else {
            mdcoureur = new ModeleCoureur();
            mdcourse = new ModeleCourse();
            mdv = new ModeleVille();
            mde = new ModeleEtape();
        }

        pcoureur = new PresenterCoureur(mdcoureur, vuecour);
        pcourse = new PresenterCourse(mdcourse, vuecourse);
        pv = new PresenterVille(mdv, vuev);
        pe = new PresenterEtape(mde, vuee);


        System.out.println("      ---- Connexion établie ----");
        System.out.println("         --- Menu Principal ---");
        System.out.println("\n");


        do {
            int ch = vcm.menu(new String[]{"1. Coureur", "\n2. Course", "\n3. Ville", "\n4. Etape", "\n5. Fin"});

            sc.skip("\n");
            switch (ch) {
                case 1:
                    pcoureur.gestion();
                    break;
                case 2:
                    pcourse.gestion();
                    break;
                case 3:
                    pv.gestion();
                    break;
                case 4:
                    pe.gestion();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }

    public static void main(String[] args) {
        String modeVue = args[0];
        String modeData = args[1];
        Gestion g = new Gestion();
        g.gestion(modeVue, modeData);
    }


    /*public void menuCoureur() {
        System.out.println("\n       **** Gestion des coureurs ****");
        do {
            System.out.println("\n      ***** Menu Principal *****");
            System.out.println("1. Ajout\n2. Modification\n3. Affichage des coureurs\n4. Suppression\n5. Recherche\n6. Fin");
            System.out.print("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    modification();
                    break;
                case 3:
                    affichage();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    recherche();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

        public void ajout() {
        System.out.print("Matricule : ");
        String MATRICULE = sc.nextLine();
        System.out.print("Nom :");
        String NOM = sc.nextLine();
        System.out.print("Prénom :");
        String PRENOM = sc.nextLine();
        System.out.println("Date de naissance: ");
        System.out.println("Jour : ");
        int j = sc.nextInt();
        System.out.println("Mois : ");
        int m = sc.nextInt();
        System.out.println("Année : ");
        int a = sc.nextInt();
        LocalDate DATENAISS = LocalDate.of(a, m, j);
        sc.nextLine();
        System.out.print("Nationalité : ");
        String NATIONALITE = sc.nextLine();


        String query1 = "insert into APICOUREUR(MATRICULE,NOM,PRENOM,DATENAISS,NATIONALITE) values(?,?,?,?,?)";
        String query2 = "select IDCOUREUR from APICOUREUR where MATRICULE=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)
        ) {
            pstm1.setString(1, MATRICULE);
            pstm1.setString(2, NOM);
            pstm1.setString(3, PRENOM);
            pstm1.setDate(4, Date.valueOf(DATENAISS));
            pstm1.setString(5, NATIONALITE);

            int n = pstm1.executeUpdate();
            System.out.println(n + " ligne insérée");
            if (n == 1) {
                pstm2.setString(1, MATRICULE);
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int IDCOUREUR = rs.getInt(1);
                    System.out.println("idCoureur = " + IDCOUREUR);
                } else System.out.println("record introuvable");
                rs.close();
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
        System.out.println();
    }

    public void suppression() {
        System.out.print("Saisissez le matricule du coureur à supprimer :  ");
        String matrech = sc.nextLine();
        String query1 = "delete from APICOUREUR where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query1);) {
            pstm.setString(1, matrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne supprimée");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    public void modification() {
        System.out.print("Saisissez le matricule du coureur à modifier : ");
        String matrech = sc.nextLine();
        System.out.print("Nouvelle nationalité : ");
        String nnationalite = sc.nextLine();
        String query = "update APICOUREUR set NATIONALITE=? where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, nnationalite);
            pstm.setString(2, matrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne mise à jour");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    public void recherche() {
        System.out.print("Saisissez le matricule du coureur recherché :  ");
        String matrech= sc.nextLine();
        String query = "select * from APICOUREUR where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, matrech);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int IDCOUREUR = rs.getInt("IDCOUREUR");
                String MATRICULE = rs.getString("MATRICULE");
                String NOM = rs.getString("NOM");
                String PRENOM = rs.getString("PRENOM");
                Date DATENAISS = rs.getDate("DATENAISS");
                String NATIONALITE = rs.getString("NATIONALITE");
                System.out.printf("%d %s %s %s %s %s\n", IDCOUREUR, MATRICULE, NOM, PRENOM, DATENAISS, NATIONALITE);
            } else System.out.println("record introuvable");
            rs.close();
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
        System.out.println();
    }

    public void affichage() {
        System.out.println("\n       *****Liste des coureurs enregistrés*****");
        try (Statement stmt = dbConnect.createStatement(); ResultSet rs = stmt.executeQuery("select * from APICOUREUR")) {
            while (rs.next()) {
                int id = rs.getInt("IDCOUREUR");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nationalite = rs.getString("NATIONALITE");
                String matricule = rs.getString("MATRICULE");
                System.out.println(id + ". " + nom + " " + prenom + " (" + nationalite+")    \t\tmatricule: "+matricule);
            }

        } catch (SQLException e) {
            System.out.println("Erreur sql : " + e);
        }
        System.out.println();

    } */

}
