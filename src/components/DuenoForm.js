import React, { useCallback, useEffect, useState } from 'react';
import { Alert, Button, Card, Col, Form, Row, Spinner } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { duenoService } from '../services/api';

const DuenoForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEditing = Boolean(id);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [formData, setFormData] = useState({
    nombreCompleto: '',
    documentoIdentidad: '',
    telefono: '',
    email: '',
    direccion: ''
  });

  const fetchDueno = useCallback(async () => {
    try {
      setLoading(true);
      const dueno = await duenoService.getById(id);
      setFormData({
        nombreCompleto: dueno.nombreCompleto || '',
        documentoIdentidad: dueno.documentoIdentidad || '',
        telefono: dueno.telefono || '',
        email: dueno.email || '',
        direccion: dueno.direccion || ''
      });
    } catch (err) {
      setError('Error al cargar el dueno');
      console.error('Error:', err);
    } finally {
      setLoading(false);
    }
  }, [id]);

  useEffect(() => {
    if (isEditing) {
      fetchDueno();
    }
  }, [fetchDueno, isEditing]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setSuccess('');

    if (!formData.nombreCompleto.trim()) {
      setError('El nombre completo es obligatorio');
      return;
    }

    if (!formData.documentoIdentidad.trim()) {
      setError('El documento de identidad es obligatorio');
      return;
    }

    try {
      setLoading(true);
      if (isEditing) {
        await duenoService.update(id, formData);
        setSuccess('Dueno actualizado correctamente');
      } else {
        await duenoService.create(formData);
        setSuccess('Dueno creado correctamente');
      }

      setTimeout(() => navigate('/duenos'), 1000);
    } catch (err) {
      const message = err.response?.data?.error || err.message || 'Error al guardar el dueno';
      setError(message);
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
        <h3>{isEditing ? 'Editar Dueno' : 'Registrar Nuevo Dueno'}</h3>
      </Card.Header>
      <Card.Body>
        {error && <Alert variant="danger" dismissible onClose={() => setError('')}>{error}</Alert>}
        {success && <Alert variant="success">{success}</Alert>}

        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Nombre completo *</Form.Label>
                <Form.Control
                  name="nombreCompleto"
                  value={formData.nombreCompleto}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group>
                <Form.Label>Documento de identidad *</Form.Label>
                <Form.Control
                  name="documentoIdentidad"
                  value={formData.documentoIdentidad}
                  onChange={handleChange}
                  required
                />
              </Form.Group>
            </Col>
          </Row>

          <Row className="mb-3">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Telefono</Form.Label>
                <Form.Control name="telefono" value={formData.telefono} onChange={handleChange} />
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group>
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" name="email" value={formData.email} onChange={handleChange} />
              </Form.Group>
            </Col>
          </Row>

          <Form.Group className="mb-4">
            <Form.Label>Direccion</Form.Label>
            <Form.Control name="direccion" value={formData.direccion} onChange={handleChange} />
          </Form.Group>

          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={() => navigate('/duenos')}>
              Cancelar
            </Button>
            <Button type="submit" variant="primary" disabled={loading}>
              {loading ? 'Guardando...' : isEditing ? 'Actualizar Dueno' : 'Crear Dueno'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default DuenoForm;
