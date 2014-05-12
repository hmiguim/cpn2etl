package pdi.components.notepad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import pdi.components.xml.Element;

/**
 *
 * @author hmg
 */
public class Notepad {

    private String note;
    private String xloc;
    private String yloc;

    private ArrayList<Element> elements = new ArrayList<>();

    public Notepad() throws IOException {
        this.readConfig();
    }

    public Notepad(String note, String xloc, String yloc) {
        try {
            this.note = note;
            this.xloc = xloc;
            this.yloc = yloc;
            this.readConfig();
        } catch (IOException ex) {
            Logger.getLogger(Notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Notepad(String note, String xloc, String yloc, ArrayList<Element> elements) {
        this.note = note;
        this.xloc = xloc;
        this.yloc = yloc;
        this.elements = elements;
    }

    public Notepad(Notepad notepad) {
        this.note = notepad.getNote();
        this.xloc = notepad.getXloc();
        this.yloc = notepad.getYloc();
        this.elements = notepad.getElements();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getXloc() {
        return xloc;
    }

    public void setXloc(String xloc) {
        this.xloc = xloc;
    }

    public String getYloc() {
        return yloc;
    }

    public void setYloc(String yloc) {
        this.yloc = yloc;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.note);
        hash = 31 * hash + Objects.hashCode(this.xloc);
        hash = 31 * hash + Objects.hashCode(this.yloc);
        hash = 31 * hash + Objects.hashCode(this.elements);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Notepad other = (Notepad) obj;
        if (!Objects.equals(this.note, other.note)) {
            return false;
        }
        if (!Objects.equals(this.xloc, other.xloc)) {
            return false;
        }
        if (!Objects.equals(this.yloc, other.yloc)) {
            return false;
        }
        return Objects.equals(this.elements, other.elements);
    }

    @Override
    public String toString() {
        return "notepad{" + "note=" + note + ", xloc=" + xloc + ", yloc=" + yloc + ", elements=" + elements + '}';
    }

    @Override
    public Notepad clone() {
        return new Notepad(this);
    }

    private void readConfig() throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get("configs/notepad"));

        String text = new String(encoded, "UTF-8");

        String[] lines = text.split("\n");

        for (String line : lines) {

            String[] split = line.split(": ");

            if (split[1].equals("[]")) {
                split[1] = "";
            }

            if (split[1].contains("{")) {
                split[1] = split[1].replace("{ ", "");
                split[1] = split[1].replace(" }", "");

                Element e = new Element(split[0], "");
                String[] nested = split[1].split(", ");

                ArrayList<Element> nested_elements = new ArrayList<>();

                for (String s : nested) {
                    String[] n = s.split("; ");
                    if (n[1].equals("[]")) {
                        n[1] = "";
                    }
                    Element a = new Element(n[0], n[1]);
                    a.setNested(false);
                    nested_elements.add(a);
                }
                e.setElements(nested_elements);
                e.setNested(true);
                this.elements.add(e);
            } else {
                Element e = new Element(split[0], split[1]);
                e.setNested(false);
                this.elements.add(e);
            }
        }
    }

}