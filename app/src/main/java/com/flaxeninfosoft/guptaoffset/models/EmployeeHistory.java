package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeHistory {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.TYPE)
    @Expose
    private int type;

    @SerializedName(Constants.ATTENDANCE)
    @Expose
    private Attendance attendance;

    @SerializedName(Constants.DEALER)
    @Expose
    private Dealer dealer;

    @SerializedName(Constants.EMPLOYEE)
    @Expose
    private Employee employee;

    @SerializedName(Constants.EOD)
    @Expose
    private Eod eod;

    @SerializedName(Constants.LEAVE)
    @Expose
    private Leave leave;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    @SerializedName(Constants.ORDER)
    @Expose
    private Order order;

    @SerializedName(Constants.PAYMENT)
    @Expose
    private PaymentRequest payment;

    @SerializedName(Constants.SCHOOL)
    @Expose
    private School school;

    @SerializedName(Constants.LR)
    @Expose
    private Lr lr;

    @SerializedName(Constants.MESSAGE)
    @Expose
    private Message message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //    Yeah Boiii!!!
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Eod getEod() {
        return eod;
    }

    public void setEod(Eod eod) {
        this.eod = eod;
    }

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentRequest getPayment() {
        return payment;
    }

    public void setPayment(PaymentRequest payment) {
        this.payment = payment;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Lr getLr() {
        return lr;
    }

    public void setLr(Lr lr) {
        this.lr = lr;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EmployeeHistory{" +
                "id=" + id +
                ", empId=" + empId +
                ", type=" + type +
                ", attendance=" + attendance +
                ", dealer=" + dealer +
                ", employee=" + employee +
                ", eod=" + eod +
                ", leave=" + leave +
                ", location=" + location +
                ", order=" + order +
                ", payment=" + payment +
                ", lr=" + lr +
                ", school=" + school +
                ", message=" + message +
                '}';
    }
}
