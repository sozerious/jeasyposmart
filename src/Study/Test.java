/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.awt.event.*;
import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        JPanel panel = new JPanel();

        /* add a new action named "foo" to the panel's action map */
        panel.getActionMap().put("foo", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("hello, world");
                }
            });

        /* connect two keystrokes with the newly created "foo" action:
           - a
           - CTRL-a
        */
        InputMap inputMap = panel.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK), "foo");
//        inputMap.put(KeyStroke.getKeyStroke(Character.valueOf('a'), 0), "foo");
//        inputMap.put(KeyStroke.getKeyStroke(Character.valueOf('a'), InputEvent.CTRL_DOWN_MASK), "foo");

        /* display the panel in a frame */
        JFrame frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
