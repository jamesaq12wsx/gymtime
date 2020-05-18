package com.jamesaq12wsx.gymtime.util;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import org.hibernate.exception.ConstraintViolationException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

    /**
     * Get stack message
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    public static void throwForeignKeyException(Throwable e, String msg){
        Throwable t = e.getCause();
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause();
        }
        if (t != null) {
            throw new ApiRequestException(msg);
        }
        assert false;
        throw new ApiRequestException("Delete Failed");
    }

}
