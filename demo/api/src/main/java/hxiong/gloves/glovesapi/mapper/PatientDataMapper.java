package hxiong.gloves.glovesapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hxiong.gloves.glovesapi.entity.PatientData;

@Mapper
public interface PatientDataMapper extends BaseMapper<PatientData> {
  int copyDocDataToPatientData(@Param(value="docId") String docId,
      @Param(value = "docVersion") Byte docVersion, @Param(value = "patientDocId") String patientDocId,
      @Param(value = "patientDocVersion") Byte patientDocVersion);
  int insertBatach(List<PatientData> list);
}