/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.util.Vector;

/**
 *
 * @author java-dev
 */
public class TestVector {
    
    public static void main(String[] args) {
        
//        int a = 0;
//        
//        test1 test1 = new test1(10);
//        test2 test2 = new Study.test2(test1);
//        
//        System.out.println(test1.a);
//        test1 = new test1(30);
//        test2.test.setA(50);
//        System.out.println(test2.test.a);
//        System.out.println(test1.a);
        
        Vector<String> v = new Vector();
        v.add("aaa");
        v.add("bbb");
        v.add("ccc");
        
        System.out.println(v);
        
        v.remove("ccc");
        
        System.out.println(v);
    }

}

class test1 {
    
    int a;

    public test1() {
    }

    public test1(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
     
}

class test2 {
    
    test1 test;

    public test2() {
    }

    public test2(test1 test) {
        this.test = test;
    }

    public test1 getTest() {
        return test;
    }

    public void setTest(test1 test) {
        this.test = test;
    }
    
}