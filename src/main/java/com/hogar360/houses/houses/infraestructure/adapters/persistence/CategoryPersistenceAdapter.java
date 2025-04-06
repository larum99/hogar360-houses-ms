package com.hogar360.houses.houses.infraestructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.infraestructure.entities.CategoryEntity;
import com.hogar360.houses.houses.infraestructure.mappers.CategoryEntityMapper;
import com.hogar360.houses.houses.infraestructure.repositories.mysql.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hogar360.houses.commons.configurations.utils.SortUtils.createSort;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryPersistenceAdapter implements CategoryPersistencePort {
    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void save(CategoryModel categoryModel) {
        categoryRepository.save(categoryEntityMapper.modelToEntity(categoryModel));
    }

    @Override
    public CategoryModel getCategoryByName(String categoryName) {
        return categoryEntityMapper.entityToModel(categoryRepository.findByName(categoryName).orElse(null));
    }

    @Override
    public PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc) {
        Sort sort = createSort("name", orderAsc);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryEntity> entityPage =
                categoryRepository.findAll(pageable);

        List<CategoryModel> categoryModels = categoryEntityMapper.entityToModelList(entityPage.getContent());

        return new PageResult<>(
                categoryModels,
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.isFirst(),
                entityPage.isLast()
        );
    }
}
