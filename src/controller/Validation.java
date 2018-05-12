/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PC
 */
public class Validation {
    
    private static final String VALID_GUEST_CREDENTIALS ="^[A-Za-z ]{1,100}$";
    
    public static boolean isValidCredentials(String credentials){
		Pattern credentialsPattern = Pattern.compile(VALID_GUEST_CREDENTIALS);
		Matcher credentialsMatcher = credentialsPattern.matcher(credentials);
		return credentialsMatcher.find();
	}
    
}
