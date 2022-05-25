package courses.gestion.presenter;

import courses.gestion.modele.DAOCoureur;
import courses.gestion.vue.VueCoureurInterface;
import courses.metier.Coureur;

public class PresenterCoureur {

    private DAOCoureur mdc;
    private VueCoureurInterface vuec;

    public PresenterCoureur(DAOCoureur mdc, VueCoureurInterface vuec) {
        this.mdc = mdc;
        this.vuec = vuec;
    }

    public void gestion() {

        do {
            int ch = vuec.menu(new String[]{"ajout", "recherche", "modification", "voir tout", "fin"});
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
        Coureur cour = new Coureur(mrech,"","","",null);
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
            cour =  vuec.update(cour);
            mdc.update(cour);
        }
    }

    protected void suppression() {
        Coureur cour = recherche();
        if (cour != null) {
            String rep;
            do {
                rep = vuec.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if( mdc.delete(cour))vuec.displayMsg("coureur supprimé");
                else vuec.displayMsg("coureur non supprimé");
            }
        }
    }

    protected void affAll() {
        vuec.affAll(mdc.readAll());
    }

}
