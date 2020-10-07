package hxiong.gloves.glovesapi.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import hxiong.gloves.glovesapi.entity.DocInfo;

@Mapper
public interface DocInfoMapper extends BaseMapper<DocInfo> {

  IPage<DocInfo> getDocInfoByPage(IPage<DocInfo> page, String keywords);
}