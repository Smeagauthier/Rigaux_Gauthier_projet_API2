package courses.gestion.vue;

import courses.metier.Coureur;

import java.time.LocalDate;
import java.util.List;

public class VueCoureur extends VueCommune implements VueCoureurInterface {

    @Override
    public Coureur create() {
        String matricule = getMsg("Matricule : ");
        String nom = getMsg("Nom : ");
        String prenom = getMsg("Prénom : ");
        String nationalite = getMsg("Nationalité : ");
        Integer jourNaiss = Integer.parseInt(getMsg("Jour de naissance : "));
        Integer moisNaiss = Integer.parseInt(getMsg("Mois de naissance : "));
        Integer annNaiss = Integer.parseInt(getMsg("Année de naissance : "));
        LocalDate dateNaiss = LocalDate.of(jourNaiss, moisNaiss, annNaiss);

        Coureur newCour = new Coureur(matricule, nom, prenom, nationalite, dateNaiss);

        return newCour;
    }

    @Override
    public void display(Coureur cour) {
        displayMsg(cour.toString());
    }

    @Override
    public Coureur update(Coureur cour) {
        do {
            String ch = getMsg("1. Changement de nationalité\n2. Fin");
            switch (ch) {
                case "1":
                    String nnat = getMsg("Nouvelle nationalité : ");
                    cour.setNationalite(nnat);
                    break;
                case "2":
                    return cour;
                default:
                    displayMsg("Veuillez entrer un choix valide");
            }
        } while (true);
    }

    @Override
    public String read() {
        String mat = getMsg("Matricule du client : ");
        return mat;
    }

    @Override
    public void affAll(List<Coureur> lcour) {
        int i = 0;
        for (Coureur c : lcour) {
            displayMsg((++i) + ") " + c.toString());
        }
    }

}
