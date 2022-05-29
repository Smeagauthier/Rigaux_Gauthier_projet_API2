package courses.metier;

import java.time.LocalDate;
import java.util.Objects;

public class Etape {

    private static int idEtapeAct = 0;
    private int idEtape;
    private int numero;
    private String description;
    private LocalDate dateEtape;
    private int km;
    private Course course;
    private Ville villeDepart;
    private Ville villeArrivee;




    public Etape(int idEtape, int numero, String description, LocalDate dateEtape, int km, Course course, Ville villeDepart, Ville villeArrivee) {
        this.idEtape = idEtape;
        this.numero = numero;
        this.description = description;
        this.dateEtape = dateEtape;
        this.km = km;
        this.course = course;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
    }

    public Etape(int numero, String description, LocalDate dateEtape, int km, Course course, Ville villeDepart, Ville villeArrivee) {
        this.idEtape = idEtapeAct++;
        this.numero = numero;
        this.description = description;
        this.dateEtape = dateEtape;
        this.km = km;
        this.course = course;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
    }

    public Etape(Ville villeDepart, Ville villeArrivee) {

        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
    }


    public Etape(Etape e) {
        this.numero = e.numero;
        this.description = e.description;
        this.dateEtape = e.dateEtape;
        this.km = e.km;
        this.course = e.course;
        this.villeDepart = e.villeDepart;
        this.villeArrivee = e.villeArrivee;
    }

    public int getIdEtape() {
        return idEtape;
    }

    public void setIdEtape(int idEtape) {
        this.idEtape = idEtape;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateEtape() {
        return dateEtape;
    }

    public void setDateEtape(LocalDate dateEtape) {
        this.dateEtape = dateEtape;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Ville getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(Ville villeDepart) {
        this.villeDepart = villeDepart;
    }

    public Ville getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(Ville villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etape etape = (Etape) o;
        return numero == etape.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "ID : " + idEtape + "\t Numéro de l'étape : " + numero + "\t Description : " + description + "\t Date de l'étape : " + dateEtape + "\t Kilométrage : " + km + "\t Course : " + course + "\t Ville de départ : " + villeDepart + "\t Ville d'arrivée : " + villeArrivee ;
    }
}
