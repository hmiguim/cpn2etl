package main;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author hmg
 */
public class Main {

    public static void main(String args[]) throws IOException, ParserConfigurationException {

    //  Place p1 = new Place();
    //  p1.setPosX(-269);
    //  p1.setPosY(-8);
    //  p1.setText("A");
    //  
    //  Place p2 = new Place();
    //  p2.setPosX(-64);
    //  p2.setPosY(-8);
    //  p2.setText("B");
    //  
    //  Place p3 = new Place();
    //  p3.setPosX(-64);
    //  p3.setPosY(-121);
    //  p3.setText("C");
    //  
    //  Place p4 = new Place();
    //  p4.setPosX(-64);
    //  p4.setPosY(-314);
    //  p4.setText("D");
    //  
    //  Place p5 = new Place();
    //  p5.setPosX(-269);
    //  p5.setPosY(-314);
    //  p5.setText("G");
    //  
    //  Place p6 = new Place();
    //  p6.setPosX(132);
    //  p6.setPosY(-230);
    //  p6.setText("E");
    //  
    //  Place p7 = new Place();
    //  p7.setPosX(132);
    //  p7.setPosY(-314);
    //  p7.setText("F");
    //  
    //  ArrayList<Place> places = new ArrayList<>();
    //  places.add(p1);
    //  places.add(p2);
     //  places.add(p3);
     //  places.add(p4);
     //  places.add(p5);
     //  places.add(p6);
     //  places.add(p7);
     //  
     //  
     //  Collection<Place> normalizePlaces = Helper.normalizePlaces(places);
        
    //    for (Place p : normalizePlaces) {
    //        System.out.println(p.getText() + " x= " + p.getPosX() + " y=" + p.getPosY());
    //    }
          new gui.Main().setVisible(true); 
    }
}
