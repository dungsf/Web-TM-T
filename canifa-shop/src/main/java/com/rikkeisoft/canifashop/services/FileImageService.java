package com.rikkeisoft.canifashop.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rikkeisoft.canifashop.common.exception.FileStorageException;
import com.rikkeisoft.canifashop.entity.ProductEntity;
import com.rikkeisoft.canifashop.entity.ProductImageEntity;
import com.rikkeisoft.canifashop.presentation.mapper.ProductMapper;
import com.rikkeisoft.canifashop.presentation.response.ProductResponse;
import com.rikkeisoft.canifashop.repositories.ProductImageRepository;
import com.rikkeisoft.canifashop.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

public interface FileImageService {

	ProductResponse storeFile(Long id, MultipartFile avatar, MultipartFile[] images);

	Resource loadFileAsResource(String pathFile);

}

@Service
@RequiredArgsConstructor
class FileImageServiceImpl implements FileImageService {

	private final ProductRepository productRepository;

	private final ProductImageRepository productImageRepository;

	private final ProductService productService;

	private final Path root = Paths.get("uploads");

	@Override
	@Transactional
	public ProductResponse storeFile(Long id, MultipartFile avatar, MultipartFile[] images) {

		ProductEntity entity = productService.getEntityById(id);

		if (entity != null) {

			String newFile = StringUtils.cleanPath(avatar.getOriginalFilename());

			String newAvatar = id + "/avatar/" + newFile; // Tạo path lưu cho Avatar

			String newImage = id + "/images/"; // Tạo path lưu cho Images

			// Lưu Avatar
			if (!isEmptyUploadFile(avatar)) {
				if (entity.getAvatar() != null) { // Nếu có ảnh trong product => xóa path ảnh đi vào cập nhật lại
					if (this.delete(id + "/avatar/")) {
						createImageAvatar(entity, newAvatar, avatar);
					}
				} else {
					createImageAvatar(entity, newAvatar, avatar);
				}
			}

			// Lưu Images
			if (!isEmptyUploadFile(images)) {
				if (entity.getProductImagesEntities() != null && !entity.getProductImagesEntities().isEmpty()) {
					productImageRepository.deleteByProductId(id);
					if (this.delete(id + "/images")) {
						createListImages(entity, newImage, images);
					}
				} else {
					createListImages(entity, newImage, images);
				}
			}

			productRepository.save(entity);
			return ProductMapper.convertToResponse(entity);
		} else {
			return null;
		}
	}

	@Override
	public Resource loadFileAsResource(String pathFile) {
		try {
			Path filePath = this.root.resolve(pathFile).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private boolean delete(String pathFile) {
		return FileSystemUtils.deleteRecursively(new File(root + "/" + pathFile));
	}

	private void createImageAvatar(ProductEntity entity, String path, MultipartFile avatar) {
		try {
			entity.setAvatar(path);

			if (path.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + path);
			}

			new File(root + "/" + entity.getId() + "/avatar").mkdirs();

			Path targetLocation = this.root.resolve(path);
			Files.copy(avatar.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + path + ". Please try again!");
		}
	}

	private void createListImages(ProductEntity entity, String path, MultipartFile[] images) {

		new File(root + "/" + entity.getId() + "/images").mkdirs();
		try {
			entity.setProductImagesEntities(new HashSet<>());
			for (MultipartFile image : images) {
				String pathImage = path + StringUtils.cleanPath(image.getOriginalFilename());

				if (path.contains("..")) {
					throw new FileStorageException("Sorry! Filename contains invalid path sequence " + pathImage);
				}

				Path targetLocation = this.root.resolve(pathImage);
				Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				ProductImageEntity imageEntity = ProductImageEntity.builder().path(pathImage).build();
				entity.addProductImageEntity(imageEntity);

			}
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + path + ". Please try again!");
		}

	}

	private boolean isEmptyUploadFile(MultipartFile[] files) {
		if (files == null || files.length <= 0)
			return true;
		if (files.length == 1 && files[0].getOriginalFilename().isEmpty())
			return true;
		return false;
	}

	private boolean isEmptyUploadFile(MultipartFile file) {
		return file == null || file.getOriginalFilename().isEmpty();
	}

}
