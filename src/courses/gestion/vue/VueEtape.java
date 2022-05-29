package courses.gestion.vue;

import courses.metier.Course;
import courses.metier.Etape;
import courses.metier.Ville;

import java.time.LocalDate;
import java.util.List;

public class VueEtape extends VueCommune implements VueEtapeInterface {


    @Override
    public Etape create() {

        Integer num = Integer.parseInt(getMsg("Numéro de l'étape : "));
        String desc = getMsg("Description : ");
        LocalDate dateEtape = readDate("Date de l'étape: ");
        /*Integer jour = Integer.parseInt(getMsg("Jour de l'étape : "));
        Integer mois = Integer.parseInt(getMsg("Mois de l'étape : "));
        Integer ann = Integer.parseInt(getMsg("Année de l'étape : "));
        LocalDate dateEtape = LocalDate.of(jour, mois, ann);*/
        Integer km = Integer.parseInt(getMsg("Kilomètres : "));
        Integer idcourse = Integer.parseInt(getMsg("Id de la course : "));
        Course co = new Course(idcourse);
        String villeDep = getMsg("Ville de départ : ");
        Ville villeD = new Ville(villeDep);
        String villeArr = getMsg("Ville d'arrivée : ");
        Ville villeA = new Ville(villeArr);

        Etape newEtape = new Etape(num, desc, dateEtape, km, co, villeD, villeA);
        return null;
    }

    @Override
    public void display(Etape et) {
        displayMsg(et.toString());
    }

    @Override
    public Etape update(Etape et) {
        do {
            String ch = getMsg("1. Changement date de l'étape\n2. fin");
            switch (ch) {
                case "1":
                    Integer nouvjour = Integer.parseInt(getMsg("Nouveau jour de l'étape : "));
                    Integer nouvmois = Integer.parseInt(getMsg("Nouveau mois de l'étape: "));
                    Integer nouvannee = Integer.parseInt(getMsg("Nouvelle année de l'étape : "));
                    LocalDate nouvDateEtape = LocalDate.of(nouvjour, nouvmois, nouvannee);
                    et.setDateEtape(nouvDateEtape);
                    break;
                case "2":
                    return et;
                default:
                    displayMsg("Veuillez entrer un choix valide");
            }
        } while (true);
    }

    @Override
    public Integer read() {
        String num = getMsg("Numéro de l'étape : ");
        int nume = Integer.parseInt(num);
        return nume;
    }

    @Override
    public void affAll(List<Etape> let) {
        int i = 0;
        for(Etape et : let){
            displayMsg((++i)+") "+et.toString());
        }
    }
}
