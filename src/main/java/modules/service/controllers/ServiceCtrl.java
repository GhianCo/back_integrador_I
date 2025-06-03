package modules.service.controllers;

import modules.service.dto.ServiceCreateRequestDTO;
import modules.service.models.Service;
import shared.BaseService;

public interface ServiceCtrl extends BaseService<Service> {
    public Service createService(ServiceCreateRequestDTO entity);
    public Service updateService(ServiceCreateRequestDTO entity);
}
