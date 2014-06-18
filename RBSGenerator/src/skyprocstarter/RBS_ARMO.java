package skyprocstarter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
            if (!a.getEDID().contains("Ench")) {
                for (FormID listaa1 : a.getArmatures()) {
                    if (RBS_ARMA.vanillaAAMapKeyEDID.containsValue(listaa1)) {
                        ListVanillaArmors.add(a);
                        vanillaArmorsMapKeyEDID.put(a.getEDID(), a.getForm());
                        vanillaArmorsMapKeyForm.put(a.getForm(), a.getEDID());
                    }
                }
            }
        }
    }

    public void CreateNewArmor(String folder) throws Exception {
        SPProgressBarPlug.setStatus("creating  " + folder + " clothes and armors");
        NumberFormat formatter = new DecimalFormat("000");
        ListVanillaArmors.stream().forEach((vanillaArmor) -> {
            for (FormID listSourceAA : vanillaArmor.getArmatures()) {
                String s = formatter.format(1);
                FormID id = RBS_ARMA.patchAAMapKeyEDID.get(RBS_ARMA.vanillaAAMapKeyForm.get(listSourceAA) + "RBS_F" + folder + s);
                if ( id != null) {
                    ARMO targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(vanillaArmor, vanillaArmor.getEDID() + "RBS_F" + folder + s);
                    RBS_ARMO.patchArmorsMapKeyForm.put(targetArmor.getForm(), targetArmor.getEDID());
                    RBS_ARMO.patchArmorsMapKeyEDID.put(targetArmor.getEDID(), targetArmor.getForm());
                    targetArmor.getArmatures().remove(listSourceAA);
                    targetArmor.getArmatures().add(id);
                    //patch.addRecord(targetArmor);
                }
            }
        });
    }
}
