package com.jamesaq12wsx.gymtime.util;

import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

/**
 * Validation Tool
 * @author James Lin
 * @date 2020-05-16
 */
public class ValidationUtils {

    /**
     * validate empty
     */
    public static void isNull(Object obj, String entity, String parameter , Object value){
        if(ObjectUtils.isNotEmpty(obj)){
            String msg = entity + " not existed "+ parameter +" is "+ value;
            throw new ApiRequestException(msg);
        }
    }

    /**
     * validate email
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }

}
