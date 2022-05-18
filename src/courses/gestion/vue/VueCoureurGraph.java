package courses.gestion.vue;

import courses.metier.Coureur;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VueCoureurGraph extends VueCommuneGraph implements VueCoureurInterface {


    @Override
    public Coureur create() {

        JTextField tfmat = new JTextField();
        JTextField tfnom = new JTextField();
        JTextField tfprenom = new JTextField();
        JTextField tfjour = new JTextField();
        JTextField tfmois = new JTextField();
        JTextField tfannee = new JTextField();
        JTextField tfnat = new JTextField();


        Object[] message = {
                "matricule: ", tfmat,
                "nom: ", tfnom,
                "prénom: ", tfprenom,
                "jour: ", tfjour,
                "mois: ", tfmois,
                "année: ", tfannee,
                "nationalité: ", tfnat,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouveau coureur", JOptionPane.DEFAULT_OPTION);
        String matricule = tfmat.getText().toString();
        String nom = tfnom.getText().toString();
        String prenom = tfprenom.getText().toString();
        Integer jour = Integer.parseInt(tfjour.getText().toString());
        Integer mois = Integer.parseInt(tfmois.getText().toString());
        Integer annee = Integer.parseInt(tfannee.getText().toString());
        LocalDate dateNaiss = LocalDate.of(jour, mois, annee);
        String nationalite = tfnat.getText().toString();

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
        StringBuffer sb = new StringBuffer(200);

        for(Coureur co : lcour) sb.append((++i)+") "+co+"\n");
        displayMsg(sb.toString());
    }

    @Override
    public void affLobj(List lobj) {
        int i = 0;
        for (Object o : lobj) {
            displayMsg((++i) + ") " + o.toString());
        }
    }
}
