package main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    //tiuewjndsa
    public static Word searchWord(String other){
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select *from av where word = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, other);
            rs = ps.executeQuery();
            int id = rs.getInt(1);
            String htmlTag = rs.getString(3);
            String word_explain = "";
            String pronounce = rs.getString(5);
            String synonym = rs.getString(6);
            String antonym = rs.getString(7);
            while (rs.next()) {
                word_explain += rs.getString(4)+"\n";
            }
            Word ans = new Word(id, other, htmlTag, word_explain, pronounce, synonym,antonym);
            return ans;
        } catch (SQLException e) {
            return new Word();
            //System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static String recommendWord(String other) {
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String s = "'" + other + "%'";
            String sql = "select word from av where word like " + s +" limit 5";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            return "";
        } catch (SQLException e) {
            return "Wrong!";
            //System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static int numberOfWords(){
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql  = "select count(id) from av";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            int size = rs.getInt(1);
            return size;
        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connection
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static void insertWord(Word other){
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "insert into av(id, word, html, description, pronounce, synonym, antonym) VALUES(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, other.getId());
            ps.setString(2, other.getWord_target());
            ps.setString(3, other.getHtmlTag());
            ps.setString(4, other.getWord_explain());
            ps.setString(5, other.getPronounce());
            ps.setString(6, other.getSynonym());
            ps.setString(7, other.getAntonym());
            ps.execute();
            System.out.println("Data has been inserted!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void deleteWord(String other){
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql="delete from av WHERE  word = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, other);
            ps.execute();
            System.out.println("The word "+ other +" has been removed!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void UpdateWord(Word other, String newWord){
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "update av set word = ? where word = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newWord);
            ps.setString(2, other.getWord_target());
            ps.execute();
            System.out.println("The word has been updated!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void UpdateWordMeaning(Word other, String newMeaning) {
        Connection conn = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "update av set description = ? where word = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newMeaning);
            ps.setString(2, other.getWord_explain());
            ps.execute();
            System.out.println("The word has been updated!");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        Word w = searchWord("low");

        if(!w.checkExists()) System.out.println("Word doesn't exist!");
        else w.display();
        //System.out.println(numberOfWords());
        recommendWord("lo");
    }
}
