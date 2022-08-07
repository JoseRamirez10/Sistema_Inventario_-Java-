import Consultas.MySql_Java;
import Consultas.ManejoDatos_MySql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

class Productor extends JPanel{

    JPanel productos;
    JPanel perfil;

    JPanel busqueda;
    JPanel inventario;
    JPanel edicion;
    JPanel informacion;

    JLabel mensaje_informacion;

    JTable tabla;
    String[] columnas = {"ID", "Nombre", "Precio", "Cantidad","Categoria"};

    MySql_Java consultas = new MySql_Java();
    Object[][] datos = consultas.getProductos();
    DefaultTableModel dtm = new DefaultTableModel(datos, columnas){
        public boolean isCellEditable(int rowIndex,int columnIndex){
            return false;
        }
    };
    String[] categorias = consultas.getCategorias();

    ManejoDatos_MySql manejo = new ManejoDatos_MySql();

    JTextField id_edit;
    JTextField nombre_edit;
    JTextField precio_edit;
    JTextField cantidad_edit;
    JComboBox<String> categoria_edit;

    JButton editar;
    JButton guardar;
    JButton eliminar;

    JComboBox<String> filtro_busqueda;
    JComboBox<String> item_busqueda;

    JButton cerrarSesion;

    public Productor(){

        String[] agotados = consultas.consulta_cantidad();
        for(int i = 0; i<agotados.length; i++){
            Notificacion notificacion = new Notificacion(agotados[i]);
        }

        setBounds(0,0,800,600);
        setLayout(null);

        productos = new JPanel();
        productos.setLayout(null);

        busqueda = new JPanel();
        busqueda.setBounds(0,0,779,50);
        busqueda.setLayout(null);
        //busqueda.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
        panelBusqueda();

        inventario = new JPanel();
        inventario.setBounds(0,50,500,450);
        //inventario.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.black));
        panelInventario();

        edicion = new JPanel();
        edicion.setBounds(500,50,279,450);
        edicion.setLayout(null);
        //edicion.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));
        panelEdicion();

        informacion = new JPanel();
        informacion.setBounds(0,500,779,24);
        informacion.setLayout(null);
        informacion.setBackground(Color.white);
        //informacion.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        informacion.setBorder(BorderFactory.createMatteBorder(0,0,1,1,Color.BLACK));
        panelInformacion();
        
        productos.add(busqueda);
        productos.add(inventario);
        productos.add(edicion);
        productos.add(informacion);

        perfil = new JPanel();
        panelPerfil();

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setBounds(0,0,800,570);
        pestañas.addTab("Productos", productos);
        pestañas.addTab("Perfil", perfil);
        
        add(pestañas);
    }

    public void panelBusqueda(){

        String[] filtro = {"-- Seleccione busqueda --","ID", "Nombre", "Categoria", "Agotado"};
        filtro_busqueda = new JComboBox<String>();
        for(int i = 0; i < filtro.length; i++){
            filtro_busqueda.addItem(filtro[i]);
        }
        filtro_busqueda.setBounds(10,0,200,50);

        item_busqueda = new JComboBox<String>();
        item_busqueda.setBounds(210, 5, 450, 40);

        JButton boton_busqueda = new JButton("Buscar");
        boton_busqueda.setBounds(660, 5, 110, 40);

        JButton cancelar_busqueda = new JButton("Cancelar");
        cancelar_busqueda.setBounds(660, 5, 110, 40);

        busqueda.add(filtro_busqueda);
        busqueda.add(item_busqueda);
        busqueda.add(boton_busqueda);

        String[] orden = {"-- Ordenar --", "Categorias ASC", "Categorias DESC"};
        for(int i = 0; i < orden.length; i++){
            item_busqueda.addItem(orden[i]);
        }
        String[] IDs = consultas.getIDs();
        String[] nombres = consultas.getNombres();

        filtro_busqueda.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mensaje_informacion.setText("Seleccionando filtro...");
                if(filtro_busqueda.getSelectedItem() == "-- Seleccione busqueda --"){
                    item_busqueda.removeAllItems();
                    for(int i = 0; i < orden.length; i++){
                        item_busqueda.addItem(orden[i]);
                    }
                }else if(filtro_busqueda.getSelectedItem() == "ID"){
                    item_busqueda.removeAllItems();
                    for(int i = 0; i < IDs.length; i++){
                        item_busqueda.addItem(IDs[i]);
                    }
                }else if(filtro_busqueda.getSelectedItem() == "Nombre"){
                    item_busqueda.removeAllItems();
                    for(int i = 0; i < nombres.length; i++){
                        item_busqueda.addItem(nombres[i]);
                    }
                }else if(filtro_busqueda.getSelectedItem() == "Categoria"){
                    item_busqueda.removeAllItems();
                    for(int i = 0; i < categorias.length; i++){
                        item_busqueda.addItem(categorias[i]);
                    }
                }else{
                    item_busqueda.removeAllItems();
                    for(int i = 0; i < orden.length; i++){
                        item_busqueda.addItem(orden[i]);
                    }
                }
            }
        });

        boton_busqueda.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                id_edit.setText("");
                nombre_edit.setText("");
                precio_edit.setText("");
                cantidad_edit.setText("");

                id_edit.setEnabled(false);
                nombre_edit.setEnabled(false);
                precio_edit.setEnabled(false);
                cantidad_edit.setEnabled(false);
                categoria_edit.setEnabled(false);

                if(guardar.isVisible()){
                    guardar.setVisible(false);
                    editar.setVisible(true);
                }
                editar.setEnabled(false);
                eliminar.setEnabled(false);

                if(filtro_busqueda.getSelectedItem() == "-- Seleccione busqueda --"){
                    if(item_busqueda.getSelectedItem() == "-- Ordenar --"){
                        tabla.setModel(dtm);
                        mensaje_informacion.setText("Visualización de todos los productos.");
                    }else if(item_busqueda.getSelectedItem() == "Categorias ASC"){
                        Object[][] nuevoModelo = consultas.getProductos_CategoriasASC();
                        mensaje_informacion.setText("Orden ascendente de categorias...");
                        DefaultTableModel modelo_catASC = new DefaultTableModel(nuevoModelo, columnas){
                            public boolean isCellEditable(int rowIndex,int columnIndex){
                                return false;
                            }
                        };
                        tabla.setModel(modelo_catASC);
                    }else{
                        Object[][] nuevoModelo = consultas.getProductos_CategoriasDESC();
                        mensaje_informacion.setText("Orden descendente de categorias...");
                        DefaultTableModel modelo_catDesc = new DefaultTableModel(nuevoModelo, columnas){
                            public boolean isCellEditable(int rowIndex,int columnIndex){
                                return false;
                            }
                        };
                        tabla.setModel(modelo_catDesc);
                    }
                    
                }else if(filtro_busqueda.getSelectedItem() == "ID"){
                    Integer id = Integer.parseInt(String.valueOf(item_busqueda.getSelectedItem()));
                    mensaje_informacion.setText("Filtro: ID = "+id+".");
                    Object[][] nuevoModelo = consultas.consultaID(id);
                    DefaultTableModel modelo_ids = new DefaultTableModel(nuevoModelo, columnas){
                        public boolean isCellEditable(int rowIndex,int columnIndex){
                            return false;
                        }
                    };
                    tabla.setModel(modelo_ids);
                }else if(filtro_busqueda.getSelectedItem() == "Nombre"){
                    String nombre = String.valueOf(item_busqueda.getSelectedItem());
                    mensaje_informacion.setText("Filtro: Nombre = "+nombre+".");
                    Object[][] nuevoModelo = consultas.consultaNombre(nombre);
                    DefaultTableModel modelo_nombres = new DefaultTableModel(nuevoModelo, columnas){
                        public boolean isCellEditable(int rowIndex,int columnIndex){
                            return false;
                        }
                    };
                    tabla.setModel(modelo_nombres);
                }else if(filtro_busqueda.getSelectedItem() == "Categoria"){
                    String categoria = String.valueOf(item_busqueda.getSelectedItem());
                    mensaje_informacion.setText("Filtro: Categoria = "+categoria+".");
                    Object[][] nuevoModelo = consultas.consultaCategoria(categoria);
                    DefaultTableModel modelo_categoria = new DefaultTableModel(nuevoModelo, columnas){
                        public boolean isCellEditable(int rowIndex,int columnIndex){
                            return false;
                        }
                    };
                    tabla.setModel(modelo_categoria);
                }else{
                    if(item_busqueda.getSelectedItem() == "-- Ordenar --"){
                        mensaje_informacion.setText("Filtro: Productos agotados.");
                        Object[][] nuevoModelo = consultas.productosAgotados("ID");
                        DefaultTableModel modelo_categoria = new DefaultTableModel(nuevoModelo, columnas){
                            public boolean isCellEditable(int rowIndex,int columnIndex){
                                return false;
                            }
                        };
                        tabla.setModel(modelo_categoria);
                    }else if(item_busqueda.getSelectedItem() == "Categorias ASC"){
                        mensaje_informacion.setText("Filtro: Productos agotados ordenados por categorias ASC.");
                        Object[][] nuevoModelo = consultas.productosAgotados("ASC");
                        DefaultTableModel modelo_categoria = new DefaultTableModel(nuevoModelo, columnas){
                            public boolean isCellEditable(int rowIndex,int columnIndex){
                                return false;
                            }
                        };
                        tabla.setModel(modelo_categoria);
                    }else{
                        mensaje_informacion.setText("Filtro: Productos agotados ordenador por categorias DESC");
                        Object[][] nuevoModelo = consultas.productosAgotados("");
                        DefaultTableModel modelo_categoria = new DefaultTableModel(nuevoModelo, columnas){
                            public boolean isCellEditable(int rowIndex,int columnIndex){
                                return false;
                            }
                        };
                        tabla.setModel(modelo_categoria);
                    }
                }
               
            }
        });

    }

    public void panelInventario(){
        tabla = new JTable(dtm);
        tabla.setPreferredScrollableViewportSize(new Dimension(480, 390));
        //tabla.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton nuevoProducto = new JButton("+");
        JButton actualizar = new JButton("Actualizar");
        
        inventario.add(scrollPane);
        inventario.add(nuevoProducto);
        inventario.add(actualizar);

        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                datos = consultas.getProductos();
                dtm = new DefaultTableModel(datos, columnas){
                    public boolean isCellEditable(int rowIndex,int columnIndex){
                        return false;
                    }
                };
                tabla.setModel(dtm);

                filtro_busqueda.setSelectedIndex(0);
                item_busqueda.setSelectedIndex(0);

                id_edit.setText("");
                nombre_edit.setText("");
                precio_edit.setText("");
                cantidad_edit.setText("");

                id_edit.setEnabled(false);
                nombre_edit.setEnabled(false);
                precio_edit.setEnabled(false);
                cantidad_edit.setEnabled(false);

                if(guardar.isVisible()){
                    guardar.setVisible(false);
                    editar.setVisible(true);
                }
                editar.setEnabled(false);
                eliminar.setEnabled(false);

                mensaje_informacion.setText("Tabla actualizada");
            }
        });

        nuevoProducto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                mensaje_informacion.setText("Agregando nuevo producto...");
                NuevoProducto nuevo = new NuevoProducto();
            }
        });

    }

    public void panelEdicion(){
        
        JLabel titulo = new JLabel("--- Edicion ---");
        titulo.setBounds(90, -10, 200, 50);

        JLabel id = new JLabel("ID");
        id.setBounds(20, 20, 150, 50);
        
        JLabel nombre = new JLabel("Nombre");
        nombre.setBounds(20, 50, 150, 50);

        JLabel precio = new JLabel("Precio");
        precio.setBounds(20, 80, 150, 50);

        JLabel cantidad = new JLabel("Cantidad");
        cantidad.setBounds(20, 110, 150, 50);

        JLabel categoria = new JLabel("Categoria");
        categoria.setBounds(20, 140, 150, 50);

        id_edit = new JTextField();
        id_edit.setBounds(100, 35, 150, 20);
        id_edit.setEnabled(false);
        
        nombre_edit = new JTextField();
        nombre_edit.setBounds(100, 65, 150, 20);
        nombre_edit.setEnabled(false);

        precio_edit = new JTextField();
        precio_edit.setBounds(100, 95, 150, 20);
        precio_edit.setEnabled(false);

        cantidad_edit = new JTextField();
        cantidad_edit.setBounds(100, 125, 150, 20);
        cantidad_edit.setEnabled(false);

        categoria_edit = new JComboBox<String>();
        categoria_edit.setBounds(100, 155, 150, 20);
        for(int i = 0; i < categorias.length ; i++){
            categoria_edit.addItem(categorias[i]);
        }
        categoria_edit.setEnabled(false);

        editar = new JButton("Editar");
        editar.setBounds(40, 200, 200, 50);
        editar.setEnabled(false);
        
        guardar = new JButton("Guardar");
        guardar.setBounds(40, 200, 200, 50);
        guardar.setVisible(false);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(40, 270, 200, 50);
        eliminar.setEnabled(false);

        edicion.add(titulo);
        
        edicion.add(id);
        edicion.add(nombre);
        edicion.add(precio);
        edicion.add(cantidad);
        edicion.add(categoria);

        edicion.add(id_edit);
        edicion.add(nombre_edit);
        edicion.add(precio_edit);
        edicion.add(cantidad_edit);
        edicion.add(categoria_edit);

        edicion.add(editar);
        edicion.add(guardar);
        edicion.add(eliminar);
    
        tabla.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 1){
                    if(guardar.isVisible()){
                        guardar.setVisible(false);
                        editar.setVisible(true);
                        eliminar.setEnabled(false);
                        nombre_edit.setEnabled(false);
                        cantidad_edit.setEnabled(false);
                        precio_edit.setEnabled(false);
                        categoria_edit.setEnabled(false);           
                    }
                    editar.setEnabled(true);
                    Point p = e.getPoint();
                    int filaSeleccionada = tabla.rowAtPoint(p);
                    id_edit.setText((String)tabla.getValueAt(filaSeleccionada,0));
                    nombre_edit.setText((String)tabla.getValueAt(filaSeleccionada,1));
                    precio_edit.setText((String)tabla.getValueAt(filaSeleccionada,2));
                    cantidad_edit.setText((String)tabla.getValueAt(filaSeleccionada,3));
                    categoria_edit.setSelectedItem((String)tabla.getValueAt(filaSeleccionada,4));
                    mensaje_informacion.setText(nombre_edit.getText()+" seleccionado.");
                }
            }
        });

        editar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editar.setVisible(false);
                guardar.setVisible(true);

                // El id no puede ser modificado
                //id_edit.setEnabled(true);
                nombre_edit.setEnabled(true);
                cantidad_edit.setEnabled(true);
                precio_edit.setEnabled(true);
                categoria_edit.setEnabled(true);

                eliminar.setEnabled(true);

                mensaje_informacion.setText("Editando "+nombre_edit.getText()+".");
            }
        });

        guardar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                guardar.setVisible(false);
                editar.setVisible(true);

                nombre_edit.setEnabled(false);
                cantidad_edit.setEnabled(false);
                precio_edit.setEnabled(false);
                categoria_edit.setEnabled(false);

                eliminar.setEnabled(false);

                Integer id = Integer.parseInt(id_edit.getText());
                String nombre = nombre_edit.getText();
                Integer precio = Integer.parseInt(precio_edit.getText());
                Integer cantidad = Integer.parseInt(cantidad_edit.getText());
                String categoria = String.valueOf(categoria_edit.getSelectedItem());

                mensaje_informacion.setText("Guardando "+nombre+"...");

                Emergente ventana = new Emergente();
                ventana.ventanaConfirmacion("Para confirmar los cambios ingrese su contraseña:");
                ventana.confirmar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String pass = new String(ventana.contraseña.getPassword());
                        try {
                            if(consultas.ComprobacionLoggin(pass)){
                                manejo.actualizarProducto(id, nombre, precio, cantidad, categoria);
                                datos = consultas.getProductos();
                                dtm = new DefaultTableModel(datos, columnas){
                                    public boolean isCellEditable(int rowIndex,int columnIndex){
                                        return false;
                                    }
                                };
                                tabla.setModel(dtm);
                                ventana.emergente.dispose();
                                mensaje_informacion.setText(nombre+" editado correctamente.");
                            }
                            else{
                                ventana.mensaje_error.setVisible(true);
                            }
                        } catch (Exception e1) {}
                    }
                });
        
                ventana.cancelar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ventana.emergente.dispose();
                        mensaje_informacion.setText("Edicion cancelada...");
                    }
                }); 

                
                
            }
        });

        eliminar.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(ActionEvent e){
                mensaje_informacion.setText("Eliminando "+nombre_edit.getText()+"...");
                Emergente ventana = new Emergente();
                ventana.ventanaConfirmacion("Para confirmar los cambios ingrese su contraseña:");
                ventana.confirmar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String pass = new String(ventana.contraseña.getPassword());
                        try {
                            if(consultas.ComprobacionLoggin(pass)){
                                Integer id = Integer.parseInt(id_edit.getText());
                                manejo.eliminarProducto(id);
                                datos = consultas.getProductos();
                                dtm = new DefaultTableModel(datos, columnas){
                                    public boolean isCellEditable(int rowIndex,int columnIndex){
                                        return false;
                                    }
                                };
                                tabla.setModel(dtm);
                                mensaje_informacion.setText(nombre_edit.getText()+" eliminado correctamente.");
                                ventana.emergente.dispose();

                                id_edit.setText("");
                                nombre_edit.setText("");
                                cantidad_edit.setText("");
                                precio_edit.setText("");
                            }
                            else{
                                ventana.mensaje_error.setVisible(true);
                            }
                        } catch (Exception e1) {}
                    }
                });

                ventana.cancelar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        ventana.emergente.dispose();
                        mensaje_informacion.setText("Eliminación cancelada...");

                        id_edit.setText("");
                        nombre_edit.setText("");
                        cantidad_edit.setText("");
                        precio_edit.setText("");
                    }
                }); 

                guardar.setVisible(false);
                editar.setVisible(true);
                editar.setEnabled(false);

                nombre_edit.setEnabled(false);
                cantidad_edit.setEnabled(false);
                precio_edit.setEnabled(false);
                categoria_edit.setEnabled(false);
                eliminar.setEnabled(false);
            }
        });
    }

    public void panelInformacion(){
        mensaje_informacion = new JLabel("¡Bienvenido!");
        mensaje_informacion.setBounds(10,0,800,20);
        informacion.add(mensaje_informacion);
    }

    public void panelPerfil(){
        JButton contraseñas = new JButton("Administrar contraseñas");
        cerrarSesion = new JButton("Cerrar sesión");

        perfil.add(contraseñas);
        perfil.add(cerrarSesion);

        contraseñas.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Emergente ventana = new Emergente();
                ventana.ventanaConfirmacion("Ingrese contraseña de administrador");
                
                ventana.confirmar.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String pass = new String(ventana.contraseña.getPassword());
                        try {
                            if(consultas.ComprobacionLoggin(pass)){
                                ventana.emergente.dispose();
                                Contraseñas administrarContraseñas = new Contraseñas();
                            }
                            else{
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
        });

    }
}
