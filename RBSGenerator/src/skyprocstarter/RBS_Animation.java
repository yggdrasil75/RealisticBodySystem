/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import static java.nio.file.StandardCopyOption.*;
import javax.swing.JOptionPane;
public class RBS_Animation {
    
    public static int amountOfAnimationFolders;
    
    private static ArrayList<File> animationFolderList;
    private static ArrayList<File> animationFileList;
    private static String defaultFemaleXMLContent;
    private static String dummyDefaultFemaleXMLContent;
    private static final Multimap<Integer, String> a_animations = ArrayListMultimap.create();
    private static final ArrayList<String> a_ChangedDefaultFemaleXML = null;

    RBS_Animation() throws Exception {
        readIntoAnimations();
        copyDefaultFemaleHKXFromRBSFolderIfNotExists();
        HKXcmd(SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx", SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml", "SAVE_TEXT_FORMAT");
        readXMLintoDefaultFemaleXMLContent();
        exchangeEntriesInDefaultFemaleXMLContent();
        readDUMMYIntoDefaultFemaleXMLContent();
        createNewDefaultFemaleXMLs();
    }

    public static void HKXcmd(String source, String destination, String mode) throws Exception {
        String command = "\"" + SkyProcStarter.pathToHKXcmd + "\" convert \"" + source + "\"" + " \"" + destination + "\" -f:\"" + mode + "\"";
        JOptionPane.showMessageDialog(null, command, "Test Titel", JOptionPane.OK_CANCEL_OPTION);

        ProcessBuilder pb = new ProcessBuilder(command);
        Process p = pb.start();
        p.waitFor();
    }

    public static void copyDefaultFemaleHKXFromRBSFolderIfNotExists() throws Exception {
        File f = new File(SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx");
        if (!f.exists()) {
            RBS_File.fileCopy(SkyProcStarter.pathSources + "defaultfemale.hkx", SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx");
        }
    }

    public static void readIntoAnimations() throws Exception {
        int folder = 1;
        animationFolderList = RBS_File.getFolderList(SkyProcStarter.pathNewAnimationsSource);
        for (File animationFolder : animationFolderList) {
            animationFileList = RBS_File.getFileList(animationFolder.toString(), ".hkx");
            for (File animationFile : animationFileList) {
                a_animations.put(folder, animationFile.getPath());
            }
            folder++;
        }
        amountOfAnimationFolders = folder;
    }

    public static void exchangeEntriesInDefaultFemaleXMLContent() throws Exception {
        String oldString;
        String newString;
        int i =1;
        String ChangedDefaultFemaleXMLTmp = defaultFemaleXMLContent;
        int size = a_animations.asMap().size();
          for (int z = 1;z <= size;z++) {
            Collection<String> collection = a_animations.get(z);
            for (String animation : collection) {
                Path path = Paths.get(animation);
                String animationName = path.getName(path.getNameCount() - 1).toString().toLowerCase();
                String folderName = path.getName(path.getNameCount() - 2).toString().toLowerCase();
                oldString = "animations\\\\female\\\\" + animationName;
                newString = folderName + "\\\\" + animationName;
                ChangedDefaultFemaleXMLTmp = ChangedDefaultFemaleXMLTmp.replaceAll("(?i)"+oldString, newString);
            }
        saveChangedDefaultFemaleAsHKX(ChangedDefaultFemaleXMLTmp, i);
        i++;
        }
    }

    public static void readXMLintoDefaultFemaleXMLContent() throws Exception {
        defaultFemaleXMLContent = RBS_File.readFromFile(SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml");
    }

    public static void readDUMMYIntoDefaultFemaleXMLContent() {
        dummyDefaultFemaleXMLContent = RBS_File.readFromFile(SkyProcStarter.pathSources + "defaultfemale.xml");
    }

    public static void saveChangedDefaultFemaleAsHKX(String ChangedDefaultFemaleXML, int i) throws Exception {
        Path oldFile;
        Path newFile;
        RBS_File.writeToFile(ChangedDefaultFemaleXML, SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml");
        HKXcmd(SkyProcStarter.pathToCharactersFemale +"defaultfemale.xml", SkyProcStarter.pathToCharactersFemale +i+".hkx", "SAVE_BINARY_FORMAT");
        oldFile = Paths.get(SkyProcStarter.pathToCharactersFemale + i + ".hkx");
        newFile = Paths.get(SkyProcStarter.pathToCharactersFemale + "defaultfemale.h" + RBS_Randomize.createID(i, 2));
        Files.move(oldFile, newFile,REPLACE_EXISTING);
    }

    public static void createNewDefaultFemaleXMLs() throws Exception {
        for (int i = 1; i < amountOfAnimationFolders; i++) {
            String newContent = dummyDefaultFemaleXMLContent;
            newContent = newContent.replace("RBS_DUMMY", "Characters Female\\defaultfemale.h" + RBS_Randomize.createID(i,2));
            RBS_File.writeToFile(newContent, SkyProcStarter.pathToCharacter + "defaultfemale.xml");
            HKXcmd(SkyProcStarter.pathToCharacter + "defaultfemale.xml", SkyProcStarter.pathToCharacter + "defaultfemale.hkx", "-f:SAVE_BINARY_FORMAT");
            File oldFile = new File(SkyProcStarter.pathToCharacter + "defaultfemale.hkx");
            oldFile.renameTo(new File(SkyProcStarter.pathToCharacter + "defaultfemale.h" + RBS_Randomize.createID(i,2)));
        }
    }
    /*
     public static void generateHKPFile() throws Exception {
     // creates a list of animation files in pathNewAnimationsSource
     // go though that list and creates canonical paths out of every entry
     // generates
     String ID;
     String targetPathSource;
     String targetPathConverted;
     String tmpHKX;
     String targetPathRenamed;
     String animationPath;
     String animationSourcePath;
     int counter = 0;

     targetPathSource = SkyProcStarter.pathHKX + "defaultfemale.hkx";
     targetPathConverted = SkyProcStarter.pathHKX + "defaultfemale.xml";
     tmpHKX = SkyProcStarter.pathHKX + "tmp.hkx";
     cleanFiles(SkyProcStarter.pathHKX);
     cleanFiles(SkyProcStarter.pathCharacterVanilla);
     ConvertHKXToXML(targetPathSource, targetPathConverted);
     for (File animationFolder : animationFolderList) {
     counter++;
     ID = RBS_Randomize.createID(counter, 2);
     targetPathRenamed = SkyProcStarter.pathHKX + "defaultfemale.h" + ID;
     ExchangeAnimationsInFile(targetPathConverted);

     File fileSource = new File(targetPathConverted);
     command = SkyProcStarter.path + "rbs" + File.separator + "hkxcmd.exe convert \"" + targetPathConverted + "\"" + " \"" + tmpHKX + "\" -f:SAVE_BINARY_FORMAT";
     process = Runtime.getRuntime().exec("cmd /c " + command);
     process.waitFor();
     Thread.sleep(1500);
     File oldFile = new File(tmpHKX);
     oldFile.renameTo(new File(targetPathRenamed));

     File file = new File(targetPathConverted);
     fileSource.renameTo(new File(targetPathConverted + ID));
     file.delete();
     generateHKTFile(ID);
     }
     }

     public static void generateHKTFile(String ID) throws Exception {
     String command;
     String newContent;
     Process process;

     String pathCharacter = SkyProcStarter.path + "meshes" + File.separator + "Actors" + File.separator + "Character" + File.separator;
     fileCopy(SPGlobal.pathToData + "rbs" + File.separator + "defaultfemale.hkx", pathCharacter + "defaultfemale.hkx");

     command = SkyProcStarter.path + "rbs" + File.separator + "hkxcmd.exe convert " + pathCharacter + "defaultfemale.hkx" + " " + pathCharacter + "defaultfemale.xml" + " -f:SAVE_TEXT_FORMAT";
     process = Runtime.getRuntime().exec("cmd /c " + command);
     process.waitFor();
     Thread.sleep(500);

     String content = readFromFile(pathCharacter + "defaultfemale.xml");
     newContent = content.replace("RBSDummy", "Characters Female\\defaultfemale.hasdf" + ID + "");
     newContent = newContent.replace("defaultfemale.h01", "defaultfemale.h" + ID + "");
     writeToFile(newContent, pathCharacter + "defaultfemale.xml");
     Thread.sleep(500);
     command = SkyProcStarter.path + "rbs" + File.separator + "hkxcmd.exe convert " + pathCharacter + "defaultfemale.xml" + " " + pathCharacter + "defaultfemale.hkx" + " -f:SAVE_BINARY_FORMAT";

     process = Runtime.getRuntime().exec("cmd /c " + command);
     process.waitFor();
     Thread.sleep(500);

     fileCopy(pathCharacter + "defaultfemale.xml", pathCharacter + "defaultfemale.x" + ID);
     fileCopy(pathCharacter + "defaultfemale.hkx", pathCharacter + "defaultfemale.h" + ID);
     //oldFile.renameTo(new File(pathCharacter + "defaultfemale.h" + ID));
     //file.renameTo(new File(pathCharacter + "defaultfemale.x" + ID));
     //file.delete();
     }
     */
}
