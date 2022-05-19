package courses.gestion.vue;

import courses.metier.Course;
import courses.metier.Etape;
import courses.metier.Ville;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VueEtapeGraph extends VueCommuneGraph implements VueEtapeInterface {


    @Override
    public Etape create() {

        JTextField tfnum = new JTextField();
        JTextField tfdesc = new JTextField();
        JTextField tfjour = new JTextField();
        JTextField tfmois = new JTextField();
        JTextField tfannee = new JTextField();
        JTextField tfkm = new JTextField();
        JTextField tfidcourse = new JTextField();
        JTextField tfvilleD = new JTextField();
        JTextField tfvilleA = new JTextField();

        Object[] message = {
                "numéro: ", tfnum,
                "description: ", tfdesc,
                "jour: ", tfjour,
                "mois: ", tfmois,
                "année: ", tfannee,
                "kilomètrage: ", tfkm,
                "id course: ", tfidcourse,
                "ville départ: ", tfvilleD,
                "ville arrivée: ", tfvilleA,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouvelle étape", JOptionPane.DEFAULT_OPTION);

        Integer num = Integer.parseInt(tfnum.getText().toString());
        String desc = tfdesc.getText().toString();
        Integer jour = Integer.parseInt(tfjour.getText().toString());
        Integer mois = Integer.parseInt(tfmois.getText().toString());
        Integer annee = Integer.parseInt(tfannee.getText().toString());
        LocalDate dateEtape= LocalDate.of(jour, mois, annee);
        Integer km = Integer.parseInt(tfkm.getText().toString());
        Integer idc = Integer.parseInt(tfidcourse.getText().toString());
        Course co = new Course(idc);
        String villed = tfvilleD.getText().toString();
        Ville vD = new Ville(villed);
        String villea = tfvilleA.getText().toString();
        Ville vA = new Ville(villea);

        Etape e = new Etape(num, desc, dateEtape, km, co, vD, vA);

        return e;
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
        String id = getMsg("Identifiant de l'étape : ");
        int ident = Integer.parseInt(id);
        return ident;
    }

    @Override
    public void affAll(List<Etape> let) {
        int i = 0;
        StringBuffer sb = new StringBuffer(200);

        for(Etape e: let) sb.append((++i)+") "+e+"\n");
        displayMsg(sb.toString());
    }
}
