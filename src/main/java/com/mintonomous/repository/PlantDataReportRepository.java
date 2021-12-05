package com.mintonomous.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mintonomous.model.PlantDataReportPayload;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlantDataReportRepository extends JpaRepository<PlantDataReportPayload, Integer> {
	@Query(value = "SELECT  "
			+ "    AVG(temperature) AS avg_temperature, "
			+ "    AVG(moisture) AS avg_moisture, "
			+ "    AVG(light) AS avg_light, "
			+ "    AVG(humidity) AS avg_humidity, "
			+ "    HOUR(pd.last_updated_date) AS daily_hour, "
			+ "    (SELECT DISTINCT "
			+ "            1 "
			+ "        FROM "
			+ "            mint.action_log al, "
			+ "            mint.plant p, "
			+ "            mint.action a "
			+ "        WHERE "
			+ "            al.plant_id = p.plant_id "
			+ "                AND a.action_id = 14 "//14 represents 'water'. Hardcoding for now
			+ "                AND al.action_id = a.action_id "
			+ "                AND p.name = :plantName "
			+ "                AND HOUR(al.last_updated_date) = HOUR(pd.last_updated_date)) AS is_water_on "
			+ "FROM "
			+ "    mint.plant_data pd "
			+ "        INNER JOIN "
			+ "    mint.plant p ON pd.plant_id = p.plant_id "
			+ "        LEFT OUTER JOIN "
			+ "    mint.action_log al ON al.plant_id = p.plant_id "
			+ "WHERE "
			+ "    p.name = :plantName  "
			+ "        AND pd.last_updated_date >= (CURDATE() - INTERVAL 1 DAY) "
			+ "GROUP BY HOUR(pd.last_updated_date)", nativeQuery = true)
	List<PlantDataReportPayload> findAvgPlantDataByEveryHourForLast1Day(@Param("plantName") String plantName);
}
