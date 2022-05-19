package courses.gestion.presenter;

import courses.gestion.modele.DAOCourse;
import courses.gestion.vue.VueCourseInterface;

public class PresenterCourse {

    private DAOCourse mdc;
    private VueCourseInterface vuec;

    public PresenterCourse(DAOCourse mdc, VueCourseInterface vuec) {
        this.mdc = mdc;
        this.vuec = vuec;
    }


}
