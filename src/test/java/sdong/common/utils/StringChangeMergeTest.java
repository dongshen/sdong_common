package sdong.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StringChangeMergeTest {
    @Test
    public void testGenerateMergeString_basic() {
        String original = "String a, b, c;";
        String str = "String a=null, b, c;";
        List<String> changeList = new ArrayList<String>();
        changeList.add(str);
        StringChangeMerge merge = new StringChangeMerge(original, changeList);
        merge.generateMergeString();
        assertEquals(str, merge.generateMergeString());
    }

    @Test
    public void testGenerateMergeString_same() {
        String original = "String a, b, c;";
        String str = original;
        List<String> changeList = new ArrayList<String>();
        changeList.add(str);
        StringChangeMerge merge = new StringChangeMerge(original, changeList);
        merge.generateMergeString();
        assertEquals(str, merge.generateMergeString());
    }

    @Test
    public void testGenerateMergeString_insert() {
        String original = "String a, b, c;";
        String str = "String a=null, b, c;";
        List<String> changeList = new ArrayList<String>();
        changeList.add(str);
        str = "String a, b=null, c;";
        changeList.add(str);
        str = "String a, b, c=null;";
        changeList.add(str);

        StringChangeMerge merge = new StringChangeMerge(original, changeList);
        merge.generateMergeString();
        assertEquals("String a=null, b=null, c=null;", merge.generateMergeString());
    }

    @Test
    public void testGenerateMergeString_mass() {
        String original = "(void)VOS_memcpy_s(v1,v2,v3,v4);";
        String str = "ret = VOS_memcpy_s(v1,v2,v3,v4);";
        List<String> changeList = new ArrayList<String>();
        changeList.add(str);
        str = "(void)memcpy_s(v1,v2,v3,v4);";
        changeList.add(str);

        StringChangeMerge merge = new StringChangeMerge(original, changeList);
        merge.generateMergeString();
        assertEquals("ret = memcpy_s(v1,v2,v3,v4);", merge.generateMergeString());

    }

    @Test
    public void testGenerateMergeString_mass2() {
        String original = "(void)VOS_memcpy_s(v1,v2,v3,v4);";
        String str = "(void)memcpy_s(v1,v2,v3,v4);";
        List<String> changeList = new ArrayList<String>();
        changeList.add(str);
        str = "ret = VOS_memcpy_s(v1,v2,v3,v4);";
        changeList.add(str);

        StringChangeMerge merge = new StringChangeMerge(original, changeList);
        merge.generateMergeString();
        assertEquals("ret = memcpy_s(v1,v2,v3,v4);", merge.generateMergeString());

    }
}
