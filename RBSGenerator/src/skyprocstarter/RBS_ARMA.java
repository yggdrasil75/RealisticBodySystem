package skyprocstarter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import skyproc.ARMA;
import skyproc.FormID;
import skyproc.MajorRecord;
import skyproc.genenums.Gender;
import skyproc.genenums.Perspective;
import skyproc.gui.SPProgressBarPlug;

public class RBS_ARMA {

    public static ArrayList<ARMA> ListVanillaAA = new ArrayList<>(100000);
    public static Map<String, FormID> vanillaAAMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaAAMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchAAMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> patchAAMapKeyForm = new HashMap<>();
    public static ArrayList<MajorRecord> nakedTorsos = new ArrayList<>(5);

    RBS_ARMA() {
        nakedTorsos.add(SkyProcStarter.merger.getArmatures().get(new FormID("000D67Skyrim.esm"))); // nakedTorso
        nakedTorsos.add(SkyProcStarter.merger.getArmatures().get(new FormID("081BA5Skyrim.esm"))); // nakedTorsoKhajiit
        nakedTorsos.add(SkyProcStarter.merger.getArmatures().get(new FormID("038A6BSkyrim.esm"))); // nakedTorsoHighElf
        nakedTorsos.add(SkyProcStarter.merger.getArmatures().get(new FormID("019386Skyrim.esm"))); // nakedTorsoDarkElf
        nakedTorsos.add(SkyProcStarter.merger.getArmatures().get(new FormID("06C5FCSkyrim.esm"))); // nakedTorsoArgonian

        // filters out needed ARMA Addons and put them into ListVanillaAA  and vanillaAAMapKeyMAps
        Predicate<ARMA> clothes_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("clothes\\");
        Predicate<ARMA> armor_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("armor\\");
        Predicate<ARMA> actors_f = (n) -> n.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("actors\\character\\character assets\\femalebody_1.nif");
        ListVanillaAA = ((ArrayList) SkyProcStarter.merger.getArmatures().getRecords().stream().filter(armor_f.or(clothes_f).or(actors_f)).collect(Collectors.toList()));
        ListVanillaAA.trimToSize();
        ListVanillaAA.stream().forEach((aa) -> {
            FilterAndFillListVanillaAA(aa);
        });
    }

    public static void FilterAndFillListVanillaAA(ARMA aa) {
        vanillaAAMapKeyEDID.put(aa.getEDID(), aa.getForm());
        vanillaAAMapKeyForm.put(aa.getForm(), aa.getEDID());
    }

    private boolean hasRBSModel(String sourcePath, String folder, String id) {
        String RBS_path = ("meshes\\rbs\\female\\" + id + File.separator + folder + File.separator + sourcePath).toLowerCase();
        if (RBS_File.filelist.contains(RBS_path)) {
            return (true);
        }
        return (false);
    }

    public void CreateNewAA(String folder, String body) throws Exception {
        SPProgressBarPlug.setStatus("creating " + folder + " Armor Addons");
        ListVanillaAA.stream().forEach((sourceAA) -> {
            if (hasRBSModel(sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase(), folder, "rbs001")) {
                SkyProcStarter.amountBodyTypes.stream().forEach((id) -> {
                    ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(sourceAA, sourceAA.getEDID() + "RBS_F" + folder + id);
                    patchAAMapKeyEDID.put(targetAA.getEDID(), targetAA.getForm());
                    patchAAMapKeyForm.put(targetAA.getForm(), targetAA.getEDID());
                    targetAA.setModelPath("rbs\\female\\rbs" + id + File.separator + folder + File.separator + sourceAA.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON) + "".toLowerCase(), Gender.FEMALE, Perspective.THIRD_PERSON);
                });
            }
        });
    }

    public void changeNakedTorso() {
        SPProgressBarPlug.setStatus("creating Torsos");
        RBS_ARMA.nakedTorsos.stream().forEach((nakedTorso) -> {
            vanillaAAMapKeyEDID.put(nakedTorso.getEDID(), nakedTorso.getForm());
            vanillaAAMapKeyForm.put(nakedTorso.getForm(), nakedTorso.getEDID());
            SkyProcStarter.amountBodyTypes.stream().forEach((id) -> {
                ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(nakedTorso, nakedTorso.getEDID() + "RBS_F" + id);
                patchAAMapKeyEDID.put(targetAA.getEDID(), targetAA.getForm());
                patchAAMapKeyForm.put(targetAA.getForm(), targetAA.getEDID());
                targetAA.setModelPath("actors\\character\\character assets\\rbs\\female\\rbs" + id + "\\femalebody_1.nif", Gender.FEMALE, Perspective.THIRD_PERSON);
            });
        });
    }

    public void addModRacesToAA() {
        for (ARMA AA : SkyProcStarter.patch.getArmatures().getRecords()) {
            for (FormID race : RBS_Race.ListHumanRaces) {
                if (!AA.getAdditionalRaces().contains(race)) {
                    AA.addAdditionalRace(race);
                }
            }
            for (FormID race : RBS_Race.ListBeastRaces) {
                if (!AA.getAdditionalRaces().contains(race)) {
                    AA.addAdditionalRace(race);
                }
            }
        }
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
