package shared.utils;

import org.apache.commons.validator.routines.EmailValidator;


/**
 *
 * @author ghianco
 */
public class EmailUtils {
       public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
