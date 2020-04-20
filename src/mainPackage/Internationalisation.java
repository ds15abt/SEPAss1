package mainPackage;


import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user1
 */
public class Internationalisation {
    
    private static final String RESOURCE_PATH = "resources/MessageBundle";
    public final ResourceBundle strings;
    
    
    public Internationalisation (){
        
        this(new Locale("en", "GB"));
        
    }
    
    
    public Internationalisation(Locale locale) {
        
          strings = ResourceBundle.getBundle(RESOURCE_PATH, locale);
        
    }
    
    public String printlnWithParams(String message, Object... params) {
      //resource bundless do not have the capacity to format strings
      //so we have to use MessageFormat
      return MessageFormat.format(message, params); 
    }
    
    public String println(String message) {
        return message;
    }
    
    
}
