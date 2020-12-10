package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.AreaDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.util.repsitory.AreaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    @Autowired
    AreaRepository areaRepository;

    private static final Logger LOGGER = LogManager.getLogger(AreaService.class);
    public List<Area> getAllAreaList(){
        List<Area> areas = areaRepository.findAll();
        LOGGER.info("Get Area List Service: List Size : " +areas.size());
        return areas;
    }

    public Area findById(int id){
        Area area = areaRepository.findById(id).orElse(null);
        return area;
    }

    public ResponseEntity addArea(AreaDTO areaDTO){
        Area area = areaRepository.findByName(areaDTO.getName());
        LOGGER.info("Save Area Service : Start : " + areaDTO.getName());
        if(area == null){
            Area newArea = new Area();
            newArea.setName(areaDTO.getName());
            newArea = areaRepository.save(newArea);
            if(newArea != null){
                LOGGER.info("Save Area Service : Success : " + areaDTO.getName());
                return new ResponseEntity(newArea, HttpStatus.OK);
            }else{
                LOGGER.info("Save Area Service : Area Creation failed : " + areaDTO.getName());
                return new ResponseEntity(new CustomError(3004, "Area Creation failed"), HttpStatus.NOT_FOUND);
            }

        }else {
            LOGGER.info("Save Area Service : Area Name Already Exist : " + areaDTO.getName());
            return new ResponseEntity(new CustomError(3002, "Area Name Already Exist"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteArea(AreaDTO areaDTO) {
        Area area = areaRepository.findById(areaDTO.getId()).orElse(null);
        if(area != null){
            areaRepository.delete(area);
            LOGGER.info("Delete Area Service : Successfully Deleted Area : " + areaDTO.getName());
            return new ResponseEntity("Successfully Deleted Area", HttpStatus.OK);
        }else {
            LOGGER.info("Delete Area Service : Area Not Found : " + areaDTO.getName());
            return new ResponseEntity(new CustomError(3001, "Area Not Found"), HttpStatus.NOT_FOUND);
        }
    }
}
