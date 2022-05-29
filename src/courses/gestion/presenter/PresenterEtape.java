package courses.gestion.presenter;

import courses.gestion.modele.DAOEtape;
import courses.gestion.vue.VueEtapeInterface;
import courses.metier.Coureur;
import courses.metier.Etape;

public class PresenterEtape {

    private DAOEtape mde;
    private VueEtapeInterface vuee;

    public PresenterEtape(DAOEtape mde, VueEtapeInterface vuee) {
        this.mde = mde;
        this.vuee = vuee;
    }

    public void gestion() {
        System.out.println("\n       **** Gestion des étapes ****");

        do {
            int ch = vuee.menu(new String[]{" Ajout", " Recherche", " Modification"," Suppression", " Voir tout", " Fin"});
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
        Etape et = new Etape(ide, "", null, 0,null, null, null);
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
            et =  vuee.update(et);
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
                if( mde.delete(et))vuee.displayMsg("coureur supprimé");
                else vuee.displayMsg("coureur non supprimé");
            }
        }
    }

    protected void affAll() {
        vuee.affAll(mde.readAll());
    }

}
