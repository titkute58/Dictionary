package main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HttpURLConnectionExample {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String searchSynonym(String wordToSearch) throws Exception {
        //System.out.println("Sending request...");

        String url = "https://api.datamuse.com/words?rel_syn=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending request to: " + url);
        //System.out.println("JSON Response: " + responseCode + "\n");

        // ordering the response
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String ans="";

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );

            ans="Synonym word of '" + wordToSearch + "': ";
            if(words.size() > 0) {
                for(Word word : words) {
                    ans += word.getWord() + ", ";
                }
                ans += "...";
                return ans;
            }
            else {
                return "None synonym word!";
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return "None synonym word!";
    }

    public static String searchAntonym(String wordToSearch) throws Exception {
        //System.out.println("Sending request...");

        String url = "https://api.datamuse.com/words?rel_ant=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String ans="";

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );

            ans="Antonym word of '" + wordToSearch + "': ";
            if(words.size() > 0) {
                for(Word word : words) {
                    ans+=word.getWord() + ", ";
                }
                ans+="...";
                return ans;
            }
            else {
                return "None antonym word!";
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return "None antonym word!";
    }

    // word and score attributes are from DataMuse API
    private static class Word {
        private String word;
        private int score;

        public String getWord() {return this.word;}
        public int getScore() {return this.score;}
    }

    public static void main(String[] args) throws Exception {
        HttpURLConnectionExample http = new HttpURLConnectionExample();

        Scanner inp = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String wordToSearch = inp.next();

        http.searchSynonym(wordToSearch);
        System.out.println();
        http.searchAntonym(wordToSearch);
    }
}