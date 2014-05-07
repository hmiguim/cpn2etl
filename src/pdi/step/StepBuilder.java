package pdi.step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import pdi.xml.Element;

/**
 *
 * @author hmg
 */
public abstract class StepBuilder {
    
    protected Step step;
    
    /**
     * Get the {@code Step} builded
     * @return A {@code Step} 
     */
    public Step getStep() {
        return this.step;
    }
    
    /** 
     * Create a new {@code Step}
     */
    public void createStep() {
        this.step = new Step();
    }
    
    /**
     * Abstract method to be implemented in each specific Step type
     */
    public abstract void buildStep();
    
    protected ArrayList<Element> readConfig(String conf) throws IOException {
        
        ArrayList<Element> elements = new ArrayList<>();
        
        byte[] encoded = Files.readAllBytes(Paths.get(conf));

       String text = new String(encoded,"UTF-8");
       
       String[] lines = text.split("\n");
       
       for (String line : lines) {
           
           String[] split = line.split(": ");
           
           if (split[1].equals("[]")) split[1] = "";
           
           if (split[1].contains("{")) {
               split[1] = split[1].replace("{ ","");
               split[1] = split[1].replace(" }", "");
               
               Element e = new Element(split[0],"");
               String[] nested = split[1].split(", ");
               
               ArrayList<Element> nested_elements = new ArrayList<>();
               
               for (String s : nested) {
                   String[] n = s.split("; ");
                   if (n[1].equals("[]")) n[1] = "";
                   Element a = new Element(n[0],n[1]);
                   a.setNested(false);
                   nested_elements.add(a);
               }
               e.setElements(nested_elements);
               e.setNested(true);
               elements.add(e);
           } else {
               Element e = new Element(split[0],split[1]);
               e.setNested(false);
               elements.add(e);
           }
       }
       
       return elements;
       
       
       
    }
}
