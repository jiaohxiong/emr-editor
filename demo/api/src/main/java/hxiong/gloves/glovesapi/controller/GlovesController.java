package hxiong.gloves.glovesapi.controller;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import hxiong.gloves.glovesapi.AmisResults;
import hxiong.gloves.glovesapi.AmisOptionItem;
import hxiong.gloves.glovesapi.util.RedisUtil;
import hxiong.gloves.glovesapi.util.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hxiong.gloves.glovesapi.controller.vo.BulkUpateDataSourceVO;
import hxiong.gloves.glovesapi.controller.vo.NewPatientDocVO;
import hxiong.gloves.glovesapi.controller.vo.QueryDocVO;
import hxiong.gloves.glovesapi.entity.*;
import hxiong.gloves.glovesapi.service.DisruptorDataEventQueueHelper;
import hxiong.gloves.glovesapi.service.DisruptorDataType;
import hxiong.gloves.glovesapi.service.GlovesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Gloves API V1.0")
@RestController
@CrossOrigin
@RequestMapping("api")
public class GlovesController {
  
  @Autowired
  private GlovesService gloves;

  @Autowired
  private DisruptorDataEventQueueHelper disruptor;

  @Autowired
  private RedisUtil redis;

  @ApiOperation(value = "getAllDocList", notes = "get all docs")
  @GetMapping("/getAllDocList")
  public AmisResults getAllDocList() {
    try {

      return AmisResults.success(gloves.getAllDocList());

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getMaxDocList", notes = "get all docs")
  @GetMapping("/getMaxDocList")
  public AmisResults getMaxDocList() {
    try {

      return AmisResults.success(gloves.getMaxDocList());

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocByPage", notes = "get all data source")
  @GetMapping("/getDocByPage")
  public AmisResults getDocByPage(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<Doc> p = new Page<Doc>(page, perPage);
      IPage<Doc> data =  gloves.getDocByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocInfoByPage", notes = "get all data source")
  @GetMapping("/getDocInfoByPage")
  public AmisResults getDocInfoByPage(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<DocInfo> p = new Page<DocInfo>(page, perPage);
      IPage<DocInfo> data = gloves.getDocInfoByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getControlByPage", notes = "get all data source")
  @GetMapping("/getControlByPage")
  public AmisResults getControlByPage(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<DataControl> p = new Page<DataControl>(page, perPage);
      IPage<DataControl> data = gloves.getControlByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocById", notes = "get all docs")
  @PostMapping("/getDocById")
  public AmisResults getDocById(@RequestBody QueryDocVO vo) {
    try {
      Doc doc = new Doc();
      doc.setDocId(vo.getDocId());
      doc.setVersion(vo.getVersion());
      doc.setName(vo.getName());
      doc.setType(vo.getType());
      doc.setTypeName(vo.getTypeName());

      return AmisResults.success(gloves.getDocById(doc));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocInfoById", notes = "get all docs")
  @GetMapping("/getDocInfoById/{docId}")
  public AmisResults getDocInfoById(@PathVariable(value = "docId") String docId){
    try {

      return AmisResults.success(gloves.getDocInfoById(docId));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocInfoById", notes = "get all docs")
  @GetMapping("/getDocInfoById4M/{docId}")
  public AmisResults getDocInfoById4M(@PathVariable(value = "docId") String docId){
    try {

      return AmisResults.success(gloves.getDocInfoById4M(docId));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "checkDocById", notes = "get all docs")
  @PostMapping("/checkDocById")
  public AmisResults checkDocById(@RequestBody QueryDocVO vo) {
    try {
      Doc doc = new Doc();
      doc.setDocId(vo.getDocId());
      doc.setVersion(vo.getVersion());

      return AmisResults.updated(gloves.checkDocById(doc));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "newPatientDoc", notes = "get all docs")
  @PostMapping("/newPatientDoc")
  public AmisResults newPatientDoc(@RequestBody NewPatientDocVO vo) {
    try {
      TypeDoc doc = new TypeDoc();

      BeanUtils.copyProperties(vo, doc);

      return AmisResults.success(gloves.newPatientDoc(doc));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }
  
  @ApiOperation(value = "getPatientDocById", notes = "get all docs")
  @PostMapping("/getPatientDocById")
  public AmisResults getPatientDocById(@RequestBody QueryDocVO vo) {
    try {
      PatientDocInfo doc = new PatientDocInfo();

      doc.setDocId(vo.getDocId());
      doc.setDocVersion(vo.getVersion());

      doc.setPatientId(vo.getPatientId());
      doc.setPatientDocId(vo.getPatientDocId());
      // 查询当前要编辑的版本，如果这个参数为空就选择最新版本
      doc.setPatientDocVersion(vo.getPatientDocVersion());

      return AmisResults.success(gloves.getPatientDocById(doc));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getPatientDocInfoById", notes = "get all docs")
  @GetMapping("/getPatientDocInfoById/{patientDocId}")
  public AmisResults getPatientDocInfoById(@PathVariable String patientDocId) {
    try {

      return AmisResults.success(gloves.getPatientDocInfoById(patientDocId));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getPatientDocInfoById4M", notes = "get all docs")
  @GetMapping("/getPatientDocInfoById4M/{patientDocId}")
  public AmisResults getPatientDocInfoById4M(@PathVariable String patientDocId) {
    try {

      Boolean exists = redis.hHasKey(DisruptorDataType.PATIENTDOC.getKey(), patientDocId);

      PatientDocInfo docInfo = null;
      if(exists) {
        docInfo = (PatientDocInfo)redis.hget(DisruptorDataType.PATIENTDOC.getKey(), patientDocId);
      } else {
        docInfo = gloves.getPatientDocInfoById4M(patientDocId);
        redis.hset(DisruptorDataType.PATIENTDOC.getKey(), patientDocId, docInfo);
      }

      return AmisResults.success(docInfo);

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }
  
  @ApiOperation(value = "getPatientDocListById", notes = "get all docs")
  @GetMapping("/getPatientDocListById/{patientId}")
  public AmisResults getPatientDocListById(@PathVariable(value = "patientId") String patientId) {
    try {

      PatientDocInfo doc = new PatientDocInfo();
      doc.setPatientId(patientId);
      
      List<PatientDocInfo> data = gloves.getPatientDocListById(doc);
      return AmisResults.page(data, data.size());

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "addPatientDoc", notes = "get all docs")
  @PostMapping("/addPatientDoc")
  public AmisResults addPatientDoc(@RequestBody PatientDocInfo doc) {
    try {
      return AmisResults.success(gloves.addPatientDoc(doc));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "addPatientDoc4M", notes = "get all docs")
  @PostMapping("/addPatientDoc4M")
  public AmisResults addPatientDoc4M(@RequestBody PatientDocInfo doc) {
    try {

      DisruptorData data = new DisruptorData();
      data.setType(DisruptorDataType.PATIENTDOC);
      data.setData(doc);
      data.setDataId(doc.getPatientDocId());

      disruptor.publishEvent(data);

      // gloves.addPatientDoc4M(doc)
      return AmisResults.success(1);

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "addDataControl", notes = "get all docs")
  @PostMapping("/addDataControl")
  public AmisResults addDataControl(@RequestBody DataControl data) {
    try {
      data.setControlId(StringUtils.getUid());
      return AmisResults.success(gloves.addDataControl(data));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "updateDataControl", notes = "get all docs")
  @PostMapping("/updateDataControl/{id}")
  public AmisResults updateDataControl(@PathVariable(value = "id") Integer id, @RequestBody DataControl data) {
    try {
      data.setId(id);
      return AmisResults.success(gloves.updateDataControl(data));

    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }


  @ApiOperation(value = "addDoc", notes = "add a new doc & stored database")
  @PostMapping("/addDoc")
  public AmisResults addDoc(@RequestBody Doc doc) {
    try {
      // 如果前端不生doc id 则后台再次生成一次。
      if (doc.getDocId() == null) {
        doc.setDocId(StringUtils.getUid());
      }
      int count = gloves.addDoc(doc);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "updateDoc", notes = "update a new doc & stored database")
  @PostMapping("/updateDoc")
  public AmisResults updateDoc(@RequestBody Doc doc) {
    try {

      int count = gloves.updateDoc(doc);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "addDataSource", notes = "add data source to db")
  @PostMapping("/addDataSource")
  public AmisResults addDataSource(@RequestBody DataSource source) {
    try {

      int count = gloves.addDataSource(source);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "updateDataSource", notes = "add data source to db")
  @PostMapping("/updateDataSource/{id}")
  public AmisResults updateDataSource(@PathVariable(value = "id") Integer id, @RequestBody DataSource source) {
    try {

      source.setId(id);
      int count = gloves.updateDataSource(source);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "updatePatientInfo", notes = "add data source to db")
  @PostMapping("/updatePatientInfo/{id}")
  public AmisResults updatePatientInfo(@PathVariable(value = "id") Integer id, @RequestBody PatientInfo info) {
    try {

      info.setId(id);
      int count = gloves.updatePatientInfo(info);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "bulkUpdateDataSource", notes = "add data source to db")
  @PostMapping("/bulkUpdateDataSource/{id}")
  public AmisResults bulkUpdateDataSource(@PathVariable(value = "id") List<Integer> ids, @RequestBody BulkUpateDataSourceVO vo) {
    try {

      List<DataSource> dataSources = new ArrayList<>();
      for (Integer id : ids) {
        DataSource d = new DataSource();
        d.setId(id);
        d.setType(vo.getType());
        dataSources.add(d);
      }
      int count = gloves.bulkUpdateDataSource(dataSources);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "deleteDataSourceById", notes = "add data source to db")
  @DeleteMapping("/deleteDataSourceById/{id}")
  public AmisResults deleteDataSourceById(@PathVariable(value = "id") Integer id) {
    try {

      int count = gloves.deleteDataSourceById(id);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "deletePatientInfoById", notes = "add data source to db")
  @DeleteMapping("/deletePatientInfoById/{id}")
  public AmisResults deletePatientInfoById(@PathVariable(value = "id") Integer id) {
    try {

      int count = gloves.deletePatientInfoById(id);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "deleteDataSourceByIds", notes = "add data source to db")
  @DeleteMapping("/deleteDataSourceByIds/{id}")
  public AmisResults deleteDataSourceByIds(@PathVariable(value = "id") List<Integer> ids) {
    try {

      int count = gloves.deleteDataSourceByIds(ids);
      return AmisResults.updated(count);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  // data source

  @ApiOperation(value = "getAllDataSource", notes = "get all data source")
  @GetMapping("/getAllDataSource")
  public AmisResults getAllDataSource() {
    try {
      return AmisResults.success(gloves.getAllDataSource());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getUserControlList", notes = "get all data source")
  @GetMapping("/getUserControlList")
  public AmisResults getUserControlList() {
    try {
      return AmisResults.success(gloves.getUserControlList());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getUserControlById", notes = "get all data source")
  @GetMapping("/getUserControlById/{id}")
  public AmisResults getUserControlById(@PathVariable(value = "id") String id) {
    try {
      return AmisResults.success(gloves.getUserControlById(id));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "deleteUserControlById", notes = "get all data source")
  @GetMapping("/deleteUserControlById/{id}")
  public AmisResults deleteUserControlById(@PathVariable(value = "id") String id) {
    try {
      return AmisResults.updated(gloves.deleteUserControlById(id));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "updateUserControl", notes = "get all data source")
  @PostMapping("/updateUserControl/{id}")
  public AmisResults updateUserControl(@PathVariable(value = "id") String id, @RequestBody DataControl control) {
    try {
      control.setControlId(id);
      return AmisResults.updated(gloves.updateUserControl(control));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getUserControlByPage", notes = "get all data source")
  @GetMapping("/getUserControlByPage")
  public AmisResults getUserControlByPage(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<DataControl> p = new Page<DataControl>(page, perPage);
      IPage<DataControl> data = gloves.getUserControlByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getADataSourceByPage", notes = "get all data source")
  @GetMapping("/getADataSourceByPage")
  public AmisResults getADataSourceByPage(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<DataSource> p = new Page<DataSource>(page, perPage);
      IPage<DataSource> data = gloves.getDataSourceByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDeptByPage", notes = "get all data source")
  @GetMapping("/getDeptByPage")
  public AmisResults getDeptByPage(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<Dept> p = new Page<Dept>(page, perPage);
      IPage<Dept> data = gloves.getDeptByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDeptList", notes = "get all dept list")
  @GetMapping("/getDeptList")
  public AmisResults getDeptList() {
    try {
      return AmisResults.success(gloves.getDeptList());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getDocTypeList", notes = "get all doc type list")
  @GetMapping("/getDocTypeList/{patientId}")
  public AmisResults getDocTypeList(@PathVariable(value="patientId") String patientId) {
    try {
      return AmisResults.success(gloves.getDocTypeList(patientId));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getControlTypeList", notes = "get all data source")
  @GetMapping("/getControlTypeList")
  public AmisResults getControlTypeList() {
    try {

      List<DataControlType> data = gloves.getControlTypeList();
      return AmisResults.page(data, data.size());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getControlTypeOptions", notes = "get all data source")
  @GetMapping("/getControlTypeOptions")
  public AmisResults getControlTypeOptions() {
    try {

      List<DataControlType> data = gloves.getControlTypeList();
      List<AmisOptionItem> options = new ArrayList<>();
      for (DataControlType d : data) {
        AmisOptionItem item = new AmisOptionItem();
        item.setLabel(d.getLabel());
        item.setValue(d.getName());

        options.add(item);
      }
      return AmisResults.options(options);
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }
  
  @ApiOperation(value = "getPatientInfoByPage", notes = "get all data source")
  @GetMapping("/getPatientInfoByPage")
  public AmisResults getPatientInfoByPage(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "keywords", required = false) String keywords) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;

      if (keywords == null)
        keywords = "";

      IPage<PatientInfo> p = new Page<PatientInfo>(page, perPage);
      IPage<PatientInfo> data = gloves.getPatientInfoByPage(p, keywords);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getPatientListByDept", notes = "get all data source")
  @GetMapping("/getPatientListByDept")
  public AmisResults getPatientListByDept(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "perPage", required = false) Integer perPage,
      @RequestParam(value = "deptId", required = false) String deptId) {
    try {
      if (page == null || page == 0)
        page = 1;
      if (perPage == null || perPage == 0)
        perPage = 10;
        IPage<PatientInfo> p = new Page<PatientInfo>(page, perPage);
        IPage<PatientInfo> data = gloves.getPatientListByDept(p, deptId);
      return AmisResults.page(data.getRecords(), data.getTotal());
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }

  @ApiOperation(value = "getPatientInfoById", notes = "get all data source")
  @GetMapping("/getPatientInfoById/{patientId}")
  public AmisResults getPatientInfoById(@PathVariable(value = "patientId") String patientId) {
    try {

      return AmisResults.success(gloves.getPatientInfoById(patientId));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }
  
  @ApiOperation(value = "getDataSourceById", notes = "get data source by id")
  @GetMapping("/getDataSourceById/{sourceId}")
  public AmisResults getDataSourceById(@PathVariable(value="sourceId") String sourceId) {
    try {
      
      return AmisResults.success(gloves.getDataSourceById(sourceId));
    } catch (Exception e) {
      return AmisResults.error(e);
    }
  }
}