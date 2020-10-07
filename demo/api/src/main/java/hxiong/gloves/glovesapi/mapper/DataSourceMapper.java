package hxiong.gloves.glovesapi.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import hxiong.gloves.glovesapi.entity.DataSource;
import hxiong.gloves.glovesapi.entity.TypeDoc;

@Mapper
public interface DataSourceMapper extends BaseMapper<DataSource>  {
  List<Map<String, Object>> getSqlDataSource(String sql);
  List<DataSource> getSqlDataSourceByDocId(TypeDoc doc);
}