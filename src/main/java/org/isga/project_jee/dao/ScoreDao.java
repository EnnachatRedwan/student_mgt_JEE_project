package org.isga.project_jee.dao;

import org.isga.project_jee.model.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDao {
    public void addScore(int studentId, int subjectId, double score) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO score (student_id, subject_id, score) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setDouble(3, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Score getScore(int scoreId) {
        Score score = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM score WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, scoreId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                int subjectId = rs.getInt("subject_id");
                double scoreValue = rs.getDouble("score");
                score = new Score(scoreId, studentId, subjectId, scoreValue);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return score;
    }

    public List<Score> getScores(int studentId){
        List<Score> scores = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM score WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int subjectId = rs.getInt("subject_id");
                double score = rs.getDouble("score");
                scores.add(new Score(id,studentId, subjectId, score));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return scores;
    }

    public void updateScore(Score score) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE score SET score = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, score.getScore());
            ps.setInt(2, score.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteScore(int scoreId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM score WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, scoreId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateAverageScore(int studentId) {
        double totalScore = 0.0;
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT score FROM score WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                totalScore += rs.getDouble("score");
                count++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count > 0 ? totalScore / count : 0.0;
    }
}
