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
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import skyproc.SPGlobal;

public class RBS_File {


    public static List<String> filelist;
    private static Charset charset;

    RBS_File() throws IOException {
        Path filePath = Paths.get(SkyProcStarter.pathSources + "ListGeneratedMeshes.txt");
        filelist = Files.readAllLines(filePath, Charset.defaultCharset());
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        if (file.exists()) {
            return (true);
        } else {
            return (false);
        }
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

    public static void writeToFile(String msg, String path) throws InterruptedException {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                bw.write(msg);
                bw.close();

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

    public static List getFileList(String path, String include) {
        List filelist = new ArrayList();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().contains(include)) {
                filelist.add(file);
            }
        }
        return (filelist);
    }

    public static List getFileList(String path, String include, String exclude) {
        List filelist = new ArrayList();
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

    public static List getFolderList(String path) {
        List filelist = new ArrayList();
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
        for (int bodies = 1; bodies <= RBS_Main.amountBodyTypes; bodies++) {
            String s = formatter.format(bodies);
            fileCopy(SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + body + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_1.nif",
                    SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_1.nif");
            fileCopy(SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + body + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_0.nif",
                    SPGlobal.pathToData + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + "actors" + File.separator + "character" + File.separator + "character assets" + File.separator + "femalebody_0.nif");
        }
    }

    public static void cleanFiles(String path) {
        List<File> animationFileList;
        animationFileList = getFileList(path, ".h", ".hkx");
        for (File animationFile : animationFileList) {
            animationFile.delete();
        }
    }

    public static String convertXMLFileToString(String fileName) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            InputStream inputStream = new FileInputStream(new File(fileName));
            org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
            StringWriter stw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(doc), new StreamResult(stw));
            return stw.toString();
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
        }
        return null;
    }

}
