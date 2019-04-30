/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.product.api;

import java.util.List;
import com.diviso.graeshoppe.client.product.model.Product;
import com.diviso.graeshoppe.client.product.model.ProductDTO;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-04-30T16:24:43.095+05:30[Asia/Calcutta]")

@Api(value = "ProductResource", description = "the ProductResource API")
public interface ProductResourceApi {

    @ApiOperation(value = "createProduct", nickname = "createProductUsingPOST", notes = "", response = ProductDTO.class, tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/products",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<ProductDTO> createProductUsingPOST(@ApiParam(value = "productDTO" ,required=true )  @Valid @RequestBody ProductDTO productDTO);


    @ApiOperation(value = "deleteProduct", nickname = "deleteProductUsingDELETE", notes = "", tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/api/products/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteProductUsingDELETE(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllProducts", nickname = "getAllProductsUsingGET", notes = "", response = ProductDTO.class, responseContainer = "List", tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/products",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<ProductDTO>> getAllProductsUsingGET(@ApiParam(value = "eagerload", defaultValue = "false") @Valid @RequestParam(value = "eagerload", required = false, defaultValue="false") Boolean eagerload,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "getProduct", nickname = "getProductUsingGET", notes = "", response = ProductDTO.class, tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/products/{id}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<ProductDTO> getProductUsingGET(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "listToDto", nickname = "listToDtoUsingPOST", notes = "", response = ProductDTO.class, responseContainer = "List", tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class, responseContainer = "List"),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/products/toDto",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<List<ProductDTO>> listToDtoUsingPOST(@ApiParam(value = "products" ,required=true )  @Valid @RequestBody List<Product> product);


    @ApiOperation(value = "searchProducts", nickname = "searchProductsUsingGET", notes = "", response = ProductDTO.class, responseContainer = "List", tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/_search/products",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<ProductDTO>> searchProductsUsingGET(@NotNull @ApiParam(value = "query", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "updateProduct", nickname = "updateProductUsingPUT", notes = "", response = ProductDTO.class, tags={ "product-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = ProductDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/products",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<ProductDTO> updateProductUsingPUT(@ApiParam(value = "productDTO" ,required=true )  @Valid @RequestBody ProductDTO productDTO);

}
