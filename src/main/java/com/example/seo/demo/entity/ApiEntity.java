package com.example.seo.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.IDENTITY;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sports_facilities")
public class ApiEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JsonProperty("SUM_YY")
    private String aggregationYear;  // String (년도 정보)

    @JsonProperty("SIGUN_NM")
    private String cityCountyName;   // String (시군명)

    @JsonProperty("SIGUN_CD")
    private String cityCode;         // String (시군 코드)

    @JsonProperty("FACLT_NM")
    private String facilityName;     // String (시설명)

    @JsonProperty("POSESN_INST_NM")
    private String owningInstitutionName;  // String (소유 기관명)

    @JsonProperty("OPERT_ORGNZT_NM")
    private String operatingOrganizationName;  // String (운영 조직명)

    @JsonProperty("CONTCT_NO")
    private String contactNumber;    // String (연락처 번호)

    @JsonProperty("HMPG_ADDR")
    private String homepageAddress;  // String (홈페이지 주소)

    @JsonProperty("MANAGE_MAINBD_NM")
    private String managingEntityName;  // String (관리 주체)

    @JsonProperty("PLOT_AR")
    private Integer landArea;        // Integer (대지 면적)

    @JsonProperty("BUILD_AR")
    private Integer buildingArea;    // Integer (건축 면적)

    @JsonProperty("TOT_AR")
    private Integer totalArea;       // Integer (총 면적)

    @JsonProperty("BOTM_MATRL_NM")
    private String floorMaterialName;  // String (바닥 재질)

    @JsonProperty("BT")
    private Integer height;          // Integer (높이)

    @JsonProperty("LENG")
    private Integer length;          // Integer (길이)

    @JsonProperty("AR")
    private Integer area;            // Integer (면적)

    @JsonProperty("PLANE_CNT")
    private Integer planeCount;      // Integer (면 수)

    @JsonProperty("AUDTRM_SEAT_CNT")
    private Integer spectatorSeats;  // Integer (관람석 좌석 수)

    @JsonProperty("ACEPTNC_PSN_CNT")
    private Integer acceptancePersonCount;  // Integer (수용 인원 수)

    @JsonProperty("SEAT_FORM_NM")
    private String seatTypeName;     // String (좌석 형태)

    @JsonProperty("BUILD_RESCUE_NM")
    private String buildingRescueName;  // String (구조물명)

    @JsonProperty("COMPLTN_YY")
    private String completionYear;   // String (완공 년도)

    @JsonProperty("CONSTR_BIZ_EXPN")
    private Integer constructionBusinessExpense;  // Integer (건설비용)

    @JsonProperty("RM_MATR")
    private String remainingMaterial;  // String (재료)

    @JsonProperty("REFINE_LOTNO_ADDR")
    private String landAddress;      // String (지번 주소)

    @JsonProperty("REFINE_ROADNM_ADDR")
    private String roadAddress;      // String (도로명 주소)

    @JsonProperty("REFINE_ZIP_CD")
    private String postalCode;       // String (우편번호)

    @JsonProperty("REFINE_WGS84_LOGT")
    private String longitude;        // String (경도)

    @JsonProperty("REFINE_WGS84_LAT")
    private String latitude;         // String (위도)

}
