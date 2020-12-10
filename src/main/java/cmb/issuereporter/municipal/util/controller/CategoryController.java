package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.CategoryDTO;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.util.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);
    @GetMapping(value = "/all")
    public ResponseEntity getCategoryList() {
        LOGGER.info("Get Category List : start" );
        List<Category> categoryList = categoryService.getAllCategoryList();
        LOGGER.info("Get Category List : end" );
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addCategory(@RequestBody CategoryDTO categoryDTO){
        LOGGER.info("Add Category  : start" );
        return categoryService.addCategory(categoryDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteCategory(@RequestBody CategoryDTO categoryDTO){
        LOGGER.info("Delete Category  : start" );
        return categoryService.deleteCategory(categoryDTO);
    }


}
