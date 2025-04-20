package org.isga.project_jee.dao;

import org.isga.project_jee.model.Score;
import org.isga.project_jee.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {

    private final ScoreDao scoreDao;

    public SubjectDao() {
        scoreDao = new ScoreDao();
    }


    public void addSubject(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO SUBJECT (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Subject> getSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM SUBJECT";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                Subject subject = new Subject(rs.getInt("id"), rs.getString("name"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjects;
    }

    public Subject getSubject(int id) {
        Subject subject = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM SUBJECT WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subject = new Subject(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }

    public void updateSubject(Subject subject) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE SUBJECT SET name = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSubject(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Score> scores = scoreDao.getScoresBySubject(id);
            for (Score score : scores) {
                scoreDao.deleteScore(score.getId());
            }
            String sql = "DELETE FROM SUBJECT WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
