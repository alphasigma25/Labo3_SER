import MetaData.Coordinate;
import MetaData.Feature;
import MetaData.Polygon;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class KMLWriter {

    String kmlFilePath;

    public KMLWriter(String kmlFilePath){
        this.kmlFilePath = kmlFilePath;
    }

    public void write(ArrayList<Feature> features) {

        try {
            Element root = new Element("kml");
            root.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
            Document kmlDoc = new Document(root);

            Element document = new Element("Document");

            for (Feature feature : features) {
                Element placemark = writeFeature(feature);

                document.addContent(placemark);
            }

            root.addContent(document);


            // TODO

            XMLOutputter xmlOutputer = new XMLOutputter();
            xmlOutputer.setFormat(Format.getPrettyFormat());
            xmlOutputer.output(kmlDoc, new FileWriter(kmlFilePath));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Element writeFeature(Feature feature) {
        Element placemark = new Element("Placemark");

        Element name = new Element("name");
        name.setText(feature.getName());
        placemark.addContent(name);

        //ExtendedData
        Element extendedData = new Element("ExtendedData");
        placemark.addContent(extendedData);

        // TODO: Remove
        /*
        //ADMIN
        Element dataAdmin = new Element("Data");
        dataAdmin.setAttribute("name", "ADMIN");
        Element adminValue = new Element("value");
        adminValue.addContent(feature.getName());
        dataAdmin.addContent(adminValue);
        extendedData.addContent(dataAdmin);
         */

        //ISO
        Element dataISO = new Element("Data");
        dataISO.setAttribute("name", "ISO_A3");
        Element ISOValue = new Element("value");
        ISOValue.addContent(feature.getCountryCode());
        dataISO.addContent(ISOValue);
        extendedData.addContent(dataISO);

        //Polygons
        Element polygons = writePolygons(feature.getPolygons());
        placemark.addContent(polygons);

        return placemark;
    }

    private Element writePolygons(ArrayList<Polygon> polygons) {
        if (polygons.size() > 1) {
            Element multigeom = new Element("MultiGeometry");
            for(Polygon p: polygons){
                multigeom.addContent(writePolygon(p));
            }
            return multigeom;
        } else {
            return writePolygon(polygons.get(0));
        }
    }

    private Element writePolygon(Polygon polygon) {
        Element polygonElement = new Element("Polygon");

        Element extrude = new Element("extrude");
        extrude.setText("1");
        polygonElement.addContent(extrude);

        Element altitudeMode = new Element("altitudeMode");
        altitudeMode.setText("relativeToGround");
        polygonElement.addContent(altitudeMode);

        Element outerBoundaryIs = new Element("outerBoundaryIs");
        Element linearRing = new Element("LinearRing");
        Element coordinates = writeCoordinates(polygon.getCoordinates());
        linearRing.addContent(coordinates);
        outerBoundaryIs.addContent(linearRing);
        polygonElement.addContent(outerBoundaryIs);

        return polygonElement;
    }

    private Element writeCoordinates(ArrayList<Coordinate> coords){
        Element coordinates = new Element("coordinates");
        StringBuilder textCoords = new StringBuilder();
        for(Coordinate c : coords){
            textCoords.append(c.getLatitude()).append(",").append(c.getLongitude()).append(" ");
        }
        coordinates.addContent(textCoords.toString());
        return coordinates;
    }

}

