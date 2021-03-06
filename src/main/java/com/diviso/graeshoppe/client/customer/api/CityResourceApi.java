/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.customer.api;

import com.diviso.graeshoppe.client.customer.model.CityDTO;
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
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-05-02T11:06:23.898839200+05:30[Asia/Calcutta]")

@Api(value = "CityResource", description = "the CityResource API")
public interface CityResourceApi {

    @ApiOperation(value = "createCity", nickname = "createCityUsingPOST", notes = "", response = CityDTO.class, tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CityDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/cities",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.POST)
    ResponseEntity<CityDTO> createCityUsingPOST(@ApiParam(value = "cityDTO" ,required=true )  @Valid @RequestBody CityDTO cityDTO);


    @ApiOperation(value = "deleteCity", nickname = "deleteCityUsingDELETE", notes = "", tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden") })
    @RequestMapping(value = "/api/cities/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCityUsingDELETE(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "getAllCities", nickname = "getAllCitiesUsingGET", notes = "", response = CityDTO.class, responseContainer = "List", tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CityDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/cities",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<CityDTO>> getAllCitiesUsingGET(@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "getCity", nickname = "getCityUsingGET", notes = "", response = CityDTO.class, tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CityDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/cities/{id}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<CityDTO> getCityUsingGET(@ApiParam(value = "id",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "searchCities", nickname = "searchCitiesUsingGET", notes = "", response = CityDTO.class, responseContainer = "List", tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CityDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/_search/cities",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<CityDTO>> searchCitiesUsingGET(@NotNull @ApiParam(value = "query", required = true) @Valid @RequestParam(value = "query", required = true) String query,@ApiParam(value = "Page number of the requested page") @Valid @RequestParam(value = "page", required = false) Integer page,@ApiParam(value = "Size of a page") @Valid @RequestParam(value = "size", required = false) Integer size,@ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.") @Valid @RequestParam(value = "sort", required = false) List<String> sort);


    @ApiOperation(value = "updateCity", nickname = "updateCityUsingPUT", notes = "", response = CityDTO.class, tags={ "city-resource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = CityDTO.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/cities",
        produces = "*/*", 
        consumes = "application/json",
        method = RequestMethod.PUT)
    ResponseEntity<CityDTO> updateCityUsingPUT(@ApiParam(value = "cityDTO" ,required=true )  @Valid @RequestBody CityDTO cityDTO);

}
