import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MascotaList from './components/MascotaList';
import MascotaForm from './components/MascotaForm';
import MascotaDetail from './components/MascotaDetail';
import DuenoList from './components/DuenoList';
import DuenoForm from './components/DuenoForm';
import CitaList from './components/CitaList';
import CitaForm from './components/CitaForm';
import Navbar from './components/Navbar';
import { Container } from 'react-bootstrap';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Container className="mt-4">
          <Routes>
            <Route path="/" element={<MascotaList />} />
            <Route path="/mascotas" element={<MascotaList />} />
            <Route path="/mascotas/new" element={<MascotaForm />} />
            <Route path="/mascotas/:id" element={<MascotaDetail />} />
            <Route path="/mascotas/:id/edit" element={<MascotaForm />} />
            <Route path="/duenos" element={<DuenoList />} />
            <Route path="/duenos/new" element={<DuenoForm />} />
            <Route path="/duenos/:id/edit" element={<DuenoForm />} />
            <Route path="/citas" element={<CitaList />} />
            <Route path="/citas/new" element={<CitaForm />} />
            <Route path="/citas/:id/edit" element={<CitaForm />} />
          </Routes>
        </Container>
      </div>
    </Router>
  );
}

export default App;
