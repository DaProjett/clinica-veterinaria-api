import React, { useEffect, useState } from 'react';
import { Alert, Button, Card, Spinner, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { citaService } from '../services/api';

const CitaList = () => {
  const [citas, setCitas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchCitas = async () => {
    try {
      setLoading(true);
      const data = await citaService.getAll();
      setCitas(data);
      setError('');
    } catch (err) {
      setError('Error al cargar las citas. Verifica que el backend este activo.');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCitas();
  }, []);

  const handleDelete = async (id) => {
    if (!window.confirm('Desea eliminar esta cita?')) return;

    try {
      await citaService.delete(id);
      setCitas(citas.filter((cita) => cita.id !== id));
    } catch (err) {
      setError('Error al eliminar la cita');
      console.error('Error:', err);
    }
  };

  const formatDateTime = (value) => {
    if (!value) return 'N/A';
    return new Date(value).toLocaleString('es-CO');
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
        <h2 className="mascota-title">Lista de Citas</h2>
        <p className="mascota-subtitle">Programa y consulta las citas medicas</p>
      </div>

      {error && <Alert variant="danger">{error}</Alert>}

      <div className="d-flex justify-content-between align-items-center mb-4">
        <h3 className="stats-title">Total de citas: {citas.length}</h3>
        <Link to="/citas/new">
          <Button variant="success" className="btn-custom btn-lg">
            Nueva Cita
          </Button>
        </Link>
      </div>

      <Card className="mascota-table-card">
        <Card.Header className="table-header-custom">
          <h4 className="table-title">Agenda de Citas</h4>
        </Card.Header>
        <Card.Body>
          <Table striped bordered hover responsive className="mascota-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Fecha y hora</th>
                <th>Mascota</th>
                <th>Motivo</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {citas.length > 0 ? (
                citas.map((cita) => (
                  <tr key={cita.id}>
                    <td><span className="badge bg-primary">{cita.id}</span></td>
                    <td>{formatDateTime(cita.fechaHora)}</td>
                    <td>{cita.mascota?.nombre || `Mascota #${cita.mascota?.id || 'N/A'}`}</td>
                    <td>{cita.motivo}</td>
                    <td><span className="badge bg-secondary">{cita.estado}</span></td>
                    <td>
                      <div className="action-buttons">
                        <Link to={`/citas/${cita.id}/edit`}>
                          <Button variant="warning" size="sm" className="btn-action">
                            Editar
                          </Button>
                        </Link>
                        <Button
                          variant="danger"
                          size="sm"
                          className="btn-action"
                          onClick={() => handleDelete(cita.id)}
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
                    No se encontraron citas
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

export default CitaList;
