package skyprocstarter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import skyproc.FormID;
import skyproc.HDPT;
import skyproc.MajorRecord;
import skyproc.NPC_;
import skyproc.gui.SPProgressBarPlug;
import static skyprocstarter.RBS_NPC.ListNPCFemale;

public class RBS_Hair {

    private static final List<HDPT> m_hairList = new ArrayList<>();
    private static final List<HDPT> m_hairListOrcs = new ArrayList<>();
    private static final List<String> m_HairsNormalWhiteList = new ArrayList<>();
    private static final List<String> m_HairsWildWhiteList = new ArrayList<>();
    private static final List<String> m_HairsUpperWhiteList = new ArrayList<>();
    private static final List<String> m_HairsLowerWhiteList = new ArrayList<>();
    private static final List<HDPT> m_HairsNormalList = new ArrayList<>();
    private static final List<HDPT> m_HairsWildList = new ArrayList<>();
    private static final List<HDPT> m_HairsUpperList = new ArrayList<>();
    private static final List<HDPT> m_HairsLowerList = new ArrayList<>();

    public void createApachiiWhileList() {

        m_HairsNormalWhiteList.add("ApachiiHairF02");
        m_HairsNormalWhiteList.add("ApachiiHairF03");
        m_HairsNormalWhiteList.add("ApachiiHairF04");
        m_HairsNormalWhiteList.add("ApachiiHairF05");

        m_HairsNormalWhiteList.add("ApachiiHairF08");
        m_HairsNormalWhiteList.add("ApachiiHairF09");
        m_HairsNormalWhiteList.add("ApachiiHairF10");
        // m_HairsNormalWhiteList.add("ApachiiHairF14"); ugly as hell
        m_HairsNormalWhiteList.add("ApachiiHairF15");
        m_HairsNormalWhiteList.add("ApachiiHairF16");
        m_HairsNormalWhiteList.add("ApachiiHairF17");
        m_HairsNormalWhiteList.add("ApachiiHairF18");
        m_HairsNormalWhiteList.add("ApachiiHairF19");
        m_HairsNormalWhiteList.add("ApachiiHairF21");
        m_HairsNormalWhiteList.add("ApachiiHairF25");

        m_HairsNormalWhiteList.add("ApachiiHairF27");
        m_HairsNormalWhiteList.add("ApachiiHairF28");
        m_HairsNormalWhiteList.add("ApachiiHairF34");

        m_HairsNormalWhiteList.add("ApachiiHairHuman01");
        m_HairsNormalWhiteList.add("ApachiiHairHuman02");
        m_HairsNormalWhiteList.add("ApachiiHairHuman03");
        m_HairsNormalWhiteList.add("ApachiiHairHuman04");
        m_HairsNormalWhiteList.add("ApachiiHairHuman07");
        m_HairsNormalWhiteList.add("ApachiiHairHuman10");
        m_HairsNormalWhiteList.add("ApachiiHairHuman36");
        m_HairsNormalWhiteList.add("ApachiiHairHuman37");
        m_HairsNormalWhiteList.add("ApachiiHairHuman42");
        m_HairsNormalWhiteList.add("HairFemaleNord14");
        m_HairsNormalWhiteList.add("HairFemaleRedguard01");
        m_HairsNormalWhiteList.add("HairFemaleRedguard02");

        m_HairsUpperWhiteList.add("ApachiiHairF03");
        m_HairsUpperWhiteList.add("ApachiiHairF09");
        m_HairsUpperWhiteList.add("ApachiiHairF18");
        m_HairsUpperWhiteList.add("ApachiiHairF21");
        m_HairsUpperWhiteList.add("ApachiiHairF26");
        m_HairsUpperWhiteList.add("ApachiiHairF28");
        m_HairsUpperWhiteList.add("ApachiiHairHuman09");
        m_HairsUpperWhiteList.add("ApachiiHairHuman13");
        m_HairsUpperWhiteList.add("ApachiiHairHuman14");
        m_HairsUpperWhiteList.add("ApachiiHairHuman15");
        m_HairsUpperWhiteList.add("ApachiiHairHuman16");
        m_HairsUpperWhiteList.add("ApachiiHairHuman18");
        m_HairsUpperWhiteList.add("ApachiiHairHuman20");
        m_HairsUpperWhiteList.add("ApachiiHairHuman21");

        m_HairsUpperWhiteList.add("ApachiiHairHuman23");

        m_HairsWildWhiteList.add("ApachiiHairF01");
        m_HairsWildWhiteList.add("ApachiiHairF06");
        m_HairsWildWhiteList.add("ApachiiHairF07");
        m_HairsWildWhiteList.add("ApachiiHairF24");
        m_HairsWildWhiteList.add("ApachiiHairF32");
        m_HairsWildWhiteList.add("ApachiiHairF35");
        m_HairsWildWhiteList.add("ApachiiHairF36");
        m_HairsWildWhiteList.add("ApachiiHairF38");
        m_HairsWildWhiteList.add("ApachiiHairF39");
        m_HairsWildWhiteList.add("ApachiiHairF40");
        m_HairsWildWhiteList.add("ApachiiHairF41");
        m_HairsWildWhiteList.add("ApachiiHairF42");
        m_HairsWildWhiteList.add("ApachiiHairHuman22");
        m_HairsWildWhiteList.add("ApachiiHairHuman25");
        m_HairsWildWhiteList.add("ApachiiHairHuman26");
        m_HairsWildWhiteList.add("ApachiiHairHuman27");
        m_HairsWildWhiteList.add("ApachiiHairHuman28");
        m_HairsWildWhiteList.add("ApachiiHairHuman29");
        m_HairsWildWhiteList.add("ApachiiHairHuman30");
        m_HairsWildWhiteList.add("ApachiiHairHuman31");
        m_HairsWildWhiteList.add("ApachiiHairHuman32");
        m_HairsWildWhiteList.add("ApachiiHairHuman33");
        m_HairsWildWhiteList.add("ApachiiHairHuman34");
        m_HairsWildWhiteList.add("ApachiiHairHuman35");
        m_HairsWildWhiteList.add("ApachiiHairHuman36");
        m_HairsWildWhiteList.add("ApachiiHairHuman41");
        m_HairsWildWhiteList.add("ApachiiHairHuman42");
        m_HairsWildWhiteList.add("HairFemaleNord16");
        m_HairsWildWhiteList.add("HairFemaleNord21");

        m_HairsLowerWhiteList.add("HairFemaleImperial1");
        m_HairsLowerWhiteList.add("HairFemaleNord1");
        m_HairsLowerWhiteList.add("HairFemaleNord2");
        m_HairsLowerWhiteList.add("HairFemaleNord3");
        m_HairsLowerWhiteList.add("HairFemaleNord4");
        m_HairsLowerWhiteList.add("HairFemaleNord5");
        m_HairsLowerWhiteList.add("HairFemaleNord6");
        m_HairsLowerWhiteList.add("HairFemaleNord7");
        m_HairsLowerWhiteList.add("HairFemaleNord8");
        m_HairsLowerWhiteList.add("HairFemaleNord9");
        m_HairsLowerWhiteList.add("HairFemaleNord10");
        m_HairsLowerWhiteList.add("HairFemaleNord11");
        m_HairsLowerWhiteList.add("HairFemaleNord12");
        m_HairsLowerWhiteList.add("HairFemaleNord13");
        m_HairsLowerWhiteList.add("HairFemaleNord15");
        m_HairsLowerWhiteList.add("HairFemaleNord17");
        m_HairsLowerWhiteList.add("HairFemaleNord18");
        m_HairsLowerWhiteList.add("HairFemaleNord19");
        m_HairsLowerWhiteList.add("HairFemaleNord20");
        m_HairsLowerWhiteList.add("HairFemaleNord22");
        m_HairsLowerWhiteList.add("HairFemaleRedguard03");
        m_HairsLowerWhiteList.add("HairFemaleRedguard04");

        m_HairsLowerWhiteList.add("ApachiiHairF10");
        m_HairsLowerWhiteList.add("ApachiiHairF11");
        m_HairsLowerWhiteList.add("ApachiiHairF20");
        m_HairsLowerWhiteList.add("ApachiiHairF35");
        m_HairsLowerWhiteList.add("ApachiiHairF39");

    }

    public void createOblivionWhiteList() {

        m_HairsNormalWhiteList.add("OblHair001");
        m_HairsNormalWhiteList.add("OblHair010");
        m_HairsNormalWhiteList.add("OblHair013");
        m_HairsNormalWhiteList.add("OblHair015");
        m_HairsNormalWhiteList.add("OblHair018");
        m_HairsNormalWhiteList.add("OblHair019");
        m_HairsNormalWhiteList.add("OblHair016");
        m_HairsNormalWhiteList.add("OblHair019");
        m_HairsNormalWhiteList.add("OblHair020");
        m_HairsNormalWhiteList.add("OblHair021");
        m_HairsNormalWhiteList.add("OblHair024");
        m_HairsNormalWhiteList.add("OblHair025");
        m_HairsNormalWhiteList.add("OblHair028");
        m_HairsNormalWhiteList.add("OblHair029");
        m_HairsNormalWhiteList.add("OblHair030");
        m_HairsNormalWhiteList.add("OblHair031");
        m_HairsNormalWhiteList.add("OblHair033");

        m_HairsNormalWhiteList.add("OblHair034");
        m_HairsNormalWhiteList.add("OblHair035");
        m_HairsNormalWhiteList.add("OblHair036");
        m_HairsNormalWhiteList.add("OblHair038");
        m_HairsNormalWhiteList.add("OblHair040");
        m_HairsNormalWhiteList.add("OblHair044");
        m_HairsNormalWhiteList.add("OblHair045");
        m_HairsNormalWhiteList.add("OblHair046");
        m_HairsNormalWhiteList.add("OblHair047");
        m_HairsNormalWhiteList.add("OblHair050");
        m_HairsNormalWhiteList.add("OblHair061");
        m_HairsNormalWhiteList.add("OblHair062");
        m_HairsNormalWhiteList.add("OblHair064");
        m_HairsNormalWhiteList.add("OblHair071");
        m_HairsNormalWhiteList.add("OblHair072");
        m_HairsNormalWhiteList.add("OblHair073");
        m_HairsNormalWhiteList.add("OblHair075");
        m_HairsNormalWhiteList.add("OblHair076");
        m_HairsNormalWhiteList.add("OblHair077");
        m_HairsNormalWhiteList.add("OblHair078");
        m_HairsNormalWhiteList.add("OblHair079");
        m_HairsNormalWhiteList.add("OblHair080");
        m_HairsNormalWhiteList.add("OblHair081");
        m_HairsNormalWhiteList.add("OblHair083");

        m_HairsNormalWhiteList.add("OblHair102");
        m_HairsNormalWhiteList.add("OblHair105");

        m_HairsNormalWhiteList.add("OblHair108");
        m_HairsNormalWhiteList.add("OblHair112");
        m_HairsNormalWhiteList.add("OblHair116");
        m_HairsNormalWhiteList.add("OblHair121");
        m_HairsNormalWhiteList.add("OblHair123");
        m_HairsNormalWhiteList.add("OblHair127");
        m_HairsNormalWhiteList.add("OblHair128");
        m_HairsNormalWhiteList.add("OblHair129");
        m_HairsNormalWhiteList.add("OblHair130");
        m_HairsNormalWhiteList.add("OblHair134");
        m_HairsNormalWhiteList.add("OblHair139");
        m_HairsNormalWhiteList.add("OblHair141");
        m_HairsNormalWhiteList.add("OblHair150");
        m_HairsNormalWhiteList.add("OblHair154");
        m_HairsNormalWhiteList.add("OblHair156");
        m_HairsNormalWhiteList.add("OblHair158");
        m_HairsNormalWhiteList.add("OblHair160");

        m_HairsUpperWhiteList.add("OblHair002");
        m_HairsUpperWhiteList.add("OblHair010");
        m_HairsUpperWhiteList.add("OblHair012");
        m_HairsUpperWhiteList.add("OblHair029");

        m_HairsUpperWhiteList.add("OblHair051");

        m_HairsUpperWhiteList.add("OblHair091");
        m_HairsUpperWhiteList.add("OblHair116");
        m_HairsUpperWhiteList.add("OblHair144");
        m_HairsUpperWhiteList.add("OblHair150");

        m_HairsWildWhiteList.add("OblHair003");
        m_HairsWildWhiteList.add("OblHair015");
        m_HairsWildWhiteList.add("OblHair016");
        m_HairsWildWhiteList.add("OblHair018");
        m_HairsWildWhiteList.add("OblHair019");
        m_HairsWildWhiteList.add("OblHair020");
        m_HairsWildWhiteList.add("OblHair021");
        m_HairsWildWhiteList.add("OblHair022");
        m_HairsWildWhiteList.add("OblHair023");
        m_HairsWildWhiteList.add("OblHair025");
        m_HairsWildWhiteList.add("OblHair035");
        m_HairsWildWhiteList.add("OblHair039");
        m_HairsWildWhiteList.add("OblHair040");
        m_HairsWildWhiteList.add("OblHair050");
        m_HairsWildWhiteList.add("OblHair052");
        m_HairsWildWhiteList.add("OblHair054");
        m_HairsWildWhiteList.add("OblHair055");
        m_HairsWildWhiteList.add("OblHair056");
        m_HairsWildWhiteList.add("OblHair059");

        m_HairsWildWhiteList.add("OblHair060");
        m_HairsWildWhiteList.add("OblHair061");
        m_HairsWildWhiteList.add("OblHair064");

        m_HairsWildWhiteList.add("OblHair071");
        m_HairsWildWhiteList.add("OblHair072");
        m_HairsWildWhiteList.add("OblHair074");
        m_HairsWildWhiteList.add("OblHair075");
        m_HairsWildWhiteList.add("OblHair076");
        m_HairsWildWhiteList.add("OblHair079");
        m_HairsWildWhiteList.add("OblHair081");
        m_HairsWildWhiteList.add("OblHair085");
        m_HairsWildWhiteList.add("OblHair092");
        m_HairsWildWhiteList.add("OblHair094");
        m_HairsWildWhiteList.add("OblHair097");
        m_HairsWildWhiteList.add("OblHair099");
        m_HairsWildWhiteList.add("OblHair101");
        m_HairsWildWhiteList.add("OblHair102");
        m_HairsWildWhiteList.add("OblHair103");

        m_HairsWildWhiteList.add("OblHair108");
        m_HairsWildWhiteList.add("OblHair121");
        m_HairsWildWhiteList.add("OblHair126");
        m_HairsWildWhiteList.add("OblHair127");
        m_HairsWildWhiteList.add("OblHair132");
        m_HairsWildWhiteList.add("OblHair135");
        m_HairsWildWhiteList.add("OblHair141");
        m_HairsWildWhiteList.add("OblHair143");
        m_HairsWildWhiteList.add("OblHair147");
        m_HairsWildWhiteList.add("OblHair151");
        m_HairsWildWhiteList.add("OblHair153");
        m_HairsWildWhiteList.add("OblHair154");
        m_HairsWildWhiteList.add("OblHair159");
        m_HairsWildWhiteList.add("OblHair160");
        m_HairsWildWhiteList.add("OblHair161");

        m_HairsLowerWhiteList.add("OblHair005");
        m_HairsLowerWhiteList.add("OblHair008");
        m_HairsLowerWhiteList.add("OblHair026");
        m_HairsLowerWhiteList.add("OblHair052");
        m_HairsLowerWhiteList.add("OblHair053");
        m_HairsLowerWhiteList.add("OblHair054");
        m_HairsLowerWhiteList.add("OblHair057");
        m_HairsLowerWhiteList.add("OblHair085");
    }

    public void createHairListsOutOfWhiteLists() {
        //    createOblivionWhiteList();
        createApachiiWhileList();
        for (HDPT headpart : SkyProcStarter.merger.getHeadParts()) {
            if (m_HairsNormalWhiteList.contains(headpart.getEDID())) {
                m_HairsNormalList.add(headpart);
            }
            if (m_HairsUpperWhiteList.contains(headpart.getEDID())) {
                m_HairsUpperList.add(headpart);
            }
            if (m_HairsWildWhiteList.contains(headpart.getEDID())) {
                m_HairsWildList.add(headpart);
            }
            if (m_HairsLowerWhiteList.contains(headpart.getEDID())) {
                m_HairsLowerList.add(headpart);
            }
        }
    }

    public static FormID getOldHair(NPC_ n) {
        for (FormID hp : n.getHeadParts()) {
            MajorRecord foundhp = SkyProcStarter.merger.getHeadParts().get(hp);
            if (foundhp != null) {
                if (foundhp.getEDID().toLowerCase().contains("hair")) {
                    return hp;
                }
            }
        }
        return null;
    }

    public NPC_ changeHair(NPC_ n) {
        HDPT hair = null;
        if (n.getClass() != null) {
            if (n.getClass().getName().contains("Beggar")
                    || n.getClass().getName().contains("Miner")
                    || n.getClass().getName().contains("Prisoner")
                    || n.getClass().getName().contains("Farmer")) {
                if (m_HairsLowerList.size() > 0) {
                    hair = m_HairsLowerList.get(RBS_Randomize.toInt(n.getEDID(), 0, m_HairsLowerList.size()));
                }
            } else if (n.getClass().getName().contains("Bandit")
                    || n.getClass().getName().contains("Barbarian")
                    || n.getClass().getName().contains("Forsworn")) {
                if (m_HairsWildList.size() > 0) {
                    hair = m_HairsWildList.get(RBS_Randomize.toInt(n.getEDID(), 0, m_HairsWildList.size()));
                }
            } else if (n.getClass().getName().contains("Bard")
                    || n.getClass().getName().contains("Citizen")
                    || n.getClass().getName().contains("Forsworn")) {
                if (m_HairsUpperList.size() > 0) {
                    hair = m_HairsUpperList.get(RBS_Randomize.toInt(n.getEDID(), 0, m_HairsUpperList.size()));
                }
            } else {
                if (m_HairsNormalList.size() > 0) {
                    hair = m_HairsNormalList.get(RBS_Randomize.toInt(n.getEDID(), 0, m_HairsNormalList.size()));
                }
            }
        }

        if (hair != null) {
            if (getOldHair(n) !=null) {
                n.removeHeadPart(getOldHair(n));
                n.addHeadPart(hair.getForm());
            }
        }

        /*
         if (!hair.getBaseTexture().equals("")) {
         String pathHairMesh = pathMeshes + hair.getModelData().getFileName().toString();
         File fileHair = new File(pathHairMesh);
         if (fileHair.exists()) {
         hair.set(MajorRecord.MajorFlags.VisibleWhenDistant, false);
         m_npc.getHeadParts().set(list, hair.getForm());
         } else {
         JOptionPane.showConfirmDialog(null, "mesh error", "Does not exist: " + hair.getModelData().toString(), JOptionPane.YES_NO_OPTION);
         }
         } else {
         JOptionPane.showConfirmDialog(null, "error", "no texturedata", JOptionPane.YES_NO_OPTION);
         }
         }
         * */
        return (n);
    }

    public void changeHairAllFemales() {
        createHairListsOutOfWhiteLists();
        int counter = 0;
        int max = ListNPCFemale.size();
        for (NPC_ n : ListNPCFemale) {
            counter++;
            SPProgressBarPlug.setStatusNumbered(counter, max, "deploying new hairstyles");
            if (!RBS_Race.ListBeastRaces.contains(n.getRace())) {
                n = changeHair(n);
            }
            SkyProcStarter.patch.addRecord(n);
        }
    }
}
