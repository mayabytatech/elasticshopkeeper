/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.product.api;

import com.diviso.graeshoppe.client.product.model.BarcodeDTO;
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

@Api(value = "BarcodeResource", description = "the BarcodeResource API")
public interface BarcodeResourceApi {

    @ApiOperation(value = "createBarcode", nickname = "createBarcodeUsingPOST", notes = "", response = BarcodeDTO.class, tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BarcodeDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/barcodes",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<BarcodeDTO> createBarcodeUsingPOST(@ApiParam(value = "barcodeDTO" ,required=true )  @Valid @RequestBody BarcodeDTO barcodeDTO);


    @ApiOperation(value = "deleteBarcode", nickname = "deleteBarcodeUsingDELETE", notes = "", tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/api/barcodes/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteBarcodeUsingDELETE(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllBarcodes", nickname = "getAllBarcodesUsingGET", notes = "", response = BarcodeDTO.class, responseContainer = "List", tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BarcodeDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/barcodes",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<BarcodeDTO>> getAllBarcodesUsingGET(@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "getBarcode", nickname = "getBarcodeUsingGET", notes = "", response = BarcodeDTO.class, tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BarcodeDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/barcodes/{id}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<BarcodeDTO> getBarcodeUsingGET(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "searchBarcodes", nickname = "searchBarcodesUsingGET", notes = "", response = BarcodeDTO.class, responseContainer = "List", tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BarcodeDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/_search/barcodes",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<BarcodeDTO>> searchBarcodesUsingGET(@NotNull @ApiParam(value = "query", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "updateBarcode", nickname = "updateBarcodeUsingPUT", notes = "", response = BarcodeDTO.class, tags={ "barcode-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = BarcodeDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/barcodes",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<BarcodeDTO> updateBarcodeUsingPUT(@ApiParam(value = "barcodeDTO" ,required=true )  @Valid @RequestBody BarcodeDTO barcodeDTO);

}
