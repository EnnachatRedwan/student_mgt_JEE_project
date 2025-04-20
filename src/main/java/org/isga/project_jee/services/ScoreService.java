package org.isga.project_jee.services;

import org.isga.project_jee.dao.ScoreDao;
import org.isga.project_jee.model.Score;

import java.util.List;

public class ScoreService {
    private final ScoreDao scoreDao;

    public ScoreService() {
        this.scoreDao = new ScoreDao();
    }

    public void addScore(Score score) {
        scoreDao.addScore(score.getStudentId(), score.getSubjectId(), score.getScore());
    }

    public Score getScore(int scoreId) {
        return scoreDao.getScore(scoreId);
    }

    public List<Score> getScores(int studentId) {
        return scoreDao.getScores(studentId);
    }

    public void updateScore(Score score) {
        scoreDao.updateScore(score);
    }

    public void deleteScore(int scoreId) {
        scoreDao.deleteScore(scoreId);
    }

    public Double calculateAverageScore(int studentId) {
        return scoreDao.calculateAverageScore(studentId);
    }
}
