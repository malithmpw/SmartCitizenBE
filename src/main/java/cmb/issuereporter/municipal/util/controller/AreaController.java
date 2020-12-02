package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.AreaDTO;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.util.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/area")
public class AreaController {
    @Autowired
    AreaService areaService;

    @GetMapping(value = "/all")
    public ResponseEntity getAreaList() {
        List<Area> areaList = areaService.getAllAreaList();
        return new ResponseEntity<>(areaList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addArea(@RequestBody AreaDTO areaDTO){
        return areaService.addArea(areaDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteArea(@RequestBody AreaDTO areaDTO){
        return areaService.deleteArea(areaDTO);
    }
}
