package hxiong.gloves.glovesapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import hxiong.gloves.glovesapi.entity.DocData;

@Mapper
public interface DocDataMapper extends BaseMapper<DocData> {
  int insertBatach(List<DocData> list);
}