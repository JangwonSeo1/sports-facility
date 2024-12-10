package com.example.seo.demo.service;

import com.example.seo.demo.ApiRepository;
import com.example.seo.demo.entity.ApiEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ApiService {

    private final ApiRepository apiRepository;

    @Value("${serviceKey}")
    private String serviceKey;

    @Value("${openapiUrl}")
    private String openapiUrl;

    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    public void loadAndSaveSportsFacilities() {
        int pageSize = 100; // 한 번에 가져올 데이터 수
        int pageIndex = 1;  // pIndex 기본값 1
        int totalCount = 0; // 총 데이터 개수 (응답에서 가져옴)

        try {
            // 첫 번째 요청을 통해 totalCount를 가져옴
            String apiUrl = buildApiUrl(pageIndex, pageSize);
            String responseData = getApiResponse(apiUrl);
            totalCount = getTotalCountFromResponse(responseData);

            log.info("총 데이터 개수: {}", totalCount);

            // 데이터가 존재할 때까지 페이지별로 요청
            while (pageIndex <= Math.ceil(totalCount / (double) pageSize)) {
                apiUrl = buildApiUrl(pageIndex, pageSize);
                System.out.println(apiUrl);
                responseData = getApiResponse(apiUrl);

                List<ApiEntity> facilities = parseApiResponse(responseData);
                saveFacilities(facilities);

                pageIndex++; // 다음 페이지로 이동
            }

        } catch (Exception e) {
            log.error("데이터 저장 중 오류 발생", e);
            throw new RuntimeException("데이터 저장 실패: " + e.getMessage());
        }
    }

    // API URL 생성
    private String buildApiUrl(int pageIndex, int pageSize) {
        return openapiUrl + "key=" + serviceKey + "&type=json&pindex=" + pageIndex + "&psize=" + pageSize;
    }

    // API 응답 데이터 가져오기
    private String getApiResponse(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-type", "application/json");

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            return bf.readLine();
        }
    }

    // 응답 데이터에서 총 데이터 개수 가져오기
    private int getTotalCountFromResponse(String responseData) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);

        // PublicTrainingFacilitySoccer -> head 배열 처리
        JSONArray publicTrainingFacilitySoccer = (JSONArray) jsonObject.get("PublicTrainingFacilitySoccer");
        if (publicTrainingFacilitySoccer == null || publicTrainingFacilitySoccer.isEmpty()) {
            log.error("PublicTrainingFacilitySoccer 데이터가 없습니다.");
            return 0;
        }

        // head는 배열, 첫 번째 객체에서 totalCount 추출
        JSONObject headObject = (JSONObject) publicTrainingFacilitySoccer.get(0);
        JSONArray headArray = (JSONArray) headObject.get("head");
        JSONObject totalCountObj = (JSONObject) headArray.get(0);
        return totalCountObj != null ? Integer.parseInt(totalCountObj.get("list_total_count").toString()) : 0;
    }

    // API 응답 데이터 파싱
    private List<ApiEntity> parseApiResponse(String responseData) throws Exception {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseData);

        // PublicTrainingFacilitySoccer -> row 배열만 추출
        JSONArray publicTrainingFacilitySoccer = (JSONArray) jsonObject.get("PublicTrainingFacilitySoccer");

        if (publicTrainingFacilitySoccer == null || publicTrainingFacilitySoccer.isEmpty()) {
            log.info("응답 데이터가 없습니다.");
            return new ArrayList<>();
        }

        // row 배열 추출 및 처리
        JSONArray rowArray = (JSONArray) ((JSONObject) publicTrainingFacilitySoccer.get(1)).get("row");
        if (rowArray == null || rowArray.isEmpty()) {
            log.info("row 데이터가 없습니다.");
            return new ArrayList<>();
        }

        return extractFacilities(rowArray);
    }

    // 시설 목록 추출
    private List<ApiEntity> extractFacilities(JSONArray rowArray) {
        List<ApiEntity> facilities = new ArrayList<>();

        for (Object obj : rowArray) {
            if (obj instanceof JSONObject data) {
                ApiEntity facility = new ApiEntity(
                        null,
                        getStringValue(data, "SUM_YY"),
                        getStringValue(data, "SIGUN_NM"),
                        getStringValue(data, "SIGUN_CD"),
                        getStringValue(data, "FACLT_NM"),
                        getStringValue(data, "POSESN_INST_NM"),
                        getStringValue(data, "OPERT_ORGNZT_NM"),
                        getStringValue(data, "CONTCT_NO"),
                        getStringValue(data, "HMPG_ADDR"),
                        getStringValue(data, "MANAGE_MAINBD_NM"),
                        getIntegerValue(data, "PLOT_AR"),
                        getIntegerValue(data, "BUILD_AR"),
                        getIntegerValue(data, "TOT_AR"),
                        getStringValue(data, "BOTM_MATRL_NM"),
                        getIntegerValue(data, "BT"),
                        getIntegerValue(data, "LENG"),
                        getIntegerValue(data, "AR"),
                        getIntegerValue(data, "PLANE_CNT"),
                        getIntegerValue(data, "AUDTRM_SEAT_CNT"),
                        getIntegerValue(data, "ACEPTNC_PSN_CNT"),
                        getStringValue(data, "SEAT_FORM_NM"),
                        getStringValue(data, "BUILD_RESCUE_NM"),
                        getStringValue(data, "COMPLTN_YY"),
                        getIntegerValue(data, "CONSTR_BIZ_EXPN"),
                        getStringValue(data, "RM_MATR"),
                        getStringValue(data, "REFINE_LOTNO_ADDR"),
                        getStringValue(data, "REFINE_ROADNM_ADDR"),
                        getStringValue(data, "REFINE_ZIP_CD"),
                        getStringValue(data, "REFINE_WGS84_LOGT"),
                        getStringValue(data, "REFINE_WGS84_LAT")
                );

                facilities.add(facility);
            }
        }

        return facilities;
    }

    // 시설 데이터 저장
    private void saveFacilities(List<ApiEntity> facilities) {
        for (ApiEntity facility : facilities) {
            // 중복 여부를 확인하는 부분
            Optional<ApiEntity> existingFacility = apiRepository.findByFacilityName(facility.getFacilityName());

            if (existingFacility.isEmpty()) {
                // 중복되지 않으면 새로운 시설 데이터를 저장
                apiRepository.save(facility);
                log.info("데이터 저장 완료: {}", facility.getFacilityName());
            } else {
                // 이미 존재하는 데이터는 저장하지 않음
                log.info("이미 존재하는 데이터: {}", facility.getFacilityName());
            }
        }
    }


    // 헬퍼 메서드
    private String getStringValue(JSONObject data, String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    private Integer getIntegerValue(JSONObject data, String key) {
        Object value = data.get(key);
        try {
            return value != null ? Integer.parseInt(value.toString()) : null;
        } catch (NumberFormatException e) {
            return null; // 변환 실패 시 null 반환
        }
    }

    public List<ApiEntity> searchFacilities(String cityCountyName, String facilityName, String owningInstitutionName) {
        return apiRepository.findByCityCountyNameContainingAndFacilityNameContainingAndOwningInstitutionNameContaining(
                cityCountyName, facilityName, owningInstitutionName);
    }
}
