package org.example.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.example.dto.Quote;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class QuoteRepository {
    Scanner sc = new Scanner(System.in);
    public static List<Quote> quotes = new ArrayList<>();
    private static final Jdbc jdbc = new Jdbc();


    public void save() {
        Connection conn = jdbc.getConnection();
        int id = 1;
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        String insertQuery = "INSERT INTO quote(content, author, created) values"
                + "(?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, content);
            pstmt.setString(2, author);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(getTotalQuoteCnt() + "번 명언이 등록되었습니다.");
    }

    private int getTotalQuoteCnt() {
        int cnt = 0;
        String findAllSql = "SELECT * FROM quote";
        Connection conn = jdbc.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(findAllSql);
            while (rs.next()) {
                cnt++;
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return cnt;
    }

    public void findAll() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------------");
        String findAllSql = "SELECT * FROM quote";
        try {
            Connection conn = jdbc.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(findAllSql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " / " + rs.getString(2) + " / " + rs.getString(3));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void DeleteById(String cmd) {
        String[] deleteCmd = cmd.split("[=]");
        if (Pattern.matches("[1-9]+", deleteCmd[1])) {
            int deleteId = Integer.parseInt(deleteCmd[1]);
            if (findById(deleteId) != null) {
                try {
                    Connection conn = jdbc.getConnection();
                    String deleteById = "DELETE FROM quote WHERE id=?";
                    PreparedStatement pstmt = conn.prepareStatement(deleteById);
                    pstmt.setInt(1, deleteId);
                    pstmt.execute();
                    System.out.println(deleteId + "번 명언이 삭제되었습니다.");
                    conn.close();
                    return;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
        }
    }

    private Quote findById(int id) {
        String findByIdSql = "SELECT * FROM quote where id=?";
        try {
            Connection conn = jdbc.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(findByIdSql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Quote(rs.getInt("id"), rs.getString("content"), rs.getString("author"));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void checkEditAndEdit(String cmd) {

        String[] editCmd = cmd.split("[=]");
        boolean flag = false;
        if (Pattern.matches("[1-9]+", editCmd[1])) {
            int editId = Integer.parseInt(editCmd[1]);
            for (int i = 0; i < quotes.size(); i++) {
                if (quotes.get(i).getId() == editId) {
                    edit(i);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.out.println(editId + "번 명언은 존재하지 않습니다.");
            }
        }
    }

    private void edit(int i) {
        Quote editQuote = quotes.get(i);
        System.out.println("명언(기존) : " + editQuote.getContent());
        System.out.print("명언 : ");
        String editContent = sc.nextLine();
        System.out.println("작가(기존) : " + editQuote.getAuthor());
        System.out.print("작가 : ");
        String editAuthor = sc.nextLine();
        editQuote.setContent(editContent);
        editQuote.setAuthor(editAuthor);
    }
}

