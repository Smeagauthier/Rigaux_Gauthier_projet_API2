package courses.metier;

import java.util.Objects;

//TABLE PAS CONNEXE A COURSE DONC CREER UN OBJET (mdc)

public class Ville {

    private static int idVilleAct = 0;
    private int idVille;
    private String nom, pays;
    private double latitude, longitude;

    public Ville() {
    }

    public Ville(int idVille, String nom, String pays, double latitude, double longitude) {
        this.idVille = idVille;
        this.nom = nom;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Ville(String nom, String pays, double latitude, double longitude) {
        this.idVille = idVilleAct++;
        this.nom = nom;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return Objects.equals(latitude, ville.latitude) && Objects.equals(longitude, ville.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Ville{" +
                "idVille=" + idVille +
                ", nom='" + nom + '\'' +
                ", pays='" + pays + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
