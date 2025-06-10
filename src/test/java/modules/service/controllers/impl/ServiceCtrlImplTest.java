package modules.service.controllers.impl;

import modules.service.dao.ServiceDao;
import modules.service.dto.ServiceCreateRequestDTO;
import modules.service.models.Service;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceCtrlImplTest {

    @Test
    void testCreateService_DeberiaCrearServicioCorrectamente() {
        // Arrange
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName("Baño y Corte");
        dto.setDescription("Servicio completo de baño y corte");
        dto.setPrice(50.0);

        Service mappedService = new Service();
        mappedService.setName(dto.getName());
        mappedService.setDescription(dto.getDescription());
        mappedService.setPrice(dto.getPrice());

        when(mockDao.create(any(Service.class))).thenReturn(10);

        Service result = controller.createService(dto);

        assertEquals("Baño y Corte", result.getName());
        assertEquals("Servicio completo de baño y corte", result.getDescription());
        assertEquals(50.0, result.getPrice());
        assertEquals(10, result.getService_id());

        verify(mockDao).create(any(Service.class));
    }

    @Test
    void testCreateService_PrecioCero() {
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName("Consulta Gratuita");
        dto.setDescription("Primera consulta sin costo");
        dto.setPrice(0.0);

        when(mockDao.create(any(Service.class))).thenReturn(1);

        Service result = controller.createService(dto);

        assertEquals(0.0, result.getPrice());
        assertEquals(1, result.getService_id());
        verify(mockDao).create(any(Service.class));
    }

    @Test
    void testCreateService_CamposNulos() {
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName(null);
        dto.setDescription(null);
        dto.setPrice(25.0);

        when(mockDao.create(any(Service.class))).thenReturn(2);

        Service result = controller.createService(dto);

        assertNull(result.getName());
        assertNull(result.getDescription());
        assertEquals(25.0, result.getPrice());
        assertEquals(2, result.getService_id());
    }

    @Test
    void testCreateService_VerificaLlamadaCreate() {
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName("Desparasitación");
        dto.setDescription("Tratamiento contra parásitos");
        dto.setPrice(35.0);

        when(mockDao.create(any(Service.class))).thenReturn(3);

        controller.createService(dto);

        verify(mockDao, times(1)).create(any(Service.class));
    }

    @Test
    void testCreateService_IdNegativo() {
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName("Vacunación");
        dto.setDescription("Vacuna antirrábica");
        dto.setPrice(15.0);

        when(mockDao.create(any(Service.class))).thenReturn(-1);

        Service result = controller.createService(dto);

        assertEquals(-1, result.getService_id());
    }

    @Test
    void testCreateService_CamposVacios() {
        ServiceDao mockDao = mock(ServiceDao.class);
        ServiceCtrlImpl controller = new ServiceCtrlImpl(mockDao);

        ServiceCreateRequestDTO dto = new ServiceCreateRequestDTO();
        dto.setName("");
        dto.setDescription("");
        dto.setPrice(10.0);

        when(mockDao.create(any(Service.class))).thenReturn(4);

        Service result = controller.createService(dto);

        assertEquals("", result.getName());
        assertEquals("", result.getDescription());
        assertEquals(10.0, result.getPrice());
        assertEquals(4, result.getService_id());
    }

}
