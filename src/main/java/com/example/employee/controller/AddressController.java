package com.example.employee.controller;

import com.example.employee.dto.AddressDTO;
import com.example.employee.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);


    private final AddressService addressService;

    // Constructor injection for AddressService
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Create a new Address
    @PostMapping("/saveAddress")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody Map<String, Object> payload) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddress((String) payload.get("address"));
        addressDTO.setCity((String) payload.get("city"));

        if (payload.containsKey("employee_id")) {
            addressDTO.setEmployeeId(((Number) payload.get("employee_id")).longValue());
        }

        // Save the address and return the created AddressDTO
        return ResponseEntity.ok(addressService.saveAddress(addressDTO));
    }

    // Get all addresses
    @GetMapping("/getAllAddress")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addressDTOList = addressService.getAllAddresses();
        return ResponseEntity.ok(addressDTOList);
    }

    // Get Address by ID
    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Address by ID
    @PutMapping("/updateAddressById/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        AddressDTO updatedAddressDTO = new AddressDTO();
        if (payload.containsKey("address")) {
            updatedAddressDTO.setAddress((String) payload.get("address"));
        }
        if (payload.containsKey("city")) {
            updatedAddressDTO.setCity((String) payload.get("city"));
        }
        if (payload.containsKey("employee_id")) {
            updatedAddressDTO.setEmployeeId(((Number) payload.get("employee_id")).longValue());
        }

        // Update the address and return the updated AddressDTO
        return addressService.updateAddress(id, updatedAddressDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete Address by ID
    @DeleteMapping("/deleteAddressById/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        return addressService.deleteAddressById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
