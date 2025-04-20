package org.isga.project_jee.services;

import org.isga.project_jee.dao.StudentDao;
import org.isga.project_jee.model.Student;

import java.util.List;

public class StudentService {
    StudentDao studentDao;

    public StudentService() {
        this.studentDao = new StudentDao();
    }

    public List<Student> getStudents() {
        return studentDao.getStudents();
    }

    public Student getStudentById(int id) {
        return studentDao.getStudentById(id);
    }

    public void addStudent(Student student) {
        studentDao.addStudent(student.getFirstName(), student.getLastName(), student.getAddress(), student.getTel());
    }

    public void updateStudent(Student student) {
        studentDao.updateStudent(student.getId(), student.getFirstName(), student.getLastName(), student.getAddress(), student.getTel());
    }

    public void deleteStudent(int id) {
        studentDao.deleteStudent(id);
    }
}
