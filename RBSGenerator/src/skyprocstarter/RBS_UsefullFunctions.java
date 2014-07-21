/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class RBS_UsefullFunctions {

    public static void msgbox(String text) {
        int result = JOptionPane.showConfirmDialog(null, text, "Debug Window", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == 2) {
            System.exit(0);
        }
    }
}
