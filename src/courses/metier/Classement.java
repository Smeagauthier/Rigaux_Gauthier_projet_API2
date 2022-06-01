package courses.metier;

import java.util.Objects;

public class Classement {

    private Coureur coureur;
    private int place;
    private double gain;
    private int idcourse;

    public Classement(Coureur c) {
        this.coureur = c;
        this.place = 0;
        this.gain = 0;
    }

    public Classement(Coureur coureur, int place, double gain) {
        this.coureur = coureur;
        this.place = place;
        this.gain = gain;
    }

    public Coureur getCoureur() {
        return coureur;
    }

    public void setCoureur(Coureur coureur) {
        this.coureur = coureur;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classement that = (Classement) o;
        return Objects.equals(coureur, that.coureur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coureur);
    }

    @Override
    public String toString() {
        return "coureur=" + coureur + ", place=" + place + ", gain=" + gain ;
    }
}
