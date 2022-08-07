
import nicon.notify.core.Notification;

class Notificacion{


    public Notificacion(String producto){
        
        Notification.show("Producto agotado", "La cantidad de "+producto+" es igual a 0.", Notification.NICON_LIGHT_THEME, true, 7000);
        
    }
}
