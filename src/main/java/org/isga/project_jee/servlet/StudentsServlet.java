package org.isga.project_jee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.isga.project_jee.dao.StudentDao;
import org.isga.project_jee.model.Student;

import java.io.IOException;
import java.util.List;


@WebServlet("/students")
public class StudentsServlet extends HttpServlet {
    private StudentDao studentDAO = new StudentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            // View students
            List<Student> students = studentDAO.getStudents();
            req.setAttribute("students", students);
            req.getRequestDispatcher("listStudents.jsp").forward(req, resp);
        } else if (action.equals("delete")) {
            // Delete student
            int studentId = Integer.parseInt(req.getParameter("id"));
            studentDAO.deleteStudent(studentId);
            resp.sendRedirect("students");
        } else if (action.equals("edit")) {
            // Edit student
            int studentId = Integer.parseInt(req.getParameter("id"));
            Student student = studentDAO.getStudentById(studentId);
            req.setAttribute("student", student);
            req.getRequestDispatcher("editStudent.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String address = req.getParameter("address");
        String tel = req.getParameter("tel");

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // Add new student
            studentDAO.addStudent(firstName, lastName, address, tel);
        } else {
            // Update existing student
            int id = Integer.parseInt(idParam);
            studentDAO.updateStudent(id, firstName, lastName, address, tel);
        }

        resp.sendRedirect("students");
    }
}
