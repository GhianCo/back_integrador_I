package modules.service.controllers.impl;

import java.util.List;
import org.json.JSONObject;
import modules.service.controllers.ServiceCtrl;
import modules.service.dao.ServiceDao;
import modules.service.dto.ServiceCreateRequestDTO;
import modules.service.models.Service;
import shared.DaoFactory;
import shared.JsonMapper;
import shared.PaginationResult;

public class ServiceCtrlImpl implements ServiceCtrl {

    ServiceDao serviceDao;

    public ServiceCtrlImpl() {
        this.instanceConn();
    }
    
    public ServiceCtrlImpl(ServiceDao mockDao) {
        this.serviceDao = mockDao;
    }

    private void instanceConn() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        serviceDao = daoFactory.getServiceDao();
    }

    @Override
    public void crear(Service service) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Service createService(ServiceCreateRequestDTO jsonRequest) {
        Service serviceToCreate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Service.class);
        int serviceIdCreate = serviceDao.create(serviceToCreate);

        serviceToCreate.setService_id(serviceIdCreate);

        return serviceToCreate;
    }

    @Override
    public Service updateService(ServiceCreateRequestDTO jsonRequest) {

        if (jsonRequest == null || jsonRequest.getService_id() == 0) {
            throw new IllegalArgumentException("El ID del servicio es necesario para actualizar.");
        }

        Service serviceToUpdate = JsonMapper.mapJsonToDto(new JSONObject(jsonRequest), Service.class);
        serviceToUpdate.setService_id(jsonRequest.getService_id());

        serviceDao.update(serviceToUpdate);

        return serviceToUpdate;
    }

    @Override
    public Service buscar(Object id) {
        return serviceDao.find(id);
    }

    @Override
    public List<Service> listar() {
        return serviceDao.findAll();
    }

    @Override
    public void update(Service service) {
        serviceDao.update(service);
    }

    @Override
    public void borrar(Object id) {
        serviceDao.delete(id);
    }

    @Override
    public PaginationResult paginate(String query, int page, int perPage) {
        return serviceDao.paginate(query, page, perPage);
    }
}
