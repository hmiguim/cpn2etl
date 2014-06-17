package pdi.components.step;

import pdi.components.step.Step;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import pdi.components.xml.Element;

/**
 *
 * @author hmg
 */
public abstract class StepBuilder {

    protected Step step;

    /**
     * Get the {@code Step} builded
     *
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

        String text = new String(encoded, "UTF-8");

        String[] lines = text.split("\n");

        for (String line : lines) {
            
            elements.add(this.decompose(line));
            
        }
        return elements;
    }
    
    private Element decompose(String str) {
        
        Element e;
        
        String[] split = str.split(": ");
        
        if (split[1].startsWith("(")) {
            e = new Element(split[0],"");
            e.setNested(true);
            e.setElements(this.decomposeParenthesis(split[1]));
        } else if (split[1].startsWith("{")) {
            e = new Element(split[0],"");
            e.setNested(true);
            e.setElements(this.decomposeBrackets(split[1]));
        } else {
            if (split[1].equals("[]")) {
                e = new Element(split[0], "");
            } else {
                e = new Element(split[0],split[1]);
            }
            
            e.setNested(false);
            
        }
        
        return e;
    }
    
    private ArrayList<Element> decomposeParenthesis(String str) {
     
        Element e;
        
        str = str.replace("( ", "");
        str = str.replace(" )", "");
        
        ArrayList<Element> elements = new ArrayList<>();
        
        String[] stu = str.split(" - ");
        
        e = new Element(stu[0],"");
            
        e.setNested(true);
        e.setElements(this.decomposeBrackets(stu[1]));
        
        elements.add(e);
        
        return elements;
    }
    
    private ArrayList<Element> decomposeBrackets(String str) {
        
        ArrayList<Element> elements = new ArrayList<>();
        
        Element e;
        
        str = str.replace("{ ", "");
        str = str.replace(" }", "");
        
        String[] stu = str.split(", ");
        
        for (String s : stu) {
            String[] split = s.split("; ");
            if (split[1].equals("[]")) {
                e = new Element(split[0], "");
            } else {
                e = new Element(split[0],split[1]);
            }
            
            elements.add(e);
            
        }
        
        return elements;
    }
}
