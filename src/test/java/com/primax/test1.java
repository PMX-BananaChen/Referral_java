package com.primax;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.primax.Utils.ExcelUtil;
import com.primax.config.MemberInfo;
import com.primax.config.WeixinConfig;
import com.primax.config.WeixinHelper;
import com.primax.controller.ReferralRest;
import com.primax.model.*;
import com.primax.service.WxEmployeeUserCQService;
import com.primax.service.referral.CodeDepartmentService;
import com.primax.service.referral.CodeHrService;
import com.primax.service.referral.EmployeeService;
import com.primax.service.referral.ReferralService;
//import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.tagext.PageData;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.primax.config.CommonUtil.httpsRequest;
import static com.primax.config.WeixinHelper.Openid2userId;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test1 {

    @Autowired
    private ReferralRest rest;
    //
    @Autowired
    private CodeDepartmentService codeDepartmentService;

    @Autowired
    private CodeHrService codeHrService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReferralService referralService;

    @Autowired
    private WxEmployeeUserCQService wxEmployeeUserCQService;

    // 测试
    @Test
    public void sizeTset() throws Exception {
//        CodeDepartment department =  codeDepartmentService.getCodedepartment("102993");
        List<CodeHr> hrs = codeHrService.getCodeHrs("610047");
        List<Integer> buids = new ArrayList<>();
        List<String> empTypes = new ArrayList<>();
        for (CodeHr hr:
                hrs) {
            buids.add(hr.getBuid());
            empTypes.add(hr.getEmpType());
        }
        Integer[] buIds = buids.toArray(new Integer[hrs.size()]);
        String[] empTypes1 = empTypes.toArray(new String[hrs.size()]);
        int[] a ;
        List<RecommendationForm> forms = new ArrayList<>();
        if (hrs != null && hrs.size() >0) {
            //HR查询 a待签核:1 b已签核:2,3 C已退单:-2

                a = new int[]{1, 11, 22};

            //未经过一级处理(经理)不查询出来 hr查询
            forms = referralService.getFormListByHR(buIds, empTypes1, a);
        }
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(forms);
        String string = jsonArray.toJSONString();
        System.out.println("+++++++++++++++++"+string);
        JSONArray jsonArray1=new JSONArray();
        jsonArray1.addAll(hrs);
        String string1 = jsonArray1.toJSONString();
        System.out.println("-----------------"+string1);
    }

//
//
//    @Before
//    public void setUp() throws Exception {
//        System.out.println("执行初始化");
//    }


    // 测试
//    @Test
//    public void sizeTset() throws Exception {
//        String empNo ="" ;
//        CodeDepartment dept = codeDepartmentService.getCodedepartmentByDeptNo("01500003");
//        while (dept.getMasterCode().isEmpty()){
//            if( dept.getProxyCode() != null){
//                empNo = dept.getProxyCode();
//                break;
//            }else {
//                dept = codeDepartmentService.getCodedepartmentByDeptNo(dept.getParentDeptNo());
//                empNo = dept.getMasterCode();
//            }
//
//        }
//        System.out.println("======================"+empNo);
//    }

    @Test
    public void sizeTset4() throws Exception {
        List<RecommendationForm> list = referralService.getFormList("620512", new int[]{0,1});
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void sizeTset5() throws Exception {
        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }

            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String s) {
                return 0;
            }

            @Override
            public String getHeader(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String s) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean b) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String s, String s1) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
                return null;
            }
        };
        request.setAttribute("status",0);
        request.setAttribute("empNo",601424);
        Result sign = rest.getRecommendationFormListBySign(request);
//        List<RecommendationForm> list = referralService.getFormListBySign("511507", new int[]{0,11,22});
        System.out.println(JSON.toJSONString(sign));
    }

    // 测试
    @Test
    public void sizeTset3() throws Exception {
        String a= "a";
        String[] split = a.split(",");
        StringBuilder builder = new StringBuilder();

//        builder.append("'").
        for (String i:
        split) {
            builder.append("'");
            builder.append(i);
            builder.append("',");
        }
        builder.deleteCharAt(builder.length()-1);
        System.out.println("======================"+builder);
    }

    @Test
    public void sizeTset2() throws Exception {
        Employee employee1 = rest.getReferralEmpNo("50015170", "191459",null,"P2");
        System.out.println("======================"+ JSON.toJSONString(employee1));
    }

    @Test
    public void sizeTset1() throws Exception {
        //05624210 178598
//        CodeDepartment dept = codeDepartmentService.getCodedepartmentByDeptNo("05624210");
//        String empNo = rest.getReferralEmpNo(dept, "178598");
//        System.out.println("=====================");
//        System.out.println(empNo);
//        String s = WeixinHelper
//                .Openid2userId("RFJElsPNblax7iIIXcaA8_ugEM38G5EWfcbEEB3LDqBd00Wvoj7jvuFkfwG_9igs8BRRFa1ErGzgzr5_HBabB9IqzhYvHbIkiHXlp_YDIMaD4Fhmroqs9zoniCGNstWW55sm3yizp3kmF8bJClAi74fZ_tERpPMtQNoC5KZer5Vowu7r3GhonlNKmsgwwg3HrFfs1WKjwzZcbt7RMoL6BA", "oapQk07HrAerfUI4R3vLI6BftzgU");
//
//
//        System.out.println(s);
//        System.out.println(s);
        MemberInfo userInfo = getMemberInfo("QO1bi2XJkC2PM5RRA6tvpCuyM_XtNvH33Go8jFQzxmXHx-IRmz-s9z6qnUhNni_oRVckL_uk3wjUdzaLjtaPaZly4FR44nPcVb7iZV3UV8Ju1MCeuAD8H0mqmSwqA9LlBgJh25VwQT8DLiDc79my6p2rDoIL0IG2KJ1y9XB5VDWLIRnq8N5WmRQyLMbZn-MLFkho_pxhTXYNqNEtajXDRQ");
        System.out.println("userInfo========>" + userInfo.toString());
        String userId = userInfo.getUserId();
        if (userId == null && userInfo.getOpenId() != null) {
            System.out.println("请绑定企业微信后重试111");
        } else if (userId != null && userInfo.getOpenId() == null) {
            WxEmployeeUser user = wxEmployeeUserCQService.getWxEmployeeUserByCQ(userId);
            if (user == null) {
                System.out.println("获取员工信息失败");
            }
            //获取员工工号
            Employee emp = employeeService.getEmployee(user.getEmployeeCode());

            emp.setUserId(userId);
            System.out.println("user.getEmployeeCode():"+user.getEmployeeCode());
            System.out.println("userId:"+userId);
        }
    }

    public MemberInfo getMemberInfo(String access_token) {
        JSONObject jsonObject = new JSONObject();
        MemberInfo memberInfo = null;
        //"errcode":0,"DeviceId":"863746041513502","errmsg":"ok","OpenId":"oapQk07HrAerfUI4R3vLI6BftzgU"
        jsonObject.put("errcode", 0);
        jsonObject.put("DeviceId", "863746041513502");
        jsonObject.put("errmsg", "ok");
        jsonObject.put("OpenId", "oapQk0yqx9PlEJliugs-SM7zVgJY");

        if (null != jsonObject) {
            try {
                memberInfo = new MemberInfo();
                if (Integer.parseInt(jsonObject.getString("errcode")) == 0 && jsonObject.getString("errmsg").equals("ok")) {
                    //返回码成功
                    memberInfo.setErrCode(0);
                    memberInfo.setErrMsg(jsonObject.getString("errmsg"));
                    memberInfo.setDeviceId(jsonObject.getString("DeviceId"));

                    if (StringUtils.isNotBlank(jsonObject.getString("OpenId"))) {
                        System.out.println("jsonObject.getString(OpenId):" + jsonObject.getString("OpenId"));
                        //说明返回的是第二种结果，不是内部成员
                        String userId = Openid2userId(access_token, jsonObject.getString("OpenId"));
//                        memberInfo.setOpenId(jsonObject.getString("OpenId"));
                        memberInfo.setUserId(userId);
                        return memberInfo;
                    } else if (StringUtils.isNotBlank(jsonObject.getString("UserId"))) {
                        System.out.println("jsonObject.getString(UserId):" + jsonObject.getString("UserId"));

                        //说明是内部成员，可以通过userid获取成员信息
                        memberInfo.setUserId(jsonObject.getString("UserId"));
                        return memberInfo;
                    } else {
                        //返回出错
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * openid与userId互换
     * Openid2userId
     *
     * @param OpenId       企业内的成员id
     * @param access_token 调用接口凭证
     * @return WeixinAouth2Token
     */
    public static String Openid2userId(String access_token, String OpenId) {
        String userid = "";
        String requestUrl = WeixinConfig.OPENID_TO_USEID;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
        //请求参数
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("openid", OpenId);
        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", jsonObject1.toString());
        System.out.println("{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}{}");
        System.out.println("获取网页凭证json数据的值是：" + jsonObject.toString());
        System.out.println("获取网页凭证json数据的值是：" + jsonObject.toString());
        System.out.println("获取网页凭证json数据的值是：" + jsonObject.toString());
        if (null != jsonObject) {
            try {
                userid = jsonObject.getString("userid");
            } catch (Exception e) {
                userid = null;
            }
        }
        return userid;
    }

    @Test
    public void test2() {

        //获取数据

        List<ExeclModel> execlModels = new ArrayList<>();
        CodeDepartment dept = new CodeDepartment();

        List<Employee> empList = employeeService.getEmpList();
        for (Employee employee :
                empList) {
            System.out.println(employee.getEmpNo());
            ExeclModel execlModel = new ExeclModel();
            execlModel.setEmpNo(employee.getEmpNo());
            execlModel.setEmpName(employee.getEmpName());
            execlModel.setDeptNo(employee.getDeptNo());
            execlModel.setDeptName(employee.getDeptName());
            execlModel.setEmpType(employee.getEmpType());
            execlModel.setEmpTitle(employee.getEmpTitle());
            dept = codeDepartmentService.getCodedepartmentByDeptNo(employee.getDeptNo());
            if (dept != null){
                //部门主管工号
                Employee employee1 = rest.getReferralEmpNo(dept.getDepartmentCode(), employee.getEmpNo(),employee.getEmpType(),employee.getEmpTitle());
//                    Employee employee1 = employeeService.getEmployee(empNo);
                    if (employee1 != null){
                        execlModel.setEmpNo1(employee1.getEmpNo());
                        execlModel.setEmpName1(employee1.getEmpName());
                        execlModel.setDeptNo1(employee1.getDeptNo());
                        execlModel.setDeptName1(employee1.getDeptName());
                        execlModel.setEmpType1(employee1.getEmpType());
                        execlModel.setEmpTitle1(employee1.getEmpTitle());
                    }

            }
            execlModels.add(execlModel);
        }
        //excel标题
        String[] title = {"员工工号", "员工姓名", "员工部门编号", "员工部门名称", "员工职位类型", "员工职等", "主管工号", "主管姓名", "主管部门编号", "主管部门名称", "主管职位类型", "主管职等"};
        //excel文件名
        String fileName = "员工一对一主管对应表" + System.currentTimeMillis() + ".xls";
        byte b[] = ExcelUtil.export(fileName, title, execlModels);

        File f = new File("D:\\tmp\\"+fileName+".xls");
        try {
            FileUtils.writeByteArrayToFile(f, b, true);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
