package courses.gestion.presenter;

import courses.gestion.modele.DAOCoureur;
import courses.gestion.modele.DAOCourse;
import courses.gestion.vue.VueCoureurInterface;
import courses.gestion.vue.VueCourseInterface;
import courses.metier.Classement;
import courses.metier.Coureur;
import courses.metier.Course;

import java.sql.PreparedStatement;
import java.util.List;

public class PresenterCourse {

    private DAOCoureur mdcoureur;
    private DAOCourse mdc;
    private VueCourseInterface vuec;
    private PresenterCoureur pc;
    private PresenterVille pv;
    private PresenterEtape pe;
    private VueCoureurInterface vueco;


    public PresenterCourse(DAOCourse mdc, VueCourseInterface vuec) {
        this.mdc = mdc;
        this.vuec = vuec;
    }

    public void setPc(PresenterCoureur pc) {
        this.pc = pc;
    }

    public void setPv(PresenterVille pv) {
        this.pv = pv;
    }

    public void setPe(PresenterEtape pe) {
        this.pe = pe;
    }


    public void gestion() {
        System.out.println("\n       **** Gestion des courses ****");

        do {
            int ch = vuec.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", " Détail des courses", " Fin"});
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    recherche();
                    break;
                case 3:
                    modification();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    affAll();
                    break;
                case 6:
                    detailCourse();
                    break;
                case 7:
                    return;
            }
        } while (true);
    }

    private void detailCourse() {

        Course co = recherche();

        if (co != null) {
            do {
                List li = null;
                int ch = vuec.menu(new String[]{"liste des coureurs-place-gain", "gain total ", "ajout coureur", "suppression coureur", "résultat", "modification", "ajout d'une étape", "suppression d'une étape", "classement complet", "vainqueur", "retour"});
                switch (ch) {
                    case 1:
                        li = mdc.listeCoureursPlaceGain(co);
                        break;
                    case 2:
                        vuec.displayMsg("Voici le gain total pour cette course: " + mdc.gainTotal(co));
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5: //l = co.resultat();
                        break;
                    case 6: //l = co.modif();
                        break;
                    case 7: //l = co.addEtape();
                        break;
                    case 8: //l = co.suppEtape();
                        break;
                    case 9: //l = co.classementComplet();
                        break;
                    case 10:
                        break;
                    case 11:
                        return;

                }
                if (li == null) {
                    if (ch != 2) {
                        vuec.displayMsg("Une erreur est survenue");
                    }
                } else {
                    if (li.isEmpty()) {
                        vuec.displayMsg("Rien à afficher");
                    } else vuec.affLobj(li);
                }


            } while (true);
        }
    }

    protected void ajout() {

        Course newCourse = vuec.create();
        newCourse = mdc.create(newCourse);
        if (newCourse == null) {
            vuec.displayMsg("erreur lors de la création de la course, c'est un doublon");
            return;
        }
        vuec.displayMsg("course créée");
        vuec.display(newCourse);
    }

    protected Course recherche() {

        int idrech = vuec.read();
        Course course = new Course(idrech, "", null, null, 0, 0);
        course = mdc.read(course);
        if (course == null) {
            vuec.displayMsg("course introuvable");
            return null;
        }
        vuec.display(course);
        return course;
    }

    protected void modification() {
        Course course = recherche();
        if (course != null) {
            course = vuec.update(course);
            mdc.update(course);
        }
    }

    protected void suppression() {
        Course course = recherche();
        if (course != null) {
            String rep;
            do {
                rep = vuec.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if (mdc.delete(course)) vuec.displayMsg("course supprimée");
                else vuec.displayMsg("course non supprimée");
            }
        }
    }

    protected void affAll() {
        vuec.affAll(mdc.readAll());
    }

    private void addCoureur() {

        Coureur coureur = pc.recherche();
        if (coureur == null) return;

    }

}
