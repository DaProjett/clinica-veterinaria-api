package com.veterinaria.app.dao;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDao {

    public Mascota buscarPorId(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, nombre, especie, raza, edad, dueno_id
            FROM mascotas
            WHERE id = ?
        """;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Mascota m = new Mascota();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecie(rs.getString("especie"));
                m.setRaza(rs.getString("raza"));
                m.setEdad(rs.getInt("edad"));
                m.setIdDueno(rs.getInt("dueno_id"));
                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, ps, rs);
        }

        return null;
    }

    public List<Mascota> buscarTodos() {

        List<Mascota> lista = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, nombre, especie, raza, edad, dueno_id
            FROM mascotas
        """;

        try {
            conn = DatabaseConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Mascota m = new Mascota();
                m.setId(rs.getInt("id"));
                m.setNombre(rs.getString("nombre"));
                m.setEspecie(rs.getString("especie"));
                m.setRaza(rs.getString("raza"));
                m.setEdad(rs.getInt("edad"));
                m.setIdDueno(rs.getInt("dueno_id"));
                lista.add(m);
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
