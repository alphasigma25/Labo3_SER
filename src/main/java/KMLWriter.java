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

            Element placemark = new Element("Placemark");

            Element name = new Element("name");
            name.setText("$feature.properties.ADMIN");
            placemark.addContent(name);

            root.addContent(placemark);

            Element polygon = new Element("MetaData.Polygon");
            Element extrude = new Element("extrude");
            extrude.setText("1");
            polygon.addContent(extrude);

            // TODO

            XMLOutputter xmlOutputer = new XMLOutputter();
            xmlOutputer.setFormat(Format.getPrettyFormat());
            xmlOutputer.output(kmlDoc, new FileWriter(kmlFilePath));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

