import Consultas.*;

import javax.swing.*;
import java.awt.event.*;

class NuevoProducto extends JFrame{

    MySql_Java consultas = new MySql_Java();
    String[] categorias = consultas.getCategorias();

    public NuevoProducto(){
        setTitle("Nuevo producto");
        setSize(300,400);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        initComponentes();

        setVisible(true);
    }

    public void initComponentes(){
        
        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(20, 20, 150, 50);
        
        JLabel precio = new JLabel("Precio");
        precio.setBounds(20, 50, 150, 50);

        JLabel cantidad = new JLabel("Cantidad");
        cantidad.setBounds(20, 80, 150, 50);

        JLabel categoria = new JLabel("Categoria");
        categoria.setBounds(20, 110, 150, 50);

        JTextField nombre_edit = new JTextField();
        nombre_edit.setBounds(100, 35, 150, 20);
        
        JTextField precio_edit = new JTextField();
        precio_edit.setBounds(100, 65, 150, 20);

        JTextField cantidad_edit = new JTextField();
        cantidad_edit.setBounds(100, 95, 150, 20);

        JComboBox<String> categoria_edit = new JComboBox<String>();
        categoria_edit.setBounds(100, 125, 150, 20);
        for(int i = 0; i < categorias.length ; i++){
            categoria_edit.addItem(categorias[i]);
        }
        
        JButton guardar = new JButton("Guardar");
        guardar.setBounds(40, 200, 200, 50);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(40, 270, 200, 50);

        JLabel mensaje_error = new JLabel("** Campo vacío");
        mensaje_error.setBounds(85, 150, 300, 50);
        mensaje_error.setVisible(false);
        
        add(nombre);
        add(precio);
        add(cantidad);
        add(categoria);

        add(nombre_edit);
        add(precio_edit);
        add(cantidad_edit);
        add(categoria_edit);
        add(mensaje_error);

        add(guardar);
        add(cancelar);

        guardar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(nombre_edit.getText().length()==0 || precio_edit.getText().length()==0 || cantidad_edit.getText().length()==0){
                    mensaje_error.setVisible(true);
                }else{
                    mensaje_error.setVisible(false);
                    Emergente ventana = new Emergente();
                    ventana.ventanaConfirmacion("");
                    ventana.confirmar.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            String pass = new String(ventana.contraseña.getPassword());
                            try {
                                if(consultas.ComprobacionLoggin(pass)){
                                    ManejoDatos_MySql insercion = new ManejoDatos_MySql();
                                    String nombre = nombre_edit.getText();
                                    Integer precio = Integer.parseInt(precio_edit.getText());
                                    Integer cantidad = Integer.parseInt(cantidad_edit.getText());
                                    String categoria = String.valueOf(categoria_edit.getSelectedItem());
                                    insercion.insertarProducto(nombre, precio, cantidad, categoria);
                                    ventana.emergente.dispose();
                                    dispose();
                                }else{
                                    ventana.mensaje_error.setVisible(true);
                                }
                            } catch (Exception e1) {}
                        }
                    });

                    ventana.cancelar.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            ventana.emergente.dispose();
                        }
                    }); 
                }
            }
        });

        cancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
    }
}
