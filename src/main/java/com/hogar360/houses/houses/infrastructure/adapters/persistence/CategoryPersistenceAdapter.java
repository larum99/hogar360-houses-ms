package com.hogar360.houses.houses.infrastructure.adapters.persistence;

import com.hogar360.houses.houses.domain.model.CategoryModel;
import com.hogar360.houses.houses.domain.utils.PageResult;
import com.hogar360.houses.houses.domain.ports.out.CategoryPersistencePort;
import com.hogar360.houses.houses.infrastructure.entities.CategoryEntity;
import com.hogar360.houses.houses.infrastructure.mappers.CategoryEntityMapper;
import com.hogar360.houses.houses.infrastructure.repositories.mysql.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Optional<CategoryModel> getCategoryByName(String categoryName) {
        Optional<CategoryEntity> entityOptional = categoryRepository.findByName(categoryName);
        if (entityOptional.isPresent()) {
            CategoryModel model = categoryEntityMapper.entityToModel(entityOptional.get());
            return Optional.of(model);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public PageResult<CategoryModel> listCategories(int page, int size, boolean orderAsc) {
        Sort sort = createSort("name", orderAsc);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CategoryEntity> entityPage = categoryRepository.findAll(pageable);

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

    @Override
    public Optional<CategoryModel> findById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElse(null);
        CategoryModel model = categoryEntityMapper.entityToModel(entity);
        return Optional.ofNullable(model);
    }
}
