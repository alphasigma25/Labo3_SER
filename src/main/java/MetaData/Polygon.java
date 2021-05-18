package MetaData;

import java.util.ArrayList;

public class Polygon {
    private final ArrayList<Coordinate> coordinates;

    public Polygon(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }
}
