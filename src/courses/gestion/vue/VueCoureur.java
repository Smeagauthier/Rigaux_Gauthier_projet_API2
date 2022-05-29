package courses.gestion.vue;

import courses.metier.Coureur;

import java.io.BufferedInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VueCoureur extends VueCommune implements VueCoureurInterface {

    private static Scanner sc = new Scanner(System.in);

    @Override
    public Coureur create() {
        String matricule = getMsg("Matricule : ");
        String nom = getMsg("Nom : ");
        String prenom = getMsg("Prénom : ");
        String nationalite = getMsg("Nationalité : ");
        LocalDate dateNaiss = readDate("Date de naissance: ");

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
            sc.skip("\n");
            String ch = getMsg("1. Changement de nationalité \n2. Fin");
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
        String mat = getMsg("Matricule du coureur : ");
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
