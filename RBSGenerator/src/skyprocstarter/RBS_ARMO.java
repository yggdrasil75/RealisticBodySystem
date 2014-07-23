package skyprocstarter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.MajorRecord;
import skyproc.gui.SPProgressBarPlug;

public class RBS_ARMO {

    //   public static Map<String, FormID> vanillaArmorsMapKeyEDID = new ConcurrentHashMap<>();
    //   public static Map<FormID, String> vanillaArmorsMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchArmorsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchArmorsMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> vanillaArmorsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaArmorsMapKeyForm = new ConcurrentHashMap<>();
    public static List<ARMO> listVanillaArmors = new ArrayList<>();
    public static List<MajorRecord> skinNaked = new ArrayList<>();

    RBS_ARMO() {
        for (ARMO a : SkyProcStarter.merger.getArmors()) {
            if (!a.getEDID().contains("Ench")) {
                for (FormID listaa1 : a.getArmatures()) {
                    if (RBS_ARMA.vanillaAAMapKeyEDID.containsValue(listaa1)) {
                        listVanillaArmors.add(a);
                        vanillaArmorsMapKeyEDID.put(a.getEDID(), a.getForm());
                        vanillaArmorsMapKeyForm.put(a.getForm(), a.getEDID());
                    }
                }
            }
        }
        skinNaked.add(SkyProcStarter.merger.getArmors().get(new FormID("000D64Skyrim.esm"))); //skinNaked
        skinNaked.add(SkyProcStarter.merger.getArmors().get(new FormID("069CE3Skyrim.esm"))); //skinNakedBeast
    }

    public void CreateNewArmor(String folder) throws Exception {
        SPProgressBarPlug.setStatus("creating  " + folder + " clothes and armors");
        NumberFormat formatter = new DecimalFormat("000");
        List<FormID> newAA = new ArrayList<>();
        List<FormID> oldAA = new ArrayList<>();
        for (int i = 1; i <= SkyProcStarter.amountBodyTypesFemale; i++) {
            String s = formatter.format(i);
            for (ARMO vanillaArmor : listVanillaArmors) {
                vanillaArmor.getArmatures().stream().forEach((vanillaARMA) -> {
                    String asdf = RBS_ARMA.vanillaAAMapKeyForm.get(vanillaARMA) + "RBS_F" + folder + s;
                    if (RBS_ARMA.patchAAMapKeyEDID.get(asdf) != null) {
                        newAA.add(RBS_ARMA.patchAAMapKeyEDID.get(asdf));
                        oldAA.add(vanillaARMA);
                    }
                });

                if (newAA.size() > 0) {
                    ARMO targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(vanillaArmor, vanillaArmor.getEDID() + "RBS_F" + folder + s);
                    RBS_ARMO.patchArmorsMapKeyForm.put(targetArmor.getForm(), targetArmor.getEDID());
                    RBS_ARMO.patchArmorsMapKeyEDID.put(targetArmor.getEDID(), targetArmor.getForm());
                    targetArmor.getArmatures().removeAll(oldAA);
                    targetArmor.getArmatures().addAll(newAA);
                    newAA.clear();
                    oldAA.clear();
                }
            }
        }
    }

    public void changeSkinNaked() {
        SPProgressBarPlug.setStatus("change SkinNaked");
        for (MajorRecord torso : RBS_ARMO.skinNaked) {
            SkyProcStarter.amountBodyTypesString.stream().forEach((id) -> {
                ARMO targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(torso, torso.getEDID() + "RBS_F" + id);
                for (MajorRecord entryAA : RBS_ARMA.nakedTorsos) {
                    targetArmor.getArmatures().remove(entryAA.getForm());
                    targetArmor.getArmatures().add(RBS_ARMA.patchAAMapKeyEDID.get(entryAA.getEDID() + "RBS_F" + id));
                }
            });
        }
    }
}
