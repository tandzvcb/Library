package googleAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class setUpAPI {
    private static final String API_KEY = "AIzaSyD7hpCj0M3qXRcBw1yLFP7F5PGcsFG5upg";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static String searchBooks(String query) {
        StringBuilder result = new StringBuilder();
        try {
            String urlString = BASE_URL + query + "&key=" + API_KEY;
            if(urlString.contains(" "))
                urlString = urlString.replace(" ", "%20");
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void parseAndPrintBooks(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray items = jsonObject.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            JSONObject volumeInfo = item.getJSONObject("volumeInfo");

            String title = volumeInfo.getString("title");
            String authors = volumeInfo.getJSONArray("authors").join(", ");
            String publisher = volumeInfo.optString("publisher", "Unknown Publisher");
            String publishedDate = volumeInfo.optString("publishedDate", "Unknown Date");

            System.out.println("Title: " + title);
            System.out.println("Authors: " + authors);
            System.out.println("Publisher: " + publisher);
            System.out.println("Published Date: " + publishedDate);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String query = "java programming";
        String response = searchBooks(query);
        parseAndPrintBooks(response);
    }
}
