package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.CategoryDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.util.repsitory.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategoryList(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Category findById(int id){
        Category category = categoryRepository.findById(id).orElse(null);
        return category;
    }

    public ResponseEntity addCategory(CategoryDTO categoryDTO){
        Category category = categoryRepository.findByName(categoryDTO.getName());
        if(category == null){
            Category newCategory = new Category();
            newCategory.setName(categoryDTO.getName());
            newCategory.setDescription(categoryDTO.getDescription());
            newCategory = categoryRepository.save(newCategory);

            if(newCategory != null){
                return new ResponseEntity(newCategory, HttpStatus.OK);
            }else{
                return new ResponseEntity(new CustomError(3004, "Category Creation failed"), HttpStatus.OK);
            }
        }else {
            return new ResponseEntity(new CustomError(3002, "Category Name Already Exist"), HttpStatus.OK);
        }
    }

    public ResponseEntity deleteCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        if(category != null){
            categoryRepository.delete(category);
            return new ResponseEntity("Successfully Deleted Category", HttpStatus.OK);
        }else {
            return new ResponseEntity(new CustomError(3001, "Category Not Found"), HttpStatus.OK);
        }
    }
}
