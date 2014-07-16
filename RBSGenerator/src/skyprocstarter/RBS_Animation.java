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
import java.util.List;
import skyproc.gui.SPProgressBarPlug;
import static skyprocstarter.SkyProcStarter.save;

public class RBS_Animation {

    public static int amountOfAnimationFolders;
    private static List<File> animationFolderList;
    private static List<File> animationFileList;
    private static String defaultFemaleXMLContent;
    private static String dummyDefaultFemaleXMLContent;
    private static final String animationsPath[][] = new String[15][50];

    RBS_Animation() throws Exception {
        readIntoAnimations();
        if (save.getBool(YourSaveFile.Settings.CHECK_FOR_NEW_ANIMATIONS_ON)) {
            copyDefaultFemaleHKXFromRBSFolderIfNotExists();
            HKXcmd(SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx", SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml", "SAVE_TEXT_FORMAT");
            readXMLintoDefaultFemaleXMLContent();
            exchangeEntriesInDefaultFemaleXMLContent();
            readDUMMYIntoDefaultFemaleXMLContent();
            createNewDefaultFemaleXMLs();
        }
    }

    public static void HKXcmd(String source, String destination, String mode) throws Exception {
        Process process;
        SPProgressBarPlug.setStatus("p " + destination);
        String command = "\"" + SkyProcStarter.pathToHKXcmd + "\" convert \"" + source + "\"" + " \"" + destination + "\" -f:\"" + mode + "\"";
        process = Runtime.getRuntime().exec(command);
        SPProgressBarPlug.setStatus("w " + destination);
        process.waitFor();
        SPProgressBarPlug.setStatus("HKX converted");
    }

    public static void copyDefaultFemaleHKXFromRBSFolderIfNotExists() throws Exception {
        SPProgressBarPlug.setStatus("defaultfemale.hkx exists?");
        File f = new File(SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx");
        if (!f.exists()) {
            SPProgressBarPlug.setStatus("Copying defaultfemale.hkx");
            RBS_File.fileCopy(SkyProcStarter.pathSources + "defaultfemale.hkx", SkyProcStarter.pathToCharactersFemale + "defaultfemale.hkx");
        } else {
            SPProgressBarPlug.setStatus("defaultfemale.hkx found");
        }
    }

    public static void readIntoAnimations() throws Exception {
        int folder = 1;
        int animations = 1;
        animationFolderList = RBS_File.getFolderList(SkyProcStarter.pathNewAnimationsSource);
        for (File animationFolder : animationFolderList) {
            animationFileList = RBS_File.getFileList(animationFolder.toString(), ".hkx");
            for (File animationFile : animationFileList) {
                animationsPath[folder][animations] = animationFolder.getPath() + animationFile.getPath();
                SPProgressBarPlug.setStatus("Animation files in F" + folder + "A" + animations);
                animations++;
            }
            folder++;
        }
        amountOfAnimationFolders = folder;
    }

    public static void exchangeEntriesInDefaultFemaleXMLContent() throws Exception {
        String ChangedDefaultFemaleXMLTmp = defaultFemaleXMLContent;
        for (int i = 0; i < animationsPath.length; i++) {
            for (String item : animationsPath[i]) {
                Path animationPath = Paths.get(item);
                String animationName = animationPath.getName(animationPath.getNameCount() - 1).toString().toLowerCase();
                String folderName = animationPath.getName(animationPath.getNameCount() - 2).toString().toLowerCase();
                String oldString = "animations\\\\female\\\\" + animationName;
                String newString = "RBS\\\\animations\\\\" + folderName + "\\\\" + animationName;
                ChangedDefaultFemaleXMLTmp = ChangedDefaultFemaleXMLTmp.replaceAll("(?i)" + oldString.toLowerCase(), newString.toLowerCase());
            }
            saveChangedDefaultFemaleAsHKX(ChangedDefaultFemaleXMLTmp, i);
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
        SPProgressBarPlug.setStatus("Saving File for processing");
        RBS_File.writeToFile(ChangedDefaultFemaleXML, SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml");
        HKXcmd(SkyProcStarter.pathToCharactersFemale + "defaultfemale.xml", SkyProcStarter.pathToCharactersFemale + i + ".hkx", "SAVE_BINARY_FORMAT");
        oldFile = Paths.get(SkyProcStarter.pathToCharactersFemale + i + ".hkx");
        newFile = Paths.get(SkyProcStarter.pathToCharactersFemale + "defaultfemale.h" + RBS_Randomize.createID(i, 2));
        Files.move(oldFile, newFile, REPLACE_EXISTING);
    }

    public static void createNewDefaultFemaleXMLs() throws Exception {
        for (int i = 1; i < amountOfAnimationFolders; i++) {
            SPProgressBarPlug.setStatus("Creating " + SkyProcStarter.pathToCharacter + "defaultfemale.h" + RBS_Randomize.createID(i, 2));
            String newContent = dummyDefaultFemaleXMLContent;
            newContent = newContent.replace("RBS_DUMMY", "Characters Female\\defaultfemale.h" + RBS_Randomize.createID(i, 2));
            RBS_File.writeToFile(newContent, SkyProcStarter.pathToCharacter + "defaultfemale.xml");
            HKXcmd(SkyProcStarter.pathToCharacter + "defaultfemale.xml", SkyProcStarter.pathToCharacter + "defaultfemale.hkx", "-f:SAVE_BINARY_FORMAT");
            File oldFile = new File(SkyProcStarter.pathToCharacter + "defaultfemale.hkx");
            oldFile.renameTo(new File(SkyProcStarter.pathToCharacter + "defaultfemale.h" + RBS_Randomize.createID(i, 2)));
        }
    }
}
