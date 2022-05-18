package courses.gestion.vue;

import courses.metier.Classement;
import courses.metier.Coureur;
import courses.metier.Course;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class VueCourseGraph extends VueCommuneGraph implements VueCourseInterface {


    @Override
    public Course create() {

        JTextField tfnom = new JTextField();
        JTextField tfjourdebut = new JTextField();
        JTextField tfmoisdebut = new JTextField();
        JTextField tfanneedebut = new JTextField();
        JTextField tfjourfin = new JTextField();
        JTextField tfmoisfin = new JTextField();
        JTextField tfanneefin = new JTextField();
        JTextField tfkm = new JTextField();
        JTextField tfpricemoney = new JTextField();


        Object[] message = {
                "nom: ", tfnom,
                "jour de début: ", tfjourdebut,
                "mois de début: ", tfmoisdebut,
                "année de début: ", tfanneedebut,
                "jour de fin: ", tfjourfin,
                "mois de fin: ", tfmoisfin,
                "année de fin: ", tfanneefin,
                "km totaux: ", tfkm,
                "price money: ", tfpricemoney,

        };

        int option = JOptionPane.showConfirmDialog(null, message, "nouvelle course", JOptionPane.DEFAULT_OPTION);
        String nom = tfnom.getText().toString();
        Integer jourdebut = Integer.parseInt(tfjourdebut.getText().toString());
        Integer moisdebut = Integer.parseInt(tfmoisdebut.getText().toString());
        Integer anneedebut = Integer.parseInt(tfanneedebut.getText().toString());
        LocalDate dateDebut = LocalDate.of(jourdebut, moisdebut, anneedebut);
        Integer jourfin = Integer.parseInt(tfjourfin.getText().toString());
        Integer moisfin = Integer.parseInt(tfmoisfin.getText().toString());
        Integer anneefin = Integer.parseInt(tfanneefin.getText().toString());
        LocalDate dateFin= LocalDate.of(jourfin, moisfin, anneefin);
        Integer km = Integer.parseInt(tfkm.getText().toString());
        Double pricemoney = Double.parseDouble(tfpricemoney.getText().toString());

        Course course = new Course(nom, dateDebut, dateFin, km, pricemoney);
        return course;
    }

    @Override
    public void display(Course cour) {
        int i=0;

        StringBuffer sb= new StringBuffer(200);
        sb.append(cour+"\n");
        for (Classement cl :cour.getListeCla()) {
            sb.append((++i)+"."+cl+"\n");
        }
        displayMsg(sb.toString());
    }

    @Override
    public Course update(Course cour) {

        do {
            String ch = getMsg("1. Changement date début\n2. Changement date de fin\n3. Changement de cash price\n4. fin");
            switch (ch) {
                case "1":
                    Integer nouvjourdebut = Integer.parseInt(getMsg("Nouveau jour de début : "));
                    Integer nouvmoisdebut = Integer.parseInt(getMsg("Nouveau mois de début: "));
                    Integer nouvanneedebut = Integer.parseInt(getMsg("Nouvelle année de début : "));
                    LocalDate nouvDateDebut = LocalDate.of(nouvjourdebut, nouvmoisdebut, nouvanneedebut);
                    cour.setDateDebut(nouvDateDebut);
                    break;
                case "2":
                    Integer nouvjourfin = Integer.parseInt(getMsg("Nouveau jour de fin: "));
                    Integer nouvmoisfin = Integer.parseInt(getMsg("Nouveau mois de fin: "));
                    Integer nouvanneefin = Integer.parseInt(getMsg("Nouvelle année de fin : "));
                    LocalDate nouvDateFin = LocalDate.of(nouvjourfin, nouvmoisfin, nouvanneefin);
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
    public void affAll(List<Course> lcour) {
        int i = 0;
        StringBuffer sb = new StringBuffer(200);

        for(Course cou : lcour) sb.append((++i)+") "+cou+"\n");
        displayMsg(sb.toString());
    }
}
