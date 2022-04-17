package com.rikkeisoft.canifashop.services;

import com.github.slugify.Slugify;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.CategoryEntity;
import com.rikkeisoft.canifashop.entity.ProductEntity;
import com.rikkeisoft.canifashop.presentation.mapper.CategoryMapper;
import com.rikkeisoft.canifashop.presentation.mapper.ProductMapper;
import com.rikkeisoft.canifashop.presentation.request.CategoryRequest;
import com.rikkeisoft.canifashop.presentation.response.CategoryResponse;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.repositories.CategoryRepository;
import com.rikkeisoft.canifashop.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface CategoryService {

	List<CategoryResponse> getAll();

	BasePagerData<CategoryResponse> getCategoryByPaging(Integer page, Integer size, String keyword);

	CategoryResponse createOrUpdate(Long id, CategoryRequest categoryRequest);

	BasePagerData<ProductResponse> getProductByCategory(Integer page, Integer pAGE_SIZE, String seo);

	void deleteById(Long id);

	CategoryResponse getById(Long id);

	CategoryResponse getBySeo(String seo);

	CategoryEntity getEntityById(Long id);

	CategoryEntity getEntityBySeo(String seo);

	List<CategoryResponse> getCategoryParent();
}

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryResponse> getAll() {
		List<CategoryEntity> categoryEntities = categoryRepository.findAll();
		return categoryEntities.stream().map(e -> CategoryMapper.convertToResponse(e)).collect(Collectors.toList());
	}

	@Override
	public BasePagerData<CategoryResponse> getCategoryByPaging(Integer page, Integer size, String keyword) {

		Pageable paging = PageRequest.of(page, size);

		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}

		Page<CategoryEntity> pagedResult = categoryRepository.searchByKeyword(keyword, paging);
		return BasePagerData.build(pagedResult.map(e -> CategoryMapper.convertToResponse(e)));

	}

	@Override
	public CategoryResponse createOrUpdate(Long id, CategoryRequest request) {
		CategoryEntity categoryParent = this.getEntityById(request.getParentId());
		if (id == null || id < 1) {
			CategoryEntity categoryEntity = CategoryMapper.convertToEntity(request);
			categoryEntity.setSeo(new Slugify().slugify(categoryEntity.getName()));
			categoryEntity.setParent(categoryParent);
			return CategoryMapper.convertToResponse(categoryRepository.save(categoryEntity));
		} else {
			CategoryEntity categoryEntity = this.getEntityById(id);
			if (categoryEntity != null) {
				categoryEntity.setName(request.getName());
				categoryEntity.setSeo(new Slugify().slugify(request.getName()));
				categoryEntity.setParent(categoryParent);
				return CategoryMapper.convertToResponse(categoryRepository.save(categoryEntity));
			} else {
				return null;
			}
		}
	}

	@Override
	public BasePagerData<ProductResponse> getProductByCategory(Integer page, Integer size, String seo) {
		Pageable paging = PageRequest.of(page, size);
		CategoryEntity categoryEntity = this.getEntityBySeo(seo);
		Set<Long> listCategoryId = new HashSet<>();
		if (categoryEntity != null) {
			categoryEntity.getListCategoryId(listCategoryId);
		}
		String checkCategory = "1";
		if (categoryEntity == null) {
			checkCategory = null;
		}
		
		Set<ProductEntity> listProducts = new HashSet<>();
		if (categoryEntity != null) {
			categoryEntity.getListProducts(listProducts);
		}
		
		Page<ProductEntity> pagedResult = productRepository.searchByCategories(checkCategory,
				listCategoryId, paging);
		return BasePagerData.build(pagedResult.map(e -> ProductMapper.convertToResponse(e)));
	}

	@Override
	public void deleteById(Long id) {
		CategoryEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("Category does not exist");
		}
		entity.setDeletedFlag(true);
		categoryRepository.save(entity);
	}

	@Override
	public CategoryResponse getById(Long id) {
		return CategoryMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public CategoryEntity getEntityById(Long id) {
		try {
			return categoryRepository.findById(id).orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CategoryResponse getBySeo(String seo) {
		return CategoryMapper.convertToResponse(this.getEntityBySeo(seo));
	}

	public CategoryEntity getEntityBySeo(String seo) {
		if (seo == null) {
			return null;
		} else {
			return categoryRepository.findBySeo(seo).get(0);
		}
	}

	@Override
	public List<CategoryResponse> getCategoryParent() {
		return categoryRepository.getAllCategoriesParent().stream().map(e -> CategoryMapper.convertToResponse(e))
				.collect(Collectors.toList());
	}

}
