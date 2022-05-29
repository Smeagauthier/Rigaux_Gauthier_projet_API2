package courses.gestion.vue;

import java.time.LocalDate;
import java.util.Scanner;

public class VueCommune implements VueCommuneInterface {

    private Scanner sc = new Scanner(System.in);

    @Override
    public int menu(String[] options) {
        do {
            StringBuilder sb = new StringBuilder(50);
            int i = 0;
            for (String option : options) sb.append((++i) + "." + option + "\n");
            sb.append("choix:");
            System.out.println(sb.toString());
            String chs = sc.nextLine();
            int ch = Integer.parseInt(chs);
            if (ch >= 1 && ch <= options.length) {
                return ch;
            }
            System.out.println("choix invalide");
        } while (true);
    }

    @Override
    public void displayMsg(String msg) {
        System.out.println(msg);
    }

    @Override
    public String getMsg(String invite) {
        displayMsg(invite);
        String msg = sc.nextLine();
        return msg;
    }

    public LocalDate readDate(String msg) {
        System.out.print(msg + "(format : jj mm aa (0 0 0 si inconnue)) : ");
        do {
            try {
                int jour = sc.nextInt();
                int mois = sc.nextInt();
                int annee = sc.nextInt();
                sc.nextLine();
                LocalDate d;
                if (jour == 0 && mois == 0 && annee == 0) d = null;
                else d = LocalDate.of(annee, mois, jour);
                return d;
            } catch (Exception e) {
                affMsg("valeurs incorrectes");
            }
        } while (true);
    }

    public void affMsg(String msg) {
        System.out.println(msg);
    }

}
