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

       /*dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.out.println("      ---- Connexion établie ----");
            System.exit(1);
        }*/

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

        pcourse.setPc(pcoureur);
        pcourse.setPv(pv);
        pcourse.setPe(pe);
        pe.setPc(pcourse);

        do {
            System.out.println("         --- Menu Principal ---");
            int ch = vcm.menu(new String[]{" Coureur", " Course", " Ville", " Etape", " Fin"});
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

}
