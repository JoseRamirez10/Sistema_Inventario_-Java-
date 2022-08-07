import Consultas.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Iterator;

class Contraseñas extends JFrame{

    MySql_Java consultas = new MySql_Java();
    JButton nueva;

    public Contraseñas(){
        setTitle("Gestor de contraseñas");
        setSize(400,300);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        initComponents();

        setVisible(true);
    }

    public void initComponents(){

        JPanel panel = new JPanel();
        panel.setBounds(0,0,400,300);

        JTable tabla = generarTabla();
        JScrollPane scrollPane = new JScrollPane(tabla);

        nueva = new JButton("Nueva contraseña");

        panel.add(scrollPane); 
        panel.add(nueva);
        add(panel);

        ManejoDatos_MySql manejo = new ManejoDatos_MySql();

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    Point p = e.getPoint();
                    int fila = tabla.rowAtPoint(p);
                    int columna = tabla.columnAtPoint(p);
                    if(columna == 1){ // Editar
                        Emergente ventana = new Emergente();
                        ventana.ventanaConfirmacion("Ingrese nueva contraseña");
                        ventana.confirmar.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e){
                                String nuevaContraseña = new String(ventana.contraseña.getPassword());
                                String antiguaContraseña = (String)tabla.getValueAt(fila, 0);
                                manejo.actualizarContraseña(antiguaContraseña, nuevaContraseña);
                                JTable nuevaTabla = generarTabla();
                                tabla.setModel(nuevaTabla.getModel());
                                ventana.emergente.dispose();
                            }
                        });

                        ventana.cancelar.addActionListener(new ActionListener(){
                            @Override 
                            public void actionPerformed(ActionEvent e){
                                ventana.emergente.dispose();
                            }
                        });
                    }else if(columna == 2){ // Eliminar
                        int confirmacion = JOptionPane.showConfirmDialog(null,"Confirmar operacion","Mensaje de confirmarción", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(confirmacion == 0){ //OK
                            String contraseñaEliminar = (String)tabla.getValueAt(fila, 0);
                            manejo.eliminarContraseña(contraseñaEliminar);;
                            JTable nuevaTabla = generarTabla();
                            tabla.setModel(nuevaTabla.getModel());
                        }
                    }
                }
            }
        });

        nueva.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Emergente ventana = new Emergente();
                ventana.ventanaConfirmacion("Ingrese nueva contraseña");                
                ventana.confirmar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String nuevaContraseña = new String(ventana.contraseña.getPassword());
                        manejo.insertarContraseña(nuevaContraseña);
                        JTable nuevaTabla = generarTabla();
                        tabla.setModel(nuevaTabla.getModel());
                        ventana.emergente.dispose();
                    }
                });

                ventana.cancelar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ventana.emergente.dispose();
                    }
                });
            }
        });

    }

    public JTable generarTabla(){
        String[] columnas = {"Contraseñas", "Editar","Eliminar"};
        Object[][] pass = consultas.getPass();
        DefaultTableModel dtm = new DefaultTableModel(pass,columnas){
            public boolean isCellEditable(int rowIndex,int columnIndex){
                return false;
            }
        };
        JTable tabla = new JTable(dtm);
        tabla.setPreferredScrollableViewportSize(new Dimension(300,200));
        for(int i = 0; i < pass.length; i++){
            tabla.setValueAt("--- Editar ---", i,1);
            tabla.setValueAt("--- Eliminar ---", i,2);
        }
        return tabla;
    }
}
