package org.isga.project_jee.services;

import org.isga.project_jee.dao.SubjectDao;
import org.isga.project_jee.model.Subject;

import java.util.List;

public class SubjectService {
    private SubjectDao subjectDao;

    public SubjectService() {
        this.subjectDao = new SubjectDao();
    }

    public void addSubject(Subject subject) {
        subjectDao.addSubject(subject.getName());
    }

    public List<Subject> getSubjects() {
        return subjectDao.getSubjects();
    }

    public Subject getSubject(int id) {
        return subjectDao.getSubject(id);
    }

    public void updateSubject(Subject subject) {
        subjectDao.updateSubject(subject);
    }

    public void deleteSubject(int id) {
        subjectDao.deleteSubject(id);
    }

}
