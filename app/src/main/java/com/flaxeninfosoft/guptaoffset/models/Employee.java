package com.flaxeninfosoft.guptaoffset.models;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.EMAIL)
    @Expose
    private String email;

    @SerializedName(Constants.PASSWORD)
    @Expose
    private String password;

    @SerializedName(Constants.DESIGNATION)
    @Expose
    private String designation;

    @SerializedName(Constants.AREA)
    @Expose
    private String area;

    @SerializedName(Constants.DAILY_ALLOWANCE)
    @Expose
    private String dailyAllowance;

    @SerializedName(Constants.DAILY_ALLOWANCE_ONE_AMOUNT)
    @Expose
    private String dailyAllowance1;

    @SerializedName(Constants.DAILY_ALLOWANCE_TWO_AMOUNT)
    @Expose
    private String dailyAllowance2;

    @SerializedName(Constants.DAILY_ALLOWANCE_THREE_AMOUNT)
    @Expose
    private String dailyAllowance3;

    @SerializedName(Constants.DAILY_ALLOWANCE_FOUR_AMOUNT)
    @Expose
    private String dailyAllowance4;

    @SerializedName(Constants.DAILY_ALLOWANCE_ONE_DESCRIPTION)
    @Expose
    private String daily_allowance_description1;

    @SerializedName(Constants.DAILY_ALLOWANCE_TWO_DESCRIPTION)
    @Expose
    private String daily_allowance_description2;

    @SerializedName(Constants.DAILY_ALLOWANCE_THREE_DESCRIPTION)
    @Expose
    private String daily_allowance_description3;

    public String getDailyAllowance1() {
        return dailyAllowance1;
    }

    public void setDailyAllowance1(String dailyAllowance1) {
        this.dailyAllowance1 = dailyAllowance1;
    }

    public String getDailyAllowance2() {
        return dailyAllowance2;
    }

    public void setDailyAllowance2(String dailyAllowance2) {
        this.dailyAllowance2 = dailyAllowance2;
    }

    public String getDailyAllowance3() {
        return dailyAllowance3;
    }

    public void setDailyAllowance3(String dailyAllowance3) {
        this.dailyAllowance3 = dailyAllowance3;
    }

    public String getDailyAllowance4() {
        return dailyAllowance4;
    }

    public void setDailyAllowance4(String dailyAllowance4) {
        this.dailyAllowance4 = dailyAllowance4;
    }

    public String getDaily_allowance_description1() {
        return daily_allowance_description1;
    }

    public void setDaily_allowance_description1(String daily_allowance_description1) {
        this.daily_allowance_description1 = daily_allowance_description1;
    }

    public String getDaily_allowance_description2() {
        return daily_allowance_description2;
    }

    public void setDaily_allowance_description2(String daily_allowance_description2) {
        this.daily_allowance_description2 = daily_allowance_description2;
    }

    public String getDaily_allowance_description3() {
        return daily_allowance_description3;
    }

    public void setDaily_allowance_description3(String daily_allowance_description3) {
        this.daily_allowance_description3 = daily_allowance_description3;
    }

    public String getDaily_allowance_description4() {
        return daily_allowance_description4;
    }

    public void setDaily_allowance_description4(String daily_allowance_description4) {
        this.daily_allowance_description4 = daily_allowance_description4;
    }

    @SerializedName(Constants.DAILY_ALLOWANCE_FOUR_DESCRIPTION)
    @Expose
    private String daily_allowance_description4;
    @SerializedName(Constants.STATUS)
    @Expose
    private String status;

    @SerializedName(Constants.PHONE)
    @Expose
    private String phone;

    @SerializedName(Constants.SALARY)
    @Expose
    private String salary;

    @SerializedName(Constants.TOKEN)
    @Expose
    private String token;

    @SerializedName(Constants.ASSIGNED_TO)
    @Expose
    private String assignedTo;

    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.LOCATION)
    @Expose
    private Location currentLocation;

    @SerializedName(Constants.FIRM)
    @Expose
    private String firm;

    @SerializedName("morning_attendance")
    @Expose
    private String morning_attendance;

    @SerializedName("evening_attendance")
    @Expose
    private String evening_attendance;

    @SerializedName("school_count")
    @Expose
    private String school_count;

    @SerializedName("petrol_amount")
    @Expose
    private String petrol_amount;

    @SerializedName("tehsil_cover")
    @Expose
    private String tehsil_cover;

    @SerializedName("km")
    @Expose
    private String km;

    @SerializedName("dealer_count")
    @Expose
    private String dealer_count;

    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    @SerializedName("school_visit_duration")
    @Expose
    private String school_visit_duration;

    public String getMorning_attendance() {
        return morning_attendance;
    }

    public void setMorning_attendance(String morning_attendance) {
        this.morning_attendance = morning_attendance;
    }

    public String getEvening_attendance() {
        return evening_attendance;
    }

    public void setEvening_attendance(String evening_attendance) {
        this.evening_attendance = evening_attendance;
    }

    public String getSchool_count() {
        return school_count;
    }

    public void setSchool_count(String school_count) {
        this.school_count = school_count;
    }

    public String getPetrol_amount() {
        return petrol_amount;
    }

    public void setPetrol_amount(String petrol_amount) {
        this.petrol_amount = petrol_amount;
    }

    public String getTehsil_cover() {
        return tehsil_cover;
    }

    public void setTehsil_cover(String tehsil_cover) {
        this.tehsil_cover = tehsil_cover;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getDealer_count() {
        return dealer_count;
    }

    public void setDealer_count(String dealer_count) {
        this.dealer_count = dealer_count;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSchool_visit_duration() {
        return school_visit_duration;
    }

    public void setSchool_visit_duration(String school_visit_duration) {
        this.school_visit_duration = school_visit_duration;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @SerializedName("flag")
    @Expose
    private String flag;


    //    Yeah Boiii!!!
    @SerializedName(Constants.BATTERY_STATUS)
    @Expose
    private String batteryStatus;

    @SerializedName(Constants.PENDING_MESSAGES)
    @Expose
    private String pendingMessages;

    @SerializedName(Constants.DESCRIPTION)
    @Expose
    private String description;

    public Employee() {
        currentLocation = new Location();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDailyAllowance() {
        return dailyAllowance;
    }

    public void setDailyAllowance(String dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public String getPendingMessages() {
        return pendingMessages;
    }

    public void setPendingMessages(String pendingMessages) {
        this.pendingMessages = pendingMessages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", designation='" + designation + '\'' +
                ", area='" + area + '\'' +
                ", dailyAllowance='" + dailyAllowance + '\'' +
                ", status='" + status + '\'' +
                ", phone='" + phone + '\'' +
                ", salary='" + salary + '\'' +
                ", token='" + token + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", date='" + date + '\'' +
                ", currentLocation=" + currentLocation +
                ", firm='" + firm + '\'' +
                ", morning_attendance='" + morning_attendance + '\'' +
                ", evening_attendance='" + evening_attendance + '\'' +
                ", school_count='" + school_count + '\'' +
                ", petrol_amount='" + petrol_amount + '\'' +
                ", tehsil_cover='" + tehsil_cover + '\'' +
                ", km='" + km + '\'' +
                ", dealer_count='" + dealer_count + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", school_visit_duration='" + school_visit_duration + '\'' +
                ", flag='" + flag + '\'' +
                ", batteryStatus='" + batteryStatus + '\'' +
                ", pendingMessages='" + pendingMessages + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
