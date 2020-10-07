package hxiong.gloves.glovesapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import hxiong.gloves.glovesapi.entity.PatientDoc;
import hxiong.gloves.glovesapi.entity.PatientDocInfo;

@Mapper
public interface PatientDocMapper extends BaseMapper<PatientDoc>{
  List<PatientDocInfo> getPatientDocListById(PatientDocInfo doc);
}