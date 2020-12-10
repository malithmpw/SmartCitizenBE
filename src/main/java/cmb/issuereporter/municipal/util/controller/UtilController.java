package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.util.service.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/util")
public class UtilController {

    @Autowired
    UtilService utilService;

    private static final Logger LOGGER = LogManager.getLogger(UtilController.class);

    @GetMapping("/appData/{userId}")
    public ResponseEntity getAppData(@PathVariable("userId") Integer userId){
        LOGGER.info("Get App Data List : start" + userId);
        return utilService.getAppData(userId);
    }
}
