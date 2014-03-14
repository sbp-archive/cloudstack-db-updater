package com.schubergphilis.utils;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClassUtilsTest {

    @Test
    public void testEquals() throws Exception {
        String a = "bbb";
        String b = "aaa";

        assertFalse(ClassUtils.doEquals(b, a));
        assertTrue(ClassUtils.doEquals(a, a));
    }

    @Test
    public void testHashCode() throws Exception {
        String a = "bbb";
        String b = "aaa";

        assertNotEquals(ClassUtils.doHashCode(a), ClassUtils.doHashCode(b));
        assertEquals(ClassUtils.doHashCode(a), ClassUtils.doHashCode(a));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(ClassUtils.doToString(new Integer(1)), containsString("[value=1]"));
    }

}
