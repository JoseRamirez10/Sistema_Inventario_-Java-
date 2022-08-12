import Consultas.SelectMySql;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

class Interfaz extends JFrame{

    JPanel log;
    JRadioButton productor;
    JPasswordField contraseña;
    JLabel mensaje;

    public Interfaz(){
        initComponentes();
    }

    public void initComponentes(){
        setTitle("Login");
        setSize(400,200);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        login();

        setVisible(true);

    }

    public void login(){
        log = new JPanel();
        log.setLayout(null);
        log.setBounds(0, 0, 400, 200);

        productor = new JRadioButton("Administrador");
        productor.setBounds(50,20,150,20);

        contraseña = new JPasswordField();
        contraseña.setBounds(200, 20, 150, 20);
        contraseña.setVisible(false);

        JRadioButton cliente = new JRadioButton("Cliente");
        cliente.setBounds(50,50,100,50);
        cliente.setSelected(true);

        ButtonGroup grupoLogg = new ButtonGroup();
        grupoLogg.add(productor);
        grupoLogg.add(cliente);

        JButton loggearse = new JButton("Continuar");
        loggearse.setBounds(100,100,200,50);

        mensaje = new JLabel();
        mensaje.setBounds(200, 50, 200, 50);

        log.add(productor);
        log.add(contraseña);
        log.add(cliente);
        log.add(mensaje);
        log.add(loggearse);

        productor.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                if(productor.isSelected()){
                    contraseña.setVisible(true);
                    contraseña.requestFocus();
                    mensaje.setText("");
                    mensaje.setVisible(true);
                }else{
                    contraseña.setVisible(false);
                    mensaje.setVisible(false);
                }
            }    
        });

        loggearse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                verificarContraseña();
            }
        });

        KeyListenerLoggin keyListener = new KeyListenerLoggin();
        contraseña.addKeyListener(keyListener);
        cliente.addKeyListener(keyListener);

        add(log);
    }

    public void verificarContraseña(){
        if(productor.isSelected()){
            String pass = new String(contraseña.getPassword());
            SelectMySql comprobandoLoggeo = new SelectMySql();
            if(pass.length() == 0){
                mensaje.setText("Ingrese una contraseña...");
            }else{
                if(comprobandoLoggeo.ComprobacionLoggin(pass)){
                    crearPanelPerfil("Vendedor");
                }
                else{
                    mensaje.setText("Contraseña incorrecta...");
                }
            }
        }else{
            crearPanelPerfil("Cliente");
        }
    }
    
    public void crearPanelPerfil(String perfil){
        remove(log);
        PanelProductos perfil_productor = new PanelProductos();
        setTitle(perfil);
        setSize(800,600);
        setLocationRelativeTo(null);
        add(perfil_productor);

        perfil_productor.cerrarSesion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                remove(perfil_productor);
                initComponentes();
            }
        });
    }
    
    public class KeyListenerLoggin implements KeyListener{
            @Override
            public void keyTyped(KeyEvent e){}

            @Override
            public void keyReleased(KeyEvent e){}

            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    verificarContraseña();
                }
            }
    }
}
