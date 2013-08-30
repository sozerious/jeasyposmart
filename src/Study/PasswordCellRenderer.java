/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author JAVA DEV
 */
public class PasswordCellRenderer extends DefaultTableCellRenderer {

    private static final String ASTERISKS = "************************";

    @Override
        public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
        int length =0;
        if (arg1 instanceof String) {
            length =  ((String) arg1).length();
        } else if (arg1 instanceof char[]) {
            length = ((char[])arg1).length;
        }
        setText(asterisks(length));
        return this;
    }

    private String asterisks(int length) {
        if (length > ASTERISKS.length()) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append('*');
            }
            return sb.toString();
        } else {
            return ASTERISKS.substring(0, length);
        }
    }
}
