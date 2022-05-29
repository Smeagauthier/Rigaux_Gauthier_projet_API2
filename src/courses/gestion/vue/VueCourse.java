package courses.gestion.vue;

import courses.metier.Classement;
import courses.metier.Course;

import java.time.LocalDate;
import java.util.List;

public class VueCourse extends VueCommune implements VueCourseInterface {

    @Override
    public Course create() {
        String nom = getMsg("Nom de la course : ");
        Integer jourdebut = Integer.parseInt(getMsg("Jour de début : "));
        Integer moisdebut = Integer.parseInt(getMsg("Mois de début : "));
        Integer anndebut = Integer.parseInt(getMsg("Année de début : "));
        LocalDate dateDebut = LocalDate.of(jourdebut, moisdebut, anndebut);
        Integer jourfin = Integer.parseInt(getMsg("Jour de fin : "));
        Integer moisfin = Integer.parseInt(getMsg("Mois de fin : "));
        Integer annfin = Integer.parseInt(getMsg("Année de fin : "));
        LocalDate dateFin = LocalDate.of(jourfin, moisfin, annfin);
        Integer kmtot = Integer.parseInt(getMsg("Kilomètres totaux : "));
        Double pricem = Double.parseDouble(getMsg("Price money : "));

        Course cou = new Course(nom, dateDebut, dateFin, kmtot, pricem);
        return cou;
    }

    @Override
    public void display(Course cou) {
        displayMsg(cou.toString());
        for (Classement cla : cou.getListeCla()) {
            displayMsg(cla.toString());
        }
    }

    @Override
    public Course update(Course cour) {
        do {
            String ch = getMsg("\n1. Changement date début\n2. Changement date de fin\n3. Changement de cash price\n4. fin");
            switch (ch) {
                case "1":
                    LocalDate nouvDateDebut = readDate("Nouvelle date de début: ");
                    cour.setDateDebut(nouvDateDebut);
                    break;
                case "2":
                    LocalDate nouvDateFin = readDate("Nouvelle date de fin: ");
                    cour.setDateDebut(nouvDateFin);
                    break;
                case "3":
                    Double nouvpricemoney = Double.parseDouble(getMsg("Nouveau price money : "));
                    cour.setPriceMoney(nouvpricemoney);
                    break;
                case "4":
                    return cour;
                default:
                    displayMsg("Entrez un choix valide");
            }
        } while (true);
    }

    @Override
    public Integer read() {
        String id = getMsg("Identifiant de la course : ");
        int ident = Integer.parseInt(id);
        return ident;
    }

    @Override
    public void affAll(List<Course> lobj) {
        int i = 0;
        for(Course co : lobj){
            displayMsg((++i)+") "+co.toString());
        }
    }

    @Override
    public void affLobj(List lobj) {
        int i = 0;
        for (Object o : lobj) {
            displayMsg((++i) + ") " + o.toString());
        }
    }
}
