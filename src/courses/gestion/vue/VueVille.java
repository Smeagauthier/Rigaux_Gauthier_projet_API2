package courses.gestion.vue;

import courses.metier.Ville;

import java.util.ArrayList;
import java.util.List;

public class VueVille extends VueCommune implements VueVilleInterface {


    @Override
    public Ville create() {
        String nomville = getMsg("Nom de la ville : ");
        String pays = getMsg("Pays : ");
        Double latitude = Double.parseDouble(getMsg("Latitude : "));
        Double longitude = Double.parseDouble(getMsg("Longitude : "));

        Ville newVille = new Ville(nomville, pays, latitude, longitude);
        return newVille;
    }

    @Override
    public void display(Ville ville) {
        displayMsg(ville.toString());
    }

    @Override
    public Ville update(Ville ville) {

        do {
            String ch = getMsg("1. Changement de ville\n2. Fin");
            switch (ch) {
                case "1":
                    String nouvVille = getMsg("Nouvelle ville : ");
                    ville.setNom(nouvVille);
                    break;

                case "2":
                    return ville;
                default:
                    displayMsg("Veuillez entrer un choix valide");
            }
        } while (true);
    }

    @Override
    public Integer read() {
        String id = getMsg("Identifiant de la ville : ");
        int ident = Integer.parseInt(id);
        return ident;
    }

    @Override
    public void affAll(List<Ville> lvil) {
        int i = 0;
        for (Ville v : lvil) {
            displayMsg((++i) + ") " + v.toString());

        }
    }
}
