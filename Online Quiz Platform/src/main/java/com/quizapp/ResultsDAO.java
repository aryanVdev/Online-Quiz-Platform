package com.quizapp;

import java.sql.*;

public class ResultsDAO {

    public static void saveResult(int userId, String examType, int score, int total, int correct, int wrong, int unanswered, int durationSeconds) {
        String sql = "INSERT INTO results(user_id, exam_type, score, total_questions, correct_answers, wrong_answers, unanswered, duration_seconds) " +
                     "VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, examType);
            ps.setInt(3, score);
            ps.setInt(4, total);
            ps.setInt(5, correct);
            ps.setInt(6, wrong);
            ps.setInt(7, unanswered);
            ps.setInt(8, durationSeconds);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
