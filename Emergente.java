import Consultas.*;

import javax.swing.*;

class Emergente{

    SelectMySql consultas = new SelectMySql();
    JFrame emergente;
    
    JPasswordField contraseña;
    JButton confirmar;
    JButton cancelar;

    JLabel mensaje_error;

    public Emergente(){

        emergente = new JFrame();
        emergente.setUndecorated(true);
        emergente.setSize(400,200);
        emergente.setResizable(false);
        emergente.setLayout(null);
        emergente.setLocationRelativeTo(null);

        contraseña = new JPasswordField();
        confirmar = new JButton("Aceptar");
        cancelar = new JButton("Cancelar");
        mensaje_error = new JLabel("Contraseña incorrecta");

    }

    public void ventanaConfirmacion(String mensaje){
        JLabel mensaje_confirmacion = new JLabel(mensaje);

        mensaje_confirmacion.setBounds(50,20,350,50);

        contraseña.setBounds(50, 65, 300, 20);
                
        confirmar.setBounds(50,100,150,50);

        cancelar.setBounds(200,100,150,50);

        mensaje_error.setBounds(125, 140, 150,50);
        mensaje_error.setVisible(false);

        emergente.add(mensaje_confirmacion);
        emergente.add(contraseña);
        emergente.add(confirmar);
        emergente.add(cancelar);
        emergente.add(mensaje_error);
        emergente.setVisible(true);
    } 
}
