package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.CategoryDTO;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.util.service.CategoryService;
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

    @GetMapping(value = "/all")
    public ResponseEntity getCategoryList() {
        List<Category> categoryList = categoryService.getAllCategoryList();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.addCategory(categoryDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.deleteCategory(categoryDTO);
    }


}
