package com.TestField;

import java.lang.reflect.Method;
import java.util.*;

public class HashMapFunction {

    public static void main(String[] args) throws Exception {
        Map<Character, Method> commands = new HashMap<>();
        
        Class<?>[] mParam1 = {String.class, Integer.class};
        commands.put('h', HashMapFunction.class.getMethod("foo1", mParam1));
        commands.put('v', HashMapFunction.class.getMethod("static_foo1", mParam1));

        char cmd = 'h';
        commands.get(cmd).invoke(new HashMapFunction(), "a", 10);
        cmd = 'v';
        commands.get(cmd).invoke(null, "b", 20);
    }
    
    public void foo1(String s, Integer n) {
    	System.out.println(s + n);
    }
    
    public static void static_foo1(String s, Integer n) {
    	System.out.println("This is static: " + s + n);
    }
}