/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.config;

import java.util.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author th567
 */
public class Base64PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence cs) {
        String encoded = Base64.getEncoder().encodeToString(cs.toString().getBytes());
        System.out.println("Encode: input={}, output={}".format(cs.toString(), encoded));
        return encoded;
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        boolean match = cs.toString().equals(string);
        System.out.println("matches: input={}, output={}".format(cs.toString(), string));
        return match;
    }
    
    
}
