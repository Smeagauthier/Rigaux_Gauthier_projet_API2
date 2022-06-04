package courses.metier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Course {

    private static int idCourseAct = 0;
    private int idCourse;
    private String nom;
    private LocalDate dateDebut, dateFin;
    private int kmTotal;
    private double priceMoney;
    private List<Etape> listeEtape = new ArrayList<>();
    private List<Classement> listeCla = new ArrayList<>();

    public Course() {
    }

    public Course(int idCourse) {
        this.idCourse = idCourse;
    }

    public Course(String nom, LocalDate dateDebut, LocalDate dateFin, int kmTotal, double priceMoney, List<Etape> listeEtape) {
        this.idCourse = ++idCourseAct;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kmTotal = kmTotal;
        this.priceMoney = priceMoney;
        this.listeEtape = listeEtape;
    }

    public Course(String nom, LocalDate dateDebut, LocalDate dateFin, int kmTotal, double priceMoney) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kmTotal = kmTotal;
        this.priceMoney = priceMoney;
    }

    public Course(int idCourse, String nom, LocalDate dateDebut, LocalDate dateFin, int kmTotal, double priceMoney) {
        this.idCourse = idCourse;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kmTotal = kmTotal;
        this.priceMoney = priceMoney;
    }


    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getKmTotal() {
        return kmTotal;
    }

    public void setKmTotal(int kmTotal) {
        this.kmTotal = kmTotal;
    }

    public double getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(double priceMoney) {
        this.priceMoney = priceMoney;
    }

    public List<Etape> getListeEtape() {
        return listeEtape;
    }

    public void setListeEtape(List<Etape> listeEtape) {
        this.listeEtape = listeEtape;
    }

    public List<Classement> getListeCla() {
        return listeCla;
    }

    public void setListeCla(List<Classement> listeCla) {
        this.listeCla = listeCla;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(nom, course.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    @Override
    public String toString() {
        return "ID : " + idCourse + "\t Nom : " + nom + "\t Date de début : " + dateDebut + "\t Date de fin : " + dateFin + "\n Kilomètres totaux : " + kmTotal + "\t Cash prize : " + priceMoney +"€";
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    public List<Classement> listeCoureursPlaceGain() {
        List<Classement> listeCourPlGain = new ArrayList<>();
        for (Classement c : listeCla) {
            listeCourPlGain.add(new Classement(c.getCoureur(), c.getPlace(), c.getGain()));
        }

        return listeCourPlGain;
    }

    public double gainTotal() {
        double tot = 0;
        for (Classement c : listeCla) {
            tot += c.getGain();
        }
        return tot;
    }

    public Coureur vainqueur() {
        for (Classement c : listeCla) {
            if (c.getPlace() == 1) {
                return c.getCoureur();
            }
        }
        return null;
    }

    public boolean addCoureur(Coureur c) {
        Classement addCour = new Classement(c);
        if (listeCla.contains(addCour)) {
            return false;
        }

        listeCla.add(addCour);
        return true;
    }

    public boolean suppCoureur(Coureur c) {
        Classement suppCour = new Classement(c);
        if (listeCla.contains(suppCour)) {
            listeCla.remove(suppCour);
            return true;
        }
        return false;
    }

    public boolean resultat(Coureur c, int place, double gain) {
        Classement clas = new Classement(c, place, gain);
        int position = listeCla.indexOf(clas);

        if (position != -1) {
            listeCla.get(position).setCoureur(c);
            listeCla.get(position).setPlace(place);
            listeCla.get(position).setGain(gain);
            return true;
        }
        return false;

    }

    public boolean modif(Coureur c, int place, double gain) {
        Classement clModif = new Classement(c, place, gain);
        int position = listeCla.indexOf(clModif);

        if (position != -1) {
            listeCla.get(position).setPlace(place);
            listeCla.get(position).setGain(gain);
            return true;
        }
        return false;
    }

    public boolean addEtape(Etape e) {
        if (listeEtape.contains(e)) {
            return false;
        }
        Etape et = new Etape(e);
        listeEtape.add(et);
        return true;
    }

    public boolean suppEtape(Etape e) {
        if (listeEtape.contains(e)) {
            listeEtape.remove(e);
            return true;
        }
        return false;
    }

    public List<Ville> listeVilles() {
        List<Ville> newListeVille = new ArrayList<>();
        for (Etape e : listeEtape) {
            if (!newListeVille.contains(e.getVilleDepart())) {
                newListeVille.add(e.getVilleArrivee());
            }
            if (!newListeVille.contains(e.getVilleArrivee())) {
                newListeVille.add(e.getVilleArrivee());
            }
        }
        if (newListeVille.isEmpty()) {
            return null;
        }
        return newListeVille;
    }

    public boolean classementComplet() {
        for (Classement c : listeCla) {
            if (c.getPlace() == 0) {
                return false;
            }
        }
        return true;
    }

}
