package hxiong.gloves.glovesapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import hxiong.gloves.glovesapi.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import hxiong.gloves.glovesapi.entity.*;
import hxiong.gloves.glovesapi.mapper.*;

import hxiong.gloves.glovesapi.service.GlovesService;
import hxiong.gloves.glovesapi.util.RedisUtil;

@Service
public class GlovesServiceImpl implements GlovesService {

  private Logger logger = LoggerFactory.getLogger(GlovesServiceImpl.class);

  @Autowired
  private DocMapper docMapper;

  @Autowired
  private DataSourceMapper dataMapper;

  @Autowired
  private DocDataMapper docDataMapper;

  @Autowired
  private DocAttrMapper docAtrrMapper;

  @Autowired
  private DocInfoMapper docInfoMapper;

  @Autowired
  private PatientDocMapper patientDocMapper;

  @Autowired
  private PatientDocListMapper patientDocListMapper;

  @Autowired
  private PatientInfoMapper patientInfoMapper;

  @Autowired
  private PatientDataMapper patientDataMapper;

  @Autowired
  private DataControlMapper dataControlMapper;

  @Autowired
  private DeptMapper deptMapper;

  @Autowired
  private DataControlTypeMapper dataControlTypeMapper;

  @Autowired
  private DataControlOptionMapper dataControlOptionMapper;

  @Autowired
  private DocOptionsMapper docOptionsMapper;

  @Autowired
  private RedisUtil redis;

  @Autowired
  private DocTypeMapper docTypeMapper;

  @Autowired
  private DocOfficialMapper docofficialMapper;

  @Autowired
  private TypeDocMapper typeDocMapper;

  @Autowired
  private TypePatientDocMapper typePatientDocMapper;

  @Override
  public List<Doc> getAllDocList() {
    LambdaQueryWrapper<Doc> query = new LambdaQueryWrapper<>();
    query.orderBy(true, true, Doc::getDocId, Doc::getVersion).select(Doc::getDocId, Doc::getName, Doc::getId,
        Doc::getVersion, Doc::getType,Doc::getTypeName, Doc::getCreateTime);
    return docMapper.selectList(query);
  }

  @Override
  public int addDoc(Doc doc) {
    return this.addDoc(doc, doc.getData(), doc.getAttrs());
  }

  @Override
  public int updateDoc(Doc doc) {
    return docMapper.updateById(doc);
  }

  @Override
  public List<DataSource> getAllDataSource() {
    return dataMapper.selectList(null);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addDoc(Doc doc, List<DocData> data, List<DocAttr> attr) {
    /**
     * 
     * 版本号概念 1、文档标号+版本号确定唯一文档 2、版本用于数据变化追踪
     */

    try {

      // 获取文档版本号
      QueryWrapper<Doc> query = new QueryWrapper<>();
      query.eq("doc_id", doc.getDocId()).select("doc_id", "max(version) version");
      Doc maxVersion = docMapper.selectOne(query);
      if (maxVersion != null) {
        doc.setVersion((byte)(maxVersion.getVersion() + 1));
      } else {
        doc.setVersion((byte)1); // 初始版本号
      }

      doc.setCreateTime(new Date());

      logger.info("Document [" + doc.getDocId() + "] 当前版本号为 [" + doc.getVersion() + "]");

      int count = docMapper.insert(doc);
      if (count == 0)
        throw new RuntimeException("插入[DOC]数据表失败！");
      for (DocData d : data) {
        d.setDocId(doc.getDocId());
        d.setVersion(doc.getVersion());
        // count = docDataMapper.insert(d);
        // if (count == 0)
        //   throw new RuntimeException("插入[DATA]数据表失败！");
      }

      if(data != null && data.size() > 0) {
        docDataMapper.insertBatach(data);
      }
      List<DocOptions> options = new ArrayList<>();

      for (DocAttr d : attr) {
        d.setDocId(doc.getDocId());
        d.setVersion(doc.getVersion());
        // count = docAtrrMapper.insert(d);

        if (d.getOptions() != null && d.getOptions().size() > 0) {
          for (DocOptions p : d.getOptions()) {
            p.setNodeId(d.getNodeId());
          }
          options.addAll(d.getOptions());
        }

        // if (count == 0)
        //   throw new RuntimeException("插入[ATTR]数据表失败！");
      }

      if(attr != null && attr.size() > 0) docAtrrMapper.insertBatach(attr);

      for (DocOptions d : options) {
        d.setDocId(doc.getDocId());
        d.setDocVersion(doc.getVersion());

        // count = docOptionsMapper.insert(d);
        // if (count == 0)
        //   throw new RuntimeException("插入[ATTR]数据表失败！");
      }

      if(options != null && options.size() > 0) docOptionsMapper.insertBatach(options);

      DocInfo info = docInfoMapper.selectOne(Wrappers.lambdaQuery(DocInfo.class).eq(DocInfo::getDocId, doc.getDocId()));

      if(info == null) {
        info = new DocInfo();
        info.setCreateTime(new Date());
        info.setCreateUserId("001");
        info.setCreateUserName("叫黄兄");
        info.setDocId(doc.getDocId());
        info.setDocName(doc.getName());
        info.setType(doc.getType());
        info.setTypeName(doc.getTypeName());
        info.setLastVersion(doc.getVersion());

        docInfoMapper.insert(info);
      } else {
        info.setLastUpdateTime(new Date());
        info.setLastUpdateUserId("001");
        info.setLastUpdateUserName("叫黄兄");
        info.setLastVersion(doc.getVersion());

        docInfoMapper.updateById(info);
      }
      return 1;
    } catch (Exception e) {
      // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

      throw e;
    }
  }

  @Override
  public Doc getDocById(Doc doc) {

    if(doc.getVersion() == null || doc.getVersion() == 0) {
      QueryWrapper<Doc> query = new QueryWrapper<>();
      query.eq("doc_id", doc.getDocId()).select("doc_id", "max(version) version");
      Doc maxVersion = docMapper.selectOne(query);
      if(maxVersion != null) doc.setVersion(maxVersion.getVersion());
    }
    LambdaQueryWrapper<Doc> query = new LambdaQueryWrapper<>();
    query.eq(Doc::getDocId, doc.getDocId()).eq(Doc::getVersion, doc.getVersion()).select(Doc::getDocId, Doc::getName,
        Doc::getId, Doc::getVersion, Doc::getType, Doc::getTypeName, Doc::getCreateTime, Doc::getHtml,Doc::getLayout);

    Doc d = docMapper.selectOne(query);

    if (d == null && !StringUtils.isEmpty(doc.getName())) {
      d = new Doc();
      d.setName(doc.getName());
      d.setCreateTime(new Date());
      d.setCreateUserId("001");
      d.setCreateUserName("叫黄兄");
      d.setHtml("新建空白文档");
      d.setVersion((byte) 1);
      d.setDocId(doc.getDocId());
      d.setType(doc.getType());
      d.setTypeName(doc.getTypeName());

      docMapper.insert(d);
    } else {
      
    }

    return d;
  }
  
  @Override
  public Doc getDocInfoById(String docId) {

    QueryWrapper<Doc> query = new QueryWrapper<>();
    query.eq("doc_id", docId).select("doc_id", "max(version) version");
    Doc doc = docMapper.selectOne(query);
    if (doc == null) {
      return null;
    }

    doc = docMapper.selectOne(Wrappers.lambdaQuery(Doc.class).eq(Doc::getDocId,
        doc.getDocId()).eq(Doc::getVersion, doc.getVersion()));

    List<DocAttr> attrs = docAtrrMapper.selectList(Wrappers.lambdaQuery(DocAttr.class).eq(DocAttr::getDocId,
        doc.getDocId()).eq(DocAttr::getVersion, 
        doc.getVersion()));

    List<DocData> data = docDataMapper.selectList(Wrappers.lambdaQuery(DocData.class)
        .eq(DocData::getDocId, doc.getDocId()).eq(DocData::getVersion, doc.getVersion()).isNotNull(DocData::getName));

    Map<String, List<DocData>> plans = new HashMap<>();
    List<DocData> removeDatas = new ArrayList<>();
    
    List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class)
        .eq(DocOptions::getDocId, doc.getDocId()).eq(DocOptions::getDocVersion, doc.getVersion()));
    
    for (int i=0; i<data.size(); i++) {
      DocData d = data.get(i);

      // 无数据源从固定选项中读取
      if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
        List<DocOptions> opt = options.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
            .collect(Collectors.toList());
        DocAttr attr = null;
        Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId())).findFirst();
        if (!attrOptional.isEmpty()) {
          attr = attrOptional.get();
          attr.setOptions(opt);
        }

        d.setAttr(attr);
      } else if ("input".equals(d.getType()) || "timeinput".equals(d.getType()) || "score".equals(d.getType())
          || "totalscore".equals(d.getType())) {
        DocAttr attr = null;
        Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId())).findFirst();
        if (!attrOptional.isEmpty()) {
          attr = attrOptional.get();
        }
        d.setAttr(attr);
      }
      String planId = d.getPlan();
      if (!StringUtils.isEmpty(planId)) {
        removeDatas.add(d);
        // DocData pData = data.remove(i);
        if(plans.containsKey(planId)) {
          plans.get(planId).add(d);
        } else {
          // System.out.println(planId);
          List<DocData> val = new ArrayList<DocData>();
          val.add(d);
          plans.put(planId, val);
        }
      }
    }
    for(DocData d : removeDatas) {
      data.remove(d);
    }
    doc.setData(data);
    doc.setPlans(plans);
    return doc;
  }

  @Override
  public int addDataSource(DataSource source) {
    
    return dataMapper.insert(source);
  }

  @Override
  public int deleteDataSourceById(Integer id) {
    return dataMapper.deleteById(id);
  }

  @Override
  public int deleteDataSourceByIds(List<Integer> ids) {
    return dataMapper.deleteBatchIds(ids);
  }

  @Override
  public int updateDataSource(DataSource source) {
    return dataMapper.updateById(source);
  }

  @Transactional
  @Override
  public int bulkUpdateDataSource(List<DataSource> source) {
    try {
      Integer count = 0;
      for (DataSource item : source) {
        count += dataMapper.updateById(item);
      }
      return count;
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      throw e;
    }
  }

  @Override
  public IPage<DataSource> getDataSourceByPage(IPage<DataSource> page, String keywords) {
    return dataMapper.selectPage(page, new LambdaQueryWrapper<DataSource>().or().like(DataSource::getName, keywords)
        .or().like(DataSource::getExtra, keywords).orderByAsc(DataSource::getCreateTime));
  }

  @Override
  public IPage<Doc> getDocByPage(IPage<Doc> page, String keywords) {
    return docMapper.selectPage(page,
        new LambdaQueryWrapper<Doc>().or().like(Doc::getName, keywords).or().like(Doc::getDocId, keywords)
            .orderByAsc(Doc::getCreateTime).select(Doc::getDocId, Doc::getName, Doc::getId, Doc::getVersion,
                Doc::getType, Doc::getCreateTime, Doc::getStructure));
  }

  @Override
  public IPage<DocInfo> getDocInfoByPage(IPage<DocInfo> p, String keywords) {
    return docInfoMapper.getDocInfoByPage(p, keywords);
  }

  @Transactional
  @Override
  public PatientDocInfo getPatientDocById(PatientDocInfo doc) {
    try {
      LambdaQueryWrapper<PatientDoc> query = new LambdaQueryWrapper<>();
      query.eq(PatientDoc::getPatientId, doc.getPatientId()); // 患者ID
      query.eq(PatientDoc::getDocId, doc.getDocId()); // 文档ID
      query.eq(PatientDoc::getDocVersion, doc.getDocVersion()); // 注意：同一个病人允使用不同版本的模版编辑多个版本的数据
      

      // 判断判断是否已写过此文档
      if (StringUtils.isEmpty(doc.getPatientDocId())) {

        Doc d = docMapper.selectOne(
            Wrappers.lambdaQuery(Doc.class).eq(Doc::getDocId, doc.getDocId()).eq(Doc::getVersion, doc.getDocVersion())
                .select(Doc::getDocId, Doc::getName, Doc::getId, Doc::getVersion, Doc::getType));
        if (d == null) {
          throw new RuntimeException("文档模版不存在");
        }
        Integer count = patientDocMapper.selectCount(query);

        String patientDocId = StringUtils.getUid();

        // 从模版创建文档
        if (count == null || count == 0) {
          // 注意：1、模版数据过大要使用表复制
          //      2、第一次固定数据放在前端组装

          PatientDocList item = new PatientDocList();
          item.setDocId(doc.getDocId());
          item.setDocVersion(doc.getDocVersion());
          item.setPatientDocId(patientDocId);
          item.setPatientDocVersion((byte) 1);
          item.setCreateUserId("001");
          item.setCreateUserName("叫黄兄");

          count = patientDocListMapper.addPatientDocList(item);

          if (count == 0) {
            throw new RuntimeException("插入[PatientDocList]数据失败");
          }

          PatientDoc pDoc = new PatientDoc();

          pDoc.setPatientId(doc.getPatientId());

          pDoc.setCreateUserId("001");
          pDoc.setCreateUserName("叫黄兄");

          pDoc.setDocId(doc.getDocId());
          pDoc.setDocVersion(doc.getDocVersion());

          pDoc.setPatientDocId(patientDocId);
          pDoc.setLastPatientDocVersion((byte) 1);

          pDoc.setDocType(d.getType());
          pDoc.setDocName(d.getName());

          count = patientDocMapper.insert(pDoc);

          if (count == 0) {
            throw new RuntimeException("插入[PatientDocList]数据失败");
          }

          // 拷贝初始数据
          count = patientDataMapper.copyDocDataToPatientData(doc.getDocId(), doc.getDocVersion(), patientDocId,
              (byte) 1);

          doc.setPatientDocId(patientDocId);
          doc.setPatientDocVersion((byte) 1);
        }
      }
      // 注意：同一个病人允使用不同版本的模版编辑多个版本的数据
      // 只提供患者ID、模版ID、模版版本号码，获取最新版本
      PatientDoc pDoc = patientDocMapper.selectOne(Wrappers.lambdaQuery(PatientDoc.class).eq(PatientDoc::getPatientDocId, doc.getPatientDocId()));
      doc.setPatientDocId(pDoc.getPatientDocId());
      doc.setPatientDocVersion(pDoc.getLastPatientDocVersion());

      LambdaQueryWrapper<PatientData> queryData = new LambdaQueryWrapper<>();
      queryData.eq(PatientData::getPatientDocId, doc.getPatientDocId());
      queryData.eq(PatientData::getPatientDocVersion, doc.getPatientDocVersion());

      List<PatientData> data = patientDataMapper.selectList(queryData);
      List<DocAttr> attrs = docAtrrMapper.selectList(
          Wrappers.lambdaQuery(DocAttr.class).eq(DocAttr::getDocId, doc.getDocId()).eq(DocAttr::getVersion,
              doc.getDocVersion()));
         
      PatientDocInfo docInfo = patientDocListMapper.getPatientDocInfoById(doc);
      List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class)
          .eq(DocOptions::getDocId, docInfo.getDocId()).eq(DocOptions::getDocVersion, docInfo.getDocVersion()));

          Map<String, List<PatientData>> plans = new HashMap<>();
          List<PatientData> removeDatas = new ArrayList<>();
      
      for (PatientData d : data) {
        
        if(!StringUtils.isEmpty(d.getSourceId())) {
          Optional<DocAttr> attr = attrs.stream().filter(e -> e.getNodeId().equals(d.getNodeId())).findFirst();
          if (!attr.isEmpty()) {
            d.setAttr(attr.get());
          } else {
            d.setAttr(new DocAttr());
          }
          DataSource source= (DataSource)redis.get(d.getSourceId());
          if (source != null && "SQL".equals(source.getType())) {
            String sql = source.getContent().replaceAll("#\\{[^\\}]+\\}", doc.getPatientId());

            List<Map<String, Object>> result = dataMapper.getSqlDataSource(sql);

            if ("input".equals(d.getType())) {
              String value = "";
              Object o = result.get(0).values().toArray()[0];
              value = o.toString();
              d.setValue(value);
            } else if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
              List<DocOptions> options1 = new ArrayList<>();
              for (Map<String, Object> r : result) {
                DocOptions op = new DocOptions();
                op.setLabel(r.get("label").toString());
                op.setValue(r.get("value").toString());
                op.setNodeId(d.getNodeId());
                op.setDocVersion(docInfo.getDocVersion());
                op.setDocId(docInfo.getDocId());

                options1.add(op);
              }
              d.getAttr().setOptions(options1);
            }
          }
        } else {
          // 无数据源从固定选项中读取
          if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
            List<DocOptions> opt = options.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
                .collect(Collectors.toList());
            DocAttr attr = null;
            Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
                .findFirst();
            if (!attrOptional.isEmpty()) {
              attr = attrOptional.get();
              attr.setOptions(opt);
            }

            d.setAttr(attr);
          } else if ("input".equals(d.getType()) || "timeinput".equals(d.getType()) || "score".equals(d.getType())
              || "totalscore".equals(d.getType())) {
            DocAttr attr = null;
            Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
                .findFirst();
            if (!attrOptional.isEmpty()) {
              attr = attrOptional.get();
            }
            d.setAttr(attr);
          }
        }

        String planId = d.getPlan();
        if (!StringUtils.isEmpty(planId)) {
          removeDatas.add(d);
          // DocData pData = data.remove(i);
          if (plans.containsKey(planId)) {
            plans.get(planId).add(d);
          } else {
            // System.out.println(planId);
            List<PatientData> val = new ArrayList<PatientData>();
            val.add(d);
            plans.put(planId, val);
          }
        }
      }

      for (PatientData d : removeDatas) {
        data.remove(d);
      }
      docInfo.setPlans(plans);
      docInfo.setData(data);

      return docInfo;
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      throw e;
    }
  }

  @Transactional
  @Override
  public int addPatientDoc(PatientDocInfo doc) {
    try {
        // 判断判断是否已写过此文档
      LambdaQueryWrapper<PatientDoc> query = new LambdaQueryWrapper<>();

      query.eq(PatientDoc::getPatientDocId, doc.getPatientDocId()); // 患者ID

      PatientDoc pDoc = patientDocMapper.selectOne(query);
      Byte nextVersion = (byte)(pDoc.getLastPatientDocVersion() + 1);
      pDoc.setLastUpdateUserId("001");
      pDoc.setLastUpateUserName("叫黄兄");
      pDoc.setLastPatientDocVersion(nextVersion);
      pDoc.setLastUpdateTime(new Date());

      int count = patientDocMapper.updateById(pDoc);

      if (count == 0) {
        throw new RuntimeException("更新[PatientDoc]数据失败");
      }

      PatientDocList newDoc = new PatientDocList();
      newDoc.setCreateTime(new Date());
      newDoc.setCreateUserId("001");
      newDoc.setCreateUserName("叫黄兄");

      newDoc.setHtml(doc.getHtml());
      newDoc.setStructure(doc.getStructure());

      newDoc.setDocVersion(doc.getDocVersion());

      newDoc.setPatientDocId(pDoc.getPatientDocId());
      newDoc.setPatientDocVersion(nextVersion);

      count = patientDocListMapper.insert(newDoc);
      if (count == 0) {
        throw new RuntimeException("插入[PatientDocList]数据失败");
      }

      List<PatientData> data = doc.getData();
      for (PatientData pd : data) {
        pd.setPatientDocId(doc.getPatientDocId());
        pd.setPatientDocVersion(newDoc.getPatientDocVersion());

        // count = patientDataMapper.insert(pd);
        // if (count == 0) {
        //   throw new RuntimeException("插入[PatientData]数据失败");
        // }
      }
      patientDataMapper.insertBatach(data);

      return 1;
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

      throw e;
    }
  }

  @Override
  public IPage<PatientInfo> getPatientInfoByPage(IPage<PatientInfo> p, String keywords) {
    return patientInfoMapper.selectPage(p, new LambdaQueryWrapper<PatientInfo>().or().like(
        PatientInfo::getRealName, keywords)
        .or().like(PatientInfo::getDeptName, keywords).orderByAsc(PatientInfo::getPatientId));
  }

  @Override
  public int updatePatientInfo(PatientInfo info) {
    return patientInfoMapper.updateById(info);
  }

  @Override
  public int deletePatientInfoById(Integer id) {
    return patientInfoMapper.deleteById(id);
  }

  @Override
  public List<PatientDocInfo> getPatientDocListById(PatientDocInfo doc) {
    return patientDocMapper.getPatientDocListById(doc);
  }

  @Override
  public IPage<DataControl> getControlByPage(IPage<DataControl> p, String keywords) {
    IPage<DataControl> controls = dataControlMapper.selectPage(p,
        new LambdaQueryWrapper<DataControl>().or().like(
            DataControl::getName, keywords).or()
            .like(DataControl::getDeptName, keywords).orderByDesc(DataControl::getCreateTime));

    for (DataControl c : controls.getRecords()) {
      if ("select".equals(c.getTypeId()) && StringUtils.isEmpty(c.getSourceId())) {
        c.setOptions(dataControlOptionMapper.selectList(
            Wrappers.lambdaQuery(DataControlOption.class).eq(DataControlOption::getControlId, c.getControlId())));
      }
    }

    return controls;
  }

  @Override
  public int addDataControl(DataControl data) {

    List<Dept> depts = deptMapper.selectList(Wrappers.lambdaQuery(Dept.class)
        .in(Dept::getDeptId, data.getDeptId().split(",")));

    StringBuilder sb = new StringBuilder();
    int index = 0;
    for (Dept d : depts) {
      if (index > 0) {
        sb.append(",");
      }
      sb.append(d.getDeptName());
      index += 1;
    }

    if(data.getOptions() != null) {
      for(DataControlOption option : data.getOptions()) {
      option.setControlId(data.getControlId());
        dataControlOptionMapper.insert(option);
      }
    }

    data.setDeptName(sb.toString());

    return dataControlMapper.insert(data);
  }

  @Override
  public IPage<Dept> getDeptByPage(IPage<Dept> p, String keywords) {
    return deptMapper.selectPage(p, new LambdaQueryWrapper<Dept>()
        .like(Dept::getDeptName, keywords));
  }

  @Override
  public List<DataControlType> getControlTypeList() {
    return dataControlTypeMapper.selectList(null);
  }

  @Override
  public List<DataControl> getUserControlList() {
    List<DataControl> controls = dataControlMapper.selectList(null);

    for(DataControl c : controls) {
      if ("select radio checkbox".indexOf(c.getTypeId()) > -1 && StringUtils.isEmpty(c.getSourceId())) {
        c.setOptions(dataControlOptionMapper.selectList(Wrappers.lambdaQuery(DataControlOption.class)
            .eq(DataControlOption::getControlId, c.getControlId())));
      } else if ("select radio checkbox".indexOf(c.getTypeId()) > -1) {
        // 规定格式 sql 必须返回label\value两个字段

        DataSource data =  dataMapper.selectOne(Wrappers.lambdaQuery(DataSource.class)
            .eq(DataSource::getSourceId, c.getSourceId()));

        if (data.getType() == "SQL") {
          String sql = "";
          // 获取参数
          Pattern r = Pattern.compile("#\\{[^\\}]+\\}");
          Matcher m = r.matcher(data.getContent());
          if (m.matches()) {
            Set<String> params = new HashSet<>();
            for (int i = 0; i < m.groupCount(); i++) {
              params.add(m.group(i));
            }

            // 如果除了系统参数外还有
            if (params.size() > 1 || params.toArray()[0] != "#{NOW}") {
              continue;
            }

            sql = data.getContent().replaceAll("#{NOW}", "2020-01-01");

          } else {
            sql = data.getContent();
          }

          List<Map<String, Object>> options = dataMapper.getSqlDataSource(sql);
          List<DataControlOption> ops = new ArrayList<>();
          for (Map<String, Object> item : options) {
            DataControlOption op = new DataControlOption();
            op.setLabel(item.get("label").toString());
            op.setValue(item.get("value").toString());
            ops.add(op);
          }

          c.setOptions(ops);
        }

        if (data.getType() == "JSON") {
          
        }
      }
    }
    return controls;
  }

  @Override
  public int updateDataControl(DataControl data) {
    
    data.setLastUpdateTime(new Date());
    data.setLastUpdateUserId("001");
    data.setLastUpdateUserName("叫黄兄");

    if (data.getOptions() != null && data.getOptions().size() > 0) {
      dataControlOptionMapper.delete(Wrappers.lambdaQuery(DataControlOption.class)
          .eq(DataControlOption::getControlId, data.getControlId()));
      for (DataControlOption option : data.getOptions()) {
        option.setControlId(data.getControlId());
        dataControlOptionMapper.insert(option);
      }
    }

    List<Dept> depts = deptMapper
        .selectList(Wrappers.lambdaQuery(Dept.class).in(Dept::getDeptId, data.getDeptId().split(",")));

    StringBuilder sb = new StringBuilder();
    int index = 0;
    for (Dept d : depts) {
      if (index > 0) {
        sb.append(",");
      }
      sb.append(d.getDeptName());
      index += 1;
    }

    data.setDeptName(sb.toString());

    dataControlMapper.updateById(data);

    return 1;
  }

  @Override
  public IPage<DataControl> getUserControlByPage(IPage<DataControl> p, String keywords) {
    return dataControlMapper.selectPage(p, new LambdaQueryWrapper<DataControl>().or().like(
        DataControl::getDeptName, keywords).like(DataControl::getName, keywords));
  }

  @Override
  public DataControl getUserControlById(String controlId) {
   return dataControlMapper.selectOne(new LambdaQueryWrapper<DataControl>().eq(
       DataControl::getControlId, controlId));
  }

  @Override
  public int deleteUserControlById(String id) {
    return dataControlMapper.delete(new LambdaQueryWrapper<DataControl>().eq(
       DataControl::getControlId, id));
  }

  @Override
  public int updateUserControl(DataControl control) {
    return dataControlMapper.update(control, new LambdaQueryWrapper<DataControl>().eq(DataControl::getControlId, control.getControlId()));
  }

  @Override
  public PatientDocInfo getPatientDocInfoById(String patientDocId) {
    PatientDocInfo doc =new PatientDocInfo();
    doc.setPatientDocId(patientDocId);

    PatientDocInfo docInfo = patientDocListMapper.getPatientDocInfoById(doc);

    LambdaQueryWrapper<PatientData> queryData = new LambdaQueryWrapper<>();
    queryData.eq(PatientData::getPatientDocId, docInfo.getPatientDocId());
    queryData.eq(PatientData::getPatientDocVersion, docInfo.getPatientDocVersion());

    List<PatientData> data = patientDataMapper.selectList(queryData);
    List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class).eq(
          DocOptions::getDocId, docInfo.getDocId()).eq(DocOptions::getDocVersion, docInfo.getDocVersion()));

      List<DocAttr> attrs = docAtrrMapper.selectList(Wrappers.lambdaQuery(DocAttr.class).eq(
          DocAttr::getDocId, docInfo.getDocId()).eq(DocAttr::getVersion, docInfo.getDocVersion()));

          Map<String, List<PatientData>> plans = new HashMap<>();
          List<PatientData> removeDatas = new ArrayList<>();

    for (PatientData d : data) {
      if (!StringUtils.isEmpty(d.getSourceId())) {
        DataSource source = (DataSource) redis.get(d.getSourceId());
        if (source != null && "SQL".equals(source.getType())) {
          if ("input".equals(d.getType())) {
            String sql = source.getContent().replaceAll("#\\{[^\\}]+\\}", docInfo.getPatientId());

            try{
              List<Map<String, Object>> result = dataMapper.getSqlDataSource(sql);
              String value = "";
              Object o = result.get(0).values().toArray()[0];
              value = o.toString();
              d.setValue(value);
            } catch (Exception e) {
              throw new RuntimeException("数据源sql存在格式不正确:"+sql);
            }
          } else if ("select".equals(d.getType())) {

          }
        } 
      } else {
        // 无数据源从固定选项中读取
        if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
          List<DocOptions> opt = options.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .collect(Collectors.toList());
          DocAttr attr = null;
          Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .findFirst();
          if (!attrOptional.isEmpty()) {
            attr = attrOptional.get();
            attr.setOptions(opt);
          }

          d.setAttr(attr);
        } else if ("input".equals(d.getType()) || "timeinput".equals(d.getType()) || "score".equals(d.getType())
            || "totalscore".equals(d.getType())) {
          DocAttr attr = null;
          Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .findFirst();
          attr = attrOptional.get();
          d.setAttr(attr);
        }
      }
    
      String planId = d.getPlan();
      if (!StringUtils.isEmpty(planId)) {
        removeDatas.add(d);
        // DocData pData = data.remove(i);
        if (plans.containsKey(planId)) {
          plans.get(planId).add(d);
        } else {
          // System.out.println(planId);
          List<PatientData> val = new ArrayList<PatientData>();
          val.add(d);
          plans.put(planId, val);
        }
      }
    }
    for (PatientData d : removeDatas) {
      data.remove(d);
    }
    docInfo.setPlans(plans);
    docInfo.setData(data);

    return docInfo;
  }

  @Override
  public List<Dept> getDeptList() {
    return deptMapper.selectList(null);
  }

  @Override
  public List<DocType> getDocTypeList(String patientId) {

    List<DocType> types = docTypeMapper.selectList(Wrappers.lambdaQuery(DocType.class).orderByAsc(DocType::getId));
    
    if (types != null && types.size() > 0) {
      List<DocType> results = new ArrayList<>();
      for (DocType t : types) {

        if (!StringUtils.isEmpty(patientId)) {
          // 获取患者文档
          TypeDoc doc = typeDocMapper.selectOne(Wrappers.lambdaQuery(TypeDoc.class).eq(TypeDoc::getType, t.getTypeId()));

          if(doc != null) {
            List<DataSource> sources = dataMapper.getSqlDataSourceByDocId(doc);
          
            if(sources != null) {
              StringBuilder sb = new StringBuilder();
              for(DataSource s : sources) {
                sb.append(s.getContent()+";");
              }
              // System.out.println(sb);
              Pattern r = Pattern.compile("(#\\{[^\\}]+\\})");
              Matcher m = r.matcher(sb.toString());
              if (m.find()) {
                // 去重复
                Set<String> params = new HashSet<>();
                for (int i = 0; i < m.groupCount(); i++) {
                  params.add(m.group(i));
                }
                
                Map<String,String> map = new HashMap<String,String>();
                for(String s : params) {
                  map.put(s.replaceAll("[#{}]", ""),null);
                }

                doc.setParams(map);
              }
            }
            t.setRelationDoc(doc);
          }

          List<TypePatientDoc> docs = typePatientDocMapper.selectList(Wrappers.lambdaQuery(TypePatientDoc.class).eq(
              TypePatientDoc::getDocType, t.getTypeId()).eq(
              TypePatientDoc::getPatientId, patientId));

          t.setPatientDocs(docs);
        }

        if (t.getParentTypeId() == null) {
          results.add(t);
        } else {
          this.addChildrenType(results, t);
        }
      }
      return results;
    }

    

    return null;
  }

  private void addChildrenType(List<DocType> results, DocType t) {
    if(results == null || results.size() == 0) return;

    for (DocType type : results) {
      if (type.getTypeId().equals(t.getTypeId())) {
        if (type.getChildren() == null) {
          type.setChildren(new ArrayList<>());
        }
        type.getChildren().add(t);
      }
    }
  }

  @Override
  public PatientInfo getPatientInfoById(String patientId) {
    return patientInfoMapper.selectOne(new LambdaQueryWrapper<PatientInfo>().eq(PatientInfo::getPatientId,patientId));
  }

  @Override
  public int checkDocById(Doc doc) {

    LambdaQueryWrapper<Doc> query = new LambdaQueryWrapper<>();
    query.eq(Doc::getDocId, doc.getDocId()).eq(Doc::getVersion, doc.getVersion()).select(Doc::getDocId, Doc::getName,
        Doc::getId, Doc::getVersion, Doc::getType);

    Doc d = docMapper.selectOne(query);
    
    if(d!=null) {

      DocOfficial officialDoc = docofficialMapper.selectOne(Wrappers.lambdaQuery(DocOfficial.class).eq(DocOfficial::getType, d.getType()));

      if(officialDoc == null) {
        officialDoc = new DocOfficial();
        officialDoc.setCheckUserId("001");
        officialDoc.setCheckUserName("叫黄兄");
        
        officialDoc.setDocId(d.getDocId());
        officialDoc.setDocName(d.getName());
        officialDoc.setType(d.getType());
        officialDoc.setTypeName(d.getTypeName());
        officialDoc.setVersion(d.getVersion());
        officialDoc.setStatus((byte)1);

        docofficialMapper.insert(officialDoc);
      } else {
        officialDoc.setCheckUserId("001");
        officialDoc.setCheckUserName("叫黄兄");
        
        officialDoc.setDocId(d.getDocId());
        officialDoc.setDocName(d.getName());
        officialDoc.setType(d.getType());
        officialDoc.setTypeName(d.getTypeName());
        officialDoc.setVersion(d.getVersion());
        officialDoc.setStatus((byte)1);

        docofficialMapper.updateById(officialDoc);
      }

      return 1;
    }
    return 0;
  }

  @Override
  public Object getDataSourceById(String sourceId) {
    
    return null;
  }

  @Override
  public Doc getDocInfoById4M(String docId) {
    QueryWrapper<Doc> query = new QueryWrapper<>();
    query.eq("doc_id", docId).select("doc_id", "max(version) version");
    Doc doc = docMapper.selectOne(query);
    if (doc == null) {
      return null;
    }

    doc = docMapper.selectOne(Wrappers.lambdaQuery(Doc.class).eq(Doc::getDocId,
        doc.getDocId()).eq(Doc::getVersion, doc.getVersion()).select(Doc::getDocId, Doc::getName,
        Doc::getId, Doc::getVersion, Doc::getType, Doc::getTypeName, Doc::getCreateTime,Doc::getLayout));

    List<DocAttr> attrs = docAtrrMapper.selectList(Wrappers.lambdaQuery(DocAttr.class).eq(DocAttr::getDocId,
        doc.getDocId()).eq(DocAttr::getVersion, 
        doc.getVersion()));

    List<DocData> data = docDataMapper.selectList(Wrappers.lambdaQuery(DocData.class)
        .eq(DocData::getDocId, doc.getDocId()).eq(DocData::getVersion, doc.getVersion()).isNotNull(DocData::getName));

    Map<String, List<DocData>> plans = new HashMap<>();
    List<DocData> removeDatas = new ArrayList<>();
    
    List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class)
        .eq(DocOptions::getDocId, doc.getDocId()).eq(DocOptions::getDocVersion, doc.getVersion()));
    
    for (int i=0; i<data.size(); i++) {
      DocData d = data.get(i);

      // 无数据源从固定选项中读取
      if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
        List<DocOptions> opt = options.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
            .collect(Collectors.toList());
        DocAttr attr = null;
        Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId())).findFirst();
        if (!attrOptional.isEmpty()) {
          attr = attrOptional.get();
          attr.setOptions(opt);
        }

        d.setAttr(attr);
      } else if ("input".equals(d.getType()) || "timeinput".equals(d.getType()) || "score".equals(d.getType())
          || "totalscore".equals(d.getType())) {
        DocAttr attr = null;
        Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId())).findFirst();
        if (!attrOptional.isEmpty()) {
          attr = attrOptional.get();
        }
        d.setAttr(attr);
      }
      String planId = d.getPlan();
      if (!StringUtils.isEmpty(planId)) {
        removeDatas.add(d);
        // DocData pData = data.remove(i);
        if(plans.containsKey(planId)) {
          plans.get(planId).add(d);
        } else {
          // System.out.println(planId);
          List<DocData> val = new ArrayList<DocData>();
          val.add(d);
          plans.put(planId, val);
        }
      }
    }
    for(DocData d : removeDatas) {
      data.remove(d);
    }
    doc.setData(data);
    doc.setPlans(plans);
    return doc;
  }

  @Override
  public PatientDocInfo getPatientDocInfoById4M(String patientDocId) {
    PatientDocInfo doc =new PatientDocInfo();
    doc.setPatientDocId(patientDocId);

    PatientDocInfo docInfo = patientDocListMapper.getPatientDocInfoById4M(doc);

    LambdaQueryWrapper<PatientData> queryData = new LambdaQueryWrapper<>();
    queryData.eq(PatientData::getPatientDocId, docInfo.getPatientDocId());
    queryData.eq(PatientData::getPatientDocVersion, docInfo.getPatientDocVersion());

    List<PatientData> data = patientDataMapper.selectList(queryData);
    List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class).eq(
          DocOptions::getDocId, docInfo.getDocId()).eq(DocOptions::getDocVersion, docInfo.getDocVersion()));

      List<DocAttr> attrs = docAtrrMapper.selectList(Wrappers.lambdaQuery(DocAttr.class).eq(
          DocAttr::getDocId, docInfo.getDocId()).eq(DocAttr::getVersion, docInfo.getDocVersion()));

          Map<String, List<PatientData>> plans = new HashMap<>();
          List<PatientData> removeDatas = new ArrayList<>();

    for (PatientData d : data) {
      if (!StringUtils.isEmpty(d.getSourceId())) {
        DataSource source = (DataSource) redis.get(d.getSourceId());
        if (source != null && "SQL".equals(source.getType())) {
          if ("input".equals(d.getType())) {
            String sql = source.getContent().replaceAll("#\\{[^\\}]+\\}", docInfo.getPatientId());

            try{
              List<Map<String, Object>> result = dataMapper.getSqlDataSource(sql);
              String value = "";
              Object o = result.get(0).values().toArray()[0];
              value = o.toString();
              d.setValue(value);
            } catch (Exception e) {
              throw new RuntimeException("数据源sql存在格式不正确:"+sql);
            }
          } else if ("select".equals(d.getType())) {

          }
        } 
      } else {
        // 无数据源从固定选项中读取
        if ("select".equals(d.getType()) || "radio".equals(d.getType()) || "checkbox".equals(d.getType())) {
          List<DocOptions> opt = options.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .collect(Collectors.toList());
          DocAttr attr = null;
          Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .findFirst();
          if (!attrOptional.isEmpty()) {
            attr = attrOptional.get();
            attr.setOptions(opt);
          }

          d.setAttr(attr);
        } else if ("input".equals(d.getType()) || "timeinput".equals(d.getType()) || "score".equals(d.getType())
            || "totalscore".equals(d.getType())) {
          DocAttr attr = null;
          Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> d.getNodeId().equals(e.getNodeId()))
              .findFirst();
          attr = attrOptional.get();
          d.setAttr(attr);
        }
      }
    
      String planId = d.getPlan();
      if (!StringUtils.isEmpty(planId)) {
        removeDatas.add(d);
        // DocData pData = data.remove(i);
        if (plans.containsKey(planId)) {
          plans.get(planId).add(d);
        } else {
          // System.out.println(planId);
          List<PatientData> val = new ArrayList<PatientData>();
          val.add(d);
          plans.put(planId, val);
        }
      }
    }
    for (PatientData d : removeDatas) {
      data.remove(d);
    }
    docInfo.setPlans(plans);
    docInfo.setData(data);

    return docInfo;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int addPatientDoc4M(PatientDocInfo doc) {
    try {
        // 判断判断是否已写过此文档
      LambdaQueryWrapper<PatientDoc> query = new LambdaQueryWrapper<>();

      query.eq(PatientDoc::getPatientDocId, doc.getPatientDocId()); // 患者ID

      PatientDoc pDoc = patientDocMapper.selectOne(query);
      int count = 0;
      byte nextVersion = 1;
      if(pDoc == null) {
        pDoc = new PatientDoc();
      
        BeanUtils.copyProperties(doc, pDoc);
        pDoc.setLastPatientDocVersion(nextVersion);
        pDoc.setLastUpdateTime(new Date());
        pDoc.setLastUpdateUserId("001");
        pDoc.setLastUpateUserName("黄兄");
        count = patientDocMapper.insert(pDoc);
         
        if (count == 0) {
          throw new RuntimeException("更新[PatientDoc]数据失败");
        }
      } else {
        nextVersion = (byte)(pDoc.getLastPatientDocVersion() + 1);
        pDoc.setLastUpdateUserId("001");
        pDoc.setLastUpateUserName("叫黄兄");
        pDoc.setLastPatientDocVersion(nextVersion);
        pDoc.setLastUpdateTime(new Date());
  
         count = patientDocMapper.updateById(pDoc);
  
        if (count == 0) {
          throw new RuntimeException("更新[PatientDoc]数据失败");
        }
      }
     

      PatientDocList newDoc = new PatientDocList();
      newDoc.setCreateTime(new Date());
      newDoc.setCreateUserId("001");
      newDoc.setCreateUserName("叫黄兄");

      if(StringUtils.isEmpty(doc.getHtml())) {
       Doc sDoc = docMapper.selectOne(Wrappers.lambdaQuery(Doc.class).eq(Doc::getDocId, pDoc.getDocId()).eq(Doc::getVersion, pDoc.getDocVersion()));
        if(sDoc != null) {
          doc.setHtml(sDoc.getHtml());
        } 
      }

      newDoc.setHtml(doc.getHtml());
      newDoc.setStructure(doc.getStructure());

      newDoc.setDocVersion(doc.getDocVersion());

      newDoc.setPatientDocId(pDoc.getPatientDocId());
      newDoc.setPatientDocVersion(nextVersion);

      count = patientDocListMapper.insert(newDoc);
      if (count == 0) {
        throw new RuntimeException("插入[PatientDocList]数据失败");
      }

      List<PatientData> data = doc.getData();
      if(data == null || data.size() == 0) {
        data = new ArrayList<PatientData>();
        Map<String,List<PatientData>> plans = doc.getPlans();
        if(plans != null && plans.size() > 0) {
          for(List<PatientData> d : plans.values()) {
            data.addAll(d);
          }
        }
      }
      for (PatientData pd : data) {
        pd.setPatientDocId(doc.getPatientDocId());
        pd.setPatientDocVersion(newDoc.getPatientDocVersion());

        // count = patientDataMapper.insert(pd);
        // if (count == 0) {
        //   throw new RuntimeException("插入[PatientData]数据失败");
        // }
      }
      patientDataMapper.insertBatach(data);

      return 1;
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

      throw e;
    }
  }

  @Override
  public List<Doc> getMaxDocList() {
    return docMapper.getMaxDocList();
  }

  @Override
  public IPage<PatientInfo> getPatientListByDept(IPage<PatientInfo> page, String deptId) {
    if(StringUtils.isEmpty(deptId)) {
      return patientInfoMapper.selectPage(page,null);
    }

    return patientInfoMapper.selectPage(page, Wrappers.lambdaQuery(PatientInfo.class).eq(PatientInfo::getDeptId,deptId));
  }

  @Override
  public PatientDocInfo newPatientDoc(TypeDoc doc) {
    try {

      // 固定参数
      String patientId = doc.getParams().get("patientId");

      Doc d = docMapper.selectOne(
          Wrappers.lambdaQuery(Doc.class).eq(Doc::getDocId, doc.getDocId()).eq(Doc::getVersion, doc.getVersion())
              .select(Doc::getDocId, Doc::getName, Doc::getId, Doc::getVersion, Doc::getType));
      if (d == null) {
        throw new RuntimeException("文档模版不存在");
      }

      String patientDocId = StringUtils.getUid();

      
      // 注意：同一个病人允使用不同版本的模版编辑多个版本的数据
      // 只提供患者ID、模版ID、模版版本号码，获取最新版本

      List<DocData> data = docDataMapper.selectList(Wrappers.lambdaQuery(DocData.class).eq(DocData::getDocId, doc.getDocId()).eq(DocData::getVersion, doc.getVersion()).isNotNull(DocData::getName));
      List<DocAttr> attrs = docAtrrMapper.selectList(
          Wrappers.lambdaQuery(DocAttr.class).eq(DocAttr::getDocId, doc.getDocId()).eq(DocAttr::getVersion,
              doc.getVersion()));
         
      PatientDocInfo docInfo = new PatientDocInfo();
      docInfo.setDocId(doc.getDocId());
      docInfo.setDocName(doc.getDocName());
      docInfo.setDocType(doc.getType());
      docInfo.setDocVersion(doc.getVersion());
      docInfo.setPatientDocId(patientId);
      docInfo.setPatientDocVersion((byte)1);
      docInfo.setPatientDocId(patientDocId);
      docInfo.setPatientId(patientId);
      docInfo.setParams(doc.getParams());
      
      List<DocOptions> options = docOptionsMapper.selectList(Wrappers.lambdaQuery(DocOptions.class)
          .eq(DocOptions::getDocId, docInfo.getDocId()).eq(DocOptions::getDocVersion, docInfo.getDocVersion()));

      Map<String, List<PatientData>> plans = new HashMap<>();
      List<PatientData> oData = new ArrayList<>();
      
      for (DocData item : data) {

        PatientData pd = new PatientData();
        BeanUtils.copyProperties(item, pd);
        
        if(!StringUtils.isEmpty(pd.getSourceId())) {
          Optional<DocAttr> attr = attrs.stream().filter(e -> e.getNodeId().equals(pd.getNodeId())).findFirst();
          if (!attr.isEmpty()) {
            pd.setAttr(attr.get());
          } else {
            pd.setAttr(new DocAttr());
          }
          DataSource source= (DataSource)redis.get(pd.getSourceId());
          if (source != null && "SQL".equals(source.getType())) {
            String sql = source.getContent().replaceAll("#\\{[^\\}]+\\}", patientId);

            List<Map<String, Object>> result = dataMapper.getSqlDataSource(sql);

            if ("input".equals(pd.getType())) {
              String value = "";
              Object o = result.get(0).values().toArray()[0];
              value = o.toString();
              pd.setValue(value);
            } else if ("select".equals(pd.getType()) || "radio".equals(pd.getType()) || "checkbox".equals(pd.getType())) {
              List<DocOptions> options1 = new ArrayList<>();
              for (Map<String, Object> r : result) {
                DocOptions op = new DocOptions();
                op.setLabel(r.get("label").toString());
                op.setValue(r.get("value").toString());
                op.setNodeId(pd.getNodeId());
                op.setDocVersion(docInfo.getDocVersion());
                op.setDocId(docInfo.getDocId());

                options1.add(op);
              }
              pd.getAttr().setOptions(options1);
            }
          }
        } else {
          // 无数据源从固定选项中读取
          if ("select".equals(pd.getType()) || "radio".equals(pd.getType()) || "checkbox".equals(d.getType())) {
            List<DocOptions> opt = options.stream().filter((e) -> pd.getNodeId().equals(e.getNodeId()))
                .collect(Collectors.toList());
            DocAttr attr = null;
            Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> pd.getNodeId().equals(e.getNodeId()))
                .findFirst();
            if (!attrOptional.isEmpty()) {
              attr = attrOptional.get();
              attr.setOptions(opt);
            }

            pd.setAttr(attr);
          } else if ("input".equals(d.getType()) || "timeinput".equals(pd.getType()) || "score".equals(pd.getType())
              || "totalscore".equals(d.getType())) {
            DocAttr attr = null;
            Optional<DocAttr> attrOptional = attrs.stream().filter((e) -> pd.getNodeId().equals(e.getNodeId()))
                .findFirst();
            if (!attrOptional.isEmpty()) {
              attr = attrOptional.get();
            }
            pd.setAttr(attr);
          }
        }

        String planId = pd.getPlan();
        if (!StringUtils.isEmpty(planId)) {
          // DocData pData = data.remove(i);
          if (plans.containsKey(planId)) {
            plans.get(planId).add(pd);
          } else {
            // System.out.println(planId);
            List<PatientData> val = new ArrayList<PatientData>();
            val.add(pd);
            plans.put(planId, val);
          }
        } else {
          oData.add(pd);
        }
      }
      docInfo.setPlans(plans);
      docInfo.setData(oData);

      return docInfo;
    } catch (Exception e) {
      throw e;
    }
  }
}