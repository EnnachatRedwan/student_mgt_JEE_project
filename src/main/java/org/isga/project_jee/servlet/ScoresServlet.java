package org.isga.project_jee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.isga.project_jee.model.Score;
import org.isga.project_jee.model.Subject;
import org.isga.project_jee.services.ScoreService;
import org.isga.project_jee.services.SubjectService;

import java.io.IOException;
import java.util.List;

@WebServlet("/scores")
public class ScoresServlet extends HttpServlet {
    private final ScoreService scoreService;
    private final SubjectService subjectService;

    public ScoresServlet() {
        scoreService = new ScoreService();
        subjectService = new SubjectService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            List<Score> scores = scoreService.getScores(studentId);
            double averageScore = scoreService.calculateAverageScore(studentId);
            req.setAttribute("scores", scores);
            req.setAttribute("averageScore", averageScore);
            req.getRequestDispatcher("/scores/listScores.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            int studentId = Integer.parseInt(req.getParameter("studentId"));
            List<Subject> subjects = subjectService.getSubjects();
            req.setAttribute("subjects", subjects);
            req.setAttribute("studentId", studentId);
            req.getRequestDispatcher("/scores/addScore.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            int scoreId = Integer.parseInt(req.getParameter("id"));
            Score score = scoreService.getScore(scoreId);
            req.setAttribute("score", score);
            req.getRequestDispatcher("/scores/editScore.jsp").forward(req, resp);
        } else if ("delete".equals(action)) {
            int scoreId = Integer.parseInt(req.getParameter("id"));
            Score scoreToDelete = scoreService.getScore(scoreId);
            scoreService.deleteScore(scoreToDelete.getId());
            resp.sendRedirect("scores?studentId=" + scoreToDelete.getStudentId());
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        int studentId;
        int subjectId;
        double score = Double.parseDouble(req.getParameter("score"));
        if (idParam == null || idParam.isEmpty()) {
            studentId = Integer.parseInt(req.getParameter("studentId"));
            subjectId = Integer.parseInt(req.getParameter("subjectId"));
            Score newScore = new Score(studentId, subjectId, score);
            scoreService.addScore(newScore);
        } else {
            int id = Integer.parseInt(idParam);
            Score scoreToUpdate = scoreService.getScore(id);
            studentId = scoreToUpdate.getStudentId();
            subjectId = scoreToUpdate.getSubjectId();
            scoreToUpdate.setScore(score);
            scoreService.updateScore(scoreToUpdate);
        }
        resp.sendRedirect("scores?studentId=" + studentId + "&subjectId=" + subjectId);
    }
}
