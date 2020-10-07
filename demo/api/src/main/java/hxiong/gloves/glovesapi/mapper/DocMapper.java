package hxiong.gloves.glovesapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hxiong.gloves.glovesapi.entity.Doc;

@Mapper
public interface DocMapper extends BaseMapper<Doc> {
  List<Doc> getDocListById(@Param(value = "docId") String docId);
  List<Doc> getMaxDocList();
}