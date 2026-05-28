import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Button, Card, Col, Form, Row, Spinner } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { citaService, mascotaService } from '../services/api';

const CitaForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditing = Boolean(id);
  const [loading, setLoading] = useState(false);
  const [mascotas, setMascotas] = useState([]);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [formData, setFormData] = useState({
    fechaHora: '',
    motivo: '',
    estado: 'PROGRAMADA',
    notas: '',
    mascotaId: ''
  });

  const toDatetimeLocal = (value) => {
    if (!value) return '';
    return value.slice(0, 16);
  };

  const fetchMascotas = useCallback(async () => {
    try {
      const data = await mascotaService.getAll();
      setMascotas(Array.isArray(data) ? data : []);
    } catch (err) {
      setError('Error al cargar mascotas para la cita');
      console.error('Error:', err);
    }
  }, []);

  const fetchCita = useCallback(async () => {
    try {
      setLoading(true);
      const cita = await citaService.getById(id);
      setFormData({
        fechaHora: toDatetimeLocal(cita.fechaHora),
        motivo: cita.motivo || '',
        estado: cita.estado || 'PROGRAMADA',
        notas: cita.notas || '',
        mascotaId: cita.mascota?.id || ''
      });
    } catch (err) {
      setError('Error al cargar la cita');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    fetchMascotas();
    if (isEditing) {
      fetchCita();
    }
  }, [fetchMascotas, fetchCita, isEditing]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setSuccess('');

    if (!formData.fechaHora) {
      setError('La fecha y hora son obligatorias');
      return;
    }

    if (!formData.motivo.trim()) {
      setError('El motivo es obligatorio');
      return;
    }

    if (!formData.mascotaId) {
      setError('Debe seleccionar una mascota');
      return;
    }

    const citaData = {
      fechaHora: formData.fechaHora,
      motivo: formData.motivo,
      estado: formData.estado,
      notas: formData.notas || null,
      mascotaId: parseInt(formData.mascotaId, 10)
    };

    try {
      setLoading(true);
      if (isEditing) {
        await citaService.update(id, citaData);
        setSuccess('Cita actualizada correctamente');
      } else {
        await citaService.create(citaData);
        setSuccess('Cita creada correctamente');
      }

      setTimeout(() => navigate('/citas'), 1000);
    } catch (err) {
      setError(err.message || 'Error al guardar la cita');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading && isEditing) {
    return (
      <div className="text-center py-5">
        <Spinner animation="border" role="status" />
      </div>
    );
  }

  return (
    <Card>
      <Card.Header>
        <h3>{isEditing ? 'Editar Cita' : 'Registrar Nueva Cita'}</h3>
      </Card.Header>
      <Card.Body>
        {error && <Alert variant="danger" dismissible onClose={() => setError('')}>{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}

        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Fecha y hora *</Form.Label>
                <Form.Control
                  type="datetime-local"
                  name="fechaHora"
                  value={formData.fechaHora}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group>
                <Form.Label>Estado *</Form.Label>
                <Form.Select name="estado" value={formData.estado} onChange={handleChange} required>
                  <option value="PROGRAMADA">Programada</option>
                  <option value="EN_PROCESO">En proceso</option>
                  <option value="COMPLETADA">Completada</option>
                  <option value="CANCELADA">Cancelada</option>
                </Form.Select>
              </Form.Group>
            </Col>
          </Row>

          <Row className="mb-3">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Mascota *</Form.Label>
                <Form.Select
                  name="mascotaId"
                  value={formData.mascotaId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Seleccione una mascota</option>
                  {mascotas.map((mascota) => (
                    <option key={mascota.id} value={mascota.id}>
                      {mascota.nombre} - {mascota.especie}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group>
                <Form.Label>Motivo *</Form.Label>
                <Form.Control
                  name="motivo"
                  value={formData.motivo}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
            </Col>
          </Row>

          <Form.Group className="mb-4">
            <Form.Label>Notas</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="notas"
              value={formData.notas}
              onChange={handleChange}
            />
          </Form.Group>

          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={() => navigate('/citas')}>
              Cancelar
            </Button>
            <Button type="submit" variant="primary" disabled={loading}>
              {loading ? 'Guardando...' : isEditing ? 'Actualizar Cita' : 'Crear Cita'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default CitaForm;
