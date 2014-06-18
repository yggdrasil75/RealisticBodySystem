package skyprocstarter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.gui.SPProgressBarPlug;

public class RBS_ARMO {

    public static Map<String, FormID> vanillaArmorsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaArmorsMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchArmorsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchArmorsMapKeyForm = new ConcurrentHashMap<>();
    public static List<ARMO> ListVanillaArmors = new ArrayList<>();

    RBS_ARMO() {
        for (ARMO a : SkyProcStarter.merger.getArmors()) {
            ArrayList<FormID> listaa = a.getArmatures();
            for (FormID listaa1 : listaa) {
                if (RBS_ARMA.vanillaAAMapKeyEDID.containsValue(listaa1)) {
                    ListVanillaArmors.add(a);
                    vanillaArmorsMapKeyEDID.put(a.getEDID(), a.getForm());
                    vanillaArmorsMapKeyForm.put(a.getForm(), a.getEDID());
                }
            }
        }
    }

    public void CreateNewArmor(String folder) throws Exception {
        SPProgressBarPlug.setStatus("creating  " + folder + " clothes and armors");
        NumberFormat formatter = new DecimalFormat("000");
        for (ARMO vanillaArmor : ListVanillaArmors) {
            if (!vanillaArmor.getEDID().contains("Ench")) {
                ArrayList<FormID> listSourceAA = vanillaArmor.getArmatures();
                for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
                    ARMO targetArmor = vanillaArmor;
                    boolean patched = false;
                    for (FormID listSourceAA1 : listSourceAA) {
                        String s = formatter.format(i);
                        FormID id = RBS_ARMA.patchAAMapKeyEDID.get(RBS_ARMA.vanillaAAMapKeyForm.get(listSourceAA1) + "RBS_F" + folder + s);
                        if (id != null) {
                            if (!patched) {
                                targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(vanillaArmor, vanillaArmor.getEDID() + "RBS_F" + folder + s);
                                patched = true;
                                RBS_ARMO.patchArmorsMapKeyForm.put(targetArmor.getForm(), targetArmor.getEDID());
                                RBS_ARMO.patchArmorsMapKeyEDID.put(targetArmor.getEDID(), targetArmor.getForm());

                            }
                            targetArmor.addArmature(id);
                            targetArmor.removeArmature(listSourceAA1);
                            //   patch.addRecord(targetArmor);
                        }
                    }
                }
            }
        }
    }
}
