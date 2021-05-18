package MetaData;

import java.util.ArrayList;

public class Feature {
    private final String name;
    private final String countryCode;
    private final ArrayList<Polygon> polygons;

    public Feature(String name, String countryCode, ArrayList<Polygon> polygons) {
        this.name = name;
        this.countryCode = countryCode;
        this.polygons = polygons;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("(").append(countryCode).append(") ").append(name);

        for (Polygon polygon : polygons) {
            s.append("\n      - ").append(polygon);
        }

        return s.toString();
    }
}
