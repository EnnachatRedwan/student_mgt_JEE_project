package org.isga.project_jee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.isga.project_jee.model.Subject;
import org.isga.project_jee.services.SubjectService;

import java.io.IOException;
import java.util.List;

@WebServlet("/subjects")
public class SubjectsServlet extends HttpServlet {
    private final SubjectService subjectService;

    public SubjectsServlet() {
        subjectService = new SubjectService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            List<Subject> subjects = subjectService.getSubjects();
            req.setAttribute("subjects",subjects);
            req.getRequestDispatcher("/subjects/listSubjects.jsp").forward(req, resp);
        } else if (action.equals("delete")) {
            int subjectId = Integer.parseInt(req.getParameter("id"));
            subjectService.deleteSubject(subjectId);
            resp.sendRedirect("subjects");
        } else if (action.equals("edit")) {
            int subjectId = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("subject", subjectService.getSubject(subjectId));
            req.getRequestDispatcher("/subjects/editSubject.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        if(idParam == null || idParam.isEmpty()) {
            Subject subject = new Subject(name);
            subjectService.addSubject(subject);
        } else {
            int id = Integer.parseInt(idParam);
            Subject subject = new Subject(id, name);
            subjectService.updateSubject(subject);
        }
        resp.sendRedirect("subjects");
    }
}
