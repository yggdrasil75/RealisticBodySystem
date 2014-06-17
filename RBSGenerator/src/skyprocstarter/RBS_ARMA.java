package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        SkyProcStarter.merger.getArmatures().getRecords().stream().parallel().forEach((aa) -> {
            FilterAndFillListVanillaAA(aa);
        });
    }

    public static void FilterAndFillListVanillaAA(ARMA aa) {
        if (RBS_Race.vanillaRacesMapKeyEDID.containsValue(aa.getRace())) {
            if (!aa.getEDID().toLowerCase().contains("child")) {
                if (aa.getModelPath(Gender.MALE, Perspective.THIRD_PERSON).toLowerCase().contains("clothes\\")
                        || aa.getModelPath(Gender.MALE, Perspective.THIRD_PERSON).toLowerCase().contains("armor\\")
                        || aa.getModelPath(Gender.MALE, Perspective.THIRD_PERSON).toLowerCase().contains("actors\\character\\character")) {
                    ListVanillaAA.add(aa);
                    vanillaAAMapKeyEDID.put(aa.getEDID(), aa.getForm());
                    vanillaAAMapKeyForm.put(aa.getForm(), aa.getEDID());
                }
            }
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

    /*
        
     if (!(sourcePath).equals("")) {
     String s = formatter.format(i);
     if (sourceAA.getEDID().contains("NakedTorso") || sourceAA.getEDID().contains("NakedHands")) {
     fileName = SkyProcStarter.path + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
     fileNamePatch = "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
     } else {
     fileName = SkyProcStarter.path + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
     fileNamePatch = "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
     }
     File targetPath = new File(fileName);
     if (targetPath.exists()) {
     ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(sourceAA, sourceAA.getEDID() + "RBS_F" + folder + s);
     }
     }
     */
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

        ArrayList<ARMA> ListAARBS = new ArrayList<>();
        String fileName;
        String fileNamePatch;
        NumberFormat formatter = new DecimalFormat("000");

        ListVanillaAA.stream().parallel().forEach((sourceAA) -> {
            if (sourceAA.getEDID().contains("Naked") && (folder.equals("killerkeo") || folder.equals("ct77"))) {
            } else {
                if (sourceAA.getEDID().contains("Ench")
                        || sourceAA.getEDID().toLowerCase().contains("child")
                        || sourceAA.getEDID().toLowerCase().contains("circlet")
                        || sourceAA.getEDID().toLowerCase().contains("shield")
                        || sourceAA.getEDID().toLowerCase().contains("Amulet")
                        || sourceAA.getEDID().toLowerCase().contains("hat")
                        || sourceAA.getEDID().toLowerCase().contains("helmet")
                        || sourceAA.getEDID().toLowerCase().contains("head")
                        || sourceAA.getEDID().toLowerCase().contains("ring")
                        || sourceAA.getEDID().toLowerCase().contains("horse")
                        || sourceAA.getEDID().toLowerCase().contains("nakeddragon")) {
                } else {
                    String sourcePath = sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase();
                    if (!(sourcePath).equals("")) {
                        ListAARBS.add(sourceAA);
                    }
                }
            }
        });

        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            for (ARMA sourceAA : ListAARBS) {

                String s = formatter.format(i);
                String sourcePath = sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase();
                hasRBSModel(sourcePath, folder, "RBS" + s);
                if (!(sourcePath).equals("")) {
                    if (sourceAA.getEDID().contains("NakedTorso") || sourceAA.getEDID().contains("NakedHands")) {
                        fileName = SkyProcStarter.path + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
                        fileNamePatch = "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + nakedBodyType + File.separator + sourcePath + "".toLowerCase();
                    } else {
                        fileName = SkyProcStarter.path + "meshes" + File.separator + "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
                        fileNamePatch = "rbs" + File.separator + "female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourcePath + "".toLowerCase();
                    }
                    File targetPath = new File(fileName);
                    if (targetPath.exists()) {
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
                    }
                }
            }
        }
    }
}
