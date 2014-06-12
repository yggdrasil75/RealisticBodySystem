package skyprocstarter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import skyproc.SPGlobal;

public class RBS_File {

    public static ArrayList m_filelist = new ArrayList();
    private static Charset charset;

    public static boolean fileExists(String path) {
        File file = new File(path);
        if (file.exists()) {
            return (true);
        } else {
            return (false);
        }
    }

    public static void GetArrayFromListOfGeneratedMeshes() throws IOException {
        Path filePath = Paths.get(SkyProcStarter.path + "tool");
        List<String> stringList = Files.readAllLines(filePath, charset);
        m_filelist.add(stringList.toArray(new String[]{}));
        //return (stringList.toArray(new String[]{}));
    }

    public static String readFromFile(String strFile) {
        File file = new File(strFile);
        URI uri = file.toURI();
        byte[] bytes;
        try {
            bytes = java.nio.file.Files.readAllBytes(Paths.get(uri));
        } catch (IOException e) {
            return "ERROR loading file " + strFile;
        }

        return new String(bytes);
    }

    public static void writeToFile(String msg, String path) {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                bw.write(msg);
            }
        } catch (IOException e) {
        }
    }

    public static int countFiles(String path, String exclude, String include) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int counter = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(include) && !file.getName().contains(exclude)) {
                counter++;
            }
        }
        return (counter);
    }

    public static int countFiles(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        int counter = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                counter++;
            }
        }
        return (counter);
    }

    public static ArrayList getFileList(String path, String include) {
        ArrayList filelist = new ArrayList();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().contains(include)) {
                filelist.add(file);
            }
        }
        return (filelist);
    }

    public static ArrayList getFileList(String path, String include, String exclude) {
        ArrayList filelist = new ArrayList();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().contains(include)) {
                if (file.getName().toLowerCase().contains(exclude)) {
                } else {
                    filelist.add(file);
                }
            }
        }
        return (filelist);
    }

    public static ArrayList getFolderList(String path) {
        ArrayList filelist = new ArrayList();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                filelist.add(file);
            }
        }
        return (filelist);
    }

    public static void fileCopy(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            if (f2.exists()) {
                f2.delete();
            }
            OutputStream out;
            //For Append the file.
//  OutputStream out = new FileOutputStream(f2,true);
            //For Overwrite the file.
            try (InputStream in = new FileInputStream(f1)) {
                //For Append the file.
//  OutputStream out = new FileOutputStream(f2,true);
                //For Overwrite the file.
                out = new FileOutputStream(f2);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            out.close();
            System.out.println("File copied.");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void copyBodyFiles(String body, String folder) throws Exception {
        NumberFormat formatter = new DecimalFormat("000");
        for (int bodies = 1; bodies < RBS_Main.amountBodyTypes; bodies++) {
            String s = formatter.format(bodies);
            fileCopy(SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + body + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_1.nif",
                    SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_1.nif");
            fileCopy(SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + body + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_0.nif",
                    SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_0.nif");
        }
    }

    public static void cleanFiles(String path) {
        ArrayList<File> animationFileList;
        animationFileList = getFileList(path, ".h", ".hkx");
        for (File animationFile : animationFileList) {
            animationFile.delete();
        }
    }
}
