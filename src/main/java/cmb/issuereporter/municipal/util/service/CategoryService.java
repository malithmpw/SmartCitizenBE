package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.CategoryDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.util.repsitory.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);
    public List<Category> getAllCategoryList(){
        List<Category> categories = categoryRepository.findAll();
        LOGGER.info("Get Category List Service: List Size : " +categories.size());
        return categories;
    }

    public Category findById(int id){
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }

    public ResponseEntity addCategory(CategoryDTO categoryDTO){
        Category category = categoryRepository.findByName(categoryDTO.getName());
        LOGGER.info("Save Category Service : Start : " + categoryDTO.getName());
        if(category == null){
            Category newCategory = new Category();
            newCategory.setName(categoryDTO.getName());
            newCategory.setDescription(categoryDTO.getDescription());
            newCategory = categoryRepository.save(newCategory);

            if(newCategory != null){
                LOGGER.info("Save Category Service : Success : " + categoryDTO.getName());
                return new ResponseEntity(newCategory, HttpStatus.OK);
            }else{
                LOGGER.info("Save Category Service : Category Creation failed : " + categoryDTO.getName());
                return new ResponseEntity(new CustomError(3004, "Category Creation failed"), HttpStatus.NOT_FOUND);
            }
        }else {
            LOGGER.info("Save Category Service : Category Name Already Exist : " + categoryDTO.getName());
            return new ResponseEntity(new CustomError(3002, "Category Name Already Exist"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        if(category != null){
            categoryRepository.delete(category);
            LOGGER.info("Delete Category Service : Successfully Deleted Category : " + categoryDTO.getName());
            return new ResponseEntity("Successfully Deleted Category", HttpStatus.OK);
        }else {
            LOGGER.info("Delete Category Service : Category Not Found : " + categoryDTO.getName());
            return new ResponseEntity(new CustomError(3001, "Category Not Found"), HttpStatus.NOT_FOUND);
        }
    }
}
