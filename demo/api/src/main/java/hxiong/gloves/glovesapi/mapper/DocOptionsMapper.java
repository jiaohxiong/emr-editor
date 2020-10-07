package hxiong.gloves.glovesapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import hxiong.gloves.glovesapi.entity.DocOptions;

@Mapper
public interface DocOptionsMapper extends BaseMapper<DocOptions> {
    int insertBatach(List<DocOptions> list);
}