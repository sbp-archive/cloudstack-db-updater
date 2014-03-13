package com.schubergphilis.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ClassUtils {

    public static boolean equals(Object leftHandSide, Object rightHandSide, String... excludedFields) {
        return EqualsBuilder.reflectionEquals(leftHandSide, rightHandSide, excludedFields);
    }

    public static int hashCode(Object object, String... excludedFields) {
        return HashCodeBuilder.reflectionHashCode(object, excludedFields);
    }

    public static String toString(Object object) {
        return ToStringBuilder.reflectionToString(object);
    }

}
