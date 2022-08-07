package Consultas;

import java.sql.*;

public class ManejoDatos_MySql {

    public void actualizarProducto(int id, String nombre, int precio, int cantidad, String categoria){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select id_categoria from categoria where categoria = '"+categoria+"';");

            int id_categoria = 0;

            while(rs.next()){
                id_categoria = rs.getInt(1);
            }

            String query_categoria_producto = "update categoria_producto set id_categoria = ? where id_producto = ? ;";
            PreparedStatement actualizarCategoria = conexion.prepareStatement(query_categoria_producto);
            actualizarCategoria.setInt(1, id_categoria);
            actualizarCategoria.setInt(2, id);
            actualizarCategoria.executeUpdate();

            String query_producto = "update producto set nombre = ? , precio = ? , cantidad = ? where id_producto= ? ; ";
            PreparedStatement preparedStmt = conexion.prepareStatement(query_producto);
            preparedStmt.setString(1, nombre);
            preparedStmt.setInt(2, precio);
            preparedStmt.setInt(3, cantidad);
            preparedStmt.setInt(4, id);

            preparedStmt.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }
    
    public void eliminarProducto(int id){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");

            String query_categoria_producto = "delete from categoria_producto where id_producto = ? ;";
            PreparedStatement eliminar_categoria = conexion.prepareStatement(query_categoria_producto);
            eliminar_categoria.setInt(1, id);
            eliminar_categoria.executeUpdate();

            String query_producto = "delete from producto where id_producto = ? ; ";
            PreparedStatement preparedStmt = conexion.prepareStatement(query_producto);
            preparedStmt.setInt(1, id);
            preparedStmt.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }

    public void insertarProducto(String nombre, int precio, int cantidad, String categoria){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");
            
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("select count(*) from producto");
            int indiceProducto = 0;
            while(rs.next()){
                indiceProducto = (rs.getInt(1)) + 1;
            }

            ResultSet rc = s.executeQuery("select id_categoria from categoria where categoria ='"+categoria+"';");
            int indiceCategoria = 0;
            while(rc.next()){
                indiceCategoria = rc.getInt(1);
            }

            String query_producto = "insert into producto values(?,?,?,?);";
            PreparedStatement insertar_producto = conexion.prepareStatement(query_producto);
            insertar_producto.setInt(1, indiceProducto);
            insertar_producto.setString(2, nombre);
            insertar_producto.setInt(3, precio);
            insertar_producto.setInt(4, cantidad);
            insertar_producto.executeUpdate();

            String query_categoria_producto = "insert into categoria_producto values(?,?);";
            PreparedStatement insertar_categoria_producto = conexion.prepareStatement(query_categoria_producto);
            insertar_categoria_producto.setInt(1, indiceCategoria);
            insertar_categoria_producto.setInt(2, indiceProducto);
            insertar_categoria_producto.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }

    public void insertarContraseña(String pass){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");

            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select contrasenia from USUARIO_CONTRASEÑA where contrasenia = '"+pass+"';");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }
            String query_contraseña = "insert into USUARIO_CONTRASEÑA values(?);";
            PreparedStatement insertar_contraseña = conexion.prepareStatement(query_contraseña);
            insertar_contraseña.setString(1, pass);
            insertar_contraseña.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }

    public void actualizarContraseña(String antigua, String nueva){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");

            Statement s = conexion.createStatement();
            ResultSet size = s.executeQuery("select contrasenia from USUARIO_CONTRASEÑA where contrasenia = '"+nueva+"';");
            int tamaño = 0;
            while(size.next()){
                tamaño = size.getInt(1);
            }

            String query_contraseña = "update USUARIO_CONTRASEÑA set contrasenia=? where contrasenia= BINARY ?;";
            PreparedStatement actualizar_contraseña = conexion.prepareStatement(query_contraseña);
            actualizar_contraseña.setString(1, nueva);
            actualizar_contraseña.setString(2, antigua);
            actualizar_contraseña.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }

    public void eliminarContraseña(String pass){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/INVENTARIO","root", "");

            String query_contraseña = "delete from  USUARIO_CONTRASEÑA where contrasenia = BINARY ?;";
            PreparedStatement eliminar_contraseña = conexion.prepareStatement(query_contraseña);
            eliminar_contraseña.setString(1, pass);
            eliminar_contraseña.executeUpdate();

            conexion.close();
            
        }catch(SQLException | ClassNotFoundException e1){}
    }

}
