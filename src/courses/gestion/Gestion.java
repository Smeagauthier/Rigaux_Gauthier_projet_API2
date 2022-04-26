package courses.gestion;

import courses.metier.Coureur;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Gestion {

    private Scanner sc = new Scanner(System.in);
    private Connection dbConnect;

    public void gestion() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("      ---- Connexion établie ----");
        System.out.println("         --- Menu Principal ---");
        System.out.println("\n");

        do {
            System.out.println("1. Menu des coureurs\n2. Fin");
            System.out.print("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    menuCoureur();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);

    }


    public void menuCoureur() {
        System.out.println("\n       **** Gestion des coureurs ****");
        do {
            System.out.println("\n      ***** Menu Principal *****");
            System.out.println("1. Ajout\n2. Modification\n3. Affichage des coureurs\n4. Suppression\n5. Recherche\n6. Fin");
            System.out.print("choix : ");
            int ch = sc.nextInt();
            sc.skip("\n");
            switch (ch) {
                case 1:
                    ajout();
                    break;
                case 2:
                    modification();
                    break;
                case 3:
                    affichage();
                    break;
                case 4:
                    suppression();
                    break;
                case 5:
                    recherche();
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("choix invalide recommencez ");
            }
        } while (true);
    }

    public void ajout() {
        System.out.print("Matricule : ");
        String MATRICULE = sc.nextLine();
        System.out.print("Nom :");
        String NOM = sc.nextLine();
        System.out.print("Prénom :");
        String PRENOM = sc.nextLine();
        System.out.println("Date de naissance: ");
        System.out.println("Jour : ");
        int j = sc.nextInt();
        System.out.println("Mois : ");
        int m = sc.nextInt();
        System.out.println("Année : ");
        int a = sc.nextInt();
        LocalDate DATENAISS = LocalDate.of(a, m, j);
        sc.nextLine();
        System.out.print("Nationalité : ");
        String NATIONALITE = sc.nextLine();


        String query1 = "insert into APICOUREUR(MATRICULE,NOM,PRENOM,DATENAISS,NATIONALITE) values(?,?,?,?,?)";
        String query2 = "select IDCOUREUR from APICOUREUR where MATRICULE=?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)
        ) {
            pstm1.setString(1, MATRICULE);
            pstm1.setString(2, NOM);
            pstm1.setString(3, PRENOM);
            pstm1.setDate(4, Date.valueOf(DATENAISS));
            pstm1.setString(5, NATIONALITE);

            int n = pstm1.executeUpdate();
            System.out.println(n + " ligne insérée");
            if (n == 1) {
                pstm2.setString(1, MATRICULE);
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int IDCOUREUR = rs.getInt(1);
                    System.out.println("idCoureur = " + IDCOUREUR);
                } else System.out.println("record introuvable");
                rs.close();
            }

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
        System.out.println();
    }

    public void suppression() {
        System.out.print("Saisissez le matricule du coureur à supprimer :  ");
        String matrech = sc.nextLine();
        String query1 = "delete from APICOUREUR where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query1);) {
            pstm.setString(1, matrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne supprimée");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    public void modification() {
        System.out.print("Saisissez le matricule du coureur à modifier : ");
        String matrech = sc.nextLine();
        System.out.print("Nouvelle nationalité : ");
        String nnationalite = sc.nextLine();
        String query = "update APICOUREUR set NATIONALITE=? where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, nnationalite);
            pstm.setString(2, matrech);
            int n = pstm.executeUpdate();
            if (n != 0) System.out.println(n + "ligne mise à jour");
            else System.out.println("record introuvable");

        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
    }

    public void recherche() {
        System.out.print("Saisissez le matricule du coureur recherché :  ");
        String matrech= sc.nextLine();
        String query = "select * from APICOUREUR where MATRICULE = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, matrech);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int IDCOUREUR = rs.getInt("IDCOUREUR");
                String MATRICULE = rs.getString("MATRICULE");
                String NOM = rs.getString("NOM");
                String PRENOM = rs.getString("PRENOM");
                Date DATENAISS = rs.getDate("DATENAISS");
                String NATIONALITE = rs.getString("NATIONALITE");
                System.out.printf("%d %s %s %s %s %s\n", IDCOUREUR, MATRICULE, NOM, PRENOM, DATENAISS, NATIONALITE);
            } else System.out.println("record introuvable");
            rs.close();
        } catch (SQLException e) {
            System.out.println("erreur sql :" + e);
        }
        System.out.println();
    }

    public void affichage() {
        System.out.println("\n       *****Liste des coureurs enregistrés*****");
        try (Statement stmt = dbConnect.createStatement(); ResultSet rs = stmt.executeQuery("select * from APICOUREUR")) {
            while (rs.next()) {
                int id = rs.getInt("IDCOUREUR");
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nationalite = rs.getString("NATIONALITE");
                String matricule = rs.getString("MATRICULE");
                System.out.println(id + ". " + nom + " " + prenom + " (" + nationalite+")    \t\tmatricule: "+matricule);
            }

        } catch (SQLException e) {
            System.out.println("Erreur sql : " + e);
        }
        System.out.println();

    }

    public static void main(String[] args) {
        Gestion g = new Gestion();
        g.gestion();
    }

}
