package com.example.garage2.controleur;

import com.example.garage2.entite.Voitures;
import com.example.garage2.service.LicensePlateRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LicensePlateController {
    private final LicensePlateRecognitionService licensePlateRecognitionService;

    @Autowired
    public LicensePlateController(LicensePlateRecognitionService licensePlateRecognitionService) {
        this.licensePlateRecognitionService = licensePlateRecognitionService;
    }

    @GetMapping("recognize/{plateNumber}")
    public ResponseEntity<Voitures> recognizeVehicleByLicensePlate(@PathVariable("plateNumber") String plaqueImmatriculation) {
        Voitures recognizedVehicle = licensePlateRecognitionService.recognizeVehicleByLicensePlate(plaqueImmatriculation);
        if (recognizedVehicle != null) {
            return ResponseEntity.ok(recognizedVehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}