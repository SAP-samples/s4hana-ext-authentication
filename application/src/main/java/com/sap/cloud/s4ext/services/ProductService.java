package com.sap.cloud.s4ext.services;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.productmaster.Product;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.ProductMasterService;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

public class ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	@Inject
	private ProductMasterService productMasterService;

	public Product findById(String productId) throws ODataRuntimeException {
		try {
			log.info("Find by productId {}", productId);

			final Product product = productMasterService.getProductByKey(productId).execute();

			log.info("Found product {}", product);
			
			return product;
		} catch (ODataException e) {
			final String message = "ODATA Exception occured in Product Service while fetching the product for given product Id";
			log.error(message, e);
			throw new ODataRuntimeException(message, e);
		}
	}

	public List<Product> findByProductGroup(String productGroup) throws ODataRuntimeException {
		try {
			log.info("Find by productGroup {}", productGroup);

			final List<Product> productsList = productMasterService.getAllProduct()
					.filter(Product.PRODUCT_GROUP.eq(productGroup))
					.execute();
			
			log.info("Found {} products", productsList.size());
			
			return productsList;
		} catch (ODataException e) {
			final String message = "ODATA Exception occured in Product Service while fetching products by productGroup";
			log.error(message, e);
			throw new ODataRuntimeException(message, e);
		}
	}

}