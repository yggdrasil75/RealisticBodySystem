package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import skyproc.ARMA;
import skyproc.FormID;
import skyproc.genenums.Gender;
import skyproc.genenums.Perspective;
import skyproc.gui.SPProgressBarPlug;

public class RBS_ARMA {

    public static List<ARMA> ListVanillaAA = new ArrayList<>();
    public static Map<String, FormID> vanillaAAMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaAAMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchAAMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> patchAAMapKeyForm = new HashMap<>();

    RBS_ARMA() {
        // filters out needed ARMA Addons and put them into ListVanillaAA  and vanillaAAMapKeyMAps
        Predicate<ARMA> clothes_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("clothes\\");
        Predicate<ARMA> armor_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("armor\\");
        Predicate<ARMA> actors_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("actors\\character\\character assets\\femalebody_1.nif");
        ListVanillaAA = SkyProcStarter.merger.getArmatures().getRecords().parallelStream().filter(armor_f.or(clothes_f).or(actors_f)).collect(Collectors.toList());
        ListVanillaAA.parallelStream().forEach((aa) -> {
            FilterAndFillListVanillaAA(aa);
        });
    }

    public static void FilterAndFillListVanillaAA(ARMA aa) {
        vanillaAAMapKeyEDID.put(aa.getEDID(), aa.getForm());
        vanillaAAMapKeyForm.put(aa.getForm(), aa.getEDID());
    }

    private String hasRBSModel(String sourcePath, String folder, String id) {
        String rbs_path = "meshes" + File.separator + "RBS" + File.separator + "female" + File.separator + id + File.separator + folder + File.separator + sourcePath;
        if (RBS_File.filelist.contains(rbs_path)) {
            return (rbs_path);
        }
        return ("");
    }

    public void CreateNewAA(String folder, String body) throws Exception {
        //for (ARMA sourceAA : ListVanillaAA) {
        Instant start = Instant.now();
        SPProgressBarPlug.setStatus("creating " + folder + " Armor Addons");
        NumberFormat formatter = new DecimalFormat("000");
        ListVanillaAA.stream().forEach(sourceAA -> {
            if (!hasRBSModel(sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase(), folder, "RBS" + formatter.format(1)).isEmpty()) {
                for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
                    String s = formatter.format(i);
                    ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(sourceAA, sourceAA.getEDID() + "RBS_F" + folder + s);
                    patchAAMapKeyEDID.put(targetAA.getEDID(), targetAA.getForm());
                    patchAAMapKeyForm.put(targetAA.getForm(), targetAA.getEDID());
                    targetAA.setModelPath("female" + File.separator + "rbs" + s + File.separator + folder + File.separator + sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON) + "".toLowerCase(), Gender.FEMALE, Perspective.THIRD_PERSON);
                    SkyProcStarter.patch.addRecord(targetAA);
                }
            }
        });
        Instant end = Instant.now();
        JOptionPane.showMessageDialog(null, Duration.between(start, end), "Test Titel", JOptionPane.OK_CANCEL_OPTION);
    }
}

/*
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
*/
