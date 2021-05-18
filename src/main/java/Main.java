import MetaData.Coordinate;
import MetaData.Feature;
import MetaData.Polygon;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Feature> features = Main.readfile("./countries.geojson");

        KMLWriter writer = new KMLWriter("countries.kml");
        writer.write(features);
    }

    /*
    features : Feature[]

    class Feature {
      name: String,
      polygons: Polygon[],
    }
    type :
    features : []
    feature : {
      type : ???
      properties : {ADMIN, ISO_A3}
      geometry :
      { type : Polygon | ???
      coordinates [[ [double,double] ]]


      }
    }
     */

    //parser geojson
    public static ArrayList<Feature> readfile(String file) {
        //JSON parser object pour lire le fichier
        JSONParser jsonParser = new JSONParser();
        ArrayList<Feature> parsedFeatures = new ArrayList<>();
        try (FileReader reader = new FileReader(file)) {
            // lecture du fichier
            Object obj = jsonParser.parse(reader);
            JSONObject collection = (JSONObject) obj;
            System.out.println(collection);
            JSONArray features = (JSONArray) collection.get("features");

            // parcours du tableau de personnes
            for (Object feature : features) {
                parsedFeatures.add(parseFeature((JSONObject) feature));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return parsedFeatures;
    }

    private static Feature parseFeature(JSONObject feature) {
        JSONObject properties = (JSONObject) feature.get("properties");
        String name = (String) properties.get("ADMIN");
        String countryCode = (String) properties.get("ISO_A3");

        ArrayList<Polygon> polygons = new ArrayList<>();
        JSONObject geometry = (JSONObject) feature.get("geometry");
        switch ((String) geometry.get("type")) {
            case "Polygon": {
                JSONArray coordinates = (JSONArray) geometry.get("coordinates");

                polygons.add(new Polygon(parseCoordinates((JSONArray) coordinates.get(0))));
                break;
            }
            case "Multipolygon": {
                JSONArray coordinatesList = (JSONArray) geometry.get("coordinates");
                for (Object coordinates : coordinatesList) {
                    JSONArray coordinates2 = (JSONArray) coordinates;

                    polygons.add(new Polygon(parseCoordinates((JSONArray) coordinates2.get(0))));
                }
                break;
            }
            default:
                System.err.println("Type de geometry non supporte");
        }

        return new Feature(name, countryCode, polygons);
    }

    private static ArrayList<Coordinate> parseCoordinates(JSONArray coordinates) {
        ArrayList<Coordinate> parsedCoordinates = new ArrayList<>();

        for (Object coordinate : coordinates) {
            JSONArray coordinates2 = (JSONArray) coordinate;
            parsedCoordinates.add(new Coordinate((double) coordinates2.get(0), (double) coordinates2.get(1)));
        }

        return parsedCoordinates;
    }
}
