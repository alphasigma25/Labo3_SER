import org.jdom2.Document;
import org.jdom2.Element;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {

        Main.readfile("./countries.geojson");
    }

    public static void readfile(String file) {
        //JSON parser object pour lire le fichier
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            // lecture du fichier
            Object obj = jsonParser.parse(reader);
            JSONObject personne = (JSONObject) obj;
            System.out.println(personne);
            JSONArray features = (JSONArray)personne.get("features");
            // parcours du tableau de personnes
            features.forEach(feature -> parseFeature((JSONObject) feature));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void parseFeature(JSONObject feature) {
        JSONObject properties = (JSONObject) feature.get("properties");
        JSONObject geometry = (JSONObject) feature.get("geometry");
        switch ((String) geometry.get("type")) {
            case "Polygon": {
                JSONArray coordinates = (JSONArray) geometry.get("coordinates");
                parseCoordinates(coordinates);
                break;
            }
            case "Multipolygon": {
                JSONArray coordinatesList = (JSONArray) geometry.get("coordinates");
                coordinatesList.forEach(coordinates -> parseCoordinates((JSONArray) coordinates));
                break;
            }
            default: System.err.println("Type de geometry non supporte");
        }
    }

    private static void parseCoordinates(JSONArray coordinates) {

    }
}
