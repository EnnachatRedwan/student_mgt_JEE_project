package org.isga.project_jee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.isga.project_jee.model.Student;
import org.isga.project_jee.services.StudentService;

import java.io.IOException;
import java.util.List;


@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    private final StudentService studentService;

    public StudentsServlet() {
        this.studentService = new StudentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            List<Student> students = studentService.getStudents();
            req.setAttribute("students", students);
            req.getRequestDispatcher("/students/listStudents.jsp").forward(req, resp);
        } else if (action.equals("delete")) {
            int studentId = Integer.parseInt(req.getParameter("id"));
            studentService.deleteStudent(studentId);
            resp.sendRedirect("students");
        } else if (action.equals("edit")) {
            int studentId = Integer.parseInt(req.getParameter("id"));
            Student student = studentService.getStudentById(studentId);
            req.setAttribute("student", student);
            req.getRequestDispatcher("/students/editStudent.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String address = req.getParameter("address");
        String tel = req.getParameter("tel");

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            Student student = new Student(firstName, lastName, address, tel);
            studentService.addStudent(student);
        } else {
            int id = Integer.parseInt(idParam);
            Student student = new Student(id, firstName, lastName, address, tel);
            studentService.updateStudent(student);
        }

        resp.sendRedirect("students");
    }
}
