package courses.gestion.vue;

import courses.metier.Coureur;

import java.time.LocalDate;
import java.util.List;

public class VueCoureur extends VueCommune implements VueCoureurInterface{

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
    public void display(Coureur obj) {

    }

    @Override
    public Coureur update(Coureur obj) {
        return null;
    }

    @Override
    public Integer read() {
        return null;
    }

    @Override
    public void affAll(List<Coureur> lcour) {
        int i = 0;
        for(Coureur c : lcour){
            displayMsg((++i)+") "+c.toString());
        }
    }
}
