/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import skyproc.SPGlobal;

public class RBS_Animation {

    private static ArrayList<File> animationFolderList;
    private static ArrayList<File> animationFileList;
    private static int amountOfAnimationFolders;
    private static String defaultFemaleXMLContent;
    private static String dummyDefaultFemaleXMLContent;
    private static String[][] a_animations;
    private static String[] a_ChangedDefaultFemaleXML;

    RBS_Animation() throws Exception {
        readIntoAnimations();
        copyDefaultFemaleHKXFromRBSFolderIfNotExists();
        HKXcmd(SkyProcStarter.pathToDefaultFemaleHKX, SkyProcStarter.pathToDefaultFemaleXML, "SAVE_TEXT_FORMAT");
        readXMLintoDefaultFemaleXMLContent();
        exchangeEntriesInDefaultFemaleXMLContent();
        saveChangedDefaultFemaleAsHKX();
        readDUMMYIntoDefaultFemaleXMLContent();
        createNewDefaultFemaleXMLs();
    }

    public static void HKXcmd(String source, String destination, String mode) throws Exception {
        String command = SkyProcStarter.pathToHKXcmd + " convert \"" + source + "\"" + " \"" + destination + "\" -f:" + mode;
        ProcessBuilder pb = new ProcessBuilder( command );
        Process p = pb.start();
        p.waitFor();
    }

    public static void copyDefaultFemaleHKXFromRBSFolderIfNotExists() throws Exception {
        File f = new File(SkyProcStarter.pathToDefaultFemaleHKX + "defaultfemale.hkx");
        if (!f.exists()) {
            RBS_File.fileCopy(SPGlobal.pathToData + "rbs" + File.separator + "defaultfemale.hkx", SkyProcStarter.pathToDefaultFemaleHKX);
        }
    }

    public static void readIntoAnimations() throws Exception {
        int folder = 1;
        int animation = 1;
        animationFolderList = RBS_File.getFolderList(SkyProcStarter.pathNewAnimationsSource);
        for (File animationFolder : animationFolderList) {
            animationFileList = RBS_File.getFileList(animationFolder.toString(), ".hkx");
            for (File animationFile : animationFileList) {
                a_animations[folder][animation] = animationFolder + File.separator + animationFile;
                animation++;
            }
            folder++;
        }
        amountOfAnimationFolders = folder;
    }

    public static void exchangeEntriesInDefaultFemaleXMLContent() {
        for (int folder = 1; folder == a_animations.length; folder++) {
            a_ChangedDefaultFemaleXML[folder] = defaultFemaleXMLContent;
            for (int animation = 1; animation == a_animations[folder].length; animation++) {
                a_ChangedDefaultFemaleXML[folder] = a_ChangedDefaultFemaleXML[folder].replaceAll("(?i)<hkcstring>(.+?)" + "Animations" + File.separator + (Paths.get(a_animations[folder][animation]).getFileName()) + "</hkcstring>", "<hkcstring>" + a_animations[folder][animation] + "</hkcstring>");
            }
        }
    }

    public static void readXMLintoDefaultFemaleXMLContent() {
        defaultFemaleXMLContent = RBS_File.readFromFile(SkyProcStarter.pathToDefaultFemaleXML);
    }

    public static void readDUMMYIntoDefaultFemaleXMLContent() {
        dummyDefaultFemaleXMLContent = RBS_File.readFromFile(SkyProcStarter.pathSources + "defaultfemale.xml");
    }

    public static void saveChangedDefaultFemaleAsHKX() throws Exception {
        int i = 0;
        for (String ChangedDefaultFemaleXML : a_ChangedDefaultFemaleXML) {
            i++;
            RBS_File.writeToFile(ChangedDefaultFemaleXML, SkyProcStarter.pathToDefaultFemaleHKX + "defaultfemale.tmp");
            HKXcmd(SkyProcStarter.pathToDefaultFemaleHKX + "defaultfemale.tmp", SkyProcStarter.pathToDefaultFemaleXML + "defaultfemale." + RBS_Randomize.createID(i), "SAVE_BINARY_FORMAT");
        }
    }

    public static void createNewDefaultFemaleXMLs() throws Exception {
        for (int i = 1; i == amountOfAnimationFolders; i++) {
            String newContent = dummyDefaultFemaleXMLContent;
            newContent = newContent.replace("RBS_DUMMY", "defaultfemale.h" + RBS_Randomize.createID(i));
            RBS_File.writeToFile(newContent, SkyProcStarter.pathCharacterVanilla + "defaultfemale.xml");
            HKXcmd(SkyProcStarter.pathCharacterVanilla  + "defaultfemale.xml", SkyProcStarter.pathCharacterVanilla + "defaultfemale.hkx", "-f:SAVE_BINARY_FORMAT");
            File oldFile = new File(SkyProcStarter.pathCharacterVanilla + "defaultfemale.hkx");
            oldFile.renameTo(new File(SkyProcStarter.pathCharacterVanilla + "defaultfemale.h"+RBS_Randomize.createID(i)));
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
