package com.veterinaria.app.dao;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDao {

    public Dueno buscarPorId(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, nombre, telefono, email
            FROM duenos
            WHERE id = ?
        """;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Dueno d = new Dueno();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                return d;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, ps, rs);
        }

        return null;
    }

    public List<Dueno> buscarTodos() {

        List<Dueno> lista = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, nombre, telefono, email
            FROM duenos
        """;

        try {
            conn = DatabaseConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Dueno d = new Dueno();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, st, rs);
        }

        return lista;
    }

    private void cerrar(Connection c, Statement s, ResultSet r) {
        try {
            if (r != null) r.close();
            if (s != null) s.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
