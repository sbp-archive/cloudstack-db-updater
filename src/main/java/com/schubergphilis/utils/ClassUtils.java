package com.schubergphilis.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClassUtils {

    private ClassUtils() {
    }

    public static boolean doEquals(Object leftHandSide, Object rightHandSide, String... excludedFields) {
        return EqualsBuilder.reflectionEquals(leftHandSide, rightHandSide, excludedFields);
    }

    public static int doHashCode(Object object, String... excludedFields) {
        return HashCodeBuilder.reflectionHashCode(object, excludedFields);
    }

    public static String doToString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }

}
