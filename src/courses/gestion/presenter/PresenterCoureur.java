package courses.gestion.presenter;

import courses.gestion.modele.DAOCoureur;
import courses.gestion.vue.VueCoureurInterface;
import courses.metier.Coureur;

import java.util.List;

public class PresenterCoureur {

    private DAOCoureur mdc;
    private VueCoureurInterface vuec;

    public PresenterCoureur(DAOCoureur mdc, VueCoureurInterface vuec) {
        this.mdc = mdc;
        this.vuec = vuec;
    }

    public void gestion() {
        System.out.println("\n       **** Gestion des coureurs ****");
        do {
            System.out.println("\n");
            int ch = vuec.menu(new String[]{" Ajout", " Recherche", " Modification", " Suppression", " Voir tout", " Retour"});
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
                    voirTout();
                    break;
                case 6:
                    return;

            }
        } while (true);
    }

    protected void ajout() {

        Coureur newCour = vuec.create();
        newCour = mdc.create(newCour);
        if (newCour == null) {
            vuec.displayMsg("erreur lors de la création du coureur, c'est un doublon");
            return;
        }
        vuec.displayMsg("coureur créé");
        vuec.display(newCour);
    }

    protected Coureur recherche() {

        String mrech = vuec.read();
        Coureur cour = new Coureur(mrech, "", "", "", null);
        cour = mdc.read(cour);
        if (cour == null) {
            vuec.displayMsg("coureur introuvable");
            return null;
        }
        vuec.display(cour);
        return cour;
    }

    protected void modification() {
        Coureur cour = recherche();
        if (cour != null) {
            cour = vuec.update(cour);
            mdc.update(cour);
        }
    }

    protected void suppression() {
        Coureur cour = recherche();
        if (cour != null) {
            String rep;
            do {
                rep = vuec.getMsg("Confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if (mdc.delete(cour)) vuec.displayMsg("coureur supprimé");
                else vuec.displayMsg("coureur non supprimé");
            }
        }
    }

    protected Coureur affAll() {
        List<Coureur> lc = mdc.readAll();
        vuec.affAll(mdc.readAll());
        do {
            String chs = vuec.getMsg("numéro de l'élément choisi (0 pour aucun) :");
            int ch = Integer.parseInt(chs);
            if (ch == 0) return null;
            if (ch >= 1 && ch <= lc.size()) return lc.get(ch - 1);
        } while (true);
    }

    protected void voirTout() {
        vuec.affAll(mdc.readAll());
    }
}


