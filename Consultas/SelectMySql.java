package Consultas;

import java.sql.*;

public class SelectMySql{
    
    public boolean ComprobacionLoggin(String contraseña){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select * from USUARIO_CONTRASEÑA");
            while(rs.next()){
                if(contraseña.equals(rs.getString(1))){
                    conexion.close();
                    return true;
                }
            }
            conexion.close();
        }catch(SQLException | ClassNotFoundException e1){}
        return false;
    }

    public Object[][] getProductos() {
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            Object[][] datos = new Object[tamaño][5];
            Statement s2 = conexion.createStatement();
            ResultSet rs = s2.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria order by 1 asc;");
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = String.valueOf(rs.getInt(1));
                datos[contador][1] = rs.getString(2);
                datos[contador][2] = String.valueOf(rs.getInt(3));
                datos[contador][3] = String.valueOf(rs.getInt(4));
                datos[contador][4] = rs.getString(5);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }

    public String[] getCategorias(){

        String[] listaNull = new String[0];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from categoria");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            String[] categorias = new String[tamaño];
            ResultSet rs = s.executeQuery("select categoria from categoria");
            int contador = 0;
            while(rs.next()){
                categorias[contador] = rs.getString(1);
                contador++;
            }
            conexion.close();
            return categorias;
        }catch(SQLException | ClassNotFoundException e1){}
        return listaNull;
    }

    public String[] getIDs(){
        String[] listaNull = new String[0];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            String[] IDs = new String[tamaño];
            ResultSet rs = s.executeQuery("select id_producto from producto");
            int contador = 0;
            while(rs.next()){
                IDs[contador] = String.valueOf(rs.getInt(1));
                contador++;
            }
            conexion.close();
            return IDs;
        }catch(SQLException | ClassNotFoundException e1){}
        return listaNull;
    }

    public String[] getNombres(){
        String[] listaNull = new String[0];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            String[] nombres = new String[tamaño];
            ResultSet rs = s.executeQuery("select nombre from producto");
            int contador = 0;
            while(rs.next()){
                nombres[contador] = rs.getString(1);
                contador++;
            }
            conexion.close();
            return nombres;
        }catch(SQLException | ClassNotFoundException e1){}
        return listaNull;
    }

    public Object[][] consultaID(int id){
        Object[][] datos = new Object[1][5];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
        
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where producto.id_producto ="+id+";");
            while(rs.next()){
                datos[0][0] = String.valueOf(rs.getInt(1));
                datos[0][1] = rs.getString(2);
                datos[0][2] = String.valueOf(rs.getInt(3));
                datos[0][3] = String.valueOf(rs.getInt(4));
                datos[0][4] = rs.getString(5);
            }
            conexion.close();
        }catch(SQLException | ClassNotFoundException e1){}
        return datos;
    }

    public Object[][] consultaNombre(String nombre){
        Object[][] datos = new Object[1][5];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where producto.nombre ='"+nombre+"';");
            while(rs.next()){
                datos[0][0] = String.valueOf(rs.getInt(1));
                datos[0][1] = rs.getString(2);
                datos[0][2] = String.valueOf(rs.getInt(3));
                datos[0][3] = String.valueOf(rs.getInt(4));
                datos[0][4] = rs.getString(5);
            }
            conexion.close();
        }catch(SQLException | ClassNotFoundException e1){}
        return datos;
    }

    public Object[][] consultaCategoria(String categoria) {
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet consulta_id_categoria = s.executeQuery("select id_categoria from categoria where categoria = '"+categoria+"'");
            int id_categoria = 0;
            while(consulta_id_categoria.next()){
                id_categoria = consulta_id_categoria.getInt(1);
            }
            ResultSet consulta_count = s.executeQuery("select count(*) from categoria_producto where id_categoria = '"+id_categoria+"'");
            int consult = 0;
            while(consulta_count.next()){
                consult = consulta_count.getInt(1);
            }
            Object[][] datos = new Object[consult][5];
            ResultSet rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where categoria.categoria = '"+categoria+"';");
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = String.valueOf(rs.getInt(1));
                datos[contador][1] = rs.getString(2);
                datos[contador][2] = String.valueOf(rs.getInt(3));
                datos[contador][3] = String.valueOf(rs.getInt(4));
                datos[contador][4] = rs.getString(5);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }

    public Object[][] getProductos_CategoriasASC() {
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            Object[][] datos = new Object[tamaño][5];
            ResultSet rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria order by 5 asc;");
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = String.valueOf(rs.getInt(1));
                datos[contador][1] = rs.getString(2);
                datos[contador][2] = String.valueOf(rs.getInt(3));
                datos[contador][3] = String.valueOf(rs.getInt(4));
                datos[contador][4] = rs.getString(5);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }

    public Object[][] getProductos_CategoriasDESC() {
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            Object[][] datos = new Object[tamaño][5];
            ResultSet rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria order by 5 desc;");
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = String.valueOf(rs.getInt(1));
                datos[contador][1] = rs.getString(2);
                datos[contador][2] = String.valueOf(rs.getInt(3));
                datos[contador][3] = String.valueOf(rs.getInt(4));
                datos[contador][4] = rs.getString(5);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }

    public String[] consulta_cantidad(){
        String[] listaNull = new String[0];
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto where cantidad = 0");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            String[] agotados = new String[tamaño];
            ResultSet rs = s.executeQuery("select nombre from producto where cantidad = 0;");
            int contador = 0;
            while(rs.next()){
                agotados[contador] = rs.getString(1);
                contador++;
            }
            conexion.close();
            return agotados;
        }catch(SQLException | ClassNotFoundException e1){}
        return listaNull;
    }

    public Object[][] productosAgotados(String orden){
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from producto where cantidad = 0");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            Object[][] datos = new Object[tamaño][5];
            ResultSet rs;
            if(orden.equals("ID")){
                rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where producto.cantidad = 0 order by 1;");
            }else if(orden.equals("ASC")){
                rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where producto.cantidad = 0 order by 5 asc;");
            }else{
                rs = s.executeQuery("select producto.id_producto, producto.nombre, producto.precio, producto.cantidad, categoria.categoria from producto inner join categoria_producto on producto.id_producto = categoria_producto.id_producto inner join categoria on categoria.id_categoria = categoria_producto.id_categoria where producto.cantidad = 0 order by 5 desc;");
            }
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = String.valueOf(rs.getInt(1));
                datos[contador][1] = rs.getString(2);
                datos[contador][2] = String.valueOf(rs.getInt(3));
                datos[contador][3] = String.valueOf(rs.getInt(4));
                datos[contador][4] = rs.getString(5);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }

    public Object[][] getPass(){
        Object[][] objectNull = {};
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select count(*) from USUARIO_CONTRASEÑA");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            Object[][] datos = new Object[tamaño][1];
            Statement s2 = conexion.createStatement();
            ResultSet rs = s2.executeQuery("select contrasenia from USUARIO_CONTRASEÑA");
            int contador = 0;
            while(rs.next()){
                datos[contador][0] = rs.getString(1);
                contador++;
            }
            conexion.close();
            return datos;
        }catch(SQLException | ClassNotFoundException e1){}
        return objectNull;
    }
}
