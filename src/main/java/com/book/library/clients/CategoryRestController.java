package com.book.library.clients;

import com.book.library.assemblers.CategoryModelAssemblers;
import com.book.library.dao.CategoryDao;
import com.book.library.entities.Category;
import com.book.library.services.category.CategoryService;
import com.book.library.util.HelperClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/category")
public class CategoryRestController {
    private final CategoryModelAssemblers categoryModelAssemblers;
    private final PagedResourcesAssembler<Category> categoriesPagedResourcesAssembler ;
    private final CategoryService categoryService;

    public CategoryRestController(CategoryModelAssemblers categoryModelAssemblers, 
                                  PagedResourcesAssembler<Category> categoriesPagedResourcesAssembler ,
                                  CategoryService categoryService) {
        this.categoryModelAssemblers = categoryModelAssemblers;
        this.categoriesPagedResourcesAssembler  = categoriesPagedResourcesAssembler ;
        this.categoryService = categoryService;
    }


    @GetMapping(value = "{categoryId}", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCategoryById(
            @PathVariable("categoryId") @NotNull final String categoryId
    ) {
        return HelperClass.generateResponse(
                "Category successfully retrieved " + "",
                HttpStatus.OK,
                categoryModelAssemblers.toModel(
                        categoryService.findOne(Integer.parseInt(categoryId))
                )
        );
    }

    @PostMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCategory(
            @RequestBody final CategoryDao categoryDao
    ) {
        log.info("categoryDao ->{}", categoryDao);
        return HelperClass.generateResponse(
                "Category successfully created " + "",
                HttpStatus.CREATED,
                categoryModelAssemblers.toModel(
                        categoryService.create(categoryDao.getNameOfCategory())
                )
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "{categoryId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> updateCategory(
            @PathVariable("categoryId") @NotNull final String categoryId,
            @RequestBody final CategoryDao categoryDao
    )  {
        return HelperClass.generateResponse(
                "Category successfully updated " + "",
                HttpStatus.OK,
                categoryModelAssemblers.toModel(
                        categoryService.update(categoryDao.getNameOfCategory(),Integer.parseInt(categoryId))
                )
        );
    }

    
    @GetMapping(value = "", produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCategories(
            @PageableDefault(
                    sort = { "createdAt" },
                    direction = Sort.Direction.DESC
            ) final Pageable pageable
            ) {
        return HelperClass.generateResponse(
                "Categories successfully retrieved " + "",
                HttpStatus.OK,
                categoriesPagedResourcesAssembler.toModel(
                        categoryService.listCategories(pageable),
                        categoryModelAssemblers
                )
        );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{categoryId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteCategory(
            @PathVariable("categoryId") @NotNull final String categoryId
    ) {
        categoryService.deleteCategory(Integer.parseInt(categoryId));

        return HelperClass.generateResponse(
                "Category item deleted successfully ",
                HttpStatus.OK,
                null
                );

    }
}
