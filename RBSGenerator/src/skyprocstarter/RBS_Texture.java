/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMA;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.SPGlobal;

import skyproc.TXST;
import skyproc.genenums.Gender;

import skyproc.gui.SPProgressBarPlug;

public class RBS_Texture {

    public static List<TXST> ListRBSTXSTMerger = new ArrayList<>();
    public static Map<String, FormID> patchTXSTMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<String, FormID> vanillaTXSTMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaTXSTMapKeyForm = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchTXSTMapKeyForm = new ConcurrentHashMap<>();
    public static int n2 = 0;
    public static int c2 = 0;
    public static int d2 = 0;
    public static int s2 = 0;
    public static int mn2 = 0;
    public static int mc2 = 0;
    public static int ms2 = 0;

    RBS_Texture() {
        for (TXST t : SkyProcStarter.merger.getTextureSets()) {
            if (t.getColorMap().toLowerCase().equals("actors\\character\\female\\femalebody_1.dds") || t.getColorMap().toLowerCase().equals("actors\\character\\female\\femalehands_1.dds") || t.getEDID().contains("SkinBodyMale")) {
                ListRBSTXSTMerger.add(t);
                vanillaTXSTMapKeyEDID.put(t.getEDID(), t.getForm());
                vanillaTXSTMapKeyForm.put(t.getForm(), t.getEDID());
            }
        }
    }

    public void textures() throws Exception {
        SPProgressBarPlug.setStatus("creating new texture entries");
        Boolean patched = false;
        for (TXST t : ListRBSTXSTMerger) {

            TXST tt = (TXST) SkyProcStarter.patch.makeCopy(t, t.getEDID() + "RBS_F");
            if (!t.getColorMap().equals("")) {
                String fileNameSource = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator + t.getColorMap();
                String fileNamePatch = "RBS" + File.separator + "female" + File.separator + t.getColorMap();
                File targetPath = new File(fileNameSource);
                if (targetPath.exists()) {
                    tt.setColorMap(fileNamePatch);
                    SkyProcStarter.patch.addRecord(tt);
                }
            }


            if (!t.getDetailMap().equals("")) {
                String fileNameSource = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator + t.getDetailMap();
                String fileNamePatch = "RBS" + File.separator + "female" + File.separator + t.getDetailMap();
                File targetPath = new File(fileNameSource);
                if (targetPath.exists()) {
                    tt.setDetailMap(fileNamePatch);
                    SkyProcStarter.patch.addRecord(tt);
                }
            }


            if (!t.getEnvironmentMap().equals("")) {
                String fileNameSource = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator + t.getEnvironmentMap();
                String fileNamePatch = "RBS" + File.separator + "female" + File.separator + t.getEnvironmentMap();
                File targetPath = new File(fileNameSource);
                if (targetPath.exists()) {
                    tt.setEnvironmentMap(fileNamePatch);
                    SkyProcStarter.patch.addRecord(tt);
                }
            }


            if (!t.getNormalMap().equals("")) {
                String fileNameSource = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator + t.getNormalMap();
                String fileNamePatch = "RBS" + File.separator + "female" + File.separator + t.getNormalMap();
                File targetPath = new File(fileNameSource);
                if (targetPath.exists()) {
                    tt.setNormalMap(fileNamePatch);
                    SkyProcStarter.patch.addRecord(tt);
                }
            }


            if (!t.getToneMap().equals("")) {
                String fileNameSource = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator + t.getToneMap();
                String fileNamePatch = "RBS" + File.separator + "female" + File.separator + t.getToneMap();
                File targetPath = new File(fileNameSource);
                if (targetPath.exists()) {
                    tt.setToneMap(fileNamePatch);
                    SkyProcStarter.patch.addRecord(tt);
                }
            }
        }
        for (TXST t : SkyProcStarter.patch.getTextureSets()) {
            patchTXSTMapKeyEDID.put(t.getEDID(), t.getForm());
            patchTXSTMapKeyForm.put(t.getForm(), t.getEDID());
        }
    }

    public void CreateTextureSetsSkinBodyFemale_1RBS() throws Exception {
        // it copies SkinBodyFemale and changes the textures getting out of RBS folders.

        for (TXST t : ListRBSTXSTMerger) {
            if (t.getEDID().equals("SkinBodyFemale_1")) {
                NumberFormat formatter = new DecimalFormat("000");
                String sourcePath = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator;
                String targetPath = "RBS" + File.separator + "female" + File.separator;
                int counter = 1;
                File sourcePath3 = new File(sourcePath + "normal" + File.separator);
                File[] normalmaps = sourcePath3.listFiles();
                for (int n = 0; n < normalmaps.length; n++) {
                    counter = counter + 1;
                    n2 = n + 1;
                    c2 = 0;
                    d2 = 0;
                    s2 = 0;
                    TXST tt = (TXST) SkyProcStarter.patch.makeCopy(t, t.getEDID() + "RBS_F_TEXT" + n2 + "_" + c2 + "_" + d2 + "_" + s2);
                    tt.setNormalMap(normalmaps[n].toString().replace(SkyProcStarter.path + "textures", ""));
                }
            }
        }
    }

    public void CreateTextureSetsSkinBodyFemale_1RBS_old() throws Exception {
        // it copies SkinBodyFemale and changes the textures getting out of RBS folders.

        for (TXST t : ListRBSTXSTMerger) {
            if (t.getEDID().equals("SkinBodyFemale_1")) {
                NumberFormat formatter = new DecimalFormat("000");
                String sourcePath = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "female" + File.separator;
                String targetPath = "RBS" + File.separator + "female" + File.separator;
                int counter = 1;
                File sourcePath1 = new File(sourcePath + "color" + File.separator);
                File[] colormaps = sourcePath1.listFiles();

                File sourcePath2 = new File(sourcePath + "detail" + File.separator);
                File[] detailmaps = sourcePath2.listFiles();

                File sourcePath3 = new File(sourcePath + "normal" + File.separator);
                File[] normalmaps = sourcePath3.listFiles();

                File sourcePath4 = new File(sourcePath + "specular" + File.separator);
                File[] specularmaps = sourcePath4.listFiles();

                for (int n = 0; n < normalmaps.length; n++) {
                    for (int c = 0; c < colormaps.length; c++) {
                        for (int d = 0; d < detailmaps.length; d++) {
                            for (int s = 0; s < specularmaps.length; s++) {
                                counter = counter + 1;
                                n2 = n + 1;
                                c2 = c + 1;
                                d2 = d + 1;
                                s2 = s + 1;
                                TXST tt = (TXST) SkyProcStarter.patch.makeCopy(t, t.getEDID() + "RBS_F_TEXT" + n2 + "_" + c2 + "_" + d2 + "_" + s2);
                                tt.setNormalMap(normalmaps[n].toString().replace(SkyProcStarter.path + "textures", ""));
                                tt.setDetailMap(detailmaps[d].toString().replace(SkyProcStarter.path + "textures", ""));
                                tt.setColorMap(colormaps[c].toString().replace(SkyProcStarter.path + "textures", ""));
                                tt.setSpecularityMap(specularmaps[s].toString().replace(SkyProcStarter.path + "textures", ""));
                                //          SkyProcStarter.patch.addRecord(tt);
                            }
                        }
                    }
                }
            }
        }
    }

    public void CreateTextureSetsSkinBodyMale_1RBS() throws Exception {
        // it copies SkinBodyFemale and changes the textures getting out of RBS folders.
        SPProgressBarPlug.setStatus("Create texture sets skin Body Male");
        for (TXST t : ListRBSTXSTMerger) {
            if (t.getEDID().contains("SkinBodyMale_1")) {
                NumberFormat formatter = new DecimalFormat("000");
                String sourcePath = SkyProcStarter.path + "textures" + File.separator + "RBS" + File.separator + "male" + File.separator;
                String targetPath = "RBS" + File.separator + "male" + File.separator;
                int counter = 1;
                File sourcePath1 = new File(sourcePath + "color" + File.separator);
                File[] colormaps = sourcePath1.listFiles();

                //  File sourcePath2 = new File(sourcePath + "detail" + File.separator);
                //  File[] detailmaps = sourcePath2.listFiles();

                File sourcePath3 = new File(sourcePath + "normal" + File.separator);
                File[] normalmaps = sourcePath3.listFiles();

                File sourcePath4 = new File(sourcePath + "specular" + File.separator);
                File[] specularmaps = sourcePath4.listFiles();

                for (int n = 0; n < normalmaps.length; n++) {
                    for (int c = 0; c < colormaps.length; c++) {
                        //     for (int d = 0; d < detailmaps.length; d++) {
                        for (int s = 0; s < specularmaps.length; s++) {
                            counter = counter + 1;
                            mn2 = n + 1;
                            mc2 = c + 1;
                            // d2 = d + 1;
                            ms2 = s + 1;
                            TXST tt = (TXST) SkyProcStarter.patch.makeCopy(t, t.getEDID() + "RBS_M_TEXT" + mn2 + "_" + mc2 + "_" + ms2);
                            tt.setNormalMap(normalmaps[n].toString().replace(SkyProcStarter.path + "textures", ""));
                            //  tt.setDetailMap(detailmaps[d].toString().replace("s:\\skyrim\\data\\textures", ""));
                            tt.setColorMap(colormaps[c].toString().replace(SkyProcStarter.path + "textures", ""));
                            tt.setSpecularityMap(specularmaps[s].toString().replace(SkyProcStarter.path + "textures", ""));
                            //          SkyProcStarter.patch.addRecord(tt);
                        }
                    }
                }
                // }
            }
        }
    }

    public void CreateSkinNakedAlternativeTexturesFemale() throws Exception {
        SPProgressBarPlug.setStatus("Create SkinNaked alternative textures for females");
        Map<FormID, String> RBS_F_TEXTAAMapKeyForm = new ConcurrentHashMap<>();
        Map<String, FormID> RBS_F_TEXTAAMapKeyEDID = new ConcurrentHashMap<>();
        Map<FormID, String> ListSkinNakedAAForm = new ConcurrentHashMap<>();
        Map<String, FormID> ListSkinNakedAAEDID = new ConcurrentHashMap<>();
        List<ARMO> ListSkinNakedRBS_F = new ArrayList<>();
        for (ARMO armor : SkyProcStarter.patch.getArmors()) {
            if (armor.getEDID().contains("SkinNakedRBS_F")) {
                ListSkinNakedRBS_F.add(armor);
            }
        }
        List<ARMA> ListNakedTorsoRBS_F_TEXT = new ArrayList<>();
        for (ARMA aa : SkyProcStarter.patch.getArmatures()) {
            if (aa.getEDID().contains("RBS_F_TEXT")) {
                ListNakedTorsoRBS_F_TEXT.add(aa);
                RBS_F_TEXTAAMapKeyEDID.put(aa.getEDID(), aa.getForm());
                RBS_F_TEXTAAMapKeyForm.put(aa.getForm(), aa.getEDID());
            }
        }

        for (ARMA aa : SkyProcStarter.patch.getArmatures()) {
            if (aa.getEDID().contains("NakedTorsoRBS_Fstandard")) {
                ListSkinNakedAAEDID.put(aa.getEDID(), aa.getForm());
                ListSkinNakedAAForm.put(aa.getForm(), aa.getEDID());
            }
        }

        NumberFormat formatter = new DecimalFormat("000");
        for (int bodies = 1; bodies <= RBS_Main.amountBodyTypes; bodies++) {
            String s_bodies = formatter.format(bodies);
            for (ARMO sourceArmor : ListSkinNakedRBS_F) {
                if (sourceArmor.getEDID().equals("SkinNakedRBS_F" + "standard" + s_bodies)) {
                    for (ARMA sourceAA : ListNakedTorsoRBS_F_TEXT) {
                        if (sourceAA.getEDID().contains(s_bodies)) {
                            String[] splitResult = sourceAA.getEDID().split("RBS_F_TEXT");
                            String bodyID = splitResult[1];
                            String oldAA = splitResult[0];
                            ARMO targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(sourceArmor, sourceArmor.getEDID() + "Texture" + bodyID);
                            targetArmor.removeArmature(ListSkinNakedAAEDID.get("NakedTorsoRBS_Fstandard" + s_bodies));
                            if (sourceAA.getEDID().equals("NakedTorsoRBS_F" + "standard" + s_bodies + "RBS_F_TEXT" + bodyID)) {
                                targetArmor.addArmature(RBS_F_TEXTAAMapKeyEDID.get("NakedTorsoRBS_F" + "standard" + s_bodies + "RBS_F_TEXT" + bodyID));
                            }
                        }
                    }
                }
            }
        }
    }

    public void CreateSkinNakedAlternativeTexturesMale() throws Exception {
        SPProgressBarPlug.setStatus("Create SkinNaked alternative textures for males");
        ARMO skinNaked = (ARMO) SkyProcStarter.merger.getArmors().get(new FormID("000D64Skyrim.esm"));
        for (ARMA sourceAA : SkyProcStarter.patch.getArmatures()) {
            if (sourceAA.getEDID().contains("RBS_M_TEXT")) {
                String[] splitResult = sourceAA.getEDID().split("RBS_M_TEXT");
                String bodyID = splitResult[1];
                ARMO targetArmor = (ARMO) SkyProcStarter.patch.makeCopy(skinNaked, skinNaked.getEDID() + "RBS_M_TEXT" + bodyID);

                for (ARMA sourceAA2 : RBS_ARMA.ListVanillaAA) {
                    targetArmor.removeArmature(sourceAA2.getForm());
                    if ((sourceAA2.getEDID().equals("NakedHands") || sourceAA2.getEDID().equals("NakedFeet"))) {
                        targetArmor.addArmature(sourceAA2.getForm());
                    }

                }
                for (ARMA sourceAA3 : SkyProcStarter.patch.getArmatures()) {
                    if (sourceAA3.getEDID().equals("NakedTorsoRBS_M_TEXT" + bodyID)) {
                        targetArmor.addArmature(sourceAA3.getForm());

                    }
                }
                //        SkyProcStarter.patch.addRecord(targetArmor);
            }
        }
    }

    public void CreateAANakedTorsoAlternativeTexturesFemales() throws Exception {
        SPProgressBarPlug.setStatus("Create AA naked torso alternative textures for females");
        NumberFormat formatter = new DecimalFormat("000");
        List<ARMA> ListRBSAANakedTorso = new ArrayList<>();
        for (ARMA sourceAA : SkyProcStarter.patch.getArmatures()) {
            if (sourceAA.getEDID().contains("NakedTorsoRBS_F")) {
                ListRBSAANakedTorso.add(sourceAA);
            }
        }
        for (int bodies = 1; bodies <= RBS_Main.amountBodyTypes; bodies++) {
            String s_bodies = formatter.format(bodies);
            for (int list = 0; list < ListRBSAANakedTorso.size(); list++) {
                if (ListRBSAANakedTorso.get(list).getEDID().equals("NakedTorsoRBS_F" + "standard" + s_bodies)) {
                    for (TXST t : SkyProcStarter.patch.getTextureSets()) {
                        if (t.getEDID().contains("RBS_F_TEXT")) {
                            String[] splitResult = t.getEDID().split("RBS_F_TEXT");
                            String bodyID = splitResult[1];
                            ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(ListRBSAANakedTorso.get(list), ListRBSAANakedTorso.get(list).getEDID() + "RBS_F_TEXT" + bodyID);
                            targetAA.setSkinTexture(t.getForm(), Gender.FEMALE);
                            //          patch.addRecord(targetAA);
                        }
                    }
                }
            }
        }
    }

    public void CreateAANakedTorsoAlternativeTexturesFemalesOld() throws Exception {
        NumberFormat formatter = new DecimalFormat("000");
        for (int bodies = 1; bodies <= RBS_Main.amountBodyTypes; bodies++) {
            String s_bodies = formatter.format(bodies);
            for (ARMA sourceAA : SkyProcStarter.patch.getArmatures()) {
                if (sourceAA.getEDID().equals("NakedTorsoRBS_F" + "standard" + s_bodies)) {
                    for (TXST t : SkyProcStarter.patch.getTextureSets()) {
                        if (t.getEDID().contains("RBS_F_TEXT")) {
                            String[] splitResult = t.getEDID().split("RBS_F_TEXT");
                            String bodyID = splitResult[1];
                            ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(sourceAA, sourceAA.getEDID() + "RBS_F_TEXT" + bodyID);
                            targetAA.setSkinTexture(t.getForm(), Gender.FEMALE);
                            //   patch.addRecord(targetAA);
                        }
                    }
                }
            }
        }
    }

    public void CreateAANakedTorsoAlternativeTexturesMales() throws Exception {
        SPProgressBarPlug.setStatus("Create AA naked torso alternative textures males");
        NumberFormat formatter = new DecimalFormat("000");
        ARMA NakedTorso = (ARMA) SkyProcStarter.merger.getArmatures().get(new FormID("000D67Skyrim.esm"));
        for (int bodies = 1; bodies <= RBS_Main.amountBodyTypesMale; bodies++) {
            String s_bodies = formatter.format(bodies);
            for (TXST t : SkyProcStarter.patch.getTextureSets()) {
                if (t.getEDID().contains("RBS_M_TEXT")) {
                    String[] splitResult = t.getEDID().split("RBS_M_TEXT");
                    String bodyID = splitResult[1];
                    ARMA targetAA = (ARMA) SkyProcStarter.patch.makeCopy(NakedTorso, NakedTorso.getEDID() + "RBS_M_TEXT" + bodyID);
                    targetAA.setSkinTexture(t.getForm(), Gender.MALE);
                    //           patch.addRecord(targetAA);
                }
            }
        }
    }
}
