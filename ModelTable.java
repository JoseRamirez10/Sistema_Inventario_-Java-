import Consultas.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;

class ModelTable {

    SelectMySql consultas = new SelectMySql();
    Object[][] datos;
    String[] columnas = {"ID", "Nombre", "Precio", "Cantidad","Categoria"};

    public JTable generarTabla(){
        datos = consultas.getProductos();
        DefaultTableModel dtm = new DefaultTableModel(datos,columnas){
            public boolean isCellEditable(int rowIndex,int columnIndex){
                return false;
            }
        };
        JTable tabla = new JTable(dtm);
        tabla.setPreferredScrollableViewportSize(new Dimension(480,390));
        return tabla;
    }

    public DefaultTableModel generarModelo(String modelo){
        switch(modelo){
            case "":
                datos = consultas.getProductos();
                break;
            case "CatASC":
                datos = consultas.getProductos_CategoriasASC();
                break;
            case "CatDESC":
                datos = consultas.getProductos_CategoriasDESC();
                break;
            case "Agotados_ID":
                datos = consultas.productosAgotados("ID");
                break;
            case "Agotados_ASC":
                datos = consultas.productosAgotados("ASC");
                break;
            case "Agotados_DESC":
                datos = consultas.productosAgotados("");

        }
        
        DefaultTableModel dtm = new DefaultTableModel(datos,columnas){
            public boolean isCellEditable(int rowIndex,int columnIndex){
                return false;
            }
        };
        return dtm;
    }

    public DefaultTableModel generarModelo(String modelo, int id){
        switch(modelo){
            case "ID":
                datos = consultas.consultaID(id);
                break;
        }
        
        DefaultTableModel dtm = new DefaultTableModel(datos,columnas){
            public boolean isCellEditable(int rowIndex,int columnIndex){
                return false;
            }
        };
        return dtm;
    }

    public DefaultTableModel generarModelo(String modelo, String nombre_categoria){
        switch(modelo){
            case "Nombre":
                datos = consultas.consultaNombre(nombre_categoria);
                break;
            case "Categoria":
                datos = consultas.consultaCategoria(nombre_categoria);
                break;
        }
        
        DefaultTableModel dtm = new DefaultTableModel(datos,columnas){
            public boolean isCellEditable(int rowIndex,int columnIndex){
                return false;
            }
        };
        return dtm;
    }
    
}
