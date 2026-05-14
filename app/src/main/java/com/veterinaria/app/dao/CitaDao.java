package com.veterinaria.app.dao;

import com.veterinaria.app.model.Cita;
import com.veterinaria.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.veterinaria.app.dao.interfaces.ICitaDao;


public class CitaDao implements ICitaDao {
    
    @Override
    public boolean guardar(Cita cita) {

        Connection conn = null;
        PreparedStatement ps = null;

        String sql = """
            INSERT INTO citas
            (fecha_hora, motivo, estado, notas, mascota_id, fecha_creacion)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(cita.getFechaHora()));
            ps.setString(2, cita.getMotivo());
            ps.setString(3, cita.getEstado());
            ps.setString(4, cita.getNotas());
            ps.setInt(5, cita.getIdMascota());
            ps.setTimestamp(6, Timestamp.valueOf(cita.getFechaCreacion()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, ps, null);
        }

        return false;
    }

    @Override
    public List<Cita> buscarTodos() {

        List<Cita> lista = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, fecha_hora, motivo, estado, notas, mascota_id, fecha_creacion
            FROM citas
        """;

        try {
            conn = DatabaseConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                c.setMotivo(rs.getString("motivo"));
                c.setEstado(rs.getString("estado"));
                c.setNotas(rs.getString("notas"));
                c.setIdMascota(rs.getInt("mascota_id"));
                c.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, st, rs);
        }

        return lista;
    }

    @Override
    public Cita buscarPorId(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = """
            SELECT id, fecha_hora, motivo, estado, notas, mascota_id, fecha_creacion
            FROM citas
            WHERE id = ?
        """;

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                c.setMotivo(rs.getString("motivo"));
                c.setEstado(rs.getString("estado"));
                c.setNotas(rs.getString("notas"));
                c.setIdMascota(rs.getInt("mascota_id"));
                c.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, ps, rs);
        }

        return null;
    }
    
    @Override
    public boolean eliminar(int id) {

        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "DELETE FROM citas WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(conn, ps, null);
        }

        return false;
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
