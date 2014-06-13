/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import java.io.File;
import java.util.ArrayList;

import skyproc.SPGlobal;

/**
 *
 * @author itv-braea1
 */
public class RBS_Animation {

    public static ArrayList<File> animationFolderList;
    public static ArrayList<File> animationFileList;
    public static String DefaultFemaleXMLContent;
    public static String[][] animations;

    public static void ConvertHKXToXML(String source, String destination) throws Exception {
        String command = SkyProcStarter.pathToHKXcmd + " convert \"" + source + "\"" + " \"" + destination + "\" -f:SAVE_TEXT_FORMAT";
        Process process = Runtime.getRuntime().exec("cmd /c " + command);
        process.waitFor();
        Thread.sleep(200);
    }

    public static void CopyDefaultFemaleHKXFromRBSFolderIfNotExists() throws Exception {
        File f = new File(SkyProcStarter.pathToDefaultFemaleHKX + "defaultfemale.hkx");
        if (!f.exists()) {
            SkyProcstarter.fileCopy(SPGlobal.pathToData + "rbs" + File.separator + "defaultfemale.hkx", SkyProcStarter.pathToDefaultFemaleHKX);
        }
    }

    public static void ReadIntoAnimations() throws Exception {
        int folder = 0;
        int animation = 0;
        animationFolderList = RBS_File.getFolderList(SkyProcStarter.pathNewAnimationsSource);
        for (File animationFolder : animationFolderList) {
            animationFileList = RBS_File.getFileList(animationFolder.toString(), ".hkx");
            for (File animationFile : animationFileList) {
                animations[folder][animation] = animationFolder + File.separator + animationFile ;
                animation++;
            }
            folder++;
        }
    }
    
    public static void ExchangeEntriesInDefaultFemaleXMLContent(String OldAnimation, String NewAnimation) {
        String DefaultFemaleXMLContentNew = DefaultFemaleXMLContent.replaceAll("(?i)<hkcstring>(.+?)" + OldAnimation + "</hkcstring>", "<hkcstring>" + NewAnimation + "</hkcstring>");
    }
    
    public static void main() throws Exception{
        ReadIntoAnimations();
        CopyDefaultFemaleHKXFromRBSFolderIfNotExists();
        ConvertHKXToXML(SkyProcStarter.pathToDefaultFemaleHKX, SkyProcStarter.pathToDefaultFemaleXML);
        DefaultFemaleXMLContent = RBS_File.readFromFile(SkyProcStarter.pathToDefaultFemaleXML);        
    }
    public static void ExchangeAnimationsInFile(String source) {
        

        for (File animationFile : animationFileList) {
            animationPath = animationFile.getPath();
            animationPath = animationPath.substring(animationPath.indexOf("animations"));
            animationPath = animationPath.replace("\\", "\\\\");
            String animationName = animationFile.getName();
            content = content.replaceAll("(?i)<hkcstring>(.+?)" + animationName + "</hkcstring>", "<hkcstring>" + animationPath + "</hkcstring>");
        }
        RBS_File.writeToFile(content, targetPathConverted);
    }

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

}
