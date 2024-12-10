package com.example.seo.demo.controller;

import com.example.seo.demo.entity.ApiEntity;
import com.example.seo.demo.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sports-facilities")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")  // React 앱의 주소로 설정
public class ApiEntityController {

    private final ApiService apiService;

    public ApiEntityController(ApiService apiService) {
        this.apiService = apiService;
    }

    // 데이터 로드 및 저장
    @GetMapping("/load")
    public String loadSaveSportsFacilitiesApi() {
        try {
            apiService.loadAndSaveSportsFacilities(); // 서비스 호출
            return "데이터 저장 완료";
        } catch (Exception e) {
            log.error("데이터 저장 중 오류 발생", e);
            return "데이터 저장 실패: " + e.getMessage();
        }
    }

    // 검색 기능(시, 군, 명)
    @GetMapping("/search")
    public List<ApiEntity> searchFacilities(
            @RequestParam(required = false, defaultValue = "") String cityCountyName,
            @RequestParam(required = false, defaultValue = "") String facilityName,
            @RequestParam(required = false, defaultValue = "") String owningInstitutionName) {
        log.info("검색 요청: cityCountyName={}, facilityName={}, owningInstitutionName={}", cityCountyName, facilityName, owningInstitutionName);
        return apiService.searchFacilities(cityCountyName, facilityName, owningInstitutionName);
    }
}
