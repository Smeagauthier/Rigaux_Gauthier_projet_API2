package courses.gestion.presenter;

import courses.gestion.modele.DAOCourse;
import courses.gestion.modele.DAOEtape;
import courses.gestion.vue.VueCourseInterface;
import courses.gestion.vue.VueEtapeInterface;
import courses.metier.Coureur;
import courses.metier.Course;
import courses.metier.Etape;

import java.util.List;

public class PresenterEtape {

    private DAOEtape mde;
    private VueEtapeInterface vuee;
    private DAOCourse mdc;
    private PresenterCourse pc;
    private PresenterEtape pe;

    public PresenterEtape(DAOEtape mde, VueEtapeInterface vuee) {
        this.mde = mde;
        this.vuee = vuee;
    }

    public void setPc(PresenterCourse pc) {
        this.pc = pc;
    }

    public void gestion() {
        System.out.println("\n       **** Gestion des étapes ****");

        do {
            System.out.println("\n");
            int ch = vuee.menu(new String[]{" Ajout (ne fonctionne pas)", " Recherche", " Modification", " Suppression", " Voir tout", " Retour"});
            switch (ch) {
                case 1:
                    vuee.displayMsg("L'ajout ne fonctionne pas car il y a une violation de contrainte d'intégrité - clé parent introuvable (ORA-02291) à cause de l'IDCOURSE (cf ligne commentée dans le create de ModeleEtapeDB)");
                    //ajout();
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
                    voirTout();
                    break;
                case 6:
                    return;

            }
        } while (true);
    }

    protected void ajout() {
        Etape newEt = vuee.create();
        newEt = mde.create(newEt);
        if (newEt == null) {
            vuee.displayMsg("erreur lors de la création de l'étape, c'est un doublon");
            return;
        }
        vuee.displayMsg("étape créée");
        vuee.display(newEt);
    }

    protected Etape recherche() {

        int ide = vuee.read();
        Etape et = new Etape(ide, "", null, 0, null, null, null);
        et = mde.read(et);
        if (et == null) {
            vuee.displayMsg("étape introuvable");
            return null;
        }
        vuee.display(et);
        return et;
    }

    protected void modification() {
        Etape et = recherche();
        if (et != null) {
            et = vuee.update(et);
            mde.update(et);
        }
    }

    protected void suppression() {
        Etape et = recherche();
        if (et != null) {
            String rep;
            do {
                rep = vuee.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if (mde.delete(et)) vuee.displayMsg("étape supprimée");
                else vuee.displayMsg("étape non supprimée");
            }
        }
    }

    protected Etape affAll() {
        List<Etape> le = mde.readAll();
        vuee.affAll(mde.readAll());
        do {
            String chs = vuee.getMsg("numéro de l'élément choisi (0 pour aucun) :");
            int ch = Integer.parseInt(chs);
            if (ch == 0) return null;
            if (ch >= 1 && ch <= le.size()) return le.get(ch - 1);
        } while (true);
    }

    protected void voirTout() {
        vuee.affAll(mde.readAll());
    }

}
