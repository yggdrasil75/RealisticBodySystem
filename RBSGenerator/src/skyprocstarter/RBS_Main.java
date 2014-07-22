package skyprocstarter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RBS_Main {

    
    final public static int amountBodyTypes = 30;

    public static int amountBodyTypesMale = 7;
    private static RBS_Main instance = null;
    public static String dataPath;

    public RBS_Main() throws Exception {
        
       // String path = new java.io.File(".").getCanonicalPath();
        //  SPGlobal.pathToData = path.replace("SkyProc\\dist", "");
        //  SPGlobal.pathToData = path.replace("SkyProc", "");
    }

    public static RBS_Main getInstance() throws Exception {
        if (instance == null) {
            instance = new RBS_Main();
        }
        return instance;
    }
}
