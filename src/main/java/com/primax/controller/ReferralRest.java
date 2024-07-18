package com.primax.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.primax.config.EhcacheUtil;
import com.primax.config.PrimaryGenerater;
import com.primax.config.WeixinConfig;
import com.primax.model.*;
import com.primax.service.*;
import com.primax.service.referral.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.primax.config.CommonUtil.httpsRequest;

@RestController
@RequestMapping("/pmx/referral/referralForm")
public class ReferralRest {

    private static final Logger logger = LoggerFactory.getLogger(ReferralRest.class);

    /**
     * 表单
     */
    @Autowired
    private ReferralService referralService;

    /**
     * 审核
     */
    @Autowired
    private FormCheckService formCheckService;

    /**
     * 部门
     */
    @Autowired
    private CodeDepartmentService codeDepartmentService;

    /**
     * HR
     */
    @Autowired
    private CodeHrService codeHrService;

    /**
     * 员工
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * 企业微信员工service
     */
    @Autowired
    private WxEmployeeUserService wxEmployeeUserService;


    /**
     * 重庆企业微信员工service
     */
    @Autowired
    private WxEmployeeUserCQService wxEmployeeUserCQService;

    /**
     * 东莞企业微信员工service
     */
    @Autowired
    private WxEmployeeUserKSService wxEmployeeUserKSService;


    /**
     * 东莞获取用户工号
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getPmxEmployee")
    @ResponseBody
    public Result getPmxEmployee(HttpServletRequest request, HttpServletResponse response) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {

            //企业微信绑定ID
            String userId = request.getParameter("userId");
            String area = request.getParameter("area");
            if (userId == null) {
                return new Result(Result.RESULT_FAILURE, "获取微信授权失败");
            }
            WxEmployeeUser user = new WxEmployeeUser();
            if (area.equals("DG")) {
                user = wxEmployeeUserService.getWxEmployeeUserByPmx(userId);
            } else if (area.equals("CQ")) {
                user = wxEmployeeUserCQService.getWxEmployeeUserByCQ(userId);
            } else if (area.equals("KS")) {
                user = wxEmployeeUserKSService.getWxEmployeeUserByKS(userId);
            }

            if (user == null) {
                return new Result(Result.RESULT_FAILURE, "企业微信系统未查到您的资料");
            }
            //获取员工工号
            Employee emp = employeeService.getEmployee(user.getEmployeeCode());

            r.setData(emp);
            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }

    /**
     * 获取用户身份经理或者HR
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmpType")
    @ResponseBody
    public Result getEmpType(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            String type = "";

            String empNo = request.getParameter("empNo");
            if (empNo == null || empNo == "") {
                return new Result(Result.RESULT_FAILURE, "未获取到审批人工号");
            }
            //获取部门信息
            CodeDepartment department = codeDepartmentService.getCodedepartment(empNo);
            //获取HR信息
            CodeHr hr = codeHrService.getCodeHr(empNo);
            if (department != null) {
                type = "0";
            } else if (hr != null) {
                type = "1";
            } else {
                type = "2";
            }

            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData(type);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }


    /**
     * 获取用户身份经理或者HR
     *
     * @param request
     * @return
     */
    @RequestMapping("/getEmpRecord")
    @ResponseBody
    public Result getEmpRecord(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            String type = "";

            String introducedIDNo = request.getParameter("introducedIDNo");
            if (introducedIDNo == null || introducedIDNo == "") {
                return new Result(Result.RESULT_FAILURE, "未获取到被推荐人身份证号");
            }
            //获取部门信息
            int record = employeeService.getEmpRecord(introducedIDNo);


            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }


    /**
     * 填写推荐单
     *
     * @param form
     * @return
     */
    @RequestMapping("/addRecommendationForm")
    @ResponseBody
    public Result addRecommendationForm(@RequestBody RecommendationForm form) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            if (form == null) {
                return r;
            }
            String idNo = form.getIntroducedIdNo();
            //判断被推荐人是否已经存在
            if (idNo != null) {
                //查询员工是否离职满三月 未满返回1
                int record = employeeService.getEmpRecord(idNo);
                logger.info("员工在职或离职未满三月返回1,不在职返回0-------" + record);
                //查询推荐单是否还在跑流程 还在跑返回1
                int record1 = referralService.getFormRecord(idNo);
                //被推荐人在推荐流程中
                if (record1 > 0) {
                    return new Result(Result.RESULT_NO_AUTHORITY, "被推荐人已经被他人推荐");
                }
                //被推荐人不在推荐流程中,但是在职或离职未满3个月
                else if (record1 == 0 && record > 0) {
                    return new Result(Result.RESULT_NO_AUTHORITY, "被推荐人已入职或离职未满3个月");
                }
            }
            Integer year = Calendar.getInstance().get(Calendar.YEAR);
            Integer age = 0;
            if (idNo.length() == 15) {

                age = (year - Integer.parseInt("19" + idNo.substring(6, 8)));
            } else if (idNo.length() == 18) {

                age = (year - Integer.parseInt(idNo.substring(6, 10)));
            }

            if (age < 16) {
                return new Result(Result.RESULT_NO_AUTHORITY, "被推荐人未满16岁");
            }

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //取内推流水单号
            String serialNumber = referralService.selectSerialNumber();
            if (serialNumber == null) {
                serialNumber = "TJ20200202000";
            }
            String number = PrimaryGenerater.getnumber(serialNumber);
            form.setFormNo(number);
            CodeDepartment dept;
            //取buid
            String deptNo = form.getIntroducerDeptNo();
            //取推荐人信息
            Employee emp = employeeService.getEmployee(form.getIntroducerEmpNo());

            //假如手动填写部门
            if (deptNo == null || deptNo.equals("")) {

                if (emp == null) {
                    return new Result(Result.RESULT_NO_AUTHORITY, "未找到推荐人资料,请确认工号是否正确");
                } else {
                    deptNo = emp.getDeptNo();
                    dept = codeDepartmentService.getCodedepartmentByDeptNo(deptNo);
                }
            } else {
                dept = codeDepartmentService.getCodedepartmentByDeptNo(deptNo);
            }
            String empType = emp.getEmpType();
            String empTitle = emp.getEmpTitle();
            form.setBuId(dept.getBuId());
            //取经理或者代理人empno
            Employee emp1 = getReferralEmpNo(deptNo, form.getIntroducerEmpNo(), empType, empTitle);
            if (null == emp1) {
                return new Result(Result.RESULT_NO_AUTHORITY, "未找到对应签核主管,请联系应用管理员");
            }
            String empNo = emp1.getEmpNo();
            logger.info("empNo================" + empNo);
            //获取userId
            WxEmployeeUser wxEmp = new WxEmployeeUser();
            //推送地区
            String url = "https://wx2.primax.com.cn/referral";
            if ("DG".equals(form.getArea())) {
                wxEmp = wxEmployeeUserService.getWxEmpByPmx(empNo);
                url = "https://wx2.primax.com.cn/referral";
            } else if ("CQ".equals(form.getArea())) {
                wxEmp = wxEmployeeUserCQService.getWxEmpByCQ(empNo);
                url = "https://wx2.primax.com.cn/referral_cq";
            } else if ("KS".equals(form.getArea())) {
                wxEmp = wxEmployeeUserKSService.getWxEmpByKS(empNo);
                url = "https://wx2.primax.com.cn/referral_ks";
            }
            if (wxEmp == null) {
                return new Result(Result.RESULT_FAILURE, "企业微信系统未查到您的资料");
            }
            String userId = wxEmp.getWorkUserId();
            logger.info("userId================" + userId);


            //获取性别简称
            String sex1 = form.getIntroducedSex1();
            if ("男".equals(sex1)) {
                form.setIntroducedSex("M");
            } else {
                form.setIntroducedSex("F");
            }
//            form.setArea(emp.getArea());
            form.setSendEmpNo(wxEmp.getEmployeeCode());//推送主管工号
            form.setFormState(0);//签核状态
            form.setApplyDate(new Date());//提交日期
            form.setIntroducerType(emp.getEmpType());//员工类别
            form.setIntroducerCostCenter(emp.getCostCenter());//成本中心
            form.setIntroducerOutDate(emp.getEmpOutDate());//离职日期
            //姓名和部门不取前端表单传来的值,电话号码取
            form.setIntroducerEmpName(emp.getEmpName());
            form.setIntroducerDeptNo(emp.getDeptNo());

            //姓名简转繁
            String introducedName = ZhConverterUtil.convertToTraditional(form.getIntroducedName());
            form.setIntroducedName(introducedName);
            referralService.addVisitorRecord(form);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //发送信息(提醒经理签核)
            //0.设置消息内容
            String title = "员工推荐审核提醒";
            String description = "<div class=\"normal\">有新的员工推荐申请需审核!</div> <div class=\"normal\">" +
                    "推荐人:" + form.getIntroducerEmpName() + "</div><div class=\"normal\">" +
                    "操作时间:" + formatter.format(form.getApplyDate()) + "</div><div class=\"normal\">" +
                    "</div>";


            //1.创建文本卡片消息对象
            TextcardMessage message = new TextcardMessage();
            //1.1非必需
            message.setTouser(userId);  //不区分大小写
            Textcard textcard = new Textcard();
            textcard.setTitle(title);
            textcard.setDescription(description);
            textcard.setUrl(url);
            message.setTextcard(textcard);
            //发送信息
            sendMessage(message, form.getArea());

            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }

    public Employee getReferralEmpNo(String deptNo, String introducerEmpNo, String empType, String title) {
        CodeDepartment codeDept = new CodeDepartment();
        String empNo = "";
        String proxyCode = "";
        String masterCode = "";
        String deptNo1 = deptNo;
        Employee employee = new Employee();
        while (true) {
            codeDept = this.codeDepartmentService.getCodedepartmentByDeptNo(deptNo1);
            if (codeDept == null) {
                break;
            }
            deptNo1 = codeDept.getParentDeptNo();

            masterCode = codeDept.getMasterCode();
            proxyCode = codeDept.getProxyCode();
            if (StringUtils.isNotBlank(proxyCode) && !proxyCode.equals(introducerEmpNo)) {
                empNo = codeDept.getProxyCode();
                employee = this.employeeService.getEmployee1(empNo);
                if (employee != null) {
                    String empTitle = employee.getEmpTitle();
                    String empTitleName = employee.getEmpTitleName();
                    if (title.contains("W0") || (!title.contains("W0") && !empTitle.contains("L3") && !empTitle.contains("L4") && !(empTitle.contains("L5") && empTitleName.contains("課長")))) {
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(masterCode) && !masterCode.equals(introducerEmpNo)) {
                empNo = codeDept.getMasterCode();
                employee = this.employeeService.getEmployee1(empNo);
                if (employee != null) {
                    String empTitle = employee.getEmpTitle();
                    String empTitleName = employee.getEmpTitleName();
//                    if ((empType.equals("DL")) ||(!empType.equals("DL") && !empTitle.contains("L3") && !empTitle.contains("L4") && !empTitle.contains("L5"))){
//                    if ((empType.equals("DL") && title.contains("W0")) ||((empType.equals("IDL") || ((empType.equals("DL") && !title.contains("W0") ))) && !empTitle.contains("L3") && !empTitle.contains("L4") && !empTitle.contains("L5"))){
//                        break;
//                    }
                    if (title.contains("W0") || (!title.contains("W0") && !empTitle.contains("L3") && !empTitle.contains("L4") && !empTitle.contains("L5") && !empTitleName.contains("課長"))) {
                        break;
                    }
                }

            }
        }

//        if (employee != null && !empType.equals("DL")){
//            String title = employee.getEmpTitle();
//            while (title.contains("L3") || title.contains("L4") || title.contains("L5")) {
//                codeDept = this.codeDepartmentService.getCodedepartmentByDeptNo(codeDept.getParentDeptNo());
//                if (codeDept == null) {
//                    break;
//                }
//                masterCode = codeDept.getMasterCode();
//                proxyCode = codeDept.getProxyCode();
//                if (StringUtils.isNotBlank(proxyCode)) {
//                    empNo = codeDept.getProxyCode();
//                    employee = this.employeeService.getEmployee(empNo);
//                    if (employee != null) {
//                        break;
//                    }
//                }
//                if (StringUtils.isNotBlank(masterCode)) {
//                    empNo = codeDept.getMasterCode();
//                    employee = this.employeeService.getEmployee(empNo);
//                    if (employee != null) {
//                        break;
//                    }
//
//                }
//            }
//        }


//
//
//        if (empType.equals("DL")) {
//            while (employee == null) {
//                codeDept = this.codeDepartmentService.getCodedepartmentByDeptNo(codeDept.getParentDeptNo());
//                if (codeDept == null) {
//                    break;
//                }
//                masterCode = codeDept.getMasterCode();
//                proxyCode = codeDept.getProxyCode();
//                if (StringUtils.isNotBlank(proxyCode)) {
//                    empNo = codeDept.getProxyCode();
//                    employee = this.employeeService.getEmployee(empNo);
//                    if (employee != null) {
//                        break;
//                    }
//                }
//                if (StringUtils.isNotBlank(masterCode)) {
//                    empNo = codeDept.getMasterCode();
//                    employee = this.employeeService.getEmployee(empNo);
//                    if (employee != null) {
//                        break;
//                    }
//
//                }
//            }
//        } else {
//            if (employee != null) {
//                String title = employee.getEmpTitle();
//                while (title.contains("L3") || title.contains("L4") || title.contains("L5")) {
//                    codeDept = this.codeDepartmentService.getCodedepartmentByDeptNo(codeDept.getParentDeptNo());
//                    if (codeDept == null) {
//                        break;
//                    }
//                    masterCode = codeDept.getMasterCode();
//                    proxyCode = codeDept.getProxyCode();
//                    if (StringUtils.isNotBlank(proxyCode)) {
//                        empNo = codeDept.getProxyCode();
//                        employee = this.employeeService.getEmployee(empNo);
//                        if (employee != null) {
//                            break;
//                        }
//                    }
//                    if (StringUtils.isNotBlank(masterCode)) {
//                        empNo = codeDept.getMasterCode();
//                        employee = this.employeeService.getEmployee(empNo);
//                        if (employee != null) {
//                            break;
//                        }
//
//                    }
//                }
//            } else {
//
//            }
//        }

        return employee;
    }


    /**
     * 员工查询内推单列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRecommendationFormList")
    @ResponseBody
    public Result getRecommendationFormList(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            int[] a = new int[2];
            String empNo = request.getParameter("empNo");
            //0：待审核 1：审核中；-1, 一级拒绝 2：已审核；-2, 二级拒绝 3: 已结案；
            String status = request.getParameter("status");
            if (empNo == null || empNo == "") {
                return new Result(Result.RESULT_FAILURE, "未获取到填单人工号");
            }
            if ("0".equals(status)) {
                a = new int[]{0, 1};
            } else if ("1".equals(status)) {
                a = new int[]{2, 3};
            } else if ("2".equals(status)) {
                a = new int[]{-1, -2};
            }
            List<RecommendationForm> forms = referralService.getFormList(empNo, a);

            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData(forms);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }

    /**
     * 经理,HR,推荐人查询提报列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getRecommendationFormListBySign")
    @ResponseBody
    public Result getRecommendationFormListBySign(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            int[] a = new int[3];
            Map<String, Object> map = new HashMap<>();
            int i = 0;
            List<RecommendationForm> forms = new ArrayList<>();

            //0：待审核 1：审核中；-1, 一级拒绝 2：已审核；-2, 二级拒绝 3: 已结案；
            String status = request.getParameter("status");
            String empNo = request.getParameter("empNo");
            if (empNo == null || empNo == "") {
                return new Result(Result.RESULT_FAILURE, "未获取到审批人工号");
            }
            //获取部门信息
            CodeDepartment department = codeDepartmentService.getCodedepartment(empNo);
//            List<String> deptNos = codeDepartmentService.getCodedepartmentByParentDeptNo(department.getDepartmentCode());
//            String[] nos = deptNos.toArray(new String[deptNos.size()]);
            //获取HR信息
            List<CodeHr> hrs = codeHrService.getCodeHrs(empNo);
//            String[] buids = hrs.toArray(new String[hrs.size()]);
//            String[] empType = hrs.toArray(new String[hrs.size()]);
            List<Integer> buids = new ArrayList<>();
            List<String> empTypes = new ArrayList<>();
            for (CodeHr hr :
                    hrs) {
                buids.add(hr.getBuid());
                empTypes.add(hr.getEmpType());
            }
            Integer[] buIds = buids.toArray(new Integer[hrs.size()]);
            String[] empTypes1 = empTypes.toArray(new String[hrs.size()]);

            //获取内推单列表
            //11 22占位,先将就
            if (department != null) {
                //经理查询 a待签核:0 b已签核:1,2,3 c已退单:-1,-2
                //======11 22均为占位=============
                if ("0".equals(status)) {
                    a = new int[]{0, 11, 22};
                } else if ("1".equals(status)) {
                    a = new int[]{1, 2, 3};
                } else if ("2".equals(status)) {
                    a = new int[]{-1, 11, 22};
                }
                //按照部门code查询内推单列表
                forms = referralService.getFormListBySign(empNo, a);

            } else if (hrs != null && hrs.size() > 0) {
                //HR查询 a待签核:1 b已签核:2,3 C已退单:-2
                if ("0".equals(status)) {
                    a = new int[]{1, 11, 22};
                } else if ("1".equals(status)) {
                    a = new int[]{2, 3, 11};
                } else if ("2".equals(status)) {
                    a = new int[]{-2, 11, 22};
                }
                i = 1;
                //未经过一级处理(经理)不查询出来 hr查询
                forms = referralService.getFormListByHR(buIds, empTypes1, a);
            }
            map.put("forms", forms);
            map.put("empTpye", i);
            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }


    /**
     * 查询内推单详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getRecommendationFormDetail")
    @ResponseBody
    public Result getRecommendationFormDetail(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            //推荐单流水号
            String formNo = request.getParameter("formNo");
            if (formNo == null || formNo == "") {
                return new Result(Result.RESULT_FAILURE, "未获取到内推单号");
            }
            RecommendationForm form = referralService.getForm(formNo);
            String deptNo = form.getIntroducerDeptNo();
            CodeDepartment dept = codeDepartmentService.getCodedepartmentByDeptNo(deptNo);
            form.setIntroducerDeptName(dept.getDepartmentName());
            //取推荐人信息
            Employee emp1 = employeeService.getEmployee(form.getIntroducerEmpNo());

            //占暂时先这样---------------
            ArrayList<String> list = new ArrayList<>();
//            {"朋友","亲戚", "同学", "老乡"};
            list.add("自己");
            list.add("朋友");
            list.add("亲戚");
            list.add("同学");
            list.add("老乡");
            form.setRelationshipName(list.get(form.getRelationship()));
            String introducedSex = form.getIntroducedSex();
            if ("M".equals(form.getIntroducedSex())) {
                introducedSex = "男";
            } else {
                introducedSex = "女";
            }
            form.setIntroducedSex(introducedSex);
            //---------------------

            if (form.getFormState() == 0) {
                String empType = emp1.getEmpType();
                String title = emp1.getEmpTitle();
                Employee emp = getReferralEmpNo(deptNo, form.getIntroducerEmpNo(), empType, title);
//                Employee emp = employeeService.getEmployee(empNo);
                if (emp != null) {
                    form.setCheckName1(emp.getEmpName());
                }

            }

            if (form.getFormState() == 1) {
                List<CodeHr> hrs = codeHrService.getCodeHrByBuId(form.getBuId(), form.getIntroducedType());
                if (hrs.size() > 0) {
                    CodeHr codeHr = hrs.get(0);
                    form.setCheckName1(codeHr.getHrEmpName());
                }
            }
            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData(form);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");
        }
        return r;
    }


    /**
     * 签核动作
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateFormStatus")
    @ResponseBody
    public Result updateFormStatus(HttpServletRequest request) {
        Result r = new Result(Result.RESULT_FAILURE, Result.RESULT_FAILURE_MSG);

        try {
            //推荐单流水号
            String formNo = request.getParameter("formNo");

            //签核者id
            String empNo = request.getParameter("empNo");

            //签核指令0：待审核;1：审核中；-1, 一级拒绝 (1是经理操作)2：已审核；-2, 二级拒绝(2是HR操作)
            //经理 1通过 -1拒绝
            //HR 2通过 -2拒绝
            String formState = request.getParameter("formState");

            //签核备注
//            String checkRemark = request.getParameter("checkRemark");

            //查询内推单
            RecommendationForm form = referralService.getForm(formNo);

            //内推单审核记录,只有被经理签核后才产生记录
            FormCheck check = formCheckService.getFormCheck(formNo);

            //判断是否存在或者是否审核关闭
            //"Close".equals(check.getCheckResult())
            if (form == null) {
                return new Result(Result.RESULT_FAILURE, "推荐单不存在或审核流程已结束");
            }

            if (formState == null) {
                return new Result(Result.RESULT_FAILURE, "签核指令错误");
            }

            if (check == null) {
                check = new FormCheck();
            }

            Integer formState1 = Integer.valueOf(formState);

            //获取签核者信息
            Employee emp = employeeService.getEmployee(empNo);

            //Form表更新
            //改变签核状态
            form.setFormState(formState1);
            //时间更新
            form.setUpDateDate(new Date());
            //签核者姓名
            form.setCheckName(emp.getEmpName());
            //签核时间
            form.setCheckDate(new Date());
            //审核通过pass 拒绝Refuse
            if (formState1 == 1) {
                form.setCheckState("Pass");
                check.setCheckResult("Next");
                check.setGrade(1);
            } else if (formState1 == -1) {
                form.setCheckState("Refuse");
                check.setCheckResult("Refuse");
                check.setGrade(3);
            } else if (formState1 == 2) {
                form.setCheckState("Pass");
                check.setCheckResult("Pass");
                check.setGrade(3);
            } else if (formState1 == -2) {
                form.setCheckState("Refuse");
                check.setCheckResult("Refuse");
                check.setGrade(3);
            }

            //Check表更新
            check.setCategory("RecommendationForm");
//          check.setCheckRemark(checkRemark);
            check.setCheckName(emp.getEmpName());
            check.setCheckTime(new Date());
            check.setFormID(form.getRecommendationID());
            check.setFormNo(form.getFormNo());
            check.setCheckManID(1);
            formCheckService.insert(check);
            //未查询到内推审核单纪录,进行插入数据操作
//            if(check.getFormNo() == null){
//                formCheckService.insert(check);
//            }else {
            //查询到内推审核记录,进行更新数据操作
//                formCheckService.update(check,);
//            }

            //最后进行内推单数据更新
            referralService.updateForm(form);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //发送信息(提醒HR签核或通知推荐人推荐单状态)
            //0.设置消息内容
            String title = "";
            //1.创建文本卡片消息对象
            TextcardMessage message = new TextcardMessage();
            //企业微信用户id;
            String userId = "";
            String description = "";
            String url = "";
            if (Integer.valueOf(formState) == 1) {
                title = "员工推荐审核提醒";
                //获取HR的userId
                //获取HR信息
                List<CodeHr> hrs = codeHrService.getCodeHrByBuId(form.getBuId(), form.getIntroducedType());
                WxEmployeeUser wxEmp = new WxEmployeeUser();
                if (hrs.size() > 0) {
                    for (CodeHr hr :
                            hrs) {
                        if (form.getArea().equals("DG")) {
                            wxEmp = wxEmployeeUserService.getWxEmpByPmx(hr.getHrEmpNo());
                        } else if (form.getArea().equals("CQ")) {
                            wxEmp = wxEmployeeUserCQService.getWxEmpByCQ(hr.getHrEmpNo());
                        } else if (form.getArea().equals("KS")) {
                            wxEmp = wxEmployeeUserKSService.getWxEmpByKS(hr.getHrEmpNo());
                        }
                        if (wxEmp == null) {
                            return new Result(Result.RESULT_FAILURE, "企业微信系统未查到您的资料");
                        }
                        userId = wxEmp.getWorkUserId();
                        description = "<div class=\"normal\">有新的员工推荐申请需审核!</div> <div class=\"normal\">" +
                                "推荐人:" + form.getIntroducerEmpName() + "</div><div class=\"normal\">" +
                                "操作时间:" + formatter.format(form.getUpDateDate()) + "</div><div class=\"normal\">" +
                                "</div>";
                        url = "httpsR//wx2.primax.com.cn/referral";
                        Textcard textcard = new Textcard();
                        textcard.setTitle(title);
                        textcard.setDescription(description);
                        textcard.setUrl(url);
                        message.setTouser(userId);

                        message.setTextcard(textcard);
                        //发送信息
                        sendMessage(message, form.getArea());
                    }
                }
            } else {
                title = "员工推荐审核结果通知";
                WxEmployeeUser wxEmp = new WxEmployeeUser();
                //获取推荐人的userId
                if (form.getArea().equals("DG")) {
                    wxEmp = wxEmployeeUserService.getWxEmpByPmx(form.getIntroducerEmpNo());
                } else if (form.getArea().equals("CQ")) {
                    wxEmp = wxEmployeeUserCQService.getWxEmpByCQ(form.getIntroducerEmpNo());
                } else if (form.getArea().equals("KS")) {
                    wxEmp = wxEmployeeUserKSService.getWxEmpByKS(form.getIntroducerEmpNo());
                }
                if (wxEmp == null) {
                    return new Result(Result.RESULT_FAILURE, "企业微信系统未查到您的资料");
                }
                userId = wxEmp.getWorkUserId();
                url = "https://wx2.primax.com.cn/referral";
                if (Integer.valueOf(formState) == -1 || Integer.valueOf(formState) == -2) {
                    description = "<div class=\"normal\">您的员工推荐申请被退单!</div> <div class=\"normal\">" +
                            "审核人:" + form.getCheckName() + "</div><div class=\"normal\">" +
                            "操作时间:" + formatter.format(form.getUpDateDate()) + "</div><div class=\"normal\">" +
                            "</div>";
                } else if (Integer.valueOf(formState) == 2) {
                    description = "<div class=\"normal\">您的员工推荐申请已被签核通过!</div> <div class=\"normal\">" +
                            "操作人:" + form.getCheckName() + "</div><div class=\"normal\">" +
                            "操作时间:" + formatter.format(form.getUpDateDate()) + "</div><div class=\"normal\">" +
                            "</div>";
                }
                Textcard textcard = new Textcard();
                textcard.setTitle(title);
                textcard.setDescription(description);
                textcard.setUrl(url);
                message.setTouser(userId);

                message.setTextcard(textcard);
                //发送信息
                sendMessage(message, form.getArea());
            }

            r.setCode(Result.RESULT_SUCCESS);
            r.setMsg(Result.RESULT_SUCCESS_MSG);
            r.setData("");
            return r;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(Result.RESULT_FAILURE, "系统异常,请联系管理员");


        }
    }


    public void sendMessage(TextcardMessage message, String Area) {
        String accessToken = "";
        message.setMsgtype("textcard");
        String requestUrl = WeixinConfig.GET_ACCESS_TOKEN;
        //1.2必需
        if (Area.equals("DG")) {
            message.setAgentid(Integer.valueOf(WeixinConfig.AGENTID));
            //获取token
            accessToken = (String) EhcacheUtil.get("referralForm", "accessTokenPMX");
            if (StringUtils.isBlank(accessToken)
                    || StringUtils.isEmpty(accessToken)) {
                requestUrl = requestUrl.replace("ID", WeixinConfig.APPID);
                requestUrl = requestUrl.replace("SECRET", WeixinConfig.CROPSECRET);
                logger.info("accessToken+requestUrl=================" + requestUrl);

                JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

                accessToken = jsonObject.getString("access_token");
                EhcacheUtil.put("referralForm", "accessTokenPMX", accessToken);
            }
        } else if (Area.equals("CQ")) {
            message.setAgentid(Integer.valueOf(WeixinConfig.AGENTID_CQ));
            //获取token
            accessToken = (String) EhcacheUtil.get("referralForm", "accessTokenCQ");
            if (StringUtils.isBlank(accessToken)
                    || StringUtils.isEmpty(accessToken)) {

                requestUrl = requestUrl.replace("ID", WeixinConfig.APPID_CQ);
                requestUrl = requestUrl.replace("SECRET", WeixinConfig.CROPSECRET_CQ);
                logger.info("accessToken+requestUrl=================" + requestUrl);

                JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

                accessToken = jsonObject.getString("access_token");
                EhcacheUtil.put("referralForm", "accessTokenCQ", accessToken);
            }
        } else if (Area.equals("KS")) {
            message.setAgentid(Integer.valueOf(WeixinConfig.AGENTID_KS));
            //获取token
            accessToken = (String) EhcacheUtil.get("referralForm", "accessTokenKS");
            if (StringUtils.isBlank(accessToken)
                    || StringUtils.isEmpty(accessToken)) {
                requestUrl = requestUrl.replace("ID", WeixinConfig.APPID_KS);
                requestUrl = requestUrl.replace("SECRET", WeixinConfig.CROPSECRET_KS);
                logger.info("accessToken+requestUrl=================" + requestUrl);

                JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

                accessToken = jsonObject.getString("access_token");
                EhcacheUtil.put("referralForm", "accessTokenKS", accessToken);
            }
        }

        //3.发送消息：调用业务类，发送消息
        SendMessageService sms = new SendMessageService();
        sms.sendMessage(accessToken, message);
    }
}