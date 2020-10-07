package hxiong.gloves.glovesapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import hxiong.gloves.glovesapi.entity.PatientDocInfo;
import hxiong.gloves.glovesapi.entity.PatientDocList;

@Mapper
public interface PatientDocListMapper extends BaseMapper<PatientDocList> {
  int addPatientDocList(PatientDocList doc);

  PatientDocInfo getPatientDocInfoById(PatientDocInfo doc);
  PatientDocInfo getPatientDocInfoById4M(PatientDocInfo doc);
}