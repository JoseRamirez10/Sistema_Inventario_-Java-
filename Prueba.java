import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import nicon.notify.core.Notification;

class Prueba {
    public static void main(String[] args){
        try {
            //UIManager.setLookAndFeel(new NimbusLookAndFeel());
            Notification.show("Titulo", "Mensaje", Notification.NICON_DARK_THEME, true, 2000);    
            Notification.show("Titulo2", "Mensaje2", Notification.NICON_LIGHT_THEME, true, 5000);    
        } catch (Exception e) {}
    }
}
