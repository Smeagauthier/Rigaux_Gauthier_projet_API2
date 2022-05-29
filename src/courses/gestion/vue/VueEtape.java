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
        Integer km = Integer.parseInt(getMsg("Kilomètres : "));
        Integer idcourse = Integer.parseInt(getMsg("Id de la course : "));
        Course co = new Course(idcourse);
        String villeDep = getMsg("Ville de départ : ");
        Ville villeD = new Ville(villeDep);
        String villeArr = getMsg("Ville d'arrivée : ");
        Ville villeA = new Ville(villeArr);

        Etape newEtape = new Etape(num, desc, dateEtape, km, co, villeD, villeA);
        return newEtape;
    }

    @Override
    public void display(Etape et) {
        displayMsg(et.toString());
    }

    @Override
    public Etape update(Etape et) {
        do {
            String ch = getMsg("\n1. Changement date de l'étape\n2. retour");
            switch (ch) {
                case "1":
                    LocalDate nouvDateEtape = readDate("Nouvelle date de l'étape : ");
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
        int numet = Integer.parseInt(num);
        return numet;
    }

    @Override
    public void affAll(List<Etape> letape) {
        int i = 0;
        for(Etape et : letape){
            displayMsg((++i)+") "+et.toString());
        }
    }
}
