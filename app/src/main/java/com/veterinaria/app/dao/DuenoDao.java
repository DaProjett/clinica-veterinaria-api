package com.veterinaria.app.dao;

import com.veterinaria.app.model.Dueno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.veterinaria.app.dao.interfaces.IDuenoDao;

@Repository
public class DuenoDao implements IDuenoDao {

    @PersistenceContext
    private EntityManager entityManager;

    // Buscar por ID
    public Dueno buscarPorId(int id) {
        return entityManager.find(Dueno.class, (long) id);
    }

    // Listar todos
    public List<Dueno> buscarTodos() {
        String jpql = "SELECT d FROM Dueno d";
        TypedQuery<Dueno> query = entityManager.createQuery(jpql, Dueno.class);
        return query.getResultList();
    }

    // Guardar
    @Transactional
    public boolean guardar(Dueno dueno) {
        try {
            entityManager.persist(dueno);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar
    @Transactional
    public boolean actualizar(Long id, Dueno dueno) {
        try {
            Dueno existente = entityManager.find(Dueno.class, id);

            if (existente == null) {
                return false;
            }

            existente.setNombreCompleto(dueno.getNombreCompleto());
            existente.setDocumentoIdentidad(dueno.getDocumentoIdentidad());
            existente.setTelefono(dueno.getTelefono());
            existente.setEmail(dueno.getEmail());
            existente.setDireccion(dueno.getDireccion());

            entityManager.merge(existente);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar
    @Transactional
    public boolean eliminar(Long id) {
        try {
            Dueno dueno = entityManager.find(Dueno.class, id);

            if (dueno == null) {
                return false;
            }

            entityManager.remove(dueno);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
``