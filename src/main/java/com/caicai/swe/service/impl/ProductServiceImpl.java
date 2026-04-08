package com.caicai.swe.service.impl;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.common.ResultCode;
import com.caicai.swe.dto.ProductPublishDTO;
import com.caicai.swe.dto.ProductQueryDTO;
import com.caicai.swe.entity.Product;
import com.caicai.swe.mapper.ProductMapper;
import com.caicai.swe.service.ProductService;
import com.caicai.swe.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Long publish(ProductPublishDTO dto, Long userId) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        product.setUserId(userId);
        productMapper.insert(product);
        return product.getId();
    }

    @Override
    public ProductVO getProductById(Long id) {
        ProductVO productVO = productMapper.selectVOById(id);
        if (productVO == null) {
            throw new RuntimeException(ResultCode.PRODUCT_NOT_EXIST.getMessage());
        }
        return productVO;
    }

    @Override
    public PageResult<ProductVO> getProductList(ProductQueryDTO dto) {
        // 计算偏移量
        int offset = (dto.getPage() - 1) * dto.getSize();

        // 查询列表
        List<ProductVO> list = productMapper.selectList(
                dto.getKeyword(),
                dto.getCategoryId(),
                dto.getStatus(),
                dto.getUserId(),
                offset,
                dto.getSize()
        );

        // 查询总数
        Long total = productMapper.selectCount(
                dto.getKeyword(),
                dto.getCategoryId(),
                dto.getStatus(),
                dto.getUserId()
        );

        return PageResult.of(list, total, dto.getPage(), dto.getSize());
    }

    @Override
    public Boolean updateProduct(Product product) {
        int result = productMapper.update(product);
        return result > 0;
    }

    @Override
    public Boolean deleteProduct(Long id) {
        int result = productMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public void incrementViewCount(Long id) {
        productMapper.incrementViewCount(id);
    }

    @Override
    public void updateFavoriteCount(Long id, boolean increment) {
        if (increment) {
            productMapper.incrementFavoriteCount(id);
        } else {
            productMapper.decrementFavoriteCount(id);
        }
    }
}