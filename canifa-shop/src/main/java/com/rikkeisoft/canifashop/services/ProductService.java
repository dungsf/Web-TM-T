package com.rikkeisoft.canifashop.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.rikkeisoft.canifashop.entity.*;
import com.rikkeisoft.canifashop.presentation.request.ColorSizeRequest;
import com.rikkeisoft.canifashop.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.github.slugify.Slugify;
import com.rikkeisoft.canifashop.base.BasePagerData;
import com.rikkeisoft.canifashop.common.exception.BadRequestException;
import com.rikkeisoft.canifashop.entity.ProductEntity;
import com.rikkeisoft.canifashop.presentation.mapper.ProductMapper;
import com.rikkeisoft.canifashop.presentation.request.ProductRequest;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.repositories.ProductDetailRepository;
import com.rikkeisoft.canifashop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

public interface ProductService {
	List<ProductResponse> getAll();

	BasePagerData<ProductResponse> getProductsByPaging(Integer page, Integer size, String keyword);

	BasePagerData<ProductResponse> getProductsByKeyword(Integer page, Integer page_size, String search,
			BigDecimal priceMin, BigDecimal priceMax, Long categoryId);

	ProductResponse createOrUpdate(Long id, ProductRequest request);

	ProductResponse getById(Long id);

	ProductResponse getBySeo(String seo);

	List<ProductResponse> getByHot();

	void deleteById(Long id);

	ProductEntity getEntityById(Long id);

	ProductEntity getEntityBySeo(String seo);
}

@Service
@RequiredArgsConstructor
class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductDetailRepository productDetailRepository;
	private final CategoryService categoryService;
	private final ColorService colorService;
	private final SizeService sizeService;

	@Override
	public List<ProductResponse> getAll() {
		List<ProductEntity> productEntities = productRepository.findAll();
		return productEntities.stream().map(e -> ProductMapper.convertToResponse(e)).collect(Collectors.toList());
	}

	@Override
	public BasePagerData<ProductResponse> getProductsByPaging(Integer page, Integer size, String keyword) {
		Pageable paging = PageRequest.of(page, size);
		if (keyword.equals("-1") || keyword.isEmpty()) {
			keyword = null;
		}
		Page<ProductEntity> pagedResult = productRepository.searchByKeyword(keyword, paging);
		return BasePagerData.build(pagedResult.map(e -> ProductMapper.convertToResponse(e)));
	}

	@Override
	public BasePagerData<ProductResponse> getProductsByKeyword(Integer page, Integer size, String search,
			BigDecimal priceMin, BigDecimal priceMax, Long categoryId) {
		Pageable paging = PageRequest.of(page, size);
		CategoryEntity categoryEntity = categoryService.getEntityById(categoryId);
		Set<Long> listCategoryId = new HashSet<>();
		if (categoryEntity != null) {
			categoryEntity.getListCategoryId(listCategoryId);
		}
		String checkCategory = "1";
		if (search.equals("-1")) {
			search = null;
		}
		if (priceMin.compareTo(new BigDecimal(-1)) == 0) {
			priceMin = null;
		}
		if (priceMax.compareTo(new BigDecimal(-1)) == 0) {
			priceMax = null;
		}
		if (categoryEntity == null) {
			checkCategory = null;
		}
		Page<ProductEntity> pagedResult = productRepository.searchByKeyword(search, priceMin, priceMax, checkCategory,
				listCategoryId, paging);
		return BasePagerData.build(pagedResult.map(e -> ProductMapper.convertToResponse(e)));
	}

	@Override
	@Transactional
	public ProductResponse createOrUpdate(Long id, ProductRequest request) {
		if (id == null || id < 1) {
			ProductEntity product = ProductMapper.convertToEntity(request);
			product.setSeo(new Slugify().slugify(request.getName()));
			// Tạo productDetail
			createProductDetail(product, request.getListColors());
			return ProductMapper.convertToResponse(productRepository.save(product));

		} else {
			ProductEntity product = productRepository.getById(id);
			CategoryEntity categoryEntity = categoryService.getEntityById(request.getCategoryId());
			if (product != null) {
				product.setName(request.getName());
				product.setPrice(request.getPrice());
				product.setDiscount(request.getDiscount());
				product.setDescription(request.getDesciption());
				product.setMaterial(request.getMaterial());
				product.setTutorial(request.getTutorial());
				product.setHot(request.isHot());
				product.setSeo(new Slugify().slugify(request.getName()));
				product.setCategoryEntity(categoryEntity);

				// Xóa tất cả productDetail cũ
				productDetailRepository.deleteByProductId(product.getId());
				// Tạo productDetail mới
				createProductDetail(product, request.getListColors());

				return ProductMapper.convertToResponse(productRepository.save(product));
			} else {
				return null;
			}
		}
	}

	@Override
	public ProductResponse getById(Long id) {
		return ProductMapper.convertToResponse(this.getEntityById(id));
	}

	@Override
	public ProductResponse getBySeo(String seo) {
		return ProductMapper.convertToResponse(this.getEntityBySeo(seo));
	}

	@Override
	public List<ProductResponse> getByHot() {
		List<ProductEntity> productEntities = productRepository.findByHot();
		return productEntities.stream().map(e -> ProductMapper.convertToResponse(e)).collect(Collectors.toList());
	}

	@Override
	public void deleteById(Long id) {
		ProductEntity entity = this.getEntityById(id);
		if (entity == null) {
			throw new BadRequestException("Product does not exist");
		}
		entity.setDeletedFlag(true);
		productRepository.save(entity);
	}

	@Override
	public ProductEntity getEntityById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public ProductEntity getEntityBySeo(String seo) {
		if (seo == null) {
			return null;
		} else {
			return productRepository.findBySeo(seo).get(0);
		}
	}

	private void createProductDetail(ProductEntity product, List<ColorSizeRequest> listColors) {
		product.setProductDetailEntities(new HashSet<>());
		listColors.forEach(c -> {
			ColorEntity colorEntity = colorService.getEntityById(c.getId());
			c.getListSizes().forEach(s -> {
				SizeEntity sizeEntity = sizeService.getEntityById(s);
				ProductDetailEntity productDetail = ProductDetailEntity.builder().colorEntity(colorEntity)
						.sizeEntity(sizeEntity).build();
				product.addProductDetailEntity(productDetail);
			});
		});
	}

}