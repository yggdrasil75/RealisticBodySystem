package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.swing.JOptionPane;
import skyproc.ARMA;
import skyproc.FormID;
import skyproc.TXST;
import skyproc.genenums.Gender;
import skyproc.genenums.Perspective;
import skyproc.gui.SPProgressBarPlug;

public class RBS_ARMA {

    public static ArrayList<ARMA> ListVanillaAA = new ArrayList<>();
    public static Map<String, FormID> vanillaAAMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> vanillaAAMapKeyForm = new HashMap<>();
    public static Map<String, FormID> patchAAMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> patchAAMapKeyForm = new HashMap<>();

    RBS_ARMA() {
        /*
        Predicate<ARMA> clothes_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).contains("clothes\\");
        Predicate<ARMA> armor_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).contains("armor\\");
        Predicate<ARMA> clothes_m = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).contains("clothes\\");
        Predicate<ARMA> armor_m = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).contains("armor\\");
        SkyProcStarter.merger.getArmatures().getRecords().stream().filter(armor_f.or(clothes_f).or(armor_m).or(clothes_m)).forEach((n) -> FilterAndFillListVanillaAA(n));
                */
        FilterAndFillListVanillaAA();
    }

    public static void FilterAndFillListVanillaAA() {
        ListVanillaAA = SkyProcStarter.merger.getArmatures().getRecords();
        //ListVanillaAA.add(aa);
        for (ARMA aaa : ListVanillaAA) {
        vanillaAAMapKeyEDID.put(aaa.getEDID(), aaa.getForm());
        vanillaAAMapKeyForm.put(aaa.getForm(), aaa.getEDID());
    }
    }

    private String hasRBSModel(String sourcePath, String folder, String id) {
        String rbs_path;
        rbs_path = "meshes" + File.separator + "RBS" + File.separator + "female" + File.separator + id + File.separator + folder + File.separator + sourcePath;
        if (RBS_File.filelist.contains(rbs_path)) {
            return (rbs_path);
        }
        return ("");
    }

    public void CreateNewAA(String folder, String body) throws Exception {
        //for (ARMA sourceAA : ListVanillaAA) {
        SPProgressBarPlug.setStatus("creating " + folder + " Armor Addons");
        String nakedBodyType = "";
        if (body.equals("0")) {
            nakedBodyType = "body";
        }
        if (body.equals("1")) {
            nakedBodyType = "bodybbp";
        }
        if (body.equals("2")) {
            nakedBodyType = "bodytbbp";
        }
        if (body.equals("3")) {
            nakedBodyType = "bodyundies";
        }
        if (body.equals("4")) {
            nakedBodyType = "bodylayerbikini";
        }

        String fileName;
        String fileNamePatch;
        NumberFormat formatter = new DecimalFormat("000");


        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            for (ARMA sourceAA : ListVanillaAA) {
                String s = formatter.format(i);
                String sourcePath = sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase();
             
                if (!hasRBSModel(sourcePath, folder, "RBS" + s).isEmpty()) {
                        
                    if (!(sourcePath).equals("")) {
                        if (sourceAA.getEDID().contains("NakedTorso") || sourceAA.getEDID().contains("NakedHands")) {
                            fileName = SkyProcStarter.canonicalPath  + "meshes" + File.separator + "female" + File.separator + "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
                            fileNamePatch =  "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
                        } else {
                            fileName = SkyProcStarter.canonicalPath  + "meshes" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
                            fileNamePatch = "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
                        }
                    
                             //JOptionPane.showMessageDialog(null, fileName, "Test Titel", JOptionPane.OK_CANCEL_OPTION);
                            ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(sourceAA, sourceAA.getEDID() + "RBS_F" + folder + s);
                            
                            patchAAMapKeyEDID.put(targetAA.getEDID(), targetAA.getForm());
                            patchAAMapKeyForm.put(targetAA.getForm(), targetAA.getEDID());
                            for (TXST t : RBS_Texture.ListRBSTXSTMerger) {
                                if (targetAA.getSkinTexture(Gender.FEMALE).equals(t.getForm())) {
                                    String newTextureName = t.getEDID() + "RBS_F";
                                    targetAA.setSkinTexture(RBS_Texture.patchTXSTMapKeyEDID.get(newTextureName), Gender.FEMALE);

                                }
                            }

                            for (TXST t : RBS_Texture.ListRBSTXSTMerger) {
                                if (targetAA.getSkinTexture(Gender.FEMALE).equals(t.getForm())) {
                                    String rand = RBS_Randomize.toString(targetAA.getEDID() + "RBS_F" + s, 1, 10);
                                    String srand = formatter.format(rand);
                                    String newTextureName = t.getEDID() + "RBS_F" + srand;
                                    for (TXST tt : SkyProcStarter.patch.getTextureSets()) {
                                        if (tt.getEDID().equals(newTextureName)) {
                                            targetAA.setSkinTexture(tt.getForm(), Gender.FEMALE);

                                        }
                                    }
                                }
                            }
                            targetAA.setModelPath(fileNamePatch, Gender.FEMALE, Perspective.THIRD_PERSON);
                            SkyProcStarter.patch.addRecord(targetAA);
                        }
                    }
                }
            }
        }
   
}
