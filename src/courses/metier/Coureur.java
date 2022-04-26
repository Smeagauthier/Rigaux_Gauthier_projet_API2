package courses.metier;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe métier de gestion d'un coureur
 *
 * @author Gauthier Rigaux
 *
 */

public class Coureur {

    /**
     * Générateur statique de l'id d'un coureur
     */
    private static int idCourAct = 0;

    /**
     * identifiant unique du coureur
     */
    private int idCoureur;

    /**
     * matricule unique du coureur
     */
    private String matricule;

    /**
     * nom du coureur
     */
    private String nom;

    /**
     * prénom du coureur
     */
    private String prenom;

    /**
     * nationalité du coureur
     */
    private String nationalite;

    /**
     * date de naissance du coureur
     */
    private LocalDate dateNaiss;

    /**
     * Constructeur par défaut
     */
    public Coureur() {
    }

    /**
     * Constructeur paramétré
     *
     * @param idCoureur identifiant unique du coureur affecté par la base de données
     * @param matricule matricule unique du coureur
     * @param nom nom du coureur
     * @param prenom prénom du coureur
     * @param nationalite nationalité du coureur
     * @param dateNaiss date de naissance du coureur
     */
    public Coureur(int idCoureur, String matricule, String nom, String prenom, String nationalite, LocalDate dateNaiss) {
        this.idCoureur = idCoureur;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaiss = dateNaiss;
    }

    /**
     * Constructeur paramétré
     *
     * @param matricule matricule unique du coureur
     * @param nom nom du coureur
     * @param prenom prénom du coureur
     * @param nationalite nationalité du coureur
     * @param dateNaiss date de naissance du coureur
     */
    public Coureur(String matricule, String nom, String prenom, String nationalite, LocalDate dateNaiss) {
        this.idCoureur = idCourAct++;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
        this.dateNaiss = dateNaiss;
    }

    /**
     * getter idCoureur
     *
     * @return identifiant du coureur
     */
    public int getIdCoureur() {
        return idCoureur;
    }

    /**
     * setter idCoureur
     *
     * @param idCoureur identifiant du coureur
     */
    public void setIdCoureur(int idCoureur) {
        this.idCoureur = idCoureur;
    }

    /**
     * getter matricule
     *
     * @return matricule du coureur
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * setter matricule
     *
     * @param matricule matricule du coureur
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * getter nom
     *
     * @return nom du coureur
     */
    public String getNom() {
        return nom;
    }

    /**
     * setter nom
     *
     * @param nom nom du coureur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * getter prenom
     *
     * @return prenom du coureur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * setter prenom
     *
     * @param prenom prenom du coureur
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * getter nationalite
     *
     * @return nationalite du coureur
     */
    public String getNationalite() {
        return nationalite;
    }

    /**
     * setter nationalite
     *
     * @param nationalite nationalite du coureur
     */
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    /**
     * getter dateNaiss
     *
     * @return date de naissance du coureur
     */
    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    /**
     * setter dateNaiss
     *
     * @param dateNaiss date de naissance du coureur
     */
    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    /**
     * méthode de comparaison basée sur le matricule
     *
     * @param o Objet
     * @return le résultat de la comparaison des matricules (vrai ou faux)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coureur coureur = (Coureur) o;
        return Objects.equals(matricule, coureur.matricule);
    }

    /**
     * méthode hashcode basée sur le matricule
     *
     * @return le matricule du client
     */
    @Override
    public int hashCode() {
        return Objects.hash(matricule);
    }

    /**
     * méthode toString
     *
     * @return informations complètes du coureur
     */
    @Override
    public String toString() {
        return "Coureur{" +
                "idCoureur=" + idCoureur +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", dateNaiss=" + dateNaiss +
                '}';
    }
}
