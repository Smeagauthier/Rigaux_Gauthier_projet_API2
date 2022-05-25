package courses.gestion.vue;

import courses.metier.Course;

import java.util.List;

public interface VueCourseInterface extends VueInterface<Course, Integer>{
    void affLobj(List lobj);
}
