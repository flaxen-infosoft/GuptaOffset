package com.flaxeninfosoft.guptaoffset.utils;

public class ApiEndpoints {

    public static final String BASE_URL = "http://88.99.248.156/~anand/api/";

    public static final String LOGIN = "auth/login.php";    //done

//    ----------------------------------------------------------------------------------------------

    public static final String GET_EMPLOYEE_BY_ID = "employee/getEmployeeById.php"; //done
    public static final String GET_ALL_EMPLOYEES = "employee/getAllEmployees.php";  //done
    public static final String GET_ALL_SUPER_EMPLOYEES = "employee/getAllSuperEmployees.php";   //done
    public static final String GET_EMPLOYEES_OF_SUPER_EMPLOYEE = "employee/getEmployeesOfSuperEmployee.php";    //done
    public static final String ADD_EMPLOYEE = "employee/addEmployee.php";   //--
    public static final String ADD_SUPER_EMPLOYEE = "employee/addSuperEmployee.php";    //--
    public static final String UPDATE_EMPLOYEE_BY_ID = "employee/updateEmployeesById.php";
    public static final String SUSPEND_EMPLOYEE_BY_ID = "employee/suspendEmployeeById.php";
    public static final String ACTIVATE_EMPLOYEE_BY_ID = "employee/activateEmployeeById.php";
    public static final String UPDATE_EMPLOYEE_BATTERY_STATUS = "employee/updateEmployeeBatteryStatus.php";

//    ----------------------------------------------------------------------------------------------

    public static final String GET_LEAVE_BY_ID = "leave/getLeaveById.php";  //--
    public static final String APPROVE_LEAVE_BY_ID = "leave/approveLeaveById.php";
    public static final String REJECT_LEAVE_BY_ID = "leave/rejectLeaveById.php";
    public static final String GET_ALL_LEAVES = "leave/getAllLeaves.php";
    public static final String GET_ALL_PENDING_LEAVES = "leave/getAllPendingLeaves.php";
    public static final String GET_ALL_APPROVED_LEAVES = "leave/getAllApprovedLeaves.php";
    public static final String GET_ALL_REJECTED_LEAVES = "leave/getAllRejectedLeaves.php";
    public static final String GET_EMPLOYEE_ALL_LEAVES = "leave/getEmployeeAllLeaves.php";
    public static final String ADD_LEAVE = "leave/addLeave.php";

//  ----------------------------------------------------------------------------------------------

    public static final String GET_EOD_BY_ID = "eod/getEodById.php";
    public static final String ADD_EOD = "eod/addEod.php";
    public static final String GET_EMPLOYEE_ALL_EOD_BY_ID = "eod/getEmployeeAllEodsById.php";
    public static final String GET_ALL_EOD = "eod/getAllEods.php";
    public static final String GET_EMPLOYEE_TODAY_EOD = "eod/getEmployeeTodaysEod.php";

//  ----------------------------------------------------------------------------------------------

    public static final String GET_EMPLOYEE_CURRENT_LOCATION = "location/getEmployeeCurrentLocation.php";
    public static final String GET_EMPLOYEE_TODAYS_LOCATION_HISTORY = "location/getEmployeeTodaysLocationHistory.php";
    public static final String ADD_EMPLOYEE_LOCATION = "location/addEmployeeLocation.php";

//  ----------------------------------------------------------------------------------------------

    public static final String GET_ORDER_BY_ID = "order/getOrderById.php";
    public static final String GET_EMPLOYEE_ORDERS = "order/getEmployeeOrders.php";
    public static final String GET_ALL_ORDERS = "order/getAllOrders.php";
    public static final String ADD_ORDER = "order/addOrder.php";

//    ----------------------------------------------------------------------------------------------

    public static final String ADD_DEALER = "dealer/addDealer.php";

    public static  final String GET_DEALER_BY_ID="dealer/getDealerById.php";
    public static final String GET_EMPLOYEE_DEALER = "dealer/getDealerByEmployee.php";

//    ----------------------------------------------------------------------------------------------

    public static final String ADD_PAYMENT = "payment/addPayment.php";
    public static final String GET_ALL_PENDING_REQUESTS = "payment/getAllPendingPayments.php";
    public static final String GET_PENDING_REQUESTS_TO_EMPLOYEE = "payment/getPendingRequestsToEmployee.php";

//    ----------------------------------------------------------------------------------------------

    public static final String ADD_SCHOOL = "school/addSchool.php";
    public static final String GET_SCHOOL_BY_ID = "school/getSchoolById.php";

//    ----------------------------------------------------------------------------------------------

    public static final String GET_EMPLOYEE_HISTORY = "history/getEmployeeHistory.php";

//    ----------------------------------------------------------------------------------------------

    public static final String SEND_MESSAGE = "message/sendMessage.php";
    public static final String GET_MESSAGES = "message/getMessages/php";

//    ----------------------------------------------------------------------------------------------

    public static final String PUNCH_ATTENDANCE = "attendance/punchAttendance.php";
    public static final String GET_TODAYS_ATTENDANCE = "attendance/getTodaysAttendance.php";
    public static final String GET_ATTENDANCE_BY_ID = "attendance/getAttendanceById.php";

}
