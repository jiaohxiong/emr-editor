package hxiong.gloves.glovesapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;

import hxiong.gloves.glovesapi.entity.DocAttr;

@Mapper
public interface DocAttrMapper extends BaseMapper<DocAttr> {
  int insertBatach(List<DocAttr> list);
}