package Efficient;

import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class File_loader extends Task<String> {
    private BufferedReader in;
    private File file;
    private ArrayList<String> projects ;
    private ComboBox box;

    public File_loader(File f, ComboBox bt) throws FileNotFoundException {
        this.file=f;
        in = new BufferedReader(new FileReader(file));
        projects = new ArrayList<>();
        this.box = bt;
    }

   public ArrayList<String> getPorject(){
        while (isRunning()){}
        return projects;
   }

    @Override
    protected String call() throws Exception {
        String line = "";
        updateValue(null);
        try {
            while ((line = in.readLine()) != null) {
                if (isCancelled()) {
                    cancel();
                }
                if (!line.equalsIgnoreCase("name(s)")) {
                   updateMessage(line);
                    updateValue(line);
                    projects.add(line);
                    box.getItems().add(line);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        updateTitle("finish");
        return line;
    }

    public ArrayList<String> getProjects() {
        return projects;
    }
}
