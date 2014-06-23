package skyprocstarter;

import java.util.ArrayList;
import java.util.HashSet;

public class RBS_FaceGen {

    public ArrayList<String> m_ListNPCFaceGen;

    public void removeDuplicatedEntries() {
        HashSet<String> hashSet = new HashSet<>(m_ListNPCFaceGen);
        m_ListNPCFaceGen.clear();
        m_ListNPCFaceGen.addAll(hashSet);
    }

    public void writeList() {
        //m_ListNPCFaceGen.
    }
}
