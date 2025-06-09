# 🐾 Sistema de Gestión Veterinaria – Clínica Rivet Perú

## 📘 Descripción General

Este proyecto corresponde al desarrollo de un **sistema web** con servicios REST (Jakarta WS – JAX-RS), enfocado en digitalizar y automatizar los procesos administrativos, de reservas y fidelización de clientes para la **Clínica Veterinaria Rivet Perú**.

---

## 📊 Análisis del Contexto

### 🏥 Entorno de la Empresa

La clínica veterinaria Rivet Perú opera en un entorno urbano competitivo, donde la tenencia de mascotas y la demanda por servicios especializados está en crecimiento. Muchas clínicas aún manejan procesos manuales o utilizan herramientas no adaptadas a sus necesidades. Rivet necesita modernizarse para responder a una clientela que valora:

- Eficiencia en la atención.
- Seguimiento postconsulta.
- Automatización de procesos.
- Atención personalizada.

---

## 🧠 Estrategias Empresariales

- Automatizar la gestión de citas, historiales clínicos y fidelización.
- Mejorar la experiencia del cliente con recordatorios y servicios en línea.
- Reducir tareas administrativas repetitivas mediante herramientas digitales.
- Potenciar relaciones con clientes mediante promociones y seguimiento inteligente.

---

## 🎯 Misión

Brindar atención veterinaria de clase mundial con un equipo altamente capacitado, comprometido con el bienestar animal, mediante servicios personalizados, soporte continuo y excelencia médica con innovación constante.

## 🌟 Visión

Ser un centro veterinario de referencia internacional, líder en innovación, formación y atención especializada, elevando los estándares de cuidado animal en Perú y la región.

---

## 🧭 Planes de Acción

### 📌 Corto Plazo (0–6 meses)
- Desarrollo del sistema web.
- Capacitación del personal.
- Promoción digital del nuevo servicio.

### 📌 Mediano Plazo (6–12 meses)
- Integración con medios de pago y CRM.
- Retroalimentación del uso.
- Mejoras incrementales.

### 📌 Largo Plazo (12 meses o más)
- Escalabilidad del sistema a más sedes.
- Incorporación de inteligencia artificial.
- Comercialización como producto B2B.

---

## ❗ Problemática Actual

- Procesos críticos como programación de citas, seguimiento, gestión de cobros y fidelización se manejan de forma manual.
- Pérdida de tiempo, errores administrativos y falta de trazabilidad de la atención clínica.
- Múltiples plataformas sin integración dificultan la gestión y reducen la calidad del servicio.

---

## 💡 Alternativas Evaluadas

### ❌ Soluciones Genéricas
- CRMs, sistemas de facturación o agendas online.
- No adaptadas al entorno veterinario, requieren personalización.

### ❌ Procesos Manuales (actuales)
- Uso de Excel, WhatsApp y calendarios.
- No permiten crecimiento, no hay automatización ni fidelización estructurada.

### ✅ Solución Propuesta
Un **sistema web especializado** en veterinarias que:
- Centralice la gestión clínica, administrativa y comercial.
- Automatice reservas, recordatorios, cobros y reportes.
- Mejore la fidelización y la eficiencia interna.

---

## 🖥️ Tecnologías Utilizadas

- **Jakarta WS (JAX-RS)** – API REST
- **Java** – Backend
- **JDBC** – Persistencia
- **MySQL** – Base de datos
- **Maven** – Gestión de dependencias

---

## 🔗 Endpoints REST (Ejemplo)

| Método | Ruta                        | Descripción                                |
|--------|-----------------------------|--------------------------------------------|
| GET    | `/reservas`                | Listar reservas                            |
| POST   | `/reservas`                | Crear nueva reserva                        |
| GET    | `/clientes/{id}/historial` | Obtener historial médico del cliente       |
| PUT    | `/clientes/{id}`           | Actualizar datos del cliente               |
| POST   | `/clientes/{id}/promos`    | Registrar promociones / puntos de lealtad  |
| GET    | `/reportes/uso`           | Consultar métricas de uso                  |

---

## ⚙️ Instalación

1. **Clonar repositorio**
   ```bash
   git clone https://github.com/GhianCo/back_integrador_I.git
   cd back_integrador_I
