package courses.gestion.vue;

import courses.metier.Course;
import courses.metier.Ville;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VueVilleGraph extends VueCommuneGraph implements VueVilleInterface{


    @Override
    public Ville create() {

        JTextField tfnom = new JTextField();
        JTextField tflatitude = new JTextField();
        JTextField tflongitude = new JTextField();
        JTextField tfpays = new JTextField();

        Object[] message = {
                "nom: ", tfnom,
                "latitude: ", tflatitude,
                "longitude: ", tflongitude,
                "pays: ", tfpays,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouvelle ville", JOptionPane.DEFAULT_OPTION);
        String nom = tfnom.getText().toString();
        Double latitude = Double.parseDouble(tflatitude.getText().toString());
        Double longitude = Double.parseDouble(tflongitude.getText().toString());
        String pays = tfnom.getText().toString();

        Ville vil = new Ville(nom, pays, longitude, latitude);

        return vil;
    }

    @Override
    public void display(Ville vi) {
        displayMsg(vi.toString());
    }

    @Override
    public Ville update(Ville vi) {
        do {
            String ch = getMsg("1. Changement de ville\n2. Fin");
            switch (ch) {
                case "1":
                    String nouvVille = getMsg("Nouvelle ville : ");
                    vi.setNom(nouvVille);

                case "2":
                    return vi;
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
        StringBuffer sb = new StringBuffer(200);

        for(Ville v : lvil) sb.append((++i)+") "+v+"\n");
        displayMsg(sb.toString());
    }
}
