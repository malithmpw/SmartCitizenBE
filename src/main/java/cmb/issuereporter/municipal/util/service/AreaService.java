package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.AreaDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.util.repsitory.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    @Autowired
    AreaRepository areaRepository;

    public List<Area> getAllAreaList(){
        List<Area> areas = areaRepository.findAll();
        return areas;
    }

    public Area findById(int id){
        Area area = areaRepository.findById(id).orElse(null);
        return area;
    }

    public ResponseEntity addArea(AreaDTO areaDTO){
        Area area = areaRepository.findByName(areaDTO.getName());
        if(area == null){
            Area newArea = new Area();
            newArea.setName(areaDTO.getName());
            newArea = areaRepository.save(newArea);
            if(newArea != null){
                return new ResponseEntity(newArea, HttpStatus.OK);
            }else{
                return new ResponseEntity(new CustomError(3004, "Area Creation failed"), HttpStatus.OK);
            }

        }else {
            return new ResponseEntity(new CustomError(3002, "Area Name Already Exist"), HttpStatus.OK);
        }
    }

    public ResponseEntity deleteArea(AreaDTO areaDTO) {
        Area area = areaRepository.findById(areaDTO.getId()).orElse(null);
        if(area != null){
            areaRepository.delete(area);
            return new ResponseEntity("Successfully Deleted Area", HttpStatus.OK);
        }else {
            return new ResponseEntity(new CustomError(3001, "Area Not Found"), HttpStatus.OK);
        }
    }
}
