/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sec.project.config;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author th567
 */
public class SecurityConfigurationTest {
    
    public SecurityConfigurationTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPasswordEncoder() {
        PasswordEncoder  pe = new PasswordEncoder() {  
            @Override
            public String encode(CharSequence cs) {
                return Base64.getEncoder().encodeToString(cs.toString().getBytes());
                
            }

            @Override
            public boolean matches(CharSequence cs, String string) {
                return cs.toString().equals(string);
            }
        };
    
        System.out.println("hevone: echo "+pe.encode("hevone"));
        assertTrue(pe.matches("hevone", "hevone"));
    }
}