package com.TestField;

import java.lang.reflect.Method;
import java.util.*;

public class HashMapFunction {

    public static void main(String[] args) throws Exception {
        Map<Character, Method> commands = new HashMap<>();

        commands.put('h', HashMapFunction.class.getMethod("foo1"));

        char cmd = 'h';
        commands.get(cmd).invoke(null);
    }
    
    public static void foo1() {
    	System.out.println("Foo1");
    }
}