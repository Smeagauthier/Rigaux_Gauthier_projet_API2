package courses.gestion.presenter;

import courses.gestion.modele.DAOVille;
import courses.gestion.vue.VueVilleInterface;
import courses.metier.Coureur;
import courses.metier.Ville;

public class PresenterVille {

    private DAOVille mdv;
    private VueVilleInterface vuev;

    public PresenterVille(DAOVille mdv, VueVilleInterface vuev) {
        this.mdv = mdv;
        this.vuev = vuev;
    }

    public void gestion() {

        do {
            int ch = vuev.menu(new String[]{"ajout", "recherche", "modification", "voir tout", "fin"});
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

        Ville newVille = vuev.create();
        newVille = mdv.create(newVille);
        if (newVille == null) {
            vuev.displayMsg("erreur lors de la création de la ville, c'est un doublon");
            return;
        }
        vuev.displayMsg("Ville créée");
        vuev.display(newVille);
    }

    protected Ville recherche() {

        int idv = vuev.read();
        Ville vi = new Ville(idv, "","", 0, 0);
        vi = mdv.read(vi);
        if (vi == null) {
            vuev.displayMsg("ville introuvable");
            return null;
        }
        vuev.display(vi);
        return vi;
    }

    protected void modification() {
        Ville vi = recherche();
        if (vi != null) {
            vi =  vuev.update(vi);
            mdv.update(vi);
        }
    }

    protected void suppression() {
        Ville vi = recherche();
        if (vi != null) {
            String rep;
            do {
                rep = vuev.getMsg("confirmez-vous la suppression (o/n) ? ");

            } while (!rep.equalsIgnoreCase("o") && !rep.equalsIgnoreCase("n"));

            if (rep.equalsIgnoreCase("o")) {
                if( mdv.delete(vi))vuev.displayMsg("ville supprimée");
                else vuev.displayMsg("ville non supprimée");
            }
        }
    }

    protected void affAll() {
        vuev.affAll(mdv.readAll());
    }



}
