import React from 'react';
import { Navbar as BootstrapNavbar, Nav, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <BootstrapNavbar
      bg="primary"
      variant="dark"
      expand="lg"
      className="navbar-veterinaria shadow-lg"
    >
      <Container fluid>
        <BootstrapNavbar.Brand as={Link} to="/" className="navbar-brand-veterinaria">
          <span className="logo-icon">CV</span>
          <span className="brand-text">Clinica Veterinaria</span>
        </BootstrapNavbar.Brand>

        <BootstrapNavbar.Toggle
          aria-controls="basic-navbar-nav"
          className="navbar-toggle-custom"
        />

        <BootstrapNavbar.Collapse id="basic-navbar-nav" className="navbar-collapse-custom">
          <Nav className="me-auto navbar-nav-custom">
            <Nav.Link as={Link} to="/mascotas" className="nav-link-custom">
              Mascotas
            </Nav.Link>
            <Nav.Link as={Link} to="/mascotas/new" className="nav-link-custom">
              Registrar Mascota
            </Nav.Link>
            <Nav.Link as={Link} to="/duenos" className="nav-link-custom">
              Duenos
            </Nav.Link>
            <Nav.Link as={Link} to="/duenos/new" className="nav-link-custom">
              Registrar Dueno
            </Nav.Link>
            <Nav.Link as={Link} to="/citas" className="nav-link-custom">
              Citas
            </Nav.Link>
            <Nav.Link as={Link} to="/citas/new" className="nav-link-custom">
              Registrar Cita
            </Nav.Link>
          </Nav>
        </BootstrapNavbar.Collapse>
      </Container>
    </BootstrapNavbar>
  );
};

export default Navbar;
