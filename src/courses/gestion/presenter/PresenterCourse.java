package courses.gestion.presenter;

import courses.gestion.modele.DAOCoureur;
import courses.gestion.modele.DAOCourse;
import courses.gestion.vue.VueCoureurInterface;
import courses.gestion.vue.VueCourseInterface;
import courses.gestion.vue.VueEtapeInterface;
import courses.gestion.vue.VueVilleInterface;
import courses.metier.*;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

public class PresenterCourse {

    private DAOCoureur mdcoureur;
    private DAOCourse mdc;
    private VueCourseInterface vuec;
    private VueVilleInterface vuev;
    private VueEtapeInterface vuee;
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
                System.out.println("\n");
                int ch = vuec.menu(new String[]{"liste des coureurs-place-gain", "liste des villes ", "gestion coureurs", "résultat", "modification du résultat", "gestion des étapes", "classement complet", "vainqueur", " gain total", "retour"});
                switch (ch) {
                    case 1:
                        li = mdc.listeCoureursPlaceGain(co);
                        break;
                    case 2:
                        li = mdc.listeVilles();
                        break;
                    case 3:
                        gestCoureur(co);
                        break;
                    case 4:
                        resultat(co);
                        break;
                    case 5:
                        modifResultat(co);
                        break;
                    case 6:
                        gestEtape(co);
                        break;
                    case 7:
                       boolean flag = co.classementComplet();
                       if(flag) vuec.displayMsg("Tous les coureurs ont une place");
                       else vuec.displayMsg("Classement non complet");
                        break;
                    case 8:
                        vuec.displayMsg("Voici le vainqueur de cette course: " + mdc.vainqueur(co));
                        break;
                    case 9:
                        vuec.displayMsg("Voici le gain total pour cette course: " + mdc.gainTotal(co));
                        break;
                    case 10:
                        return;
                }
                if (li == null) {
                    if(ch < 2){
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

    public void gestCoureur(Course c) {
        do {
            int ch = vuec.menu(new String[]{" ajout coureur", " suppression coureur", " fin"});
            switch (ch) {
                case 1:
                    addCoureur(c);
                    break;
                case 2:
                    suppCoureur(c);
                    break;
                case 3:
                    return;
            }
        } while (true);
    }

    public void gestEtape(Course c) {
        do {
            int ch = vuec.menu(new String[]{" ajout étape", " suppression étape", " fin"});
            switch (ch) {
                case 1:
                    addEtape(c);
                    break;
                case 2:
                    suppEtape(c);
                    break;
                case 3:
                    return;
            }
        } while (true);
    }


    private void addEtape(Course c) {
        Etape etape = pe.affAll();
        if (etape == null) return;
        boolean res = mdc.addEtape(etape, c);
        if (res) vuec.displayMsg("étape ajoutée");
        else vuec.displayMsg("étape pas ajoutée");
    }

    private void suppEtape(Course c) {
        Etape et = pe.affAll();
        if (et == null) return;
        boolean res = mdc.suppEtape(et);
        if (res) vuec.displayMsg("étape supprimée");
        else vuec.displayMsg("étape pas supprimée");
    }

    private void resultat(Course co) {
        Coureur cour = pc.affAll();
        if (cour == null) return;
        int place = Integer.parseInt(vuec.getMsg("Place: "));
        double gain = Double.parseDouble(vuec.getMsg("Gain: "));
        boolean res = mdc.resultat(cour, place, gain, co);
        if (res) vuec.displayMsg("Résultat modifié");
        else vuec.displayMsg("Erreur de modification");
    }

    private void modifResultat(Course co) {
        Coureur cour = pc.affAll();
        if (cour == null) return;
        int newplace = Integer.parseInt(vuec.getMsg("nouvelle place : "));
        double newgain = Double.parseDouble(vuec.getMsg("nouveau gain : "));
        boolean res = mdc.resultat(cour, newplace, newgain, co);
        if (res) vuec.displayMsg("Résultat modifié");
        else vuec.displayMsg("Erreur de modification");
    }


    private void addCoureur(Course c) {
        Coureur cour = pc.affAll();
        if (cour == null) return;
        boolean res = mdc.addCoureur(cour, c);
        if (res) vuec.displayMsg("coureur ajouté");
        else vuec.displayMsg("coureur pas ajouté");
    }

    private void suppCoureur(Course c) {
        Coureur co = pc.affAll();
        if (co == null) return;
        boolean res = mdc.suppCoureur(co);
        if (res) vuec.displayMsg("coureur supprimé");
        else vuec.displayMsg("coureur pas supprimé");
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
}
