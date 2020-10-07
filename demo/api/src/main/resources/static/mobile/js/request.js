const BASE_URL =
  window.BASE_URL ||
  (location.host.indexOf("80") > -1
    ? "http://" + location.hostname + ":8828"
    : location.host.indexOf("xdawo") > -1
    ? "http://emr.xdawo.com"
    : "");

const addPatientDoc = function (begin, data, url) {
  if (begin && typeof begin == "function") begin.apply();
  if(!url) {
    url = BASE_URL + "/api/addPatientDoc";
  }
  return new Promise((resolve, reject) => {
    try {
      jQuery.support.cors = true;
      $.ajax({
        url: url,
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function (r) {
          if (r.status != 0) {
            reject(r.msg);
          } else {
            resolve(r.data);
          }
        },
        //请求失败或者超时后的回调。
        error: function (xhr) {
          console.log(xhr.statusText);
          reject("接口服务已断开或正在重启中～");
        },
      });
    } catch (err) {
      reject(err);
    }
  });
};

function getPatientDocInfoById(patientDocId) {
  return new Promise((resolve, reject) => {
    try {
      let url = BASE_URL + "/api/getPatientDocInfoById/" + patientDocId;
      console.log("req url", url);
      jQuery.support.cors = true;
      $.ajax({
        url: url,
        type: "get",
        success: function (r) {
          console.log(r.status);
          if (r.status != 0) {
            reject(r.msg);
          } else {
            resolve(r.data);
          }
        },
        //请求失败或者超时后的回调。
        error: function (xhr) {
          console.log(xhr);
          reject("接口服务已断开或正在重启中～");
        },
      });
    } catch (err) {
      reject(err);
    }
  });
}

function getDocInfoById(docId) {
  return new Promise((resolve, reject) => {
    try {
      let url = BASE_URL + "/api/getDocInfoById/" + docId;
      console.log("req url", url);
      jQuery.support.cors = true;
      $.ajax({
        url: url,
        timeout: 50000,
        type: "get",
        success: function (r) {
          console.log(r.status);
          if (r.status != 0) {
            reject(r.msg);
          } else {
            resolve(r.data);
          }
        },
        //请求失败或者超时后的回调。
        error: function (xhr, status) {
          console.log(xhr);
          reject("状态：" + status + ",接口服务已断开或正在重启中～");
        },
      });
    } catch (err) {
      reject(err);
    }
  });
}

function uuid() {
  var s = [];
  var hexDigits = "0123456789abcdef";
  for (var i = 0; i < 36; i++) {
    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
  }
  s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
  s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
  s[8] = s[13] = s[18] = s[23] = "-";

  var uuid = s.join("");
  return uuid;
}

window.req = { getPatientDocInfoById,getDocInfoById, uuid };