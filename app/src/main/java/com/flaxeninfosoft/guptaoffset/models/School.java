package com.flaxeninfosoft.guptaoffset.models;

import android.net.Uri;

import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School {

    @SerializedName(Constants.ID)
    @Expose
    private Long id;

    @SerializedName(Constants.EMPLOYEE_ID)
    @Expose
    private Long empId;

    @SerializedName(Constants.NAME)
    @Expose
    private String name;

    @SerializedName(Constants.TOTAL_STUDENT_STRENGTH)
    @Expose
    private String totalStudentStrength;

    @SerializedName(Constants.NINTH_STUDENT_STRENGTH)
    @Expose
    private String ninthStudentStrength;

    @SerializedName(Constants.TENTH_STUDENT_STRENGTH)
    @Expose
    private String tenthStudentStrength;

    @SerializedName(Constants.ELEVENTH_SCIENCE_STUDENT_STRENGTH)
    @Expose
    private String eleventhScienceStudentStrength;

    @SerializedName(Constants.ELEVENTH_COMMERCE_STUDENT_STRENGTH)
    @Expose
    private String eleventhCommerceStudentStrength;

    @SerializedName(Constants.ELEVENTH_ARTS_STUDENT_STRENGTH)
    @Expose
    private String eleventhArtsStudentStrength;

    @SerializedName(Constants.TWELFTH_SCIENCE_STUDENT_STRENGTH)
    @Expose
    private String twelfthScienceStudentStrength;

    @SerializedName(Constants.TWELFTH_COMMERCE_STUDENT_STRENGTH)
    @Expose
    private String twelfthCommerceStudentStrength;

    @SerializedName(Constants.TWELFTH_ARTS_STUDENT_STRENGTH)
    @Expose
    private String twelfthArtsStudentStrength;

    @SerializedName(Constants.IMAGE)
    @Expose
    private String snap;
    @SerializedName(Constants.SPECIMEN)
    @Expose
    private String specimen;

    @SerializedName(Constants.PHONE)
    @Expose
    private String contact;
//    Yeah Boiii!!!
    @SerializedName(Constants.DATE)
    @Expose
    private String date;

    @SerializedName(Constants.TIME)
    @Expose
    private String time;
    @SerializedName(Constants.LOCATION)
    @Expose
    private Location location;

    @SerializedName(Constants.DISTRICT)
    @Expose
    private Location district;

    @SerializedName(Constants.MEDIUM)
    @Expose
    private String medium;

    @SerializedName(Constants.HINDI)
    @Expose
    private String hindi;

    @SerializedName(Constants.ENGLISH)
    @Expose
    private String english;

    @SerializedName(Constants.NOTES)
    @Expose
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getHindi() {
        return hindi;
    }

    public void setHindi(String hindi) {
        this.hindi = hindi;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }


    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


    public Location getDistrict() {
        return district;
    }

    public void setDistrict(Location district) {
        this.district = district;
    }

    private Uri hoadingImageUri;
    private Uri specimenImageUri;

    public School() {
        location = new Location();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalStudentStrength() {
        return totalStudentStrength;
    }

    public void setTotalStudentStrength(String totalStudentStrength) {
        this.totalStudentStrength = totalStudentStrength;
    }

    public String getNinthStudentStrength() {
        return ninthStudentStrength;
    }

    public void setNinthStudentStrength(String ninthStudentStrength) {
        this.ninthStudentStrength = ninthStudentStrength;
    }

    public String getTenthStudentStrength() {
        return tenthStudentStrength;
    }

    public void setTenthStudentStrength(String tenthStudentStrength) {
        this.tenthStudentStrength = tenthStudentStrength;
    }

    public String getEleventhScienceStudentStrength() {
        return eleventhScienceStudentStrength;
    }

    public void setEleventhScienceStudentStrength(String eleventhScienceStudentStrength) {
        this.eleventhScienceStudentStrength = eleventhScienceStudentStrength;
    }

    public String getEleventhCommerceStudentStrength() {
        return eleventhCommerceStudentStrength;
    }

    public void setEleventhCommerceStudentStrength(String eleventhCommerceStudentStrength) {
        this.eleventhCommerceStudentStrength = eleventhCommerceStudentStrength;
    }

    public String getEleventhArtsStudentStrength() {
        return eleventhArtsStudentStrength;
    }

    public void setEleventhArtsStudentStrength(String eleventhArtsStudentStrength) {
        this.eleventhArtsStudentStrength = eleventhArtsStudentStrength;
    }

    public String getTwelfthScienceStudentStrength() {
        return twelfthScienceStudentStrength;
    }

    public void setTwelfthScienceStudentStrength(String twelfthScienceStudentStrength) {
        this.twelfthScienceStudentStrength = twelfthScienceStudentStrength;
    }

    public String getTwelfthCommerceStudentStrength() {
        return twelfthCommerceStudentStrength;
    }

    public void setTwelfthCommerceStudentStrength(String twelfthCommerceStudentStrength) {
        this.twelfthCommerceStudentStrength = twelfthCommerceStudentStrength;
    }

    public String getTwelfthArtsStudentStrength() {
        return twelfthArtsStudentStrength;
    }

    public void setTwelfthArtsStudentStrength(String twelfthArtsStudentStrength) {
        this.twelfthArtsStudentStrength = twelfthArtsStudentStrength;
    }

    public String getSnap() {
        return snap;
    }

    public void setSnap(String snap) {
        this.snap = snap;
    }

    public String getSpecimen() {
        return specimen;
    }

    public void setSpecimen(String specimen) {
        this.specimen = specimen;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Uri getHoadingImageUri() {
        return hoadingImageUri;
    }

    public void setHoadingImageUri(Uri hoadingImageUri) {
        this.hoadingImageUri = hoadingImageUri;
    }

    public Uri getSpecimenImageUri() {
        return specimenImageUri;
    }

    public void setSpecimenImageUri(Uri specimenImageUri) {
        this.specimenImageUri = specimenImageUri;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", empId=" + empId +
                ", name='" + name + '\'' +
                ", strength='" + totalStudentStrength + '\'' +
                ", snap='" + snap + '\'' +
                ", specimen='" + specimen + '\'' +
                ", contact='" + contact + '\'' +
                ", date='" + date + '\'' +
                ", location=" + location +
                ", hoadingImageUri=" + hoadingImageUri +
                ", specimenImageUri=" + specimenImageUri +
                '}';
    }
}
