package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.AreaDTO;
import cmb.issuereporter.municipal.issue.IssueController;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.util.service.AreaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOGGER = LogManager.getLogger(AreaController.class);
    @GetMapping(value = "/all")
    public ResponseEntity getAreaList() {
        LOGGER.info("Get Area List : start" );
        List<Area> areaList = areaService.getAllAreaList();
        LOGGER.info("Get Area List : End : Result Size : "+areaList.size() );
        return new ResponseEntity<>(areaList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addArea(@RequestBody AreaDTO areaDTO){
        LOGGER.info("Save Area  : " );
        return areaService.addArea(areaDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteArea(@RequestBody AreaDTO areaDTO){
        LOGGER.info("Delete Area  : " );
        return areaService.deleteArea(areaDTO);
    }
}
