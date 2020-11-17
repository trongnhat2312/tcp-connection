package model;

import java.util.Arrays;

public class Answer {
    public static final long serialVersionUID = 2L;
    public Student student;
    public Object[] answers;
    public boolean[] isRights;
    public boolean alreadyRegistration;

    public Answer(Student student) {
        this.student = student;
    }

    public Answer(Student student, boolean alreadyRegistration) {
        this.student = student;
        this.alreadyRegistration = alreadyRegistration;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "student=" + student +
                ", isRights=" + Arrays.toString(isRights) +
                ", alreadyRegistration=" + alreadyRegistration +
                '}';
    }
}
