import React, { useEffect, useState } from 'react';
import { Alert, Button, Card, Spinner, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { duenoService } from '../services/api';

const DuenoList = () => {
  const [duenos, setDuenos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchDuenos = async () => {
    try {
      setLoading(true);
      const data = await duenoService.getAll();
      setDuenos(data);
      setError('');
    } catch (err) {
      setError('Error al cargar los duenos. Verifica que el backend este activo.');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDuenos();
  }, []);

  const handleDelete = async (id, nombre) => {
    if (!window.confirm(`Desea eliminar a ${nombre}?`)) return;

    try {
      await duenoService.delete(id);
      setDuenos(duenos.filter((dueno) => dueno.id !== id));
    } catch (err) {
      setError('Error al eliminar el dueno. Puede tener mascotas asociadas.');
      console.error('Error:', err);
    }
  };

  if (loading) {
    return (
      <div className="text-center py-5">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Cargando...</span>
        </Spinner>
      </div>
    );
  }

  return (
    <div className="fade-in">
      <div className="mascota-header mb-5">
        <h2 className="mascota-title">Lista de Duenos</h2>
        <p className="mascota-subtitle">Gestiona las personas responsables de las mascotas</p>
      </div>

      {error && <Alert variant="danger">{error}</Alert>}

      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3 className="stats-title">Total de duenos: {duenos.length}</h3>
        <Link to="/duenos/new">
          <Button variant="success" className="btn-custom btn-lg">
            Nuevo Dueno
          </Button>
        </Link>
      </div>

      <Card className="mascota-table-card">
        <Card.Header className="table-header-custom">
          <h4 className="table-title">Registro de Duenos</h4>
        </Card.Header>
        <Card.Body>
          <Table striped bordered hover responsive className="mascota-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Documento</th>
                <th>Telefono</th>
                <th>Email</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {duenos.length > 0 ? (
                duenos.map((dueno) => (
                  <tr key={dueno.id}>
                    <td><span className="badge bg-primary">{dueno.id}</span></td>
                    <td><strong>{dueno.nombreCompleto}</strong></td>
                    <td>{dueno.documentoIdentidad}</td>
                    <td>{dueno.telefono || 'N/A'}</td>
                    <td>{dueno.email || 'N/A'}</td>
                    <td>
                      <div className="action-buttons">
                        <Link to={`/duenos/${dueno.id}/edit`}>
                          <Button variant="warning" size="sm" className="btn-action">
                            Editar
                          </Button>
                        </Link>
                        <Button
                          variant="danger"
                          size="sm"
                          className="btn-action"
                          onClick={() => handleDelete(dueno.id, dueno.nombreCompleto)}
                        >
                          Eliminar
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={6} className="text-center empty-state">
                    No se encontraron duenos
                  </td>
                </tr>
              )}
            </tbody>
          </Table>
        </Card.Body>
      </Card>
    </div>
  );
};

export default DuenoList;
