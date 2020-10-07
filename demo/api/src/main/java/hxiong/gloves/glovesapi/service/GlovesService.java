package hxiong.gloves.glovesapi.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import hxiong.gloves.glovesapi.entity.*;

public interface GlovesService {
  List<Doc> getAllDocList();
  List<Doc> getMaxDocList();

  int addDoc(Doc doc);

  int addDoc(Doc doc, List<DocData> data, List<DocAttr> attr);

  int updateDoc(Doc doc);

  List<DataSource> getAllDataSource();

  Doc getDocById(Doc doc);

  Doc getDocInfoById(String docId);
  Doc getDocInfoById4M(String docId);

  int addDataSource(DataSource source);

  int deleteDataSourceById(Integer id);

  int deleteDataSourceByIds(List<Integer> ids);

  int updateDataSource(DataSource source);

  int bulkUpdateDataSource(List<DataSource> source);

  IPage<DataSource> getDataSourceByPage(IPage<DataSource> page,String keyworks);

  IPage<Doc> getDocByPage(IPage<Doc> p, String keywords);

  IPage<DocInfo> getDocInfoByPage(IPage<DocInfo> p, String keywords);

  PatientDocInfo getPatientDocById(PatientDocInfo doc);

  int addPatientDoc(PatientDocInfo doc);
  int addPatientDoc4M(PatientDocInfo doc);

  IPage<PatientInfo> getPatientInfoByPage(IPage<PatientInfo> p, String keywords);

  int updatePatientInfo(PatientInfo info);

  int deletePatientInfoById(Integer id);

  List<PatientDocInfo> getPatientDocListById(PatientDocInfo doc);

  IPage<DataControl> getControlByPage(IPage<DataControl> p, String keywords);

  int addDataControl(DataControl data);

  IPage<Dept> getDeptByPage(IPage<Dept> p, String keywords);

  List<DataControlType> getControlTypeList();

  List<DataControl> getUserControlList();

  int updateDataControl(DataControl data);

  IPage<DataControl> getUserControlByPage(IPage<DataControl> p, String keywords);

  DataControl getUserControlById(String controlId);

  int deleteUserControlById(String id);

  int updateUserControl(DataControl control);

  PatientDocInfo getPatientDocInfoById(String patientDocId);
  PatientDocInfo getPatientDocInfoById4M(String patientDocId);

  List<Dept> getDeptList();

  List<DocType> getDocTypeList(String patientId);

  PatientInfo getPatientInfoById(String patientId);

  int checkDocById(Doc doc);

  Object getDataSourceById(String sourceId);
  IPage<PatientInfo> getPatientListByDept(IPage<PatientInfo> page, String deptId);
  PatientDocInfo newPatientDoc(TypeDoc doc);
}